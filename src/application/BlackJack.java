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
import util.MatrixMath;

public class BlackJack extends Application {

	Mesh mesh;
	Mesh background;
	Mesh.Hud hud, hud2;
	
	Matrix4f transform;
	
	CameraController.OrthographicCameraController cam;
	
	int count =1 ;
	
	public BlackJack() {
		// Call super - Set window Title to "Blackjack" set width to 1280 set height to 720
		super("BlackJack", 1280, 720);
		cam = new CameraController.OrthographicCameraController(16.f/9.f);
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
				},indices1, new Shader(ShaderLib.Texture_PositionF3_CoordF2_V_MVP_Shader), new Texture("Images/Checkerboard.png"),
				transform, cam.GetCamera());
		
		pos = new Vector3f(0.f,0.f,.01f);
		scale = new Vector3f(.75f,1.f,1.f);
		transform = MatrixMath.createTransformMatrix(pos, new Vector3f(0, 0, 0f), scale);
		mesh = new Mesh(vertices, new VertexArray.BufferElement[]{
				new VertexArray.BufferElement(VertexArray.ElementType.Float3, "u_Position"),
				new VertexArray.BufferElement(VertexArray.ElementType.Float2, "u_TexCoord") 
				},
		indices, new Shader(ShaderLib.Texture_PositionF3_CoordF2_V_MVP_Shader), new Texture("Images/Anchor.png"),
		transform, cam.GetCamera());
		
		pos = new Vector3f(0f,0.f,-1f);
		scale = new Vector3f(2f,2.f,1.f);
		transform = new Matrix4f();
		transform = MatrixMath.createTransformMatrix(pos, new Vector3f(0, 0, 0f), scale);
		
		hud = new Mesh.Hud(vertices1, new VertexArray.BufferElement[]{
				new VertexArray.BufferElement(VertexArray.ElementType.Float3, "u_Position"),
				new VertexArray.BufferElement(VertexArray.ElementType.Float2, "u_TexCoord") 
				},
		indices1, new Shader(ShaderLib.Texture_PositionF3_CoordF2_V_T_Shader), new Texture("Images/titleBar.png"),
		transform, cam.GetCamera());
		
		pos = new Vector3f(0.f,0.f,-.1f);
		scale = new Vector3f(1.f,1.f,1.f);
		transform = new Matrix4f();
		transform = MatrixMath.createTransformMatrix(pos, new Vector3f(0, 0, 0f), scale);
		
		hud2 = new Mesh.Hud(vertices1, new VertexArray.BufferElement[]{
				new VertexArray.BufferElement(VertexArray.ElementType.Float3, "u_Position"),
				new VertexArray.BufferElement(VertexArray.ElementType.Float2, "u_TexCoord") 
				},
		indices1, new Shader(ShaderLib.Texture_PositionF3_CoordF2_V_T_Shader), new Texture("Images/2_of_diamonds.png"),
		transform, cam.GetCamera());

		
		
	}
	
	// Called every frame
	@Override
	protected void OnUpdate(float timeStep) {
		count++;
		// Render by Z-Order (Not including HUD)
		Renderer.Draw(background);
		Renderer.Draw(mesh);
		//Renderer.Draw(hud2);
		Renderer.Draw(hud);
		
		
		


		
		
		if (Input.IsMouseButtonPressed(KeyCodes.MOUSE_RIGHT)) {
			//System.out.println("We are right clicking");
		}
		
		if (Input.IsKeyPressed(KeyCodes.KEY_D)) {
			cam.Position.x += 1 * timeStep;
		}
		if (Input.IsKeyPressed(KeyCodes.KEY_A)) {
			cam.Position.x -= 1 * timeStep;
		}
		if (Input.IsKeyPressed(KeyCodes.KEY_W)) {
			cam.Position.y += 1 * timeStep;
		}
		if (Input.IsKeyPressed(KeyCodes.KEY_S)) {
			cam.Position.y -= 1 * timeStep;
		}
		
		if (Input.IsKeyPressed(KeyCodes.KEY_Q)) {
			cam.rotation += 10 * timeStep;
		}
		
		if (Input.IsKeyPressed(KeyCodes.KEY_E)) {
			cam.rotation -= 10 * timeStep;
		}
		
		cam.OnUpdate(timeStep);
		
		
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
