package com.autostore.app.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBUtils {

    public static void closeConn(Connection connection) {

        if (connection != null) {

            try {

                connection.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void closeStatement(PreparedStatement statement) {

        if(statement != null) {

            try {

                statement.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void closeResultSet(ResultSet resultSet) {

        if(resultSet != null) {

            try {

                resultSet.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
