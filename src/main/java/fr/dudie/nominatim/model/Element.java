package fr.dudie.nominatim.model;

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
 * Represents an address element or nameDetails element.
 * <p>
 * Address element returned from the OpenStreetMap Nominatim API looks like the following:
 * 
 * <pre>
 * {
 *     "road": "Boulevard de Vitré",
 *     "suburb": "Jeanne d'Arc",
 *     "city": "Rennes",
 *     "administrative": "Rennes",
 *     "state": "Britanny",
 *     "postcode": "35042",
 *     "country": "France",
 *     "country_code": "fr"
 * }
 * </pre>
 *
 * <p>
 * NameDetails element returned from the OpenStreetMap Nominatim API looks like the following:
 * <pre>
 *  {
 *      "name":"Pałac Kultury i Nauki","name:de":"Kultur- und Wissenschaftspalast",
 *      "name:en":"Palace of Culture and Science",
 *      (...)
 *      "name:hu":"Kultúra és Tudomány Palotája","name:ru":"Дворец культуры и науки",
 *      "alt_name":"Pałac Młodzieży",
 *      "short_name":"PKiN"}
 * </pre>
 *
 * Keys can't be enumerated entirely, so the java representation is a list of multiple
 * {@link Element}s.
 * 
 * @author Jérémie Huchet
 */
public class Element {

    /** The element key. */
    private String key;

    /** The element value. */
    private String value;

    /**
     * Gets the key.
     * 
     * @return the key
     */
    public final String getKey() {

        return key;
    }

    /**
     * Sets the key.
     * 
     * @param key
     *            the key to set
     */
    public final void setKey(final String key) {

        this.key = key;
    }

    /**
     * Gets the value.
     * 
     * @return the value
     */
    public final String getValue() {

        return value;
    }

    /**
     * Sets the value.
     * 
     * @param value
     *            the value to set
     */
    public final void setValue(final String value) {

        this.value = value;
    }
}
