package dao;

import database.MyDatabase;
import entities.Equipment;
import java.sql.*;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class EquipmentDAO {
    private  Connection conn;
    public EquipmentDAO(Connection conn) {
        this.conn = conn;
    }

    // Constructor initializes a database connection if not provided
    public EquipmentDAO() {
        try {
            this.conn = MyDatabase.getConnection(); // Ensure a valid connection
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Database connection failed.");
        }
    }
    public Equipment getEquipmentById(int id) throws SQLException {
        String sql = "SELECT * FROM equipment WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToEquipment(rs);
                }
            }
        }
        return null;
    }

    public boolean addEquipment(Equipment equipment) throws SQLException {
        String sql = "INSERT INTO equipment (name, type, availability, price) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, equipment.getName());
            pstmt.setString(2, equipment.getType());
            pstmt.setBoolean(3, equipment.isAvailable());
            pstmt.setInt(4, equipment.getPrice());
            pstmt.executeUpdate();
        }
        return false;
    }
    public boolean isEquipmentAvailable(int equipmentId, LocalDate startDate, LocalDate endDate) throws SQLException {
        String sql = "SELECT availability FROM equipment WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, equipmentId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next() && !rs.getBoolean("availability")) {
                return false; // Equipment inherently unavailable
            }
        }
        sql = "SELECT * FROM rentals WHERE equipment_id = ? AND (start_date <= ? AND end_date >= ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, equipmentId);
            pstmt.setDate(2, java.sql.Date.valueOf(endDate));
            pstmt.setDate(3, java.sql.Date.valueOf(startDate));
            ResultSet rs = pstmt.executeQuery();

        }
        return false;
    }
    // Get all Equipment
    public List<Equipment> getAllEquipment() throws SQLException {
        List<Equipment> equipmentList = new ArrayList<>();
        String sql = "SELECT * FROM equipment";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                equipmentList.add(mapResultSetToEquipment(rs));
            }
        }
        return equipmentList;
    }

    // ✅ New method: Get Available Equipment
    public List<Equipment> getAvailableEquipment() throws SQLException {
        List<Equipment> availableEquipment = new ArrayList<>();
        String sql = "SELECT * FROM equipment WHERE availability = true";

        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                availableEquipment.add(mapResultSetToEquipment(rs));
            }
        }
        System.out.println("✅ Fetched available equipment: " + availableEquipment.size() + " items.");
        return availableEquipment;
    }


    // Dynamic Price calculation (add this in the DAO or service layer)
    public int calculateDynamicPrice(int basePrice) {
        // Assume peak season increases price by 20%
        LocalDate currentDate = LocalDate.now();
        if (currentDate.getMonth() == Month.DECEMBER || currentDate.getMonth() == Month.JULY) {
            return (int)(basePrice * 1.2); // 20% increase for peak season
        }
        return basePrice; // Regular price
    }

    // Modify the update method to use dynamic price
    public boolean updateEquipment(int id, Equipment equipment) throws SQLException {
        int dynamicPrice = calculateDynamicPrice(equipment.getPrice());  // Use dynamic price
        String sql = "UPDATE equipment SET name = ?, type = ?, price = ? WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, equipment.getName());
            pstmt.setString(2, equipment.getType());
            pstmt.setInt(3, dynamicPrice);  // Set dynamic price
            pstmt.setInt(4, id);
            pstmt.executeUpdate();
        }
        return false;
    }

    // Delete Equipment
    public boolean deleteEquipment(int id) throws SQLException {
        String sql = "DELETE FROM equipment WHERE id=?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            System.out.println("✅ Equipment deleted successfully.");
        }
        return false;
    }

    // ✅ Helper method: Convert ResultSet to Equipment object
    private Equipment mapResultSetToEquipment(ResultSet rs) throws SQLException {
        return new Equipment(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getBoolean("availability"),
                rs.getString("type"),
                rs.getInt("price")
        );
    }

}
