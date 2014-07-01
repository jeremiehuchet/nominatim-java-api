package fr.dudie.nominatim.client.request;

import fr.dudie.nominatim.client.request.paramhelper.QueryParameter;

/**
 * Classes extending this should be able to build URL query string parts to append to nominatim server URL to make
 * requests.
 * 
 * Uses {@link QueryParameter} annotations to describe the query parameters.
 * 
 * @author Jeremie Huchet
 */
public abstract class NominatimRequest {

    /**
     * Generates the query string to be sent to nominatim to execute a request.
     * <p>
     * The query string <b>must</b> have URL encoded parameters.
     * <p>
     * example: <code>q=some%20city&polygon_geojson=1</code>
     * 
     * @return a query string
     */
    public final String getQueryString() {
        return QueryParameterAnnotationHandler.process(this);
    }
}
