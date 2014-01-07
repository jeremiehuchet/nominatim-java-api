package fr.dudie.nominatim.client;

import java.util.Locale;

import fr.dudie.nominatim.client.request.NominatimSearchRequest;
import fr.dudie.nominatim.client.request.paramhelper.PolygonFormat;
import fr.dudie.nominatim.model.BoundingBox;

/**
 * Nominatim client options.
 * 
 * @author Jeremie Huchet
 */
public class NominatimOptions {

    /** The preferred search bounds. */
    private BoundingBox viewBox;

    /** True to use strict bounds. */
    private Boolean bounded;

    /** Default polygon points format. */
    private PolygonFormat polygonFormat;

    /** Preferred localization for results. */
    private Locale acceptLanguage;

    /**
     * Gets the preferred search bounds.
     * 
     * @return the preferred search bounds
     */
    public BoundingBox getViewBox() {
        return viewBox;
    }

    /**
     * Sets the preferred search bounds.
     * 
     * @param searchBounds
     *            the preferred search bounds to set
     */
    public void setViewBox(final BoundingBox searchBounds) {
        this.viewBox = searchBounds;
    }

    /**
     * Gets whether or not the search is strictly bounded.
     * 
     * @return whether or not the search is strictly bounded
     */
    public boolean isBounded() {
        return bounded;
    }

    /**
     * Sets whether or not the search is strictly bounded.
     * 
     * @param bounded
     *            whether or not the search is strictly bounded
     */
    public void setBounded(boolean bounded) {
        this.bounded = bounded;
    }

    /**
     * Gets the default polygon points format.
     * 
     * @return the default polygon points format
     */
    public PolygonFormat getPolygonFormat() {
        return polygonFormat;
    }

    /**
     * Sets the default polygon points format.
     * 
     * @param format
     *            the default polygon points format to set
     */
    public void setPolygonFormat(final PolygonFormat format) {
        this.polygonFormat = format;
    }

    /**
     * Gets the preferred locale for results.
     * 
     * @return the preferred locale for results
     */
    public Locale getAcceptLanguage() {
        return acceptLanguage;
    }

    /**
     * Sets the preferred locale for results.
     * 
     * @param acceptLanguage
     *            the preferred locale for results
     */
    public void setAcceptLanguage(final Locale acceptLanguage) {
        this.acceptLanguage = acceptLanguage;
    }

    /**
     * Merge this default options to the given request.
     * 
     * @param request
     *            the request where the result is merged
     */
    void mergeTo(final NominatimSearchRequest request) {
        if (null == request.getBounded()) {
            request.setBounded(bounded);
        }
        if (null == request.getViewBox()) {
            request.setViewBox(viewBox);
        }
        if (null == request.getPolygonFormat()) {
            request.setPolygonFormat(polygonFormat);
        }
        if (null == request.getAcceptLanguage()) {
            request.setAcceptLanguage(acceptLanguage.toString());
        }
    }
}
