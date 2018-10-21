package com.autostore.app.Inventory;

public class Inventory {

    int inventoryID;
    String name;
    int stockQuantity;
    int quantityToOrder;
    double unitPrice;
    String description;

    public int getInventoryID() {
        return inventoryID;
    }
    public void setInventoryID(int inventoryID) {
        this.inventoryID = inventoryID;
    }
    public String getPartName() {
        return name;
    }
    public void setPartName(String name) {
        this.name = name;
    }
    public int getStockQuantity() {
        return stockQuantity;
    }
    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }
    public double getUnitPrice() {
        return unitPrice;
    }
    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public int getQuantityToOrder() {
        return quantityToOrder;
    }
    public void setQuantityToOrder(int quantityToOrder) {
        this.quantityToOrder = quantityToOrder;
    }
}
