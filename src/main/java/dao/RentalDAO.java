package dao;

import entities.Rental;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RentalDAO {
    private Connection connection;

    public RentalDAO(Connection connection) {
        this.connection = connection;
    }

    public RentalDAO() {

    }

    // 1. Add a new rental
    public boolean addRental(Rental rental) throws SQLException {
        EquipmentDAO equipmentDAO = new EquipmentDAO(connection);
        if (!equipmentDAO.isEquipmentAvailable(rental.getEquipmentId(), rental.getStartDate(), rental.getEndDate())) {
            throw new SQLException("Equipment is not available for these dates.");
        }
        String sql = "INSERT INTO Rental (equipment_id, user_id, start_date, end_date, status) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, rental.getEquipmentId());
            stmt.setInt(2, rental.getUserId());
            stmt.setDate(3, Date.valueOf(rental.getStartDate()));
            stmt.setDate(4, Date.valueOf(rental.getEndDate()));
            stmt.setString(5, rental.getStatus());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

    // 2. Get a rental by ID
    public List<Rental> getRentalsByUser(int userId) throws SQLException {
        List<Rental> rentals = new ArrayList<>();
        String sql = "SELECT * FROM Rental WHERE user_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    rentals.add(new Rental(
                            rs.getInt("id"),
                            rs.getInt("equipment_id"),
                            rs.getInt("user_id"),
                            rs.getDate("start_date").toLocalDate(),
                            rs.getDate("end_date").toLocalDate(),
                            rs.getString("status")
                    ));
                }
            }
        }
        return rentals;
    }

    // 3. Get all rentals
    public List<Rental> getAllRentals() throws SQLException {
        List<Rental> rentals = new ArrayList<>();
        String sql = "SELECT * FROM Rental";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                rentals.add(new Rental(
                        rs.getInt("id"),
                        rs.getInt("equipment_id"),
                        rs.getInt("user_id"),
                        rs.getDate("start_date").toLocalDate(),
                        rs.getDate("end_date").toLocalDate(),
                        rs.getString("status")
                ));
            }
        }
        return rentals;
    }

    // 4. Update a rental
    public void updateRental(Rental rental) throws SQLException {
        String sql = "UPDATE Rental SET equipment_id = ?, user_id = ?, start_date = ?, end_date = ?, status = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, rental.getEquipmentId());
            stmt.setInt(2, rental.getUserId());
            stmt.setDate(3, Date.valueOf(rental.getStartDate()));
            stmt.setDate(4, Date.valueOf(rental.getEndDate()));
            stmt.setString(5, rental.getStatus());
            stmt.setInt(6, rental.getId());
            stmt.executeUpdate();
        }
    }

    // Delete a rental and return boolean indicating success
    public boolean deleteRental(int rentalId) throws SQLException {
        String sql = "DELETE FROM Rental WHERE id=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, rentalId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;  // Return true if rental was deleted successfully
        }
    }

}

