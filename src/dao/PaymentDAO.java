package dao;

import database.DatabaseConnection;
import entities.Payment;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentDAO {

    // CREATE: Add a new payment
    public void addPayment(Payment payment) {
        String sql = "INSERT INTO payments (ticket_id, amount, method, payment_date) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, payment.getTicketId());
            stmt.setDouble(2, payment.getAmount());
            stmt.setString(3, payment.getMethod());
            stmt.setDate(4, new java.sql.Date(payment.getPaymentDate().getTime()));
            stmt.executeUpdate();
            System.out.println("✅ Payment added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
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
            e.printStackTrace();
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
            System.out.println("✅ Payment updated successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // DELETE: Delete a payment
    public void deletePayment(int paymentId) {
        String sql = "DELETE FROM payments WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, paymentId);
            stmt.executeUpdate();
            System.out.println("✅ Payment deleted successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
