package application;



import org.joml.Vector3f;

import engine.Application;
import engine.CameraController;
import engine.Events;
import engine.Input;
import engine.KeyCodes;
import engine.SceneManager;


public class BlackJack extends Application {

	CameraController cam;
	
	public BlackJack() {
		// Call super - Set window Title to "Blackjack" set width to 1280 set height to 720
		super("BlackJack", 1280, 720);
		
	}
	
	@Override
	protected void OnInit() {
		System.out.println("Black Jack Init!");
		
		// Enable/Disable vsync
		window.SetVSync(vsync);
		
		// Create Camera 
		cam = new CameraController.Orthographic(16.f/9.f);
		// Move camera backwards 5 units
		cam.SetPosition(new Vector3f(0,0,5f));
		// Initalize Scene Manager
		new MainMenuScene("mainMenu",cam);
		new TestScene("testScene",cam);
		SceneManager.SetCurrentScene("mainMenu");
		
	}
	
	// Called every frame
	@Override
	protected void OnUpdate(float deltaTime) {
	
		
		if (Input.IsMouseButtonPressed(KeyCodes.MOUSE_RIGHT)) {
			//System.out.println("We are right clicking");
		}
		
		cam.OnUpdate(deltaTime);
		
	}

	@Override
	protected void OnEvent(Events.Event e) {
		cam.OnEvent(e);
	}
	
	@Override
	protected boolean KeyEvent(Events.KeyEvent e) {
		return false;
	}
	
	@Override
	protected boolean MouseScrolledEvent(Events.MouseScrolledEvent e) {
		return false;
	}
	
	
	
	// Called on application start
	
	
	// Called on application end
	@Override
	protected void OnShutdown() {
		System.out.println("Black Jack Shutdown!");	
	}
	
	
}
