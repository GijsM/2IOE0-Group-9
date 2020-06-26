package app.Game.Map;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import app.Game.Object.GameObject;
import app.Game.Object.Entity.Player.Player;
import app.Util.Interfaces.IRenderable;
import app.Util.Interfaces.IUpdateable;
import app.engine.Mesh;
import app.graphics.Loader;
import app.graphics.ObjectLoader;
import app.graphics.RawModel;
import app.graphics.Texture;
import app.graphics.TexturedModel;

public class GameMap implements IUpdateable, IRenderable {

    //Perhaps use this for seeded randomization
    public final Random random;
    public Player player;
    public World world;

    // Readonly, perhaps all gameobjects can move through rooms, hence why they are part of the entire map object
    private final List<GameObject> gameobjects = new ArrayList<>();
    public Room currentRoom;
    public Mesh enemyMesh;
    
    public GameMap(final Random random) {
        Vec2 gravity = new Vec2(0.0f, 0.0f);
        boolean doSleep = true;
        world = new World(gravity, doSleep);
        this.random = random;
        Loader loader = new Loader();
    	RawModel playermodel = ObjectLoader.loadObjModel("Tree",loader);
    	Texture texture = new Texture(".\\2IOE0-Group-9\\res\\Textures\\tree.png");
        TexturedModel texturedModel = new TexturedModel(playermodel,texture);
        for (int p = 0 ; p < playermodel.colors.length ; p++){
            playermodel.colors[p++] = 0.0f;
            playermodel.colors[p] = 0.5f;
        }
        enemyMesh = new Mesh(playermodel.positions, playermodel.colors, playermodel.indices);
        player = new Player(this, enemyMesh);
        spawnObject(player);
        player.setScale(0.02f);
        Room defaultRoom = new Room(this);
        setRoom(defaultRoom);
       
    }
    
    public void setRoom(Room room) {
    	if (currentRoom != null) {
    		currentRoom.unload(world);
    	}
    	currentRoom = room;
    	this.rooms.add(currentRoom);
    	room.load(world);
    	player.setPosition((float) currentRoom.doorWayLocation[1]/10, -2, (float) currentRoom.doorWayLocation[0]/-10);
    	
    }

    // TODO: add a generator of some kind to add new rooms
    private final List<Room> rooms = new ArrayList<>();

    public List<GameObject> getGameobjects() {
    	List<GameObject> list = new ArrayList<GameObject>(currentRoom.getGameobjects());
    	list.addAll(gameobjects);
        return list;
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
    	//System.out.println("Update");
        world.step(1/60f, 1, 1);
        for (final GameObject gameObject : gameobjects) {
            gameObject.update();
        }
        currentRoom.update();
        if (currentRoom.exitDoorWayLocation[0] == player.getPlayerY() && currentRoom.exitDoorWayLocation[1] == player.getPlayerX()) {
        	setRoom(new Room(this));
        }

    }
    
}