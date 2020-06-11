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

public class GameObject implements  IRenderable, IUpdateable, ILoadable, ICollidable {
    protected GameMap gameMap;
    protected Body body;
    
    private float scale;
    private final Mesh mesh;			// TODO: requires merging mesh.java's from branches
    private final Vector3f position;
    private final Vector3f rotation;

    public GameObject(Mesh mesh) {
//        this.gameMap = gameMap;
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
    @Override
    public void load(World world) {
        body = world.createBody(getBodyDef());
    }

    @Override
    public void unload(World world) {
        world.destroyBody(body);
        body = null;
    }

	@Override
	public BodyDef getBodyDef() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render() {
		// TODO Auto-generated method stub
		
	}
}