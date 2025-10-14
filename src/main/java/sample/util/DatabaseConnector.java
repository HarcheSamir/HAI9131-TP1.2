package sample.util;

/**
 * This class contains a method with an excessive number of parameters.
 * PURPOSE: To be the top result for Question 13 (most parameters).
 */
public class DatabaseConnector {

    // This method is designed to have the maximum number of parameters.
    public static void connect(String host, int port, String user, String pass, String dbName, int poolSize, int timeout, boolean useSsl) {
        // Database connection logic...
    	System.out.println("Connecting to database...");
    }
}