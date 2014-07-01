package fr.dudie.nominatim.client.request.paramhelper;

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
