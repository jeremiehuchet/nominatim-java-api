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
 * Holds parameters for a simple search query.
 * 
 * @author Jeremie Huchet
 */
public class SimpleSearchQuery extends SearchQuery {

    /** Query string to search for. */
    @QueryParameter("q=%s")
    private String query;

    /**
     * @param simpleQuery
     *            the query string to search for
     */
    public SimpleSearchQuery(final String simpleQuery) {
        this.query = simpleQuery;
    }

    /**
     * @return the query string to search for
     */
    public String getQuery() {
        return query;
    }

    /**
     * @param query
     *            the querystring to set
     */
    public void setQuery(final String query) {
        this.query = query;
    }

}
