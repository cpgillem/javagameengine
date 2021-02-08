package javagameengine.graphics;

import org.joml.Vector4f;

import java.awt.*;

/**
 * A sprite is a location and size within a texture.
 */
public class Sprite {
    private Texture texture;
    private Vector4f color = new Vector4f(1, 1, 1, 1);

    private float u = 0;
    private float v = 0;
    private float width = 1;
    private float height = 1;

    public Sprite(Texture texture) {
        this.texture = texture;
    }

    public Sprite(Texture texture, float u, float v, float width, float height) {
        this(texture);

        this.u = u;
        this.v = v;
        this.width = width;
        this.height = height;
    }

    public Sprite(Texture texture, int x, int y, int width, int height) {
        this(texture,
                (float)x / texture.getWidth(),
                (float)y / texture.getHeight(),
                (float)width / texture.getWidth(),
                (float) height / texture.getHeight());
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public Vector4f getColor() {
        return color;
    }

    public void setColor(Vector4f color) {
        this.color = color;
    }

    public float getU() {
        return u;
    }

    public float getU2() {
        return u + width;
    }

    public void setU(float u) {
        this.u = u;
    }

    public float getV() {
        return v;
    }
    public float getV2() {
        return v + height;
    }

    public void setV(float v) {
        this.v = v;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    private float xToU(int x) {
        return x / texture.getWidth();
    }

    private float yToV(int y) {
        return y / texture.getHeight();
    }
}
