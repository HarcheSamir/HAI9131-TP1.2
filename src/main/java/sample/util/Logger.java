package sample.util;

/**
 * A simple logging utility to be called by other classes.
 */
public class Logger {
    public static void log(String message) {
        System.out.println("[LOG] " + message);
    }
}