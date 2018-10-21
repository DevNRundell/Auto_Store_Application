package com.autostore.app.supplier;

import com.autostore.app.controllers.DialogController;
import com.autostore.app.database.DBConnect;
import com.autostore.app.database.DBUtils;
import com.autostore.app.model.SupplierInvoiceModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SearchSupplierInvoiceItem {

        private ObservableList<SupplierInvoiceModel> invoiceData = FXCollections.observableArrayList();

        public void searchInvoiceItemData(int orderID) {

            Connection connection = null;
            PreparedStatement preparedStatement = null;
            PreparedStatement preparedStatement2 = null;
            ResultSet resultSet = null;
            ResultSet resultSet2 = null;

            ArrayList<Integer> inventoryIDs = new ArrayList<>();
            Map<Integer, Integer> invMap = new HashMap<>();

            try {

                connection = DBConnect.getConnection();

                if(!connection.isClosed()) {

                    String query = "select * from supplier_purchaseline where purchase_id = ?";

                    preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setInt(1, orderID);
                    resultSet = preparedStatement.executeQuery();

                    while(resultSet.next()) {
                        inventoryIDs.add(resultSet.getInt("inventory_id"));
                        invMap.put(resultSet.getInt("inventory_id"), resultSet.getInt("quantity"));
                    }

                    if(!inventoryIDs.isEmpty()) {

                        query = "select * from store_inventory where inventory_id = ?";
                        preparedStatement2 = connection.prepareStatement(query);

                        for(int id : inventoryIDs) {

                            preparedStatement2.setInt(1, id);
                            resultSet2 = preparedStatement2.executeQuery();

                            if (resultSet2.next()) {

                                SupplierInvoiceModel supplierInvoiceModel = new SupplierInvoiceModel();

                                supplierInvoiceModel.setInventoryID(resultSet2.getInt("inventory_id"));
                                supplierInvoiceModel.setName(resultSet2.getString("name"));
                                supplierInvoiceModel.setDescription(resultSet2.getString("description"));
                                supplierInvoiceModel.setQtyOrdered(invMap.get(id));

                                invoiceData.add(supplierInvoiceModel);

                            }
                        }
                    }
                }

            } catch (SQLException e) {
                e.printStackTrace();
                DialogController.showDatabaseError();
            } finally {
                DBUtils.closeStatement(preparedStatement);
                DBUtils.closeStatement(preparedStatement2);
                DBUtils.closeResultSet(resultSet);
                DBUtils.closeResultSet(resultSet2);
                DBUtils.closeConn(connection);
            }
        }

        public ObservableList<SupplierInvoiceModel> getInvoiceData() {
            return invoiceData;
        }
    }

