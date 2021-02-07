package javagameengine.graphics;

import javagameengine.exceptions.GLException;
import org.joml.*;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL20.*;

public class ShaderProgram {
    private static final String MAT4_NAME = Matrix4f.class.getName();

    private Shader vertex, fragment;
    private int id = -1;
    private boolean inUse =  false;

    public ShaderProgram(Shader vertex, Shader fragment) {
        this.vertex = vertex;
        this.fragment = fragment;
    }

    /**
     * Links the shaders and stores the new program ID. The shaders must both be compiled.
     * This will always create a new shader program and update the ID.
     */
    public void link() throws GLException {
        int success = GL_TRUE;

        id = glCreateProgram();

        glAttachShader(id, vertex.getId());
        glAttachShader(id, fragment.getId());

        glLinkProgram(id);

        // Check status.
        success = glGetProgrami(id, GL_LINK_STATUS);
        if (success == GL_FALSE) {
            int len = glGetProgrami(id, GL_INFO_LOG_LENGTH);
            throw new GLException("Could not link shader programs.", glGetProgramInfoLog(id, len));
        }
    }

    public void bind() {
        if (!inUse) {
            glUseProgram(id);
            inUse = true;
        }
    }

    public void unbind() {
        glUseProgram(0);
        inUse = false;
    }

    public <T> void uploadUniformVariable(String name, T value) {
        int location = glGetUniformLocation(id, name);
        bind();

        if (value instanceof Matrix4f) uploadMat4f(location, (Matrix4f)value);
        else if (value instanceof Matrix3f) uploadMat3f(location, (Matrix3f)value);
        else if (value instanceof Vector4f) uploadVec4f(location, (Vector4f) value);
        else if (value instanceof Vector3f) uploadVec3f(location, (Vector3f) value);
        else if (value instanceof Vector2f) uploadVec2f(location, (Vector2f) value);
        else if (value instanceof Float) uploadFloat(location, (float)value);
        else if (value instanceof Integer) uploadInt (location, (int)value);
        else throw new IllegalStateException("Unexpected type: " + value.getClass().getName());
    }

    private void uploadMat4f(int location, Matrix4f value) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
        ((Matrix4f)value).get(buffer);
        glUniformMatrix4fv(location, false, buffer);
    }

    private void uploadMat3f(int location, Matrix3f value) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(9);
        ((Matrix3f)value).get(buffer);
        glUniformMatrix3fv(location, false, buffer);
    }

    private void uploadVec4f(int location, Vector4f value) {
        glUniform4f(location, value.x, value.y, value.z, value.w);
    }

    private void uploadVec3f(int location, Vector3f value) {
        glUniform3f(location, value.x, value.y, value.z);
    }

    private void uploadVec2f(int location, Vector2f value ) {
        glUniform2f(location, value.x, value.y);
    }

    private void uploadFloat(int location, float value) {
        glUniform1f(location, value);
    }

    private void uploadInt(int location, int value) {
        glUniform1i(location, value);
    }

    public Shader getVertex() {
        return vertex;
    }

    public Shader getFragment() {
        return fragment;
    }

    public int getId() {
        return id;
    }

    public boolean isInUse() {
        return inUse;
    }
}
