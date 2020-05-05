package app.Game.Object;

import app.Game.Map.GameMap;
import app.Util.IRenderable;
import app.Util.IUpdateable;

public abstract class GameObject implements IRenderable, IUpdateable{
    protected GameMap gameMap;

    public GameObject(GameMap gameMap) {
        this.gameMap = gameMap;
    }
}