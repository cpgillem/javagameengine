package javagameengine.graphics;

import javagameengine.exceptions.GLException;

import static org.lwjgl.opengl.GL20.*;

/**
 * Represents a vertex, fragment, geometry, etc. shader.
 */
public class Shader {
    private int id = -1;
    private int type;
    private String source;

    public Shader(int type, String source) {
        this.type = type;
        this.source = source;
    }

    /**
     * Compiles and links the shader and stores the ID from OpenGL in "id."
     */
    public void compile() throws GLException {
        int success = GL_TRUE;

        id = glCreateShader(type);
        glShaderSource(id, source);
        glCompileShader(id);

        // Check the status.
        success = glGetShaderi(id, GL_COMPILE_STATUS);
        if (success == GL_FALSE) {
            int len = glGetShaderi(id, GL_INFO_LOG_LENGTH);
            throw new GLException("Could not compile shader.", glGetShaderInfoLog(id, len));
        }
    }

    public int getId() {
        return id;
    }

    public int getType() {
        return type;
    }

    public String getSource() {
        return source;
    }
}
