package app.Game;

import app.GUI.GUIStateManager;
import app.GUI.MenuGUIState;
import app.Util.MouseInput;
import app.Util.Timer;
import app.Util.WindowManager;

public class Engine implements Runnable{
	private WindowManager window;
//	private GUIStateManager guiManager;
	private Game game;
	private Timer timer;
	private final MouseInput mouseInput;
	
	private boolean running = false;
	private static final int TARGET_FPS = 60;
	private static final int TARGET_UPS = 30;
	
	public Engine(String title, int width, int height, Game game) {
		window = new WindowManager(title, width, height);
//		guiManager = new GUIStateManager();
//		guiManager.setCurrentGuiState(new MenuGUIState());
		mouseInput = new MouseInput();
		this.game = game;
		timer = new Timer();
	}
	
	public WindowManager getWindow() {
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
		window.start(window);
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
