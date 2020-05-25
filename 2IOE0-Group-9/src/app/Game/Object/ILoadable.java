package app.Game.Object;

import org.jbox2d.dynamics.World;

public interface ILoadable {
    public void load(World world);
    public void unload(World world);
}