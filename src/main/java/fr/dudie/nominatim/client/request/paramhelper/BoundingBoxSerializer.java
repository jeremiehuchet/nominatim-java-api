package fr.dudie.nominatim.client.request.paramhelper;

import fr.dudie.nominatim.model.BoundingBox;

/**
 * Serializes a bounding box parameter.
 * 
 * @author Jeremie Huchet
 */
public class BoundingBoxSerializer implements QueryParameterSerializer {

    /** Serializer to transform double values to string. */
    private final DoubleSerializer doubleSerializer = new DoubleSerializer();

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
            s.append(doubleSerializer.handle(bbox.getWest())).append(',');
            s.append(doubleSerializer.handle(bbox.getNorth())).append(',');
            s.append(doubleSerializer.handle(bbox.getEast())).append(',');
            s.append(doubleSerializer.handle(bbox.getSouth()));
        } else {
            s.append(value);
        }
        return s.toString();
    }
}
