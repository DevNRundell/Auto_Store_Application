package com.autostore.app.supplier;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.autostore.app.controllers.DialogController;
import com.autostore.app.database.DBConnect;
import com.autostore.app.database.DBUtils;

public class UpdateSupplier extends Supplier {

    public boolean update() {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {

            connection = DBConnect.getConnection();

            if(!connection.isClosed()) {

                String query = "update supplier set name = ?, address = ?, city = ?, state = ?, email = ?, phone = ?, contact_name = ? where supplier_id = ?";

                connection.setAutoCommit(false);
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, address);
                preparedStatement.setString(3, city);
                preparedStatement.setString(4, state);
                preparedStatement.setString(5, email);
                preparedStatement.setString(6, phone);
                preparedStatement.setString(7, contactName);
                preparedStatement.setInt(8, supplierID);

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

