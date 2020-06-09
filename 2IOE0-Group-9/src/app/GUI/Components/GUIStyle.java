package app.GUI.Components;

import app.engine.Rect;
import app.engine.Texture;

public class GUIStyle {
    public String name;
    public Rect offset;
    public Rect padding;
    public Rect uv;
    public Rect paddingUV;

    public GUIStyle(String name, Texture t, Rect offset, Rect padding) {
        this.name = name;
        this.offset = offset;
        this.padding = padding;

        float w = t.getWidth();
        float h = t.getHeight();

        uv = new Rect(offset.x / w, offset.y / h, offset.width / w, offset.height / h);
        paddingUV = new Rect(padding.x / w, padding.y / h, padding.width / w, padding.height / h);
    }

}
