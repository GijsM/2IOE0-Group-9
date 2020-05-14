package app.GUI;

import app.Game.Game;

public class GUIStateManager {
    //Manages the GUI, makes sure switching states is smooth
    private GUIState currentGuiState;

    //Possible game states
    public static enum GameStates {
        INTRO, MAIN_MENU, GAME, PAUSE;
    }

    public static GameStates gameStates;
    public static Intro intro;
    public static MainMenu mainMenu;
    public static Game game;
    public static Pause pause;

    //For every update we do, we check the current state of the game
    public static void checkState() {
        switch(gameStates) {
            case INTRO:
                if (intro == null) {
                    intro = new Intro();
                }
                break;
            case MAIN_MENU:
                if (mainMenu == null) {
                    mainMenu = new MainMenu();
                }
                MainMenu.update();
                break;
            case GAME:
                if (game == null) {
                    game = new Game();
                }

                break;
            case PAUSE:
                if (pause == null) {
                    pause = new Pause();
                }
                break;
        }
    }

    public GUIState getCurrentGuiState() {
        return currentGuiState;
    }

    public void setCurrentGuiState(GUIState currentGuiState) {
        if (this.currentGuiState != null) {
            this.currentGuiState.stop();
        }
        this.currentGuiState = currentGuiState;
        this.currentGuiState.start();
    }
}
