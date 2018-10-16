package com.autostore.app.model;

public class CustomerInvoiceModel {

    private int inventoryID;
    private String name;
    private String description;
    private int qtyOrdered;
    private double customerPrice;

    public int getQtyOrdered() {
        return qtyOrdered;
    }
    public void setQtyOrdered(int qtyOrdered) {
        this.qtyOrdered = qtyOrdered;
    }
    public int getInventoryID() {
        return inventoryID;
    }
    public void setInventoryID(int inventoryID) {
        this.inventoryID = inventoryID;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public double getCustomerPrice() {
        return customerPrice;
    }
    public void setCustomerPrice(double customerPrice) {
        this.customerPrice = customerPrice;
    }
}
