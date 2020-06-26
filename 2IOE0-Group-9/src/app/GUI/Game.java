package app.GUI;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL20.glBindAttribLocation;
import static org.lwjgl.opengl.GL20.glValidateProgram;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_Q;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_E;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.opengl.GL11.*;

import app.Game.Map.GameMap;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GLUtil;
import org.lwjgl.system.Callback;

import app.Window;
import app.Game.Object.GameObject;
import app.Game.Object.Tree;
import app.Input.Mouse;
import app.engine.Shader;
import app.graphics.Camera;
import app.graphics.Texture;
import app.graphics.Transformation;

import java.util.List;
import java.util.Random;


public class Game extends State {
    private static Game instance = null;
    protected static StateManager stateManager;
    protected static GUI gui;
    protected static Mouse mouse;
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
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_LIGHTING);
        glEnable(GL_LIGHT0);
        glColorMaterial ( GL_FRONT_AND_BACK, GL_AMBIENT_AND_DIFFUSE );
//        glDisable(GL_CULL_FACE);
    	gui = GUI.getInstance();
    	mouse = Mouse.getInstance();
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


		// Rotate camera to 2D"ish" view
		camera.movePosition(1.6f, -0.75f, 0f);
		camera.setRotation(45f, -90f, 100f);
		
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
        if (window.isKeyPressed(GLFW_KEY_Q)) {
            cameraVec.y = 1;
        } else if (window.isKeyPressed(GLFW_KEY_E)) {
            cameraVec.y = -1;
        }
        if (window.isKeyPressed(GLFW_KEY_ESCAPE)) {
        	System.out.println("Game has been paused");
        	stateManager.toPause();
        }
    	
        if (mouse.isRightButtonPressed()) {
    		Vector2f rotVec = mouse.getDisplVec();
    		camera.moveRotation(rotVec.x * MOUSE_SENSITIVITY, rotVec.y * MOUSE_SENSITIVITY,0);
    	}

    	camera.movePosition(cameraVec.x * CAMERA_POS_STEP, cameraVec.y * CAMERA_POS_STEP, cameraVec.z * CAMERA_POS_STEP);
    }
  

    public void update() {
    	controlCamera();
    	
    	GL13.glEnable(GL13.GL_DEPTH_TEST);
    
        // Uniforms required for 3D camera
        try {
        	shader.createUniform("projectionMatrix");
			shader.createUniform("texture_sampler");
			//shader.createUniform("lightPos");
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
        	Matrix4f modelViewMatrix = transformation.getModelViewMatrix((Tree) obj, viewMatrix);
        	GL20.glUniformMatrix4fv(GL20.glGetUniformLocation(shader.program,"modelMatrix"),false,transformation.getModelMatrix((Tree) obj).get(new float[16]));
            GL20.glUniformMatrix4fv(GL20.glGetUniformLocation(shader.program,"viewMatrix"),false,viewMatrix.get(new float[16]));
            GL20.glUniformMatrix4fv(GL20.glGetUniformLocation(shader.program,"modelViewMatrix"),false,modelViewMatrix.get(new float[16]));
            obj.getMesh().render();
        }

        shader.setUniform("texture_sampler", 0);
        GL20.glUniform3f(GL20.glGetUniformLocation(shader.program,"lightPos"),1.2f,1,0);
        GL20.glUniform3f(GL20.glGetUniformLocation(shader.program,"viewPos"),camera.getPosition().x,camera.getPosition().y,camera.getPosition().z);
        shader.unbind();

        
        
        map.update();
    }

    public void render() { }
}

