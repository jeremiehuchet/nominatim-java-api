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

import fr.dudie.nominatim.client.request.NominatimRequest;

/**
 * Serializes input value using {@link Object#toString()} unless the input object is an instance of
 * {@link NominatimRequest}, then it uses {@link NominatimRequest#getQueryString()}.
 * 
 * @author Jeremie Huchet
 */
public class ToStringSerializer implements QueryParameterSerializer {

    /**
     * {@inheritDoc}
     * 
     * @see fr.dudie.nominatim.client.request.paramhelper.QueryParameterSerializer#handle(java.lang.Object)
     */
    @Override
    public String handle(final Object value) {
        if (value instanceof NominatimRequest) {
            return ((NominatimRequest) value).getQueryString();
        } else {
            return value.toString();
        }
    }
}
