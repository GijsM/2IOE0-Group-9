package app.Game.Object.Entity.Enemy;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.World;
import org.joml.Vector3f;

import app.Game.Map.GameMap;
import app.Game.Object.Entity.Entity;
import app.Game.Object.Entity.Unit;
import app.engine.Mesh;

public class Enemy extends Entity{

	public Enemy(GameMap gameMap, Mesh mesh) {
		super(gameMap, new EnemyBehaviour(), mesh);
	}

	public int getEnemyX() {
		// TODO Auto-generated method stub
		return (int) (Math.round(getPosition().x/0.1));
	}
	
	public int getEnemyY() {
		// TODO Auto-generated method stub
		return (int) (Math.round(getPosition().z/-0.1));
	}
	
	@Override
	public void load(World world) {
		BodyDef def = new BodyDef();
		def.position = new Vec2(position.x, position.z);
		body = world.createBody(def);
		body.setType(BodyType.DYNAMIC);
	}
	
	@Override
	public void setPosition(float x, float y, float z) {
		super.setPosition(x, y, z);
		if (body != null) {
			body.setTransform(new Vec2(x,z), body.getAngle());
		}
		
	}
	
	@Override
	public Vector3f getPosition() {
		// TODO Auto-generated method stub
		if (body != null) {
			Vec2 bodyPosition = body.getPosition();
			return new Vector3f(bodyPosition.x, -2f, bodyPosition.y);
		} else {
			return position;
		}
		
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
