package engine;

import org.joml.Vector3f;
import org.joml.Vector4f;

import engine.Events.Event;
import engine.Events.KeyEvent;
import engine.Events.MouseButtonEvent;
import engine.renderer.Renderer;
import engine.renderer.Shader;
import engine.renderer.Texture;
import engine.renderer.Transform;
import engine.renderer.GUI.GUIQuad_Draggable;
import engine.renderer.GUI.GUIText;
import engine.renderer.text.FontType;

public class Debugger {

	private static GUIQuad_Draggable debugMenu;
	
	private static boolean DisplayMenu = true;
	public static final int MenuKeyCode = KeyCodes.KEY_GRAVE_ACCENT; // ~
	
	private static float xPos = .70f, yPos = .4f;
	
	private static int keyPressed = -1;
	private static int mousePressed = -1;

	private static Vector4f quadHoverColor = new Vector4f(.25f,.25f,.25f, .8f);
	private static Vector4f quadColor = new Vector4f(0.1f,0.1f,0.1f,.8f);
	
	// Initialize by creating Menus
	public static void Init() {
		ShowMenu(DisplayMenu);
	}
	
	// Add / Remove Menu based on show/don't show
	public static void ShowMenu(boolean b) {
		DisplayMenu = b;
		if (b) {
			CreateMenu();
			debugMenu.Add();
		}else {
			debugMenu.Remove();
		}
	}
	
	// Update the text
	public static void Update() {
		// If we are shown
		if (DisplayMenu) {
		int[] gpu = Application.GetWindow().GetGpuUsage();
		((GUIText)debugMenu.GetChild("DebugText")).SetText(
			    "Debug Menu: (Drag to move or Press ~ to toggle) \n \n" +
				"App Info       " + "Title: "+ Application.app.title +" | " + Application.app.fps + " FPS"+
				"\n                   VSync: " + Application.app.vsync +" | Width: " + Application.app.window.GetWidth() +
				"\n                   Height: " +Application.app.window.GetHeight() + " | Paused: " + Application.IsPaused() +
				
				"\n\nRender Info  " +" Texture Pool: " + Texture.GetPoolSize() + " | Shader Pool: " + Shader.GetPoolSize() +
				"\n                    Font Pool: " + FontType.GetPoolSize() +" | Atlas Pool: " + TextureAtlas.GetCount() +
				"\n                    Vertex Arrays: " + Renderer.GetVertexArrayCount() + " | Buffers: " + Renderer.GetBufferCount() +  
						
				"\n\nI/O Info        MouseX: " + (int)Input.GetMouseX() +" | MouseY: " + (int)Input.GetMouseY() +
				"\n                    Key Input: " + keyPressed + " | Mouse Input: " + mousePressed +
				
				"\n\nScene Info     Scene Count: " + SceneManager.GetSceneCount() +
				"\n                    Scene: " + ( SceneManager.GetCurrentScene() != null ? SceneManager.GetCurrentScene().GetName() : "NULL") + 
				"\n                    Actor Count: " + (SceneManager.GetCurrentScene() != null ? SceneManager.GetCurrentScene().GetActorCount() : "0") +
				
				"\n\nCamera Info   Position: " + (float) (Math.round(SceneManager.GetCurrentScene().GetCameraController().Position.x  * 100.0) / 100.0)+ "," + 
				(Math.round(SceneManager.GetCurrentScene().GetCameraController().Position.y  * 100.0) / 100.0)+
				 "," +(Math.round(SceneManager.GetCurrentScene().GetCameraController().Position.z  * 100.0) / 100.0) +
				
				"\n                     Zoom: " + (float) (Math.round(( (CameraController.Orthographic)SceneManager.GetCurrentScene().GetCameraController()).GetZoomLevel()  * 100.0) / 100.0)  + 
				"\n                     Rotation: " + (float) (Math.round(( (CameraController.Orthographic)SceneManager.GetCurrentScene().GetCameraController()).rotation * 100.0) / 100.0)  +
				
				"\n\nHardware       Logical VM Processors: " + Runtime.getRuntime().availableProcessors() +
				"\n                     GPU Memory: " + gpu[2] + "mb/" + gpu[0] + "mb" +
				
				
				"\n\nMemory Info   Usage: " + Math.round( (Runtime.getRuntime().totalMemory()- Runtime.getRuntime().freeMemory())/1e6 ) +"mb"  +
				"\n                     Alloc: "+Math.round(Runtime.getRuntime().freeMemory()/1e6) +"mb/"+(Math.round(Runtime.getRuntime().totalMemory()/1e6)) + "mb "
				

				
				);
		
		}
		
		// Reset key inputs
		if (keyPressed != -1 && !Input.IsKeyPressed(keyPressed)) {
			keyPressed = -1;
		}
		// Reset mouse inputs
		if (mousePressed != -1 && !Input.IsMouseButtonPressed(mousePressed)) {
			mousePressed = -1;
		}
		
		
	}
	
	// Capture events
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
				ShowMenu(!DisplayMenu);
			}
		}
		
		return false;
	}
	
	public static void ShutDown() {
		debugMenu.Remove();
	}
	
	// Create menu
	private static void CreateMenu() {
		
		// Create draggable GUI Text Quad
		debugMenu = (GUIQuad_Draggable) new GUIQuad_Draggable("DraggableQuad",new Transform( 
				new Vector3f(xPos,yPos-WindowFrame.GetTop(), 1e6f), // Position x,y, Z-Order higher is on top
				new Vector3f(0f, 0f,0f),  // Rotation
				new Vector3f(.225f,.525f,1f)), // Scale x,y,z
				"Images/blankTexture.png",  // Quad Texture path
				quadColor // Quad Color r,g,b,a
		) {
			@Override
			protected void OnMouseEnter() {
				SelectGUI();
			}
			
			@Override
			protected void OnSelect() {
				
			}
			
			@Override
			protected void OnDrag() {
				xPos = nXPos;
				yPos = nYPos;
			}
			
			@Override
			protected void OnDragStart() {
				SetColor(quadHoverColor);
			}
			
			@Override
			public void OnDragEnd() {
				SetColor(quadColor);
			}
			
			@Override
			protected void OnMouseExit() {
				DeselectGUI();
			}
			
			@Override
			public void OnDeselect() {
				StopDragging();
			}
			
		}.AddChild(new GUIText( 
				"DebugText",
				new Transform(
					new Vector3f(0f,0f,1f)
				), // Font Offset (used to center text if needed) 
				"Fonts/verdana",  // Font path
				"", // Font String
				new Vector4f(.95f,.95f,.95f,1f), // Font color r,g,b,a
				.2f, // Text Line Width ( how wide each line will be can use \n in string for new line)
				.6f, // Font Size
				false // Center Text
				));
	}
	
}
