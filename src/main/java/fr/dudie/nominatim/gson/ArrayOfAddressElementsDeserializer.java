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
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import fr.dudie.nominatim.model.Element;

/**
 * Deserializes the attribute named "address" of a response from the Nominatim API. It will become
 * an Array of {@link Element}s.
 * <p>
 * Sample "address" attribute:
 * 
 * <pre>
 *     "address": {
 *         "road": "Boulevard de Vitré",
 *         "suburb": "Jeanne d'Arc",
 *         "city": "Rennes",
 *         "administrative": "Rennes",
 *         "state": "Britanny",
 *         "postcode": "35042",
 *         "country": "France",
 *         "country_code": "fr"
 *     }
 * </pre>
 * 
 * @author Jérémie Huchet
 */
public final class ArrayOfAddressElementsDeserializer implements JsonDeserializer<Element[]> {

    /** The event logger. */
    private static final Logger LOGGER = LoggerFactory
            .getLogger(ArrayOfAddressElementsDeserializer.class);

    /**
     * {@inheritDoc}
     * 
     * @see com.google.gson.JsonDeserializer#deserialize(com.google.gson.JsonElement,
     *      java.lang.reflect.Type, com.google.gson.JsonDeserializationContext)
     */
    @Override
    public Element[] deserialize(final JsonElement json, final Type typeOfT,
                                 final JsonDeserializationContext context) {

        final Element[] elements;

        if (json.isJsonObject()) {
            elements = new Element[json.getAsJsonObject().entrySet().size()];
            int i = 0;
            for (final Entry<String, JsonElement> elem : json.getAsJsonObject().entrySet()) {
                elements[i] = new Element();
                elements[i].setKey(elem.getKey());
                elements[i].setValue(elem.getValue().getAsString());
                i++;
            }
        } else {
            throw new JsonParseException("Unexpected data: " + json.toString());
        }
        return elements;
    }
}
