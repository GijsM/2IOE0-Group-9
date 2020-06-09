package app.GUI;

import app.Window;
import app.engine.Rect;
import app.engine.Texture;

public class SettingsMenu {
    protected static Texture title = new Texture("Title.png");
    protected static StateManager stateManager;
    protected static GUI gui;
    protected static Window window;

    public static void init() {
    	gui = GUI.getInstance();
    	window = Window.getInstance();
    	stateManager = StateManager.getInstance();
    	
        gui.DrawTexture(title, new Rect((window.getWidth() / 4),0, window.getWidth() / 2, 100));
        gui.Button("Audio", new Rect(window.getWidth() / 4, window.getHeight() / 6, window.getWidth() / 2, window.getHeight() / 10), "Button", "ButtonHover");
        gui.Button("Controls", new Rect(window.getWidth() / 4, window.getHeight() / 3, window.getWidth() / 2, window.getHeight() / 10), "Button", "ButtonHover");
        gui.Button("Back to Menu", new Rect(window.getWidth() / 4, window.getHeight() / 2, window.getWidth() / 2, window.getHeight() / 10), "Button", "ButtonHover");
        update();
    }

    public static void update() {
        if (gui.Button("Audio", new Rect(window.getWidth() / 4, window.getHeight() / 6, window.getWidth() / 2, window.getHeight() / 10), "Button", "ButtonHover")) {
            System.out.println("Go to audio options");
//          stateManager.update();
        }

        if (gui.Button("Controls", new Rect(window.getWidth() / 4, window.getHeight() / 3, window.getWidth() / 2, window.getHeight() / 10), "Button", "ButtonHover")) {
            System.out.println("Here the controls can be found");
//          stateManager.update();
        }

        if (gui.Button("Back to Menu", new Rect(window.getWidth() / 4, window.getHeight() / 2, window.getWidth() / 2, window.getHeight() / 10), "Button", "ButtonHover")) {
            System.out.println("Game now goes back to menu");
            stateManager.toMenu();
            stateManager.update();
        }
    }

    public static void render() {

    }
}
