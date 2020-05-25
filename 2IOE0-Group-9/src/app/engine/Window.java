package engine;

import Input.Mouse;
import math.Color;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.glClearColor;

public class Window {
    private static final String title = "Genocrawl";
    private static int width = 800;
    private static int height = 600;
    private static long window;
    private static Rect r;


    private static GLFWWindowSizeCallback windowSizeCallback;

    public static long Init() {
        if (!glfwInit()) {
            System.err.println("GLFW failed to initialize");
            System.exit(-1);
        }

        r = new Rect (0, 0 , width, height);

        window = glfwCreateWindow(width, height, title, 0, 0);
        glfwShowWindow(window);
        glfwMakeContextCurrent(window);

        GL.createCapabilities();
        glClearColor(0, 0, 0, 1);

        glfwSetMouseButtonCallback(window, new Mouse());

        windowSizeCallback = GLFWWindowSizeCallback.create(Window::OnWindowResized);
        glfwSetWindowSizeCallback(window, windowSizeCallback);

        return window;
    }
    public static void OnWindowResized(long win, int w, int h) {
        width = w;
        height = h;
        r.Set(0, 0, w, h);
        GL11.glViewport(0,0, w, h);
    }

    public static void CloseWindow() {
        glfwWindowShouldClose(window);
        glfwDestroyWindow(window);
        glfwTerminate();
    }

    public static void ClearWindow() {
        Color clear = Color.color(0f, 0,0);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glClearColor(clear.r, clear.g, clear.b, 1);
    }

    public static void UpdateScreen() {
        glfwSwapBuffers(window);
    }

    public static int Width() {
        return width;
    }

    public static int Height() {
        return height;
    }

    public static Rect GetRect() {
        return r;
    }

    public static long Window() {
        return window;}
}
