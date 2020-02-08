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
import renderer.font.TextBatcher;
import renderer.font.GUIText;
import renderer.font.TextRenderer;
import util.MatrixMath;

public class BlackJack extends Application {

	Mesh mesh;
	Mesh background;
	Mesh.Hud hud, hud2;
	
	Matrix4f transform;
	
	CameraController.OrthographicCameraController cam;
	
	
	TextBatcher fr;
	
	GUIText text;
	
	int count =1 ;
	
	public BlackJack() {
		// Call super - Set window Title to "Blackjack" set width to 1280 set height to 720
		super("BlackJack", 1280, 720);
		cam = new CameraController.OrthographicCameraController(16.f/9.f);
		
		fpsCap = 60;
		
	}
	
	@Override
	protected void OnInit() {
		System.out.println("Black Jack Init!");
		
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
		
		
		Vector3f pos = new Vector3f(0.f,0.f,0.f);
		Vector3f scale = new Vector3f(1000f,1000.f,1.f);
		transform = new Matrix4f();
		transform = MatrixMath.createTransformMatrix(pos, new Vector3f(0, 0, 0f), scale);
		
		background = new Mesh(vertices1, new VertexArray.BufferElement[]{
				new VertexArray.BufferElement(VertexArray.ElementType.Float3, "u_Position"),
				new VertexArray.BufferElement(VertexArray.ElementType.Float2, "u_TexCoord") 
				},indices1, new Shader(ShaderLib.Shader_U_Texture_ViewProj_Transform_L_V3Pos_V2Coord), new Texture("Images/Checkerboard.png"),
				transform, cam.GetCamera());
		
		pos = new Vector3f(0.f,0.f,.01f);
		scale = new Vector3f(.75f,1.f,1.f);
		transform = MatrixMath.createTransformMatrix(pos, new Vector3f(0, 0, 0f), scale);
		mesh = new Mesh(vertices, new VertexArray.BufferElement[]{
				new VertexArray.BufferElement(VertexArray.ElementType.Float3, "u_Position"),
				new VertexArray.BufferElement(VertexArray.ElementType.Float2, "u_TexCoord") 
				},
		indices, new Shader(ShaderLib.Shader_U_Texture_ViewProj_Transform_L_V3Pos_V2Coord), new Texture("Images/Anchor.png"),
		transform, cam.GetCamera());
		
		pos = new Vector3f(0f,0.f,-1f);
		scale = new Vector3f(2f,2.f,1.f);
		transform = new Matrix4f();
		transform = MatrixMath.createTransformMatrix(pos, new Vector3f(0, 0, 0f), scale);
		
		hud = new Mesh.Hud(vertices1, new VertexArray.BufferElement[]{
				new VertexArray.BufferElement(VertexArray.ElementType.Float3, "u_Position"),
				new VertexArray.BufferElement(VertexArray.ElementType.Float2, "u_TexCoord") 
				},
		indices1, new Shader(ShaderLib.Shader_U_Texture_Transform_L_Vec3Pos_Vec2Coord), new Texture("Images/titleBar.png"),
		transform, cam.GetCamera());
		
		pos = new Vector3f(0.f,.3f,-.1f);
		scale = new Vector3f(1.f,1.f,1.f);
		transform = new Matrix4f();
		transform = MatrixMath.createTransformMatrix(pos, new Vector3f(0, 0, 0f), scale);
		
		hud2 = new Mesh.Hud(vertices1, new VertexArray.BufferElement[]{
				new VertexArray.BufferElement(VertexArray.ElementType.Float3, "u_Position"),
				new VertexArray.BufferElement(VertexArray.ElementType.Float2, "u_TexCoord") 
				},
		indices1, new Shader(ShaderLib.Shader_U_Texture_Transform_L_Vec3Pos_Vec2Coord), new Texture("Images/2_of_diamonds.png"),
		transform, cam.GetCamera());
		
		
		
		text = new GUIText(
				"The quick brown fox jumped over the lazy dog0123456789", // Text to display
				2f, // Font height
				"Fonts/verdana", // Font path without png or fnt
				MatrixMath.createTransformMatrix( // Create transform
				new Vector3f(.5f,0f,-1f), // Position (x, y,z)
				new Vector3f(0f,0f,0f),  // Rotation (x, y ,z)
				new Vector3f(1f,1f,1f)),  // Scale (x, y, z)
				new Vector3f(0.f,0.f,1.f), // Color (r, g, b)
				.5f, // Text Length 0-1 (Percentage of screen)
				true // Center Text   
				);
		
		TextRenderer.init();

	}
	
	// Called every frame
	@Override
	protected void OnUpdate(float deltaTime) {
		count++;
		
		// Render by Z-Order (Not including HUD)
		Renderer.DrawIndexed(background);
		Renderer.DrawIndexed(mesh);
		Renderer.DrawIndexed(hud2);
		Renderer.DrawIndexed(hud);
		TextRenderer.render();
		
		
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
