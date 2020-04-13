package application;

import engine.Application;

/**
 * 
 * Entry point
 * 		- Change TestingApp to the final app
 *
 */
public class Main {

	public static void main(String[] args) {
		Application app = new BlackJack();
		app.Init();
		app.Run();
		app.Shutdown();
	}

}
