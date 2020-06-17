package app.engine;

import app.Util.Vector2f;

public class Rect {
    public float x;
    public float y;
    public float width;
    public float height;

    public Rect(float x, float y, float w, float h) {
        this.x = x;
        this.y = y;
        width = w;
        height = h;
    }

    public void Set(float x, float y, float w, float h) {
        this.x = x;
        this.y = y;
        width = w;
        height = h;
    }

    public Rect AddPosition(Rect r) {
        return new Rect(r.x + x, r.y + y, width, height);
    }

    public boolean Contains(Vector2f v) {
        return v.x > x && v.x < x + width && v.y > y && v.y < y + height;
    }

    public boolean Intersects(Rect r) {
        if (x > r.x + r.width || x + width < r.x || y > r.y + r.height || y + height < r.y) {
            return false;
        }
        return true;
    }

    public Rect GetIntersection(Rect r) {
        if (!Intersects(r)) {
            return null;
        }
        Vector2f v = new Vector2f(Math.max(x, r.x), Math.max(y, r.y));
        return new Rect(v.x, v.y, Math.min(x + width, r.x + r.width) - v.x, Math.min(y + height, r.y + r.height) - v.y);
    }
}
