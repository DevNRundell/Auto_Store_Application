package com.autostore.app.customer;

import com.autostore.app.Inventory.Inventory;
import com.autostore.app.controllers.DialogController;
import com.autostore.app.database.DBConnect;
import com.autostore.app.database.DBUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;

public class SubmitOrder extends Inventory {

    public boolean add(double tax, double subTotal, double discount, double total, int customerID) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {

            connection = DBConnect.getConnection();

            if(!connection.isClosed()) {

                String query = "insert into customer_order (date, tax, sub_total, discount, total, customer_id) values (?,?,?,?,?,?)";

                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setDate(1, new Date(Calendar.getInstance().getTime().getTime()));
                preparedStatement.setDouble(2, tax);
                preparedStatement.setDouble(3, subTotal);
                preparedStatement.setDouble(4, discount);
                preparedStatement.setDouble(5, total);
                preparedStatement.setInt(6, customerID);
                preparedStatement.executeUpdate();

                return true;

            }
        } catch (SQLException e) {
            e.printStackTrace();
            DialogController.showDatabaseError();
        } finally {
            DBUtils.closeStatement(preparedStatement);
            DBUtils.closeConn(connection);
        }
        return false;
    }

    public void addOrderToOrderLine(ArrayList<Inventory> itemsOrdered) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {

            connection = DBConnect.getConnection();

            if(!connection.isClosed()) {

                String query = "insert into customer_orderline (order_id, quantity, inventory_id) values (?,?,?)";

                int orderID = getLatestOrderID();

                for(Inventory item: itemsOrdered) {

                    preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setInt(1, orderID);
                    preparedStatement.setInt(2, item.getQuantityToOrder());
                    preparedStatement.setInt(3, item.getInventoryID());
                    preparedStatement.executeUpdate();

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            DialogController.showDatabaseError();
        } finally {
            DBUtils.closeStatement(preparedStatement);
            DBUtils.closeConn(connection);
        }
    }

    private int getLatestOrderID() {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;

        int orderID = 0;

        try {

            connection = DBConnect.getConnection();

            if(!connection.isClosed()) {

                String query = "select * from customer_order order by order_id desc limit 1";

                preparedStatement = connection.prepareStatement(query);
                resultSet = preparedStatement.executeQuery();

                if(resultSet.next()) {
                    orderID = resultSet.getInt("order_id");
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
            DialogController.showDatabaseError();
        } finally {
            DBUtils.closeStatement(preparedStatement);
            DBUtils.closeConn(connection);
        }
        return orderID;
    }
}
