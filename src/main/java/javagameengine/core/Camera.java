package javagameengine.core;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

public abstract class Camera {
    protected Window window;
    protected Matrix4f projection = new Matrix4f();
    protected Matrix4f view = new Matrix4f();
    protected Vector2f position;
    protected float zFar = 100;

    public Camera(Window window, Vector2f position) {
        this.window = window;
        this.position = position;
    }

    public abstract void update();

    public Window getWindow() {
        return window;
    }

    public void setWindow(Window window) {
        this.window = window;
    }

    public Matrix4f getProjection() {
        return projection;
    }

    public Matrix4f getInvertedProjection() {
        Matrix4f result = new Matrix4f();
        projection.invert(result);
        return result;
    }

    public void setProjection(Matrix4f projection) {
        this.projection = projection;
    }

    public Matrix4f getView() {
        return view;
    }

    public Matrix4f getInvertedView() {
        Matrix4f result = new Matrix4f();
        view.invert(result);
        return result;
    }

    public void setView(Matrix4f view) {
        this.view = view;
    }

    public Vector2f getPosition() {
        return position;
    }

    public void setPosition(Vector2f position) {
        this.position = position;
    }

    public float getzFar() {
        return zFar;
    }

    public void setzFar(float zFar) {
        this.zFar = zFar;
    }

    public Vector3f getCameraUp() {
        return new Vector3f(0, 1, 0);
    }

    public Vector3f getCameraFront() {
        return new Vector3f(position.x, position.y, -1);
    }
}
