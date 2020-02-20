package application;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import engine.Actor;
import engine.Application;
import engine.CameraController;
import engine.Events;
import engine.Input;
import engine.KeyCodes;
import engine.Events.Event;
import renderer.GUIRenderer;
import renderer.Renderer;
import renderer.Transform;
import renderer.GUI.GUIText;
import renderer.GUI.GUIText_Draggable;
import renderer.mesh.Mesh2DBackground;
import renderer.mesh.Mesh2DQuad;
import engine.Scene;

public class TestScene extends Scene {

	private float rotationSpeed = 180f;
	
	public TestScene(String name, CameraController cam) {
		super(name, cam);
	}

	@Override
	public void OnUpdate(float deltaTime) {
		if (GetActor("card") != null) {
		((CardMesh)GetActor("card").GetComponent("Mesh")).SetRotation(((CardMesh)GetActor("card").GetComponent("Mesh")).GetRotation().x,
				((CardMesh)GetActor("card").GetComponent("Mesh")).GetRotation().y-rotationSpeed*deltaTime, ((CardMesh)GetActor("card").GetComponent("Mesh")).GetRotation().z+rotationSpeed*deltaTime );
		}
		if (GetActor("card4") != null) {
			((CardMesh)GetActor("card4").GetComponent("Mesh")).SetRotation(((CardMesh)GetActor("card4").GetComponent("Mesh")).GetRotation().x,
					((CardMesh)GetActor("card4").GetComponent("Mesh")).GetRotation().y+rotationSpeed*deltaTime, ((CardMesh)GetActor("card4").GetComponent("Mesh")).GetRotation().z-rotationSpeed*deltaTime );
		}
	}

	@Override
	public void OnBegin() {
		Renderer.SetClearColor(0f,1f,0f,1f);
		
		Actor.Create("card", this).AddComponent(new CardMesh("Mesh", 
				new Transform( // Card Transform
						new Vector3f(0, -.35f, 1f), // Position
						new Vector3f(0f, 0, 0), // Rotation
						new Vector3f(.75f, 1f, 1f) ), // Scale
				"AS", // Card front Suit
				"card_back_red", // Card back Suit
				this.cam.GetCamera()));// Camera))
		
		Actor.Create("card2", this).AddComponent(new CardMesh("Mesh", 
				new Transform( // Card Transform
						new Vector3f(0, .75f, 0), // Position
						new Vector3f(0f, 0, 0), // Rotation
						new Vector3f(.75f, 1f, 1f) ), // Scale
				"4H", // Card front Suit
				"card_back_red", // Card back Suit
				this.cam.GetCamera()));// Camera))
		
		
		Actor.Create("card3", this).AddComponent(new CardMesh("Mesh", 
				new Transform( // Card Transform
						new Vector3f(-.75f, -.35f, 0), // Position
						new Vector3f(0f, 0, 0), // Rotation
						new Vector3f(.75f, 1f, 1f) ), // Scale
				"JH", // Card front Suit
				"card_back_red", // Card back Suit
				this.cam.GetCamera()));// Camera))
		
		Actor.Create("card4", this).AddComponent(new CardMesh("Mesh", 
				new Transform( // Card Transform
						new Vector3f(-.75f, .75f, 1f), // Position
						new Vector3f(0f, 0, 0), // Rotation
						new Vector3f(.75f, 1f, 1f) ), // Scale
				"8C", // Card front Suit
				"card_back_red", // Card back Suit
				this.cam.GetCamera()));// Camera))
		/*
		Actor.Create("example", this).AddComponent(new Mesh2DQuad("quad", //Name
				new Transform( // Card Transform
				new Vector3f(2, 0, 0), // Position
				new Vector3f(0f, 0, 0f), // Rotation
				new Vector3f(3f, 3f, 1f) ),// Scale)
				"Atlas/cardAtlas.png",// Texture
				new Vector4f(1f), // Color
				this.cam.GetCamera())
				);
		*/
		Actor.Create("background", this).AddComponent(new Mesh2DBackground("background",
				new Transform(
						new Vector3f(0f,0,-1f),  // Position
						new Vector3f(0f), // Rotation
						new Vector3f(50000, 50000, 1f)), // Scale
				"Images/blankTexture.png", // Texture
				new Vector4f(0f,.5f,.15f,1f) , // Color
				new Vector2f(1f), // UV Scale
				this.cam.GetCamera() // Camera
				));

	 
	 
		Actor.Create("blackJackText", this).AddComponent(new GUIText_Draggable("textQuad",new Transform(
				new Vector3f(0, 0f, 0f), // Position
				new Vector3f(0f), // Rotation (buggy keep at 0)
				new Vector3f(.225f, .225f, 1f) // Quad Scale
				),
				"Images/blankTexture.png", // Texture
				new Vector4f(.125f, .125f,.25f,1f), // Quad Color
				new Vector2f(0f, 0f), // Text Position offset
				"Fonts/BebasNeue", // Text Font
				"Black Jack is a game of chance, but also a game of great skill!", // Text
				new Vector4f(1.f,1f,1f,1f), // Text Color
				.2f, // Textbox Width
				2f,// Font size  2 : .925   1 : .95   0.5 : .975
				true, // Center?
				false // Auto width based on quad?
				));
		
		
		/*
		Actor.Create("testDrag").AddComponent(new GUITextQuad_Draggable("DraggableQuad",new Transform( 
				new Vector3f(0,0, 999.f), // Position x,y, Z-Order higher is on top
				new Vector3f(0f, 0f,0f),  // Rotation
				new Vector3f(.225f,.4f,1f)), // Scale x,y,z
				"Images/blankTexture.png",  // Quad Texture path
				new Vector4f(0f,0f,0f,.75f), // Quad Color r,g,b,a
				new Vector2f(.9f, -.65f), // Font Offset (used to center text if needed) 
				"Fonts/verdana",  // Font path
				"", // Font String
				new Vector4f(.95f,.95f,.95f,1f), // Font color r,g,b,a
				.2f, // Text Line Width ( how wide each line will be can use \n in string for new line)
				.6f, // Font Size
				false, // Center Text
				false // Auto expand width to match quad
		) {
			@Override
			protected void OnMouseEnter() {
				SelectGUI();
			}
			
			@Override
			protected void OnSelect() {
				
			}
			
			@Override
			protected void StartDragging() {
				isDragging = true;
				dragPos.x = Input.GetMouseX();
				dragPos.y = Input.GetMouseY();
			}
			
			@Override
			public void StopDragging() {
				isDragging = false;
			}
			
			@Override
			protected void OnMouseExit() {
				DeselectGUI();
			}
			
			@Override
			protected void OnDrag(float x, float y) {
				int[] w_pos = Application.app.GetWindow().GetWindowPosition(); 
				int[] b_size = Application.app.GetWindow().GetFrameBuffers();
				SetPosition( this.transform.GetPosition().x + ( ((x-dragPos.x)/GUIRenderer.GetWidth())*2f ),
						this.transform.GetPosition().y - ( ((y-dragPos.y)/GUIRenderer.GetHeight()*2f)),
						this.transform.GetPosition().z
						);
				dragPos.x = x;
				dragPos.y = y;
			}
			
			@Override
			public void OnDeselect() {
				StopDragging();
			}
			
		});
		*/
	}

	@Override
	public void OnEnd() {
		
	}

	@Override
	public void OnEvent(Event e) {
		if (e instanceof Events.KeyPressedEvent) {
			if (((Events.KeyPressedEvent)e ).GetKeyCode() == KeyCodes.KEY_T) {
				Actor.Create("cardRunTime", this);
				if (GetActor("cardRunTime") != null && GetActor("cardRunTime").GetComponent("Mesh") != null) {
					Actor.Remove("cardRunTime", this);
				} else if (GetActor("cardRunTime") != null) {
					GetActor("cardRunTime").AddComponent(new CardMesh("Mesh", 
							new Transform( // Card Transform
									new Vector3f(-.75f, .75f, 0f), // Position
									new Vector3f(0f, 0, 0), // Rotation
									new Vector3f(.75f, 1f, 1f) ), // Scale
							"AH", // Card front Suit
							"card_back_red", // Card back Suit
							this.cam.GetCamera()));
				}
			}
		}
		
	}


	
	
}
