package fr.dudie.nominatim.client.request;

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

import java.util.List;

import fr.dudie.nominatim.client.request.paramhelper.BooleanSerializer;
import fr.dudie.nominatim.client.request.paramhelper.QueryParameter;

/**
 * Holds request parameters for a lookup request.
 * <p>
 * Attributes documentation was extracted from <a href="http://wiki.openstreetmap.org/wiki/Nominatim">Nominatim Wiki</a>
 * page on September 1st, 2015.
 * 
 * @author Sunil D S
 */
public class NominatimLookupRequest extends NominatimRequest {

    /**
     * Preferred language order for showing search results, overrides the browser value. Either uses standard rfc2616
     * accept-language string or a simple comma separated list of language codes.
     */
    @QueryParameter("accept-language=%s")
    private String acceptLanguage;

    /** Holds the OSM lookup request. */
    @QueryParameter
    private LookupQuery query;

    /** Include a breakdown of the address into elements. */
    @QueryParameter(value = "addressdetails=%s", serializer = BooleanSerializer.class)
    private Boolean addressDetails;
    
    /**
     * Gets the preferred language order for showing search results which overrides the browser value.
     * 
     * @return the accept-language value
     */
    public String getAcceptLanguage() {
        return acceptLanguage;
    }

    /**
     * Sets the preferred language order for showing search results, overrides the browser value.
     * 
     * @param acceptLanguage
     *            a standard rfc2616 accept-language string or a simple comma separated list of language codes
     */
    public void setAcceptLanguage(final String acceptLanguage) {
        this.acceptLanguage = acceptLanguage;
    }

    /**
     * @return the lookup query parameters
     */
    public LookupQuery getQuery() {
        return query;
    }

    /**
     * @param query
     *            the lookup query parameters to set
     */
    public void setQuery(final LookupQuery query) {
        this.query = query;
    }

    public void setQuery(final List<String> typeId) {
        this.query = new OsmTypeAndIdLookupQuery(typeId);
    }

    /**
     * Include a breakdown of the address into elements.
     * 
     * @return the addressDetails
     */
    public Boolean getAddressDetails() {
        return addressDetails;
    }

    /**
     * Include a breakdown of the address into elements.
     * 
     * @param addressDetails
     *            the addressDetails to set
     */
    public void setAddressDetails(final boolean addressDetails) {
        this.addressDetails = addressDetails;
    }
}

