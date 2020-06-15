package app;

public class App {
	/*
	 * Game Properties
	 */
	private final String TITLE = "Genocrawl";
	private final int WIDTH = 1280;
	private final int HEIGHT = 800;
	
	protected Engine engine;
	
	public App() {
		engine = new Engine();
		engine.createWindow(TITLE, WIDTH, HEIGHT);
		engine.init();
		engine.start();
	}
	
	public Engine getEngine() {
		return engine;
	}
	
	public void setEngine(Engine engine) {
		this.engine = engine;
	}

	public static void main(String[] args) {
		new App();
	}

}