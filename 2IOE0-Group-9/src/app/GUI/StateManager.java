package gui;

public class StateManager {

    public static enum GameState{
        MAINMENU, PAUSE, SETTINGS;
    }

    public static GameState gameState = GameState.MAINMENU;

    public static MainMenu mainMenu;
    public static Pause pause;
    public static SettingsMenu settingsMenu;

    public static void update() {
        switch(gameState) {
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

    public static void toMenu() {
        gameState = GameState.MAINMENU;
    }

    public static void toPause() {
        gameState = GameState.PAUSE;
    }
    public static void toSettings() {gameState = GameState.SETTINGS;}
}
