package fr.dudie.nominatim.client.request;

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

import fr.dudie.nominatim.client.request.paramhelper.DoubleSerializer;
import fr.dudie.nominatim.client.request.paramhelper.QueryParameter;

/**
 * Holds coordinates of the reverse geocoding request.
 * <p>
 * Attributes documentation was extracted from <a href="http://wiki.openstreetmap.org/wiki/Nominatim">Nominatim Wiki</a>
 * page on October 26th, 2013.
 * 
 * @author Jeremie Huchet
 */
public class CoordinatesReverseQuery extends ReverseQuery {

    /** The latitude. */
    @QueryParameter(value = "lat=%s", serializer = DoubleSerializer.class)
    private Double latitude;

    /** The longitude. */
    @QueryParameter(value = "lon=%s", serializer = DoubleSerializer.class)
    private Double longitude;

    /**
     * @param longitude
     *            the longitude
     * @param latitude
     *            the latitude
     */
    public CoordinatesReverseQuery(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    /**
     * @param longitude
     *            the longitude
     * @param latitude
     *            the latitude
     */
    public CoordinatesReverseQuery(int longitudeE6, int latitudeE6) {
        this(longitudeE6 / 1E6, latitudeE6 / 1E6);
    }

    /**
     * @return the latitude
     */
    public Double getLatitude() {
        return latitude;
    }

    /**
     * @param latitude
     *            the latitude to set
     */
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    /**
     * @return the longitude
     */
    public Double getLongitude() {
        return longitude;
    }

    /**
     * @param longitude
     *            the longitude to set
     */
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
