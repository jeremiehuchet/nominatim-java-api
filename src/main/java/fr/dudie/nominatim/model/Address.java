/*
 * This program is free software: you can redistribute it and/or modify it under 
 * the terms of the GNU General Public License as published by the 
 * Free Software Foundation, either version 3 of the License, or 
 * (at your option) any later version. 
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; 
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
 * See the GNU General Public License for more details. 
 * 
 * You should have received a copy of the GNU General Public License along with this program. 
 * If not, see <http://www.gnu.org/licenses/>.
 */
package fr.dudie.nominatim.model;

/**
 * Represents an address.
 * 
 * <pre>
 * {
 * 
 *     place_id: "46467619"
 *     licence: "Data Copyright OpenStreetMap Contributors, Some Rights Reserved. CC-BY-SA 2.0."
 *     osm_type: "way"
 *     osm_id: "45247695"
 *     -
 *     boundingbox: [
 *         "48.4065589904785"
 *         "48.4071006774902"
 *         "-4.52232980728149"
 *         "-4.52062273025513"
 *     ]
 *     polygonpoints: [ ]
 *     lat: "48.40683015"
 *     lon: "-4.52147625"
 *     display_name: "Rue de Fougères, Bellevue, Brest, Finistère, France"
 *     class: "highway"
 *     type: "residential"
 *     -
 *     address: {
 *         road: "Rue de Fougères"
 *         suburb: "Bellevue"
 *         city: "Brest"
 *         county: "Finistère"
 *         country: "France"
 *         country_code: "fr"
 *     }
 * 
 * }
 * </pre>
 * 
 * @author Jérémie Huchet
 */
public class Address {

    /** The OpenStreetMap type. */
    private String osmType;

    /** The bounding box enclosing the element. */
    private BoundingBox boundingBox;

    /** The polygon points representing the element. */
    private PolygonPoint[] polygonpoints;

    /** The address longitude. */
    private double longitude;

    /** The address latitude. */
    private double latitude;

    /** The address display name. */
    private String displayName;

    /** The OpenStreetMap element class (ex: highway). */
    private String elementClass;

    /** The OpenStreetMap element type (ex: residential). */
    private String elementType;

    /** The elements describing the address (ex: road, city, coutry...). */
    private AddressElement[] addressElements;

    /**
     * Gets the osmType.
     * 
     * @return the osmType
     */
    public final String getOsmType() {

        return osmType;
    }

    /**
     * Sets the osmType.
     * 
     * @param osmType
     *            the osmType to set
     */
    public final void setOsmType(final String osmType) {

        this.osmType = osmType;
    }

    /**
     * Gets the boundingBox.
     * 
     * @return the boundingBox
     */
    public final BoundingBox getBoundingBox() {

        return boundingBox;
    }

    /**
     * Sets the boundingBox.
     * 
     * @param boundingBox
     *            the boundingBox to set
     */
    public final void setBoundingBox(final BoundingBox boundingBox) {

        this.boundingBox = boundingBox;
    }

    /**
     * Gets the polygonpoints.
     * 
     * @return the polygonpoints
     */
    public final PolygonPoint[] getPolygonpoints() {

        return polygonpoints;
    }

    /**
     * Sets the polygonpoints.
     * 
     * @param polygonpoints
     *            the polygonpoints to set
     */
    public final void setPolygonpoints(final PolygonPoint[] polygonpoints) {

        this.polygonpoints = polygonpoints;
    }

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

    /**
     * Gets the displayName.
     * 
     * @return the displayName
     */
    public final String getDisplayName() {

        return displayName;
    }

    /**
     * Sets the displayName.
     * 
     * @param displayName
     *            the displayName to set
     */
    public final void setDisplayName(final String displayName) {

        this.displayName = displayName;
    }

    /**
     * Gets the elementClass.
     * 
     * @return the elementClass
     */
    public final String getElementClass() {

        return elementClass;
    }

    /**
     * Sets the elementClass.
     * 
     * @param elementClass
     *            the elementClass to set
     */
    public final void setElementClass(final String elementClass) {

        this.elementClass = elementClass;
    }

    /**
     * Gets the elementType.
     * 
     * @return the elementType
     */
    public final String getElementType() {

        return elementType;
    }

    /**
     * Sets the elementType.
     * 
     * @param elementType
     *            the elementType to set
     */
    public final void setElementType(final String elementType) {

        this.elementType = elementType;
    }

    /**
     * Gets the addressElements.
     * 
     * @return the addressElements
     */
    public final AddressElement[] getAddressElements() {

        return addressElements;
    }

    /**
     * Sets the addressElements.
     * 
     * @param addressElements
     *            the addressElements to set
     */
    public final void setAddressElements(final AddressElement[] addressElements) {

        this.addressElements = addressElements;
    }

}
