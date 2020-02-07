package application;


import org.joml.Matrix4f;
import org.joml.Vector3f;

import engine.Application;
import engine.Events;
import engine.Input;
import engine.KeyCodes;
import math.MatrixMath;
import renderer.Mesh;
import renderer.Renderer;
import renderer.Shader;
import renderer.ShaderLib;
import renderer.Texture;
import renderer.VertexArray;

public class BlackJack extends Application {

	Mesh mesh;
	
	Matrix4f transform;
	
	int count =1 ;
	
	public BlackJack() {
		// Call super - Set window Title to "Blackjack" set width to 1280 set height to 720
		super("BlackJack", 1280, 720);
	}
	
	@Override
	protected void OnInit() {
		System.out.println("Black Jack Init!");
		
		float[] vertices = {
				-.5f,  -.5f, 0f,  0.f,0.f, 
				 .5f, -.5f,  0f,  1.f, 0.f,
				 .5f,  .5f,  0f,  1.f, 1.f,
				 -.5f, .5f,  0f,  0.f, 1.f
				 
		};
		int[] indices = {
			0,1,2,
			2,3,0
		};
		mesh = new Mesh(vertices, new VertexArray.BufferElement[]{
				new VertexArray.BufferElement(VertexArray.ElementType.Float3, "u_Position"),
				new VertexArray.BufferElement(VertexArray.ElementType.Float2, "u_TexCoord") 
				},
		indices, new Shader(ShaderLib.Texture_PositionF3_CoordF2_V_Shader), new Texture("./res/2_of_diamonds.png"));

		
		Vector3f pos = new Vector3f(0.f,0.f,0.f);
		Vector3f scale = new Vector3f(1.f,1.f,1.f);
		transform = new Matrix4f();
		
		
		MatrixMath.createTransformMatrix(pos, new Vector3f(0,0,0), scale);
		
	}
	
	// Called every frame
	@Override
	protected void OnUpdate() {
		count++;
		
		Renderer.Draw(mesh, transform);
		
		if (Input.IsMouseButtonPressed(KeyCodes.MOUSE_RIGHT)) {
			//System.out.println("We are right clicking");
		}
		
		if (Input.IsKeyPressed(KeyCodes.KEY_D)) {
			//System.out.println("We are pressing the letter D");
		}
		
	}
	
	@Override
	protected boolean KeyEvent(Events.KeyEvent e) {
		
		
		return false;
	}
	
	// Called on application start
	
	
	// Called on application end
	@Override
	protected void OnShutdown() {
		System.out.println("Black Jack Shutdown!");	
	}
	
	
}
