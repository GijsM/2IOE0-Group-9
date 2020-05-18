package app.GUI;

import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.opengl.GL;

import app.Util.IRenderable;
import app.Util.WindowManager;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class MenuGUIState extends GUIState implements IRenderable {
	private Thread thread;
	WindowManager manager = new WindowManager();
	
    @Override
    public void start() {
    	if (manager.getThread() == null) {
    		thread = new Thread(new Runnable() {
        		public void run() {
        			System.out.println("new menu thread");
        			render();
        		}
        	});    		
    		
    		manager.setThread(thread);
    	} else {
    		thread = manager.getThread();
    	}
    	
    	thread.start();
    }

    @Override
    public void stop() {
        // TODO Auto-generated method stub

    }
    
    @Override
    public void render() {
    	manager = new WindowManager();
    	manager.start();
    	manager.loop();


//    	glfwMakeContextCurrent(window);
//    	GL.createCapabilities();
//    	glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
    	
    }
    
}