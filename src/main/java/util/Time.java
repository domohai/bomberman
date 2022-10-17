package util;

public class Time {
    public static double timeStarted = System.nanoTime();
    public static double getTime() {//in seconds
        return (System.nanoTime() - timeStarted) * 1E-9;
    }
}
