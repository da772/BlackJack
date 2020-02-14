package application;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import engine.Application;
import engine.CameraController;
import engine.Events;
import engine.Input;
import engine.KeyCodes;
import engine.Events.Event;
import renderer.Transform;
import renderer.GUI.GUITextQuad;
import renderer.GUI.GUITextQuad_Draggable;
import renderer.mesh.Mesh2D;
import renderer.mesh.Mesh2DBackground;

public class TestingApp extends Application {

	
	// Mesh Variables
	Mesh2D card1;
	Mesh2D card2;
	Mesh2D card3;
	Mesh2D card4;
	Mesh2D card5;
	Mesh2D card6;
	
	Mesh2D background;
	// GUI Variables
	GUITextQuad tQuad;
	// Mesh manipulation Variables
	float rotate = 0;
	// Camera variable
	CameraController cam;
	
	public TestingApp() {
		// Call super - Set window Title to "Blackjack" set width to 1280 set height to 720
		super("Testing", 1280, 720);
		
	}
	
	@Override
	protected void OnInit() {
		System.out.println(title+" Init!");
		
		// Enable/Disable vsync
		window.SetVSync(vsync);
		
		// Create Camera 
		cam = new CameraController.Orthographic(16.f/9.f);
		// Move camera backwards 5 units
		cam.SetPosition(new Vector3f(0,0,5f));

		// Create background
		background = new Mesh2DBackground(
				new Transform(
						new Vector3f(0f,0,-100f),  // Position
						new Vector3f(0f), // Rotation
						new Vector3f(1000, 1000f, 0f)), // Scale
				"Images/mosaico_multicolor_tile.jpg", // Texture
				new Vector4f(1f,1f,1f,1f) , // Color
				new Vector2f(1000f), // UV Scale
				cam.GetCamera() // Camera
				);
		// Add background
		background.Add();
		
		// Create Mesh
		card1 = new CardMesh(
				new Transform( // Card Transform
						new Vector3f(0f, 0, 0f), // Position
						new Vector3f(0f, 180f, 0f), // Rotation
						new Vector3f(.75f, 1f, 1f) ), // Scale
				"QC", // Card front Suit
				"card_back_black", // Card back Suit
				cam.GetCamera() // Camera
				);
		// Add Mesh
		card1.Add();
		
		// Create Mesh
		card2 = new CardMesh(
				new Transform( // Card Transform
						new Vector3f(-1f, 0, 0f), // Position
						new Vector3f(0f, 0f, 0f), // Rotation
						new Vector3f(.75f, 1f, 1f) ), // Scale
				"AC", // Card front Suit
				"card_back_black", // Card back Suit
				cam.GetCamera() // Camera
				);
		// Add Mesh
		card2.Add();
		
		// Create Mesh
		card3 = new CardMesh(
				new Transform( // Card Transform
						new Vector3f(1f, 0, 0f), // Position
						new Vector3f(0f, 0f, 0f), // Rotation
						new Vector3f(.75f, 1f, 1f) ), // Scale
				"2H", // Card front Suit
				"card_back_black", // Card back Suit
				cam.GetCamera() // Camera
				);
		// Add Mesh
		card3.Add();
		
		//Create Draggable GUI
		tQuad = new GUITextQuad_Draggable(new Transform(
				new Vector3f(-.775f, .775f, 0f), // Position
				new Vector3f(0f), // Rotation (buggy keep at 0)
				new Vector3f(.225f, .225f, 1f) // Quad Scale
				),
				"Images/blankTexture.png", // Texture
				new Vector4f(.125f, .125f,.25f,.9f), // Quad Color
				new Vector2f(.87f, -.95f), // Text Position offset
				"Fonts/verdana", // Text Font
				"Click and hold to drag!", // Text
				new Vector4f(0.f,0f,0f,1f), // Text Color
				.25f, // Textbox Width
				1.25f,// Font size
				true, // Center?
				false // Auto width based on quad?
				);
		// Add GUI
		tQuad.Add();
		
		
		for (int i = 0; i < 100; i++) {
			CardMesh m = new CardMesh(
					new Transform( // Card Transform
							new Vector3f(-5 + (.1f*i), 0, -.1f), // Position
							new Vector3f(0f, 180f, 0f), // Rotation
							new Vector3f(.75f, 1f, 1f) ), // Scale
					"2C", // Card front Suit
					"card_back_black", // Card back Suit
					cam.GetCamera() // Camera
					);
			m.Add();
		}
		
		
	}
	
	// Called every frame
	@Override
	protected void OnUpdate(float deltaTime) {
		
		// Mesh3 Rotation
		rotate += 180.f * deltaTime;
		card3.SetRotation(0f,rotate, 0f);
		
		// Camera Control
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
		// Camera Update
		cam.OnUpdate(deltaTime);
		// Title Update
		window.SetTitle(title+" - " + fps + " FPS - OpenGL" + window.GetGLInfo() );
		
	}
	
	// Camera OnEvent
	protected void OnEvent(Event event) {
		cam.OnEvent(event);
	};
	
	//Key Event
	@Override
	protected boolean KeyEvent(Events.KeyEvent e) {
		// Can cast key event to Pressed or Released
		if (e instanceof Events.KeyPressedEvent) {
			// We pressed a key!
		}
		
		if (e instanceof Events.KeyReleasedEvent) {
			// We released a key!
		}
		
		return false;
	}
	
	// Scroll Event
	@Override
	protected boolean MouseScrolledEvent(Events.MouseScrolledEvent e) {
		// Zoom Camera
		cam.OnZoom(e.GetScrollX(), e.GetScrollY(), .1f);
		return false;
	}
	
	
	// Called on application end
	@Override
	protected void OnShutdown() {
		System.out.println(title+ " Shutdown!");	
	}
	
	
}
