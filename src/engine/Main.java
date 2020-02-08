package engine;

import application.BlackJack;
import application.TestingApp;

public class Main {

	public static void main(String[] args) {
		Application app = new TestingApp();
		app.Init();
		app.Run();
		app.Shutdown();
	}

}
