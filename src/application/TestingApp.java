package application;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import engine.Application;
import engine.CameraController;
import engine.Events;
import engine.Input;
import engine.KeyCodes;
import engine.Events.Event;
import renderer.GUIRenderer;
import renderer.Mesh;
import renderer.Renderer;
import renderer.Shader;
import renderer.ShaderLib;
import renderer.Texture;
import renderer.Transform;
import renderer.VertexArray;
import renderer.GUIPrimitives.GUITextQuad;
import util.MathLib;

public class TestingApp extends Application {

	Mesh mesh;
	Mesh mesh2;
	Mesh background;

	Matrix4f transform;
	
	CameraController.OrthographicCameraController cam;
	
	GUITextQuad tQuad;
	
	float lastX =0, lastY = 0;
	
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
		
		
		tQuad = new GUITextQuad(new Transform(
				new Vector3f(0f, 0f, 0f),
				new Vector3f(0f),
				new Vector3f(.225f, .225f, 1f)
				),
				"Images/blankTexture.png",
				new Vector4f(.125f, .125f,.25f,.9f),
				new Vector2f(.5f, -.475f),
				"Fonts/verdana",
				"Click to Drag Me!",
				new Vector4f(1.f),
				.25f, 1.25f, true, true
				){
			@Override
			protected void OnMouseEnter() {
				
			}
			
			@Override
			protected void OnMouseClicked(int button) {
				if (button == KeyCodes.MOUSE_LEFT) {
					if (!IsSelected()) {
						SelectGUI();
					} else {
						DeselectGUI();
					}
				}
				
				if (button == KeyCodes.MOUSE_RIGHT) {
					System.out.println("Pos: " + GetPosition());
				}
			}
			
			@Override
			protected void OnSelect() {
				SetQuadColor(new Vector4f(.125f, .125f,.45f,.9f));
				BeginDrag(Input.GetMouseX(), Input.GetMouseY());
			}
			
			@Override
			protected void OnMouseExit() {
				if (IsSelected()) {
					DeselectGUI();
				}
			}
			
			@Override
			protected void OnDeselect() {
				SetQuadColor(new Vector4f(.125f, .125f,.25f,.9f));
			}
			
			@Override
			public void SelectedOnEvent(Event e) {
				if (e instanceof Events.MouseButtonPressedEvent) {
					if (((Events.MouseButtonEvent)e).GetKeyCode() == KeyCodes.MOUSE_LEFT
							&& !IsMouseOver()) {
						DeselectGUI();
					}
				}
				
				if (e instanceof Events.MouseButtonEvent) {
					if (((Events.MouseButtonEvent)e).GetKeyCode() == KeyCodes.MOUSE_LEFT) {
						DeselectGUI();	
					}
				
				}
				
				if (e instanceof Events.MouseMovedEvent) {
					if ( !MathLib.InBounds( ((Events.MouseMovedEvent)e).GetMouseX(), 0f, (float)GUIRenderer.GetWidth())
						|| !MathLib.InBounds( ((Events.MouseMovedEvent)e).GetMouseY(), 0f, (float)GUIRenderer.GetWidth()))
					{
						DeselectGUI();
						return;
					}
					
					Drag( ((Events.MouseMovedEvent)e).GetMouseX(),
					((Events.MouseMovedEvent)e).GetMouseY());
					}
				
				}
			
				
				
			
		};
		
		tQuad.Add();

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
		
		float moveSpeed = .1f;
		
		if (Input.IsKeyPressed(KeyCodes.KEY_LEFT)) {
			tQuad.SetPosition(tQuad.GetPosition().x - moveSpeed*deltaTime, tQuad.GetPosition().y
					, tQuad.GetPosition().z );
		}
		if (Input.IsKeyPressed(KeyCodes.KEY_RIGHT)) {
			tQuad.SetPosition(tQuad.GetPosition().x + moveSpeed*deltaTime, tQuad.GetPosition().y
					, tQuad.GetPosition().z );
		}
		if (Input.IsKeyPressed(KeyCodes.KEY_UP)) {
			tQuad.SetPosition(tQuad.GetPosition().x, tQuad.GetPosition().y + moveSpeed*deltaTime
					, tQuad.GetPosition().z );
		}
		if (Input.IsKeyPressed(KeyCodes.KEY_DOWN)) {
			tQuad.SetPosition(tQuad.GetPosition().x, tQuad.GetPosition().y - moveSpeed*deltaTime
					, tQuad.GetPosition().z );
		}
		
		
		if (Input.IsKeyPressed(KeyCodes.KEY_BACKSPACE)) {
		
		}
		
		if (Input.IsKeyPressed(KeyCodes.KEY_ENTER)) {
			
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
