package app.Game.Object.Entity.Player;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
import org.joml.Vector3f;

import app.Game.Map.GameMap;
import app.Game.Object.Entity.Entity;
import app.Game.Object.Entity.EntityBehaviour;
import app.Game.Object.Entity.Unit;
import app.engine.Mesh;

public class Player extends Entity {
	
	public Player(GameMap gameMap, Mesh mesh) {
		super(gameMap, new PlayerBehaviour(), mesh);
	}

	public static int getPlayerX() {
		// TODO Auto-generated method stub
		return Unit.getCenterX();
	}
	
	public static int getPlayerY() {
		// TODO Auto-generated method stub
		return Unit.getCenterY();
	}

	@Override
	public void render() {
		// TODO Auto-generated method stub
	}

	@Override
	public void load(World world) {
		BodyDef def = new BodyDef();
		def.position = new Vec2(position.x, position.y);
		body = world.createBody(def);

		body.setType(BodyType.DYNAMIC);
		
	}
	
	@Override
	public Vector3f getPosition() {
		// TODO Auto-generated method stub
		Vec2 bodyPosition = body.getPosition();
		return new Vector3f(bodyPosition.x, -2f, bodyPosition.y);
	}
	
	@Override
	public Vector3f getRotation() {
		float rotation = body.getAngle();
		Vector3f returnValue =  new Vector3f(0, rotation, 0);
		return returnValue;
	}
	
	@Override
	public void update() {
		super.update();
		body.applyForce(body.getLinearVelocity().mul(-6.9f), new Vec2());
	}

}
