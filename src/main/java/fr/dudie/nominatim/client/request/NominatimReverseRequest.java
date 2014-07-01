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

import fr.dudie.nominatim.client.request.paramhelper.BooleanSerializer;
import fr.dudie.nominatim.client.request.paramhelper.OsmType;
import fr.dudie.nominatim.client.request.paramhelper.QueryParameter;

/**
 * Holds request parameters for a reverse geocoding request.
 * <p>
 * Attributes documentation was extracted from <a href="http://wiki.openstreetmap.org/wiki/Nominatim">Nominatim Wiki</a>
 * page on October 26th, 2013.
 * 
 * @author Jeremie Huchet
 */
public class NominatimReverseRequest extends NominatimRequest {

    /**
     * Preferred language order for showing search results, overrides the browser value. Either uses standard rfc2616
     * accept-language string or a simple comma separated list of language codes.
     */
    @QueryParameter("accept-language=%s")
    private String acceptLanguage;

    /** Holds the OSM reverse geocoding request. */
    @QueryParameter
    private ReverseQuery query;

    /** Level of detail required where 0 is country and 18 is house/building. */
    @QueryParameter("zoom=%s")
    private Integer zoom;

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
     * @return the reverse geocoding query parameters
     */
    public ReverseQuery getQuery() {
        return query;
    }

    /**
     * @param query
     *            the reverse geocoding query parameters to set
     */
    public void setQuery(final ReverseQuery query) {
        this.query = query;
    }

    public void setQuery(final OsmType type, final long id) {
        this.query = new OsmTypeAndIdReverseQuery(type, id);
    }

    public void setQuery(final double longitude, final double latitude) {
        this.query = new CoordinatesReverseQuery(longitude, latitude);
    }

    /**
     * Gets the level of detail requested.
     * 
     * 0 is country and 18 is house/building.
     * 
     * @return the level of detail requested
     */
    public Integer getZoom() {
        return zoom;
    }

    /**
     * Sets the level of detail required.
     * 
     * @param zoom
     *            the level of detail, where 0 is country and 18 is house/building
     */
    public void setZoom(final int zoom) {
        this.zoom = zoom;
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
