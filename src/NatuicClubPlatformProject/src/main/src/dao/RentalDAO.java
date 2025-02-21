package dao;
import database.DatabaseConnection;
import entities.Rental;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RentalDAO {
    private Connection conn;

    public RentalDAO() {
        conn = DatabaseConnection.getConnection();
    }

    // CREATE - Add Rental
    public void addRental(Rental rental) {
        String sql = "INSERT INTO rentals (user_id, equipment_id, status, start_time, end_time) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(2, rental.getEquipment().getId());
            stmt.setString(3, rental.getStatus());
            stmt.setTimestamp(4, Timestamp.valueOf(rental.getstart_time()));
            stmt.setTimestamp(5, rental.getEnd_time() != null ? Timestamp.valueOf(rental.getEnd_time()) : null);
            stmt.executeUpdate();
            System.out.println("✅ Rental added successfully!");
        } catch (SQLException e) {
            System.err.println("❌ Error adding rental: " + e.getMessage());
        }
    }

    // READ - Get All Rentals
    public List<Rental> getAllRentals() {
        List<Rental> rentalList = new ArrayList<>();
        String sql = "SELECT * FROM rentals";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Rental rental = new Rental (

                        rs.getString("status"),
                        rs.getTimestamp("start_time").toLocalDateTime(),
                        rs.getTimestamp("end_time").toLocalDateTime()
                );
                rentalList.add(rental);
            }
        } catch (SQLException e) {
            System.err.println("❌ Error retrieving rentals: " + e.getMessage());
        }
        return rentalList;
    }

    // UPDATE - Modify Rental
    public void updateRental(Rental rental) {
        String sql = "UPDATE rentals SET user_id = ?, equipment_id = ?, status = ?, start_time = ?, end_time = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(2, rental.getEquipment().getId());
            stmt.setString(3, rental.getStatus());
            stmt.setTimestamp(4, Timestamp.valueOf(rental.getstart_time()));
            stmt.setTimestamp(5, rental.getEnd_time() != null ? Timestamp.valueOf(rental.getEnd_time()) : null);
            stmt.setInt(6, rental.getEquipment().getId());
            stmt.executeUpdate();
            System.out.println("✅ Rental updated successfully!");
        } catch (SQLException e) {
            System.err.println("❌ Error updating rental: " + e.getMessage());
        }
    }

    // DELETE - Remove Rental
    public void deleteRental(int id) {
        String sql = "DELETE FROM rentals WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("✅ Rental deleted successfully!");
        } catch (SQLException e) {
            System.err.println("❌ Error deleting rental: " + e.getMessage());
        }
    }


}
