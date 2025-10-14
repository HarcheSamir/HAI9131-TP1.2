package sample.services;

import sample.util.Logger; 


public class OrderProcessor {
    private int processedOrderCount = 0;

    public void createNewOrder(int customerId) {
        Logger.log("Creating new order for customer " + customerId);
        this.processedOrderCount++;
    }
    public void cancelOrder(int orderId) { /* Logic */ }
    public void shipOrder(int orderId) { /* Logic */ }
    public void trackOrder(int orderId) { /* Logic */ }
    public void returnOrder(int orderId) { /* Logic */ }
    public void applyDiscount(int orderId, int discountCode) { /* Logic */ }
    public void getOrderStatus(int orderId) { /* Logic */ }
    public void listAllOrders() { /* Logic */ }
    public void updateShippingAddress(int orderId, String address) { /* Logic */ }
    public void verifyPayment(int orderId) { /* Logic */ }
    public void archiveOldOrders() { /* Logic */ }
}