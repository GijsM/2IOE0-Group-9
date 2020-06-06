package app.Game;

import java.util.Random;

import org.joml.Vector2f;
import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.*;

import app.graphics.Camera;
import app.engine.Mesh2;
import app.engine.Coordinates;
import app.Game.Map.GameMap;
import app.Game.Object.GameObject;
import app.Input.Mouse;
import app.engine.Window;

public class Game {

    private GameMap gamemap;
    private final Coordinates coordinates;
    private final Camera camera;
    private final Vector3f cameraVec;
    private Mesh2 mesh;
    private GameObject[] gameObjects;
    
    
    private boolean running;
    private static final float CAMERA_POS_STEP = 0.05f;
	private static final float MOUSE_SENSITIVITY = 0.2f;
    
    public Game() {
    	coordinates = new Coordinates();
    	camera = new Camera();
    	cameraVec = new Vector3f();
    }
        
    public void start() throws Exception {
		coordinates.init();
		running = true;
		/*
		 * TEST RENDERING
		 */
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
            mesh = new Mesh2(positions, colours, indices);
            GameObject obj1 = new GameObject(mesh);
            obj1.setScale(0.5f);
            obj1.setPosition(0, 0, -2);
            GameObject obj2 = new GameObject(mesh);
            obj2.setScale(0.5f);
            obj2.setPosition(0.5f, 0.5f, -2);
            gameObjects = new GameObject[] {obj1, obj2};
    		/*
    		 * END : TEST RENDERING
    		 */
            

            /*
             * WIP : Render Map from GameMap/GameObjects/Rooms and pass to gameObjects array
             */
              Random rng = new Random();
              GameMap gMap = new GameMap(rng);
            //  gMap.getRooms();
            
            // Create mesh of rooms/map
            // for (room: GameMap) {
            //	mesh = new Mesh(room.positions, room.colours, room.indices);
            // 	gameObjects.add(new GameObject(mesh);

            /*
             * WIP : Render Map from GameMap/GameObjects/Rooms and pass to gameObjects array
             */   
    }

    
    public void stop() {
        running = false;
        coordinates.cleanup();
        mesh.cleanUp();
    }
    
    
    public void input(Mouse mouseInput) {
        cameraVec.set(0, 0, 0);
        if (Window.isKeyPressed(GLFW_KEY_W)) {
            cameraVec.z = -1;
        } else if (Window.isKeyPressed(GLFW_KEY_S)) {
            cameraVec.z = 1;
        }
        if (Window.isKeyPressed(GLFW_KEY_A)) {
            cameraVec.x = -1;
        } else if (Window.isKeyPressed(GLFW_KEY_D)) {
            cameraVec.x = 1;
        }
        if (Window.isKeyPressed(GLFW_KEY_Z)) {
            cameraVec.y = -1;
        } else if (Window.isKeyPressed(GLFW_KEY_X)) {
            cameraVec.y = 1;
        }
    }

    //TODO perhaps call update and render in different threads for performance (Can do this at a later stage if performance becomes a problem)
    
    public void update(float interval, Mouse mouseInput) {
    	// TODO: Implement update() in GameMap.java 
//        gamemap.update();
    	camera.movePosition(cameraVec.x * CAMERA_POS_STEP, cameraVec.y * CAMERA_POS_STEP, cameraVec.z * CAMERA_POS_STEP);
    	
    	if (mouseInput.isRightButtonPressed()) {
    		Vector2f rotVec = mouseInput.getDisplVec();
    		camera.moveRotation(rotVec.x * MOUSE_SENSITIVITY, rotVec.y * MOUSE_SENSITIVITY,0);
    	}
    }

    public void render() {
    	coordinates.render(camera, gameObjects);
    } 
    
    public GameMap getGamemap() {
        return gamemap;
    }

    public void setGamemap(GameMap gamemap) {
        this.gamemap = gamemap;
    }
}
