package project;

import org.lwjgl.glfw.GLFW;

import engine.io.Input;
import engine.io.Window;

public class FinalProject implements Runnable {
	public Thread game;
	public static Window window;
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 760;

	public void start() {

		game = new Thread(this, "Final Project");
		game.start();

	}

	/**
	 * This method is where you initialize all the objects such as windows, books,
	 * etc
	 */
	public static void init() {

		window = new Window(WIDTH, HEIGHT, "Final Project Title");
		window.setBackgroundColor(1.0f, 100, 0);
		window.setFullscreen(true);
		window.create();
	}

	public void run() {
		init();

		final int TARGET_FPS = 60;
		final long FRAME_TIME = 1000 / TARGET_FPS;

		long lastTime = System.currentTimeMillis();
		long timer = System.currentTimeMillis();
		int frames = 0;

		while (!window.close() && !Input.isKeyDown(GLFW.GLFW_KEY_ESCAPE)) { // Proper way to check if the window should
																			// close
			long now = System.currentTimeMillis();
			long elapsedTime = now - lastTime;

			if (elapsedTime >= FRAME_TIME) {
				update();
				render();

				window.swapBuffers();

				lastTime = now;
				frames++;
			}

			// FPS counter updates every second
			if (System.currentTimeMillis() - timer >= 1000) {
				// Set the window title with FPS
				window.setTitle("Final Project | FPS: " + frames);
				frames = 0;
				timer += 1000;
			}

			GLFW.glfwPollEvents();
		}
		window.destroy();// closes the window
	}

	private void update() {

		window.update();
		if (Input.isButtonDown(GLFW.GLFW_MOUSE_BUTTON_LEFT)) {// If the left mouse button is pressed

			System.out.println("X: " + Input.getMouseX() + " , Y: " + Input.getMouseY());

		}
	}

	private void render() {

		window.swapBuffers();
	}

	public static void main(String[] args) {

		FinalProject a = new FinalProject();
		a.start();

	}

}
