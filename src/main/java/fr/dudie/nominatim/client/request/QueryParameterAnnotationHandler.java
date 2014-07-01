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

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.dudie.nominatim.client.request.paramhelper.QueryParameter;
import fr.dudie.nominatim.client.request.paramhelper.ToStringSerializer;

/**
 * Parse a bean definition wit {@link QueryParameter} annotations and generates a query string.
 * 
 * <pre>
 * public class Query {
 *     &#064;QueryParameter(&quot;paramName=%s&quot;)
 *     private String param = &quot;value with space&quot;;
 * 
 *     &#064;QueryParameter(&quot;secondParamName=%d&quot;)
 *     private Integer param2 = 10;
 * }
 * </pre>
 * 
 * will generate the following query string :
 * 
 * <pre>
 * paramName=value+with+space&param2=10
 * </pre>
 * 
 * @author Jeremie Huchet
 */
class QueryParameterAnnotationHandler {

    /** The event logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(QueryParameterAnnotationHandler.class);

    /**
     * Process an object to generate a query string.
     * <ul>
     * <li>only {@link QueryParameter} annotated fields are used</li>
     * <li>null or empty values are ignored</li>
     * </ul>
     * 
     * @param o
     *            the object to scan for {@link QueryParameter} annotations
     * @return the generated query string
     * @throws UnsupportedEncodingException
     */
    static String process(final Object o) {

        final StringBuilder s = new StringBuilder();

        for (final Field f : o.getClass().getDeclaredFields()) {

            final QueryParameter paramMetadata = f.getAnnotation(QueryParameter.class);
            final Object fieldValue = getValue(o, f);

            // each field having the QueryParameter annotation is processed
            if (null != fieldValue && null != paramMetadata) {

                final String paramFormat = paramMetadata.value();
                String paramValue = serialize(paramMetadata, fieldValue, f.getName());

                if (null != paramValue && !"".equals(paramValue.trim())) {
                    if (s.length() > 0) {
                        s.append('&');
                    }
                    if (paramMetadata.encode()) {
                        paramValue = uriEncode(paramValue);
                    }
                    s.append(String.format(Locale.US, paramFormat, paramValue));
                }
            }
        }
        return s.toString();
    }

    private static String uriEncode(String paramValue) {
        try {
            return new URI(null, null, null, paramValue, null).getRawQuery();
        } catch (final URISyntaxException e) {
            LOGGER.error("Failure encoding query parameter value {}", new Object[] { paramValue, e });
            return paramValue;
        }
    }

    /**
     * Serializes the field value regardless of reflection errors. Fallback to the {@link ToStringSerializer}.
     * 
     * @param paramMetadata
     *            the query parameter annotation
     * @param fieldValue
     *            the field value
     * @param fieldName
     *            the field name (for logging purposes only)
     * @return the serialized value
     * @throws UnsupportedEncodingException
     *             UTF-8 encoding issue
     */
    private static String serialize(final QueryParameter paramMetadata, final Object fieldValue, final String fieldName) {
        String paramValue;
        try {
            paramValue = paramMetadata.serializer().newInstance().handle(fieldValue);
        } catch (final InstantiationException e) {
            LOGGER.error("Failure while serializing field {}", new Object[] { fieldName, e });
            paramValue = new ToStringSerializer().handle(fieldValue);
        } catch (final IllegalAccessException e) {
            LOGGER.error("Failure while serializing field {}", new Object[] { fieldName, e });
            paramValue = new ToStringSerializer().handle(fieldValue);
        }
        return paramValue;
    }

    /**
     * Gets a field value regardless of reflection errors.
     * 
     * @param o
     *            the object instance
     * @param f
     *            the field
     * @return the field value or null if a reflection error occurred
     */
    private static Object getValue(final Object o, final Field f) {
        try {
            f.setAccessible(true);
            return f.get(o);
        } catch (final IllegalArgumentException e) {
            LOGGER.error("failure accessing field value {}", new Object[] { f.getName(), e });
        } catch (final IllegalAccessException e) {
            LOGGER.error("failure accessing field value {}", new Object[] { f.getName(), e });
        }
        return null;
    }

}
