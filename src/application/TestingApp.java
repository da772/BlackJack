package application;

import org.joml.Matrix4f;

import org.joml.Vector3f;

import engine.Application;
import engine.CameraController;
import engine.Events;
import engine.Input;
import engine.KeyCodes;
import engine.Events.Event;
import renderer.Mesh;
import renderer.Renderer;
import renderer.Shader;
import renderer.ShaderLib;
import renderer.Texture;
import renderer.VertexArray;
import renderer.text.GUIText;
import renderer.text.TextBatcher;
import renderer.text.TextRenderer;
import util.MathLib;

public class TestingApp extends Application {

	Mesh mesh;
	Mesh mesh2;
	Mesh background;
	Mesh.Hud hud, hud2;
	
	Matrix4f transform;
	
	CameraController.OrthographicCameraController cam;
	
	
	TextBatcher fr;
	boolean toggleDebug = true, vsync = false;
	GUIText text, debugText;
	
	int count =1 ;
	
	public TestingApp() {
		// Call super - Set window Title to "Blackjack" set width to 1280 set height to 720
		super("BlackJack", 1280, 720);
		
	}
	
	@Override
	protected void OnInit() {
		System.out.println("Black Jack Init!");
		
		cam = new CameraController.OrthographicCameraController(16.f/9.f);
		window.SetVSync(vsync);
		
		float[] vertices = {
				-1.5f,  -.5f, 0f,  0.f,0.f, 
				 -.5f, -.5f,  0f,  1.f, 0.f,
				 -.5f,  .5f,  0f,  1.f, 1.f,
				 -1.5f, .5f,  0f,  0.f, 1.f,
				 
				 -1.5f + .75f,  -.5f, 0f,  0.f,0.f, 
				 -.5f +.75f, -.5f,  0f,  1.f, 0.f,
				 -.5f+.75f,  .5f,  0f,  1.f, 1.f,
				 -1.5f+.75f, .5f,  0f,  0.f, 1.f
		};
		int[] indices = {
			0,1,2,
			2,3,0,
			
			4,5,6,
			6,7,4
			
		};
		
		float[] vertices1 = {
				 -.5f,  -.5f, 0f,  0.f,0.f, 
				  .5f, -.5f,  0f,  1.f, 0.f,
				 .5f,  .5f,  0f,  1.f, 1.f,
				 -.5f, .5f,  0f,  0.f, 1.f,
		};
		int[] indices1 = {
				0,1,2,
				2,3,0,
		};
		
		float[] hudQuad = {
				-1f, -1f, 0, 0f, 0f,
				1f, -1f, 0f, 1f, 0f,
				1f, 1f, 0f, 1f, 1f,
				-1f, 1f, 0f, 0f, 1f
		};
		
		
		Vector3f pos = new Vector3f(0.f,0.f,0.0f);
		Vector3f scale = new Vector3f(1000f,1000.f,1.f);
		transform = new Matrix4f();
		transform = MathLib.createTransformMatrix(pos, new Vector3f(0, 0, 0f), scale);
		
		background = new Mesh(vertices1, new VertexArray.BufferElement[]{
				new VertexArray.BufferElement(VertexArray.ElementType.Float3, "u_Position"),
				new VertexArray.BufferElement(VertexArray.ElementType.Float2, "u_TexCoord") 
				}, indices1, Shader.Create(ShaderLib.Shader_U_Texture_ViewProj_UV_Transform_L_V3Pos_V2Coord),
				Texture.Create("Images/mosaico_multicolor_tile.jpg"),
				transform, cam.GetCamera());
		
		pos = new Vector3f(0.f,0.f,.2f);
		scale = new Vector3f(.75f,1.f,1.f);
		transform = MathLib.createTransformMatrix(pos, new Vector3f(0, 0, 0f), scale);
		mesh = new Mesh(vertices, new VertexArray.BufferElement[]{
				new VertexArray.BufferElement(VertexArray.ElementType.Float3, "u_Position"),
				new VertexArray.BufferElement(VertexArray.ElementType.Float2, "u_TexCoord") 
				},
		indices, Shader.Create(ShaderLib.Shader_U_Texture_ViewProj_Transform_L_V3Pos_V2Coord), 
		Texture.Create("Images/Anchor.png"),
		transform, cam.GetCamera());
		
		pos = new Vector3f(0.f,0.f,.1f);
		scale = new Vector3f(.75f,1.f,1.f);
		transform = MathLib.createTransformMatrix(pos, new Vector3f(0, 0, 0f), scale);
		mesh2 = new Mesh(vertices1, new VertexArray.BufferElement[]{
				new VertexArray.BufferElement(VertexArray.ElementType.Float3, "u_Position"),
				new VertexArray.BufferElement(VertexArray.ElementType.Float2, "u_TexCoord") 
				},
		indices1, Shader.Create(ShaderLib.Shader_U_Texture_ViewProj_Transform_L_V3Pos_V2Coord),
		Texture.Create("Images/2_of_diamonds.png"),
		transform, cam.GetCamera());
		
		pos = new Vector3f(0f,-.5f,0f);
		scale = new Vector3f(2f,3f,1.f);
		transform = new Matrix4f();
		transform = MathLib.createTransformMatrix(pos, new Vector3f(0, 0, 0f), scale);
		
		hud = new Mesh.Hud(vertices1, new VertexArray.BufferElement[]{
				new VertexArray.BufferElement(VertexArray.ElementType.Float3, "u_Position"),
				new VertexArray.BufferElement(VertexArray.ElementType.Float2, "u_TexCoord") 
				},
		indices1, Shader.Create(ShaderLib.Shader_U_Texture_ViewProj_UV2_Transform_L_V3Pos_V2Coord), Texture.Create("Images/titleBar.png"),
		transform, cam.GetCamera());
		
		
		float guiOffset = .75f;
		
		pos = new Vector3f(guiOffset,guiOffset,0f);
		scale = new Vector3f(.25f,.25f,1.f);
		transform = new Matrix4f();
		transform = MathLib.createTransformMatrix(pos, new Vector3f(0, 0, 0f), scale);
		hud2 = new Mesh.Hud(hudQuad, new VertexArray.BufferElement[]{
				new VertexArray.BufferElement(VertexArray.ElementType.Float3, "u_Position"),
				new VertexArray.BufferElement(VertexArray.ElementType.Float2, "u_TexCoord") 
				},
		indices1, Shader.Create(ShaderLib.Shader_U_Texture_Transform_L_Vec3Pos_Vec2Coord), Texture.Create("Images/debugPanel.png"),
		transform, cam.GetCamera());
		
		text = new GUIText(
				"a", // Text to display
				1.75f, // Font height
				"Fonts/verdana", // Font path without png or fnt
				 // Create transform
				new Vector3f(0f,0f,-1f), // Position (x, y,z)
				new Vector3f(0f,0f,0f),  // Rotation (x, y ,z)
				new Vector3f(1f,1f,1f),  // Scale (x, y, z)
				new Vector3f(0f,0f,0f), // Color (r, g, b)
				.1f, // Text Length 0-1 (Percentage of screen)
				false // Center Text   
				);
		
		
		debugText = new GUIText(
				"0000 Fps", // Text to display
				1f, // Font height
				"Fonts/verdana", // Font path without png or fnt
				 // Create transform
				new Vector3f(-.25f + guiOffset,.2f + guiOffset,-1f), // Position (x, y,z)
				new Vector3f(0f,0f,0f),  // Rotation (x, y ,z)
				new Vector3f(1f,1f,1f),  // Scale (x, y, z)
				new Vector3f(1f,0f,1f), // Color (r, g, b)
				.25f, // Text Length 0-1 (Percentage of screen)
				true // Center Text   
				);
		
		TextRenderer.init();
	
	}
	
	boolean flip = false ;
	
	// Called every frame
	@Override
	protected void OnUpdate(float deltaTime) {
		count++;
		
		// Render by Z-Order (Not including HUD)
		Renderer.DrawIndexed(background);
		Renderer.DrawIndexed(mesh2);
		Renderer.DrawIndexed(mesh);
		
		Renderer.SetDepth(false);
		if (toggleDebug) {
			Renderer.DrawIndexed(hud2);
		}
		Renderer.DrawIndexed(hud);
		Renderer.SetDepth(true);
		
		TextRenderer.render();
		
		float r = text.getColor().y;
		if (r >= 1) {
			flip = true;
		}
		if (r <= 0 ) {
			flip = false;
		}
		
		if (flip) {
			r -= 1 * deltaTime;
			
		} else {
			r+= 1 * deltaTime;
			
		}
		if (toggleDebug) {
		debugText.SetText(fps + " fps | VSync: " + vsync + "\nTexture Pool: " + Texture.GetPoolSize() + " | Shader Pool: " + Shader.GetPoolSize() + 
				"\nMouseX: " + (int)Input.GetMouseX() + " | MouseY: " + (int)Input.GetMouseY() );
		}
		text.setColor(1, r, 1);
		
		
		if (Input.IsMouseButtonPressed(KeyCodes.MOUSE_RIGHT)) {
			//System.out.println("We are right clicking");
		}
		
		if (Input.IsKeyPressed(KeyCodes.KEY_D)) {
			cam.Position.x += 1 * deltaTime;
		}
		if (Input.IsKeyPressed(KeyCodes.KEY_A)) {
			cam.Position.x -= 1 * deltaTime;
		}
		if (Input.IsKeyPressed(KeyCodes.KEY_W)) {
			cam.Position.y += 1 * deltaTime;
		}
		if (Input.IsKeyPressed(KeyCodes.KEY_S)) {
			cam.Position.y -= 1 * deltaTime;
		}
		
		if (Input.IsKeyPressed(KeyCodes.KEY_Q)) {
			cam.rotation += 10 * deltaTime;
		}
		
		if (Input.IsKeyPressed(KeyCodes.KEY_E)) {
			cam.rotation -= 10 * deltaTime;
		}
		
		if (Input.IsKeyPressed(KeyCodes.KEY_BACKSPACE)) {
			TextRenderer.removeText(text);
		}
		
		if (Input.IsKeyPressed(KeyCodes.KEY_ENTER)) {
			TextRenderer.loadText(text);
		}
		
		cam.OnUpdate(deltaTime);
		
		
		window.SetTitle("BlackJack - " + fps + " FPS - OpenGL" + window.GetGLInfo() );
		
	}
	
	protected void OnEvent(Event event) {
		cam.OnEvent(event);
	};
	
	@Override
	protected boolean KeyEvent(Events.KeyEvent e) {
	
		if (e instanceof Events.KeyPressedEvent) {
			Events.KeyPressedEvent ev = (Events.KeyPressedEvent) e;
			if (ev.GetKeyCode() == KeyCodes.KEY_G) {
				toggleDebug = !toggleDebug;
				
				if (toggleDebug == false) {
					debugText.remove();
				} else {
					debugText.add();
				}
				
			}
		}
		
		return false;
	}
	
	
	
	@Override
	protected boolean MouseScrolledEvent(Events.MouseScrolledEvent e) {
		cam.OnZoom(e.GetScrollX(), e.GetScrollY(), .1f);
		return false;
	}
	
	
	
	// Called on application start
	
	
	// Called on application end
	@Override
	protected void OnShutdown() {
		System.out.println("Black Jack Shutdown!");	
	}
	
	
}
