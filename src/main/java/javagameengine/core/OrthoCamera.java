package javagameengine.core;

import org.joml.Vector2f;
import org.joml.Vector3f;

public class OrthoCamera extends Camera {
    private Vector2f worldSize = new Vector2f(100, 100);

    public OrthoCamera(Window window, Vector2f position) {
        super(window, position);
    }

    @Override
    public void update() {
        projection.identity();
        projection.ortho(0, worldSize.x, 0, worldSize.y, 0, zFar);

        view.identity();
        view.lookAt(new Vector3f(position.x, position.y, zFar / 2), getCameraFront(), getCameraUp());
    }
}
