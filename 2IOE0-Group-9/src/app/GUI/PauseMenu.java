package app.GUI;

public class PauseMenu extends State {
    private static PauseMenu instance = null;
    
    public static PauseMenu getInstance() {
    	if (instance == null) {
    		instance = new PauseMenu();
    	}
    	
    	return instance;
    } 
    
    public void init() {

    }

    public void update() {

    }

    public void render() {

    }
}
