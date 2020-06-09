package app;

import app.engine.Rect;
import app.Input.Mouse;
import app.Util.Color;

import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glEnable;

import java.util.concurrent.TimeUnit;

public class Window {
	private static Window instance = null;
	
	private String title;
	private long window;
	private Mouse mouse;
	private int width;
	private int height;
	private Rect rect;

	public static Window getInstance() {
		if(instance == null) {
			instance = new Window();
		}
		
		return instance;
	}
	
	public void init() { }
	
	public void create(String title, int width, int height) {
		mouse = Mouse.getInstance();
		setTitle(title);
		setWidth(width);
		setHeight(height);
		setRect(0, 0, width, height);
		
		window = glfwCreateWindow(width, height, title, 0, 0);
		if (window == 0) {
            System.err.println("ERROR: Window wasn't created");
            System.exit(-1);
        }
		
		glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
			if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
				glfwSetWindowShouldClose(window, true);	
		});
		
		glfwSetMouseButtonCallback(window, (window, key, action, mods) -> {
			if (key == GLFW_MOUSE_BUTTON_LEFT && action == GLFW_PRESS)
				mouse.setLeftButtonPressed(true);
			if (key == GLFW_MOUSE_BUTTON_LEFT && action == GLFW_RELEASE)
				mouse.setLeftButtonPressed(false);	
			if (key == GLFW_MOUSE_BUTTON_RIGHT && action == GLFW_PRESS)
		        mouse.setRightButtonPressed(true);
			if (key == GLFW_MOUSE_BUTTON_RIGHT && action == GLFW_RELEASE)
				mouse.setRightButtonPressed(false);	
			
		});
		
		glfwMakeContextCurrent(window);
		glfwSwapInterval(1);
		GL.createCapabilities();
		
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		
		glfwShowWindow(window);
		
	}
	
	public void render() {
		glfwPollEvents();
		glfwSwapBuffers(window);
	}

	public void clearWindow() {
		Color clear = Color.color(0f, 0,0);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glClearColor(clear.r, clear.g, clear.b, 1);
	}
	
	public void clean() {
		glfwDestroyWindow(window);
	}
	
	public void closeWindow() {
		glfwSetWindowShouldClose(window, true);
	}
	
	public boolean shouldClose() {
		return glfwWindowShouldClose(window);
	}
	
	public  boolean isKeyPressed(int keyCode) {
		return glfwGetKey(window, keyCode) == GLFW_PRESS;
	}
	
	public  boolean isButtonPressed(int buttonCode) {
		return glfwGetKey(window, buttonCode) == GLFW_PRESS;
	}
	
	public void setWindow(long window) {
		this.window = window;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}
	
	public void setHeight(int height) {
		this.height = height;
	}

	public void setRect(int x, int y, int width, int height) {
		rect = new Rect(x, y, width, height);
	}

	public long getWindow() {
		return window;
	}
	
	public String getTitle() {
		return title;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public Rect getRect() {
		return rect;
	}
}


// OLD
//public static long start() {
//	if (!glfwInit()) {
//		System.err.println("GLFW failed to initialize");
//		System.exit(-1);
//	}
//	
//	r = new Rect (0, 0 , width, height);
//	
//	window = glfwCreateWindow(width, height, title, 0, 0);
//	if (window == 0) {
//        System.err.println("ERROR: Window wasn't created");
//        System.exit(-1);
//    }
//	
//	glfwSetFramebufferSizeCallback(window, (window, width, height) -> {
//		Window.width = width;
//		Window.height = height;
//		r.Set(0, 0, width, height);
//		GL11.glViewport(0,0, width, height);
//	});
//
//	glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
//		if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
//			glfwSetWindowShouldClose(window, true);
//	});
//	
//    GLFWVidMode videoMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
//    int windowPosX = (videoMode.width() - width) / 2;
//    int windowPosY = (videoMode.height() - height) / 2;
//    GLFW.glfwSetWindowPos(window, windowPosX, windowPosY);
//    
//    
//    GLFW.glfwMakeContextCurrent(window);
//    GLFW.glfwSwapInterval(1);
//	glfwShowWindow(window);
//	
//    GL.createCapabilities();
//	glClearColor(0, 0, 0, 1);
//    GL11.glEnable(GL11.GL_DEPTH_TEST);
//
//    // TEST
//	glEnable(GL_BLEND);
//	glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
//	
//	return window;
//}