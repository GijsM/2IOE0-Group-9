package app.GUI;

public class GUIStateManager {
    //Manages the GUI, makes sure switching states is smooth
    private GUIState currentGuiState;

    public GUIState getCurrentGuiState() {
        return currentGuiState;
    }

    public void setCurrentGuiState(GUIState currentGuiState) {
        if (this.currentGuiState != null) {
            this.currentGuiState.stop();
        }
        this.currentGuiState = currentGuiState;
        this.currentGuiState.start(WindowManager window);
    }
}