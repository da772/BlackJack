package engine;

import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.MemoryStack;

import engine.Events.Event;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class Window {

	String title;
	int width, height;
	long window;
	long monitor;
	int vsync = 0;
	float contentScaleX, contentScaleY;
	EventFunction OnEventCallback;
	
	
	public static Window window_context;
	
	public long GetWindowContext() {
		return window;
	}
	
	
	public int GetWidth() {
		return width;
	}
	
	public int GetHeight() {
		return height;
	}
	
 	public static abstract class EventFunction {
		public abstract boolean run(Event e);
	}
	
 	/**
 	 * 
 	 * @param title - starting title of window
 	 * @param w - starting width of window
 	 * @param h - starting height of window
 	 */
	Window(final String title, final int w, final int h) {
		this.title = title;
		this.width = w;
		this.height = h;
	}
	
	/**
	 * 
	 * @param title - starting title of window
	 * @param w - starting width of window
	 * @param h - starting height of window
	 * @param vsync - vsync enable ?
	 */
	Window(final String title, final int w, final int h, final boolean vsync) {
		this.title = title;
		this.width = w;
		this.height = h;
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
		glfwWindowHint(GLFW_SAMPLES, 4); // MSAA x4
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
	    glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
	    glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
	    glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
		monitor = glfwGetPrimaryMonitor();
		
		// Create the window
		window = glfwCreateWindow(width, height, title, NULL, NULL);
		if ( window == NULL )
			throw new RuntimeException("Failed to create the GLFW window");
		
		// Print success
		System.out.println("GLFW Initialized: " + this.title + " ( " + width + ", " + height + " )");
		
		// Get window DPI
		try (MemoryStack s = MemoryStack.stackPush()) {
            FloatBuffer px = s.mallocFloat(1);
            FloatBuffer py = s.mallocFloat(1);

            glfwGetMonitorContentScale(monitor, px, py);

            contentScaleX = px.get(0);
            contentScaleY = py.get(0);
            px.clear();
            py.clear();
		}
		
		// Setup a key callback. It will be called every time a key is pressed, repeated or released.
		glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
			switch (action ) {
			case GLFW_PRESS: {
					Events.KeyPressedEvent e = new Events.KeyPressedEvent(key, 0);
					OnEvent( (Events.Event)((Events.KeyEvent) e) );
					break;
				}
			case GLFW_RELEASE: {
					Events.KeyReleasedEvent e = new Events.KeyReleasedEvent(key);
					OnEvent( (Events.Event)((Events.KeyEvent) e) );
					break;
				}
			case GLFW_REPEAT: {
					Events.KeyPressedEvent e = new Events.KeyPressedEvent(key, 1);
					OnEvent( (Events.Event)((Events.KeyEvent) e) );
					break;
				}
			}
		});
		
		// Setup Mouse button callback. It will be called every time a mouse button is pressed
		glfwSetMouseButtonCallback(window, (window, button, action, mods) ->  {
			
			switch (action) {
			case GLFW_PRESS: {
					Events.MouseButtonPressedEvent e = new Events.MouseButtonPressedEvent(button, 0);
					OnEvent( (Events.Event) e );
					break;
				}
			case GLFW_RELEASE: {
					Events.MouseButtonReleasedEvent e = new Events.MouseButtonReleasedEvent(button);
					OnEvent( (Events.Event)(e));
					break;
				}
			case GLFW_REPEAT: {
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
			this.width = width;
			this.height = height;
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
		glfwSwapInterval(vsync);
		// Make the window visible
		glfwShowWindow(window);
		// Setup OpenGL
		GL.createCapabilities();
		// Print GL hardware
		System.out.println("OpenGL Initialized: " + glGetString(GL_VENDOR) + ", " + glGetString(GL_RENDERER) + ", " +  glGetString(GL_VERSION));
		// Set the clear color
		glClearColor(1.0f, 0.0f, 0.0f, 0.0f);
		
		window_context = this;
		
	}
	
	/**
	 * 
	 * @param b - true on / false off
	 */
	public void SetVSync(boolean b) {
		vsync = b ? 1 : 0;
		glfwSwapInterval(vsync);
	}
	
	/**
	 * 
	 * @param title - set window's title
	 */
	public void SetTitle(String title) {
		this.title = title;
		glfwSetWindowTitle(window, title);
	}
	
	public void Update() {
		// Swap buffers
		glfwSwapBuffers(window);
		// Poll events
		glfwPollEvents();
	}
	
	/**
	 * 
	 * @param width - buffer to return framebuffer width to
	 * @param height - buffer to return framebuffer height to
	 */
	public void GetFrameBuffers(IntBuffer width, IntBuffer height) {
		try (MemoryStack stack = MemoryStack.stackPush())
		{
			IntBuffer w = stack.mallocInt(1);
			IntBuffer h = stack.mallocInt(1);	
			glfwGetFramebufferSize(window, w, h);
			width.put(w.get());
			height.put(h.get());
			width.flip();
			height.flip();
			w.clear();
			h.clear();	
		}
		
	}
	
	public int[] GetFrameBuffers() {
		int coords[] = new int[2];
		try (MemoryStack stack = MemoryStack.stackPush())
		{
			IntBuffer w = stack.mallocInt(1);
			IntBuffer h = stack.mallocInt(1);	
			glfwGetFramebufferSize(window, w, h);
			coords[0] = w.get();
			coords[1] = h.get();
			w.clear();
			h.clear();	
		}
		return coords;
	}
	
	/**
	 * 
	 * @param xPos - buffer to return x position
	 * @param yPos - buffer to return y position
	 */
	public int[] GetWindowPosition() {
		int coords[] = new int[2];
		try (MemoryStack stack = MemoryStack.stackPush())
		{
			IntBuffer xpos = stack.mallocInt(1);
			IntBuffer ypos = stack.mallocInt(1);	
			glfwGetWindowPos(window, xpos, ypos);
			coords[0] = xpos.get();
			coords[1] = ypos.get();
			xpos.clear();
			ypos.clear();	
		}
		return coords;
		
	}
	
	public int[] GetGpuUsage() {
		int[] gpu = new int[3];
		try (MemoryStack stack = MemoryStack.stackPush())
		{
			IntBuffer t = stack.mallocInt(1);
			IntBuffer u = stack.mallocInt(1);
			glGetIntegerv(0x9048, t);
			glGetIntegerv(0x9049, u);
			gpu[0] = (int) Math.round(t.get()/1e03);
			gpu[1] = (int) Math.round(u.get()/1e03);
			t.clear();
			u.clear();
		}
		gpu[2] = gpu[0]-gpu[1];
		return gpu;
	}
	
	public String GetGLInfo() {
		return  glGetString(GL_VENDOR) + " " + glGetString(GL_VERSION) + ", " + glGetString(GL_RENDERER);
	}
	
	public boolean Vsync() {
		return vsync > 0;
	}
	
	public float GetTime() {
		return (float)glfwGetTime();
	}
	
	public boolean IsClosed() {
		return glfwWindowShouldClose(window);
	}

	
	public float GetContentScaleX() {
		return contentScaleX;
	}
	
	public float GetContentScaleY() {
		return contentScaleY;
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
	
	/**
	 * 
	 * @param xPos - xposition of window
	 * @param yPos - yposition of window
	 */
	public void SetWindowLocation(final int xPos, final int yPos) {
		// Set Window Location
		glfwSetWindowPos(
			window,
			xPos,
			yPos
		);
	}
	
	
	/**
	 * 
	 * @param title - string to set title to
	 */
	public void SetWindowTitle(final String title) {
		// Set our title var
		this.title = title;
		// Set Window Title
		glfwSetWindowTitle(window, title);
	}
	
	
}
