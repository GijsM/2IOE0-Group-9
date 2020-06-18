package app.Util;

import java.io.Serializable;
import java.nio.FloatBuffer;

public class Vector3f extends Vector implements Serializable, ReadableVector3f, WritableVector3f {

    private static final long serialVersionUID = 1L;

    public float x, y, z;

    /**
     * Constructor for Vector3f.
     */
    public Vector3f(){super();}

    /**
     * Constructor
     * @param src
     */
    public Vector3f(ReadableVector3f src) {
        set(src);
    }


    /**
     * Constructor
     * @param x
     * @param y
     * @param z
     */
    public Vector3f(float x, float y, float z) {set(x, y, z);}

    public void set(float x, float y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void set(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Load from another Vector3f
     * @param src
     * @return
     */
    public Vector3f set(ReadableVector3f src) {
        x = src.getX();
        y = src.getY();
        z = src.getZ();
        return this;
    }

    public float lengthSquared() {
        return x * x + y * y + z * z;
    }

    public Vector3f translate(float x, float y, float z) {
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }

    public Vector load(FloatBuffer buf) {
        x = buf.get();
        y = buf.get();
        z = buf.get();
        return this;
    }

    public static Vector3f sub(Vector3f left, Vector3f right, Vector3f dest) {
        if (dest == null) {
            return new Vector3f(left.x - right.x, left.y - right.y, left.z - right.z);
        } else {
            dest.set(left.x - right.x, left.y - right.y, left.z - right.z);
            return dest;
        }
    }

    public Vector negate() {
        x = -x;
        y = -y;
        z = -z;
        return this;
    }

    public Vector3f negate(Vector3f dest) {
        if (dest == null) {
            dest = new Vector3f();
        }
        dest.x = -x;
        dest.y = -y;
        dest.z = -z;
        return dest;
    }

    /**
     * Add a vector to another vector and place the result in a destination
     * vector.
     *
     * @param left The LHS vector
     * @param right The RHS vector
     * @param dest The destination vector, or null if a new vector is to be
     * created
     * @return the sum of left and right in dest
     */
    public static Vector3f add(Vector3f left, Vector3f right, Vector3f dest) {
        if (dest == null) {
            return new Vector3f(left.x + right.x, left.y + right.y, left.z + right.z);
        } else {
            dest.set(left.x + right.x, left.y + right.y, left.z + right.z);
            return dest;
        }
    }

    /**
     * Normalise this vector and place the result in another vector.
     * @param dest The destination vector, or null if a new vector is to be created
     * @return the normalised vector
     */
    public Vector3f normalise(Vector3f dest) {
        float l = length();

        if (dest == null)
            dest = new Vector3f(x / l, y / l, z / l);
        else
            dest.set(x / l, y / l, z / l);

        return dest;
    }

    public float length() {
        double xSquare = Math.pow(x, 2f);
        double ySquare = Math.pow(y, 2f);
        double zSquare = Math.pow(z, 2f);
        double hypo = Math.sqrt(xSquare + ySquare + zSquare);

        return (float) hypo;
    }

    public Vector store(FloatBuffer buf) {

        buf.put(x);
        buf.put(y);
        buf.put(z);

        return this;
    }

    public Vector scale(float scale) {
        x *= scale;
        y *= scale;
        z *= scale;
        return this;
    }


    public float getX() { return x; }

    public void setX(float x) { this.x = x; }

    public float getY() { return y; }

    public void setY(float y) { this.y = y; }

    public void setZ(float z) { this.z = z; }

    public float getZ() { return z; }

}
