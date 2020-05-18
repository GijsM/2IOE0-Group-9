package app.Util;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.nio.*;
import java.util.ArrayList;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class WindowManager implements IStartStopable{
    private long window;
    private Thread thread;
    //COPIED FROM TUTORIAL NOT SURE IF THIS IS CORRECT
	ArrayList<ArrayList> map = new ArrayList<>();

    @Override
    public void start() {
		// Setup an error callback. The default implementation
		// will print the error message in System.err.
		GLFWErrorCallback.createPrint(System.err).set();
		fillMap();

		// Initialize GLFW. Most GLFW functions will not work before doing this.
		if ( !glfwInit() )
			throw new IllegalStateException("Unable to initialize GLFW");

		// Configure GLFW
		glfwDefaultWindowHints(); // optional, the current window hints are already the default
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable

		// Create the window
		window = glfwCreateWindow(300, 300, "Hello World!", NULL, NULL);
		if ( window == NULL )
			throw new RuntimeException("Failed to create the GLFW window");

		// Setup a key callback. It will be called every time a key is pressed, repeated or released.
		glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
			if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
				glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
		});

		// Get the thread stack and push a new frame
		try ( MemoryStack stack = stackPush() ) {
			IntBuffer pWidth = stack.mallocInt(1); // int*
			IntBuffer pHeight = stack.mallocInt(1); // int*

			// Get the window size passed to glfwCreateWindow
			glfwGetWindowSize(window, pWidth, pHeight);

			// Get the resolution of the primary monitor
			GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

			// Center the window
			glfwSetWindowPos(
				window,
				(vidmode.width() - pWidth.get(0)) / 2,
				(vidmode.height() - pHeight.get(0)) / 2
			);
		} // the stack frame is popped automatically

		// Make the OpenGL context current
		glfwMakeContextCurrent(window);
		// Enable v-sync
		glfwSwapInterval(1);
		


		// Make the window visible
		glfwShowWindow(window);
	}

	private void fillMap() {
    	for(int i = 0; i < 10 ; i ++){
    		map.add(new ArrayList<Integer>());
		}
    	for(int i = 0 ; i < 10 ; i++){
    		for(int j = 0; j < 10 ; j++){
    			if(i == 0 || i == 9 || j == 0 || j == 9){
    				map.get(i).add(0);
				} else {
    				map.get(i).add(1);
				}
			}
		}
	}

	@Override
    public void stop() {
        // Free the window callbacks and destroy the window
		glfwFreeCallbacks(window);
		glfwDestroyWindow(window);

		// Terminate GLFW and free the error callback
		glfwTerminate();
		glfwSetErrorCallback(null).free();

    }
    
    public long getWindow() {
    	return window;
    }
    
    public Thread getThread() {
    	return thread;
    }
    
    public void setThread(Thread thrd) {
    	this.thread = thrd;
    }

    //USED ORIGINALLY, DEAD CODE, KEPT FOR REFERENCE
    public void loop() {
		// This line is critical for LWJGL's interoperation with GLFW's
		// OpenGL context, or any context that is managed externally.
		// LWJGL detects the context that is current in the current thread,
		// creates the GLCapabilities instance and makes the OpenGL
		// bindings available for use.
		GL.createCapabilities();

		// Set the clear color
		glClearColor(0f, 0.0f, 0.0f, 0.0f);

		// Run the rendering loop until the user has attempted to close
		// the window or has pressed the ESCAPE key.
		while ( !glfwWindowShouldClose(window) ) {
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
			glColor4d(1f, 0f, 0f, 0.3f);

			glLoadIdentity();
			float topX =(float) -1;
			float botX =(float) -0.8;
			float topY = 1;
			float botY = (float) 0.8;
			float delta = (float) 2/map.size();
			for(float i = 0 ; i < map.size(); i++){
				for( float j = 0 ; j < map.size(); j++){

					float xOne = topX + j*delta;
					float xTwo = botX + j*delta;
					float yOne = topY - i*delta;
					float yTwo = botY - i*delta;

					if(i == 0 || i == 9 || j == 0 || j == 9){

						glBegin(GL_QUADS);

						glNormal3f(0, 0, 1);
						glColor3f(0,1,0);
						glVertex3f(xOne ,yOne, 0); //Draw Back Wall - Top Left
						glColor3f(0,0,1);
						glVertex3f(xTwo,yOne, 0); //Draw Back Wall - Top Right
						glColor3f(0,1,0);
						glVertex3f(xTwo,yTwo, 0); //Draw Back Wall - Bottom Right
						glColor3f(0,0,1);
						glVertex3f(xOne,yTwo, 0); //Draw Back Wall - Bottom Left
						glEnd();
					} else {
						glBegin(GL_QUADS);

						glNormal3f(0, 0, 1);
						glColor3f(1,0,0);
						glVertex3f(xOne ,yOne, 0); //Draw Back Wall - Top Left
						glColor3f(0,0,1);
						glVertex3f(xTwo,yOne, 0); //Draw Back Wall - Top Right
						glColor3f(1,0,0);
						glVertex3f(xTwo,yTwo, 0); //Draw Back Wall - Bottom Right
						glColor3f(0,0,1);
						glVertex3f(xOne,yTwo, 0); //Draw Back Wall - Bottom Left
						glEnd();
					}
				}
			}




			glfwSwapBuffers(window); // swap the color buffers

			// Poll for window events. The key callback above will only be
			// invoked during this call.
			glfwPollEvents();
		}
	}
}