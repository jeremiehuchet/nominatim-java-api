/*
 * This program is free software: you can redistribute it and/or modify it under 
 * the terms of the GNU General Public License as published by the 
 * Free Software Foundation, either version 3 of the License, or 
 * (at your option) any later version. 
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; 
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
 * See the GNU General Public License for more details. 
 * 
 * You should have received a copy of the GNU General Public License along with this program. 
 * If not, see <http://www.gnu.org/licenses/>.
 */
package fr.dudie.nominatim.client;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import fr.dudie.nominatim.gson.ArrayOfAddressElementsDeserializer;
import fr.dudie.nominatim.gson.ArrayOfPolygonPointsDeserializer;
import fr.dudie.nominatim.gson.BoundingBoxDeserializer;
import fr.dudie.nominatim.gson.PolygonPointDeserializer;
import fr.dudie.nominatim.model.Address;
import fr.dudie.nominatim.model.AddressElement;
import fr.dudie.nominatim.model.BoundingBox;
import fr.dudie.nominatim.model.PolygonPoint;



/**
 * An implementation of the Nominatim Api Service.
 * 
 * @author Jérémie Huchet
 */
public final class JsonNominatimClient implements NominatimClient {

    /** The event logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonNominatimClient.class);

    /** Gson instance for Nominatim API calls. */
    private final Gson gsonInstance;

    /** The url to make search queries. */
    private final RequestBuilder searchBuilder;

    /** The url to make a query for a reverse geocoding. */
    private final RequestBuilder reverseGeocodingBuilder;

    /** The HTTP client. */
    private final HttpClient httpClient;

    /** The default response handler for search requests. */
    private final NominatimResponseHandler<List<Address>> defaultSearchResponseHandler;

    /** The default response handler for reverse geocoding requests. */
    private final NominatimResponseHandler<Address> defaultReverseGeocodingHandler;

        
    /**
     * Creates the json nominatim client.
     * 
     * @param httpClient
     *            an HTTP client
     * @param email
     *            an email to add in the HTTP requests parameters to "sign" them
     */
    public JsonNominatimClient(final HttpClient httpClient, final String email) {

        this(httpClient, email, "http://nominatim.openstreetmap.org/");
    }

    /**
     * Creates the json nominatim client for a given api url.
     * 
     * "search" and "reverse" will be appended to the api url in the assumption that the services exist there.
     * Make sure to end the baseApiUrl with a slash if necessary.
     * 
     * @param httpClient
     *            an HTTP client
     * @param email
     *            an email to add in the HTTP requests parameters to "sign" them
     * @param baseApiUrl
     *            an URL pointing to a nominatim service 
     */
    public JsonNominatimClient(final HttpClient httpClient, final String email, String baseApiUrl) {
        this(httpClient, email, new SimpleRequestBuilder(baseApiUrl + "search"), new SimpleRequestBuilder(baseApiUrl + "reverse"));
    }

    
    /**
     * Creates the json nominatim client for a given api url.
     * 
     * "search" and "reverse" will be appended to the api url in the assumption that the services exist there.
     * Make sure to end the baseApiUrl with a slash if necessary.
     * 
     * @param httpClient
     *            an HTTP client
     * @param email
     *            an email to add in the HTTP requests parameters to "sign" them
     * @param searchRequest
     *            a request template for searches
     * @param reverseRequest 
     *            a request template for reverse lookups
     */
    public JsonNominatimClient(final HttpClient httpClient, final String email, RequestBuilder searchRequest, RequestBuilder reverseRequest) {
        this(httpClient, email, null, false, false, searchRequest, reverseRequest);
    }
    
    
    
    /**
     * Creates the json nominatim client.
     * 
     * @param httpClient
     *            an HTTP client
     * @param email
     *            an email to add in the HTTP requests parameters to "sign" them
     * @param searchBounds
     *            the prefered search bounds
     * @param strictBounds
     *            set to true if you want the results to be located into the given bounding box
     * @param polygon
     *            true to get results with polygon points
     */
    public JsonNominatimClient(final HttpClient httpClient, final String email,
    		final BoundingBox searchBounds, final boolean strictBounds, final boolean polygon) {
    	this(httpClient, email, searchBounds, strictBounds, polygon, new SimpleRequestBuilder("http://nominatim.openstreetmap.org/search"), new SimpleRequestBuilder("http://nominatim.openstreetmap.org/reverse"));
    }

    
    /**
     * Creates the json nominatim client.
     * 
     * @param httpClient
     *            an HTTP client
     * @param email
     *            an email to add in the HTTP requests parameters to "sign" them
     * @param searchBounds
     *            the prefered search bounds
     * @param strictBounds
     *            set to true if you want the results to be located into the given bounding box
     * @param polygon
     *            true to get results with polygon points
     * @param searchRequest
     *            a request template for searches
     * @param reverseRequest 
     *            a request template for reverse lookups            
     */
    public JsonNominatimClient(final HttpClient httpClient, final String email,
            final BoundingBox searchBounds, final boolean strictBounds, final boolean polygon, final RequestBuilder searchRequest, final RequestBuilder reverseRequest) {
    	
        // prepare search URL template
    	RequestBuilder search = searchRequest.instantiate();
    	search.add("format", "json");
    	search.add("addressdetails", "1");
    	search.add("email", email);
    	

        if (polygon) {
        	search.add("polygon", "1");
        } else {
        	search.add("polygon", "0");
        }

        if (searchBounds != null) {
            String bounds = 
            		toString(searchBounds.getWest()) + "," +
            		toString(searchBounds.getNorth()) + "," +
            		toString(searchBounds.getEast()) + "," +
            		searchBounds.getSouth();
            
            search.add("viewbox", bounds);
            
            if (strictBounds) {
            	search.add("bounded", "1");
            }
        }
        this.searchBuilder = search.instantiate();

        // prepare reverse geocoding URL template
        RequestBuilder reverse = reverseRequest.instantiate();
        reverse.add("format", "json");
        reverse.add("addressdetails", "1");
        reverse.add("email", email);        
        
        this.reverseGeocodingBuilder = reverse;

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("API search URL: {}", searchBuilder);
            LOGGER.debug("API reverse geocoding URL: {}", reverseGeocodingBuilder);
        }

        // prepare gson instance
        final GsonBuilder gsonBuilder = new GsonBuilder();

        gsonBuilder.registerTypeAdapter(AddressElement[].class,
                new ArrayOfAddressElementsDeserializer());
        gsonBuilder.registerTypeAdapter(PolygonPoint.class, new PolygonPointDeserializer());
        gsonBuilder.registerTypeAdapter(PolygonPoint[].class,
                new ArrayOfPolygonPointsDeserializer());
        gsonBuilder.registerTypeAdapter(BoundingBox.class, new BoundingBoxDeserializer());

        gsonInstance = gsonBuilder.create();

        // prepare httpclient
        this.httpClient = httpClient;
        defaultSearchResponseHandler = new NominatimResponseHandler<List<Address>>(gsonInstance,
                new TypeToken<List<Address>>() {
                }.getType());
        defaultReverseGeocodingHandler = new NominatimResponseHandler<Address>(gsonInstance,
                Address.class);
    }

    /**
     * {@inheritDoc}
     * 
     * @see fr.dudie.nominatim.client.NominatimClient#search(java.lang.String)
     */
    @Override
    public List<Address> search(final String query) throws IOException {

    	RequestBuilder request = searchBuilder.instantiate();
    	request.add("q", URLEncoder.encode(query, "UTF-8"));
    	
        final String apiCall = request.toString();
        LOGGER.debug("request url: {}", apiCall);

        final HttpGet req = new HttpGet(apiCall);

        final List<Address> addresses = httpClient.execute(req, defaultSearchResponseHandler);
        return addresses;
    }

    /**
     * {@inheritDoc}
     * 
     * @see fr.dudie.nominatim.client.NominatimClient#getAddress(double, double)
     */
    @Override
    public Address getAddress(final double longitude, final double latitude) throws IOException {

    	RequestBuilder request = this.reverseGeocodingBuilder.instantiate();
    	request.add("lat", toString(latitude));
    	request.add("lon", toString(longitude));
    	
        final String apiCall = request.toString();
        LOGGER.debug("request url: {}", apiCall);

        final HttpGet req = new HttpGet(apiCall);

        final Address address = httpClient.execute(req, defaultReverseGeocodingHandler);
        return address;
    }

    /**
     * {@inheritDoc}
     * 
     * @see fr.dudie.nominatim.client.NominatimClient#getAddress(int, int)
     */
    @Override
    public Address getAddress(final int longitudeE6, final int latitudeE6) throws IOException {

        return getAddress((longitudeE6 / 1E6), (latitudeE6 / 1E6));
    }

    /**
     * Gets the string representation of a double value.
     * 
     * @param value
     *            a double value
     * @return the string representation (e. g. "48.054600")
     */
    private static String toString(final double value) {

        return String.format("%f", value).replaceAll(",", ".");
    }
}
