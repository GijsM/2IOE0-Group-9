/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.graphics;

/**
 *
 * @author iris
 * https://www.youtube.com/watch?v=WMiggUPst-Q&list=PLRIWtICgwaX0u7Rf9zkZhLoLuZVfUksDP&index=2
 */
public class RawModel {
    
    private int vaoID;
    private int vertexCount;
    public float[] positions;
    public float[] colors;
    public int[] indices;
    
    public RawModel(int vaoID, int vertexCount, float[] positions , float[] colours, int[] indices) {
        this.vaoID = vaoID;
        this.vertexCount = vertexCount;
        this.positions = positions;
        this.colors = colours;
        this.indices = indices;

    }

    public int getVaoID() {
        return vaoID;
    }

    public int getVertexCount() {
        return vertexCount;
    }
    
}
