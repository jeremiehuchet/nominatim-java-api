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
import java.util.List;

import fr.dudie.nominatim.client.request.NominatimLookupRequest;
import fr.dudie.nominatim.client.request.NominatimReverseRequest;
import fr.dudie.nominatim.client.request.NominatimSearchRequest;
import fr.dudie.nominatim.model.Address;

/**
 * Interface to use the Nominatim Service.
 * 
 * @author Jérémie Huchet
 * @author Sunil D S
 * @since 1.0
 */
public interface NominatimClient {

    /**
     * Search for results with the given query.
     * <p>
     * See also {@link #search(NominatimSearchRequest)} to obtain more result details.
     * 
     * @param query
     *            the query
     * @return a list of results
     * @throws IOException
     *             a communication error occurred
     * @since 1.0
     */
    List<Address> search(String query) throws IOException;

    /**
     * Reverse geocode the given coordinates.
     * 
     * @param longitude
     *            a longitude
     * @param latitude
     *            a latitude
     * @return an address corresponding to the given longitude and latitude or <code>null</code> if no result found
     * @throws IOException
     *             a communication error occurred
     * @since 1.0
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
     * @return an address corresponding to the given longitude and latitude or <code>null</code> if no result found
     * @throws IOException
     *             a communication error occurred
     * @since 2.0.1
     */
    Address getAddress(double longitude, double latitude, int zoom) throws IOException;

    /**
     * A convenience method to do the same as {@link #getAddress(double, double)} but with int E6 latitude and
     * longitude.
     * 
     * @param longitudeE6
     *            a longitude E6
     * @param latitudeE6
     *            a latitude E6
     * @return an address corresponding to the given longitude and latitude or <code>null</code> if no result found
     * @throws IOException
     *             a communication error occurred
     * @since 1.0
     */
    Address getAddress(int longitudeE6, int latitudeE6) throws IOException;

    /**
     * Reverse geocode the given OSM id.
     * 
     * @param type
     *            An OSM type [N|W|R]
     * @param id
     *            An OSM id
     * @return an address corresponding to the given osm type and id pair or <code>null</code> if no result found
     * @throws IOException
     * @since 2.0.1
     * @deprecated
     * @see fr.dudie.nominatim.client.NominatimClientV3#getAddress(fr.dudie.nominatim.client.request.NominatimReverseRequest)
     */
    @Deprecated
    Address getAddress(String type, long id) throws IOException;
    
    /**
     * This method can be used to lookup addresses with an OSM type and ID
     * 
     * @param typeId
     *            [N|W|R]ID
     * @return a list of addresses corresponding to the given OSM type and ID or <code>null</code> if no result found
     * @throws IOException
     *             a communication error occurred
     * @since 3.2
     */
    List<Address> lookupAddress(List<String> typeId) throws IOException;

    /**
     * Search for addresses.
     * 
     * @param search
     *            the search request parameters
     * @return a list of results
     * @throws IOException
     *             a communication error occurred
     * @since 3.0
     */
    List<Address> search(NominatimSearchRequest search) throws IOException;

    /**
     * Reverse geocoding request.
     * 
     * @param reverse
     *            a reverse geocoding request
     * @return an address corresponding to the given longitude and latitude or <code>null</code> if no result found
     * @throws IOException
     *             a communication error occurred
     * @since 3.0
     */
    Address getAddress(NominatimReverseRequest reverse) throws IOException;
    
    /**
     * Address lookup request.
     * 
     * @param lookup
     *            a lookup request
     * @return a list of addresses corresponding to the given OSM type and ID or <code>null</code> if no result found
     * @throws IOException
     *             a communication error occurred
     * @since 3.2
     */
    List<Address> lookupAddress(NominatimLookupRequest lookup) throws IOException;

}
