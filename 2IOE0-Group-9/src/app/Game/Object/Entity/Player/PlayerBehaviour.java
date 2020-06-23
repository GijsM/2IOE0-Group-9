package app.Game.Object.Entity.Player;

import org.joml.Vector3f;

import app.Window;
import app.Game.Map.GameMap;
import app.Game.Object.Entity.Entity;
import app.Game.Object.Entity.EntityBehaviour;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_UP;

import org.jbox2d.common.Vec2;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_DOWN;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT;

public class PlayerBehaviour implements EntityBehaviour {
	
	Window window = Window.getInstance();

	@Override
	public void behave(Entity entity, GameMap gameMap) {
		Player player = (Player) entity;
		Vector3f pos = entity.getPosition();
		player.setPosition(pos.x, pos.y, pos.z);
		if (window.isKeyPressed(GLFW_KEY_UP)) {
			float x = (float) Math.cos(Math.toRadians(player.body.getAngle()));
			float y = (float) Math.sin(Math.toRadians(player.body.getAngle()));
			player.body.applyForce(new Vec2(x, y).mul(3), new Vec2(0,0));
		}
		if (window.isKeyPressed(GLFW_KEY_DOWN)) {
			float x = (float) -Math.cos(Math.toRadians(player.body.getAngle()));
			float y = (float) -Math.sin(Math.toRadians(player.body.getAngle()));
			player.body.applyForce(new Vec2(x, y).mul(3), new Vec2(0,0));
		}
		if (window.isKeyPressed(GLFW_KEY_LEFT)) {
			player.body.setTransform(player.body.getPosition(), player.body.getAngle()-5f);
		}
		if (window.isKeyPressed(GLFW_KEY_RIGHT)) {
			player.body.setTransform(player.body.getPosition(), player.body.getAngle()+5f);
		}
	}

}
