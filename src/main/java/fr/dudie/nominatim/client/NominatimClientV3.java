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
import java.util.List;

import fr.dudie.nominatim.client.request.NominatimReverseRequest;
import fr.dudie.nominatim.client.request.NominatimSearchRequest;
import fr.dudie.nominatim.model.Address;

public interface NominatimClientV3 extends NominatimClient {

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

}
