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
