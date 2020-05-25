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
	WindowManager manager = new WindowManager("Hello World", 800, 600);
	
    @Override
    public void start(WindowManager window) {
    	if (manager.getThread() == null) {
    		thread = new Thread(new Runnable() {
        		public void run() {
        			System.out.println("new menu thread");
        			render(window);
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
    public void render(WindowManager window) {
    	window.start(window);
    	manager.loop();


//    	glfwMakeContextCurrent(window);
//    	GL.createCapabilities();
//    	glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
    	
    }
    
}