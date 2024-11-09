package library.management.data.DAO;

import library.management.data.database.DatabaseConnection;
import library.management.data.entity.Genre;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GenreDAO implements DAOInterface<Genre> {

    private GenreDAO() {}

    public static GenreDAO getInstance() {
        return new GenreDAO();
    }

    // Thêm một thể loại vào cơ sở dữ liệu
    @Override
    public int add(Genre genre) {
        String query = "INSERT INTO genre (tag) VALUES (?)";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, genre.getTag());
            int rowsInserted = stmt.executeUpdate();
            return rowsInserted;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Xóa một thể loại khỏi cơ sở dữ liệu
    @Override
    public int delete(Genre genre) {
        String query = "DELETE FROM genre WHERE genreID = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, genre.getGenreID());
            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Cập nhật thông tin của một thể loại trong cơ sở dữ liệu
    @Override
    public int update(Genre genre) {
        String query = "UPDATE genre SET tag = ? WHERE genreID = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, genre.getTag());
            stmt.setString(2, genre.getGenreID());
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<String> getAllTags() {
        List<String> tags = new ArrayList<>();
        String query = "SELECT tag FROM genre";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                tags.add(rs.getString("tag"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tags;
    }

    public List<Genre> getGenreList() {
        List<Genre> genres = new ArrayList<>();
        String query = "SELECT genreID, tag FROM genre";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String genreID = rs.getString("genreID");
                String tag = rs.getString("tag");
                Genre genre = new Genre(genreID, tag);
                genres.add(genre);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return genres;
    }
}
