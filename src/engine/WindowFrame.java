package engine;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import engine.Events.Event;
import engine.renderer.Renderer;
import engine.renderer.Shader;
import engine.renderer.Transform;
import engine.renderer.GUI.GUI;
import engine.renderer.GUI.GUIButton;
import engine.renderer.GUI.GUIQuad;
import engine.renderer.GUI.GUIQuad_Draggable;
import engine.renderer.GUI.GUIText;
import engine.util.Timing;

public class WindowFrame {

	static GUI tbar, resizeTab, text, screen, meshScreen;
	static final float top = 0f;//.035f;
	static final float left = 0f;//.0175f/2f;
	static final float barButtonSize = .9f;
	static boolean fullScreen = false;
	static boolean resizeTabHidden = false;
	static final String fontType = "Fonts/Segoe";
	static final float titleFontSize = .8f;
	static final Vector4f borderColor = new Vector4f(.15f,.15f,.15f,1f);
	static final Transform fullScreenTransform = new Transform(
			new Vector3f(0f, 0, 1e6f),
			new Vector3f(0f),
			new Vector3f(1f, 1f, 1f)
			);
	static final Transform minScreenTransform = new Transform(
			new Vector3f(0f, -top, 1e6f),
			new Vector3f(0f),
			new Vector3f(1f, 1f-top, 1f)
			);
	
	public static void Init() {
		fullScreen = Application.GetWindow().IsFullScreen();
		//CreateTitleBar();
		CreateResizeTab();
		CreateScreen();
		
		resizeTab.Add();
		//tbar.Add();
		screen.Add();
		meshScreen.Add();
	}
	

	public static float GetTop() {
		return fullScreen ? 0f : top;
	}
	
	public static float GetLeft() {
		return fullScreen ? 0f : left;
	}
	
	public static void OnEvent(Event e) {
		if (e instanceof Events.WindowSetTitleEvent) {
			//((GUIText)text).SetText(((Events.WindowSetTitleEvent)e).GetTitle());
		}
		
		if (e instanceof Events.WindowFullScreenEvent) {
			Events.WindowFullScreenEvent _e = ((Events.WindowFullScreenEvent)e);
			fullScreen = _e.IsFullScreen();
			if (fullScreen) {
			//	resizeTab.Remove();
			//	tbar.Remove();
			//	screen.SetTransform(fullScreenTransform);
			} else {
			//	resizeTab.Add();
			//	tbar.Add();
			//	screen.SetTransform(minScreenTransform);
			}
		}
		
		
	}
	
	public static void Shutdown() {
		//tbar.Remove();
		//resizeTab.Remove();
	}
	
	
	public static void SetMeshShader(String[] shaders) {
		meshScreen.SetShader(Shader.Create(shaders));
	}
	
	public static void SetScreenShader(String[] shaders) {
		screen.SetShader(Shader.Create(shaders));
	}
	
	
	private static void CreateScreen() {
		meshScreen = new GUIQuad("MeshScreen", new Transform(
				new Vector3f(0, 0, 0f),
				new Vector3f(0f),
				new Vector3f(1f, 1f, 1f)
				), 
				Renderer.GetMeshTexture().GetFileName(),
				new Vector4f(1f),
				new Vector2f(1f),
				ShaderLib.Shader_GUIQuad) {
			
			
			@Override
			public void Bind() {
				super.Bind();
				shader.UploadUniformFloat("time", Application.GetWindow().GetTime());
			}
			
			
		};
		meshScreen.SetWindowElement(true);
		meshScreen.SetMeshScreen(true);
		meshScreen.SetGUICollision(false);
		
		screen = new GUIQuad("Screen", fullScreen ? fullScreenTransform : minScreenTransform , 
				Renderer.GetGUITexture().GetFileName(),
				new Vector4f(1f),
				new Vector2f(1f),
				ShaderLib.Shader_GUIQuad) {
			
			@Override
			public void Bind() {
				super.Bind();
				shader.UploadUniformFloat("time", Application.GetWindow().GetTime());
			}
			
		};
		screen.SetWindowElement(true);
		screen.SetGUICollision(false);
		
	}
	
	private static void CreateTitleBar() {
		
		tbar = new GUIQuad_Draggable(
				"Titlebar",new Transform( 
						new Vector3f(0, 1-top-left, 1e6f), // Position x,y, Z-Order higher is on top
						new Vector3f(0f, 0f,0f),  // Rotation
						new Vector3f(1f-left, top-left,1f)), // Scale x,y,z
				"Images/blankTexture.png",  // Quad Texture path
				borderColor // Quad Color r,g,b,a
		) {
			@Override
			public GUI AddChild(GUI g) { 
				super.AddChild(g);
				g.SetWindowElement(true);
				return this;
			}
			
			@Override
			public GUI RemoveChild(GUI g) { 
				super.RemoveChild(g);
				g.isWindow = false;
				return this;
			}
			
			private double lastTime = 0;
			@Override
			protected void OnMouseEnter() {
				SelectGUI();
			}
			
			@Override
			protected void OnSelect() {
			}
			
			@Override
			protected void OnDragEnd() {
				
			}
			
			@Override
			protected void OnMouseUp() {
				double nt = Timing.getTimeMS();
				if (lastTime == 0) {
					lastTime = Timing.getTimeMS();
					return;
				}
				
				if (nt - lastTime <= 300 && !isDragging) {
					Application.GetWindow().SetFullScreen(!Application.GetWindow().IsFullScreen());
				}
				lastTime = nt;
			}
			
			
			@Override
			protected void OnMouseExit() {
				DeselectGUI();
			}
			
			@Override
			protected void _Drag(float x, float y) {
				int[] w_pos = Application.GetWindow().GetWindowPosition();
				if (!Application.GetWindow().IsFullScreen()) { 
				Application.GetWindow().SetWindowLocation( 
						(int)(w_pos[0] + (x-dragPos.x)),
						(int)(w_pos[1] + (y-dragPos.y)) 
								);
				} else {
					Application.GetWindow().SetFullScreen(false);
				}
			}
			
			@Override
			public void OnDeselect() {
				StopDragging();
			}
		};
		
		tbar.AddChild(new GUIText("titleBarText",
				new Transform(
						new Vector3f(.05f,left,1f)),
				fontType,
				"TitleBar",
				new Vector4f(1f),
				1f,
				titleFontSize,
				false
				));
		tbar.AddChild(
		new GUIButton(
		"MinimizeButton",new Transform( 
		new Vector3f(.875f,left,.1f), // Position x,y, Z-Order higher is on top
		new Vector3f(0f, 0f,0f),  // Rotation
		new Vector3f(.025f,top,1f)), // Scale x,y,z
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
				Application.GetWindow().MinimizeWindow();
			}
			@Override
			public void OnDeselect() {
				SetButtonTexture(false);
				SetColor(.55f,.6f,.075f,0f);
			}
		}.AddChild(new GUIText(
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
				)));
		
		
		tbar.AddChild(new GUIButton(
				"FullScreenButton",new Transform( 
		new Vector3f(.925f,left,.1f), // Position x,y, Z-Order higher is on top
		new Vector3f(0f, 0f,0f),  // Rotation
		new Vector3f(.025f,top,1f)), // Scale x,y,z
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
				Application.GetWindow().SetFullScreen(!Application.GetWindow().IsFullScreen());
			}
			@Override
			public void OnDeselect() {
				SetButtonTexture(false);
				SetColor(.55f,.6f,.075f,0f);
			}
		}.AddChild(new GUIText(
		"AttachText",
				new Transform(
						new Vector3f(0f,0f,.1f),
						new Vector3f(0f),
						new Vector3f(1f)
						),
				fontType,
				"[ ]",
				new Vector4f(1f),
				.2f,
				barButtonSize,
				true
				)));
		
		
		tbar.AddChild(new GUIButton(
		"CloseButton",new Transform( 
			new Vector3f(.975f,left,.1f), // Position x,y, Z-Order higher is on top
			new Vector3f(0f, 0f,0f),  // Rotation
			new Vector3f(.025f,top,1f)), // Scale x,y,z
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
				Application.CloseApplication();
			}
			@Override
			public void OnDeselect() {
				SetButtonTexture(false);
				SetColor(.95f,.01f,.075f,0f);
			}
		}.AddChild(new GUIText(
				"AttachText",
				new Transform(
						new Vector3f(0f,0f,.1f),
						new Vector3f(0f),
						new Vector3f(1f)
						),
				fontType,
				"X",
				new Vector4f(1f),
				.2f,
				barButtonSize,
				true
				)));
		
		tbar.isWindow = true;
		text = tbar.GetChild("titleBarText");
		
	};
	
	private static void CreateResizeTab() {
		
		resizeTab = new GUIQuad_Draggable(
				"resizeTab", new Transform( 
						new Vector3f(0,0, 1e5f), // Position x,y, Z-Order higher is on top
						new Vector3f(0f, 0f,0f),  // Rotation
						new Vector3f(1f,1f,1f)), 
				"Images/blankTexture.png", 
				borderColor
		) {
			@Override
			protected void OnMouseEnter() {
				SelectGUI();
			}
			
			@Override
			protected void OnSelect() {
				Application.GetWindow().SetCursor(Window.CursorType.VResizeCursor);
			}
			
			
			
			@Override
			protected void OnMouseExit() {
				DeselectGUI();
			}
			
			@Override
			protected void _Drag(float x, float y) {
				int[] w_pos = Application.GetWindow().GetWindowSize();
				if (!Application.GetWindow().IsFullScreen()) { 
				Application.GetWindow().SetWindowSize( 
						(int)(w_pos[0] + (x-dragPos.x)),
						(int)(w_pos[1] + (y-dragPos.y)) );
				dragPos.x = x;
				dragPos.y = y;
				} else {
					Application.GetWindow().SetFullScreen(false);
				}
			}
			
			@Override
			public void OnDeselect() {
				StopDragging();
				Application.GetWindow().SetCursor(Window.CursorType.ArrowCursor);
			}
			
			@Override
			public void SelectedOnEvent(Event e) {
				super.SelectedOnEvent(e);
				/*
				if (e instanceof Events.MouseMovedEvent) {
					if (((Events.MouseMovedEvent) e).GetMouseY() >= top*Application.GetWindow().GetFrameBuffers()[1]) {
						Application.GetWindow().SetCursor(Window.CursorType.VResizeCursor);
					} else if (((Events.MouseMovedEvent) e).GetMouseY() <= 
							Application.GetWindow().GetFrameBuffers()[1] - top*Application.GetWindow().GetFrameBuffers()[1]) {
						Application.GetWindow().SetCursor(Window.CursorType.VResizeCursor);
					}
				}
				*/
			}
			
			
		};
		resizeTab.SetWindowElement(true);
		resizeTab.SetCollision(false);
	}

	
}
