package com.autostore.app.model;

import java.sql.Date;

public class InvoiceTableModel extends CustomerTableModel {
	
	private int orderID;
	private Date date;
	private double discount;
	private double tax;
	private double subTotal;
	private double total;
	
	public int getOrderID() {
		return orderID;
	}
	public void setOrderID(int orderID) {
		this.orderID = orderID;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public double getDiscount() {
		return discount;
	}
	public void setDiscount(double discount) {
		this.discount = discount;
	}
	public double getTax() {
		return tax;
	}
	public void setTax(double tax) {
		this.tax = tax;
	}
	public double getSubTotal() {
		return subTotal;
	}
	public void setSubTotal(double subTotal) {
		this.subTotal = subTotal;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}

	public boolean submitOrder(int customerID) {
		string orderQuery = "INSERT INTO customer_order (customer_id, total, tax, subtotal, discount, date) VALUES (" +
				customerID + "," +  this.total + "," + this.tax + "," + this.subtotal + "," +
				this.discount + "," + this.date + ")";
		DBUtils dbUtils = new DBUtils();
		boolean queryResult = dbUtils.runQuery(orderQuery);
		return queryResult;
	}
}
