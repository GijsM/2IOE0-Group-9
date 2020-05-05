package app.Game.Object.Entity;

import app.Game.Map.GameMap;

public interface EntityBehaviour {
    public void behave(Entity entity, GameMap gameMap);
}