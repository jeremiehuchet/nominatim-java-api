package fr.dudie.nominatim.client.request.paramhelper;

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

/**
 * Enumerates OSM POLYGON FORMAT possible parameters values.
 * 
 * @author Jeremie Huchet
 */
public enum PolygonFormat {

    /** No polygons. */
    NONE,

    /** Output geometry of results in geojson format. */
    GEO_JSON("polygon_geojson=1"),

    /** Output geometry of results in kml format. */
    KML("polygon_kml=1"),

    /** Output geometry of results in svg format. */
    SVG("polygon_svg=1"),

    /** Output geometry of results as a WKT. */
    TEXT("polygon_text=1");

    /** The parameter name AND its value. */
    private final String param;

    private PolygonFormat() {
        param = null;
    }

    private PolygonFormat(final String param) {
        this.param = param;
    }

    @Override
    public String toString() {
        return param;
    }
}
