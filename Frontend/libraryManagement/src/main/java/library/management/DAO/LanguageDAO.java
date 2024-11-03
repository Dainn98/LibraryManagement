package library.management.DAO;

import library.management.database.KetNoiCSDL;
import library.management.entity.Language;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LanguageDAO implements DAOInterface<Language> {

    private LanguageDAO() {}

    public static LanguageDAO getInstance() {
        return new LanguageDAO();
    }

    @Override
    public int them(Language language) {
        Connection con = KetNoiCSDL.getConnection();
        String query = "INSERT INTO language (lgName) VALUES (?)";
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, language.getLgName()); // lgName là tên ngôn ngữ

            int rowsInserted = stmt.executeUpdate(); // Thực thi lệnh và lấy số dòng đã chèn
            return rowsInserted;
        } catch (SQLException e) {
            e.printStackTrace(); // Xử lý ngoại lệ SQL
        }
        return 0; // Trả về 0 nếu thêm thất bại
    }

    @Override
    public int xoa(Language language) {
        Connection con = KetNoiCSDL.getConnection();
        String query = "DELETE FROM language WHERE lgID = ?";
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, language.getLgID()); // lgID là mã ngôn ngữ để xác định bản ghi cần xóa

            int rowsDeleted = stmt.executeUpdate(); // Thực thi lệnh và lấy số dòng đã xóa
            return rowsDeleted;
        } catch (SQLException e) {
            e.printStackTrace(); // Xử lý ngoại lệ SQL
        }
        return 0; // Trả về 0 nếu xóa thất bại
    }

    @Override
    public int capNhat(Language language) {
        Connection con = KetNoiCSDL.getConnection();
        String query = "UPDATE language SET lgName = ? WHERE lgID = ?";
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, language.getLgName()); // Cập nhật tên ngôn ngữ
            stmt.setString(2, language.getLgID());   // Cập nhật mã ngôn ngữ

            int rowsUpdated = stmt.executeUpdate(); // Thực thi lệnh và lấy số dòng đã cập nhật
            return rowsUpdated;
        } catch (SQLException e) {
            e.printStackTrace(); // Xử lý ngoại lệ SQL
        }
        return 0; // Trả về 0 nếu cập nhật thất bại
    }

    public List<Language> layTatCa() {
        Connection con = KetNoiCSDL.getConnection();
        String query = "SELECT * FROM language";
        List<Language> list = new ArrayList<>();
        try (PreparedStatement stmt = con.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Language language = new Language();
                language.setSTT(rs.getInt("STT"));
                language.setLgName(rs.getString("lgName"));
                language.setLgID(rs.getString("lgID"));

                list.add(language); // Thêm đối tượng vào danh sách
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Xử lý ngoại lệ SQL
        }
        return list; // Trả về danh sách các ngôn ngữ
    }

    public Language layTheoId(int STT) {
        Connection con = KetNoiCSDL.getConnection();
        String query = "SELECT * FROM language WHERE STT = ?";
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setInt(1, STT);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Language language = new Language();
                language.setSTT(rs.getInt("STT"));
                language.setLgName(rs.getString("lgName"));
                language.setLgID(rs.getString("lgID"));

                return language; // Trả về đối tượng Language theo STT
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Xử lý ngoại lệ SQL
        }
        return null; // Trả về null nếu không tìm thấy
    }
}
