package application;



import engine.Application;
import engine.Events;
import engine.Input;
import engine.KeyCodes;


public class BlackJack extends Application {

	public BlackJack() {
		// Call super - Set window Title to "Blackjack" set width to 1280 set height to 720
		super("BlackJack", 1280, 720);
		
	}
	
	@Override
	protected void OnInit() {
		System.out.println("Black Jack Init!");
		
		

	}
	
	// Called every frame
	@Override
	protected void OnUpdate(float deltaTime) {
	
		
		if (Input.IsMouseButtonPressed(KeyCodes.MOUSE_RIGHT)) {
			//System.out.println("We are right clicking");
		}
		
		
		
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
