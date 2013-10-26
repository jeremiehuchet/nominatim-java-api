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

import java.util.Locale;

import fr.dudie.nominatim.model.BoundingBox;

/**
 * Serializes a bounding box parameter.
 * 
 * @author Jeremie Huchet
 */
public class BoundingBoxSerializer implements QueryParameterSerializer {

    /**
     * Converts the input value to a <code>left,top,right,bottom</code> string representation.
     * 
     * @return a ltrb value
     * @see fr.dudie.nominatim.client.request.paramhelper.QueryParameterSerializer#handle(java.lang.Object)
     */
    @Override
    public String handle(Object value) {
        final StringBuilder s = new StringBuilder();
        if (value instanceof BoundingBox) {
            final BoundingBox bbox = (BoundingBox) value;
            s.append(toString(bbox.getWest())).append(',');
            s.append(toString(bbox.getNorth())).append(',');
            s.append(toString(bbox.getEast())).append(',');
            s.append(toString(bbox.getSouth()));
        } else {
            s.append(value);
        }
        return s.toString();
    }

    /**
     * Gets the string representation of a double value.
     * 
     * @param value
     *            a double value
     * @return the string representation (e. g. "48.054600")
     */
    private static String toString(final double value) {

        return String.format(Locale.US, "%.14f", value);
    }
}
