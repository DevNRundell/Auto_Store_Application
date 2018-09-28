package com.autostore.app.customer;

import com.autostore.app.controllers.DialogController;
import com.autostore.app.database.DBConnect;
import com.autostore.app.database.DBUtils;
import com.autostore.app.model.InvoiceTableModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InvoiceCustomer {

    private ObservableList<InvoiceTableModel> invoiceData = FXCollections.observableArrayList();

    public void searchInvoiceData(int customerID) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            connection = DBConnect.getConnection();

            if(!connection.isClosed()) {

                String query = "select * from customer_order where customer_id = ?";

                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, customerID);
                resultSet = preparedStatement.executeQuery();

                while(resultSet.next()) {

                    InvoiceTableModel invoiceModel = new InvoiceTableModel();

                    invoiceModel.setOrderID(resultSet.getInt("order_id"));
                    invoiceModel.setDate(resultSet.getDate("date"));
                    invoiceModel.setTax(resultSet.getDouble("tax"));
                    invoiceModel.setSubTotal(resultSet.getDouble("sub_total"));
                    invoiceModel.setDiscount(resultSet.getDouble("discount"));
                    invoiceModel.setTotal(resultSet.getDouble("total"));
                    invoiceModel.setCustomerID(resultSet.getInt("customer_id"));

                    invoiceData.add(invoiceModel);

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

    public ObservableList<InvoiceTableModel> getCustomerData() {
        return invoiceData;
    }
}
