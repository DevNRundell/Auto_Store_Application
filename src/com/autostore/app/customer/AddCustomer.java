package com.autostore.app.customer;

import com.autostore.app.controllers.DialogController;
import com.autostore.app.database.DBConnect;
import com.autostore.app.database.DBUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddCustomer extends Customer {

    public boolean add() {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {

            connection = DBConnect.getConnection();

            if(!connection.isClosed()) {

                String query = "insert into customer_info (first_name, last_name, address, email, phone, city, state, zip_code) values (?,?,?,?,?,?,?,?)";

                connection.setAutoCommit(false);
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, firstName);
                preparedStatement.setString(2, lastName);
                preparedStatement.setString(3, address);
                preparedStatement.setString(4, email);
                preparedStatement.setString(5, phone);
                preparedStatement.setString(6, city);
                preparedStatement.setString(7, state);
                preparedStatement.setString(8, zipCode);
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
}
