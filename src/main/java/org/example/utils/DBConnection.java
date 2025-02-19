package org.example.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    // Database URL, user, and password
    private static final String URL = "jdbc:mysql://localhost:3306/pidev_db";
    private static final String USER = "root";
    private static final String PASSWORD = "rayen346139";

    private static Connection connection = null;

    private DBConnection() {
        // private constructor to prevent instantiation
    }

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        }
        return connection;
    }
}
