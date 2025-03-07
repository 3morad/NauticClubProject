package api;

import dao.RentalDAO;
import database.DatabaseConnection;
import entities.Rental;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class RentalBookingAPI {
    private final RentalDAO rentalDAO;

    public RentalBookingAPI() {
        try {
            // Pass the database connection to RentalDAO
            Connection conn = DatabaseConnection.getConnection();
            this.rentalDAO = new RentalDAO(conn);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to establish database connection", e);
        }
    }

    public boolean bookRental(Rental rental) {
        try {
            return rentalDAO.addRental(rental);  // Return true if rental was added successfully
        } catch (SQLException e) {
            e.printStackTrace();  // Log error
            return false;  // Return false if an error occurred
        }
    }

    public List<Rental> getUserRentals(int userId) {
        try {
            return rentalDAO.getRentalsByUser(userId);  // Return list of rentals for the user
        } catch (SQLException e) {
            e.printStackTrace();  // Log error
            return null;  // Return null if an error occurred
        }
    }

    public boolean cancelRental(int rentalId) {
        try {
            return rentalDAO.deleteRental(rentalId);  // Return true if rental was deleted successfully
        } catch (SQLException e) {
            e.printStackTrace();  // Log error
            return false;  // Return false if an error occurred
        }
    }
}


