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


    
	public Tree(Mesh mesh) {
		super(mesh);
		// TODO Auto-generated constructor stub
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
		body = world.createBody(def);
		PolygonShape shape = new PolygonShape();
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.density = 1;
		body.createFixture(fixtureDef);
		
	}
}
