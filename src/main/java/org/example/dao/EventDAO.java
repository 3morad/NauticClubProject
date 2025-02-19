package org.example.dao;

import org.example.models.Event;
import org.example.utils.DBConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EventDAO {

    // CREATE
    public void createEvent(Event event) {
        String sql = "INSERT INTO events (name, description, price, event_date, location_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, event.getName());
            stmt.setString(2, event.getDescription());
            stmt.setDouble(3, event.getPrice());
            stmt.setDate(4, Date.valueOf(event.getEventDate()));
            stmt.setInt(5, event.getLocationId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // READ ALL
    public List<Event> getAllEvents() {
        List<Event> events = new ArrayList<>();
        String sql = "SELECT * FROM events";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Event ev = new Event(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getDate("event_date").toLocalDate(),
                        rs.getInt("location_id")
                );
                events.add(ev);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return events;
    }

    // READ BY ID
    public Event getEventById(int id) {
        Event event = null;
        String sql = "SELECT * FROM events WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                event = new Event(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getDate("event_date").toLocalDate(),
                        rs.getInt("location_id")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return event;
    }

    // UPDATE
    public void updateEvent(Event event) {
        String sql = "UPDATE events SET name=?, description=?, price=?, event_date=?, location_id=? WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, event.getName());
            stmt.setString(2, event.getDescription());
            stmt.setDouble(3, event.getPrice());
            stmt.setDate(4, Date.valueOf(event.getEventDate()));
            stmt.setInt(5, event.getLocationId());
            stmt.setInt(6, event.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // DELETE
    public void deleteEvent(int id) {
        String sql = "DELETE FROM events WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Assign an instructor to an event (bridging table: event_instructors)
    public void assignInstructor(int eventId, int instructorId) {
        String sql = "INSERT IGNORE INTO event_instructors (event_id, instructor_id) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, eventId);
            stmt.setInt(2, instructorId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
