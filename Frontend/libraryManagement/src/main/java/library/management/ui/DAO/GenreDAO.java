package library.management.ui.DAO;

import library.management.ui.database.databaseConnection;
import library.management.ui.entity.Genre;

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

    @Override
    public int add(Genre genre) {
        Connection con = databaseConnection.getConnection();
        String query = "INSERT INTO genre (tag) VALUES (?)";
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, genre.getTag()); // tag là tên thể loại

            int rowsInserted = stmt.executeUpdate(); // Thực thi lệnh và lấy số dòng đã chèn
            return rowsInserted;
        } catch (SQLException e) {
            e.printStackTrace(); // Xử lý ngoại lệ SQL
        }
        return 0; // Trả về 0 nếu thêm thất bại
    }

    @Override
    public int delete(Genre genre) {
        Connection con = databaseConnection.getConnection();
        String query = "DELETE FROM genre WHERE genreID = ?";
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, genre.getGenreID()); // STT là khóa chính

            int rowsDeleted = stmt.executeUpdate(); // Thực thi lệnh và lấy số dòng đã xóa
            return rowsDeleted;
        } catch (SQLException e) {
            e.printStackTrace(); // Xử lý ngoại lệ SQL
        }
        return 0; // Trả về 0 nếu xóa thất bại
    }

    @Override
    public int update(Genre genre) {
        Connection con = databaseConnection.getConnection();
        String query = "UPDATE genre SET tag = ? WHERE genreID = ?";
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, genre.getTag()); // Cập nhật tag
            stmt.setString(2, genre.getGenreID()); // Cập nhật genreID

            int rowsUpdated = stmt.executeUpdate(); // Thực thi lệnh và lấy số dòng đã cập nhật
            return rowsUpdated;
        } catch (SQLException e) {
            e.printStackTrace(); // Xử lý ngoại lệ SQL
        }
        return 0; // Trả về 0 nếu cập nhật thất bại
    }

    public List<Genre> layTatCa() {
        Connection con = databaseConnection.getConnection();
        String query = "SELECT * FROM genre";
        List<Genre> list = new ArrayList<>();
        try (PreparedStatement stmt = con.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Genre genre = new Genre();
                genre.setSTT(rs.getInt("STT"));
                genre.setTag(rs.getString("tag"));
                genre.setGenreID(rs.getString("genreID"));

                list.add(genre); // Thêm đối tượng vào danh sách
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Xử lý ngoại lệ SQL
        }
        return list; // Trả về danh sách các thể loại sách
    }

    public Genre layTheoId(int STT) {
        Connection con = databaseConnection.getConnection();
        String query = "SELECT * FROM genre WHERE STT = ?";
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setInt(1, STT);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Genre genre = new Genre();
                genre.setSTT(rs.getInt("STT"));
                genre.setTag(rs.getString("tag"));
                genre.setGenreID(rs.getString("genreID"));

                return genre; // Trả về thể loại sách theo ID
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Xử lý ngoại lệ SQL
        }
        return null; // Trả về null nếu không tìm thấy
    }
}
