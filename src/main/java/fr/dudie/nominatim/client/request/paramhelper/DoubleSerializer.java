package fr.dudie.nominatim.client.request.paramhelper;


public class DoubleSerializer implements QueryParameterSerializer {

    /**
     * {@inheritDoc}
     * 
     * @see fr.dudie.nominatim.client.request.paramhelper.QueryParameterSerializer#handle(java.lang.Object)
     */
    @Override
    public String handle(final Object value) {
        if (value instanceof Double) {
            return String.format("%f", (Double) value);
        } else {
            throw new IllegalArgumentException("Can't serialize anything but Double");
        }
    }
}