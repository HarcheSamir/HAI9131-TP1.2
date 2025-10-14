package sample.data;

import java.util.Date;

/**
 * A "Data Class" that holds product information.
 * PURPOSE: To be the top result for Question 9 (most attributes).
 */
public class Product {
    private String productId;
    private String name;
    private String description;
    private double price;
    private double weight;
    private String category;
    private int stockQuantity;
    private String supplier;
    private Date dateAdded;
    private boolean isAvailable;
    private String warehouseLocation;
    private String imageUrl;

    public Product(String id, String name) {
        this.productId = id;
        this.name = name;
    }
    public double getPrice() { return this.price; }
    public String getName() { return this.name; }
}