package com.autostore.app.supplier;

import com.autostore.app.Inventory.Inventory;
import com.autostore.app.controllers.DialogController;
import com.autostore.app.database.DBConnect;
import com.autostore.app.database.DBUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;

public class SubmitOrder extends Inventory{

        public boolean add(double tax, double subTotal, double discount, double total, int supplierID) {

            Connection connection = null;
            PreparedStatement preparedStatement = null;

            try {

                connection = DBConnect.getConnection();

                if(!connection.isClosed()) {

                    String query = "insert into supplier_purchase (date, tax, sub_total, discount, total, supplier_id) values (?,?,?,?,?,?)";

                    preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setDate(1, new Date(Calendar.getInstance().getTime().getTime()));
                    preparedStatement.setDouble(2, tax);
                    preparedStatement.setDouble(3, subTotal);
                    preparedStatement.setDouble(4, discount);
                    preparedStatement.setDouble(5, total);
                    preparedStatement.setInt(6, supplierID);
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

        public void addOrderToPurchaseLine(ArrayList<Inventory> itemsOrdered) {

            Connection connection = null;
            PreparedStatement preparedStatement = null;

            try {

                connection = DBConnect.getConnection();

                if(!connection.isClosed()) {

                    String query = "insert into supplier_purchaseline (purchase_id, quantity, inventory_id) values (?,?,?)";

                    int purchaseID = getLatestPurchaseID();

                    for(Inventory item: itemsOrdered) {

                        preparedStatement = connection.prepareStatement(query);
                        preparedStatement.setInt(1, purchaseID);
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

        private int getLatestPurchaseID() {

            Connection connection = null;
            PreparedStatement preparedStatement = null;
            ResultSet resultSet;

            int purchaseID = 0;

            try {

                connection = DBConnect.getConnection();

                if(!connection.isClosed()) {

                    String query = "select * from supplier_purchase order by purchase_id desc limit 1";

                    preparedStatement = connection.prepareStatement(query);
                    resultSet = preparedStatement.executeQuery();

                    if(resultSet.next()) {
                        purchaseID = resultSet.getInt("purchase_id");
                    }

                }
            } catch (SQLException e) {
                e.printStackTrace();
                DialogController.showDatabaseError();
            } finally {
                DBUtils.closeStatement(preparedStatement);
                DBUtils.closeConn(connection);
            }
            return purchaseID;
        }
    }

