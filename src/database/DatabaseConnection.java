package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/nautic_club?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root"; // Change if needed
    private static final String PASSWORD = ""; // Default for WAMP/XAMPP

    public static Connection getConnection() {
        try {
            // ✅ Explicitly load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("❌ MySQL JDBC Driver not found!", e);
        } catch (SQLException e) {
            throw new RuntimeException("❌ Database connection failed!", e);
        }
    }
}