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
import app.Game.Object.Tree;

public abstract class GameObject implements  IRenderable, IUpdateable, ILoadable, ICollidable {
    protected GameMap gameMap;
    protected Body body;
    
    private float scale;
    private final Mesh mesh;			// TODO: requires merging mesh.java's from branches

    public GameObject(Mesh mesh) {
//        this.gameMap = gameMap;
    	this.mesh = mesh;
    	scale = 1;
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