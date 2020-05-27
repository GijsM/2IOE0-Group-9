package app;


import org.joml.Matrix4f;
import org.lwjgl.opengl.GL;

import Graphics.Camera;
import Graphics.Mesh;
import Graphics.ShaderProgram;
import Graphics.Transformation;
import app.Game.Object.GameObject;
import app.Util.WindowManager;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

import java.io.InputStream;
import java.util.Scanner;

public class Renderer {

    /**
     * Field of View in Radians
     */
    private static final float FOV = (float) Math.toRadians(60.0f);

    private static final float Z_NEAR = 0.01f;

    private static final float Z_FAR = 1000.f;
    
    private final Transformation transformation;

    private ShaderProgram shaderProgram;

    public Renderer() {
    	transformation = new Transformation();
    }

    public void init(WindowManager window) throws Exception {
    	GL.createCapabilities();
        // Create shader
        shaderProgram = new ShaderProgram();
        shaderProgram.createVertexShader(loadResource("../Graphics/shaders/vertex.vs"));
        shaderProgram.createFragmentShader(loadResource("../Graphics/shaders/fragment.fs"));
        shaderProgram.link();

        // Create projection matrix
        shaderProgram.createUniform("projectionMatrix");
        shaderProgram.createUniform("modelViewMatrix");      
    }

    public void clear() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    public void render(WindowManager window, Camera camera, GameObject[] objects) {
        clear();

        shaderProgram.bind();
        
        // Projection matrix
        Matrix4f projectionMatrix = transformation.getProjectionMatrix(FOV, window.getWidth(), window.getHeight(), Z_NEAR, Z_FAR);
        shaderProgram.setUniform("projectionMatrix", projectionMatrix);
        
     // Update view Matrix
        Matrix4f viewMatrix = transformation.getViewMatrix(camera);
        
        for (GameObject obj: objects) {
        	Matrix4f modelViewMatrix = transformation.getModelViewMatrix(obj, viewMatrix);
        	shaderProgram.setUniform("modelViewMatrix", modelViewMatrix);
        	obj.getMesh().render();
        }

        shaderProgram.unbind();
    }

    public void cleanup() {
        if (shaderProgram != null) {
            shaderProgram.cleanup();
        }
    }
    
    
    /*
     * Aux. Function for reading shaders
     */
    public static String loadResource(String fileName) throws Exception {
        String result;
        try (InputStream in = Renderer.class.getResourceAsStream(fileName);
             Scanner scanner = new Scanner(in, java.nio.charset.StandardCharsets.UTF_8.name())) {
            result = scanner.useDelimiter("\\A").next();
        }
        return result;
    }
}
