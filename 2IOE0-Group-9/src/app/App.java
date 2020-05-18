package app;

import app.GUI.GUIStateManager;
import app.GUI.MenuGUIState;
import app.Util.WindowManager;

public class App {

	public GUIStateManager guiManager;
	public WindowManager windowManager;
	
	public void run() throws InterruptedException {
		guiManager = new GUIStateManager();
		windowManager = new WindowManager();

		windowManager.start();
		guiManager.setCurrentGuiState(new MenuGUIState());
		//DO NOTHING AS THE GUISTATE HANDLES THREADING
//		wait();

	}

	public static void main(String[] args) {
		try {
			new App().run();
		} catch (InterruptedException e) {
			System.out.println("test");
			e.printStackTrace();
		}
	}

}