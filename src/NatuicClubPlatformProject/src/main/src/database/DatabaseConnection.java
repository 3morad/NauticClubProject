//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/nautic_club?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public DatabaseConnection() {
    }

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/nautic_club?useSSL=false&serverTimezone=UTC", "root", "");
        } catch (ClassNotFoundException var1) {
            ClassNotFoundException e = var1;
            throw new RuntimeException("❌ MySQL JDBC Driver not found!", e);
        } catch (SQLException var2) {
            SQLException e = var2;
            throw new RuntimeException("❌ Database connection failed!", e);
        }
    }
}

