package org.example.dao;

import org.example.models.Location;
import org.example.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LocationDAO {

    /**
     * Creates a new location in the database and returns the auto-generated ID.
     *
     * @param loc The Location object containing name, address, and file.
     * @return The newly generated ID (auto-increment) from the 'locations' table, or -1 if something failed.
     */
    public int createLocation(Location loc) {
        String sql = "INSERT INTO locations (name, address, file) VALUES (?, ?, ?)";
        int generatedId = -1; // Default if insertion fails

        try (Connection conn = DBConnection.getConnection();
             // Ask for generated keys
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, loc.getName());
            stmt.setString(2, loc.getAddress());
            stmt.setString(3, loc.getFile());

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                // Retrieve the auto-generated key
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        generatedId = rs.getInt(1);
                        loc.setId(generatedId); // Optionally store the ID in the Location object
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return generatedId;
    }

    /**
     * Retrieves all locations from the database.
     *
     * @return A List of Location objects.
     */
    public List<Location> getAllLocations() {
        List<Location> list = new ArrayList<>();
        String sql = "SELECT * FROM locations";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Location loc = new Location(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("address"),
                        rs.getString("file")
                );
                list.add(loc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Retrieves a single Location by its ID.
     *
     * @param id The location's ID.
     * @return A Location object or null if not found.
     */
    public Location getLocationById(int id) {
        Location loc = null;
        String sql = "SELECT * FROM locations WHERE id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                loc = new Location(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("address"),
                        rs.getString("file")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return loc;
    }

    /**
     * Updates an existing Location in the database.
     *
     * @param loc The Location object with updated fields (including the ID).
     */
    public void updateLocation(Location loc) {
        String sql = "UPDATE locations SET name=?, address=?, file=? WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, loc.getName());
            stmt.setString(2, loc.getAddress());
            stmt.setString(3, loc.getFile());
            stmt.setInt(4, loc.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes a Location from the database by its ID.
     *
     * @param id The location's ID.
     */
    public void deleteLocation(int id) {
        String sql = "DELETE FROM locations WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
