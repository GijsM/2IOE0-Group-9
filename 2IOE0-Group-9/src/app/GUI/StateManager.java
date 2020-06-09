package app.GUI;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;

import app.Util.Color;

public class StateManager {
	
	private static StateManager instance = null;
    public static GameState gameState;

    public static enum GameState{
        GAME, MAINMENU, PAUSE, SETTINGS;
    }
    
    public StateManager() {
    	gameState = GameState.MAINMENU;
    }

    public static StateManager getInstance() {
    	if (instance == null) {
    		instance = new StateManager();
    	}
    	
    	return instance;
    }

    public void update() {
		Color clear = Color.color(0f, 0,0);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		glClearColor(clear.r, clear.g, clear.b, 1);
		
        switch(gameState) {
        	case GAME:
        		Game.init();
        		break;
            case MAINMENU:
                MainMenu.init();
                break;

            case PAUSE:
                Pause.init();
                break;

            case SETTINGS:
                SettingsMenu.init();
                break;
        }
    }

    public void toGame() { gameState = GameState.GAME; }
    public void toMenu() { gameState = GameState.MAINMENU; }
    public void toPause() { gameState = GameState.PAUSE; }
    public void toSettings() { gameState = GameState.SETTINGS; }
}
