package app.Game.Map;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import app.Game.Object.GameObject;
import app.Util.IRenderable;
import app.Util.IUpdateable;
import app.Util.MouseInput;
import app.Util.WindowManager;

public class GameMap implements IUpdateable {

    //Perhaps use this for seeded randomization
    private final Random random;

    // Readonly, perhaps all gameobjects can move through rooms, hence why they are part of the entire map object
    private final List<GameObject> gameobjects = new ArrayList<>();
    public List<Room> rooms = new ArrayList<>();

    public GameMap(final Random random) {
        this.random = random;
        this.rooms.add(new app.Game.Map.Room());
        this.rooms.get(0).print();
        this.rooms.get(0).rotateCounterClockwise();
        this.rooms.get(0).print();
    }

    public List<GameObject> getGameobjects() {
        return gameobjects;
    }

    public List<Room> getRooms() {
        return rooms;
    }



    @Override
    public void update(float interval, MouseInput mouseInput) {
    	// TODO: REIMPLEMENT
    	
//        for (final GameObject gameObject : gameobjects) {
//            gameObject.update();
//        }
//        for (final Room room : rooms) {
//            room.update();
//        }

    }   
}


// GameMap.render() no longer needed => rendering is now done by Renderer.java

//@Override
//public void render(WindowManager window) {
//  for (final GameObject gameObject : gameobjects) {
//      gameObject.render(window);
//  }
//  for (final Room room : rooms) {
//      room.render(window);
//  }
//}