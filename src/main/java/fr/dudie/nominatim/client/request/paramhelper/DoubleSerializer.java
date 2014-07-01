package fr.dudie.nominatim.client.request.paramhelper;

import java.util.Locale;


public class DoubleSerializer implements QueryParameterSerializer {

    /**
     * {@inheritDoc}
     * 
     * @see fr.dudie.nominatim.client.request.paramhelper.QueryParameterSerializer#handle(java.lang.Object)
     */
    @Override
    public String handle(final Object value) {
        if (value instanceof Double) {
            return String.format(Locale.US, "%.14f", (Double) value);
        } else {
            throw new IllegalArgumentException("Can't serialize anything but Double");
        }
    }
}