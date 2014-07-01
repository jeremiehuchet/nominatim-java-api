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

import fr.dudie.nominatim.client.request.paramhelper.QueryParameter;

/**
 * Classes extending this should be able to build URL query string parts to append to nominatim server URL to make
 * requests.
 * 
 * Uses {@link QueryParameter} annotations to describe the query parameters.
 * 
 * @author Jeremie Huchet
 */
public abstract class NominatimRequest {

    /**
     * Generates the query string to be sent to nominatim to execute a request.
     * <p>
     * The query string <b>must</b> have URL encoded parameters.
     * <p>
     * example: <code>q=some%20city&polygon_geojson=1</code>
     * 
     * @return a query string
     */
    public final String getQueryString() {
        return QueryParameterAnnotationHandler.process(this);
    }
}
