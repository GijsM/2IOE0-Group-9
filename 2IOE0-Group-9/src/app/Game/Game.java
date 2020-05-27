package app.Game;

import java.util.Random;

import org.joml.Vector2f;
import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.*;

import Graphics.Camera;
import Graphics.Mesh;
import app.Renderer;
import app.Game.Map.GameMap;
import app.Game.Object.GameObject;
import app.Util.IRenderable;
import app.Util.IStartStopable;
import app.Util.IUpdateable;
import app.Util.MouseInput;
import app.Util.WindowManager;

public class Game implements IStartStopable, IRenderable, IUpdateable {

    private GameMap gamemap;
    private final Renderer renderer;
    private final Camera camera;
    private final Vector3f cameraVec;
    private Mesh mesh;
    private GameObject[] gameObjects;
    
    
    private boolean running;
    private static final float CAMERA_POS_STEP = 0.05f;
	private static final float MOUSE_SENSITIVITY = 0.2f;
    
    public Game() {
    	renderer = new Renderer();
    	camera = new Camera();
    	cameraVec = new Vector3f();
    }

    @Override
    public void start(WindowManager window) throws Exception {
		renderer.init(window);
		// Test Triangle
        float[] positions = new float[]{
                // V0
                -0.5f, 0.5f, 0.5f,
                // V1
                -0.5f, -0.5f, 0.5f,
                // V2
                0.5f, -0.5f, 0.5f,
                // V3
                0.5f, 0.5f, 0.5f,
                // V4
                -0.5f, 0.5f, -0.5f,
                // V5
                0.5f, 0.5f, -0.5f,
                // V6
                -0.5f, -0.5f, -0.5f,
            };
            float[] colours = new float[]{
                0.5f, 0.0f, 0.0f,
                0.0f, 0.5f, 0.0f,
                0.0f, 0.0f, 0.5f,
                0.0f, 0.5f, 0.5f,
                0.0f, 0.5f, 0.5f,
                0.0f, 0.5f, 0.5f,
            };
            int[] indices = new int[]{
                0, 1, 3, 3, 1, 2,
            };
            mesh = new Mesh(positions, colours, indices);
            GameObject obj1 = new GameObject(gamemap, mesh);
            obj1.setScale(0.5f);
            obj1.setPosition(0, 0, -2);
            GameObject obj2 = new GameObject(gamemap, mesh);
            obj2.setScale(0.5f);
            obj2.setPosition(0.5f, 0.5f, -2);
            
            gameObjects = new GameObject[] {obj1, obj2};
            //
            running = true;
            Random rng = new Random();
            GameMap gMap = new GameMap(rng);
            setGamemap(gMap);

    }

    @Override
    public void stop() {
        running = false;
        renderer.cleanup();
        mesh.cleanUp();
    }
    
    
    public void input(WindowManager window, MouseInput mouseInput) {
        cameraVec.set(0, 0, 0);
        if (window.isKeyPressed(GLFW_KEY_W)) {
            cameraVec.z = -1;
        } else if (window.isKeyPressed(GLFW_KEY_S)) {
            cameraVec.z = 1;
        }
        if (window.isKeyPressed(GLFW_KEY_A)) {
            cameraVec.x = -1;
        } else if (window.isKeyPressed(GLFW_KEY_D)) {
            cameraVec.x = 1;
        }
        if (window.isKeyPressed(GLFW_KEY_Z)) {
            cameraVec.y = -1;
        } else if (window.isKeyPressed(GLFW_KEY_X)) {
            cameraVec.y = 1;
        }
    }

    //TODO perhaps call update and render in different threads for performance (Can do this at a later stage if performance becomes a problem)
    @Override
    public void update(float interval, MouseInput mouseInput) {
//        gamemap.update();
    	camera.movePosition(cameraVec.x * CAMERA_POS_STEP, cameraVec.y * CAMERA_POS_STEP, cameraVec.z * CAMERA_POS_STEP);
    	
    	if (mouseInput.isRightButtonPressed()) {
    		Vector2f rotVec = mouseInput.getDisplVec();
    		camera.moveRotation(rotVec.x * MOUSE_SENSITIVITY, rotVec.y * MOUSE_SENSITIVITY,0);
    	}
    }

    @Override
    public void render(WindowManager window) {
    	renderer.render(window, camera, gameObjects);
//        gamemap.render();

    }
    
    public GameMap getGamemap() {
        return gamemap;
    }

    public void setGamemap(GameMap gamemap) {
        this.gamemap = gamemap;
    }    
}