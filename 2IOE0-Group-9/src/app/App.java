package app;

import app.Game.Engine;
import app.Game.Game;

public class App {
	public void run() throws InterruptedException {
		Game game = new Game();
		Engine engine = new Engine("GameWindow", 800, 600, game);
		engine.run();
	}

	public static void main(String[] args) {
		try {
			new App().run();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}