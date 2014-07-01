package fr.dudie.nominatim.gson;

import java.lang.reflect.Type;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import fr.dudie.nominatim.model.PolygonPoint;

/**
 * Deserializes the attribute named "polygonpoints" of a response from the Nominatim API. It will
 * become an array of {@link PolygonPoint}s.
 * <p>
 * Sample "polygonpoints" attribute:
 * 
 * <pre>
 *     "polygonpoints": [
 *         "48.1190567016602",
 *         "48.1191635131836",
 *         "-1.6499342918396",
 *         "-1.64988231658936"
 *     ],
 * </pre>
 * 
 * @author Jérémie Huchet
 */
public final class ArrayOfPolygonPointsDeserializer implements JsonDeserializer<PolygonPoint[]> {

    /** The event logger. */
    private static final Logger LOGGER = LoggerFactory
            .getLogger(ArrayOfPolygonPointsDeserializer.class);

    /**
     * {@inheritDoc}
     * 
     * @see com.google.gson.JsonDeserializer#deserialize(com.google.gson.JsonElement,
     *      java.lang.reflect.Type, com.google.gson.JsonDeserializationContext)
     */
    @Override
    public PolygonPoint[] deserialize(final JsonElement json, final Type typeOfT,
            final JsonDeserializationContext context) {

        final PolygonPoint[] points;

        if (json.isJsonArray()) {

            final JsonArray pointsJsonArray = json.getAsJsonArray();
            points = new PolygonPoint[pointsJsonArray.size()];

            for (int i = 0; i < pointsJsonArray.size(); i++) {
                points[i] = context.deserialize(pointsJsonArray.get(i), PolygonPoint.class);
            }
        } else {
            throw new JsonParseException("Unexpected data: " + json.toString());
        }

        return points;
    }
}
