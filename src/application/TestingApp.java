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

import util.MathLib;

public class TestingApp extends Application {

	Mesh mesh;
	Mesh mesh2;
	Mesh background;

	Matrix4f transform;
	
	CameraController.OrthographicCameraController cam;
		
	
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

	}
	
	
	// Called every frame
	@Override
	protected void OnUpdate(float deltaTime) {
		
		// Render by Z-Order (Not including HUD)
		Renderer.DrawIndexed(background);
		Renderer.DrawIndexed(mesh2);

		Renderer.DrawIndexed(mesh);
		
		
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
		System.out.println(title+ " Shutdown!");	
	}
	
	
}
