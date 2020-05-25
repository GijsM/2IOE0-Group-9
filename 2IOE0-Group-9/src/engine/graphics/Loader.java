/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package engine.graphics;

import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import static org.lwjgl.stb.STBImage.*;
import org.lwjgl.system.MemoryStack;

/**
 *
 * @author iris
 * https://www.youtube.com/watch?v=WMiggUPst-Q&list=PLRIWtICgwaX0u7Rf9zkZhLoLuZVfUksDP&index=2
 */
public class Loader {

    //lists of created storedVAOs and storedVBOs
    private List<Integer> storedVAOs = new ArrayList<Integer>();
    private List<Integer> storedVBOs = new ArrayList<Integer>();
    private List<Integer> textures = new ArrayList<Integer>();

    /**
     * deletes all created storedVAOs and storedVBOs
     */
    public void clear() {
        storedVAOs.forEach((vao) -> {
            GL30.glDeleteVertexArrays(vao);
        });
        storedVBOs.forEach((vbo) -> {
            GL30.glDeleteBuffers(vbo);
        });
        textures.forEach((texture) -> {
            GL11.glDeleteTextures(texture);
        });
    }

    /**
     *
     * @param positions
     * @return new RawModel
     */
    public RawModel loadToVAO(float[] positions, float[] textureCoords,
            int[] indices) {
        int vaoID = createVAO();
        bindIndicesBuffer(indices);
        storedVAOs.add(vaoID);
        storeDataInAttributeList(0, 3, positions);
        storeDataInAttributeList(1, 2, textureCoords);
        unbindVAO();
        return new RawModel(vaoID, indices.length);
    }

    public int loadTexture(String path) {

        int textureID;
        int width, height;
        ByteBuffer image;

        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);
            IntBuffer comp = stack.mallocInt(1);

            image = stbi_load("res/" + path + ".png", w, h, comp, 4);
            if (image == null) {
                System.out.println("Failed to load texture file: " + path + "\n"
                        + stbi_failure_reason()
                );
            }
            width = w.get();
            height = h.get();
        }

        textureID = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, textureID);
        textures.add(textureID);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST); //sets MINIFICATION filtering to nearest
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST); //sets MAGNIFICATION filtering to nearest
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, image);

        return textureID;
    }

    /**
     *
     * @return
     */
    private int createVAO() {
        int vaoID = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(vaoID);

        return vaoID;
    }

    /**
     *
     * @param attributeNumber
     * @param data
     */
    private void storeDataInAttributeList(int attributeNumber, 
            int coordinateSize, float[] data) {
        int vboID = GL15.glGenBuffers();
        storedVBOs.add(vboID);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
        FloatBuffer buffer = storeDataInFloatBuffer(data);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(attributeNumber, coordinateSize, 
                GL11.GL_FLOAT, false, 0, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    }

    /**
     *
     */
    private void unbindVAO() {
        GL30.glBindVertexArray(0);
    }

    private void bindIndicesBuffer(int[] indices) {
        int vboID = GL15.glGenBuffers();
        storedVBOs.add(vboID);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboID);
        IntBuffer buffer = storeDataInIntBuffer(indices);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);

    }

    private IntBuffer storeDataInIntBuffer(int[] data) {
        IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }

    /**
     *
     * @param data
     * @return
     */
    private FloatBuffer storeDataInFloatBuffer(float[] data) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }

}
