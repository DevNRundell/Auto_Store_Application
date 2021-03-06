package com.autostore.app.customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.autostore.app.controllers.DialogController;
import com.autostore.app.database.DBConnect;
import com.autostore.app.database.DBUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SearchCustomer extends Customer {
	
	private ObservableList<Customer> customerData = FXCollections.observableArrayList();

	public void searchCustomerData(String query, String searchValue) {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			connection = DBConnect.getConnection();

			if(!connection.isClosed()) {

				preparedStatement = connection.prepareStatement(query);
				preparedStatement.setString(1, searchValue);
				resultSet = preparedStatement.executeQuery();

				while(resultSet.next()) {

					Customer customerModel = new Customer();

					customerModel.setCustomerID(resultSet.getInt("customer_id"));
					customerModel.setFirstName(resultSet.getString("first_name"));
					customerModel.setLastName(resultSet.getString("last_name"));
					customerModel.setAddress(resultSet.getString("address"));
					customerModel.setEmail(resultSet.getString("email"));
					customerModel.setPhone(resultSet.getString("phone"));
					customerModel.setCity(resultSet.getString("city"));
					customerModel.setState(resultSet.getString("state"));
					customerModel.setZipCode(resultSet.getString("zip_code"));

					customerData.add(customerModel);

				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
			DialogController.showDatabaseError();
		} finally {
			DBUtils.closeStatement(preparedStatement);
			DBUtils.closeResultSet(resultSet);
			DBUtils.closeConn(connection);
		}
	}
	
	public ObservableList<Customer> getCustomerData() {
		return customerData;
	}

}
