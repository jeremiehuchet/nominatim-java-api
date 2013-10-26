package fr.dudie.nominatim.client.request;

/**
 * Holds keywords for a search request.
 * <p>
 * There is two ways to enter the search query:
 * <ul>
 * <li>using the <i>query<i> <b>q</b> parameter, see {@link SimpleSearchQuery} ;</li>
 * <li>using one or more parameters from the following list : <i>street</i>,
 * <i>city</i>,<i>county</i>,<i>state</i>,<i>country</i>,<i>postal_code</i>, note that this option is currently labeled
 * as <b>experimental</b>, see {@link ExtendedSearchQuery}.</li>
 * </ul>
 * 
 * @author Jeremie Huchet
 * @since 3.0
 */
abstract class SearchQuery extends NominatimRequest {

}
