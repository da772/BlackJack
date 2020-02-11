package engine;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import engine.Events.Event;
import engine.Events.KeyEvent;
import engine.Events.MouseButtonEvent;
import renderer.Renderer;
import renderer.Shader;
import renderer.Texture;
import renderer.Transform;
import renderer.GUIPrimitives.GUITextQuad;
import renderer.text.FontType;

public class Debugger {

	private static GUITextQuad debugMenu;
	
	private static boolean DisplayMenu = false;
	public static final int MenuKeyCode = KeyCodes.KEY_GRAVE_ACCENT; // ~
	
	private static final float xPos = .4f, yPos = .375f;
	
	private static int keyPressed = -1;
	private static int mousePressed = -1;
	
	public static void Init() {
		debugMenu = new GUITextQuad(new Transform( 
				new Vector3f(xPos,yPos,10000.f), 
				new Vector3f(0f, 0f,0f), 
				new Vector3f(.225f,.4f,1f)),
				"Images/blankTexture.png", new Vector4f(.1f,0.1f,0.1f,.8f) ,
				new Vector2f(.5f, -.39f), "Fonts/verdana",
				"",
				new Vector4f(.95f,.95f,.95f,1f),.2f, .6f, false, false
		);
	}
	
	public static void ShowMenu(boolean b) {
		DisplayMenu = b;
		if (b) {
			debugMenu.Add();
		}else {
			debugMenu.Remove();
		}
	}
	
	public static void Update() {
		if (DisplayMenu) {
		debugMenu.SetText( "Debug Menu:\n \n" +
				"App Info       " + "Title: "+ Application.app.title +
				 " | " + Application.app.fps + " FPS \n                   VSync: " + Application.app.vsync +
				" | Width: " + Application.app.window.GetWidth() + "\n                   Height: " +
				 Application.app.window.GetHeight() +
				"\n\nRender Info  " +
				" Texture Pool: " + Texture.GetPoolSize() + " | Shader Pool: " + Shader.GetPoolSize() +
				"\n                    Font Pool: " + FontType.GetPoolSize() +
				" | Buffers: " + Renderer.GetBufferCount() + "\n                    Vertex Arrays: " + Renderer.GetVertexArrayCount()+
				"\n\nMemory Info  Usage: " + Math.round( (Runtime.getRuntime().totalMemory()
				- Runtime.getRuntime().freeMemory())/1e6 ) +"mb"  +
				"\n                    Alloc: "+
				Math.round(Runtime.getRuntime().freeMemory()/1e6) +"mb/"
				+(Math.round(Runtime.getRuntime().totalMemory()/1e6) + "mb " + 
				"\n\nI/O Info        MouseX: " + (int)Input.GetMouseX() +
				" | MouseY: " + (int)Input.GetMouseY() +
				"\n                    Key Input: " + keyPressed + " | Mouse Input: " + mousePressed
					));
		}
		
		if (keyPressed != -1 && !Input.IsKeyPressed(keyPressed)) {
			keyPressed = -1;
		}
		
		if (mousePressed != -1 && !Input.IsMouseButtonPressed(mousePressed)) {
			mousePressed = -1;
		}
		
	}
	
	public static void OnEvent(Event event) {
		Events.EventDispatcher<Event> dispatcher = new Events.EventDispatcher<Event>(event);
		dispatcher.Dispatch(KeyEvent.GetGenericType(), new Events.EventFunction<Event>(event) {
			@Override public boolean run(Event t) { return KeyEvent((KeyEvent) t);} });
		
		dispatcher.Dispatch(MouseButtonEvent.GetGenericType(), new Events.EventFunction<Event>(event) {
			@Override public boolean run(Event t) { return MouseButtonEvent((MouseButtonEvent) t);  } });
	}
	
	public static boolean MouseButtonEvent(MouseButtonEvent e) {
		
		if (e instanceof Events.MouseButtonPressedEvent) {
			mousePressed = e.GetKeyCode();
		}
		
		
		return false;
	}
	
	public static boolean KeyEvent(KeyEvent e) {
		if (e instanceof Events.KeyPressedEvent) {
			keyPressed = e.GetKeyCode();
			if (e.GetKeyCode() == MenuKeyCode) {
				ShowMenu(!DisplayMenu);
			}
		}
		
		return false;
	}
	
	public static void ShutDown() {
		debugMenu.Remove();
	}
	
}
