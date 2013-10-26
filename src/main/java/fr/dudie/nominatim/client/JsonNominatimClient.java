/*
 * Copyright (C) 2010 Dudie
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.dudie.nominatim.client;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import fr.dudie.nominatim.client.request.NominatimReverseRequest;
import fr.dudie.nominatim.client.request.NominatimSearchRequest;
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
    
    /** The default nominatim base URL. */
    private static final String DEFAULT_BASE_URL = "http://nominatim.openstreetmap.org/";
    
    /** UTF-8 encoding.*/
    public static final String ENCODING_UTF_8 = "UTF-8";

    /** Gson instance for Nominatim API calls. */
    private final Gson gsonInstance;

    /** The url to make search queries. */
    private final String searchUrl;

    /** The url for reverse geocoding. */
    private final String reverseUrl;

    /** The HTTP client. */
    private HttpClient httpClient;

    /** The default response handler for search requests. */
    private NominatimResponseHandler<List<Address>> defaultSearchResponseHandler;

    /** The default response handler for reverse geocoding requests. */
    private NominatimResponseHandler<Address> defaultReverseGeocodingHandler;

    /** Keep the older version for forward compatibility. */
    private NominatimClient deprecatedClient;

    /**
     * Creates the json nominatim client with the default base URL ({@value #DEFAULT_BASE_URL}.
     * 
     * @param httpClient
     *            an HTTP client
     * @param email
     *            an email to add in the HTTP requests parameters to "sign" them
     */
    public JsonNominatimClient(final HttpClient httpClient, final String email) {

        this(DEFAULT_BASE_URL, httpClient, email);
    }

    /**
     * Creates the json nominatim client.
     * 
     * @param baseUrl
     *            the nominatim server url
     * @param httpClient
     *            an HTTP client
     * @param email
     *            an email to add in the HTTP requests parameters to "sign" them (see
     *            http://wiki.openstreetmap.org/wiki/Nominatim_usage_policy)
     */
    public JsonNominatimClient(final String baseUrl, final HttpClient httpClient, final String email) {

        String emailEncoded;
        try {
            emailEncoded = URLEncoder.encode(email, ENCODING_UTF_8);
        } catch (UnsupportedEncodingException e) {
            emailEncoded = email;
        }
        this.searchUrl = String.format("%s/search?format=json&email=%s", baseUrl.replaceAll("/$", ""), emailEncoded);
        this.reverseUrl = String.format("%s/reverse?format=json&email=%s", baseUrl.replaceAll("/$", ""), emailEncoded);

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("API search URL: {}", searchUrl);
            LOGGER.debug("API reverse URL: {}", reverseUrl);
        }

        // prepare gson instance
        final GsonBuilder gsonBuilder = new GsonBuilder();

        gsonBuilder.registerTypeAdapter(AddressElement[].class, new ArrayOfAddressElementsDeserializer());
        gsonBuilder.registerTypeAdapter(PolygonPoint.class, new PolygonPointDeserializer());
        gsonBuilder.registerTypeAdapter(PolygonPoint[].class, new ArrayOfPolygonPointsDeserializer());
        gsonBuilder.registerTypeAdapter(BoundingBox.class, new BoundingBoxDeserializer());

        gsonInstance = gsonBuilder.create();

        // prepare httpclient
        this.httpClient = httpClient;
        defaultSearchResponseHandler = new NominatimResponseHandler<List<Address>>(gsonInstance, new TypeToken<List<Address>>() {
        }.getType());
        defaultReverseGeocodingHandler = new NominatimResponseHandler<Address>(gsonInstance, Address.class);
    }

    /**
     * {@inheritDoc}
     * 
     * @see fr.dudie.nominatim.client.NominatimClient#search(fr.dudie.nominatim.client.request.NominatimSearchRequest)
     */
    @Override
    public List<Address> search(final NominatimSearchRequest search) throws IOException {

        final String apiCall = String.format("%s&%s", searchUrl, search.getQueryString());
        LOGGER.debug("search url: {}", apiCall);
        final HttpGet req = new HttpGet(apiCall);
        return httpClient.execute(req, defaultSearchResponseHandler);
    }
    
    /**
     * {@inheritDoc}
     * 
     * @see fr.dudie.nominatim.client.NominatimClient#getAddress(fr.dudie.nominatim.client.request.NominatimReverseRequest)
     */
    @Override
    public Address getAddress(final NominatimReverseRequest reverse) throws IOException {

        final String apiCall = String.format("%s&%s", reverseUrl, reverse.getQueryString());
        LOGGER.debug("reverse geocoding url: {}", apiCall);
        final HttpGet req = new HttpGet(apiCall);
        return httpClient.execute(req, defaultReverseGeocodingHandler);
    }

    /*
     * DEPRECATED IMPLEMENTATION 
     */

    /**
     * Creates the json nominatim client.
     * 
     * @param baseUrl
     *            the nominatim server url
     * @param httpClient
     *            an HTTP client
     * @param email
     *            an email to add in the HTTP requests parameters to "sign" them (see
     *            http://wiki.openstreetmap.org/wiki/Nominatim_usage_policy)
     * @param searchBounds
     *            the prefered search bounds
     * @param strictBounds
     *            set to true if you want the results to be located into the given bounding box
     * @param polygon
     *            true to get results with polygon points
     */
    @Deprecated
    public JsonNominatimClient(final String baseUrl, final HttpClient httpClient,
            final String email, final BoundingBox searchBounds, final boolean strictBounds,
            final boolean polygon) {
        this(baseUrl, httpClient, email);
        deprecatedClient = new DeprecatedJsonNominatimClient(baseUrl, httpClient, email, null, false, false, null);
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
     *            (see http://wiki.openstreetmap.org/wiki/Nominatim_usage_policy)
     * @param searchBounds
     *            the prefered search bounds
     * @param strictBounds
     *            set to true if you want the results to be located into the
     *            given bounding box
     * @param polygon
     *            true to get results with polygon points
     * @param acceptLanguage
     *            Preferred language order for showing search results, overrides
     *            the browser value.<br/>
     *            Either uses standard rfc2616 accept-language string or a
     *            simple comma separated list of language codes.
     */
    @Deprecated
    public JsonNominatimClient(final String baseUrl, final HttpClient httpClient,
            final String email, final BoundingBox searchBounds, final boolean strictBounds,
            final boolean polygon, final String acceptLanguage) {
        this(baseUrl, httpClient, email);
        deprecatedClient = new DeprecatedJsonNominatimClient(baseUrl, httpClient, email, searchBounds, strictBounds, polygon, acceptLanguage);
    }

    /**
     * {@inheritDoc}
     * 
     * @deprecated Now you should use {@link #search(NominatimSearchRequest)}
     * @see fr.dudie.nominatim.client.NominatimClient#search(java.lang.String)
     */
    @Deprecated
    @Override
    public List<Address> search(final String query) throws IOException {

        return deprecatedClient.search(query);
    }

    /**
     * {@inheritDoc}
     * 
     * @deprecated Now you should use {@link #getAddress(NominatimReverseRequest)}
     * @see fr.dudie.nominatim.client.NominatimClient#getAddress(double, double)
     */
    @Deprecated
    @Override
    public Address getAddress(final double longitude, final double latitude) throws IOException {

        return deprecatedClient.getAddress(longitude, latitude);
    }

    /**
     * {@inheritDoc}
     * 
     * @deprecated Now you should use {@link #getAddress(NominatimReverseRequest)}
     * @see fr.dudie.nominatim.client.NominatimClient#getAddress(double, double, int)
     */
    @Deprecated
    @Override
    public Address getAddress(final double longitude, final double latitude, final int zoom)
            throws IOException {

        return deprecatedClient.getAddress(longitude, latitude);
    }

    /**
     * {@inheritDoc}
     * 
     * @deprecated Now you should use {@link #getAddress(NominatimReverseRequest)}
     * @see fr.dudie.nominatim.client.NominatimClient#getAddress(int, int)
     */
    @Deprecated
    @Override
    public Address getAddress(final int longitudeE6, final int latitudeE6) throws IOException {

        return deprecatedClient.getAddress(longitudeE6, latitudeE6);
    }

    /**
     * {@inheritDoc}
     * 
     * @deprecated Now you should use {@link #getAddress(NominatimReverseRequest)}
     * @see fr.dudie.nominatim.client.NominatimClient#getAddress(String, int)
     */
    @Deprecated
    @Override
    public Address getAddress(final String type, final long id) throws IOException {

        return deprecatedClient.getAddress(type, id);
    }
}
