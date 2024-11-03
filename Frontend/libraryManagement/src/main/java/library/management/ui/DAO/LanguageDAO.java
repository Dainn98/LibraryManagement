package library.management.ui.DAO;

import library.management.ui.database.DatabaseConnection;
import library.management.ui.entity.Language;

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

    // Thêm một ngôn ngữ vào cơ sở dữ liệu
    @Override
    public int add(Language language) {
        String query = "INSERT INTO language (lgName) VALUES (?)";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, language.getLgName());
            int rowsInserted = stmt.executeUpdate();
            return rowsInserted;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Xóa một ngôn ngữ khỏi cơ sở dữ liệu
    @Override
    public int delete(Language language) {
        String query = "DELETE FROM language WHERE lgID = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, language.getLgID());
            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Cập nhật thông tin của một ngôn ngữ trong cơ sở dữ liệu
    @Override
    public int update(Language language) {
        String query = "UPDATE language SET lgName = ? WHERE lgID = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, language.getLgName());
            stmt.setString(2, language.getLgID());
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Lấy danh sách tất cả các ngôn ngữ
    public List<Language> layTatCa() {
        String query = "SELECT * FROM language";
        List<Language> list = new ArrayList<>();
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Language language = new Language();
                language.setSTT(rs.getInt("STT"));
                language.setLgName(rs.getString("lgName"));
                language.setLgID(rs.getString("lgID"));
                list.add(language);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Lấy thông tin ngôn ngữ theo STT
    public Language layTheoId(int STT) {
        String query = "SELECT * FROM language WHERE STT = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setInt(1, STT);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Language language = new Language();
                    language.setSTT(rs.getInt("STT"));
                    language.setLgName(rs.getString("lgName"));
                    language.setLgID(rs.getString("lgID"));
                    return language;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
