package javagameengine.core;

public abstract class Component {
    public Entity entity;

    public Component(Entity entity) {
        this.entity = entity;
    }

    public abstract void update();
    public void init() {}

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    public Entity getEntity() {
        return entity;
    }
}
