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
package fr.itinerennes.nominatim.client;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.itinerennes.nominatim.exceptions.NominatimNoResultException;
import fr.itinerennes.nominatim.model.Address;
import fr.itinerennes.nominatim.model.BoundingBox;

/**
 * An implementation of the Nominatim Api Service.
 * 
 * @author Jérémie Huchet
 */
public class JsonNominatimClient implements NominatimClient {

    /** The event logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonNominatimClient.class);

    /** The HTTP header "Accept". */
    private static final String H_ACCEPT = "Accept";

    private final String searchUrl;

    /** The url to make a query for a reverse geocoding. */
    private final String reverseGeocodingUrl;

    /** The HTTP client. */
    private final HttpClient httpClient;

    /** The default response handler for search requests. */
    private final NominatimSearchResponseHandler defaultSearchResponseHandler = new NominatimSearchResponseHandler();

    /** The default response handler for reverse geocoding requests. */
    private final ReverseGeocodingResponseHandler defaultReverseGeocodingHandler = new ReverseGeocodingResponseHandler();

    /**
     * Creates the json nominatim client.
     * 
     * @param httpClient
     *            an HTTP client
     * @param email
     *            an email to add in the HTTP requests parameters to sign them
     */
    public JsonNominatimClient(final HttpClient httpClient, final String email) {

        this(httpClient, email, null, false);
    }

    /**
     * Creates the json nominatim client.
     * 
     * @param httpClient
     *            an HTTP client
     * @param email
     *            an email to add in the HTTP requests parameters to sign them
     * @param searchBounds
     *            the prefered search bounds
     * @param strictBounds
     *            set to true if you want the results to be located into the given bounding box
     */
    public JsonNominatimClient(final HttpClient httpClient, final String email,
            final BoundingBox searchBounds, final boolean strictBounds) {

        this.httpClient = httpClient;

        final StringBuilder searchUrlBuilder = new StringBuilder();
        searchUrlBuilder
                .append("http://nominatim.openstreetmap.org/search?format=json&polygon=0&addressdetails=1&q=%s&email=");
        searchUrlBuilder.append(email);

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
        this.reverseGeocodingUrl = "http://nominatim.openstreetmap.org/reverse?format=json&polygon=0&addressdetails=1&lat=%s&lon=%s&email="
                + email;

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("API search URL: {}", searchUrl);
            LOGGER.debug("API reverse geocoding URL: {}", reverseGeocodingUrl);
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see fr.itinerennes.nominatim.client.NominatimClient#search(java.lang.String)
     */
    @Override
    public List<Address> search(final String query) throws IOException {

        final String apiCall = String.format(searchUrl, URLEncoder.encode(query, "UTF-8"));
        final HttpGet req = new HttpGet(apiCall);
        req.addHeader(H_ACCEPT, "text/json");
        req.addHeader(H_ACCEPT, "application/json");
        req.addHeader(H_ACCEPT, "*/*");

        final List<Address> addresses = httpClient.execute(req, defaultSearchResponseHandler);
        return addresses;
    }

    /**
     * {@inheritDoc}
     * 
     * @see fr.itinerennes.nominatim.client.NominatimClient#getAddress(double, double)
     */
    @Override
    public final Address getAddress(final double longitude, final double latitude)
            throws IOException, NominatimNoResultException {

        final String apiCall = String.format(reverseGeocodingUrl, toString(latitude),
                toString(longitude));
        final HttpGet req = new HttpGet(apiCall);
        req.addHeader(H_ACCEPT, "text/json");
        req.addHeader(H_ACCEPT, "application/json");
        req.addHeader(H_ACCEPT, "*/*");

        final Address address = httpClient.execute(req, defaultReverseGeocodingHandler);
        return address;
    }

    /**
     * {@inheritDoc}
     * 
     * @see fr.itinerennes.nominatim.client.NominatimClient#getAddress(int, int)
     */
    @Override
    public final Address getAddress(final int longitudeE6, final int latitudeE6)
            throws IOException, NominatimNoResultException {

        return getAddress((longitudeE6 / 1E6), (latitudeE6 / 1E6));
    }

    /**
     * Gets the string representation of a double value.
     * 
     * @param value
     *            a double value
     * @return the string representation (e. g. "48.054600")
     */
    private final static String toString(final double value) {

        return String.format("%f", value).replaceAll(",", ".");
    }
}
