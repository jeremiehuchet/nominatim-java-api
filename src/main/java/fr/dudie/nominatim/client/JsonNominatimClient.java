package fr.dudie.nominatim.client;

/*
 * [license]
 * Nominatim Java API client
 * ~~~~
 * Copyright (C) 2010 - 2014 Dudie
 * ~~~~
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-3.0.html>.
 * [/license]
 */

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.filosganga.geogson.gson.GeometryAdapterFactory;
import com.github.filosganga.geogson.jts.JtsAdapterFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import fr.dudie.nominatim.client.request.NominatimLookupRequest;
import fr.dudie.nominatim.client.request.NominatimReverseRequest;
import fr.dudie.nominatim.client.request.NominatimSearchRequest;
import fr.dudie.nominatim.client.request.paramhelper.OsmType;
import fr.dudie.nominatim.gson.ArrayOfAddressElementsDeserializer;
import fr.dudie.nominatim.gson.ArrayOfPolygonPointsDeserializer;
import fr.dudie.nominatim.gson.BoundingBoxDeserializer;
import fr.dudie.nominatim.gson.PolygonPointDeserializer;
import fr.dudie.nominatim.model.Address;
import fr.dudie.nominatim.model.Element;
import fr.dudie.nominatim.model.BoundingBox;
import fr.dudie.nominatim.model.PolygonPoint;

/**
 * An implementation of the Nominatim Api Service.
 * 
 * @author Jérémie Huchet
 * @author Sunil D S
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
    
    /** The url for address lookup. */
    private final String lookupUrl;

    /** The default search options. */
    private final NominatimOptions defaults;

    /** The HTTP client. */
    private HttpClient httpClient;

    /** The default response handler for search requests. */
    private NominatimResponseHandler<List<Address>> defaultSearchResponseHandler;

    /** The default response handler for reverse geocoding requests. */
    private NominatimResponseHandler<Address> defaultReverseGeocodingHandler;
    
    /** The default response handler for lookup requests. */
    private NominatimResponseHandler<List<Address>> defaultLookupHandler;

    /**
     * Creates the json nominatim client with the default base URL ({@value #DEFAULT_BASE_URL}.
     * 
     * @param httpClient
     *            an HTTP client
     * @param email
     *            an email to add in the HTTP requests parameters to "sign" them
     */
    public JsonNominatimClient(final HttpClient httpClient, final String email) {

        this(DEFAULT_BASE_URL, httpClient, email, new NominatimOptions());
    }

    /**
     * Creates the json nominatim client with the default base URL ({@value #DEFAULT_BASE_URL}.
     * 
     * @param httpClient
     *            an HTTP client
     * @param email
     *            an email to add in the HTTP requests parameters to "sign" them
     * @param defaults
     *            defaults options, they override null valued requests options
     */
    public JsonNominatimClient(final HttpClient httpClient, final String email, final NominatimOptions defaults) {

        this(DEFAULT_BASE_URL, httpClient, email, defaults);
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

        this(baseUrl, httpClient, email, new NominatimOptions());
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
     * @param defaults
     *            defaults options, they override null valued requests options
     */
    public JsonNominatimClient(final String baseUrl, final HttpClient httpClient, final String email, final NominatimOptions defaults) {

        String emailEncoded;
        try {
            emailEncoded = URLEncoder.encode(email, ENCODING_UTF_8);
        } catch (UnsupportedEncodingException e) {
            emailEncoded = email;
        }
        this.searchUrl = String.format("%s/search?format=jsonv2&email=%s", baseUrl.replaceAll("/$", ""), emailEncoded);
        this.reverseUrl = String.format("%s/reverse?format=json&email=%s", baseUrl.replaceAll("/$", ""), emailEncoded);
        this.lookupUrl = String.format("%s/lookup?format=json&email=%s", baseUrl.replaceAll("/$", ""), emailEncoded);

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("API search URL: {}", searchUrl);
            LOGGER.debug("API reverse URL: {}", reverseUrl);
        }

        this.defaults = defaults;

        // prepare gson instance
        final GsonBuilder gsonBuilder = new GsonBuilder();

        gsonBuilder.registerTypeAdapter(Element[].class, new ArrayOfAddressElementsDeserializer());
        gsonBuilder.registerTypeAdapter(PolygonPoint.class, new PolygonPointDeserializer());
        gsonBuilder.registerTypeAdapter(PolygonPoint[].class, new ArrayOfPolygonPointsDeserializer());
        gsonBuilder.registerTypeAdapter(BoundingBox.class, new BoundingBoxDeserializer());

        gsonBuilder.registerTypeAdapterFactory(new JtsAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new GeometryAdapterFactory());

        gsonInstance = gsonBuilder.create();

        // prepare httpclient
        this.httpClient = httpClient;
        defaultSearchResponseHandler = new NominatimResponseHandler<List<Address>>(gsonInstance, new TypeToken<List<Address>>() {
        }.getType());
        defaultReverseGeocodingHandler = new NominatimResponseHandler<Address>(gsonInstance, Address.class);
        defaultLookupHandler = new NominatimResponseHandler<List<Address>>(gsonInstance, new TypeToken<List<Address>>() {
        }.getType());
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

    /**
     * {@inheritDoc}
     * 
     * @see fr.dudie.nominatim.client.NominatimClient#lookupAddress(fr.dudie.nominatim.client.request.NominatimLookupRequest)
     */
    @Override
    public List<Address> lookupAddress(final NominatimLookupRequest lookup) throws IOException {

        final String apiCall = String.format("%s&%s", lookupUrl, lookup.getQueryString());
        LOGGER.debug("lookup url: {}", apiCall);
        final HttpGet req = new HttpGet(apiCall);
        return httpClient.execute(req, defaultLookupHandler);
    }
    
    /**
     * {@inheritDoc}
     * 
     * @see fr.dudie.nominatim.client.NominatimClient#search(java.lang.String)
     */
    @Override
    public List<Address> search(final String query) throws IOException {

        final NominatimSearchRequest q = new NominatimSearchRequest();
        defaults.mergeTo(q);
        q.setQuery(query);
        return this.search(q);
    }

    /**
     * {@inheritDoc}
     * 
     * @see fr.dudie.nominatim.client.NominatimClient#getAddress(double, double)
     */
    @Override
    public Address getAddress(final double longitude, final double latitude) throws IOException {

        final NominatimReverseRequest q = new NominatimReverseRequest();
        q.setQuery(longitude, latitude);
        return this.getAddress(q);
    }

    /**
     * {@inheritDoc}
     * 
     * @see fr.dudie.nominatim.client.NominatimClient#getAddress(double, double, int)
     */
    @Override
    public Address getAddress(final double longitude, final double latitude, final int zoom)
            throws IOException {

        final NominatimReverseRequest q = new NominatimReverseRequest();
        q.setQuery(longitude, latitude);
        q.setZoom(zoom);
        return this.getAddress(q);
    }

    /**
     * {@inheritDoc}
     * 
     * @see fr.dudie.nominatim.client.NominatimClient#getAddress(int, int)
     */
    @Override
    public Address getAddress(final int longitudeE6, final int latitudeE6) throws IOException {

        return this.getAddress((double) (longitudeE6 / 1E6), (double) (latitudeE6 / 1E6));
    }

    /**
     * {@inheritDoc}
     * 
     * @see fr.dudie.nominatim.client.NominatimClient#getAddress(String, int)
     */
    @Override
    public Address getAddress(final String type, final long id) throws IOException {

        final NominatimReverseRequest q = new NominatimReverseRequest();
        q.setQuery(OsmType.from(type), id);
        return this.getAddress(q);
    }

    /**
     * {@inheritDoc}
     * 
     * @see fr.dudie.nominatim.client.NominatimClient#lookupAddress(java.util.List)
     */
    @Override
    public List<Address> lookupAddress(final List<String> typeId) throws IOException {

        final NominatimLookupRequest q = new NominatimLookupRequest();
        q.setQuery(typeId);
        return this.lookupAddress(q);
    }
}
