package com.autostore.app.supplier;

import com.autostore.app.controllers.DialogController;
import com.autostore.app.database.DBConnect;
import com.autostore.app.database.DBUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddSupplier extends Supplier {

    public boolean add() {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {

            connection = DBConnect.getConnection();

            if(!connection.isClosed()) {

                String query = "insert into customer_info (name, address, email, phone, city, state, contactName) values (?,?,?,?,?,?,?,?)";

                connection.setAutoCommit(false);
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, address);
                preparedStatement.setString(3, email);
                preparedStatement.setString(4, phone);
                preparedStatement.setString(5, city);
                preparedStatement.setString(6, state);
                preparedStatement.setString(7, contactName);
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

