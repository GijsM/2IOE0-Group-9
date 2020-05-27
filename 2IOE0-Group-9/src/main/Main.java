package main;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import engine.graphics.Mesh;
import engine.graphics.Renderer;
import engine.graphics.Vertex;
import engine.io.Input;
import engine.io.Window;
import engine.maths.Vector3f;

public class Main implements Runnable {
    public Thread game;
    public Window window;
    public Renderer renderer;
    public final int WIDTH = 1280, HEIGHT = 760;

    private static enum STATE {
        MENU, GAME;
    }
    private STATE state = STATE.GAME;

    public Mesh mesh = new Mesh(new Vertex[] {
            new Vertex(new Vector3f(-0.5f,  0.5f, 0.0f)),
            new Vertex(new Vector3f(-0.5f, -0.5f, 0.0f)),
            new Vertex(new Vector3f( 0.5f, -0.5f, 0.0f)),
            new Vertex(new Vector3f( 0.5f,  0.5f, 0.0f))
    }, new int[] {
            0, 1, 2,
            0, 3, 2
    });

    public void start() {
        game = new Thread(this, "game");
        game.start();
    }

    public void init() {
        window = new Window(WIDTH, HEIGHT, "Game");
        renderer = new Renderer();
        window.setBackgroundColor(1.0f, 0, 0);
        window.create();
        mesh.create();
    }

    public void run() {
        init();
        while (!window.shouldClose() && !Input.isKeyDown(GLFW.GLFW_KEY_ESCAPE)) {
           update();
            render();
            if (Input.isKeyDown(GLFW.GLFW_KEY_F11)) window.setFullscreen(!window.isFullscreen());
        }
        window.destroy();
    }

    private void update() {
        window.update();
        if (Input.isButtonDown(GLFW.GLFW_MOUSE_BUTTON_LEFT)) System.out.println("X: " + Input.getScrollX() + ", Y: " + Input.getScrollY());
    }

    private void render() {
       /* GL11.glBegin(GL11.GL_TRIANGLES);
        GL11.glColor3f(1.0f, 0.1f, 1.0f);
        GL11.glVertex3f(0, 0, 0);
        GL11.glVertex3f(1, 0, 0);
        GL11.glVertex3f(0, 1, 0);
        GL11.glColor3f(1.0f, 1.0f, 1.0f);
        GL11.glEnd();*/
        if (state == STATE.GAME) {
            renderer.renderMesh(mesh);
        }
        else if (state == STATE.MENU) {

        }
        window.swapBuffers();
    }

    public static void main(String[] args) {
        new Main().start();
    }
}
