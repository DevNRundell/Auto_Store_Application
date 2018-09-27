package com.autostore.app.model;

public class SearchByCBModel {
	
	private String searchValue;
	private String sqlValue;
	
	public SearchByCBModel(String searchValue, String sqlValue) {
		this.searchValue = searchValue;
		this.sqlValue = sqlValue;
	}
	
	public String getSearchValue() {
		return searchValue;
	}
	public String getSqlValue() {
		return sqlValue;
	}
	
	 @Override
     public String toString() {
         return searchValue;
     }
}
