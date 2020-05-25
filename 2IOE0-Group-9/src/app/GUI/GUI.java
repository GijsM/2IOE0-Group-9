package gui;

import static org.lwjgl.opengl.GL11.*;

import Input.Mouse;
import engine.*;
import math.Color;
import math.Matrix4X4;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class GUI {

    public static Color backgroundColor = Color.color(0, 1, 1);
    public static Color textColor = Color.color(1,1,1);
    private static GUISkin skin;

    private static Mesh mesh;
    private static Shader shader;
    private static Matrix4X4 ortho;

    private static char[] c;
    private static float xTemp;
    private static Font font;
    private static int boundTex = -1;
    private static Color boundColor = Color.color(1, 1, 1);

    private static Rect area = new Rect(0,0, Window.Width(), Window.Height());
    private static List<Rect> areas = new ArrayList<Rect>();

    public static void init() {
        float[] meshData = new float[] {0,1, 1, 1, 1, 0, 1, 0, 0, 0, 0, 1};
        mesh = new Mesh(meshData, meshData);
        shader = new Shader("DefaultShader");
        skin = new GUISkin("DefaultGUI");
        font = new Font("Candarai", 22);
    }

    public static void Start() {
        areas.clear();
        areas.add(Window.GetRect());
        area = areas.get(areas.size() - 1);

        glDisable(GL_DEPTH_TEST);
        shader.Bind();
        shader.SetUniform("matColor", backgroundColor);
        ortho = Matrix4X4.Ortho(0, Window.Width(), Window.Height(), 0, -1, 1);
        shader.SetUniform("projection", ortho);
        mesh.Bind();
    }

    public static boolean Button(String text, Rect r, String normalSystle, String hoverStyle) {
        return Button(text, r, skin.Get(normalSystle), skin.Get(hoverStyle));
    }

    public static boolean Button(String text, Rect r, GUIStyle normalStyle, GUIStyle hoverStyle) {
        Rect rf = r.AddPosition(area);

        if (rf.Contains(Mouse.Position())){
            Rect p = Box(r, hoverStyle);
            Label(text, (r.x * 2) - (text.length() * 4), r.y + r.height * 0.3f);

            if (Mouse.GetButtonDown(0)) {
                return true;
            }
        }
        else {
            Rect p = Box(r, normalStyle);
            Label(text, (r.x * 2) - (text.length() * 4), r.y + r.height * 0.3f);
        }
        return false;
    }

    public static Rect Box(Rect r, String style) {
        return Box(r, skin.Get(style));
    }

    public static Rect Box(Rect r, GUIStyle e) {
        if (e == null) {
            return null;
        }

        Texture t = skin.texture;

        Rect tl = new Rect(r.x, r.y, e.padding.x, e.padding.y);
        Rect tlu = new Rect(e.uv.x, e.uv.y, e.paddingUV.x, e.paddingUV.y);
        DrawTextureWithTexCoords(t, tl, tlu);

        Rect tr = new Rect((r.x + r.width) - e.padding.width, r.y, e.padding.width, e.padding.y);
        Rect tru = new Rect((e.uv.x + e.uv.width) - e.paddingUV.width, e.uv.y, e.paddingUV.width, e.paddingUV.y);
        DrawTextureWithTexCoords(t, tr, tru);

        Rect bl = new Rect(r.x, (r.y + r.height) - e.padding.height, e.padding.x, e.padding.height);
        Rect blu = new Rect(e.uv.x, (e.uv.y + e.uv.height) - e.paddingUV.height, e.paddingUV.x, e.paddingUV.height);
        DrawTextureWithTexCoords(t, bl, blu);

        Rect br = new Rect(tr.x, bl.y, e.padding.width, e.padding.height);
        Rect bru = new Rect(tru.x, blu.y, e.paddingUV.width, e.paddingUV.height);
        DrawTextureWithTexCoords(t, br, bru);

        Rect l = new Rect(r.x, r.y + e.padding.y, e.padding.x, r.height - (e.padding.y + e.padding.height));
        Rect lu = new Rect(e.uv.x, e.uv.y + e.paddingUV.y, e.paddingUV.x, e.uv.height - (e.paddingUV.y + e.paddingUV.height));
        DrawTextureWithTexCoords(t, l, lu);

        Rect ri = new Rect(tr.x, r.y + e.padding.y, e.padding.width, l.height);
        Rect ru = new Rect(tru.x, lu.y, e.paddingUV.width, lu.height);
        DrawTextureWithTexCoords(t, ri, ru);

        Rect ti = new Rect(r.x + e.padding.x, r.y, r.width - (e.padding.x + e.padding.width), e.padding.y);
        Rect tu = new Rect(e.uv.x + e.paddingUV.x, e.uv.y, e.uv.width - (e.paddingUV.x + e.paddingUV.width), e.paddingUV.y);
        DrawTextureWithTexCoords(t, ti, tu);

        Rect b = new Rect(ti.x, bl.y, ti.width, e.padding.height);
        Rect bu = new Rect(tu.x, blu.y, tu.width, e.paddingUV.height);
        DrawTextureWithTexCoords(t, b, bu);

        Rect c = new Rect(ti.x, l.y, ti.width, l.height);
        Rect cu = new Rect(tu.x, lu.y, tu.width, lu.height);
        DrawTextureWithTexCoords(t, c, cu);

        return c;
    }

    public static void Label(String text, float x, float y) {
        Map<Character, Glyph> chars = font.GetCharacters();
        xTemp = x;

        c = text.toCharArray();

        for (int i = 0; i < c.length; i++) {
            Glyph r = chars.get(c[i]);

            DrawTextureWithTexCoords(font.getTexture(), new Rect(xTemp, y, r.scaleX, r.scaleY), new Rect(r.x, r.y, r.w, r.h), textColor);

            xTemp += r.scaleX;
        }
    }

    public static void DrawTexture(Texture tex, Rect r) {
        DrawTextureWithTexCoords(tex, r, new Rect(0,0,1, 1));
    }

    public static void DrawTextureWithTexCoords(Texture tex, Rect drawRect, Rect uv) {
        DrawTextureWithTexCoords(tex, drawRect, uv, backgroundColor);
    }

    public static void DrawTextureWithTexCoords(Texture tex, Rect drawRect, Rect uv, Color c) {
        if (area == null) {
            return;
        }
        Rect r = area.GetIntersection(new Rect(drawRect.x + area.x, drawRect.y + area.y, drawRect.width, drawRect.height));
        if (r == null) {
            return;
        }

        float x = uv.x + ((((r.x - drawRect.x) - area.x) / drawRect.width) * uv.width);
        float y = uv.y + ((((r.y - drawRect.y) - area.y) / drawRect.height) * uv.height);
        Rect u = new Rect(x, y, (r.width / drawRect.width) * uv.width, (r.height / drawRect.height) * uv.height);

        if (tex.ID() != boundTex) {
            glBindTexture(GL_TEXTURE_2D, tex.ID());
        }
        shader.SetUniform("offset", u.x, u.y, u.width, u.height);
        shader.SetUniform("pixelScale", r.width, r.height);
        shader.SetUniform("screenPos", r.x, r.y);

        if (!boundColor.Compare(c)) {
            shader.SetUniform("matColor", c);
            boundColor = c;
        }
        mesh.Render();
    }

    public static void Window(Rect r, String title, Consumer<Integer> f, String style) {
        Window(r, title, f, skin.Get(style));
    }

    public static void Window(Rect r, String title, Consumer<Integer> f, GUIStyle style) {
        if (style != null) {
            Rect center = Box(r, style);
            Label(title, r.x + style.padding.x, r.y + 4);
            BeginArea(center);
        }
        else {
            Label(title, r.x, r.y);
            BeginArea(r);
        }

        f.accept(0);
        EndArea();
    }

    public static void BeginArea(Rect r) {
        areas.add(area.GetIntersection(new Rect(area.x + r.x, area.y + r.y, r.width, r.height)));
        area = areas.get(areas.size() - 1);
    }

    public static void EndArea() {
        if (areas.size() == 1) {
            return;
        }
        areas.remove(areas.size() - 1);
        area = areas.get(areas.size() - 1);
    }

    public static void Unbind() {
        mesh.Unbind();
    }

}
