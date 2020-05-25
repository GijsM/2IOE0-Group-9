package app.Game.Object;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;

import app.Util.IRenderable;

public abstract class StaticGameObject implements IRenderable, ILoadable, ICollidable{
    protected Body body;
    
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