package application;

import engine.Events.Event;
import renderer.Renderer;
import renderer.Transform;
import renderer.GUI.GUITextQuad;
import renderer.mesh.Mesh2DBackground;
import renderer.mesh.Mesh2DQuad;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import engine.Actor;
import engine.CameraController;
import engine.Events;
import engine.KeyCodes;
import engine.Scene;

public class MainMenuScene extends Scene {

	private float rotationSpeed = 90f;
	
	public MainMenuScene(String name, CameraController cam) {
		super(name, cam);
		this.cam = cam;
	
		
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
		
		Actor.Create("example", this).AddComponent(new Mesh2DQuad("quad", //Name
				new Transform( // Card Transform
				new Vector3f(2, 0, 0), // Position
				new Vector3f(0f, 0, 0f), // Rotation
				new Vector3f(3f, 3f, 1f) ),// Scale)
				"Atlas/cardAtlas.png",// Texture
				new Vector4f(1f), // Color
				this.cam.GetCamera())
				);
		
		Actor.Create("background", this).AddComponent(new Mesh2DBackground("background",
				new Transform(
						new Vector3f(0f,0,-100f),  // Position
						new Vector3f(0f), // Rotation
						new Vector3f(1000, 1000f, 0f)), // Scale
				"Images/pokerFelt.jpg", // Texture
				new Vector4f(1f,1f,1f,1f) , // Color
				new Vector2f(10000f), // UV Scale
				this.cam.GetCamera() // Camera
				));

	 
	 
		Actor.Create("blackJackText", this).AddComponent(new GUITextQuad("textQuad",new Transform(
				new Vector3f(0, .775f, 0f), // Position
				new Vector3f(0f), // Rotation (buggy keep at 0)
				new Vector3f(.225f, .225f, 1f) // Quad Scale
				),
				"Images/blankTexture.png", // Texture
				new Vector4f(.125f, .125f,.25f,0f), // Quad Color
				new Vector2f(.825f, -.95f), // Text Position offset
				"Fonts/poker1", // Text Font
				"Black Jack", // Text
				new Vector4f(0.f,0f,0f,1f), // Text Color
				.45f, // Textbox Width
				2f,// Font size
				true, // Center?
				false // Auto width based on quad?
				));
		
		
		
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