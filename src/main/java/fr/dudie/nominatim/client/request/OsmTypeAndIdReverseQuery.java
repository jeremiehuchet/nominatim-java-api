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

import fr.dudie.nominatim.client.request.paramhelper.OsmType;
import fr.dudie.nominatim.client.request.paramhelper.QueryParameter;

/**
 * Holds OSM TYPE and ID of the reverse geocoding request.
 * <p>
 * Attributes documentation was extracted from <a href="http://wiki.openstreetmap.org/wiki/Nominatim">Nominatim Wiki</a>
 * page on October 26th, 2013.
 * 
 * @author Jeremie Huchet
 */
public class OsmTypeAndIdReverseQuery extends ReverseQuery {

    /**
     * The type of the searched element.
     */
    @QueryParameter("osm_type=%s")
    private OsmType type;

    /**
     * The id of the searched element.
     */
    @QueryParameter("osm_id=%s")
    private long id;

    /**
     * @param type
     *            the type of the searched element
     * @param id
     *            the id of the searched element
     */
    public OsmTypeAndIdReverseQuery(final OsmType type, final long id) {
        this.type = type;
        this.id = id;
    }

    /**
     * @return the osm type
     */
    public OsmType getType() {
        return type;
    }

    /**
     * @param type
     *            the osm type to set
     */
    public void setType(OsmType type) {
        this.type = type;
    }

    /**
     * @return the osm id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id
     *            the osm id to set
     */
    public void setId(long id) {
        this.id = id;
    }
}
