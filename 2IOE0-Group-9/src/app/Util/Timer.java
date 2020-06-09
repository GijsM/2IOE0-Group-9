package app.Util;

public class Timer {
	private static Timer instance = null;
	
    private double lastLoopTime;
    
    public static Timer getInstance() {
    	if (instance == null) {
    		instance = new Timer();
    	}
    	
    	return instance;
    }
    
    public void init() {
        lastLoopTime = getTime();
    }

    public double getTime() {
        return System.nanoTime() / 1000_000_000.0;
    }

    public float getElapsedTime() {
        double time = getTime();
        float elapsedTime = (float) (time - lastLoopTime);
        lastLoopTime = time;
        return elapsedTime;
    }

    public double getLastLoopTime() {
        return lastLoopTime;
    }
}
