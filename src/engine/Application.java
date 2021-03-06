package engine;

import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

import engine.Events.Event;
import engine.Events.KeyEvent;
import engine.Events.MouseButtonEvent;
import engine.Events.MouseMovedEvent;
import engine.Events.MouseScrolledEvent;
import engine.Events.WindowClosedEvent;
import engine.Events.WindowResizedEvent;
import engine.audio.AudioManager;
import engine.renderer.Renderer;
import engine.util.Timing;



/**
 * Application Class
 * 		Applications are created by making children of this object
 * 		Blackjack.java is a child of this class
 * 
 * 		
 * 
 * 		Derivable class with several overrideable functions
 * 		(Requirement 2.1.0)		
 * 
 * 		
 **/

public class Application {
	
	protected static Application app;
	protected Window window;
	protected int width = 1280, height = 720;
	protected String title = "Application";
	protected boolean running = true, paused = false;
	private float lastFrameTime = 0;
	protected long lastTime = 0;
	protected int fps = 0, frames = 0;
	protected int fpsCap = 255;
	protected static long threadId = -1;
	protected boolean vsync = false;
	protected boolean debugMode = true;
	
		
	public Application() {
		
	}
	
	/**
	 * Application( final String title, final int width, final int height)
	 * 		title - title of window the application will create
	 * 		width - width of the window the application will create
	 * 		height - height of the window the application will create
	 * 	
	 * 		1. Set variables
	 * 
	 **/			
	
	public Application(final String title, final int width, final int height) {
		this.width = width;
		this.height = height;
		this.title = title;
		Application.app = this;
	}
	
	
	/* *
	 * Init()
	 * 		1. Create window
	 * 		2. Initialize the window
	 * 		3. Set the window event callbacks
	 * 		4. Call init of our inherited class
	**/
	
	public void Init() {
		threadId = Thread.currentThread().getId();
		window = new Window(title, width, height);
		if (window != null) {
			window.Init();
			window.SetWindowEventCallback(new Window.EventFunction() { @Override public boolean run(Event e) { Event(e); return false; } });
		}
		Renderer.Init(window.GetFrameBuffers()[0], window.GetFrameBuffers()[1]);
		if (debugMode) Debugger.Init();
		Collision2D.Begin();
		AudioManager.Init();
		WindowFrame.Init();
		OnInit();
	}
	

	
	protected void OnInit() {};
	
	
	/**
	 * Event(Event event)
	 * 		event - event the window is telling us about
	 * 
	 * 		1. Create dispatcher (checks each event then runs function based on class)
	 * 		2. Dispatch KeyEvent - KeyPress, KeyReleased, KeyTyped
	 * 		3. Dispatch MouseButtonEvent - MouseButtonPressed, MouseButtonReleased
	 * 		4. Dispatch MouseMovedEvent
	 * 		5. Dispatch MouseScrolledEvent
	 * 		6. Dispatch WindowResizedEvent
	 * 		7. Dispatch WindowClosedEvent
	 * 
	 **/
	public void Event(Event event) {
		Events.EventDispatcher<Event> dispatcher = new Events.EventDispatcher<Event>(event);
		dispatcher.Dispatch(KeyEvent.GetGenericType(), new Events.EventFunction<Event>(event) {
			@Override public boolean run(Event t) { return KeyEvent((KeyEvent) t);} });
		
		dispatcher.Dispatch(MouseButtonEvent.GetGenericType(), new Events.EventFunction<Event>(event) {
			@Override public boolean run(Event t) { return MouseButtonEvent((MouseButtonEvent) t);  } });
		
		dispatcher.Dispatch(MouseMovedEvent.GetGenericType(), new Events.EventFunction<Event>(event) {
			@Override public boolean run(Event t) { return MouseMoveEvent((MouseMovedEvent) t); } });
		
		dispatcher.Dispatch(MouseScrolledEvent.GetGenericType(), new Events.EventFunction<Event>(event) {
			@Override public boolean run(Event t) { return MouseScrolledEvent((MouseScrolledEvent) t);} });
		
		dispatcher.Dispatch(WindowResizedEvent.GetGenericType(), new Events.EventFunction<Event>(event) {
			@Override public boolean run(Event t) {
				return WindowResizedEvent((WindowResizedEvent) t);}
			});
		dispatcher.Dispatch(WindowClosedEvent.GetGenericType(), new Events.EventFunction<Event>(event) {
			@Override public boolean run(Event t) {return WindowClosedEvent((WindowClosedEvent)t ); } });
		Collision2D.OnEvent(event);
		WindowFrame.OnEvent(event);
		if (debugMode) Debugger.OnEvent(event);
		OnEvent(event);
		SceneManager.OnEvent(event);
		
	}
	
	protected void OnEvent(Event event) {};
	
	/**
	 * @param e (WindowResizedEvent)
	 *            - event
	**/
	protected boolean WindowResizedEvent(WindowResizedEvent e) {
		
		IntBuffer w = BufferUtils.createIntBuffer(1);
		IntBuffer h = BufferUtils.createIntBuffer(1);
		window.GetFrameBuffers(w, h);
		Renderer.Resize(0, 0, w.get(),h.get());
		w.clear();
		h.clear();
		return false;
	}
	
	/**
	 * @param e (WindowClosedEvent)
	 *  - To be overridden by child class
	 * 		WindowClosedEvent(WindowResizedEvent e)	
	 * 
	 **/
	protected boolean WindowClosedEvent(WindowClosedEvent e) {
	
		//System.out.println(e);
		running = false;
		return true;
	}
	/**
	 * To be overridden by child class
	 * 		MouseScrolledEvent(MouseScrolledEvent e)
	 * 				
	 * 		@param e (MouseScrolledEvent)
	 * 			(float) e.GetScrollX() - returns scroll amount on X axis (horizontal scrolling)
	 * 			(float) e.GetScrollY() - returns scroll amount on Y axis (vertical scrolling)
	 * 		
	 **/
	protected boolean MouseScrolledEvent(MouseScrolledEvent e) {
		//System.out.println(e);
		return false;
	}
	/**
	 * To be overridden by child class
	 * 		MouseMoveEvent(MouseScrolledEvent e)
	 * 				
	 * 		@param e (MouseMoveEvent)
	 * 			(float) e.GetMouseX() - returns new x location of cursor
	 * 			(float) e.GetMouseX() - returns new y location of cursor
	 * 		
	 **/
	protected boolean MouseMoveEvent(MouseMovedEvent e) {
		//System.out.println(e);
		return false;
	}
	/**
	 * To be overridden by child class
	 * 		MouseButtonEvent(MouseScrolledEvent e)
	 * 				
	 * 		@param e (MouseButtonEvent) - can either be MouseButtonReleasedEvent or MouseButtonPressedEvent
	 * 			(int) e.GetKeyCode() - returns keycode of key pressed
	 * 			(EventType) e.GetEventType() - returns the event type either EventType.MouseButtonReleased or EventType.MouseButtonPressed
	 * 		
	 **/
	protected boolean MouseButtonEvent(MouseButtonEvent e) {
		//System.out.println(e);
		return false;
	}
	/**
	 * To be overridden by child class
	 * 		KeyEvent(MouseScrolledEvent e)
	 * 				
	 * 		@param e (KeyEvent)  - can either be KeyReleasedEvent or KeyPressedEvent
	 * 			(int) e.GetKeyCode() - returns keycode of key pressed
	 * 			(EventType) e.GetEventType() - returns the event type either EventType.KeyReleased or EventType.KeyPressed
	 * 		
	 **/
	protected boolean KeyEvent (KeyEvent e) {
		//System.out.println(e);	
		return false;
	}
	
	
	/**
	 * Main application loop
	 * 		Run()
	 * 				
	 * 		1. Loop with running variable
	 * 		2. Call inherited OnUpdate
	 * 		3. Check window is valid
	 * 		4. Update window
	 * 
	 * 		
	 **/
	public void Run() {
		while (running) {
			CalcFPS();
			float time = window.GetTime();
			float deltaTime = time - lastFrameTime;
			lastFrameTime = time;
			OnUpdate(deltaTime);
			SceneManager.OnUpdate(deltaTime, paused);
			if (debugMode) Debugger.Update();
			AudioManager.Update();
			Renderer.Render();
			window.Update();
			Timing.sync(fpsCap, GetWindow().Vsync());
		}
	}
	
	
	private void CalcFPS() {
		frames++;
		long newTime = System.nanoTime();
		if (newTime >= lastTime + 1e9) {
			fps = frames;
			frames = 0;
			lastTime = newTime;
		}	
	}
	
	/**
	 * Application Shutdown
	 * 		Shutdown()
	 * 				
	 * 		1. Call inherited shutdown
	 * 		2. Check window is valid
	 * 		3. Shutdown Window
	 * 
	 * 		
	 **/
	public void Shutdown() {
		OnShutdown();
		SceneManager.Shutdown();
		WindowFrame.Shutdown();
		if (debugMode) Debugger.ShutDown();
		Collision2D.CleanUp();
		AudioManager.Shutdown();
		Renderer.ShutDown();
		if (window != null) {
			window.Shutdown();
		}
	}
	
	public static boolean IsRunning() {
		return app.running;
	}
	
	public static void CloseApplication() {
		app.running = false;
	}
	
	public static int GetFPSCap() {
		return app.fpsCap;
	}
	
	/**
	 * 	GetWindow() - returns window object
	 * 		
	 **/
	public static Window GetWindow() {
		return app.window;
	}
	/**
	 * To be overridden by child class
	 * 	(Requirement 2.1.1)
	 **/
	protected void OnUpdate(float deltaTime) {};
	/**
	 * To be overridden by child class
	 * 	
	 **/
	protected void OnShutdown() {};
	
	/**
	 * Checks if we are on the main thread
	 *   - needed for modifying the graphics context since it is created on the main thread
	 * @return
	 */
	public static boolean ThreadSafe() {
		return threadId == Thread.currentThread().getId();
	}
	
	
	public static boolean IsVsync() {
		return app.vsync;
	}
	
	public static void SetVSync(boolean b) {
		app.vsync = b;
		GetWindow().SetVSync(b);
	}
	
	public static Application GetApp() {
		return app;
	}
	
	public static boolean IsPaused() {
		return GetApp().paused;
	}
	
	public static void SetFPSCap(int cap) {
		app.fpsCap = cap;
		Collision2D.Reset();
	}
	
	public void SetPaused(boolean b) {
		this.paused = b;
	}

}
