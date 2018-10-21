package com.autostore.app.Inventory;

import com.autostore.app.controllers.DialogController;
import com.autostore.app.database.DBConnect;
import com.autostore.app.database.DBUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateInventory extends Inventory{

    public boolean updateItem() {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {

            connection = DBConnect.getConnection();

            if(!connection.isClosed()) {

                String query = "update store_inventory set name = ?, stock_quantity = ?, customer_price = ?, description = ? where inventory_id = ?";

                connection.setAutoCommit(false);
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, name);
                preparedStatement.setInt(2, stockQuantity);
                preparedStatement.setDouble(3, unitPrice);
                preparedStatement.setString(4, description);
                preparedStatement.setInt(5, inventoryID);
                preparedStatement.executeUpdate();
                connection.commit();

                return true;

            }

        } catch (SQLException e) {
            e.printStackTrace();
            DialogController.showDatabaseError();
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } finally {
            DBUtils.closeStatement(preparedStatement);
            DBUtils.closeConn(connection);
        }
        return false;
    }

    public boolean updateQuantity() {


        return false;
    }
}
