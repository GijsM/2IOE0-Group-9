package app.GUI;

import app.engine.Rect;
import app.engine.Texture;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_RIGHT;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.glfwSetMouseButtonCallback;

import app.Window;


public class MainMenu extends State{
    private static MainMenu instance = null;
    protected static Texture title = new Texture("Title.png");
    protected static StateManager stateManager;
    protected static GUI gui;
    protected static Window window;

    public void init() {
    	gui = GUI.getInstance();
    	window = Window.getInstance();
    	stateManager = StateManager.getInstance();
    }

    public static MainMenu getInstance(){
        if(instance == null) {
            instance = new MainMenu();
        }

        return instance;
    }

    public void update() {
    	gui.DrawTexture(title, new Rect((window.getWidth() / 4),0, window.getWidth() / 2, 100));
        if (gui.Button("Start", new Rect(window.getWidth() / 4, window.getHeight() / 6, window.getWidth() / 2, window.getHeight() / 10), "Button", "ButtonHover")) {
        	System.out.println("Go to game");
        	stateManager.toGame(); 
        }

        if (gui.Button("Settings", new Rect(window.getWidth() / 4, window.getHeight() / 3, window.getWidth() / 2, window.getHeight() / 10), "Button", "ButtonHover")) {
            System.out.println("Go to settings menu");
            stateManager.toSettings();
        }

        if (gui.Button("Exit", new Rect(window.getWidth() / 4, window.getHeight() / 2, window.getWidth() / 2, window.getHeight() / 10), "Button", "ButtonHover")) {
            System.out.println("Game is being shutdown");
            window.closeWindow();
        }
    }

    public void render() {

    }
}
