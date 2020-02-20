package engine;

import org.joml.Vector3f;
import org.joml.Vector4f;

import engine.Events.Event;
import renderer.Transform;
import renderer.GUI.GUI;
import renderer.GUI.GUIButton;
import renderer.GUI.GUIQuad_Draggable;
import renderer.GUI.GUIText;

public class TitleBar {

	static GUI tbar, resizeTab, text;
	static float height = .035f;
	static float barButtonSize = .9f;
	static boolean resizeTabHidden = false;
	
	public static void Init() {
		CreateTitleBar();
		CreateResizeTab();
		
		tbar.Add();
		resizeTab.Add();
	}
	
	public static float GetHeight() {
		return height;
	}
	
	public static void OnEvent(Event e) {
		if (e instanceof Events.WindowSetTitleEvent) {
			((GUIText)text).SetText(((Events.WindowSetTitleEvent)e).GetTitle());
		}
		
		if (e instanceof Events.WindowFullScreenEvent) {
			Events.WindowFullScreenEvent _e = ((Events.WindowFullScreenEvent)e);
			resizeTabHidden = _e.IsFullScreen();
			if (resizeTabHidden) {
				resizeTab.Remove();
			} else {
				resizeTab.Add();
			}
			
		}
	}
	
	public static void Shutdown() {
		tbar.Remove();
		resizeTab.Remove();
	}
	
	private static void CreateTitleBar() {
		
		tbar = new GUIQuad_Draggable(
				"Titlebar",new Transform( 
						new Vector3f(0,.965f, 1e6f), // Position x,y, Z-Order higher is on top
						new Vector3f(0f, 0f,0f),  // Rotation
						new Vector3f(1f, height,1f)), // Scale x,y,z
				"Images/blankTexture.png",  // Quad Texture path
				new Vector4f(.15f,.15f,.15f,1f) // Quad Color r,g,b,a
		) {
			@Override
			protected void OnMouseEnter() {
				SelectGUI();
			}
			
			@Override
			protected void OnSelect() {
				
			}
			
			
			@Override
			protected void OnMouseExit() {
				DeselectGUI();
			}
			
			@Override
			protected void _Drag(float x, float y) {
				int[] w_pos = Application.app.GetWindow().GetWindowPosition();
				if (!Application.app.GetWindow().IsFullScreen()) { 
				Application.app.GetWindow().SetWindowLocation( 
						(int)(w_pos[0] + (x-dragPos.x)),
						(int)(w_pos[1] + (y-dragPos.y)) 
								);
				} else {
					Application.app.GetWindow().SetFullScreen(false);
				}
			}
			
			@Override
			public void OnDeselect() {
				StopDragging();
			}
			
		}.AddChild(new GUIText("titleBarText",
				new Transform(
						new Vector3f(.05f,0f,1f)),
				"Fonts/verdana",
				"TitleBar",
				new Vector4f(1f),
				1f,
				.6f,
				false
				)).AddChild(
		new GUIButton(
		"MinimizeButton",new Transform( 
		new Vector3f(.875f,0,.1f), // Position x,y, Z-Order higher is on top
		new Vector3f(0f, 0f,0f),  // Rotation
		new Vector3f(.025f,height,1f)), // Scale x,y,z
		"Images/blankTexture.png",  // Button texture
		"Images/blankTexture.png", // Button pressed texture
		new Vector4f(.55f,.6f,.075f,0f) // Quad Color r,g,b,a
		
		) {
			@Override
			protected void OnSelect() {
				SetColor(.4f,.4f,.4f,1f);
			}
			@Override
			protected void OnMousePressed() {
				SetButtonTexture(true);
			}
			@Override
			protected void OnMouseReleased() {
				SetButtonTexture(false);
				Application.app.GetWindow().MinimizeWindow();
			}
			@Override
			public void OnDeselect() {
				SetButtonTexture(false);
				SetColor(.55f,.6f,.075f,0f);
			}
		}.
		AddChild(new GUIText(
				"AttachText",
				new Transform(
						new Vector3f(0f,0f,.1f),
						new Vector3f(0f),
						new Vector3f(1f)
						),
				"Fonts/verdana",
				"-",
				new Vector4f(1f),
				.2f,
				barButtonSize,
				true
				)))
		.AddChild(new GUIButton(
				"FullScreenButton",new Transform( 
		new Vector3f(.925f,0,.1f), // Position x,y, Z-Order higher is on top
		new Vector3f(0f, 0f,0f),  // Rotation
		new Vector3f(.025f,height,1f)), // Scale x,y,z
		"Images/blankTexture.png",  // Button texture
		"Images/blankTexture.png", // Button pressed texture
		new Vector4f(.55f,.6f,.075f,0f) // Quad Color r,g,b,a
		
		){
			@Override
			protected void OnSelect() {
				SetColor(.4f,.4f,.4f,1f);
			}
			@Override
			protected void OnMousePressed() {
				SetButtonTexture(true);
			}
			@Override
			protected void OnMouseReleased() {
				SetButtonTexture(false);
				Application.app.GetWindow().SetFullScreen(!Application.app.GetWindow().IsFullScreen());
			}
			@Override
			public void OnDeselect() {
				SetButtonTexture(false);
				SetColor(.55f,.6f,.075f,0f);
			}
		}
		.AddChild(new GUIText(
		"AttachText",
				new Transform(
						new Vector3f(0f,0f,.1f),
						new Vector3f(0f),
						new Vector3f(1f)
						),
				"Fonts/BebasNeue",
				"[]",
				new Vector4f(1f),
				.2f,
				barButtonSize,
				true
				)))
		.AddChild(new GUIButton(
		"CloseButton",new Transform( 
			new Vector3f(.975f,0,.1f), // Position x,y, Z-Order higher is on top
			new Vector3f(0f, 0f,0f),  // Rotation
			new Vector3f(.025f,height,1f)), // Scale x,y,z
			"Images/blankTexture.png",  // Button texture
			"Images/blankTexture.png", // Button pressed texture
			new Vector4f(.55f,.6f,.075f,0f) // Quad Color r,g,b,a
		
		) {;
			@Override
			protected void OnSelect() {
				SetColor(.9f,.1f,.3f,1f);
			}
			@Override
			protected void OnMousePressed() {
				SetButtonTexture(true);
			}
			@Override
			protected void OnMouseReleased() {
				SetButtonTexture(false);
				Application.app.CloseApplication();
			}
			@Override
			public void OnDeselect() {
				SetButtonTexture(false);
				SetColor(.95f,.01f,.075f,0f);
			}
		}
		.AddChild(new GUIText(
				"AttachText",
				new Transform(
						new Vector3f(0f,0f,.1f),
						new Vector3f(0f),
						new Vector3f(1f)
						),
				"Fonts/verdana",
				"X",
				new Vector4f(1f),
				.2f,
				barButtonSize,
				true
				)));
		
		text = tbar.GetChild("titleBarText");
		
	};
	
private static void CreateResizeTab() {
		
		resizeTab = new GUIQuad_Draggable(
				"resizeTab", new Transform( 
						new Vector3f(.975f,-.965f, 1e6f), // Position x,y, Z-Order higher is on top
						new Vector3f(0f, 0f,0f),  // Rotation
						new Vector3f(.025f,height,1f)), 
				"Images/resizeIcon.png", 
				new Vector4f(1,1,1,.25f)
		) {
			@Override
			protected void OnMouseEnter() {
				SelectGUI();
			}
			
			@Override
			protected void OnSelect() {
				
			}
			
			
			@Override
			protected void OnMouseExit() {
				DeselectGUI();
			}
			
			@Override
			protected void _Drag(float x, float y) {
				int[] w_pos = Application.app.GetWindow().GetWindowSize();
				if (!Application.app.GetWindow().IsFullScreen()) { 
				Application.app.GetWindow().SetWindowSize( 
						(int)(w_pos[0] + (x-dragPos.x)),
						(int)(w_pos[1] + (y-dragPos.y)) );
				dragPos.x = x;
				dragPos.y = y;
				} else {
					Application.app.GetWindow().SetFullScreen(false);
				}
			}
			
			@Override
			public void OnDeselect() {
				StopDragging();
			}
			
		};
	}

	
}
