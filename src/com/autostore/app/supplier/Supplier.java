package com.autostore.app.supplier;

public abstract class Supplier {

    int supplierID;
    String name;
    String address;
    String city;
    String state;
    String email;
    String phone;
    String contactName;

    public int getSupplierID() {
        return supplierID;
    }
    public void setSupplierID(int supplierID) {this.supplierID = supplierID;}
    public String getName() {
        return name;
    }
    public void setName(String name) {this.name = name;}
    public String getAddress() {return address;}
    public void setAddress(String address) {this.address = address;}
    public String getCity() {return city;}
    public void setCity(String city) {this.city = city;}
    public String getState() {return state;}
    public void setState(String state) {this.state = state;}
    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}
    public String getPhone() {return phone;}
    public void setPhone(String phone) {this.phone = phone;}
    public String getContactName() {return contactName;}
    public void setContactName(String contactName) {this.contactName = contactName;}
}
