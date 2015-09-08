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
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import fr.dudie.nominatim.model.BoundingBox;

public class NominatimLookupRequestTest {

    private NominatimLookupRequest req;

    @Before
    public void reset() {
        req = new NominatimLookupRequest();
    }

    @Test
    public void simpleQuery() throws UnsupportedEncodingException {
		List<String> typeIds = new ArrayList<String>();
		typeIds.add("R146656");
		typeIds.add("W104393803");
		
        req.setQuery(typeIds);
        assertEquals("osm_ids=R146656,W104393803", req.getQueryString());
    }

    @Test
    public void simpleQueryWithAcceptLanguage() throws UnsupportedEncodingException {
    	List<String> typeIds = new ArrayList<String>();
		typeIds.add("R146656");
		typeIds.add("W104393803");
		
		req.setQuery(typeIds);
        req.setAcceptLanguage("fr_FR");
        assertEquals("accept-language=fr_FR&osm_ids=R146656,W104393803", req.getQueryString());
    }

    @Test
    public void simpleQueryWithAddress() throws UnsupportedEncodingException {
		List<String> typeIds = new ArrayList<String>();
		typeIds.add("R146656");
		typeIds.add("W104393803");
		
        req.setQuery(typeIds);
        req.setAddressDetails(true);
        assertEquals("osm_ids=R146656,W104393803&addressdetails=1", req.getQueryString());
    }

    @Test
    public void simpleQueryWithoutAddress() throws UnsupportedEncodingException {
		List<String> typeIds = new ArrayList<String>();
		typeIds.add("R146656");
		typeIds.add("W104393803");
		
        req.setQuery(typeIds);
        req.setAddressDetails(false);
        assertEquals("osm_ids=R146656,W104393803&addressdetails=0", req.getQueryString());
    }
}

