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

import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.dudie.nominatim.model.Address;

/**
 * Parses a json response from the Nominatim API for a reverse geocoding request.
 * 
 * @author Jérémie Huchet
 */
public final class ReverseGeocodingResponseHandler implements ResponseHandler<Address> {

    /** The event logger. */
    private static final Logger LOGGER = LoggerFactory
            .getLogger(ReverseGeocodingResponseHandler.class);

    /**
     * {@inheritDoc}
     * 
     * @see org.apache.http.client.ResponseHandler#handleResponse(org.apache.http.HttpResponse)
     */
    @Override
    public Address handleResponse(final HttpResponse response) throws IOException {

        String content = null;
        Address address = null;
        try {
            content = EntityUtils.toString(response.getEntity());
            final JSONObject json = new JSONObject(content);
            address = NominatimJsonUtils.toAddress(json);
        } catch (final JSONException e) {
            throw new IOException("Unable to parse the json response received from Nominatim:\n"
                    + content);
        }

        return address;
    }
}
