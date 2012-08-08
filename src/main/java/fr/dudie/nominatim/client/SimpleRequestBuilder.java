package fr.dudie.nominatim.client;

/**
 * A simple implementation of the RequestBuilder that
 * adds properties to an URL's query string.
 * 
 * @author Claus Stadler <cstadler@informatik.uni-leipzig.de>
 *
 */
class SimpleRequestBuilder
	implements RequestBuilder
{
	private String url;
	boolean isFirstArg;
	
	/**
	 * Creates a SimpleRequestBuilder object.
	 * 
	 * @param baseUrl The baseUrl. Must not contain a query string.
	 */
	public SimpleRequestBuilder(String baseUrl) {
		this(baseUrl, true);
	}

	/**
	 * Creates a SimpleRequestBuilder object.
	 * 
	 * @param baseUrl The baseUrl.
	 * @param isFirstArg Whether the baseUrl already has query string arguments
	 */
	public SimpleRequestBuilder(String baseUrl, boolean isFirstArg) {
		this.url = baseUrl;
		this.isFirstArg = isFirstArg;
	}

	
	/**
	 * Adds a property to the querysting of the URL
	 * 
	 */
	@Override
	public void add(String key, String value) {
		if(isFirstArg) {
			url += "?";
			isFirstArg = false;
		} else {
			url += "&";
		}
		
		url += key + "=" + value;
	}
	
	/**
	 * Clones the object
	 * 
	 */
	@Override
	public SimpleRequestBuilder instantiate() {
		return clone();
	}
	
	@Override
	public SimpleRequestBuilder clone() {
		return new SimpleRequestBuilder(url, this.isFirstArg);
	}
	
	@Override
	public String toString() {
		return url;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (isFirstArg ? 1231 : 1237);
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SimpleRequestBuilder other = (SimpleRequestBuilder) obj;
		if (isFirstArg != other.isFirstArg)
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}
}