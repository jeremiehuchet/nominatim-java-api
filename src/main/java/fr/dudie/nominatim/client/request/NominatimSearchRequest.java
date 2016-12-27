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

import java.util.ArrayList;
import java.util.List;

import fr.dudie.nominatim.client.request.paramhelper.BooleanSerializer;
import fr.dudie.nominatim.client.request.paramhelper.BoundingBoxSerializer;
import fr.dudie.nominatim.client.request.paramhelper.ListSerializer;
import fr.dudie.nominatim.client.request.paramhelper.PolygonFormat;
import fr.dudie.nominatim.client.request.paramhelper.QueryParameter;
import fr.dudie.nominatim.model.BoundingBox;

/**
 * Holds request parameters for a search request.
 * <p>
 * Attributes documentation was extracted from <a href="http://wiki.openstreetmap.org/wiki/Nominatim">Nominatim Wiki</a>
 * page on October 26th, 2013.
 * 
 * @author Jeremie Huchet
 */
public class NominatimSearchRequest extends NominatimRequest {

    /** Holds the query parameters. */
    @QueryParameter(encode = false)
    private SearchQuery query;

    /**
     * Preferred language order for showing search results, overrides the browser value. Either uses standard rfc2616
     * accept-language string or a simple comma separated list of language codes.
     */
    @QueryParameter("accept-language=%s")
    private String acceptLanguage;

    /**
     * Limit search results to a specific country (or a list of countries). &lt;countrycode&gt; should be the ISO
     * 3166-1alpha2 code, e.g. gb for the United Kingdom, de for Germany, etc.
     */
    @QueryParameter(value = "countrycodes=%s", serializer = ListSerializer.class)
    private List<String> countryCodes;

    /** The preferred area to find search results. */
    @QueryParameter(value = "viewbox=%s", serializer = BoundingBoxSerializer.class)
    private BoundingBox viewBox;

    /**
     * Restrict the results to only items contained with the bounding box. <br>
     * Restricting the results to the bounding box also enables searching by amenity only.<br>
     * For example a search query of just "[pub]" would normally be rejected but with bounded=1 will result in a list of
     * items matching within the bounding box.
     */
    @QueryParameter(value = "bounded=%s", serializer = BooleanSerializer.class)
    private Boolean bounded;

    /** Include a breakdown of the address into elements. */
    @QueryParameter(value = "addressdetails=%s", serializer = BooleanSerializer.class)
    private Boolean address;

    /**
     * Include a list of alternative names in the results.
     * These may include language variants, references, operator and brand.
     */
    @QueryParameter(value = "namedetails=%s", serializer = BooleanSerializer.class)
    private Boolean name;

    /**
     * If you do not want certain openstreetmap objects to appear in the search result, give a comma separated list of
     * the place_id's you want to skip.
     */
    @QueryParameter(value = "exclude_place_ids=%s", serializer = ListSerializer.class)
    private List<String> excludePlaceIds;

    /** Limit the number of returned results. */
    @QueryParameter("limit=%s")
    private Integer limit;

    /** Choose output geometry format. */
    @QueryParameter(encode = false)
    private PolygonFormat polygonFormat;

    /**
     * Gets the query parameters.
     * 
     * @return the query parameters
     */
    public SearchQuery getQuery() {
        return query;
    }

    /**
     * Sets query parameters.
     * 
     * @param query
     *            the query parameters object holder
     */
    public void setQuery(final SearchQuery query) {
        this.query = query;
    }

    /**
     * Sets a simple query.
     * 
     * @param simpleQuery
     *            the query string, such as <i>Rennes, France</i>
     */
    public void setQuery(final String simpleQuery) {
        this.query = new SimpleSearchQuery(simpleQuery);
    }

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
     * Gets the country codes.
     * 
     * @return the country codes
     */
    public List<String> getCountryCodes() {
        return countryCodes;
    }

    /**
     * Limit search results to a specific country (or a list of countries). &lt;countrycode&gt; should be the ISO
     * 3166-1alpha2 code, e.g. gb for the United Kingdom, de for Germany, etc.
     * 
     * @param countryCodes
     *            the country codes to set
     */
    public void setCountryCodes(final List<String> countryCodes) {
        this.countryCodes = countryCodes;
    }

    /**
     * Limit search results to a specific country (or a list of countries). &lt;countrycode&gt; should be the ISO
     * 3166-1alpha2 code, e.g. gb for the United Kingdom, de for Germany, etc.
     * 
     * @param countryCode
     *            the country code to add
     */
    public void addCountryCode(final String countryCode) {
        if (null == countryCodes) {
            countryCodes = new ArrayList<String>();
        }
        countryCodes.add(countryCode);
    }

    /**
     * Gets the preferred area to find search results.
     * 
     * @return the viewBox the preferred area to find search results
     */
    public BoundingBox getViewBox() {
        return viewBox;
    }

    /**
     * Sets the preferred area to find search results;
     * 
     * @param viewBox
     *            the vthe preferred area to find search results to set
     */
    public void setViewBox(final BoundingBox viewBox) {
        this.viewBox = viewBox;
    }

    /**
     * Sets the preferred area to find search results;
     * 
     * @param west
     *            the west bound
     * @param north
     *            the north bound
     * @param east
     *            the east bound
     * @param south
     *            the south bound
     */
    public void setViewBox(final double west, final double north, final double east, final double south) {
        this.viewBox = new BoundingBox();
        this.viewBox.setWest(west);
        this.viewBox.setNorth(north);
        this.viewBox.setEast(east);
        this.viewBox.setSouth(south);
    }

    /**
     * Sets the preferred area to find search results;
     * 
     * @param westE6
     *            the west bound
     * @param northE6
     *            the north bound
     * @param eastE6
     *            the east bound
     * @param southE6
     *            the south bound
     */
    public void setViewBox(final int westE6, final int northE6, final int eastE6, final int southE6) {
        this.viewBox = new BoundingBox();
        this.viewBox.setWestE6(westE6);
        this.viewBox.setNorthE6(northE6);
        this.viewBox.setEastE6(eastE6);
        this.viewBox.setSouthE6(southE6);
    }

    /**
     * Gets whether or not the results will be bounded to the given {@link #viewBox}.
     * 
     * @return the bounded
     */
    public Boolean getBounded() {
        return bounded;
    }

    /**
     * Setting <code>true</code> will restrict the results to only items contained with the bounding box. <br>
     * Restricting the results to the bounding box also enables searching by amenity only.<br>
     * For example a search query of just "[pub]" would normally be rejected but with bounded=1 will result in a list of
     * items matching within the bounding box.
     * 
     * @param bounded
     *            the bounded to set
     */
    public void setBounded(final boolean bounded) {
        this.bounded = bounded;
    }

    /**
     * When true, include a breakdown of the address into elements.
     * 
     * @return the address
     */
    public boolean getAddress() {
        return address;
    }

    /**
     * Include a breakdown of the address into elements.
     * 
     * @param address
     *            the address to set
     */
    public void setAddress(final boolean address) {
        this.address = address;
    }

    /**
     * When true, include a list of alternative names in the results.
     *
     * @return
     */
    public Boolean getName() {
        return name;
    }

    /**
     * Include a list of alternative names in the results.
     *
     * @param name
     */
    public void setName(Boolean name) {
        this.name = name;
    }

    /**
     * A list of OSM elements ids to be excluded from search results.
     * 
     * @return the excluded place ids
     */
    public List<String> getExcludePlaceIds() {
        return excludePlaceIds;
    }

    /**
     * If you do not want certain openstreetmap objects to appear in the search result, give a list of the place_id's
     * you want to skip.
     * 
     * @param excludePlaceIds
     *            the excluded place ids to set
     */
    public void setExcludePlaceIds(final List<String> excludePlaceIds) {
        this.excludePlaceIds = excludePlaceIds;
    }

    /**
     * If you do not want certain openstreetmap objects to appear in the search result, give a list of the place_id's
     * you want to skip.
     * 
     * @param placeId
     *            the place id to exclude
     */
    public void addExcludedlaceId(final String placeId) {
        if (null == this.countryCodes) {
            this.excludePlaceIds = new ArrayList<String>();
        }
        this.excludePlaceIds.add(placeId);
    }

    /**
     * Gets the maximum number of results to be returned.
     * 
     * @return the limit
     */
    public Integer getLimit() {
        return limit;
    }

    /**
     * Limit the number of returned results.
     * 
     * @param limit
     *            the limit to set
     */
    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    /**
     * Gets the output geometry format.
     * 
     * @return the polygon format
     */
    public PolygonFormat getPolygonFormat() {
        return polygonFormat;
    }

    /**
     * Choose output geometry format.
     * 
     * @param polygonFormat
     *            the polygon format to set
     */
    public void setPolygonFormat(PolygonFormat polygonFormat) {
        this.polygonFormat = polygonFormat;
    }
}
