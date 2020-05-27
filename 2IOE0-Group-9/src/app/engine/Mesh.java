package app.engine;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.CallbackI;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL30.*;

public class Mesh {
    private float[] vertices;
    private float[] indices;

    private int vao;
    private int v_id;
    private int u_id;

    private static List<Mesh> meshList = new ArrayList<Mesh>();

    public Mesh(float[] vertices, float[] indices) {
        this.vertices = vertices;
        this.indices = indices;

        vao = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(vao);

        v_id = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, v_id);
        glBufferData(GL_ARRAY_BUFFER, CreateBuffer(vertices), GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(0, vertices.length/3, GL_FLOAT, false, 0, 0);
        GL15.glBindBuffer(GL_ARRAY_BUFFER, 0);

        u_id = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, u_id);
        glBufferData(GL_ARRAY_BUFFER, CreateBuffer(indices), GL_STATIC_DRAW);

        glBindBuffer(GL_ARRAY_BUFFER, 0);

        meshList.add(this);
    }

    public void Render(){
        glDrawArrays(GL_TRIANGLES, 0, 6);
    }

    public void Bind() {
        GL30.glBindVertexArray(vao);
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        glBindBuffer(GL_ARRAY_BUFFER, v_id);
        glVertexAttribPointer(0, 2, GL_FLOAT, false, 0, 0);

        glBindBuffer(GL_ARRAY_BUFFER, u_id);
        glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);
    }

    public void Unbind() {
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        GL30.glBindVertexArray(0);
    }

    public FloatBuffer CreateBuffer(float[] data) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }

    public void CleanUp() {
        GL30.glDeleteVertexArrays(vao);
        GL15.glDeleteBuffers(v_id);
        GL30.glDeleteBuffers(u_id);
        GL30.glDisableVertexAttribArray(0);

//        GL30.glBindBuffer(GL30.GL_VERTEX_ARRAY, 0);
//        GL30.glBindVertexArray(0);
//        GL30.glBindVertexArray(1);
    }

    public static void CleanAllMesh() {
        for (int i = 0; i < meshList.size(); i++) {
            meshList.get(i).CleanUp();
        }
    }

    public int getVAO() {
        return vao;
    }

    public int getV_id() {
        return v_id;
    }

    public int getU_id() {
        return u_id;
    }

    public float[] getIndices() {
        return indices;
    }

    public float[] getVertices() {
        return vertices;
    }
}
