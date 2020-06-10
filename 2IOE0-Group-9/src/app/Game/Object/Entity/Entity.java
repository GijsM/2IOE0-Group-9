package app.Game.Object.Entity;

import app.Game.Map.GameMap;
import app.Game.Object.GameObject;

public abstract class Entity extends GameObject {
    
    public Entity(GameMap gameMap, EntityBehaviour behaviour) {
        super(null);
    }

    protected EntityBehaviour behaviour;

    @Override
    public void update() {
        behaviour.behave(this, gameMap);
    }
}