package app.engine;

import org.joml.Matrix4f;
import org.lwjgl.opengl.GL;

import app.graphics.Camera;
import app.graphics.Transformation;
import app.Game.Object.GameObject;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;

import static org.lwjgl.opengl.GL11.glClear;

public class Coordinates {

    /**
     * Field of View in Radians
     */
    private static final float FOV = (float) Math.toRadians(60.0f);

    private static final float Z_NEAR = 0.01f;

    private static final float Z_FAR = 1000.f;
    
    private final Transformation transformation;

    private Shader shader;

    public Coordinates() {
    	transformation = new Transformation();
    }

    public void init(Window window) throws Exception {
    	GL.createCapabilities();
        // Create shader
        shader = new Shader("DefaultShader");
        shader.createVertexShader(Shader.loadResource("../Graphics/shaders/vertex.vs"));
        shader.createFragmentShader(Shader.loadResource("../Graphics/shaders/fragment.fs"));
        shader.link();

        // Create projection matrix
        shader.createUniform("projectionMatrix");
        shader.createUniform("modelViewMatrix");      
    }

    public void clear() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    public void render(Window window, Camera camera, GameObject[] objects) {
        clear();

        shader.bind();
        
        // Projection matrix
        Matrix4f projectionMatrix = transformation.getProjectionMatrix(FOV, window.getWidth(), window.getHeight(), Z_NEAR, Z_FAR);
        shader.pushUniform("projectionMatrix", projectionMatrix);
        
     // Update view Matrix
        Matrix4f viewMatrix = transformation.getViewMatrix(camera);
        
        for (GameObject obj: objects) {
        	Matrix4f modelViewMatrix = transformation.getModelViewMatrix(obj, viewMatrix);
        	shader.pushUniform("modelViewMatrix", modelViewMatrix);
        	obj.getMesh().render();
        }

        shader.unbind();
    }

    public void cleanup() {
        if (shader != null) {
            shader.unbind();
        }
    }
}
