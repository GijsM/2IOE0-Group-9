package app.GUI;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_X;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_Z;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

import app.Window;
import app.Game.Object.GameObject;
import app.engine.Mesh2;
import app.engine.Shader;
import app.graphics.Camera;
import app.graphics.Transformation;


public class Game {
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
    private static GameObject[] gameObjects;
    
    public static void init() { 
    	gui = GUI.getInstance();
    	window = Window.getInstance();
    	stateManager = StateManager.getInstance();
		camera = Camera.getInstance();
		transformation = Transformation.getInstance();
		shader = new Shader("GameShader");
        
		try {
			shader.createVertexShader(Shader.loadResource("GameShader.vs"));
			shader.createFragmentShader(Shader.loadResource("GameShader.fs"));
			shader.link();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		makeObjects();
    }
    
    public static void makeObjects() {
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
            Mesh2 mesh = new Mesh2(positions, colours, indices);
            GameObject obj1 = new GameObject(mesh);
            obj1.setScale(0.5f);
            obj1.setPosition(0, 0, -2);
            GameObject obj2 = new GameObject(mesh);
            obj2.setScale(0.5f);
            obj2.setPosition(0.5f, 0.5f, -2);
            gameObjects = new GameObject[] {obj1, obj2};
            
            update();
    }
    
    public static void controlCamera() {
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
  

    public static void update() {
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
    }

    public void render() { }
}

