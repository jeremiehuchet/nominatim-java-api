package fr.dudie.nominatim.model;

/*
 * [license]
 * Nominatim Java API client
 * ~~~~
 * Copyright (C) 2010 - 2014 Dudie
 * ~~~~
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-3.0.html>.
 * [/license]
 */

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
