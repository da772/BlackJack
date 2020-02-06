package engine;

import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;

import engine.Events.Event;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Window {

	String title;
	int width, height;
	long window;
	int vsync = 1;
	EventFunction OnEventCallback;
	
	
	public long GetWindowContext() {
		return window;
	}
	
	public static abstract class EventFunction {
		public abstract boolean run(Event e);
	}
	
	Window(final String title, final int i, final int j) {
		this.title = title;
		this.width = i;
		this.height = j;
	}
	
	
	Window(final String title, final int i, final int j, final boolean vsync) {
		this.title = title;
		this.width = i;
		this.height = j;
		this.vsync = vsync ? 1 : 0;
	}
	
	public void Init() {
		
		GLFWErrorCallback.createPrint(System.err).set();

		// Initialize GLFW. Most GLFW functions will not work before doing this.
		if ( !glfwInit() )
			throw new IllegalStateException("Unable to initialize GLFW");

		// Configure GLFW
		glfwDefaultWindowHints(); // optional, the current window hints are already the default
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable
		
		
		// Create the window
		window = glfwCreateWindow(width, height, title, NULL, NULL);
		if ( window == NULL )
			throw new RuntimeException("Failed to create the GLFW window");
		
		// Print success
		System.out.println("GLFW Initialized: " + this.title + " ( " + width + ", " + height + " )");

		// Setup a key callback. It will be called every time a key is pressed, repeated or released.
		glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
			switch (action ) {
			case GLFW_PRESS:
				{
					Events.KeyPressedEvent e = new Events.KeyPressedEvent(key, 0);
					OnEvent( (Events.Event)((Events.KeyEvent) e) );
					break;
				}
			case GLFW_RELEASE:
				{
					Events.KeyReleasedEvent e = new Events.KeyReleasedEvent(key);
					OnEvent( (Events.Event)((Events.KeyEvent) e) );
					break;
				}
			case GLFW_REPEAT:
				{
					Events.KeyPressedEvent e = new Events.KeyPressedEvent(key, 1);
					OnEvent( (Events.Event)((Events.KeyEvent) e) );
					break;
				}
			}
			if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
				glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
		});
		
		// Setup Mouse button callback. It will be called every time a mouse button is pressed
		glfwSetMouseButtonCallback(window, (window, button, action, mods) ->  {
			
			switch (action) {
			case GLFW_PRESS:
				{
					Events.MouseButtonPressedEvent e = new Events.MouseButtonPressedEvent(button, 0);
					OnEvent( (Events.Event) e );
					break;
				}
			case GLFW_RELEASE:
				{
					Events.MouseButtonReleasedEvent e = new Events.MouseButtonReleasedEvent(button);
					OnEvent( (Events.Event)(e));
					break;
				}
			case GLFW_REPEAT:
				{
					Events.MouseButtonPressedEvent e = new Events.MouseButtonPressedEvent(button, 1);
					OnEvent( (Events.Event)(e) );
					break;
				}
			}
			
		});
		
		// Set up a char callback it will be called every time a char is pressed.
		glfwSetCharCallback(window, (window, keycode) ->
		{
			Events.KeyTypedEvent e = new Events.KeyTypedEvent(keycode);
			OnEvent( (Events.Event)(e));
		});
		
		// Set up a scroll callback it will be called every time the mouse is scrolled
		glfwSetScrollCallback(window, (window, xOffset, yOffset) ->
		{	
			Events.MouseScrolledEvent e = new Events.MouseScrolledEvent((float) xOffset,(float) yOffset);
			OnEvent( (Events.Event) e );
		});
		
		// Set up a cursor callback. It will be called every time the cursor is moved.
		glfwSetCursorPosCallback(window, (window, xPos, yPos) ->
		{
			Events.MouseMovedEvent e = new Events.MouseMovedEvent((float) xPos,(float) yPos);
			OnEvent( (Events.Event) e );
		});
		
		// Set up a  window resized callback it will be called everytime the window is resized
		glfwSetWindowSizeCallback(window, (window, width, height) -> {
			Events.WindowResizedEvent e = new Events.WindowResizedEvent(width,height);
			OnEvent( (Events.Event) e );
		});
		
		// Set up a window closed callback it will be called every time the window is closed.
		glfwSetWindowCloseCallback(window, (window) ->
		{
			Events.WindowClosedEvent e = new Events.WindowClosedEvent();
			OnEvent( (Events.Event) e );
		});
		
		// Get the resolution of the primary monitor
		GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		// Center the window
		glfwSetWindowPos(
			window,
			(vidmode.width() - width) / 2,
			(vidmode.height() - height) / 2
		);
		
		// Make the OpenGL context current
		glfwMakeContextCurrent(window);
		// Enable v-sync
		glfwSwapInterval((int)vsync);
		// Make the window visible
		glfwShowWindow(window);
		// Setup OpenGL
		GL.createCapabilities();
		// Print GL hardware
		System.out.println("OpenGL Initialized: " + glGetString(GL_VENDOR) + ", " + glGetString(GL_RENDERER) + ", " +  glGetString(GL_VERSION));
		// Set the clear color
		glClearColor(1.0f, 0.0f, 0.0f, 0.0f);
		
	}
	
	public void Update() {
		// Clear buffer
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		// Swap buffers
		glfwSwapBuffers(window);
		// Poll events
		glfwPollEvents();
	}
	
	public boolean IsClosed() {
		return glfwWindowShouldClose(window);
	}

	public void Shutdown() {
		// Clean up
		OnEventCallback = null;
		glfwFreeCallbacks(window);
		glfwDestroyWindow(window);
		glfwTerminate();
		glfwSetErrorCallback(null).free();
	}
	

	// Funnel Events to our own callback
	void OnEvent(Events.Event e) {
		if (OnEventCallback != null ) {
			OnEventCallback.run(e);
		}
	}
	
	// Set window callback
	public void SetWindowEventCallback(final EventFunction fn) {
		OnEventCallback = fn;
	}
	
	public void SetWindowSize(final int width, final int height) {
		// set our new size vars
		this.width = width;
		this.height = height;
		// set window size
		glfwSetWindowSize(window, width, height);
	}
	
	public void SetWindowLocation(final int xPos, final int yPos) {
		// Set Window Location
		glfwSetWindowPos(
			window,
			xPos,
			yPos
		);
	}
	
	public void SetWindowTitle(final String title) {
		// Set our title var
		this.title = title;
		// Set Window Title
		glfwSetWindowTitle(window, title);
	}
	
	
}
