package app.engine;

import org.joml.Matrix4f;
import org.lwjgl.opengl.GL;

import app.graphics.Camera;
import app.graphics.Mesh;		// change to other mesh
import app.engine.Shader2;
import app.graphics.Transformation;
import app.Game.Object.GameObject;
import app.engine.Window;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

import java.io.InputStream;
import java.util.Scanner;

public class Coordinates {

    /**
     * Field of View in Radians
     */
    private static final float FOV = (float) Math.toRadians(60.0f);

    private static final float Z_NEAR = 0.01f;

    private static final float Z_FAR = 1000.f;
    
    private final Transformation transformation;

    private Shader2 shader2;

    public Coordinates() {
    	transformation = new Transformation();
    }

    public void init(Window window) throws Exception {
    	GL.createCapabilities();
        // Create shader
        shader2 = new Shader2();
        shader2.createVertexShader(loadResource("../Graphics/shaders/vertex.vs"));
        shader2.createFragmentShader(loadResource("../Graphics/shaders/fragment.fs"));
        shader2.link();

        // Create projection matrix
        shader2.createUniform("projectionMatrix");
        shader2.createUniform("modelViewMatrix");      
    }

    public void clear() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    public void render(Window window, Camera camera, GameObject[] objects) {
        clear();

        shader2.bind();
        
        // Projection matrix
        Matrix4f projectionMatrix = transformation.getProjectionMatrix(FOV, window.getWidth(), window.getHeight(), Z_NEAR, Z_FAR);
        shader2.setUniform("projectionMatrix", projectionMatrix);
        
     // Update view Matrix
        Matrix4f viewMatrix = transformation.getViewMatrix(camera);
        
        for (GameObject obj: objects) {
        	Matrix4f modelViewMatrix = transformation.getModelViewMatrix(obj, viewMatrix);
        	shader2.setUniform("modelViewMatrix", modelViewMatrix);
        	obj.getMesh().render();
        }

        shader2.unbind();
    }

    public void cleanup() {
        if (shader2 != null) {
            shader2.cleanup();
        }
    }
    
    
    /*
     * Aux. Function for reading shaders
     */
    public static String loadResource(String fileName) throws Exception {
        String result;
        try (InputStream in = Coordinates.class.getResourceAsStream(fileName);
             Scanner scanner = new Scanner(in, java.nio.charset.StandardCharsets.UTF_8.name())) {
            result = scanner.useDelimiter("\\A").next();
        }
        return result;
    }
}
