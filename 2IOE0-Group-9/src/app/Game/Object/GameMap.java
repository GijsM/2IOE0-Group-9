package app.Game.Object;

import app.Util.IRenderable;

import java.util.ArrayList;

public class GameMap  implements IRenderable {

    public ArrayList<Room> rooms = new ArrayList<>();

    public GameMap(){
        this.rooms.add(new Room());
        this.rooms.get(0).print();
        this.rooms.get(0).rotateCounterClockwise();
        this.rooms.get(0).print();
    }
    @Override
    public void render() {

    }
}
