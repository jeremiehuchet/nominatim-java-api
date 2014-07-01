package fr.dudie.nominatim.client.request;

import fr.dudie.nominatim.client.request.paramhelper.QueryParameter;

/**
 * Holds parameters for an extended search query.
 * <p>
 * <b>This is labeled as experimental on Nominatim Wiki page</b>
 * <p>
 * Attributes documentation was extracted from <a href="http://wiki.openstreetmap.org/wiki/Nominatim">Nominatim Wiki</a>
 * page on October 26th, 2013.
 * @author Jeremie Huchet
 */
public class ExtendedSearchQuery extends SearchQuery {

    /** A street name. */
    @QueryParameter("street=%s")
    private String street;

    /** A city name. */
    @QueryParameter("city=%s")
    private String city;

    /** A county name. */
    @QueryParameter("county=%s")
    private String county;

    /** A state name. */
    @QueryParameter("state=%s")
    private String state;

    /** A country name. */
    @QueryParameter("country=%s")
    private String country;

    /** A postal code. */
    @QueryParameter("postal_code=%s")
    private String postalCode;

    /**
     * @return the street
     */
    public String getStreet() {
        return street;
    }

    /**
     * @param street
     *            the street to set
     */
    public void setStreet(String street) {
        this.street = street;
    }

    /**
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city
     *            the city to set
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * @return the county
     */
    public String getCounty() {
        return county;
    }

    /**
     * @param county
     *            the county to set
     */
    public void setCounty(String county) {
        this.county = county;
    }

    /**
     * @return the state
     */
    public String getState() {
        return state;
    }

    /**
     * @param state
     *            the state to set
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * @return the country
     */
    public String getCountry() {
        return country;
    }

    /**
     * @param country
     *            the country to set
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * @return the postalCode
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * @param postalCode
     *            the postalCode to set
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

}
