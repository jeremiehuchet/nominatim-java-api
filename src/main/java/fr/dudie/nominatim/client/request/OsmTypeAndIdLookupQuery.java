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

import fr.dudie.nominatim.client.request.paramhelper.ListSerializer;
import fr.dudie.nominatim.client.request.paramhelper.QueryParameter;

/**
 * Holds OSM TYPE and ID of the lookup request.
 * <p>
 * Attributes documentation was extracted from <a href="http://wiki.openstreetmap.org/wiki/Nominatim">Nominatim Wiki</a>
 * page on September 1st, 2015.
 * 
 * @author Sunil D S
 */
public class OsmTypeAndIdLookupQuery extends LookupQuery {

    /**
     * List of type and id of the elements to lookup.
     */
    @QueryParameter(value = "osm_ids=%s", serializer = ListSerializer.class)
    private List<String> typeId;

    /**
     * @param typeId
     *            list of type and id of the elements to lookup
     */
    public OsmTypeAndIdLookupQuery(final List<String> typeId) {
        this.typeId = typeId;
    }

    /**
     * @return the typeId
     */
    public List<String> getTypeId() {
        return typeId;
    }

    /**
     * @param typeId
     *            list of type and id of the elements to lookup set
     */
    public void setTypeId(List<String> typeId) {
        this.typeId = typeId;
    }
}
