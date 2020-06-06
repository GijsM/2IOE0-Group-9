package app.engine;

import app.math.Color;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.glClearColor;

public class Window {
	private static final String title = "Genocrawl";
	private static int width = 800;
	private static int height = 600;
	private static int frames;
	private static long window;
	private static long time;
	private static Rect r;



	public static long start() {
		if (!glfwInit()) {
			System.err.println("GLFW failed to initialize");
			System.exit(-1);
		}
		
		r = new Rect (0, 0 , width, height);
		
		window = glfwCreateWindow(width, height, title, 0, 0);
		if (window == 0) {
            System.err.println("ERROR: Window wasn't created");
            System.exit(-1);
        }
		
		glfwSetFramebufferSizeCallback(window, (window, width, height) -> {
			Window.width = width;
			Window.height = height;
			r.Set(0, 0, width, height);
			GL11.glViewport(0,0, width, height);
		});
	
		glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
			if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
				glfwSetWindowShouldClose(window, true);
		});
		
        GLFWVidMode videoMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
        int windowPosX = (videoMode.width() - width) / 2;
        int windowPosY = (videoMode.height() - height) / 2;
        GLFW.glfwSetWindowPos(window, windowPosX, windowPosY);
        
        
        GLFW.glfwMakeContextCurrent(window);
        GLFW.glfwSwapInterval(1);
		glfwShowWindow(window);
		
        GL.createCapabilities();
		glClearColor(0, 0, 0, 1);
        GL11.glEnable(GL11.GL_DEPTH_TEST);

		return window;
	}
	
	public static void update() {
		glfwSwapBuffers(window);
		glfwPollEvents();
		
        frames++;
        if (System.currentTimeMillis() > time + 1000) {
            GLFW.glfwSetWindowTitle(window, title + " | FPS: " + frames);
            time = System.currentTimeMillis();
            frames = 0;
        }
	}
	
	public static void clear() {
		Color clear = Color.color(0f, 0,0);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glClearColor(clear.r, clear.g, clear.b, 1);
	}
	
	public static void stop() {
		glfwFreeCallbacks(window);
		glfwWindowShouldClose(window);
		glfwDestroyWindow(window);
		glfwTerminate();
	}
	
	public static boolean windowShouldClose() {
		return glfwWindowShouldClose(window);
	}
	
	public static boolean isKeyPressed(int keyCode) {
		return glfwGetKey(window, keyCode) == GLFW_PRESS;
	}
	
	public static int getWidth() {
		return width;
	}

	public static int getHeight() {
		return height;
	}

	public static Rect getRect() {
		return r;
	}

	public static long getWindow() {
		return window;}
}