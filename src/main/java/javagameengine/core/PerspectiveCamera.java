package javagameengine.core;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class PerspectiveCamera extends Camera {
    private float fov = 60;

    public PerspectiveCamera(Window window, Vector2f position) {
        super(window, position);
    }

    @Override
    public void update() {
        projection.identity();
        projection.perspective(fov, window.getHeight() / window.getWidth(), 0, zFar);

        view.identity();
        view.lookAt(new Vector3f(position.x, position.y, zFar / 2), getCameraFront(), getCameraUp());
    }

    public float getFov() {
        return fov;
    }

    public void setFov(float fov) {
        this.fov = fov;
    }
}
