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

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private static final Logger LOGGER = LoggerFactory.getLogger(OsmType.class);

    /** The parameter value. */
    private final String value;

    private OsmType(final String param) {
        this.value = param;
    }

    @Override
    public String toString() {
        return value;
    }

    public static OsmType from(final String type) {
        OsmType result = null;
        final OsmType[] v = values();
        for (int i = 0; null == result && i < v.length; i++) {
            if (v[i].value.equalsIgnoreCase(type)) {
                result = v[i];
            }
        }
        if (null == result) {
            LOGGER.warn("Unexpected OsmType value: {}. {}", type, Arrays.toString(v));
        }
        return result;
    }
}
