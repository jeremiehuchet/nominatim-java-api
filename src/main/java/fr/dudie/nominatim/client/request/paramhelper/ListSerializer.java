package fr.dudie.nominatim.client.request.paramhelper;

import java.util.List;

/**
 * Serializes list values.
 * 
 * @author Jeremie Huchet
 */
public class ListSerializer implements QueryParameterSerializer {

    /**
     * Converts the list to a comma separated representation.
     * 
     * @return a comma separated list of values
     * @see fr.dudie.nominatim.client.request.paramhelper.QueryParameterSerializer#handle(java.lang.Object)
     */
    @Override
    public String handle(final Object value) {
        final StringBuilder s = new StringBuilder();
        if (value instanceof List) {
            final List<?> l = (List<?>) value;
            for (final Object o : l) {
                if (s.length() > 0) {
                    s.append(',');
                }
                s.append(o.toString());
            }
        } else {
            s.append(value);
        }
        return s.toString();
    }
}
