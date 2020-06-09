package app;

import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwTerminate;

import app.Input.Mouse;
import app.Util.Timer;

public class Engine {

	protected Mouse mouse;
	protected Renderer renderer;
	protected Timer timer;

	protected Window window;

	private boolean running;
	
	public void createWindow(String title, int width, int height) {
		glfwInit();
		
		window = Window.getInstance();
		renderer = Renderer.getInstance();
		timer = Timer.getInstance();	
		mouse = Mouse.getInstance();
		
		window.create(title, width, height);
	}
	
	public void init() {
		renderer.init();
	}
	
	public void start() {
		if (running) {
			return;
		}
	
		run();
	}
	
	public void run() { 
		this.running = true;
		
		// GameLoop
		while(running) {			
			glfwPollEvents();
			mouse.reset();			

			if (window.shouldClose()) {
				stop();
			}
				
			update();
			render();
		}

		clean();
	}
	private void render() {
		renderer.render();
	}
	
	public void update() {
		renderer.update();
	}
	
	private void stop() {
		if (!running) {
			return;
		}
		
		running = false;
	}
	
	private void clean() {
		window.clean();
		glfwTerminate();
	}
}
