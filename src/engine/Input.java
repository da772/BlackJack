package engine;

import static org.lwjgl.glfw.GLFW.*;
import java.nio.DoubleBuffer;
import org.lwjgl.BufferUtils;

public class Input {
	
	public static boolean IsKeyPressed(int keycode) {
		if (Application.app != null && Application.app.GetWindow() != null) {
			int state = glfwGetKey(Application.app.GetWindow().GetWindowContext(), keycode);
			return state == GLFW_PRESS || state == GLFW_REPEAT;
		}
		return false;
	}
	
	public static boolean IsMouseButtonPressed(int button) {
		if (Application.app != null && Application.app.GetWindow() != null) {
			int state = glfwGetMouseButton(Application.app.GetWindow().GetWindowContext(), button);
			return state == GLFW_PRESS;
		}
		return false;
	}
	
	public static float GetMouseX() {
		if (Application.app != null && Application.app.GetWindow() != null) {
			DoubleBuffer posX = BufferUtils.createDoubleBuffer(1);
			glfwGetCursorPos(Application.app.GetWindow().GetWindowContext(), posX, null);
			float x = (float)posX.get(0);
			posX.clear();
			return x;
		}
		return 0;
	}
	
	public static float GetMouseY() {
		if (Application.app != null && Application.app.GetWindow() != null) {
			DoubleBuffer posY = BufferUtils.createDoubleBuffer(1);
			glfwGetCursorPos(Application.app.GetWindow().GetWindowContext(), null, posY);
			float y = (float)posY.get(0);
			posY.clear();
			return y;
		}
		return 0;
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
