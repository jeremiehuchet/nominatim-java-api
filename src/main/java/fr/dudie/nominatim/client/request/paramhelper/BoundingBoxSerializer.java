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
