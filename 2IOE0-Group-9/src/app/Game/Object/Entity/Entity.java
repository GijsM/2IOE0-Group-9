package app.Game.Object.Entity;

import app.Game.Map.GameMap;
import app.Game.Object.GameObject;
import app.engine.Mesh;

public abstract class Entity extends GameObject {
    
    public Entity(GameMap gameMap, EntityBehaviour behaviour, Mesh mesh) {
        super(mesh);
        this.gameMap = gameMap;
        this.behaviour = behaviour;
    }

    protected EntityBehaviour behaviour;

    @Override
    public void update() {
        behaviour.behave(this, gameMap);
    }
}