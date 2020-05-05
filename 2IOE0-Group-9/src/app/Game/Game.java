package app.Game;

import java.util.Random;

import app.Game.Map.GameMap;
import app.Util.IRenderable;
import app.Util.IStartStopable;
import app.Util.IUpdateable;

public class Game implements IStartStopable, IRenderable, IUpdateable {

    private GameMap gamemap;
    private boolean running;

    public GameMap getGamemap() {
        return gamemap;
    }

    public void setGamemap(GameMap gamemap) {
        this.gamemap = gamemap;
    }

    @Override
    public void start() {
        running = true;
        Random rng = new Random();
        GameMap gMap = new GameMap(rng);
        setGamemap(gMap);
        // Initialize all needed to operate the game, including a new thread which runs update and render continuously
    }

    @Override
    public void stop() {
        running = false;
        // TODO Auto-generated method stub

    }

    //TODO perhaps call update and render in different threads for performance (Can do this at a later stage if performance becomes a problem)
    @Override
    public void update() {
        gamemap.update();
    }

    @Override
    public void render() {
        gamemap.render();

    }
    
}