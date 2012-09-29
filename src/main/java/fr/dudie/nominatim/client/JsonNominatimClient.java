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

    private static final String EMPTY_STRING = "";

	/** The event logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonNominatimClient.class);

    /** Gson instance for Nominatim API calls. */
    private final Gson gsonInstance;

    /** The url to make search queries. */
    private final String searchUrl;

    /** The url to make a query for a latitude/longitude reverse geocoding. */
    private final String reverseGeocodingUrl;
    
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

        this("http://nominatim.openstreetmap.org/", httpClient, email, null, false, false);
    }

    /**
     * Creates the json nominatim client.
     * 
     * @param baseUrl
     *            the nominatim server url
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
    public JsonNominatimClient(final String baseUrl, final HttpClient httpClient, final String email,
            final BoundingBox searchBounds, final boolean strictBounds, final boolean polygon) {

        // prepare search URL template
        final StringBuilder searchUrlBuilder = new StringBuilder();
        searchUrlBuilder.append(baseUrl);
        searchUrlBuilder.append("/search?format=json&addressdetails=1&q=%s&email=");
        searchUrlBuilder.append(email);
        if (polygon) {
            searchUrlBuilder.append("&polygon=1");
        } else {
            searchUrlBuilder.append("&polygon=0");
        }

        if (searchBounds != null) {
            searchUrlBuilder.append("&viewbox=");
            searchUrlBuilder.append(toString(searchBounds.getWest())).append(",");
            searchUrlBuilder.append(toString(searchBounds.getNorth())).append(",");
            searchUrlBuilder.append(toString(searchBounds.getEast())).append(",");
            searchUrlBuilder.append(toString(searchBounds.getSouth()));

            if (strictBounds) {
                searchUrlBuilder.append("&bounded=1");
            }
        }
        this.searchUrl = searchUrlBuilder.toString();

        // prepare reverse geocoding URL template
        final StringBuilder reverseGeocodingUrlBuilder = new StringBuilder();
        reverseGeocodingUrlBuilder.append(baseUrl);
        reverseGeocodingUrlBuilder
                .append("/reverse?format=json&addressdetails=1&lat=%s&lon=%s&email=&osm_type=%s&osm_id=%s");
        reverseGeocodingUrlBuilder.append(email);
        this.reverseGeocodingUrl = reverseGeocodingUrlBuilder.toString();
        
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("API search URL: {}", searchUrl);
            LOGGER.debug("API reverse geocoding URL: {}", reverseGeocodingUrl);
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

        final String apiCall = String.format(searchUrl, URLEncoder.encode(query, "UTF-8"));
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

        final String apiCall = buildReverseGeocodingUrlFor(longitude, latitude);
        LOGGER.debug("request url: {}", apiCall);

        final HttpGet req = new HttpGet(apiCall);

        final Address address = httpClient.execute(req, defaultReverseGeocodingHandler);
        return address;
    }
    
    /**
     * {@inheritDoc}
     * 
     * @see fr.dudie.nominatim.client.NominatimClient#getAddress(double, double, int)
     */
	@Override
	public Address getAddress(double longitude, double latitude, int zoom) throws IOException {

        final StringBuilder apiCallBuilder = new StringBuilder(buildReverseGeocodingUrlFor(longitude, latitude));
        apiCallBuilder.append("&zoom=");
        apiCallBuilder.append(Integer.toString(zoom));
        
        final String apiCall = apiCallBuilder.toString();
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
     * {@inheritDoc}
     * 
     * @see fr.dudie.nominatim.client.NominatimClient#getAddress(int, int)
     */
    @Override
    public Address getAddress(final String type, final long id) throws IOException {
    	 final String apiCall = buildReverseGeocodingUrlFor(type, id);
         LOGGER.debug("request url: {}", apiCall);

         final HttpGet req = new HttpGet(apiCall);

         final Address address = httpClient.execute(req, defaultReverseGeocodingHandler);
         return address;
    }
    
	/**
     * Builds the full url for a latitude/longitude reverse geocoding api call
     * 
     * @param longitude
     * @param latitude
     * @return the reverse geocoding api call url
     */
    private String buildReverseGeocodingUrlFor(final double longitude,
			final double latitude) {
		return String.format(reverseGeocodingUrl, toString(latitude),
	            toString(longitude), EMPTY_STRING, EMPTY_STRING);
	}
    
    private String buildReverseGeocodingUrlFor(String type, long id) {
    	return String.format(reverseGeocodingUrl, EMPTY_STRING, EMPTY_STRING,
    			type, toString(id));
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
