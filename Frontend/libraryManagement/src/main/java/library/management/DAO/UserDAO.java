package library.management.DAO;

import library.management.database.KetNoiCSDL;
import library.management.entity.User;

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

    @Override
    public int them(User user) {
        Connection con = KetNoiCSDL.getConnection();
        String query = "INSERT INTO user (userName, address, identityCard, mobile, email, membershipLevel) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, user.getUserName());
            stmt.setString(2, user.getAddress());
            stmt.setString(3, user.getIdentityCard());
            stmt.setString(4, user.getMobile());
            stmt.setString(5, user.getEmail());
            stmt.setString(6, user.getMembershipLevel());

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int xoa(User user) {
        Connection con = KetNoiCSDL.getConnection();
        String query = "DELETE FROM user WHERE userId = ?";
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, user.getUserId());

            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int capNhat(User user) {
        Connection con = KetNoiCSDL.getConnection();
        String query = "UPDATE user SET userName = ?, address = ?, identityCard = ?, mobile = ?, email = ?, membershipLevel = ? WHERE userId = ?";
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, user.getUserName());
            stmt.setString(2, user.getAddress());
            stmt.setString(3, user.getIdentityCard());
            stmt.setString(4, user.getMobile());
            stmt.setString(5, user.getEmail());
            stmt.setString(6, user.getMembershipLevel());
            stmt.setString(7, user.getUserId());

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<User> layTatCa() {
        Connection con = KetNoiCSDL.getConnection();
        String query = "SELECT * FROM user";
        List<User> list = new ArrayList<>();
        try (PreparedStatement stmt = con.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                User user = new User();
                user.setSTT(rs.getInt("STT"));
                user.setUserId(rs.getString("userId"));
                user.setUserName(rs.getString("userName"));
                user.setAddress(rs.getString("address"));
                user.setIdentityCard(rs.getString("identityCard"));
                user.setMobile(rs.getString("mobile"));
                user.setEmail(rs.getString("email"));
                user.setMembershipLevel(rs.getString("membershipLevel"));

                list.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public User layTheoId(int STT) {
        Connection con = KetNoiCSDL.getConnection();
        String query = "SELECT * FROM user WHERE STT = ?";
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setInt(1, STT);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setSTT(rs.getInt("STT"));
                user.setUserId(rs.getString("userId"));
                user.setUserName(rs.getString("userName"));
                user.setAddress(rs.getString("address"));
                user.setIdentityCard(rs.getString("identityCard"));
                user.setMobile(rs.getString("mobile"));
                user.setEmail(rs.getString("email"));
                user.setMembershipLevel(rs.getString("membershipLevel"));

                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
