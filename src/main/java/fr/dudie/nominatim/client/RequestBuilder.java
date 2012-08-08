package fr.dudie.nominatim.client;

/**
 * Interface used by the NominatimClient for crafting request URLs
 * 
 * @author Claus Stadler <cstadler@informatik.uni-leipzig.de>
 *
 */
public interface RequestBuilder {
	/**
	 * Adds a property to the request
	 * 
	 * @param key
	 * @param value
	 */
	void add(String key, String value);
	
	/**
	 * Treats the request builder as a template and instanciates it.
	 * 
	 * Note that this method differs from cloning:
	 * If the parent request builder keeps track of the properties in e.g. a multimap,
	 * then for the instance these properties might have become parts of the url, so the objects
	 * are not state-equal.
	 * 
	 * 
	 * @return A new RequestBuilder with the same state as this one.
	 */
	RequestBuilder instantiate();
}