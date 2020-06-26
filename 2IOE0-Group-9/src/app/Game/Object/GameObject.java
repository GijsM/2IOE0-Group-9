package app.Game.Object;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.World;
import org.joml.Vector3f;

import app.Game.Map.GameMap;
import app.Util.Interfaces.ICollidable;
import app.Util.Interfaces.ILoadable;
import app.Util.Interfaces.IRenderable;
import app.Util.Interfaces.IUpdateable;
import app.engine.Mesh;

public abstract class GameObject implements  IRenderable, IUpdateable, ILoadable {
    protected GameMap gameMap;
    public Body body;
    
    private float scale;
    private final Mesh mesh;			// TODO: requires merging mesh.java's from branches
    
	protected final Vector3f position;
    protected final Vector3f rotation;
        

    public GameObject(Mesh mesh) {
    	this.rotation = new Vector3f();
    	this.position = new Vector3f();
    	this.mesh = mesh;
    	scale = 1;
    }

	public Vector3f getPosition() {
    	return position;
    }
    
    public void setPosition(float x, float y, float z) {
    	this.position.x = x;
    	this.position.y = y;
    	this.position.z = z;
    }
    
    public Vector3f getRotation() {
    	return rotation;
    }
    
    public void setRotation(float x, float y, float z) {
    	this.rotation.x = x;
    	this.rotation.y = y;
    	this.rotation.z = z;
    }
    
    public float getScale() {
    	return scale;
    }
    
    public void setScale(float scale) {
    	this.scale = scale;
    }
      
    public Mesh getMesh() {
    	return mesh;
    }
    
    @Override
    public void render() {
    	mesh.render();
    }

    @Override
    public void unload(World world) {
        world.destroyBody(body);
        body = null;
    }
}