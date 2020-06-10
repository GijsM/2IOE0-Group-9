package app.GUI.Components;

import app.engine.Rect;
import app.engine.Texture;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

public class GUISkin {
    public Texture texture;
    private List<GUIStyle> styles = new ArrayList<GUIStyle>();

    public GUISkin (String name) {
        BufferedReader br;

        try{
            br = new BufferedReader(new FileReader(new File(".\\2IOE0-Group-9\\res\\Skins\\" + name + ".Skin")));
            texture = Texture.Find(br.readLine().split(" ")[1]);

            String line = br.readLine();
            while(line != null) {
                if (line.startsWith("Name:")) {
                    String[] o = br.readLine().split(" ")[1].split(",");
                    String[] p = br.readLine().split(" ")[1].split(",");

                    Rect offset = new Rect(Float.parseFloat(o[0]), Float.parseFloat(o[1]), Float.parseFloat(o[2]), Float.parseFloat(o[3]));
                    Rect padding = new Rect(Float.parseFloat(p[0]), Float.parseFloat(p[1]), Float.parseFloat(p[2]), Float.parseFloat(p[3]));

                    GUIStyle style = new GUIStyle(line.split(" ")[1],texture, offset, padding);
                    styles.add(style);
                }
                line = br.readLine();
            }
            br.close();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    public GUIStyle Get(String name) {
        for (int i = 0; i < styles.size(); i++) {
            if(styles.get(i).name.equals(name)) {
                return styles.get(i);
            }
        }
        return null;
    }
}
