package fr.dudie.nominatim.model;

/**
 * Represents a geographical location.
 * 
 * @author Jérémie Huchet
 */
public class PolygonPoint {

    /** The point's longitude. */
    private double longitude;

    /** The point's latitude. */
    private double latitude;

    /**
     * Gets the longitude.
     * 
     * @return the longitude
     */
    public final double getLongitude() {

        return longitude;
    }

    /**
     * Gets the longitude.
     * 
     * @return the longitude
     */
    public final int getLongitudeE6() {

        return (int) (longitude * 1E6);
    }

    /**
     * Sets the longitude.
     * 
     * @param longitude
     *            the longitude to set
     */
    public final void setLongitude(final double longitude) {

        this.longitude = longitude;
    }

    /**
     * Sets the longitude.
     * 
     * @param longitude
     *            the longitude to set
     */
    public final void setLongitudeE6(final int longitude) {

        this.longitude = longitude / 1E6;
    }

    /**
     * Gets the latitude.
     * 
     * @return the latitude
     */
    public final double getLatitude() {

        return latitude;
    }

    /**
     * Gets the latitude.
     * 
     * @return the latitude
     */
    public final int getLatitudeE6() {

        return (int) (latitude * 1E6);
    }

    /**
     * Sets the latitude.
     * 
     * @param latitude
     *            the latitude to set
     */
    public final void setLatitude(final double latitude) {

        this.latitude = latitude;
    }

    /**
     * Sets the latitude.
     * 
     * @param latitude
     *            the latitude to set
     */
    public final void setLatitudeE6(final int latitude) {

        this.latitude = latitude / 1E6;
    }
}
