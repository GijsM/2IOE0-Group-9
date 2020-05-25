package app.Game.Map;

import java.util.ArrayList;
import java.util.List;

import app.Game.Object.StaticGameObject;
import app.Util.IRenderable;
import app.Util.IUpdateable;
import app.Util.WindowManager;

public class Room implements IUpdateable, IRenderable {

    private GameMap map;
    private List<StaticGameObject> gameobjects = new ArrayList<>();

    public Room(GameMap map) {
        this.setMap(map);
    }

    public List<StaticGameObject> getGameobjects() {
        return gameobjects;
    }

    public GameMap getMap() {
        return map;
    }

    public void setMap(GameMap map) {
        this.map = map;
    }

    @Override
    public void render(WindowManager window) {
       for (StaticGameObject staticGameObject : gameobjects) {
           staticGameObject.render(window);
       }
    }

    @Override
    public void update() {
        // TODO not sure if this is necessary

    }
    
}