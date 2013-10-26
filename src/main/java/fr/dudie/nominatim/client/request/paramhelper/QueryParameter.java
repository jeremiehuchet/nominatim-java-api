package fr.dudie.nominatim.client.request.paramhelper;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Formatter;

/**
 * Declare a field as an URL query parameter.
 * 
 * @author Jeremie Huchet
 * @since 3.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface QueryParameter {

    /**
     * A format string (see {@link Formatter}) in whish the output of the serialization of the field value will be
     * merged.
     * <p>
     * Default is "<code>%s</code>"
     * 
     * @return the format string where to merge the field value
     */
    String value() default "%s";

    /**
     * Defines whether or not the output of the field value serialization should be URL encoded.
     * <p>
     * Default is true.
     * 
     * @return whether or not the output of the field value serialization should be URL encoded
     */
    boolean encode() default true;

    /**
     * Defines the serializer to use to convert the field value into a string value.
     * <p>
     * Default is {@link ToStringSerializer}.
     * 
     * @return the serializer class to use
     */
    Class<? extends QueryParameterSerializer> serializer() default ToStringSerializer.class;
}
