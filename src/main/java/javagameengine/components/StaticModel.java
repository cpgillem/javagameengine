package javagameengine.components;

import javagameengine.core.Camera;
import javagameengine.core.Component;
import javagameengine.core.Entity;
import javagameengine.graphics.ShaderProgram;
import javagameengine.graphics.Texture;
import javagameengine.util.Time;
import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

/**
 * Represents the shape, in 3D space, of an entity. Stores data for a VBO in OpenGL. Requires a shader program to render.
 */
public class StaticModel extends Component {
    private static final int LOCATION_SIZE = 3 * Float.BYTES;
    private static final int COLOR_SIZE = 4 * Float.BYTES;
    private static final int TEX_COORD_SIZE = 2 * Float.BYTES;

    private static final int ATTRIB_LOCATION = 0;
    private static final int ATTRIB_COLOR = 1;
    private static final int ATTRIB_TEX_COORDS = 2;

    private static final int PTR_LOCATION = 0;
    private static final int PTR_COLOR = LOCATION_SIZE;
    private static final int PTR_TEX_COORDS = LOCATION_SIZE + COLOR_SIZE;

    private ShaderProgram program;
    private Camera camera;
    private Texture texture;
    private float[] vertices;
    private int[] elements;
    private int vertexArrayObject = -1;
    private int vertexBufferObject = -1;
    private int elementBufferObject = -1;

    public StaticModel(Entity entity, ShaderProgram program, Camera camera, Texture texture, float[] vertices, int[] elements) {
        super(entity);

        this.program = program;
        this.camera = camera;
        this.texture = texture;
        this.vertices = vertices;
        this.elements = elements;
    }

    public void init() {
        int vertSize = LOCATION_SIZE + COLOR_SIZE + TEX_COORD_SIZE;
        FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(vertices.length);
        IntBuffer elementBuffer = BufferUtils.createIntBuffer(elements.length);

        // Put array information into buffers.
        vertexBuffer.put(vertices).flip();
        elementBuffer.put(elements).flip();

        // Create and bind the array object.
        vertexArrayObject = glGenVertexArrays();
        glBindVertexArray(vertexArrayObject);

        // Create the VBO and upload the vertex array to it.
        vertexBufferObject = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vertexBufferObject);
        glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);

        // Define the element buffer: the order to draw vertices.
        elementBufferObject = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, elementBufferObject);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, elementBuffer, GL_STATIC_DRAW);

        // The layout of each vertex in the array is defined by three attributes.
        glVertexAttribPointer(ATTRIB_LOCATION, LOCATION_SIZE, GL_FLOAT, false, vertSize, PTR_LOCATION);
        glEnableVertexAttribArray(ATTRIB_LOCATION);

        glVertexAttribPointer(ATTRIB_COLOR, COLOR_SIZE, GL_FLOAT, false, vertSize, PTR_COLOR);
        glEnableVertexAttribArray(ATTRIB_COLOR);

        glVertexAttribPointer(ATTRIB_TEX_COORDS, TEX_COORD_SIZE, GL_FLOAT, false, vertSize, PTR_TEX_COORDS);
        glEnableVertexAttribArray(ATTRIB_TEX_COORDS);
    }

    @Override
    public void update() {
        program.bind();

        // Upload variables to the program.
        program.uploadUniformVariable(ShaderProgram.VAR_PROJECTION_MATRIX, camera.getProjection());
        program.uploadUniformVariable(ShaderProgram.VAR_VIEW_MATRIX, camera.getView());
        program.uploadUniformVariable(ShaderProgram.VAR_TIME, Time.getTime());
        program.uploadUniformVariable(ShaderProgram.VAR_TEXTURE_SAMPLER, 0);

        // Bind the texture.
        glActiveTexture(GL_TEXTURE0);
        texture.bind();

        // Bind the VAO.
        glBindVertexArray(vertexArrayObject);

        // Enable attributes.
        glEnableVertexAttribArray(ATTRIB_LOCATION);
        glEnableVertexAttribArray(ATTRIB_COLOR);
        glEnableVertexAttribArray(ATTRIB_TEX_COORDS);

        // Draw the model.
        glDrawElements(GL_TRIANGLES, elements.length, GL_UNSIGNED_INT, 0);

        // Disable attributes.
        glDisableVertexAttribArray(ATTRIB_LOCATION);
        glDisableVertexAttribArray(ATTRIB_COLOR);
        glDisableVertexAttribArray(ATTRIB_TEX_COORDS);

        // Unbind VAO.
        glBindVertexArray(0);

        // Detach shader.
        program.unbind();
    }
}
