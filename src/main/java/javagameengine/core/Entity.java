package javagameengine.core;

import java.util.ArrayList;
import java.util.List;

public class Entity {
    private String id;
    private List<Component> components = new ArrayList<Component>();
    private State state;

    public Entity(State state, String id) {
        this.state = state;
        this.id = id;
    }

    public void init() {
        for (int i = 0; i < components.size(); i++) {
            components.get(i).init();
        }
    }

    public void update() {
        for (int i = 0; i < components.size(); i++) {
            components.get(i).update();
        }
    }

    public <T extends Component> T getComponent(Class<T> componentClass) {
        // Try to find the component.
        for (Component c : components) {
            if (componentClass.isAssignableFrom(c.getClass())) {
                return componentClass.cast(c);
            }
        }

        return null;
    }

    public <T extends Component> void removeComponent(Class<T> componentClass) {
        // Use normal for loop to avoid concurrent modifications
        for (int i = 0; i < components.size(); i++) {
            Component c = components.get(i);
            if (componentClass.isAssignableFrom(c.getClass())) {
                components.remove(i);
                return;
            }
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public State getState() {
        return state;
    }
}
