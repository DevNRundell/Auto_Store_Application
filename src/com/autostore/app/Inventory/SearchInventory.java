package com.autostore.app.Inventory;

import com.autostore.app.controllers.DialogController;
import com.autostore.app.database.DBConnect;
import com.autostore.app.database.DBUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SearchInventory {

    private ObservableList<Inventory> inventoryData = FXCollections.observableArrayList();

    public void searchInventoryData(String query, String searchValue) {

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

                    Inventory model = new Inventory();

                    model.setInventoryID(resultSet.getInt("inventory_id"));
                    model.setPartName(resultSet.getString("name"));
                    model.setStockQuantity(resultSet.getInt("stock_quantity"));
                    model.setUnitPrice(resultSet.getDouble("customer_price"));
                    model.setDescription(resultSet.getString("description"));

                    inventoryData.add(model);

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

    public void searchAllInventory() {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            connection = DBConnect.getConnection();

            if(!connection.isClosed()) {

                String query = "select * from store_inventory";

                preparedStatement = connection.prepareStatement(query);
                resultSet = preparedStatement.executeQuery();

                while(resultSet.next()) {

                    Inventory model = new Inventory();

                    model.setInventoryID(resultSet.getInt("inventory_id"));
                    model.setPartName(resultSet.getString("name"));
                    model.setStockQuantity(resultSet.getInt("stock_quantity"));
                    model.setUnitPrice(resultSet.getDouble("customer_price"));
                    model.setDescription(resultSet.getString("description"));

                    inventoryData.add(model);

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

    public ObservableList<Inventory> getInventoryData() {
        return inventoryData;
    }
}
