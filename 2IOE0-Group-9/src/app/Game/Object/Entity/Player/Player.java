package app.Game.Object.Entity.Player;

import org.jbox2d.dynamics.World;

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
		// TODO Auto-generated method stub
		
	}

}
