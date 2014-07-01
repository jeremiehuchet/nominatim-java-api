package fr.dudie.nominatim.client.request.paramhelper;

/**
 * Serializes a boolean parameter to nominatim expected format.
 * 
 * @author Jeremie Huchet
 */
public class BooleanSerializer implements QueryParameterSerializer {

    /**
     * Converts the input value into a nominatim boolean parameter.
     * 
     * @return either <code>1</code> or <code>0</code>
     * @see fr.dudie.nominatim.client.request.paramhelper.QueryParameterSerializer#handle(java.lang.Object)
     */
    @Override
    public String handle(final Object value) {
        if (Boolean.parseBoolean(value.toString())) {
            return "1";
        } else {
            return "0";
        }
    }

}
