package com.autostore.app.model;

import com.autostore.app.supplier.Supplier;

public class SupplierTableModel extends Supplier {

	private int supplier_id;
	private String name;
	private String contactName;

	public int getSupplier_id() {
		return supplier_id;
	}
	public void setSupplier_id(int supplier_id) {
		this.supplier_id = supplier_id;
	}
	public String getName() {return name;}
	public void setName(String name) {this.name = name;	}
//	public String getContactName() {return contactName;}
//	public void setContactName(String contactName) {this.contactName = contactName;}

}
