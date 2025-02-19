package dao;

import database.DatabaseConnection;
import entities.Ticket;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TicketDAO {

    // CREATE: Add a new ticket
    public void addTicket(Ticket ticket) {
        String sql = "INSERT INTO tickets (event_id, user_id, issue_date) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, ticket.getEventId());
            stmt.setInt(2, ticket.getUserId());
            stmt.setDate(3, new java.sql.Date(ticket.getIssueDate().getTime()));
            stmt.executeUpdate();
            System.out.println("✅ Ticket added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // READ: Get all tickets
    public List<Ticket> getAllTickets() {
        List<Ticket> tickets = new ArrayList<>();
        String sql = "SELECT * FROM tickets";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Ticket ticket = new Ticket(
                        rs.getInt("id"),
                        rs.getInt("event_id"),
                        rs.getInt("user_id"),
                        rs.getDate("issue_date")
                );
                tickets.add(ticket);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tickets;
    }

    // UPDATE: Update ticket issue date
    public void updateTicket(int ticketId, Date newIssueDate) {
        String sql = "UPDATE tickets SET issue_date = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, new java.sql.Date(newIssueDate.getTime()));
            stmt.setInt(2, ticketId);
            stmt.executeUpdate();
            System.out.println("✅ Ticket updated successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // DELETE: Delete a ticket
    public void deleteTicket(int ticketId) {
        String sql = "DELETE FROM tickets WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, ticketId);
            stmt.executeUpdate();
            System.out.println("✅ Ticket deleted successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
