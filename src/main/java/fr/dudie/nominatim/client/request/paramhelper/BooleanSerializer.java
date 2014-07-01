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
 * Serializes a boolean parameter to nominatim expected format.
 * 
 * @author Jeremie Huchet
 */
public class BooleanSerializer implements QueryParameterSerializer {

    /**
     * Converts the input value into a nominatim boolean parameter.
     * 
     * @return either <code>1</code> or <code>0</code>
     * @see fr.dudie.nominatim.client.request.paramhelper.QueryParameterSerializer#handle(java.lang.Object)
     */
    @Override
    public String handle(final Object value) {
        if (Boolean.parseBoolean(value.toString())) {
            return "1";
        } else {
            return "0";
        }
    }

}
