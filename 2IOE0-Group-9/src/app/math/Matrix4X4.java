package math;

import java.nio.FloatBuffer;

public class Matrix4X4 {

    private float[][] m = new float[4][4];

    public Matrix4X4() {
        SetIdentity();
    }

    public final void SetIdentity() {
        m[0][0] = 1; m[0][1] = 0; m[0][2] = 0; m[0][3] = 0;
        m[1][0] = 0; m[1][1] = 1; m[1][2] = 0; m[1][3] = 0;
        m[2][0] = 0; m[2][1] = 0; m[2][2] = 1; m[2][3] = 0;
        m[3][0] = 0; m[3][1] = 0; m[3][2] = 0; m[3][3] = 1;
    }

    public void GetBuffer(FloatBuffer buffer) {
        buffer.put(m[0][0]).put(m[0][1]).put(m[0][2]).put(m[0][3]);
        buffer.put(m[1][0]).put(m[1][1]).put(m[1][2]).put(m[1][3]);
        buffer.put(m[2][0]).put(m[2][1]).put(m[2][2]).put(m[2][3]);
        buffer.put(m[3][0]).put(m[3][1]).put(m[3][2]).put(m[3][3]);
        buffer.flip();
    }

    public static Matrix4X4 Ortho(float left, float right, float bottom, float top, float near, float far) {
        Matrix4X4 matrix = new Matrix4X4();

        float width = right - left;
        float height = top - bottom;
        float depth = far - near;

        matrix.m[0][0] = 2f / width;
        matrix.m[1][1] = 2f / height;
        matrix.m[2][2] = 2f / depth;
        matrix.m[3][0] = -(right + left) / width;
        matrix.m[3][1] = -(top + bottom) / height;
        matrix.m[3][2] = -(far + near) / depth;

        return matrix;
    }

}
