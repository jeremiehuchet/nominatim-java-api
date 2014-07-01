package fr.dudie.nominatim.client.request.paramhelper;

/**
 * Enumerates OSM POLYGON FORMAT possible parameters values.
 * 
 * @author Jeremie Huchet
 */
public enum PolygonFormat {

    /** No polygons. */
    NONE,

    /** Output geometry of results in geojson format. */
    GEO_JSON("polygon_geojson=1"),

    /** Output geometry of results in kml format. */
    KML("polygon_kml=1"),

    /** Output geometry of results in svg format. */
    SVG("polygon_svg=1"),

    /** Output geometry of results as a WKT. */
    TEXT("polygon_text=1");

    /** The parameter name AND its value. */
    private final String param;

    private PolygonFormat() {
        param = null;
    }

    private PolygonFormat(final String param) {
        this.param = param;
    }

    @Override
    public String toString() {
        return param;
    }
}
