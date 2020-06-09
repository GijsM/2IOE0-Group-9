package app.Game.Map;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import app.Game.Object.GameObject;
import app.Util.Interfaces.IRenderable;
import app.Util.Interfaces.IUpdateable;

public class GameMap implements IUpdateable, IRenderable {

    //Perhaps use this for seeded randomization
    private final Random random;
    public World world;

    // Readonly, perhaps all gameobjects can move through rooms, hence why they are part of the entire map object
    private final List<GameObject> gameobjects = new ArrayList<>();

    public GameMap(final Random random) {
        Vec2 gravity = new Vec2(0.0f, -10.0f);
        boolean doSleep = true;
        world = new World(gravity, doSleep);
        this.random = random;
    }

    // TODO: add a generator of some kind to add new rooms
    private final List<Room> rooms = new ArrayList<>();

    public List<GameObject> getGameobjects() {
        return gameobjects;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void spawnObject(GameObject object) {
        gameobjects.add(object);
        object.load(world);
    }

    public void despawnObject(GameObject object) {
        gameobjects.remove(object);
        object.unload(world);
    }

    @Override
    public void render() {
        for (final GameObject gameObject : gameobjects) {
            gameObject.render();
        }
        for (final Room room : rooms) {
            room.render();
        }
    }

    @Override
    public void update() {
        world.step(1/60f, 1, 1);
        for (final GameObject gameObject : gameobjects) {
            gameObject.update();
        }
        for (final Room room : rooms) {
            room.update();
        }

    }
    
}