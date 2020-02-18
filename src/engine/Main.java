package engine;

import application.BlackJack;
import application.TestingApp;

/**
 * 
 * Entry point
 * 		- Change TestingApp to the final app
 *
 */
public class Main {

	public static void main(String[] args) {
		Application app = new TestingApp();
		app.Init();
		app.Run();
		app.Shutdown();
	}

}
