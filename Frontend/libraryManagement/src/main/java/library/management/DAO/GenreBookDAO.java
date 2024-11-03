package library.management.DAO;

import library.management.database.KetNoiCSDL;
import library.management.entity.GenreBook;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GenreBookDAO implements DAOInterface<GenreBook> {

    private GenreBookDAO() {}

    public static GenreBookDAO getInstance() {
        return new GenreBookDAO();
    }

    @Override
    public int them(GenreBook genreBook) {
        Connection con = KetNoiCSDL.getConnection();
        String query = "INSERT INTO genrebook (tag) VALUES (?)";
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, genreBook.getTag()); // tag là tên thể loại

            int rowsInserted = stmt.executeUpdate(); // Thực thi lệnh và lấy số dòng đã chèn
            return rowsInserted;
        } catch (SQLException e) {
            e.printStackTrace(); // Xử lý ngoại lệ SQL
        }
        return 0; // Trả về 0 nếu thêm thất bại
    }

    @Override
    public int xoa(GenreBook genreBook) {
        Connection con = KetNoiCSDL.getConnection();
        String query = "DELETE FROM genrebook WHERE genreID = ?";
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, genreBook.getGenreID()); // STT là khóa chính

            int rowsDeleted = stmt.executeUpdate(); // Thực thi lệnh và lấy số dòng đã xóa
            return rowsDeleted;
        } catch (SQLException e) {
            e.printStackTrace(); // Xử lý ngoại lệ SQL
        }
        return 0; // Trả về 0 nếu xóa thất bại
    }

    @Override
    public int capNhat(GenreBook genreBook) {
        Connection con = KetNoiCSDL.getConnection();
        String query = "UPDATE genrebook SET tag = ? WHERE genreID = ?";
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, genreBook.getTag()); // Cập nhật tag
            stmt.setString(2, genreBook.getGenreID()); // Cập nhật genreID

            int rowsUpdated = stmt.executeUpdate(); // Thực thi lệnh và lấy số dòng đã cập nhật
            return rowsUpdated;
        } catch (SQLException e) {
            e.printStackTrace(); // Xử lý ngoại lệ SQL
        }
        return 0; // Trả về 0 nếu cập nhật thất bại
    }

    public List<GenreBook> layTatCa() {
        Connection con = KetNoiCSDL.getConnection();
        String query = "SELECT * FROM genrebook";
        List<GenreBook> list = new ArrayList<>();
        try (PreparedStatement stmt = con.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                GenreBook genreBook = new GenreBook();
                genreBook.setSTT(rs.getInt("STT"));
                genreBook.setTag(rs.getString("tag"));
                genreBook.setGenreID(rs.getString("genreID"));

                list.add(genreBook); // Thêm đối tượng vào danh sách
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Xử lý ngoại lệ SQL
        }
        return list; // Trả về danh sách các thể loại sách
    }

    public GenreBook layTheoId(int STT) {
        Connection con = KetNoiCSDL.getConnection();
        String query = "SELECT * FROM genrebook WHERE STT = ?";
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setInt(1, STT);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                GenreBook genreBook = new GenreBook();
                genreBook.setSTT(rs.getInt("STT"));
                genreBook.setTag(rs.getString("tag"));
                genreBook.setGenreID(rs.getString("genreID"));

                return genreBook; // Trả về thể loại sách theo ID
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Xử lý ngoại lệ SQL
        }
        return null; // Trả về null nếu không tìm thấy
    }
}
