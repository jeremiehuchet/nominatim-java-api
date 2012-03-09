package fr.dudie.nominatim.gson;

import java.lang.reflect.Type;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import fr.dudie.nominatim.model.BoundingBox;

/**
 * Deserializes the attribute named "boundingbox" of a response from the Nominatim API. It will
 * become an {@link BoundingBox}.
 * <p>
 * Sample "boundingbox" attribute:
 * 
 * <pre>
 *     "boundingbox": [
 *         "48.1190567016602",
 *         "48.1191635131836",
 *         "-1.6499342918396",
 *         "-1.64988231658936"
 *     ],
 * </pre>
 * 
 * @author Jérémie Huchet
 */
public final class BoundingBoxDeserializer implements JsonDeserializer<BoundingBox> {

    /** The event logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(BoundingBoxDeserializer.class);

    /**
     * {@inheritDoc}
     * 
     * @see com.google.gson.JsonDeserializer#deserialize(com.google.gson.JsonElement,
     *      java.lang.reflect.Type, com.google.gson.JsonDeserializationContext)
     */
    @Override
    public BoundingBox deserialize(final JsonElement json, final Type typeOfT,
            final JsonDeserializationContext context) {

        final BoundingBox bbox;

        if (json.isJsonArray()) {
            final JsonArray bboxJsonArray = json.getAsJsonArray();
            bbox = new BoundingBox();
            bbox.setEast(bboxJsonArray.get(0).getAsDouble());
            bbox.setWest(bboxJsonArray.get(1).getAsDouble());
            bbox.setNorth(bboxJsonArray.get(2).getAsDouble());
            bbox.setSouth(bboxJsonArray.get(3).getAsDouble());
        } else {
            throw new JsonParseException("Unexpected data: " + json.toString());
        }

        return bbox;
    }
}
