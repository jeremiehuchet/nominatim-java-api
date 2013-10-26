package fr.dudie.nominatim.client.request;

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
