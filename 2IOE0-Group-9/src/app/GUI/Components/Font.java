package app.GUI.Components;

import static org.lwjgl.opengl.GL11.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import app.Util.Vector2f;
import app.engine.Texture;

public class Font {
    private int fontID;
    private BufferedImage bufferedImage;
    private Vector2f imageSize;
    private java.awt.Font font;
    private FontMetrics fontMetrics;
    private Texture texture;

    private Map<Character, Glyph> chars = new HashMap<Character, Glyph>();

    public Font(String name, float size) {
        try {
            font = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, new File(".\\2IOE0-Group-9\\res\\Font\\" + name + ".ttf")).deriveFont(size);
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        GenerateFont();
    }

    public Font(java.awt.Font font) {
        this.font = font;
        GenerateFont();
    }

    private void GenerateFont() {
        GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
        Graphics2D graphics = gc.createCompatibleImage(1, 1, Transparency.TRANSLUCENT).createGraphics();
        graphics.setFont(font);

        fontMetrics = graphics.getFontMetrics();
        imageSize = new Vector2f(1024, 1024);
        bufferedImage = graphics.getDeviceConfiguration().createCompatibleImage((int) imageSize.x, (int) imageSize.y, Transparency.TRANSLUCENT);

        fontID = glGenTextures();
        glEnable(GL_TEXTURE_2D);
        glBindTexture(GL_TEXTURE_2D, fontID);
        glTexImage2D(GL_TEXTURE_2D, 0 , GL_RGBA, (int) imageSize.x, (int) imageSize.y, 0, GL_RGBA, GL_UNSIGNED_BYTE, GenerateImage());

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

        texture = new Texture(fontID, 1024, 1024);
    }

    private ByteBuffer GenerateImage() {
        Graphics2D g = (Graphics2D) bufferedImage.getGraphics();
        g.setFont(font);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        DrawCharacters(g);
        return CreateBuffer();
    }

    private void DrawCharacters(Graphics2D g) {
        int tempX = 0;
        int tempY = 0;
        float h = (float) (fontMetrics.getMaxAscent() + fontMetrics.getMaxDescent());

        for (int i = 32; i < 256; i++) {
            if (i == 127) continue;

            char c = (char) i;
            float charWidth = fontMetrics.charWidth(c);

            float advance = charWidth + 8;

            if (tempX + advance > imageSize.x) {
                tempX = 0;
                tempY += 1;
            }
            chars.put(c, new Glyph(tempX / imageSize.x, (tempY * h) / imageSize.y, charWidth / imageSize.x, h / imageSize.y, charWidth, h));
            g.drawString(String.valueOf(c), tempX, fontMetrics.getMaxAscent() + (h * tempY));

            tempX += advance;
        }
    }

    private ByteBuffer CreateBuffer() {
        int w = (int)imageSize.x;
        int h = (int)imageSize.y;
        int[] pixels = new int[w * h];
        bufferedImage.getRGB(0, 0, w, h, pixels, 0, w);
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(w * h * 4);

        for (int i = 0; i < pixels.length; i++) {
            byteBuffer.put((byte) ((pixels[i] >> 16) & 0xFF));
            byteBuffer.put((byte) ((pixels[i] >> 8) & 0xFF));
            byteBuffer.put((byte) (pixels[i] & 0xFF));
            byteBuffer.put((byte) ((pixels[i] >> 24) & 0xFF));

        }
        byteBuffer.flip();

        return byteBuffer;
    }

    public int ID() {
        return fontID;
    }
    public Texture getTexture() {
        return texture;
    }

    public Map<Character, Glyph> GetCharacters() {
        return chars;
    }

    public int StringWith(String s) {
        return fontMetrics.stringWidth(s);
    }
}
