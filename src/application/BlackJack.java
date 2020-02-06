package application;

import engine.Application;
import engine.Input;
import engine.KeyCodes;

public class BlackJack extends Application {

	public BlackJack() {
		// Call super - Set window Title to "Blackjack" set width to 1280 set height to 720
		super("BlackJack", 1280, 720);
	}
	
	// Called every frame
	@Override
	protected void OnUpdate() {
		if (Input.IsMouseButtonPressed(KeyCodes.MOUSE_RIGHT)) {
			System.out.println("We are right clicking");
		}
		
		if (Input.IsKeyPressed(KeyCodes.KEY_D)) {
			System.out.println("We are pressing the letter D");
		}
		
	}
	
	// Called on application start
	@Override
	protected void OnInit() {
		System.out.println("Black Jack Init!");
	}
	
	// Called on application end
	@Override
	protected void OnShutdown() {
		System.out.println("Black Jack Shutdown!");	
	}
	
	
}
