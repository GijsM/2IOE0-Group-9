package app.GUI;

import app.engine.Rect;
import app.engine.Texture;
import app.engine.Window;
import app.App;
import org.lwjgl.glfw.GLFW;

public class MainMenu {
    private static Texture title;

    public static void init() {
        title = new Texture("Title.png");


        GUI.DrawTexture(title, new Rect((Window.Width() / 4),0, Window.Width() / 2, 100));
        GUI.Button("Start", new Rect(Window.Width() / 4, Window.Height() / 6, Window.Width() / 2, Window.Height() / 10), "Button", "ButtonHover");
        GUI.Button("Settings", new Rect(Window.Width() / 4, Window.Height() / 3, Window.Width() / 2, Window.Height() / 10), "Button", "ButtonHover");
        GUI.Button("Exit", new Rect(Window.Width() / 4, Window.Height() / 2, Window.Width() / 2, Window.Height() / 10), "Button", "ButtonHover");
        update();
    }

    public static void update() {
        if (GUI.Button("Start", new Rect(Window.Width() / 4, Window.Height() / 6, Window.Width() / 2, Window.Height() / 10), "Button", "ButtonHover")) {
            System.out.println("The game has started");
        }

        if (GUI.Button("Settings", new Rect(Window.Width() / 4, Window.Height() / 3, Window.Width() / 2, Window.Height() / 10), "Button", "ButtonHover")) {
            System.out.println("Go to settings menu");
            Window.ClearWindow();
            Window.UpdateScreen();
            StateManager.toSettings();
        }

        if (GUI.Button("Exit", new Rect(Window.Width() / 4, Window.Height() / 2, Window.Width() / 2, Window.Height() / 10), "Button", "ButtonHover")) {
            System.out.println("Game is being shutdown");
            Window.CloseWindow();
        }
    }

    public void render() {

    }
}
