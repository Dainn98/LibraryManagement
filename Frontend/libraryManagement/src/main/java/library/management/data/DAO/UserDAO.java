package library.management.data.DAO;

import library.management.data.database.DatabaseConnection;
import library.management.data.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO implements DAOInterface<User> {

    private UserDAO() {}

    public static UserDAO getInstance() {
        return new UserDAO();
    }

    // Thêm một người dùng vào cơ sở dữ liệu
    @Override
    public int add(User user) {
        String query = "INSERT INTO user (userName, address, identityCard, phoneNumber, email, country, state) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, user.getUserName());
            stmt.setString(2, user.getAddress());
            stmt.setString(3, user.getIdentityCard());
            stmt.setString(4, user.getPhoneNumber());
            stmt.setString(5, user.getEmail());
            stmt.setString(6, user.getCountry());
            stmt.setString(7, user.getState());

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Xóa một người dùng khỏi cơ sở dữ liệu
    @Override
    public int delete(User user) {
        String query = "DELETE FROM user WHERE userId = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setInt(1, user.getIntUserId());
            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Cập nhật thông tin của một người dùng trong cơ sở dữ liệu
    @Override
    public int update(User user) {
        String query = "UPDATE user SET userName = ?, address = ?, identityCard = ?, phoneNumber = ?, email = ?, country = ?, state = ? WHERE userId = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, user.getUserName());
            stmt.setString(2, user.getAddress());
            stmt.setString(3, user.getIdentityCard());
            stmt.setString(4, user.getPhoneNumber());
            stmt.setString(5, user.getEmail());
            stmt.setString(6, user.getCountry());
            stmt.setString(7, user.getState());
            stmt.setInt(8, user.getIntUserId());

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Lấy danh sách tất cả người dùng
    public List<User> getAllUser() {
        String query = "SELECT * FROM user";
        List<User> list = new ArrayList<>();
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                User user = new User();
                user.setUserId(String.format("USER%s", rs.getInt("userId")));
                user.setUserName(rs.getString("userName"));
                user.setAddress(rs.getString("address"));
                user.setIdentityCard(rs.getString("identityCard"));
                user.setPhoneNumber(rs.getString("phoneNumber"));
                user.setEmail(rs.getString("email"));
                user.setCountry(rs.getString("country"));
                user.setState(rs.getString("state"));
                list.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Lấy thông tin người dùng theo STT
    public User layTheoId(int STT) {
        String query = "SELECT * FROM user WHERE STT = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setInt(1, STT);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setUserId(rs.getString("userId"));
                    user.setUserName(rs.getString("userName"));
                    user.setAddress(rs.getString("address"));
                    user.setIdentityCard(rs.getString("identityCard"));
                    user.setPhoneNumber(rs.getString("phoneNumber"));
                    user.setEmail(rs.getString("email"));
                    user.setCountry(rs.getString("country"));
                    user.setState(rs.getString("state"));
                    return user;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Đếm tổng số người dùng
    public int getTotalUsersCount() {
        String query = "SELECT COUNT(*) FROM user";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
