package app.GUI;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;

import app.Window;

public class PauseMenu extends State {
    private static PauseMenu instance = null;
    
    protected static StateManager stateManager;
    protected static GUI gui;
    protected static Window window;
    
    public static PauseMenu getInstance() {
    	if (instance == null) {
    		instance = new PauseMenu();
    	}
    	
    	return instance;
    } 
    
    public void init() {
    	gui = GUI.getInstance();
    	window = Window.getInstance();
    	stateManager = StateManager.getInstance();
    }

    public void update() {
        if (window.isKeyPressed(GLFW_KEY_ESCAPE)) {
        	System.out.println("Game has been unpaused");
        	stateManager.toGame();
        }
    }

    public void render() {

    }
}
