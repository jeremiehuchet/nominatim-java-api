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

import java.util.List;

/**
 * Serializes list values.
 * 
 * @author Jeremie Huchet
 */
public class ListSerializer implements QueryParameterSerializer {

    /**
     * Converts the list to a comma separated representation.
     * 
     * @return a comma separated list of values
     * @see fr.dudie.nominatim.client.request.paramhelper.QueryParameterSerializer#handle(java.lang.Object)
     */
    @Override
    public String handle(final Object value) {
        final StringBuilder s = new StringBuilder();
        if (value instanceof List) {
            final List<?> l = (List<?>) value;
            for (final Object o : l) {
                if (s.length() > 0) {
                    s.append(',');
                }
                s.append(o.toString());
            }
        } else {
            s.append(value);
        }
        return s.toString();
    }
}
