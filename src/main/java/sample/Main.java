package sample;

// Import statements are now required because classes are in different packages.
import sample.services.InventoryManager;
import sample.services.OrderProcessor;
import sample.util.DatabaseConnector;
import sample.util.ReportGenerator;

/**
 * Main entry point to orchestrate calls between different services and utilities.
 * PURPOSE: Creates a clear and non-trivial call graph for analysis.
 */
public class Main {
    public static void main(String[] args) {
        // Instantiate the main service classes
        OrderProcessor processor = new OrderProcessor();
        InventoryManager inventory = new InventoryManager();
        ReportGenerator reports = new ReportGenerator();

        // Create a sequence of calls
        processor.createNewOrder(101);
        inventory.checkStock("PROD-123");
        reports.generateSalesReport();
        processor.cancelOrder(101);

        // A call to the method with the most parameters
        DatabaseConnector.connect("localhost", 3306, "root", "password123", "store_db", 50, 5000, true);
    }
}