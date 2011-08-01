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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.dudie.nominatim.model.Address;

/**
 * Parses a json response from the Nominatim API for a reverse geocoding request.
 * 
 * @author Jérémie Huchet
 */
public final class NominatimSearchResponseHandler implements ResponseHandler<List<Address>> {

    /** The event logger. */
    private static final Logger LOGGER = LoggerFactory
            .getLogger(NominatimSearchResponseHandler.class);

    /**
     * {@inheritDoc}
     * 
     * @see org.apache.http.client.ResponseHandler#handleResponse(org.apache.http.HttpResponse)
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<Address> handleResponse(final HttpResponse response) throws IOException {

        String content = null;
        List<Address> addresses = Collections.EMPTY_LIST;
        try {
            content = EntityUtils.toString(response.getEntity(), "utf-8");
            final JSONArray json = new JSONArray(content);
            addresses = new ArrayList<Address>(json.length());
            for (int i = 0; i < json.length(); i++) {
                addresses.add(NominatimJsonUtils.toAddress(json.getJSONObject(i)));
            }
        } catch (final JSONException e) {
            throw new IOException("Unable to parse the json response received from Nominatim:\n"
                    + content);
        }

        return addresses;
    }
}
