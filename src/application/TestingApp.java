package application;

import org.joml.Matrix4f;

import org.joml.Vector3f;
import org.joml.Vector4f;

import engine.Application;
import engine.CameraController;
import engine.Events;
import engine.Input;
import engine.KeyCodes;
import engine.Events.Event;
import renderer.GUI;
import renderer.GUIQuad;
import renderer.GUIRenderer;
import renderer.Mesh;
import renderer.Renderer;
import renderer.Shader;
import renderer.ShaderLib;
import renderer.Texture;
import renderer.VertexArray;
import renderer.text.FontType;
import renderer.text.GUIText;
import renderer.Transform;
import util.MathLib;

public class TestingApp extends Application {

	Mesh mesh;
	Mesh mesh2;
	Mesh background;
	Mesh.Hud hud, hud2;
	
	Matrix4f transform;
	
	CameraController.OrthographicCameraController cam;
	
	
	boolean toggleDebug = true, vsync = false;
	GUIText text, debugText;
	
	GUI h ;
	
	
	int count =1 ;
	
	public TestingApp() {
		// Call super - Set window Title to "Blackjack" set width to 1280 set height to 720
		super("Testing", 1280, 720);
		
	}
	
	@Override
	protected void OnInit() {
		System.out.println(title+" Init!");
		
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
		Texture.Create("Images/Cards/5D.png"),
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
				"Hello World! this is my text box i hope u like it or enjoy it", // Text to display
				2f, // Font height
				"Fonts/verdana", // Font path without png or fnt
				 // Create transform
				new Transform(
				new Vector3f(0f,.1f,2f), // Position (x, y,z)
				new Vector3f(0f,0f,0f),  // Rotation (x, y ,z)
				new Vector3f(1f,1f,1f)),  // Scale (x, y, z)
				new Vector4f(0f,0f,0f,1f), // Color (r, g, b)
				.5f, // Text Length 0-1 (Percentage of screen)
				true // Center Text   
				);

		
		
		
		debugText = new GUIText(
				"HELLO", // Text to display
				.9f, // Font height
				"Fonts/verdana", // Font path without png or fnt
				 // Create transform
				new Transform(
				new Vector3f(0f,0f,1.1f), // Position (x, y,z)
				new Vector3f(0f,0f,0f),  // Rotation (x, y ,z)
				new Vector3f(1f,1f,1f)),  // Scale (x, y, z)
				new Vector4f(1f,0f,1f, 1f), // Color (r, g, b, a)
				.2f, // Text Length 0-1 (Percentage of screen)
				true // Center Text   
				);
		
		
		
		h = new GUIQuad (
				new Transform( // Transform of the HUD
						new Vector3f(-.50f,0f,1.f), // Position of the hud -1 far left 1 far right 0 center
						new Vector3f(), // Rotation of the hud
						new Vector3f(.65f,.25f,1f)), // Scale of the hud
				"Images/debugPanel.png", // Texture of the hud
				new Vector4f(1.f,0.f,0.f,1.f) // Color of the hud
				);
		new GUIQuad (
				new Transform( // Transform of the HUD
						new Vector3f(.25f,0f,0f), // Position of the hud -1 far left 1 far right 0 center
						new Vector3f(), // Rotation of the hud
						new Vector3f(.25f,.25f,1f)), // Scale of the hud
				"Images/debugPanel.png", // Texture of the hud
				new Vector4f(0.f,1.f,0.f,1.f) // Color of the hud
				);
		
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
		
		
		GUIRenderer.Render();
		//TextRenderer.Render();
		
		float r = 0;//text.GetColor().y;
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
		debugText.SetText(fps + " fps | VSync: " + vsync +
		"\nWidth: " + window.GetWidth() + " | Height: " + window.GetHeight() +
		"\nMouseX: " + (int)Input.GetMouseX() + " | MouseY: " + (int)Input.GetMouseY()  +
		"\nTexture Pool: " + Texture.GetPoolSize() + " | Shader Pool: " + Shader.GetPoolSize() +
		"\nFont Pool: " + FontType.GetPoolSize() +
				"\nMemory Usage: " + Math.round( (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory())/1e6 ) +"mb"  +
				"\nMemory Alloc: "+ Math.round(Runtime.getRuntime().freeMemory()/1e6) +"mb/" +(Math.round(Runtime.getRuntime().totalMemory()/1e6) + "mb "
				));
		}
		text.SetColor(1f, r, 1f, 1f);
		
		
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
		
		if (Input.IsKeyPressed(KeyCodes.KEY_LEFT)) {
			h.SetPosition(new Vector3f(h.GetTransform().GetPosition().x - .1f*deltaTime, h.GetTransform().GetPosition().y, h.GetTransform().GetPosition().z ));
		}
		
		if (Input.IsKeyPressed(KeyCodes.KEY_RIGHT)) {
			h.SetPosition(new Vector3f(h.GetTransform().GetPosition().x + .1f*deltaTime , h.GetTransform().GetPosition().y, (h.GetTransform().GetPosition().z )));
		}
		
		if (Input.IsKeyPressed(KeyCodes.KEY_UP)) {
			h.SetPosition(new Vector3f(h.GetTransform().GetPosition().x , h.GetTransform().GetPosition().y + .1f*deltaTime , (h.GetTransform().GetPosition().z )));
		}
		
		if (Input.IsKeyPressed(KeyCodes.KEY_DOWN)) {
			h.SetPosition(new Vector3f(h.GetTransform().GetPosition().x , h.GetTransform().GetPosition().y - .1f*deltaTime , (h.GetTransform().GetPosition().z )));
		}
		
		if (Input.IsKeyPressed(KeyCodes.KEY_T)) {
			h.SetScale(new Vector3f(h.GetTransform().GetScale().x - .1f*deltaTime, h.GetTransform().GetScale().y - .1f*deltaTime, 1f));
		}
		
		if (Input.IsKeyPressed(KeyCodes.KEY_R)) {
			h.SetScale(new Vector3f(h.GetTransform().GetScale().x + .1f*deltaTime, h.GetTransform().GetScale().y + .1f*deltaTime, 1f));
		}
		
		if (Input.IsKeyPressed(KeyCodes.KEY_BACKSPACE)) {
			GUIRenderer.Remove(text);
		}
		
		if (Input.IsKeyPressed(KeyCodes.KEY_ENTER)) {
			//TextRenderer.loadText(text);
		}
		
		cam.OnUpdate(deltaTime);
		
		
		window.SetTitle(title+" - " + fps + " FPS - OpenGL" + window.GetGLInfo() );
		
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
