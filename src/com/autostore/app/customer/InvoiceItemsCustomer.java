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

public class InvoiceItemsCustomer {

    /*private ObservableList<InvoiceTableModel> invoiceData = FXCollections.observableArrayList();

    public void searchInvoiceItemData(int orderID) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            connection = DBConnect.getConnection();

            if(!connection.isClosed()) {

                String query = "";

                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, orderID);
                resultSet = preparedStatement.executeQuery();

                while(resultSet.next()) {



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
    }*/

    //Wait until better understanding of DB concept
}
