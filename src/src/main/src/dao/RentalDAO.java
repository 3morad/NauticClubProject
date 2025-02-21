package dao;
import database.MyDatabase;
import entities.Rental;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class RentalDAO {
    // Create Rental
    public void addRental(Rental rental) {
        String sql = "INSERT INTO rental (status, start_time, end_time, equipment_id) VALUES (?, ?, ?, ?)";
        try (Connection conn = MyDatabase.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, rental.getStatus());
            pstmt.setTimestamp(2, new Timestamp(rental.getSatrt_time().getTime()));
            pstmt.setTimestamp(3, new Timestamp(rental.getEnd_time().getTime()));
            pstmt.setInt(4, rental.getEquipment().getId()); // Assuming Equipment has an id
            pstmt.executeUpdate();
            System.out.println("Rental added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Read Rentals
    public List<Rental> getAllRentals() {
        List<Rental> rentalList = new ArrayList<>();
        String sql = "SELECT * FROM rental";
        try (Connection conn = MyDatabase.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Rental rental = new Rental(
                        rs.getString("status"),
                        rs.getTimestamp("start_time"),
                        rs.getTimestamp("end_time"),
                        null // Need to fetch Equipment separately
                );
                rentalList.add(rental);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rentalList;
    }

    // Update Rental
    public void updateRental(int id, Rental rental) {
        String sql = "UPDATE rental SET status=?, start_time=?, end_time=?, equipment_id=? WHERE id=?";
        try (Connection conn = MyDatabase.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, rental.getStatus());
            pstmt.setTimestamp(2, new Timestamp(rental.getSatrt_time().getTime()));
            pstmt.setTimestamp(3, new Timestamp(rental.getEnd_time().getTime()));
            pstmt.setInt(4, rental.getEquipment().getId());
            pstmt.setInt(5, id);
            pstmt.executeUpdate();
            System.out.println("Rental updated successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete Rental
    public void deleteRental(int id) {
        String sql = "DELETE FROM rental WHERE id=?";
        try (Connection conn = MyDatabase.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            System.out.println("Rental deleted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
