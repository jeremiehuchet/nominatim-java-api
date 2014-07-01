package fr.dudie.nominatim.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;

import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

/**
 * Parses a json response from the Nominatim API for a reverse geocoding request.
 * 
 * @author Jérémie Huchet
 */
public final class NominatimResponseHandler<T> implements ResponseHandler<T> {

    /** The event logger. */
    private static final Logger LOGGER = LoggerFactory
            .getLogger(NominatimResponseHandler.class);

    /** Gson instance for Nominatim API calls. */
    private final Gson gsonInstance;

    /** The expected type of objects. */
    private final Type responseType;

    /**
     * Constructor.
     * 
     * @param gsonInstance
     *            the Gson instance
     * @param responseType
     *            the expected type of objects
     */
    public NominatimResponseHandler(final Gson gsonInstance, final Type responseType) {

        this.gsonInstance = gsonInstance;
        this.responseType = responseType;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.apache.http.client.ResponseHandler#handleResponse(org.apache.http.HttpResponse)
     */
    @Override
    public T handleResponse(final HttpResponse response) throws IOException {

        InputStream content = null;
        final T addresses;

        try {
            content = response.getEntity().getContent();
            addresses = gsonInstance
                    .fromJson(new InputStreamReader(content, "utf-8"), responseType);
        } finally {
            if (null != content) {
                content.close();
            }
            response.getEntity().consumeContent();
        }

        return addresses;
    }
}
