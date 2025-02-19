package dao;

import database.DatabaseConnection;
import entities.Feedback;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FeedbackDAO {

    // CREATE: Add a new feedback
    public void addFeedback(Feedback feedback) {
        String sql = "INSERT INTO feedback (user_id, event_id, comment, rating) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, feedback.getUserId());
            stmt.setInt(2, feedback.getEventId());
            stmt.setString(3, feedback.getComment());
            stmt.setInt(4, feedback.getRating());
            stmt.executeUpdate();
            System.out.println("✅ Feedback added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // READ: Get all feedback
    public List<Feedback> getAllFeedback() {
        List<Feedback> feedbackList = new ArrayList<>();
        String sql = "SELECT * FROM feedback";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Feedback feedback = new Feedback(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getInt("event_id"),
                        rs.getString("comment"),
                        rs.getInt("rating")
                );
                feedbackList.add(feedback);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return feedbackList;
    }

    // UPDATE: Update a feedback comment and rating
    public void updateFeedback(int feedbackId, String newComment, int newRating) {
        String sql = "UPDATE feedback SET comment = ?, rating = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newComment);
            stmt.setInt(2, newRating);
            stmt.setInt(3, feedbackId);
            stmt.executeUpdate();
            System.out.println("✅ Feedback updated successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // DELETE: Delete a feedback entry
    public void deleteFeedback(int feedbackId) {
        String sql = "DELETE FROM feedback WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, feedbackId);
            stmt.executeUpdate();
            System.out.println("✅ Feedback deleted successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
