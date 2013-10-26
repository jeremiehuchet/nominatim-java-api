package fr.dudie.nominatim.client.request.paramhelper;

/**
 * Enumerates OSM TYPE possible parameters values.
 * 
 * @author Jeremie Huchet
 */
public enum OsmType {

    /** A node. */
    NODE("N"),

    /** A way. */
    WAY("W"),

    /** A relation. */
    RELATION("R");

    /** The parameter value. */
    private final String value;

    private OsmType(final String param) {
        this.value = param;
    }

    @Override
    public String toString() {
        return value;
    }
}
