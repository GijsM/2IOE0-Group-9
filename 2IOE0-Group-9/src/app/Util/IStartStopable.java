package app.Util;

public interface IStartStopable {
//    public void start();
    public void start(WindowManager manager) throws Exception;
    public void stop();
}