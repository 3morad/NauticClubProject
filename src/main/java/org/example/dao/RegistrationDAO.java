package org.example.dao;

import org.example.models.Registration;
import org.example.utils.DBConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RegistrationDAO {

    // CREATE (register a member for an event)
    public void registerMember(int memberId, int eventId) {
        String sql = "INSERT INTO registrations (member_id, event_id) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, memberId);
            stmt.setInt(2, eventId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // READ all registrations
    public List<Registration> getAllRegistrations() {
        List<Registration> regs = new ArrayList<>();
        String sql = "SELECT * FROM registrations";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Registration r = new Registration(
                        rs.getInt("id"),
                        rs.getInt("member_id"),
                        rs.getInt("event_id"),
                        // convert DATETIME to LocalDateTime
                        rs.getTimestamp("registration_date").toLocalDateTime()
                );
                regs.add(r);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return regs;
    }

    // READ by ID
    public Registration getRegistrationById(int id) {
        Registration reg = null;
        String sql = "SELECT * FROM registrations WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                reg = new Registration(
                        rs.getInt("id"),
                        rs.getInt("member_id"),
                        rs.getInt("event_id"),
                        rs.getTimestamp("registration_date").toLocalDateTime()
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reg;
    }

    // UPDATE (if you have more fields to update)
    public void updateRegistration(Registration reg) {
        String sql = "UPDATE registrations SET member_id=?, event_id=? WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, reg.getMemberId());
            stmt.setInt(2, reg.getEventId());
            stmt.setInt(3, reg.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // DELETE (cancel a registration)
    public void deleteRegistration(int id) {
        String sql = "DELETE FROM registrations WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Possibly: getRegistrationsByEvent, getRegistrationsByMember, etc.
}
