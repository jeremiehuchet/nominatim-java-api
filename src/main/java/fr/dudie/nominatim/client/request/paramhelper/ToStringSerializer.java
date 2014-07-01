package fr.dudie.nominatim.client.request.paramhelper;

import fr.dudie.nominatim.client.request.NominatimRequest;

/**
 * Serializes input value using {@link Object#toString()} unless the input object is an instance of
 * {@link NominatimRequest}, then it uses {@link NominatimRequest#getQueryString()}.
 * 
 * @author Jeremie Huchet
 */
public class ToStringSerializer implements QueryParameterSerializer {

    /**
     * {@inheritDoc}
     * 
     * @see fr.dudie.nominatim.client.request.paramhelper.QueryParameterSerializer#handle(java.lang.Object)
     */
    @Override
    public String handle(final Object value) {
        if (value instanceof NominatimRequest) {
            return ((NominatimRequest) value).getQueryString();
        } else {
            return value.toString();
        }
    }
}
