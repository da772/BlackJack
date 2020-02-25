package engine;

import static org.lwjgl.glfw.GLFW.*;
import java.nio.DoubleBuffer;
import org.lwjgl.BufferUtils;

public class Input {
	
	/**
	 * 
	 * @param keycode - keycode to check (use KeyCodes class)
	 * @return boolean - true if pressed false if not
	 */
	public static boolean IsKeyPressed(int keycode) {
		if (Application.app != null && Application.GetWindow() != null) {
			int state = glfwGetKey(Application.GetWindow().GetWindowContext(), keycode);
			return state == GLFW_PRESS || state == GLFW_REPEAT;
		}
		return false;
	}
	
	/**
	 * 
	 * @param button - button to check (use KeyCodes class)
	 * @return boolean - true if pressed false if not
	 */
	public static boolean IsMouseButtonPressed(int button) {
		if (Application.app != null && Application.GetWindow() != null) {
			int state = glfwGetMouseButton(Application.GetWindow().GetWindowContext(), button);
			return state == GLFW_PRESS;
		}
		return false;
	}
	
	/**
	 * 
	 * @return float - MouseX position relative to window
	 */
	public static float GetMouseX() {
		if (Application.app != null && Application.GetWindow() != null) {
			DoubleBuffer posX = BufferUtils.createDoubleBuffer(1);
			glfwGetCursorPos(Application.GetWindow().GetWindowContext(), posX, null);
			float x = (float)posX.get(0);
			posX.clear();
			return x;
		}
		return 0;
	}
	
	/**
	 * 
	 * @return float - MouseY position relative to window
	 */
	public static float GetMouseY() {
		if (Application.app != null && Application.GetWindow() != null) {
			DoubleBuffer posY = BufferUtils.createDoubleBuffer(1);
			glfwGetCursorPos(Application.GetWindow().GetWindowContext(), null, posY);
			float y = (float)posY.get(0);
			posY.clear();
			return y;
		}
		return 0;
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
