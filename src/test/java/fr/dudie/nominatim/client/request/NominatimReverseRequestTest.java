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
