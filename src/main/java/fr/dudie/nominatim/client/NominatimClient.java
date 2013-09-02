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
import java.util.List;

import fr.dudie.nominatim.model.Address;

/**
 * Interface to use the Nominatim Service.
 * 
 * @author Jérémie Huchet
 */
public interface NominatimClient {

    /**
     * Search for results with the given query.
     * 
     * @param query
     *            the query
     * @return a list of results
     * @throws IOException
     *             a communication error occurred
     */
    List<Address> search(String query) throws IOException;

    /**
     * Reverse geocode the given coordinates.
     * 
     * @param longitude
     *            a longitude
     * @param latitude
     *            a latitude
     * @return an address corresponding to the given longitude and latitude or <code>null</code> if
     *         no result found
     * @throws IOException
     *             a communication error occurred
     */
    Address getAddress(double longitude, double latitude) throws IOException;

    /**
     * Reverse geocode the given coordinates using a specific zoom level
     * 
     * @param longitude
     *            a longitude
     * @param latitude
     *            a latitude
     * @param zoom
     *            a osm zoom level
     * @return an address corresponding to the given longitude and latitude or <code>null</code> if
     *         no result found
     * @throws IOException
     *             a communication error occurred
     */
    Address getAddress(double longitude, double latitude, int zoom) throws IOException;

    /**
     * A convenience method to do the same as {@link #getAddress(double, double)} but with int E6
     * latitude and longitude.
     * 
     * @param longitudeE6
     *            a longitude E6
     * @param latitudeE6
     *            a latitude E6
     * @return an address corresponding to the given longitude and latitude or <code>null</code> if
     *         no result found
     * @throws IOException
     *             a communication error occurred
     */
    Address getAddress(int longitudeE6, int latitudeE6) throws IOException;

    /**
     * Reverse geocode the given OSM id.
     * 
     * @param type
     *            An OSM type [N|W|R]
     * @param id
     *            An OSM id
     * @return an address corresponding to the given osm type and id pair or <code>null</code> if no
     *         result found
     * @throws IOException
     */
    Address getAddress(String type, long id) throws IOException;

}
