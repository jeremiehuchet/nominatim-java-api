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

import fr.dudie.nominatim.model.BoundingBox;

/**
 * Deserializes the attribute named "boundingbox" of a response from the Nominatim API. It will
 * become an {@link BoundingBox}.
 * <p>
 * Sample "boundingbox" attribute:
 * 
 * <pre>
 *     "boundingbox": [
 *         "48.1190567016602", S
 *         "48.1191635131836", N
 *         "-1.6499342918396", W
 *         "-1.64988231658936" E
 *     ],
 * </pre>
 * 
 * @author Jérémie Huchet
 */
public final class BoundingBoxDeserializer implements JsonDeserializer<BoundingBox> {

    /** The event logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(BoundingBoxDeserializer.class);

    /**
     * {@inheritDoc}
     * 
     * @see com.google.gson.JsonDeserializer#deserialize(com.google.gson.JsonElement,
     *      java.lang.reflect.Type, com.google.gson.JsonDeserializationContext)
     */
    @Override
    public BoundingBox deserialize(final JsonElement json, final Type typeOfT,
            final JsonDeserializationContext context) {

        final BoundingBox bbox;

        if (json.isJsonArray()) {
            final JsonArray bboxJsonArray = json.getAsJsonArray();
            bbox = new BoundingBox();
            bbox.setSouth(bboxJsonArray.get(0).getAsDouble());
            bbox.setNorth(bboxJsonArray.get(1).getAsDouble());
            bbox.setWest(bboxJsonArray.get(2).getAsDouble());
            bbox.setEast(bboxJsonArray.get(3).getAsDouble());
        } else {
            throw new JsonParseException("Unexpected data: " + json.toString());
        }

        return bbox;
    }
}
