package app.engine;

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.system.MemoryStack;

import app.Util.Color;
import app.Util.Matrix4X4;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static org.lwjgl.opengl.GL20.*;

public class Shader {
    private int program;
    private int vertexShaderId;
    private int fragmentShaderId;
    private int vs;
    
    private final Map<String, Integer> uniforms;

    public Shader (String fileName) {
        program = glCreateProgram();
        uniforms = new HashMap<>();

        glBindAttribLocation(program, 0, "vertices");
        glBindAttribLocation(program, 1, "uv");
        glValidateProgram(program);
    }
    
    
    protected int createShader(String shaderCode, int shaderType) throws Exception {
        int shaderId = glCreateShader(shaderType);
        if (shaderId == 0) {
            throw new Exception("Error creating shader. Type: " + shaderType);
        }

        glShaderSource(shaderId, shaderCode);
        glCompileShader(shaderId);

        if (glGetShaderi(shaderId, GL_COMPILE_STATUS) == 0) {
            throw new Exception("Error compiling Shader code: " + glGetShaderInfoLog(shaderId, 1024));
        }

        glAttachShader(program, shaderId);

        return shaderId;
    }
    
    public void createVertexShader(String shaderCode) throws Exception {
        vertexShaderId = createShader(shaderCode, GL_VERTEX_SHADER);
    }

    public void createFragmentShader(String shaderCode) throws Exception {
        fragmentShaderId = createShader(shaderCode, GL_FRAGMENT_SHADER);
    }
    
    public void createUniform(String uniformName) throws Exception {
        int uniformLocation = glGetUniformLocation(program, uniformName);
        if (uniformLocation < 0) {
            throw new Exception("Could not find uniform:" + uniformName);
        }
        uniforms.put(uniformName, uniformLocation);
    }

    public void setUniform(String name, float x, float y) {
        int location = glGetUniformLocation(program, name);
        if (location != -1) {
            glUniform2f(location, x, y);
        }
    }

    public void setUniform(String name, float x, float y, float z, float w) {
        int location = glGetUniformLocation(program, name);
        if (location != -1) {
            glUniform4f(location, x, y, z, w);
        }
    }

    public void setUniform(String name, Color c) {
        int location = glGetUniformLocation(program, name);
        if(location != 1) {
            glUniform4f(location, c.r, c.g, c.b, c.a);
        }
    }

    public void setUniform(String name, Matrix4X4 m) {
        int location = glGetUniformLocation(program, name);
        FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
        m.GetBuffer(buffer);
        if (location != 1) {
            glUniformMatrix4fv(location, false, buffer);
            buffer.flip();
        }
    }
    
    public void setUniform(String uniformName, Matrix4f value) {
        // Dump the matrix into a float buffer
        try (MemoryStack stack = MemoryStack.stackPush()) {
            glUniformMatrix4fv(uniforms.get(uniformName), false,
                               value.get(stack.mallocFloat(16)));
        }
    }
    
    public void pushUniform(String uniformName, Matrix4f value) {
        // Dump the matrix into a float buffer
        try (MemoryStack stack = MemoryStack.stackPush()) {
            glUniformMatrix4fv(uniforms.get(uniformName), false,
                               value.get(stack.mallocFloat(16)));
        }
    }
    
    public void link() throws Exception {
        glLinkProgram(program);
        if (glGetProgrami(program, GL_LINK_STATUS) == 0) {
            throw new Exception("Error linking Shader code: " + glGetProgramInfoLog(program, 1024));
        }

        if (vertexShaderId != 0) {
            glDetachShader(program, vertexShaderId);
        }
        if (fragmentShaderId != 0) {
            glDetachShader(program, fragmentShaderId);
        }

        glValidateProgram(program);
        if (glGetProgrami(program, GL_VALIDATE_STATUS) == 0) {
            System.err.println("Warning validating Shader code: " + glGetProgramInfoLog(program, 1024));
        }
    }

    public void bind() {
        glUseProgram(program);
    }

    public void unbind() {
        glUseProgram(0);
    }
    
    /*
     * Aux. Function for reading shaders
     */
    public static String loadResource(String fileName) throws Exception {
        StringBuilder sb = new StringBuilder();
        BufferedReader br;

        try {
        	br = new BufferedReader(new FileReader(new File("C:\\Users\\malte\\Documents\\GameDev\\Current\\2IOE0-Group-9\\res\\Shaders\\" + fileName)));
            String line;
            while ((line = br.readLine()) != null) {
            	sb.append(line);
            	sb.append("\n");
            }
            br.close();
        } catch (IOException e) {
        	e.printStackTrace();
        }
        
        return sb.toString();
    }
}
