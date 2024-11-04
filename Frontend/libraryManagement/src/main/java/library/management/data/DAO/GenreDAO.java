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

    // Lấy danh sách tất cả các thể loại
    public List<Genre> layTatCa() {
        String query = "SELECT * FROM genre";
        List<Genre> list = new ArrayList<>();
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Genre genre = new Genre();
                genre.setSTT(rs.getInt("STT"));
                genre.setTag(rs.getString("tag"));
                genre.setGenreID(rs.getString("genreID"));
                list.add(genre);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Lấy thông tin thể loại theo STT
    public Genre layTheoId(int STT) {
        String query = "SELECT * FROM genre WHERE STT = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setInt(1, STT);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Genre genre = new Genre();
                    genre.setSTT(rs.getInt("STT"));
                    genre.setTag(rs.getString("tag"));
                    genre.setGenreID(rs.getString("genreID"));
                    return genre;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
