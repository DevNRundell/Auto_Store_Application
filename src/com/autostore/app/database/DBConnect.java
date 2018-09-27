package com.autostore.app.database;

import com.autostore.app.controllers.DialogController;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect {

    private static DBConnect instance = new DBConnect();
    private static final String url = "jdbc:mysql://localhost:3306/auto_application";
    private static final String userNameDB = "root";
    private static final String passwordDB = "root";
    private static final String Driver = "com.mysql.jdbc.Driver";

    private DBConnect() {

        try {

            Class.forName(Driver);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private Connection createConnection() {

        Connection connection = null;

        try {

            connection = DriverManager.getConnection(url, userNameDB, passwordDB);
            
        } catch (SQLException e) {
            e.printStackTrace();
            DialogController.showConnectionError();
        }
  
        return connection;
        
    }

    public static Connection getConnection() {

        return instance.createConnection();

    }

}
