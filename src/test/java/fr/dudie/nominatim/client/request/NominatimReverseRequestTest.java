package fr.dudie.nominatim.client.request;

import static org.junit.Assert.*;

import java.io.UnsupportedEncodingException;

import org.junit.Before;
import org.junit.Test;

import fr.dudie.nominatim.client.request.paramhelper.OsmType;

public class NominatimReverseRequestTest {

    private NominatimReverseRequest req;

    @Before
    public void reset() {
        req = new NominatimReverseRequest();
    }

    @Test
    public void simpleCoordQuery() throws UnsupportedEncodingException {
        req.setQuery(-1.14465546607971, 48.1462173461914);
        assertEquals("lat=48.14621734619140&lon=-1.14465546607971", req.getQueryString());
    }

    @Test
    public void simpleOsmTypeAndIdQuery() throws UnsupportedEncodingException {
        req.setQuery(OsmType.NODE, 12345);
        assertEquals("osm_type=N&osm_id=12345", req.getQueryString());
    }

    @Test
    public void simpleCoordQueryWithAcceptLanguage() throws UnsupportedEncodingException {
        req.setQuery(-1.14465546607971, 48.1462173461914);
        req.setAcceptLanguage("fr_FR");
        assertEquals("accept-language=fr_FR&lat=48.14621734619140&lon=-1.14465546607971", req.getQueryString());
    }

    @Test
    public void simpleCoordQueryWithAddressDetails() throws UnsupportedEncodingException {
        req.setQuery(-1.14465546607971, 48.1462173461914);
        req.setAddressDetails(true);
        assertEquals("lat=48.14621734619140&lon=-1.14465546607971&addressdetails=1", req.getQueryString());
    }

    @Test
    public void simpleCoordQueryWithoutAddressDetails() throws UnsupportedEncodingException {
        req.setQuery(-1.14465546607971, 48.1462173461914);
        req.setAddressDetails(false);
        assertEquals("lat=48.14621734619140&lon=-1.14465546607971&addressdetails=0", req.getQueryString());
    }

    @Test
    public void simpleCoordQueryWithZoom() throws UnsupportedEncodingException {
        req.setQuery(-1.14465546607971, 48.1462173461914);
        req.setZoom(15);
        assertEquals("lat=48.14621734619140&lon=-1.14465546607971&zoom=15", req.getQueryString());
    }
}
