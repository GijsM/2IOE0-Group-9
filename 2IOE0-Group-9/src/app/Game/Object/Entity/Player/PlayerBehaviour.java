package app.Game.Object.Entity.Player;

import app.Game.Map.GameMap;
import app.Game.Object.Entity.Entity;
import app.Game.Object.Entity.EntityBehaviour;

public class PlayerBehaviour implements EntityBehaviour {

	@Override
	public void behave(Entity entity, GameMap gameMap) {
		Player player = (Player) entity;
	}

}
