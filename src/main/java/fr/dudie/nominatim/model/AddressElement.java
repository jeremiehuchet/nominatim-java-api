package fr.dudie.nominatim.model;

/**
 * Represents an address element.
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
 * Keys can't be enumerated entirely, so the java representation is a list of multiple
 * {@link AddressElement}s.
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
