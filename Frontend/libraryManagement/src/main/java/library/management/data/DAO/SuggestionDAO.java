package library.management.data.DAO;

import library.management.data.database.DatabaseConnection;
import library.management.data.entity.Suggestion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SuggestionDAO implements DAOInterface<Suggestion> {

    private static SuggestionDAO instance;

    private SuggestionDAO() {}

    public static synchronized SuggestionDAO getInstance() {
        if (instance == null) {
            instance = new SuggestionDAO();
        }
        return instance;
    }

    @Override
    public int add(Suggestion suggestion) {
        String query = "INSERT INTO suggestion (value, frequency) VALUES (?, ?)";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, suggestion.getValue());
            stmt.setInt(2, suggestion.getFrequency());
            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                System.err.println("Adding suggestion failed, no rows affected.");
                return 0;
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    suggestion.setId(generatedKeys.getInt(1));
                } else {
                    System.err.println("Adding suggestion failed, no ID obtained.");
                }
            }

            return affectedRows;

        } catch (SQLException e) {
            System.err.println("Error adding suggestion: " + e.getMessage());
        }
        return 0;
    }

    @Override
    public int delete(Suggestion suggestion) {
        String query = "DELETE FROM suggestion WHERE id = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setInt(1, suggestion.getId());
            return stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error deleting suggestion: " + e.getMessage());
        }
        return 0;
    }

    @Override
    public int update(Suggestion suggestion) {
        String query = "UPDATE suggestion SET value = ?, frequency = ? WHERE id = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, suggestion.getValue());
            stmt.setInt(2, suggestion.getFrequency());
            stmt.setInt(3, suggestion.getId());
            return stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error updating suggestion: " + e.getMessage());
        }
        return 0;
    }

    public List<Suggestion> getAll() {
        List<Suggestion> suggestions = new ArrayList<>();
        String query = "SELECT id, value, frequency FROM suggestion";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String value = rs.getString("value");
                int frequency = rs.getInt("frequency");

                suggestions.add(new Suggestion(id, value, frequency));
            }

        } catch (SQLException e) {
            System.err.println("Error retrieving all suggestions: " + e.getMessage());
        }
        return suggestions;
    }

    public void incrementFrequencyByValue(String value) {
        String query = """
            INSERT INTO suggestion (value, frequency) 
            VALUES (?, 1)
            ON DUPLICATE KEY UPDATE frequency = frequency + 1
            """;

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, value);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error incrementing frequency by value: " + e.getMessage());
        }
    }

}
