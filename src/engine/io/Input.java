package engine.io;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;

public class Input {
	private static boolean[] keys = new boolean[GLFW.GLFW_KEY_LAST];
	private static boolean[] buttons = new boolean[GLFW.GLFW_MOUSE_BUTTON_LAST];
	private static double mouseX;
	private static double mouseY;

	private GLFWKeyCallback keyboard;
	private GLFWCursorPosCallback mouseMove;
	private GLFWMouseButtonCallback mouseButtons;

	public Input() {

		keyboard = new GLFWKeyCallback() {

			@Override
			public void invoke(long window, int key, int scancode, int action, int mods) {
				keys[key] = (action != GLFW.GLFW_RELEASE);
			}
		};
		mouseMove = new GLFWCursorPosCallback() {
			public void invoke(long window, double xPos, double yPos) {
				mouseX = xPos;
				mouseY = yPos;
			}
		};
		mouseButtons = new GLFWMouseButtonCallback() {
			public void invoke(long window, int button, int action, int mods) {
				buttons[button] = (action != GLFW.GLFW_RELEASE);
			}
		};
	}
	/** @return Returns whether the key is being pressed or not*/
	public static boolean isKeyDown (int key) {
		
		return keys[key];
		
	}
	/** @return Returns whether the mouse is moving or not */
public static boolean isButtonDown (int button) {
		
		return buttons[button];
		
	}
	
	/** Clears the keyboard callback*/
	public void destroy() {

		keyboard.free();
		mouseButtons.free();
		mouseMove.free();

	}

	public GLFWKeyCallback getKeyboardCallback() {
		return keyboard;
	}

	public void setKeyboard(GLFWKeyCallback keyboard) {
		this.keyboard = keyboard;
	}

	public static double getMouseX() {
		return mouseX;
	}

	public static double getMouseY() {
		return mouseY;
	}

	public GLFWCursorPosCallback getMouseMoveCallback() {
		return mouseMove;
	}

	public GLFWMouseButtonCallback getMouseButtonsCallback() {
		return mouseButtons;
	}

}
