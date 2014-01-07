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
