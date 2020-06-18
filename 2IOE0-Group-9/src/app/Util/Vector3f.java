package app.Util;

public class Vector3f {
    private float x, y, z;

    public Vector3f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void set(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }

     private float length() {
        double xSquare = Math.pow(x, 2f);
        double ySquare = Math.pow(y, 2f);
        double zSquare = Math.pow(z, 2f);
        double hypo = Math.sqrt(xSquare + ySquare + zSquare);

        return (float) hypo;
    }
}
