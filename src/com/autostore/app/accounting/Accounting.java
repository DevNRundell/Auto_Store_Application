package com.autostore.app.accounting;

import com.autostore.app.controllers.DialogController;
import com.autostore.app.database.DBConnect;
import com.autostore.app.database.DBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Accounting {

    public void addToBalance(double amount) {


        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {

            connection = DBConnect.getConnection();

            if(!connection.isClosed()) {

                String query = "update accounting set balance = ? where account_id = 1";

                double balance = getBalance();
                double newBalance = balance + amount;

                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setDouble(1, newBalance);
                preparedStatement.executeUpdate();

            }
        } catch (SQLException e) {
            e.printStackTrace();
            DialogController.showDatabaseError();
        } finally {
            DBUtils.closeStatement(preparedStatement);
            DBUtils.closeConn(connection);
        }
    }

    private double getBalance() {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;

        double balance = 0.0;

        try {

            connection = DBConnect.getConnection();

            if(!connection.isClosed()) {

                String query = "select * from accounting";

                preparedStatement = connection.prepareStatement(query);
                resultSet = preparedStatement.executeQuery();

                if(resultSet.next()) {
                    balance = resultSet.getDouble("balance");
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
            DialogController.showDatabaseError();
        } finally {
            DBUtils.closeStatement(preparedStatement);
            DBUtils.closeConn(connection);
        }
        return balance;
    }
}
