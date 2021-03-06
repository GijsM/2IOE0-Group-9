package app.engine;


import static org.lwjgl.stb.STBImage.stbi_image_free;
import static org.lwjgl.stb.STBImage.stbi_load;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.nio.IntBuffer;
import org.lwjgl.BufferUtils;


public class Texture {

    private String name = "";

    private int id;
    private int width;
    private int height;

    private static List<Texture> textureInstances = new ArrayList<Texture>();
    private static Texture tmp = null;

    public Texture(String fileName) {
        name = fileName;

        IntBuffer width = BufferUtils.createIntBuffer(1);
        IntBuffer height = BufferUtils.createIntBuffer(1);
        IntBuffer channels = BufferUtils.createIntBuffer(1);

        ByteBuffer data = stbi_load("./res/Textures/" + fileName, width, height, channels, 4);

        id = glGenTextures();
        this.width = width.get();
        this.height = height.get();

        glBindTexture(GL_TEXTURE_2D, id);

        glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, this.width, this.height, 0, GL_RGBA, GL_UNSIGNED_BYTE, data);
        stbi_image_free(data);

        textureInstances.add(this);
    }

    public Texture(int id, int width, int height) {
        this.id = id;
        this.width = width;
        this.height = height;
    }

    public int ID() {
        return id;
    }

    public void Bind() {
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, id);
    }

    public void Unbind() {
        glBindTexture(GL_TEXTURE_2D, 0);
    }

    public static Texture Find(String textureName) {
        for (int i = 0; i < textureInstances.size(); i++) {
            tmp = textureInstances.get(i);
            if (tmp.name.startsWith(textureName)) {
                return tmp;
            }
        }
        return null;
    }

    public static void CleanUp() {
        for (int i = 0; i < textureInstances.size(); i++) {
            glDeleteTextures(textureInstances.get(i).ID());
        }
    }

    public static List<Texture> GetTextures() {
        return textureInstances;
    }

    public int Width() {
        return width;
    }

    public int Height() {
        return height;
    }

}
