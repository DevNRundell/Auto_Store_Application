package com.autostore.app.model;

import com.autostore.app.supplier.Supplier;

public class SupplierTableModel extends Supplier {

	private int supplier_id;
	private String name;

	public SupplierTableModel() {
	}

	public int getSupplier_id() {
		return this.supplier_id;
	}
	public void setSupplier_id(int supplier_id) {
		this.supplier_id = supplier_id;
	}
	public String getName() {return name;}
	public void setName(String name) {this.name = name;	}


}
