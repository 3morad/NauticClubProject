package org.example.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    // Adjust if your DB name is not "pidev_db"
    private static final String URL = "jdbc:mysql://localhost:3306/pidev_db";
    private static final String USER = "root";
    private static final String PASS = "rayen346139";

    // Optionally, you can add "?serverTimezone=UTC" if you have timezone issues:
    // private static final String URL = "jdbc:mysql://localhost:3306/pidev_db?serverTimezone=UTC";

    private static Connection connection;

    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(URL, USER, PASS);
                System.out.println("DB connection established successfully!");
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException("Error connecting to the database", e);
            }
        }
        return connection;
    }
}
