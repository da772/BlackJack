package application;


import org.joml.Vector2f;
import org.joml.Vector3f;

import engine.Application;
import engine.CameraController;
import engine.Events;
import engine.Input;
import engine.KeyCodes;
import engine.SceneManager;
import engine.Events.Event;


public class TestingApp extends Application {



	CameraController cam;
	
	public TestingApp() {
		// Call super - Set window Title to "Blackjack" set width to 1280 set height to 720
		super("Testing", 1280, 720);
		this.fpsCap = 255;
		
	}
	
	@Override
	protected void OnInit() {
		System.out.println(title+" Init!");
		window.SetFullScreen(true);
		// Enable/Disable vsync
		window.SetVSync(vsync);
		
		// Create Camera 
		cam = new CameraController.Orthographic(16.f/9.f);
		// Move camera backwards 5 units
		cam.SetPosition(new Vector3f(0,0,5f));
		// Initalize Scene Manager
		new MainMenuScene("mainMenu",cam);
		new TestScene("testScene",cam);
		SceneManager.SetCurrentScene("testScene");
	}
		
		
	
	// Called every frame
	@Override
	protected void OnUpdate(float deltaTime) {
		
		// Mesh3 Rotation
		
		
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
		
		int[] w_pos = window.GetWindowPosition();
		Vector2f w_pos_ = new Vector2f(w_pos[0], w_pos[1]);
		
		if (Input.IsKeyPressed(KeyCodes.KEY_RIGHT)) {
			w_pos_.x +=  1f;
		}
		if (Input.IsKeyPressed(KeyCodes.KEY_LEFT)) {
			w_pos_.x += -1f;
		}
		if (Input.IsKeyPressed(KeyCodes.KEY_UP)) {
			w_pos_.y += -1f;
		}
		if (Input.IsKeyPressed(KeyCodes.KEY_DOWN)) {
			w_pos_.y += 1f;
		}
		// Camera Update
		cam.OnUpdate(deltaTime);
		// Title Update
		window.SetWindowLocation((int)w_pos_.x,(int) w_pos_.y);
		window.SetTitle(title+" - " + fps + " FPS - OpenGL" + window.GetGLInfo() );
		
		
	}
	
	// Camera OnEvent
	protected void OnEvent(Event event) {
		if (cam != null)
		cam.OnEvent(event);
	};
	
	//Key Event
	@Override
	protected boolean KeyEvent(Events.KeyEvent e) {
		// Can cast key event to Pressed or Released
		if (e instanceof Events.KeyPressedEvent) {
			// We pressed a key!
			if ((( Events.KeyPressedEvent)e).GetKeyCode() == KeyCodes.KEY_F) {
				SceneManager.SetCurrentScene("testScene");
			}
			if ((( Events.KeyPressedEvent)e).GetKeyCode() == KeyCodes.KEY_V) {
				SceneManager.SetCurrentScene("mainMenu");
			}	
		}
		
		
		if (e instanceof Events.KeyReleasedEvent) {
			
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