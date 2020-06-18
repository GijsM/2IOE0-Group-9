package app.Game.Object;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
import org.joml.Vector3f;

import app.engine.Mesh;

public class Tree extends GameObject {

	private final Vector3f position;
    private final Vector3f rotation;
    
	public Tree(Mesh mesh) {
		super(mesh);
		position = new Vector3f();
    	rotation = new Vector3f();
		// TODO Auto-generated constructor stub
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

	@Override
	public void render() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void load(World world) {
		BodyDef def = new BodyDef();
		def.position = new Vec2(position.x, position.y);
		//def.type = BodyType.STATIC;
//		body = world.createBody(def);
		PolygonShape shape = new PolygonShape();
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.density = 1;
//		body.createFixture(fixtureDef);
		
	}
}
