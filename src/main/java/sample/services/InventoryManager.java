package sample.services;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * An anti-pattern class that has BOTH many methods and many attributes.
 * PURPOSE: To be a top result for Question 10 (in both top categories).
 */
public class InventoryManager {
    // Attributes
    private String warehouseLocation;
    private int totalItemCount;
    private Date lastUpdated;
    private List<String> suppliers;
    private HashMap<String, Integer> stockLevels;
    private String managerName;
    private boolean isAutomated;
    private double totalStockValue;

    // Methods
    public void checkStock(String productId) { /* Logic */ }
    public void restockItem(String productId, int quantity) { /* Logic */ }
    public void discontinueItem(String productId) { /* Logic */ }
    public void findSupplier(String productId) { /* Logic */ }
    public void listLowStockItems() { /* Logic */ }
    public void performAnnualAudit() { /* Logic */ }
    public void updateStockValue() { /* Logic */ }
    public void transferStock(String fromLocation, String toLocation) { /* Logic */ }
}