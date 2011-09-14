/*
 * This program is free software: you can redistribute it and/or modify it under 
 * the terms of the GNU General Public License as published by the 
 * Free Software Foundation, either version 3 of the License, or 
 * (at your option) any later version. 
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; 
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
 * See the GNU General Public License for more details. 
 * 
 * You should have received a copy of the GNU General Public License along with this program. 
 * If not, see <http://www.gnu.org/licenses/>.
 */
package fr.dudie.nominatim.model;

/**
 * Represents an address element.
 * 
 * @author Jérémie Huchet
 */
public class AddressElement {

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
