package app.Game.Map;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import app.Game.Object.GameObject;
import app.Util.IRenderable;
import app.Util.IUpdateable;

public class GameMap implements IUpdateable, IRenderable {

    //Perhaps use this for seeded randomization
    private final Random random;

    // Readonly, perhaps all gameobjects can move through rooms, hence why they are part of the entire map object
    private final List<GameObject> gameobjects = new ArrayList<>();

    public GameMap(final Random random) {
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
        for (final GameObject gameObject : gameobjects) {
            gameObject.update();
        }
        for (final Room room : rooms) {
            room.update();
        }

    }
    
}