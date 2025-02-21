package dao;
import database.DatabaseConnection;
import entities.Equipment;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class EquipmentDAO {
    private Connection conn;

    public EquipmentDAO() {
        conn = DatabaseConnection.getConnection();
    }

    // CREATE - Add Equipment
    public void addEquipment(Equipment equipment) {
        String sql = "INSERT INTO equipment (name, type, available, price) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, equipment.getName());
            stmt.setString(2, equipment.getType());
            stmt.setBoolean(3, equipment.isAvailable());
            stmt.setDouble(4, equipment.getPrice());
            stmt.executeUpdate();
            System.out.println("✅ Equipment added successfully!");
        } catch (SQLException e) {
            System.err.println("❌ Error adding equipment: " + e.getMessage());
        }
    }

    // READ - Get All Equipment
    public List<Equipment> getAllEquipment() {
        List<Equipment> equipmentList = new ArrayList<>();
        String sql = "SELECT * FROM equipment";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Equipment equipment = new Equipment(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("type"),
                        rs.getBoolean("available"),
                        rs.getDouble("price")
                );
                equipmentList.add(equipment);
            }
        } catch (SQLException e) {
            System.err.println("❌ Error retrieving equipment: " + e.getMessage());
        }
        return equipmentList;
    }

    // UPDATE - Modify Equipment
    public void updateEquipment(Equipment equipment) {
        String sql = "UPDATE equipment SET name = ?, type = ?, available = ?, price = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, equipment.getName());
            stmt.setString(2, equipment.getType());
            stmt.setBoolean(3, equipment.isAvailable());
            stmt.setDouble(4, equipment.getPrice());
            stmt.setInt(5, equipment.getId());
            stmt.executeUpdate();
            System.out.println("✅ Equipment updated successfully!");
        } catch (SQLException e) {
            System.err.println("❌ Error updating equipment: " + e.getMessage());
        }
    }

    // DELETE - Remove Equipment
    public void deleteEquipment(int id) {
        String sql = "DELETE FROM equipment WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("✅ Equipment deleted successfully!");
        } catch (SQLException e) {
            System.err.println("❌ Error deleting equipment: " + e.getMessage());
        }
    }

}
