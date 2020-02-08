package engine;

import engine.Events.Event;
import engine.Events.KeyEvent;
import engine.Events.MouseButtonEvent;
import engine.Events.MouseMovedEvent;
import engine.Events.MouseScrolledEvent;
import engine.Events.WindowClosedEvent;
import engine.Events.WindowResizedEvent;
//TEMP
import renderer.Renderer;
import util.Timing;


/* 
 * Application Class
 * 		Applications are created by making children of this object
 * 		Blackjack.java is a child of this class
 * 
 * 		EntryPoint.java creates an Application Object and runs it.
 * 		
 * 		
 */

public class Application {
	
	public static Application app;
	protected Window window;
	protected int width = 1280, height = 720;
	protected String title = "Application";
	protected boolean running = true;
	private float lastFrameTime = 0;
	protected long lastTime = 0;
	protected int fps = 0, frames = 0;
	protected int fpsCap = 0;
	
		
	public Application() {
		
	}
	
	/*
	 * Application( final String title, final int width, final int height)
	 * 		title - title of window the application will create
	 * 		width - width of the window the application will create
	 * 		height - height of the window the application will create
	 * 	
	 * 		1. Set variables
	 * 
	 */			
	
	public Application(final String title, final int width, final int height) {
		this.width = width;
		this.height = height;
		this.title = title;
		Application.app = this;
	}
	
	
	/* 
	 * Init()
	 * 		1. Create window
	 * 		2. Initialize the window
	 * 		3. Set the window event callbacks
	 * 		4. Call init of our inherited class
	 */
	
	public void Init() {
		window = new Window(title, width, height);
		if (window != null) {
			window.Init();
			window.SetWindowEventCallback(new Window.EventFunction() { @Override public boolean run(Event e) { Event(e); return false; } });
		}
		Renderer.Init();
		OnInit();
		
	}
	
	protected void OnInit() {};
	
	
	/*
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
	 */
	
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
			@Override public boolean run(Event t) { return WindowResizedEvent((WindowResizedEvent) t);} });
		
		dispatcher.Dispatch(WindowClosedEvent.GetGenericType(), new Events.EventFunction<Event>(event) {
			@Override public boolean run(Event t) {return WindowClosedEvent((WindowClosedEvent)t ); } });
	
		OnEvent(event);
	}
	
	protected void OnEvent(Event event) {};
	
	/*
	 * To be overridden by child class
	 * 		WindowResizedEvent(WindowResizedEvent e)
	 * 				
	 * 		WindowResizedEvent e
	 * 			(float) e.GetWidth() - returns new width
	 * 			(float) e.GetHeight() - returns new height
	 * 		
	 */
	protected boolean WindowResizedEvent(WindowResizedEvent e) {
		//System.out.println(e);
		Renderer.SetViewport(0, 0, (int)e.GetWidth(), (int)e.GetHeight());
		return false;
	}
	
	/*
	 * To be overridden by child class
	 * 		WindowClosedEvent(WindowResizedEvent e)
	 * 				
	 * 		WindowClosedEvent e
	 * 			
	 * 
	 */
	protected boolean WindowClosedEvent(WindowClosedEvent e) {
	
		//System.out.println(e);
		running = false;
		return true;
	}
	/*
	 * To be overridden by child class
	 * 		MouseScrolledEvent(MouseScrolledEvent e)
	 * 				
	 * 		MouseScrolledEvent e
	 * 			(float) e.GetScrollX() - returns scroll amount on X axis (horizontal scrolling)
	 * 			(float) e.GetScrollY() - returns scroll amount on Y axis (vertical scrolling)
	 * 		
	 */
	protected boolean MouseScrolledEvent(MouseScrolledEvent e) {
		//System.out.println(e);
		return false;
	}
	/*
	 * To be overridden by child class
	 * 		MouseMoveEvent(MouseScrolledEvent e)
	 * 				
	 * 		MouseMoveEvent e
	 * 			(float) e.GetMouseX() - returns new x location of cursor
	 * 			(float) e.GetMouseX() - returns new y location of cursor
	 * 		
	 */
	protected boolean MouseMoveEvent(MouseMovedEvent e) {
		//System.out.println(e);
		return false;
	}
	/*
	 * To be overridden by child class
	 * 		MouseButtonEvent(MouseScrolledEvent e)
	 * 				
	 * 		MouseButtonEvent e - can either be MouseButtonReleasedEvent or MouseButtonPressedEvent
	 * 			(int) e.GetKeyCode() - returns keycode of key pressed
	 * 			(EventType) e.GetEventType() - returns the event type either EventType.MouseButtonReleased or EventType.MouseButtonPressed
	 * 		
	 */
	protected boolean MouseButtonEvent(MouseButtonEvent e) {
		//System.out.println(e);
		return false;
	}
	/*
	 * To be overridden by child class
	 * 		KeyEvent(MouseScrolledEvent e)
	 * 				
	 * 		KeyEvent e - can either be KeyReleasedEvent or KeyPressedEvent
	 * 			(int) e.GetKeyCode() - returns keycode of key pressed
	 * 			(EventType) e.GetEventType() - returns the event type either EventType.KeyReleased or EventType.KeyPressed
	 * 		
	 */
	protected boolean KeyEvent (KeyEvent e) {
		//System.out.println(e);
		return false;
	}
	
	
	/*
	 * Main application loop
	 * 		Run()
	 * 				
	 * 		1. Loop with running variable
	 * 		2. Call inherited OnUpdate
	 * 		3. Check window is valid
	 * 		4. Update window
	 * 
	 * 		
	 */
	public void Run() {
		while (running) {
			CalcFPS();
			float time = window.GetTime();
			float deltaTime = time - lastFrameTime;
			lastFrameTime = time;
			Renderer.Prepare();
			OnUpdate(deltaTime);
			if (window != null && !window.IsClosed()) {
				window.Update();
			}
			
			Timing.sync(fpsCap, window.Vsync());
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
	
	/*
	 * Application Shutdown
	 * 		Shutdown()
	 * 				
	 * 		1. Call inherited shutdown
	 * 		2. Check window is valid
	 * 		3. Shutdown Window
	 * 
	 * 		
	 */
	public void Shutdown() {
		OnShutdown();
		Renderer.Cleanup();
		if (window != null) {
			window.Shutdown();
		}
	}
	
	/* 
	 * 	GetWindow() - returns window object
	 * 		
	 */
	public Window GetWindow() {
		return window;
	}
	/*
	 * To be overridden by child class
	 * 	
	 */
	protected void OnUpdate(float deltaTime) {};
	/*
	 * To be overridden by child class
	 * 	
	 */
	protected void OnShutdown() {};
	
	

}
