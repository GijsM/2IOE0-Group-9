package app.Game.Object;

import org.joml.Vector3f;

import Graphics.Mesh;
import app.Game.Map.GameMap;
import app.Util.IRenderable;
import app.Util.IUpdateable;

public class GameObject {
    protected GameMap gameMap;

    private float scale;
    private final Mesh mesh;
    private final Vector3f position;
    private final Vector3f rotation;


    public GameObject(GameMap gameMap, Mesh mesh) {
        this.gameMap = gameMap;
        
        this.mesh = mesh;
        position = new Vector3f();
        scale = 1;
        rotation = new Vector3f();
    }
    
    public Vector3f getPosition() {
    	return position;
    }
    
    public void setPosition(float x, float y, float z) {
    	this.position.x = x;
    	this.position.y = y;
    	this.position.z = z;
    }
    
    public float getScale() {
    	return scale;
    }
    
    public void setScale(float scale) {
    	this.scale = scale;
    }
    
    public Vector3f getRotation() {
    	return rotation;
    }
    
    public void setRotation(float x, float y, float z) {
    	this.rotation.x = x;
    	this.rotation.y = y;
    	this.rotation.z = z;
    }
    
    public Mesh getMesh() {
    	return mesh;
    }
}