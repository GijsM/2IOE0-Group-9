package app.GUI;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_X;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_Z;

import app.Game.Map.GameMap;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import app.Window;
import app.Game.Object.GameObject;
import app.engine.Shader;
import app.graphics.Camera;
import app.graphics.Transformation;

import java.util.List;
import java.util.Random;


public class Game extends State {
    private static Game instance = null;
    protected static StateManager stateManager;
    protected static GUI gui;
    protected static Window window;
	protected static Camera camera;
	protected static Transformation transformation;
	protected static Shader shader;
    
	private final static Vector3f cameraVec = new Vector3f();
    private static final float FOV = (float) Math.toRadians(60.0f);
    private static final float Z_NEAR = 0.01f;
    private static final float Z_FAR = 1000.f;
    private static final float CAMERA_POS_STEP = 0.05f;
	private static final float MOUSE_SENSITIVITY = 0.2f;
    private static List<GameObject> gameObjects;
    private GameMap map;

    public static Game getInstance() {
        if(instance == null) {
            instance = new Game();
        }

        return instance;
    }
    
    public void init() {
    	gui = GUI.getInstance();
    	window = Window.getInstance();
    	stateManager = StateManager.getInstance();
		camera = Camera.getInstance();
		transformation = Transformation.getInstance();
		shader = new Shader("GameShader");
		map = new GameMap(new Random());
        
		try {
			shader.createVertexShader(Shader.loadResource("GameShader.vs"));
			shader.createFragmentShader(Shader.loadResource("GameShader.fs"));
			shader.link();
		} catch (Exception e) {
			e.printStackTrace();
		}
		makeObjects();
		camera.movePosition(-0.1f, 0.1f, 0f);
    }
    
    public void makeObjects() {
    	gameObjects = this.map.getRooms().get(0).getGameobjects();
    	map.render();
    	//update();
    }
    
    public void controlCamera() {
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
        
    	camera.movePosition(cameraVec.x * CAMERA_POS_STEP, cameraVec.y * CAMERA_POS_STEP, cameraVec.z * CAMERA_POS_STEP);
    }
  

    public void update() {
    	controlCamera();

        // Uniforms required for 3D camera
        try {
        	shader.createUniform("projectionMatrix");
			shader.createUniform("modelViewMatrix");
		} catch (Exception e) {
			e.printStackTrace();
		}
        shader.bind();
        
        // Projection matrix
        Matrix4f projectionMatrix = transformation.getProjectionMatrix(FOV, window.getWidth(), window.getHeight(), Z_NEAR, Z_FAR);
        shader.setUniform("projectionMatrix", projectionMatrix);
        
        // Update view Matrix
        Matrix4f viewMatrix = transformation.getViewMatrix(camera);

        for (GameObject obj: gameObjects) {
        	Matrix4f modelViewMatrix = transformation.getModelViewMatrix(obj, viewMatrix);
        	shader.setUniform("modelViewMatrix", modelViewMatrix);
        	obj.getMesh().render();
        }

        shader.unbind();
        
        
        map.update();
    }

    public void render() { }
}

