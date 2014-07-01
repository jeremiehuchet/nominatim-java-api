package fr.dudie.nominatim.client.request;

import static org.junit.Assert.*;

import java.io.UnsupportedEncodingException;

import org.junit.Before;
import org.junit.Test;

import fr.dudie.nominatim.model.BoundingBox;

public class NominatimSearchRequestTest {

    private NominatimSearchRequest req;

    @Before
    public void reset() {
        req = new NominatimSearchRequest();
    }

    @Test
    public void simpleQuery() throws UnsupportedEncodingException {
        req.setQuery("vitré, france");
        assertEquals("q=vitré,%20france", req.getQueryString());
    }

    @Test
    public void simpleQueryWithAcceptLanguage() throws UnsupportedEncodingException {
        req.setQuery("france");
        req.setAcceptLanguage("fr_FR");
        assertEquals("q=france&accept-language=fr_FR", req.getQueryString());
    }

    @Test
    public void simpleQueryWithAddress() throws UnsupportedEncodingException {
        req.setQuery("france");
        req.setAddress(true);
        assertEquals("q=france&addressdetails=1", req.getQueryString());
    }

    @Test
    public void simpleQueryWithoutAddress() throws UnsupportedEncodingException {
        req.setQuery("france");
        req.setAddress(false);
        assertEquals("q=france&addressdetails=0", req.getQueryString());
    }

    @Test
    public void simpleQueryWithCountryCodes() throws UnsupportedEncodingException {
        req.setQuery("france");

        req.addCountryCode("FR");
        assertEquals("q=france&countrycodes=FR", req.getQueryString());

        req.addCountryCode("BE");
        assertEquals("q=france&countrycodes=FR,BE", req.getQueryString());
    }

    @Test
    public void simpleQueryWithViewBox() throws UnsupportedEncodingException {
        final double w = -1.14465546607971;
        final double n = 48.1462173461914;
        final double e = -1.24950230121613;
        final double s = 48.0747871398926;
        final String expectedQueryString   = "q=france&viewbox=-1.14465546607971,48.14621734619140,-1.24950230121613,48.07478713989260";
        final String expectedQueryStringE6 = "q=france&viewbox=-1.14465500000000,48.14621700000000,-1.24950200000000,48.07478700000000";

        req.setQuery("france");
        final BoundingBox bbox = new BoundingBox();
        bbox.setWest(w);
        bbox.setNorth(n);
        bbox.setEast(e);
        bbox.setSouth(s);
        req.setViewBox(bbox);
        assertEquals(expectedQueryString, req.getQueryString());

        req.setViewBox(w, n, e, s);
        assertEquals(expectedQueryString, req.getQueryString());

        req.setViewBox((int) (w * 1E6), (int) (n * 1E6), (int) (e * 1E6), (int) (s * 1E6));
        assertEquals(expectedQueryStringE6, req.getQueryString());
    }
}
