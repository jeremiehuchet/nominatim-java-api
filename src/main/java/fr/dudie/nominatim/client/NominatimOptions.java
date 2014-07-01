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

import java.util.Locale;

import fr.dudie.nominatim.client.request.NominatimSearchRequest;
import fr.dudie.nominatim.client.request.paramhelper.PolygonFormat;
import fr.dudie.nominatim.model.BoundingBox;

/**
 * Nominatim client options.
 * 
 * @author Jeremie Huchet
 */
public class NominatimOptions {

    /** The preferred search bounds. */
    private BoundingBox viewBox;

    /** True to use strict bounds. */
    private Boolean bounded;

    /** Default polygon points format. */
    private PolygonFormat polygonFormat;

    /** Preferred localization for results. */
    private Locale acceptLanguage;

    /**
     * Gets the preferred search bounds.
     * 
     * @return the preferred search bounds
     */
    public BoundingBox getViewBox() {
        return viewBox;
    }

    /**
     * Sets the preferred search bounds.
     * 
     * @param searchBounds
     *            the preferred search bounds to set
     */
    public void setViewBox(final BoundingBox searchBounds) {
        this.viewBox = searchBounds;
    }

    /**
     * Gets whether or not the search is strictly bounded.
     * 
     * @return whether or not the search is strictly bounded
     */
    public boolean isBounded() {
        return bounded;
    }

    /**
     * Sets whether or not the search is strictly bounded.
     * 
     * @param bounded
     *            whether or not the search is strictly bounded
     */
    public void setBounded(boolean bounded) {
        this.bounded = bounded;
    }

    /**
     * Gets the default polygon points format.
     * 
     * @return the default polygon points format
     */
    public PolygonFormat getPolygonFormat() {
        return polygonFormat;
    }

    /**
     * Sets the default polygon points format.
     * 
     * @param format
     *            the default polygon points format to set
     */
    public void setPolygonFormat(final PolygonFormat format) {
        this.polygonFormat = format;
    }

    /**
     * Gets the preferred locale for results.
     * 
     * @return the preferred locale for results
     */
    public Locale getAcceptLanguage() {
        return acceptLanguage;
    }

    /**
     * Sets the preferred locale for results.
     * 
     * @param acceptLanguage
     *            the preferred locale for results
     */
    public void setAcceptLanguage(final Locale acceptLanguage) {
        this.acceptLanguage = acceptLanguage;
    }

    /**
     * Merge this default options to the given request.
     * 
     * @param request
     *            the request where the result is merged
     */
    void mergeTo(final NominatimSearchRequest request) {
        if (null == request.getBounded() && null != bounded) {
            request.setBounded(bounded);
        }
        if (null == request.getViewBox()) {
            request.setViewBox(viewBox);
        }
        if (null == request.getPolygonFormat()) {
            request.setPolygonFormat(polygonFormat);
        }
        if (null == request.getAcceptLanguage() && null != acceptLanguage) {
            request.setAcceptLanguage(acceptLanguage.toString());
        }
    }
}
