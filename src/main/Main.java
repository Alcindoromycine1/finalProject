package main;

import org.lwjgl.glfw.GLFW;

import engine.io.Input;
import engine.io.Window;

public class Main implements Runnable {

	public Thread game;
	public Window window;
	public final int WIDTH = 1280, HEIGHT = 760;

	public void start() {
		game = new Thread(this, "game");
		game.start();
	}

	public void init() {
		System.out.println("Started Game!");
		window = new Window(WIDTH, HEIGHT, "Game");
		window.create();

	}

	public void run() {
		init();

		final int TARGET_FPS = 60;
		final long FRAME_TIME = 1000 / TARGET_FPS;

		long lastTime = System.currentTimeMillis();
		long timer = System.currentTimeMillis();
		int frames = 0;

		while (!window.close()) { // Proper way to check if the window should close
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
			if (Input.isKeyDown(GLFW.GLFW_KEY_ESCAPE)) return;
		}
		window.destroy();
	}

	private void update() {
		// System.out.println("Updating Game!");
		window.update();
		if (Input.isButtonDown(GLFW.GLFW_MOUSE_BUTTON_LEFT)) {
			System.out.println("X: " + Input.getMouseX() + ", Y: " + Input.getMouseY());
		}

	}

	private void render() {
		// System.out.println("Rendering Game!");
		window.swapBuffers();
	}

	public static void main(String[] args) {

		Main m = new Main();
		m.start();

	}

}
