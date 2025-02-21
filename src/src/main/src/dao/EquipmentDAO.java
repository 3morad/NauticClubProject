package dao;
import database.MyDatabase;
import entities.Equipment;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class EquipmentDAO {
    // Create Equipment
    public void addEquipment(Equipment equipment) {
        String sql = "INSERT INTO equipment (name, type, availability, price) VALUES (?, ?, ?, ?)";
        try (Connection conn = MyDatabase.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, equipment.getName());
            pstmt.setString(2, equipment.getType());
            pstmt.setBoolean(3, equipment.isAvailable());
            pstmt.setInt(4, equipment.getPrice());
            pstmt.executeUpdate();
            System.out.println("Equipment added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            // Read Equipment
            public List<Equipment> getAllEquipment() {
                List<Equipment> equipmentList = new ArrayList<>();
                String sql = "SELECT * FROM equipment";
                try (Connection conn = MyDatabase.getConnection();
                     Statement stmt = conn.createStatement();
                     ResultSet rs = stmt.executeQuery(sql)) {

                    while (rs.next()) {
                        Equipment equipment = new Equipment(
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

            // Update Equipment
            public void updateEquipment(int id, Equipment equipment) {
                String sql = "UPDATE equipment SET name=?, type=?, availability=?, price=? WHERE id=?";
                try (Connection conn = MyDatabase.getConnection();
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

            // Delete Equipment
            public void deleteEquipment(int id) {
                String sql = "DELETE FROM equipment WHERE id=?";
                try (Connection conn = MyDatabase.getConnection();
                     PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setInt(1, id);
                    pstmt.executeUpdate();
                    System.out.println("Equipment deleted successfully.");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
