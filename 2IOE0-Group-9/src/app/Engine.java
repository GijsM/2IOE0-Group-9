package app;

import app.Game.Game;
//import app.GUI.GUIStateManager;
//import app.GUI.MenuGUIState;
import app.Input.Mouse2;
//import app.Util.Timer;
import app.engine.Window;

public class Engine implements Runnable{
	private long window;
//	private GUIStateManager guiManager;
	private Game game;
	private Timer timer;
	private final Mouse2 mouse2;
	
	private boolean running = false;
	private static final int TARGET_UPS = 30;
	
	public Engine(String title, int width, int height, Game game) {
		window = Window.getWindow();
//		guiManager = new GUIStateManager();
//		guiManager.setCurrentGuiState(new MenuGUIState());
		mouse2 = new Mouse2();
		this.game = game;
		timer = new Timer();
	}
	
	public long getWindow() {
		return window;
	}

	@Override
	public void run() {
		try {
			init();
			loop();
		} catch (Exception excp) {
			excp.printStackTrace();
		} finally {
			cleanup();
		}
	}
	
	protected void init() throws Exception {
		Window.start(window);
		mouseInput.init(window);
		game.start(window);
	}
	
	protected void loop() {
		float elapsedTime;
		float accumulator = 0f;
		float interval = 1f / TARGET_UPS;
		
		boolean running = true;
		
		while(running && !window.windowShouldClose()) {
			elapsedTime = timer.getElapsedTime();
			accumulator += elapsedTime;
			
			input();
			
			while (accumulator >= interval) {
				update(interval);
				accumulator -= interval;
			}
			
			render();
		}
	}
	
	protected void input() {
		mouseInput.input(window);
		game.input(window, mouseInput);
	}
	
	protected void update(float interval) {
		game.update(interval, mouseInput);
	}
	
	protected void render() {
		game.render(window);
		window.update();
	}
	
	protected void cleanup() {
		game.stop();
	}
	
}
