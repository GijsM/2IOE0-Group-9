package app.GUI;

import app.Util.IRenderable;
import app.Util.WindowManager;

public class GameGUIState extends GUIState implements IRenderable {
	private Thread thread;
	
    @Override
    public void start() {
    	if (thread == null) {
    		thread = new Thread(new Runnable() {
        		public void run() {
        			System.out.println("new game thread");
        			render();
        		}
        	});    		
    	} 
    	thread.start();
    }

    @Override
    public void stop() {
        // TODO Auto-generated method stub

    }
    
    @Override
    public void render() {
    	final WindowManager manager = new WindowManager();
    	long window = manager.getWindow();
    	
    	manager.start();
    	manager.loop();
    	

    	
//    	glfwMakeContextCurrent(window);
//    	GL.createCapabilities();
//    	glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
    	
    }
    
}