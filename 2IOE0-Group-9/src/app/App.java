package app;

import app.GUI.GUI;
import app.GUI.StateManager;
import app.Input.Mouse;
import app.engine.Mesh;
import app.engine.Texture;
import app.engine.Window;
import app.math.Color;
import app.graphics.Renderer;
import app.Game.Map.GameMap;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;


public class App {

//	public GUIStateManager guiManager;
//	public WindowManager windowManager;

//	GameMap gameMap = new GameMap();

	private static Texture tex;
	private static Texture title;

	public void run() throws InterruptedException {
		long window = Window.Init();
		Color clear = Color.color(0f, 0,0);

		tex = new Texture("Egg.png");

		new Texture("DefaultGUI.png");

		GUI.init();

		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

		while(!glfwWindowShouldClose(window)){
			Mouse.Reset();
			glfwPollEvents();

			Window.ClearWindow();

			//GUI.Start();

			//StateManager.update();

//			GameMap.render();

			//GUI.Unbind();

			Window.UpdateScreen();
		}

		Texture.CleanUp();
		Mesh.CleanAllMesh();

		glfwTerminate();
		wait();
	}

	public static void main(String[] args) {
		try {
			new App().run();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}