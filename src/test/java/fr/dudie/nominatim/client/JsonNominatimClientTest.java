package fr.dudie.nominatim.client;

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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.SingleClientConnManager;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vividsolutions.jts.geom.Geometry;

import fr.dudie.nominatim.client.request.NominatimLookupRequest;
import fr.dudie.nominatim.client.request.NominatimSearchRequest;
import fr.dudie.nominatim.client.request.paramhelper.PolygonFormat;
import fr.dudie.nominatim.model.Address;

/**
 * Test class for {@link JsonNominatimClient}.
 * 
 * @author Jérémie Huchet
 * @author Sunil D S
 */
public final class JsonNominatimClientTest {

    /** The event logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonNominatimClientTest.class);

    /** The tested Nominatim client. */
    private static JsonNominatimClient nominatimClient;

    /** Path to the properties file. */
    private static final String PROPS_PATH = "/nominatim-client-test.properties";

    /** Loaded properties. */
    private static final Properties PROPS = new Properties();

    /**
     * Instantiates the test.
     * 
     * @throws IOException
     *             an error occurred during initialization
     */
    public JsonNominatimClientTest() throws IOException {

        LOGGER.info("Loading configuration file {}", PROPS_PATH);
        final InputStream in = JsonNominatimClientTest.class.getResourceAsStream(PROPS_PATH);
        PROPS.load(in);

        LOGGER.info("Preparing http client");
        final SchemeRegistry registry = new SchemeRegistry();
        registry.register(new Scheme("http", new PlainSocketFactory(), 80));
        final ClientConnectionManager connexionManager = new SingleClientConnManager(null, registry);

        final HttpClient httpClient = new DefaultHttpClient(connexionManager, null);

        final String baseUrl = PROPS.getProperty("nominatim.server.url");
        final String email = PROPS.getProperty("nominatim.headerEmail");
        nominatimClient = new JsonNominatimClient(baseUrl, httpClient, email);
    }

    @Test
    public void testGetAddress() throws IOException {

        LOGGER.info("testGetAddress.start");

        final Address address = nominatimClient.getAddress(1.64891269513038, 48.1166561643464);
        LOGGER.debug(ToStringBuilder.reflectionToString(address, ToStringStyle.MULTI_LINE_STYLE));

        assertNotNull("a result should be found", address);
        assertTrue("address expose the OSM place_id", address.getPlaceId() > 0);
        assertNull("no polygonpoint", address.getPolygonPoints());
        assertNull("no geojson", address.getGeojson());

        LOGGER.info("testGetAddress.end");
    }

    @Test
    public void testSearchWithResults() throws IOException {

        LOGGER.info("testSearchWithResults.start");

        final List<Address> addresses = nominatimClient.search("vitré, rennes");

        assertNotNull("result list is never null", addresses);
        for (final Address address : addresses) {
            LOGGER.debug(ToStringBuilder
                    .reflectionToString(address, ToStringStyle.MULTI_LINE_STYLE));
        }
        assertTrue("list is not empty", !addresses.isEmpty());

        LOGGER.info("testSearchWithResults.end");
    }

    @Test
    public void testSearchWithGeoJsonResults() throws IOException {

        LOGGER.info("testSearchWithGeoJsonResults.start");

        NominatimSearchRequest request = new NominatimSearchRequest();
        request.setPolygonFormat(PolygonFormat.GEO_JSON);
        request.setQuery("vitré, rennes");

        final List<Address> addresses = nominatimClient.search(request);

        assertNotNull("result list is never null", addresses);
        assertTrue("list is not empty", !addresses.isEmpty());

        for (final Address address : addresses) {
            final Geometry geom = address.getGeojson();
            assertNotNull("geometry/geojson of address is available in result", geom);
            assertTrue("geometry/geojson of address has at least one vertex", geom.getNumPoints() > 0);
        }

        LOGGER.info("testSearchWithGeoJsonResults.end");
    }

    @Test
    public void testSearchWithoutResults() throws IOException {

        LOGGER.info("testSearchWithoutResults.start");

        final List<Address> addresses = nominatimClient
                .search("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaabbbbbbbbbbbbbbbbb");

        assertNotNull("result list is never null", addresses);
        assertTrue("list is empty", addresses.isEmpty());

        LOGGER.info("testSearchWithoutResults.end");
    }

    @Test
    public void testSearchWithForLongPlaceId() throws IOException {

        LOGGER.info("testSearchWithResults.start");

        final List<Address> addresses = nominatimClient.search("ул. Евдокимова,37, Ростов-на-дону");

        assertNotNull("result list is not null", addresses);
        for (final Address address : addresses) {
            LOGGER.debug(ToStringBuilder
                    .reflectionToString(address, ToStringStyle.MULTI_LINE_STYLE));
        }
        assertTrue("list is not empty", !addresses.isEmpty());

        LOGGER.info("testSearchWithResults.end");
    }

    @Test
    @Ignore
    public void testReverseLookUpZoomLevelCanBeControlled() throws Exception {

        LOGGER.info("testReverseLookUpZoomLevelCanBeControlled.start");

        final Address highLevelAddress = nominatimClient.getAddress(-0.32, 51.44, 1);
        assertEquals("European Union", highLevelAddress.getDisplayName());

        final Address lowerLevelAddress = nominatimClient.getAddress(-0.32, 51.44, 10);
        assertTrue(lowerLevelAddress.getPlaceId() != highLevelAddress.getPlaceId());

        LOGGER.info("testReverseLookUpZoomLevelCanBeControlled.end");

    }

    @Test
    @Ignore
    public void testReverseLookUpTypeOsmId() throws Exception {

        LOGGER.info("testReverseLookUpTypeOsmId");

        final Address address = nominatimClient.getAddress("W", 26932726);
        assertEquals(
                "Eel Pie Island, Thames Path, Ham, London Borough of Richmond upon Thames, London, Greater London, England, TW1 3DT, United Kingdom, European Union",
                address.getDisplayName());
        assertEquals("way", address.getOsmType());
        assertEquals("26932726", address.getOsmId());

        LOGGER.info("testReverseLookUpTypeOsmId");
    }

    @Test
    public void testAddressWithDetails() throws IOException {

        LOGGER.info("testAddressWithDetails");

        final NominatimSearchRequest r = new NominatimSearchRequest();
        r.setQuery("rennes, france");
        r.setAddress(true);
        final List<Address> addresses = nominatimClient.search(r);

        assertNotNull("result list is not null", addresses);
        assertTrue("there is more than one result", addresses.size() > 0);
        for (final Address address : addresses) {
            assertNotNull("address details are available in result", address.getAddressElements());
            assertTrue("at least one address detail is available", address.getAddressElements().length > 0);
        }

        LOGGER.info("testAddressWithDetails");
    }

    @Test
    public void testAddressWithNameDetails() throws IOException {

        LOGGER.info("testAddressWithDetails");

        final NominatimSearchRequest r = new NominatimSearchRequest();
        r.setQuery("pkin");
        r.setName(true);
        final List<Address> addresses = nominatimClient.search(r);

        assertNotNull("result list is not null", addresses);
        assertTrue("there is more than one result", addresses.size() > 0);
        for (final Address address : addresses) {
            assertNotNull("address details are available in result", address.getNameDetails());
            assertTrue("at least one address detail is available", address.getNameDetails().length > 0);
        }

        LOGGER.info("testAddressWithDetails");
    }

    @Test
    public void testAddressWithoutDetails() throws IOException {

        LOGGER.info("testAddressWithoutDetails");

        final NominatimSearchRequest r = new NominatimSearchRequest();
        r.setQuery("rennes, france");
        r.setAddress(false);
        final List<Address> addresses = nominatimClient.search(r);

        assertNotNull("result list is not null", addresses);
        assertTrue("there is more than one result", addresses.size() > 0);
        for (final Address address : addresses) {
            assertNull("address details are not available in result", address.getAddressElements());
        }

        LOGGER.info("testAddressWithoutDetails");
    }

    @Test
    public void testAddressLookupWithDetails() throws IOException {

        LOGGER.info("testAddressLookupWithDetails");

        final NominatimLookupRequest r = new NominatimLookupRequest();

        List<String> typeIds = new ArrayList<String>();
        typeIds.add("R146656");
        typeIds.add("W104393803");
        typeIds.add("N240109189");

        r.setQuery(typeIds);
        r.setAddressDetails(true);
        final List<Address> addresses = nominatimClient.lookupAddress(r);

        assertNotNull("result list is not null", addresses);
        assertTrue("there is more than one result", addresses.size() > 0);
        for (final Address address : addresses) {
            assertNotNull("address details are available in result", address.getAddressElements());
            assertTrue("at least one address detail is available", address.getAddressElements().length > 0);
        }

        LOGGER.info("testAddressLookupWithDetails");
    }
    
    @Test
    public void testAddressLookupWithoutDetails() throws IOException {

        LOGGER.info("testAddressLookupWithDetails");

        final NominatimLookupRequest r = new NominatimLookupRequest();
        
        List<String> typeIds = new ArrayList<String>();
        typeIds.add("R146656");
        typeIds.add("W104393803");
        typeIds.add("N240109189");

        r.setQuery(typeIds);
        r.setAddressDetails(false);
        final List<Address> addresses = nominatimClient.lookupAddress(r);

        assertNotNull("result list is not null", addresses);
        assertTrue("there is more than one result", addresses.size() > 0);
        for (final Address address : addresses) {
            assertNull("address details are not available in result", address.getAddressElements());
        }

        LOGGER.info("testAddressLookupWithoutDetails");
    }

    @Test
    public void testBuildingLevelPlaceRank() throws IOException {
        final NominatimSearchRequest r = new NominatimSearchRequest();
        r.setQuery("2 rue de chateaudun, rennes, france");

        final List<Address> addresses = nominatimClient.search(r);
        assertEquals("there is one result", 1, addresses.size());
        final Address result = addresses.get(0);
        assertEquals("building level place rank", 30, result.getPlaceRank());
    }

    @Test
    public void testStreetLevelPlaceRank() throws IOException {
        final NominatimSearchRequest r = new NominatimSearchRequest();
        r.setQuery("rue de chateaudun, rennes, france");

        final List<Address> addresses = nominatimClient.search(r);
        assertEquals("there is one result", 1, addresses.size());
        final Address result = addresses.get(0);
        assertEquals("streen level place rank", 26, result.getPlaceRank());
    }

}
