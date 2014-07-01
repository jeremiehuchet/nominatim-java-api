package fr.dudie.nominatim.gson;

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

import java.lang.reflect.Type;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import fr.dudie.nominatim.model.PolygonPoint;

/**
 * Deserializes a polygonpoint as a {@link PolygonPoint} object.
 * <p>
 * Sample "polygonpoint":
 * 
 * <pre>
 *         [
 *             "34.50669",
 *             "28.0885916"
 *         ],
 * </pre>
 * 
 * @author Jérémie Huchet
 */
public final class PolygonPointDeserializer implements JsonDeserializer<PolygonPoint> {

    /** The event logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(PolygonPointDeserializer.class);

    /**
     * {@inheritDoc}
     * 
     * @see com.google.gson.JsonDeserializer#deserialize(com.google.gson.JsonElement,
     *      java.lang.reflect.Type, com.google.gson.JsonDeserializationContext)
     */
    @Override
    public PolygonPoint deserialize(final JsonElement json, final Type typeOfT,
            final JsonDeserializationContext context) {

        final PolygonPoint point;

        if (json.isJsonArray()) {
            final JsonArray pointsJsonArray = json.getAsJsonArray();
            point = new PolygonPoint();
            point.setLongitude(pointsJsonArray.get(0).getAsDouble());
            point.setLatitude(pointsJsonArray.get(1).getAsDouble());
        } else {
            throw new JsonParseException("Unexpected data: " + json.toString());
        }

        return point;
    }
}
