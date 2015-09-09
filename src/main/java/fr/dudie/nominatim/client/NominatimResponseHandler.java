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

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ResponseHandler;

import com.google.gson.Gson;

/**
 * Parses a json response from the Nominatim API for a reverse geocoding request.
 * 
 * @author Jérémie Huchet
 */
public final class NominatimResponseHandler<T> implements ResponseHandler<T> {

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
            final StatusLine status = response.getStatusLine();
            if (status.getStatusCode() >= HttpStatus.SC_BAD_REQUEST) {
                throw new IOException(String.format("HTTP error: %s %s", status.getStatusCode(), status.getReasonPhrase()));
            }
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
