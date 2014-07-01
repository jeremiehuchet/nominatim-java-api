package fr.dudie.nominatim.client.request.paramhelper;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private static final Logger LOGGER = LoggerFactory.getLogger(OsmType.class);

    /** The parameter value. */
    private final String value;

    private OsmType(final String param) {
        this.value = param;
    }

    @Override
    public String toString() {
        return value;
    }

    public static OsmType from(final String type) {
        OsmType result = null;
        final OsmType[] v = values();
        for (int i = 0; null == result && i < v.length; i++) {
            if (v[i].value.equalsIgnoreCase(type)) {
                result = v[i];
            }
        }
        if (null == result) {
            LOGGER.warn("Unexpected OsmType value: {}. {}", type, Arrays.toString(v));
        }
        return result;
    }
}
