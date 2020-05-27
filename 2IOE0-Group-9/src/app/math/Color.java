package app.math;

public class Color {
    public float r = 1;
    public float g = 1;
    public float b = 1;
    public float a = 1;


    public Color (float r, float g, float b, float a) {
        this.r = Math.Clamp(r, 0, 1);
        this.g = Math.Clamp(g, 0, 1);;
        this.b = Math.Clamp(b, 0, 1);;
        this.a = Math.Clamp(a, 0, 1);;
    }

    public Color (float r, float g, float b) {
        this.r = Math.Clamp(r, 0, 1);
        this.g = Math.Clamp(g, 0, 1);;
        this.b = Math.Clamp(b, 0, 1);;
    }

    public static Color color(float r, float g, float b) {
        return new Color(r,g,b);
    }

    public boolean Compare(Color c) {
        return c.r == r && c.g == g && c.b == b && c.a == a;
    }
}
