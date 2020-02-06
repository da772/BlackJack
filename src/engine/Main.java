package engine;

import application.BlackJack;

public class Main {

	public static void main(String[] args) {
		Application app = new BlackJack();
		app.Init();
		app.Run();
		app.Shutdown();
	}

}
