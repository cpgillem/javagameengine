package javagameengine.core;

import javagameengine.events.Event;
import javagameengine.util.Time;

import java.util.LinkedList;
import java.util.Queue;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;

public class Application {
    private Window window;
    private State state;

    // Events
    private Queue<Event> eventQueue = new LinkedList<Event>();

    private float beginTime = 0;
    private float endTime = 0;
    private float delta = 0;

    public Application(Window window, State state) {
        this.window = window;
        this.state = state;
    }

    public void run() {
        init();

        // Main game loop
        while (window.isOpen()) {
            update();
        }

        dispose();
    }

    public void init() {
        window.init();

        // Add event listeners to window
        glfwSetCursorPosCallback(window.getHandle(), MouseListener::mousePosCallback);
        glfwSetMouseButtonCallback(window.getHandle(), MouseListener::mouseButtonCallback);
        glfwSetScrollCallback(window.getHandle(), MouseListener::mouseScrollCallback);
        glfwSetKeyCallback(window.getHandle(), KeyListener::keyCallback);

        beginTime = Time.getTime();
    }

    public void update() {
        state.update();

        // Calculate delta and set new begin time for next update
        endTime = Time.getTime();
        delta = endTime - beginTime;
        beginTime = endTime;
    }

    public void dispose() {
        window.dispose();
    }

    public float getDelta() {
        return delta;
    }

    public Queue<Event> getEventQueue() {
        return eventQueue;
    }

    public Window getWindow() {
        return window;
    }

    public void setWindow(Window window) {
        this.window = window;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
