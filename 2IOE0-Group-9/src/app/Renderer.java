package app;

import app.GUI.GUI;
import app.GUI.StateManager;
import app.Input.Mouse;

public class Renderer {
	private static Renderer instance = null;
	
	protected StateManager stateManager;
	protected Mouse mouse;
	protected Window window;
	protected GUI gui;
	
	public Renderer() {
		window = Window.getInstance();
		gui = GUI.getInstance();
		mouse = Mouse.getInstance();
		stateManager = StateManager.getInstance();
	}
	
	public static Renderer getInstance() {
		if (instance == null) {
			instance = new Renderer();
		}
		
		return instance;
	}
	
	public void init() {
		window.init();

		try {
			gui.init();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void render() {
		window.render();	
	}
	
	public void update() {
		// Input.update()
		// Camera.update()
		stateManager.update();
	}
}
