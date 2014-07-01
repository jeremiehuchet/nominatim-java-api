package fr.dudie.nominatim.client.request;

import fr.dudie.nominatim.client.request.paramhelper.QueryParameter;

/**
 * Holds parameters for a simple search query.
 * 
 * @author Jeremie Huchet
 */
public class SimpleSearchQuery extends SearchQuery {

    /** Query string to search for. */
    @QueryParameter("q=%s")
    private String query;

    /**
     * @param simpleQuery
     *            the query string to search for
     */
    public SimpleSearchQuery(final String simpleQuery) {
        this.query = simpleQuery;
    }

    /**
     * @return the query string to search for
     */
    public String getQuery() {
        return query;
    }

    /**
     * @param query
     *            the querystring to set
     */
    public void setQuery(final String query) {
        this.query = query;
    }

}
