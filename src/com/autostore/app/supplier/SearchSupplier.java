package com.autostore.app.supplier;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.autostore.app.controllers.DialogController;
import com.autostore.app.database.DBConnect;
import com.autostore.app.database.DBUtils;
import com.autostore.app.model.SupplierTableModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SearchSupplier extends Supplier {

    private ObservableList<SupplierTableModel> supplierData = FXCollections.observableArrayList();

    public void searchSupplierData(String query, String searchValue) {

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

                    SupplierTableModel supplierModel = new SupplierTableModel();
                    supplierModel.setSupplier_id(resultSet.getInt("supplier_id"));
                    supplierModel.setName(resultSet.getString("name"));
                    supplierModel.setAddress(resultSet.getString("address"));
                    supplierModel.setEmail(resultSet.getString("email"));
                    supplierModel.setPhone(resultSet.getString("phone"));
                    supplierModel.setCity(resultSet.getString("city"));
                    supplierModel.setState(resultSet.getString("state"));
                    supplierModel.setContactName(resultSet.getString("contact"));

                    supplierData.add(supplierModel);

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

    public ObservableList<SupplierTableModel> getSupplierData() {
        return supplierData;
    }

}
