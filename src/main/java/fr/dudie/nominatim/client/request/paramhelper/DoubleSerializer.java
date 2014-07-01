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

import java.util.Locale;


public class DoubleSerializer implements QueryParameterSerializer {

    /**
     * {@inheritDoc}
     * 
     * @see fr.dudie.nominatim.client.request.paramhelper.QueryParameterSerializer#handle(java.lang.Object)
     */
    @Override
    public String handle(final Object value) {
        if (value instanceof Double) {
            return String.format(Locale.US, "%.14f", (Double) value);
        } else {
            throw new IllegalArgumentException("Can't serialize anything but Double");
        }
    }
}