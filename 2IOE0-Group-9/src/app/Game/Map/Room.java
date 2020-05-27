package app.Game.Map;

import java.util.ArrayList;
import java.util.List;

import org.jbox2d.dynamics.World;

import app.Game.Object.ILoadable;
import app.Game.Object.StaticGameObject;
import app.Util.IRenderable;
import app.Util.IUpdateable;

public class Room implements IUpdateable, IRenderable, ILoadable {

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
    public void render() {
        for (StaticGameObject staticGameObject : gameobjects) {
            staticGameObject.render();
        }
    }

    @Override
    public void update() {
        // TODO not sure if this is necessary

    }

    @Override
    public void load(World world) {
        for (StaticGameObject staticGameObject : gameobjects) {
            staticGameObject.load(world);
        }

    }

    @Override
    public void unload(World world) {
        for (StaticGameObject staticGameObject : gameobjects) {
            staticGameObject.unload(world);
        }
    }
    
}