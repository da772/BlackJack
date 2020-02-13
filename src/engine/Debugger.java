package engine;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import engine.Events.Event;
import engine.Events.KeyEvent;
import engine.Events.MouseButtonEvent;
import renderer.GUIRenderer;
import renderer.Renderer;
import renderer.Shader;
import renderer.Texture;
import renderer.Transform;
import renderer.GUIPrimitives.GUITextQuad;
import renderer.text.FontType;
import util.MathLib;

public class Debugger {

	private static GUITextQuad debugMenu;
	
	private static boolean DisplayMenu = true;
	public static final int MenuKeyCode = KeyCodes.KEY_GRAVE_ACCENT; // ~
	
	private static final float xPos = .775f, yPos = .7f;
	
	private static int keyPressed = -1;
	private static int mousePressed = -1;
	
	private static Vector4f quadHoverColor = new Vector4f(.25f,.25f,.25f, .8f);
	private static Vector4f quadColor = new Vector4f(0.1f,0.1f,0.1f,.8f);
	
	
	public static void Init() {
		CreateMenu();
		ShowMenu(DisplayMenu);
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
		debugMenu.SetText(
			    "Debug Menu:\n \n" +
				"App Info       " + "Title: "+ Application.app.title +" | " + Application.app.fps + " FPS"+
				"\n                   VSync: " + Application.app.vsync +" | Width: " + Application.app.window.GetWidth() +
				"\n                   Height: " +Application.app.window.GetHeight() +
				
				"\n\nRender Info  " +" Texture Pool: " + Texture.GetPoolSize() + " | Shader Pool: " + Shader.GetPoolSize() +
				"\n                    Font Pool: " + FontType.GetPoolSize() +" | Buffers: " + Renderer.GetBufferCount() + 
				"\n                    Vertex Arrays: " + Renderer.GetVertexArrayCount()+
				
				
				"\n\nMemory Info  Usage: " + Math.round( (Runtime.getRuntime().totalMemory()- Runtime.getRuntime().freeMemory())/1e6 ) +"mb"  +
				"\n                    Alloc: "+Math.round(Runtime.getRuntime().freeMemory()/1e6) +"mb/"+(Math.round(Runtime.getRuntime().totalMemory()/1e6) + "mb " + 
				
						
				"\n\nI/O Info        MouseX: " + (int)Input.GetMouseX() +" | MouseY: " + (int)Input.GetMouseY() +
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
				debugMenu.DeselectGUI();
				debugMenu.SetMouseExit();
				ShowMenu(!DisplayMenu);
			}
		}
		
		return false;
	}
	
	public static void ShutDown() {
		debugMenu.Remove();
	}
	
	private static void CreateMenu() {
		debugMenu = new GUITextQuad(new Transform( 
				new Vector3f(xPos,yPos,10000.f), // Position x,y, Z-Order higher is on top
				new Vector3f(0f, 0f,0f),  // Rotation
				new Vector3f(.225f,.3f,1f)), // Scale x,y,z
				"Images/blankTexture.png",  // Quad Texture path
				quadColor, // Quad Color r,g,b,a
				new Vector2f(.9f, -.72f), // Font Offset (used to center text if needed) 
				"Fonts/verdana",  // Font path
				"", // Font String
				new Vector4f(.95f,.95f,.95f,1f), // Font color r,g,b,a
				.2f, // Text Line Width ( how wide each line will be can use \n in string for new line)
				.6f, // Font Size
				false, // Center Text
				false // Auto expand width to match quad
		) {
			
			boolean isDragging = false;
			
			@Override
			protected void OnMouseEnter() {
				SelectGUI();
			}
			
			@Override
			protected void OnSelect() {
				
			}
			
			protected void StartDragging() {
				isDragging = true;
				SetQuadColor(quadHoverColor);
				BeginDrag(Input.GetMouseX(), Input.GetMouseY());
			}
			
			public void StopDragging() {
				isDragging = false;
				SetQuadColor(quadColor);
			}
			
			@Override
			protected void OnMouseExit() {
				DeselectGUI();
			}
			
			@Override
			public void OnDeselect() {
				StopDragging();
			}
			
			@Override
			public void SelectedOnEvent(Event e) {
				if (e instanceof Events.MouseButtonPressedEvent) {
					if (((Events.MouseButtonEvent)e).GetKeyCode() == KeyCodes.MOUSE_LEFT) {
						if (!isDragging) StartDragging();	
					}
				}
				
				if (e instanceof Events.MouseButtonReleasedEvent) {
					if (((Events.MouseButtonEvent)e).GetKeyCode() == KeyCodes.MOUSE_LEFT) {
						if (isDragging) StopDragging();
					}
				}
				
				if (e instanceof Events.MouseMovedEvent) {
					if ( !MathLib.InBounds( ((Events.MouseMovedEvent)e).GetMouseX(), 0f, (float)GUIRenderer.GetWidth())
						|| !MathLib.InBounds( ((Events.MouseMovedEvent)e).GetMouseY(), 0f, (float)GUIRenderer.GetWidth()))
					{
						DeselectGUI();
						return;
					}
					if (isDragging) {
						Drag( ((Events.MouseMovedEvent)e).GetMouseX(),
						((Events.MouseMovedEvent)e).GetMouseY());
					}
				}
			}
		};
	
	}
	
}
