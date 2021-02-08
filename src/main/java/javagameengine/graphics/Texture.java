package javagameengine.graphics;

import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;

/**
 * Essentially a sprite sheet.
 */
public class Texture {
    private int id;
    private int width;
    private int height;
    private int channels;
    private ByteBuffer imageData;

    private int wrapMode = GL_REPEAT; // Repeat by default
    private int resizeMode = GL_NEAREST; // Pixelate by default

    public Texture(int width, int height, int channels, ByteBuffer imageData) {
        this.width = width;
        this.height = height;
        this.channels = channels;
        this.imageData = imageData;
    }

    /**
     * Uploads the texture to the graphics card and stores an ID.
     */
    public void init() {
        id = glGenTextures();

        glBindTexture(GL_TEXTURE_2D, id);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, wrapMode);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, wrapMode);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, resizeMode);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, resizeMode);

        glTexImage2D(GL_TEXTURE_2D, 0, getGLMode(), width, height, 0, getGLMode(), GL_UNSIGNED_BYTE, imageData);
    }

    public void bind() {
        glBindTexture(GL_TEXTURE_2D, id);
    }

    public void unbind() {
        glBindTexture(GL_TEXTURE_2D, 0);
    }

    public int getId() {
        return id;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getChannels() {
        return channels;
    }

    public ByteBuffer getImageData() {
        return imageData;
    }

    public int getWrapMode() {
        return wrapMode;
    }

    /**
     * Must be set before init().
     * @param wrapMode
     */
    public void setWrapMode(int wrapMode) {
        this.wrapMode = wrapMode;
    }

    public int getResizeMode() {
        return resizeMode;
    }

    /**
     * Must be set before init().
     * @param resizeMode
     */
    public void setResizeMode(int resizeMode) {
        this.resizeMode = resizeMode;
    }

    private int getGLMode() {
        switch (channels) {
            case 4:
                return GL_RGBA;
            default:
                return GL_RGB;
        }
    }
}