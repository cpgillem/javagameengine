package javagameengine.core;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryUtil;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;

public class Window {
	private int width = 800;
	private int height = 600;
	private String title = "";
	private boolean resizable = true;
	private boolean maximized = true;

	// Parent application
	private Application application;

	// GLFW handle
	private long handle = -1;

	public Window(Application application) {
		this.application = application;
	}

	public void init() {
		// Set the error callback to System.err
		GLFWErrorCallback.createPrint(System.err).set();

		// Initialize GLFW
		if (!glfwInit()) {
			throw new IllegalStateException("Unable to initialize GLFW");
		}

		// Set window hints
		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // Hide first
		glfwWindowHint(GLFW_RESIZABLE, resizable ? GLFW_TRUE : GLFW_FALSE);
		glfwWindowHint(GLFW_MAXIMIZED, maximized ? GLFW_TRUE : GLFW_FALSE);

		// Create a window
		handle = glfwCreateWindow(width, height, title, MemoryUtil.NULL, MemoryUtil.NULL);

		if (handle == MemoryUtil.NULL) {
			throw new IllegalStateException("Could not create GLFW window: NULL handle");
		}

		// Make OpenGL current context
		glfwMakeContextCurrent(handle);

		// Enable v-sync
		glfwSwapInterval(1);

		// Show the window
		glfwShowWindow(handle);

		GL.createCapabilities();
	}

	public void update() {
		// Poll events
		glfwPollEvents();

		// Swap buffers
		glfwSwapBuffers(handle);
	}

	public boolean isOpen() {
		return !glfwWindowShouldClose(handle);
	}
	
	public void dispose() {
        // Free memory
         glfwFreeCallbacks(handle);
         glfwDestroyWindow(handle);

         // Terminate GLFW
		glfwTerminate();
		glfwSetErrorCallback(null).free();
	}

	public Application getApplication() {
		return application;
	}

	public long getHandle() {
		return handle;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public String getTitle() {
		return title;
	}

	public boolean isResizable() {
		return resizable;
	}

	public boolean isMaximized() {
		return maximized;
	}
}
