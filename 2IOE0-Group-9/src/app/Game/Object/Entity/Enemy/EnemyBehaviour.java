package app.Game.Object.Entity.Enemy;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_DOWN;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_UP;

import java.util.List;

import org.jbox2d.common.Vec2;
import org.joml.Vector3f;

import app.Window;
import app.Game.AI.AStar;
import app.Game.AI.AStar.Node;
import app.Game.Map.GameMap;
import app.Game.Object.Entity.Entity;
import app.Game.Object.Entity.EntityBehaviour;

public class EnemyBehaviour implements EntityBehaviour {

	Window window = Window.getInstance();
	List<Node> currentPath;

	@Override
	public void behave(Entity entity, GameMap gameMap) {
		Enemy enemy = (Enemy) entity;

		if (currentPath == null) {
			AStar astar = new AStar(gameMap.currentRoom.ToInt(gameMap.currentRoom.room), enemy.getEnemyX(), enemy.getEnemyY(), false, gameMap.currentRoom);
			astar.PerformAStar(gameMap.currentRoom.ToInt(gameMap.currentRoom.room));
			currentPath = astar.findPathTo(gameMap.currentRoom.exitDoorWayLocation[1], gameMap.currentRoom.exitDoorWayLocation[0]);
			System.out.println(currentPath.get(0).x + " " + currentPath.get(0).y);
			System.out.println(enemy.getEnemyX() + " " + enemy.getEnemyY());
		}
		if (currentPath.size() == 2) {
			return;
		}
		Node nextNode = currentPath.get(1);
		if (nextNode.x == enemy.getEnemyX() && nextNode.y == enemy.getEnemyY()) {
			currentPath.remove(0);
		}
		moveTowards(currentPath.get(0), currentPath.get(1), enemy);
		
//		Enemy player = (Enemy) entity;
//		Vector3f pos = entity.getPosition();
//		player.setPosition(pos.x, pos.y, pos.z);
//		if (window.isKeyPressed(GLFW_KEY_UP)) {
//			float x = (float) Math.cos(Math.toRadians(player.body.getAngle()));
//			float y = (float) Math.sin(Math.toRadians(player.body.getAngle()));
//			player.body.applyForce(new Vec2(x, y).mul(2f), new Vec2(0,0));
//		}
//		if (window.isKeyPressed(GLFW_KEY_DOWN)) {
//			float x = (float) -Math.cos(Math.toRadians(player.body.getAngle()));
//			float y = (float) -Math.sin(Math.toRadians(player.body.getAngle()));
//			player.body.applyForce(new Vec2(x, y).mul(2f), new Vec2(0,0));
//		}
//		if (window.isKeyPressed(GLFW_KEY_LEFT)) {
//			player.body.setTransform(player.body.getPosition(), player.body.getAngle()-3f);
//		}
//		if (window.isKeyPressed(GLFW_KEY_RIGHT)) {
//			player.body.setTransform(player.body.getPosition(), player.body.getAngle()+3f);
//		}
	}
	
	public void moveTowards(Node currentNode, Node nextNode, Enemy entity) {
		Vector3f vec = entity.getPosition();
		if (currentNode.y > nextNode.y) {
			entity.setPosition(vec.x, vec.y, vec.z+0.005f);
		} else if (currentNode.y < nextNode.y) {
			entity.setPosition(vec.x, vec.y, vec.z-0.005f);
		}
		
		if (currentNode.x > nextNode.x) {
			entity.setPosition(vec.x-0.005f, vec.y, vec.z);
		} else if (currentNode.x < nextNode.x) {
			entity.setPosition(vec.x+0.005f, vec.y, vec.z);
		}
	}
	
	

}
