package database;

import entities.Equipment;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.SQLException;

public class MyDatabase {

    // Method to get all Equipment from the database
    public List<Equipment> getAllEquipment() {
        List<Equipment> equipmentList = new ArrayList<>();
        String sql = "SELECT * FROM equipment";

        // Using try-with-resources to ensure automatic closing of resources
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Equipment equipment = new Equipment(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getBoolean("availability"),
                        rs.getString("type"),
                        rs.getInt("price")
                );
                equipmentList.add(equipment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return equipmentList;
    }
    public static Connection getConnection() throws SQLException {
        return DatabaseConnection.getConnection();
    }

    // Method to add Equipment to the database
    public void addEquipment(Equipment equipment) {
        String sql = "INSERT INTO equipment (name, type, availability, price) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, equipment.getName());
            pstmt.setString(2, equipment.getType());
            pstmt.setBoolean(3, equipment.isAvailable());
            pstmt.setInt(4, equipment.getPrice());
            pstmt.executeUpdate();
            System.out.println("Equipment added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to update Equipment in the database
    public void updateEquipment(int id, Equipment equipment) {
        String sql = "UPDATE equipment SET name=?, type=?, availability=?, price=? WHERE id=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, equipment.getName());
            pstmt.setString(2, equipment.getType());
            pstmt.setBoolean(3, equipment.isAvailable());
            pstmt.setInt(4, equipment.getPrice());
            pstmt.setInt(5, id);
            pstmt.executeUpdate();
            System.out.println("Equipment updated successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to delete Equipment from the database
    public void deleteEquipment(int id) {
        String sql = "DELETE FROM equipment WHERE id=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            System.out.println("Equipment deleted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
