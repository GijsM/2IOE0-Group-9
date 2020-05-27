package app.Game.Object;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;

import app.Game.Map.GameMap;
import app.Util.IRenderable;
import app.Util.IUpdateable;

public abstract class GameObject implements IRenderable, IUpdateable, ILoadable, ICollidable {
    protected GameMap gameMap;
    protected Body body;

    public GameObject(GameMap gameMap) {
        this.gameMap = gameMap;
    }

    @Override
    public void load(World world) {
        body = world.createBody(getBodyDef());
    }

    @Override
    public void unload(World world) {
        world.destroyBody(body);
        body = null;
    }
}