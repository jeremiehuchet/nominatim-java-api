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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.itinerennes.nominatim.model.Address;
import fr.itinerennes.nominatim.model.AddressElement;
import fr.itinerennes.nominatim.model.BoundingBox;
import fr.itinerennes.nominatim.model.PolygonPoint;

/**
 * Utilities to convert json responses returned by Nominatim API.
 * 
 * @author Jérémie Huchet
 */
public final class NominatimJsonUtils {

    /** The event logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(NominatimJsonUtils.class);

    /**
     * Converts a json string to a java object.
     * 
     * @param json
     *            a json string representing an {@link Address}
     * @return an address
     * @throws JSONException
     */
    public static Address toAddress(final JSONObject json) throws JSONException {

        final Address address = new Address();
        address.setOsmType(json.optString("osm_type"));

        final JSONArray jsonBbox = json.optJSONArray("boundingbox");
        if (null != jsonBbox) {
            address.setBoundingBox(getBoundingBox(jsonBbox));
        }
        if (LOGGER.isInfoEnabled() && null != json.optJSONArray("polygonpoints")) {
            LOGGER.info("response conatins polygonpoints but they are ignored");
        }

        address.setLatitude(json.optDouble("lat"));
        address.setLongitude(json.optDouble("lon"));

        address.setDisplayName(json.optString("display_name"));
        address.setElementClass(json.optString("class"));
        address.setElementType(json.optString("type"));
        address.setAddressElements(getAddressElements(json.optJSONObject("address")));

        return address;
    }

    /**
     * Converts a json string representing a bounding box to a java object.
     * 
     * @param json
     *            a json string containing an array of 4 double
     * 
     *            <pre>
     *                 boundingbox: [
     *                     "48.4065589904785"
     *                     "48.4071006774902"
     *                     "-4.52232980728149"
     *                     "-4.52062273025513"
     *                 ]
     * </pre>
     * @return a bounding box object
     * @throws JSONException
     */
    private static BoundingBox getBoundingBox(final JSONArray json) throws JSONException {

        if (null == json) {
            return null;
        }

        if (json.length() != 4) {
            final String msg = String
                    .format("A json array representing a bounding box must contains 4 values (this one contains %s values)",
                            json.length());
            throw new JSONException(msg);
        }
        final BoundingBox bbox = new BoundingBox();
        bbox.setEast(json.getDouble(0));
        bbox.setWest(json.getDouble(1));
        bbox.setNorth(json.getDouble(2));
        bbox.setSouth(json.getDouble(3));
        return bbox;
    }

    /**
     * Converts a json string representing an array of polygon points to a java object.
     * 
     * @param json
     *            a json string containing an array of polygon points
     * @return an array of polygon points
     * @throws JSONException
     */
    private static PolygonPoint[] getPlygonPoints(final JSONArray json) throws JSONException {

        if (null == json) {
            return new PolygonPoint[0];
        }

        final PolygonPoint[] points = new PolygonPoint[json.length()];

        for (int i = 0; i < json.length(); i++) {
            final JSONArray jsonPoint = json.getJSONArray(i);
            final PolygonPoint p = new PolygonPoint();
            p.setLongitude(jsonPoint.getDouble(0));
            p.setLatitude(jsonPoint.getDouble(1));
            points[i] = p;
        }

        return points;
    }

    /**
     * Extracts the address properties of a json object.
     * 
     * @param json
     *            the json string containing the address properties.
     * @param a
     *            the address object
     * @return the list of elements contained in the json string
     * @throws JSONException
     */
    private static AddressElement[] getAddressElements(final JSONObject json) throws JSONException {

        if (null == json) {
            return new AddressElement[0];
        }

        final JSONArray keys = json.names();

        final AddressElement[] elems = new AddressElement[keys.length()];

        for (int i = 0; i < json.length(); i++) {
            final AddressElement elem = new AddressElement();
            elem.setKey(keys.getString(i));
            elem.setValue(json.getString(elem.getKey()));
            elems[i] = elem;
        }
        return elems;
    }
}
