package app;

import app.GUI.GUI;
import app.GUI.StateManager;
import app.Game.Map.GameMap;
import app.Input.Mouse;

import java.util.Random;

public class Renderer {
	private static Renderer instance = null;
	
	protected StateManager stateManager;
	protected Mouse mouse;
	protected Window window;
	protected GUI gui;
	protected GameMap map;
	
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
		 mouse.input();
		// Camera.update()
		stateManager.update();
	}
}
