package app.Util;

public class Color {
    public float r = 1;
    public float g = 1;
    public float b = 1;
    public float a = 1;


    public Color (float r, float g, float b, float a) {
        this.r = MathClamp.Clamp(r, 0, 1);
        this.g = MathClamp.Clamp(g, 0, 1);;
        this.b = MathClamp.Clamp(b, 0, 1);;
        this.a = MathClamp.Clamp(a, 0, 1);;
    }

    public Color (float r, float g, float b) {
        this.r = MathClamp.Clamp(r, 0, 1);
        this.g = MathClamp.Clamp(g, 0, 1);;
        this.b = MathClamp.Clamp(b, 0, 1);;
    }

    public static Color color(float r, float g, float b) {
        return new Color(r,g,b);
    }

    public boolean Compare(Color c) {
        return c.r == r && c.g == g && c.b == b && c.a == a;
    }
}
