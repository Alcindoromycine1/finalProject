package engine.io;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.opengl.CGL;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

public class Window {

	private int width;// Width of the window size
	private int height;// Height of the window size
	private String title;// Title of the window
	private long window;
	public Input input;
	private float backgroundR, backgroundG, backgroundB;
	private GLFWWindowSizeCallback sizeCallback;
	private boolean isResized;
	private boolean isFullscreen;

	/** This sets the window size of the output and gives it a title */
	public Window(int width, int height, String title) {
		this.width = width;
		this.height = height;
		this.title = title;

	}

	public void create() {

		// This will check if something was not initialized in init();
		if (!GLFW.glfwInit()) {
			System.err.println("Something is not initialized");
			return;
		}
		input = new Input();
		window = GLFW.glfwCreateWindow(width, height, title, isFullscreen ?  GLFW.glfwGetPrimaryMonitor() : 0, 0);// Creates the window with a size of 800 x 500 pixels
																	// and a title named Final Project
		// This will check if window has been initialized
		if (window == 0) {
			System.err.println("Window is not created");
			return;
		}

		GLFWVidMode videoMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
		GLFW.glfwSetWindowPos(window, (videoMode.width() - width) / 2, (videoMode.height() - height) / 2);// Sets
		GLFW.glfwMakeContextCurrent(window); // the
		// window to
		// the
		// center of
		// the
		// monitor

		GL.createCapabilities();

		createCallBack();

		GLFW.glfwShowWindow(window);// displays the window

	}

	public void destroy() {

		input.destroy();
		GLFW.glfwWindowShouldClose(window);
		GLFW.glfwDestroyWindow(window);
		GLFW.glfwTerminate();

	}

	public void createCallBack() {
		sizeCallback = new GLFWWindowSizeCallback() {

			@Override
			public void invoke(long window, int w, int h) {
				width = w;
				height = h;
				isResized = true;
			}
		};

		// Input with Mouse and Keyboard
		GLFW.glfwSetKeyCallback(window, input.getKeyboardCallback());
		GLFW.glfwSetCursorPosCallback(window, input.getMouseMoveCallback());
		GLFW.glfwSetMouseButtonCallback(window, input.getMouseButtonsCallback());
		GLFW.glfwSetWindowSizeCallback(window, sizeCallback);

	}

	public void update() {
		if (isResized) {
			GL11.glViewport(0, 0, width, height);
			isResized = false;
		}
		GL11.glViewport(0, 0, width, height);
		GL11.glClearColor(backgroundR, backgroundG, backgroundB, 1.0f);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

		GLFW.glfwPollEvents();
	}

	public void swapBuffers() {

		GLFW.glfwSwapBuffers(window);// colours the window correctly so it's not just a white screen

	}

	/**
	 * This method allows us to close the window (application) after you are done
	 * using it
	 */
	public boolean close() {

		return GLFW.glfwWindowShouldClose(window);

	}

	/** Sets the window title with fps counter */
	public void setTitle(String title) {
		GLFW.glfwSetWindowTitle(window, title);
	}

	/** Sets the background colour using RGB */
	public void setBackgroundColor(float r, float g, float b) {

		backgroundR = r;
		backgroundG = g;
		backgroundB = b;

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

	public long getWindow() {
		return window;
	}

	public boolean isFullscreen() {
		return isFullscreen;
	}

	public void setFullscreen(boolean isFullscreen) {
		this.isFullscreen = isFullscreen;
		isResized = true;
	}

}
