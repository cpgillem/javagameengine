package javagameengine.util;

import javagameengine.components.StaticModel;
import javagameengine.graphics.Sprite;
import org.joml.Vector4f;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**
 * Provides static methods to create simple shapes. Functions will output vertex arrays and element/index arrays for creating VBOs.
 */
public class ModelFactory {
    /**
     * @param sprite Section of a texture.
     */
    public static void rectangle(Sprite sprite, float width, float height, FloatBuffer vertices, IntBuffer indices) {
        Vector4f color = sprite.getColor();

        float[] vs = new float[] {
                // BOTTOM RIGHT
                width, 0, 0,
                color.x, color.y, color.z, color.w,
                sprite.getU2(), sprite.getV2(),

                // TOP LEFT
                0, height, 0,
                color.x, color.y, color.z, color.w,
                sprite.getU(), sprite.getV(),

                // TOP RIGHT
                width, height, 0,
                color.x, color.y, color.z, color.w,
                sprite.getU2(), sprite.getV(),

                // BOTTOM LEFT
                0, 0, 0,
                color.x, color.y, color.z, color.w,
                sprite.getU(), sprite.getV2(),
        };

        int[] is = new int[] {2, 1, 0, 0, 1, 3};

        vertices.put(vs);
        indices.put(is);
    }
}
