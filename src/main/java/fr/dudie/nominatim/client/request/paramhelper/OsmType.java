/*
 * Copyright (C) 2010 Dudie
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.dudie.nominatim.client.request.paramhelper;

/**
 * Enumerates OSM TYPE possible parameters values.
 * 
 * @author Jeremie Huchet
 */
public enum OsmType {

    /** A node. */
    NODE("N"),

    /** A way. */
    WAY("W"),

    /** A relation. */
    RELATION("R");

    /** The parameter value. */
    private final String value;

    private OsmType(final String param) {
        this.value = param;
    }

    @Override
    public String toString() {
        return value;
    }
}
