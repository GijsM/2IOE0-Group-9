package engine;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.tinyfd.TinyFileDialogs.*;

import Input.Mouse;
import gui.GUI;
import gui.GUISkin;
import gui.StateManager;
import math.Color;
import math.Matrix4X4;
import org.lwjgl.opengl.GL11;

public class main {
    private static Texture tex;
    private static Texture title;

    public static void main(String[] args) {
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

            GUI.Start();

            StateManager.update();

            GUI.Unbind();

            Window.UpdateScreen();
        }

        Texture.CleanUp();
        Mesh.CleanAllMesh();

        glfwTerminate();
    }

}
