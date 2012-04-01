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
package fr.dudie.nominatim;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.SingleClientConnManager;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.dudie.nominatim.client.JsonNominatimClient;
import fr.dudie.nominatim.model.Address;

/**
 * Test class for {@link JsonNominatimClient}.
 * 
 * @author Jérémie Huchet
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

        final String email = PROPS.getProperty("nominatim.headerEmail");
        nominatimClient = new JsonNominatimClient(httpClient, email, null, false, true);
    }

    @Test
    public void testGetAddress() throws IOException {

        LOGGER.info("testGetAddress.start");

        final Address address = nominatimClient.getAddress(1.64891269513038, 48.1166561643464);
        LOGGER.debug(ToStringBuilder.reflectionToString(address, ToStringStyle.MULTI_LINE_STYLE));

        assertNotNull("a result should be found", address);
        assertTrue("address expose the OSM place_id", address.getPlaceId() > 0);
        assertNull("no polygonpoint", address.getPolygonPoints());

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
}
