package dao;

import database.DatabaseConnection;
import entities.Payment;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PaymentDAO {
    private static final Logger LOGGER = Logger.getLogger(PaymentDAO.class.getName());

    // CREATE: Add a new payment with auto-incremented ticket_id
    public void addPayment(Payment payment) {
        String ticketQuery = "INSERT INTO tickets (event_id, user_id, issue_date) VALUES (?, ?, ?)";
        String paymentQuery = "INSERT INTO payments (ticket_id, amount, method, payment_date) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false); // 🔒 Start transaction to ensure ticket & payment are added together

            // 1️⃣ Insert new ticket and get generated ticket_id
            int ticketId = -1;
            try (PreparedStatement ticketStmt = conn.prepareStatement(ticketQuery, Statement.RETURN_GENERATED_KEYS)) {
                ticketStmt.setInt(1, 1); // Dummy event_id (replace with actual logic)
                ticketStmt.setInt(2, 1); // Dummy user_id (replace with actual logic)
                ticketStmt.setDate(3, new java.sql.Date(System.currentTimeMillis()));

                ticketStmt.executeUpdate();

                try (ResultSet generatedKeys = ticketStmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        ticketId = generatedKeys.getInt(1);
                    }
                }
            }

            if (ticketId == -1) {
                throw new SQLException("❌ Failed to generate ticket_id!");
            }

            // 2️⃣ Insert payment using the generated ticket_id
            try (PreparedStatement paymentStmt = conn.prepareStatement(paymentQuery)) {
                paymentStmt.setInt(1, ticketId);
                paymentStmt.setDouble(2, payment.getAmount());
                paymentStmt.setString(3, payment.getMethod());
                paymentStmt.setDate(4, new java.sql.Date(payment.getDate().getTime()));

                paymentStmt.executeUpdate();
            }

            conn.commit(); // ✅ Commit transaction
            LOGGER.info("✅ Payment added successfully with new ticket_id: " + ticketId);

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "❌ SQL Error", e);
        }
    }

    // READ: Get all payments
    public List<Payment> getAllPayments() {
        List<Payment> payments = new ArrayList<>();
        String sql = "SELECT * FROM payments";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Payment payment = new Payment(
                        rs.getInt("id"),
                        rs.getInt("ticket_id"),
                        rs.getDouble("amount"),
                        rs.getString("method"),
                        rs.getDate("payment_date")
                );
                payments.add(payment);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "❌ SQL Error", e);
        }
        return payments;
    }

    // UPDATE: Update payment amount and method
    public void updatePayment(int paymentId, double newAmount, String newMethod) {
        String sql = "UPDATE payments SET amount = ?, method = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, newAmount);
            stmt.setString(2, newMethod);
            stmt.setInt(3, paymentId);
            stmt.executeUpdate();
            LOGGER.info("✅ Payment updated successfully!");
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "❌ SQL Error", e);
        }
    }

    // DELETE: Delete a payment
    public void deletePayment(int paymentId) {
        String sql = "DELETE FROM payments WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, paymentId);
            stmt.executeUpdate();
            LOGGER.info("✅ Payment deleted successfully!");
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "❌ SQL Error", e);
        }
    }
}

