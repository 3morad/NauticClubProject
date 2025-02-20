package org.example.test;

import org.example.utils.DBConnection;

import java.sql.Connection;
import java.sql.SQLException;

public class TestDBConnection {
    public static void main(String[] args) {
        try (Connection conn = DBConnection.getConnection()) {
            if (conn != null && !conn.isClosed()) {
                System.out.println("DB connection successful!");
            } else {
                System.out.println("DB connection failed or is closed!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error: " + e.getMessage());
        }
    }
}
