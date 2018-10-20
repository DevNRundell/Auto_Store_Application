package com.autostore.app.supplier;

import com.autostore.app.controllers.DialogController;
import com.autostore.app.database.DBConnect;
import com.autostore.app.database.DBUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SearchSupplierInvoice {

        private ObservableList<SupplierInvoice> invoiceData = FXCollections.observableArrayList();

        public void searchInvoiceData(int supplierID) {

            Connection connection = null;
            PreparedStatement preparedStatement = null;
            ResultSet resultSet = null;

            try {

                connection = DBConnect.getConnection();

                if(!connection.isClosed()) {

                    String query = "select * from supplier_purchase where supplier_id = ?";

                    preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setInt(1, supplierID);
                    resultSet = preparedStatement.executeQuery();

                    while(resultSet.next()) {

                        SupplierInvoice invoiceModel = new SupplierInvoice();

                        invoiceModel.setOrderID(resultSet.getInt("purchase_id"));
                        invoiceModel.setDate(resultSet.getDate("date"));
                        invoiceModel.setTax(resultSet.getDouble("tax"));
                        invoiceModel.setSubTotal(resultSet.getDouble("sub_total"));
                        invoiceModel.setDiscount(resultSet.getDouble("discount"));
                        invoiceModel.setTotal(resultSet.getDouble("total"));
                        invoiceModel.setSupplierID(resultSet.getInt("supplier_id"));

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

        public ObservableList<SupplierInvoice> getSupplierData() {
            return invoiceData;
        }
    }
