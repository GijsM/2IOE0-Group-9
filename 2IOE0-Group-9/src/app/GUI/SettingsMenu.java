package app.GUI;

import app.engine.Rect;
import app.engine.Texture;
import app.engine.Window;

public class SettingsMenu {
    private static Texture title;

    public static void init() {
        title = new Texture("Title.png");

        GUI.DrawTexture(title, new Rect((Window.Width() / 4),0, Window.Width() / 2, 100));
        GUI.Button("Audio", new Rect(Window.Width() / 4, Window.Height() / 6, Window.Width() / 2, Window.Height() / 10), "Button", "ButtonHover");
        GUI.Button("Controls", new Rect(Window.Width() / 4, Window.Height() / 3, Window.Width() / 2, Window.Height() / 10), "Button", "ButtonHover");
        GUI.Button("Back to Menu", new Rect(Window.Width() / 4, Window.Height() / 2, Window.Width() / 2, Window.Height() / 10), "Button", "ButtonHover");
        update();
    }

    public static void update() {
        if (GUI.Button("Audio", new Rect(Window.Width() / 4, Window.Height() / 6, Window.Width() / 2, Window.Height() / 10), "Button", "ButtonHover")) {
            System.out.println("Go to audio options");
        }

        if (GUI.Button("Controls", new Rect(Window.Width() / 4, Window.Height() / 3, Window.Width() / 2, Window.Height() / 10), "Button", "ButtonHover")) {
            System.out.println("Here the controls can be found");
        }

        if (GUI.Button("Back to Menu", new Rect(Window.Width() / 4, Window.Height() / 2, Window.Width() / 2, Window.Height() / 10), "Button", "ButtonHover")) {
            System.out.println("Game now goes back to menu");
            Window.ClearWindow();
            Window.UpdateScreen();
            StateManager.toMenu();
        }
    }

    public static void render() {

    }
}
