package engine.io;
import java.awt.*;

public class Menu {



    public void render (Graphics window) {
        Font font1 = new Font("arial", Font.BOLD, 50);
        window.setFont(font1);
        window.setColor((Color.blue));
        window.drawString("Genocrawl", 1280/2, 100);
    }
}
