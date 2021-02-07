package javagameengine.core;

import java.util.ArrayList;
import java.util.List;

public abstract class State {
    private Application application;
    private List<Entity> entities = new ArrayList<Entity>();
    private OrthoCamera orthoCamera;
    private PerspectiveCamera perspectiveCamera;

    public State(Application application) {
        this.application = application;
    }

    public void init() {

    }

    public void update() {
        for (int i = 0; i < entities.size(); i++) {
            entities.get(i).update();
        }
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public OrthoCamera getOrthoCamera() {
        return orthoCamera;
    }

    public void setOrthoCamera(OrthoCamera orthoCamera) {
        this.orthoCamera = orthoCamera;
    }

    public PerspectiveCamera getPerspectiveCamera() {
        return perspectiveCamera;
    }

    public void setPerspectiveCamera(PerspectiveCamera perspectiveCamera) {
        this.perspectiveCamera = perspectiveCamera;
    }
}
