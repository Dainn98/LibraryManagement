package library.management.data.DAO;

import library.management.data.database.DatabaseConnection;
import library.management.data.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserDAO implements DAOInterface<User> {
    private static UserDAO instance;

    private UserDAO() {
    }

    public static UserDAO getInstance() {
        if (instance == null) {
            instance = new UserDAO();
        }
        return instance;
    }

    @Override
    public int add(User user) {
        String query = "INSERT INTO user (userName, identityCard, phoneNumber, email, password, country, state, status, registeredDate) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, user.getUserName());
            stmt.setString(2, user.getIdentityCard());
            stmt.setString(3, user.getPhoneNumber());
            stmt.setString(4, user.getEmail());
            stmt.setString(5, user.getPassword());
            stmt.setString(6, user.getCountry());
            stmt.setString(7, user.getState());
            stmt.setString(8, user.getStatus());
            stmt.setObject(9, user.getRegisteredDate());

            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int delete(User user) {
        String query = "UPDATE user SET status = 'removed' WHERE userName = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, user.getUserName());
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int update(User user) {
        String query = "UPDATE user SET identityCard = ?, phoneNumber = ?, email = ?, password = ?, country = ?, state = ?, registeredDate = ?, status = ? WHERE userName = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, user.getIdentityCard());
            stmt.setString(2, user.getPhoneNumber());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getPassword());
            stmt.setString(5, user.getCountry());
            stmt.setString(6, user.getState());
            stmt.setObject(7, user.getRegisteredDate());
            stmt.setString(8, user.getStatus());
            stmt.setString(9, user.getUserName());

            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<User> getAllApprovedUsers() {
        String query = "SELECT * FROM user WHERE status = 'approved'";
        List<User> users = new ArrayList<>();

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                users.add(buildUserFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    /**
     * Search approved users by a keyword in userName, email, or phoneNumber.
     */
    public List<User> searchApprovedUsersByKeyword(String keyword) {
        String query = "SELECT * FROM user WHERE status = 'approved' AND (userName LIKE ? OR email LIKE ? OR phoneNumber LIKE ?)";
        List<User> users = new ArrayList<>();

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            String searchPattern = "%" + keyword + "%";
            stmt.setString(1, searchPattern);
            stmt.setString(2, searchPattern);
            stmt.setString(3, searchPattern);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    users.add(buildUserFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    /**
     * Search approved users by name.
     */
    public List<User> searchApprovedUserByName(String name) {
        String query = "SELECT * FROM user WHERE status = 'approved' AND userName LIKE ?";
        List<User> users = new ArrayList<>();

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, "%" + name + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    users.add(buildUserFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    /**
     * Search approved users by phone number.
     */
    public List<User> searchApprovedUserByPhoneNumber(String phoneNumber) {
        String query = "SELECT * FROM user WHERE status = 'approved' AND phoneNumber LIKE ?";
        List<User> users = new ArrayList<>();

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, "%" + phoneNumber + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    users.add(buildUserFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    /**
     * Search approved users by email.
     */
    public List<User> searchApprovedUserByEmail(String email) {
        String query = "SELECT * FROM user WHERE status = 'approved' AND email LIKE ?";
        List<User> users = new ArrayList<>();

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, "%" + email + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    users.add(buildUserFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    /**
     * Get all pending users filtered by name, country, state, and registered year.
     */
    public List<User> searchPendingUserByFilter(String nameQuery, List<String> countries, List<String> states, List<String> years) {
        if ((countries != null && countries.isEmpty()) ||
                (states != null && states.isEmpty()) ||
                (years != null && years.isEmpty())) {
            return new ArrayList<>();
        }

        StringBuilder query = new StringBuilder("SELECT * FROM user WHERE status = 'pending'");
        if (nameQuery != null && !nameQuery.isEmpty()) {
            query.append(" AND userName LIKE ?");
        }
        if (countries != null && !countries.isEmpty()) {
            query.append(" AND country IN (");
            query.append(countries.stream().map(c -> "?").collect(Collectors.joining(", ")));
            query.append(")");
        }
        if (states != null && !states.isEmpty()) {
            query.append(" AND state IN (");
            query.append(states.stream().map(s -> "?").collect(Collectors.joining(", ")));
            query.append(")");
        }
        if (years != null && !years.isEmpty()) {
            query.append(" AND YEAR(registeredDate) IN (");
            query.append(years.stream().map(y -> "?").collect(Collectors.joining(", ")));
            query.append(")");
        }

        List<User> users = new ArrayList<>();
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query.toString())) {

            int paramIndex = 1;
            if (nameQuery != null && !nameQuery.isEmpty()) {
                stmt.setString(paramIndex++, "%" + nameQuery + "%");
            }
            if (countries != null) {
                for (String country : countries) {
                    stmt.setString(paramIndex++, country);
                }
            }
            if (states != null) {
                for (String state : states) {
                    stmt.setString(paramIndex++, state);
                }
            }
            if (years != null) {
                for (String year : years) {
                    stmt.setString(paramIndex++, year);
                }
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    users.add(buildUserFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    /**
     * Helper method to build a User object from ResultSet.
     */
    private User buildUserFromResultSet(ResultSet rs) throws SQLException {
        User user = new User();
        user.setUserName(rs.getString("userName"));
        user.setIdentityCard(rs.getString("identityCard"));
        user.setPhoneNumber(rs.getString("phoneNumber"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        user.setCountry(rs.getString("country"));
        user.setState(rs.getString("state"));
        user.setStatus(rs.getString("status"));
        user.setRegisteredDate(rs.getObject("registeredDate", LocalDateTime.class));
        return user;
    }


    public int approve(User user) {
        String query = "UPDATE user SET status = 'approved' WHERE userName = ? AND status = 'pending'";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, user.getUserName());
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int disapprove(User user) {
        String query = "UPDATE user SET status = 'disapproved' WHERE userName = ? AND status = 'pending'";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, user.getUserName());
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Trả về số lượng tất cả người dùng với mọi trạng thái.
     * @return int - Số lượng người dùng.
     */
    public int getAllUsersCount() {
        String query = "SELECT COUNT(*) AS userCount FROM user";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("userCount");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    /**
     * Trả về số lượng người dùng với trạng thái 'approved'.
     * @return int - Số lượng người dùng có trạng thái approved.
     */
    public int getApprovedUsersCount() {
        String query = "SELECT COUNT(*) AS userCount FROM user WHERE status = 'approved'";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("userCount");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public List<User> getPendingUsers() {
        String query = "SELECT * FROM user WHERE status = 'pending'";
        List<User> pendingUsers = new ArrayList<>();

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                pendingUsers.add(buildUserFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pendingUsers;
    }

    public List<String> getAllPendingCountries() {
        String query = "SELECT DISTINCT country FROM user WHERE status = 'pending'";
        List<String> countries = new ArrayList<>();

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                countries.add(rs.getString("country"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return countries;
    }

    /**
     * Trả về danh sách các bang (state) có người dùng đang ở trạng thái 'pending'.
     * @return List<String> - Danh sách các bang có trạng thái pending, không trùng lặp.
     */
    public List<String> getAllPendingStates() {
        String query = "SELECT DISTINCT state FROM user WHERE status = 'pending'";
        List<String> states = new ArrayList<>();

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                states.add(rs.getString("state"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return states;
    }

    /**
     * Trả về danh sách các năm đăng ký (registered year) của người dùng đang ở trạng thái 'pending'.
     * @return List<String> - Danh sách các năm đăng ký có trạng thái pending, không trùng lặp.
     */
    public List<String> getAllPendingYears() {
        String query = "SELECT DISTINCT YEAR(registeredDate) AS year FROM user WHERE status = 'pending'";
        List<String> years = new ArrayList<>();

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                years.add(rs.getString("year"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return years;
    }

    /**
     * Trả về danh sách tên người dùng (userName) đang ở trạng thái 'pending' chứa từ khóa tìm kiếm.
     * @param keyword - Từ khóa tìm kiếm (có thể là một phần của userName).
     * @return List<String> - Danh sách tên người dùng có trạng thái pending.
     */
    public List<String> getAllPendingUserName(String keyword) {
        String query = "SELECT userName FROM user WHERE status = 'pending' AND userName LIKE ?";
        List<String> userNames = new ArrayList<>();

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, "%" + keyword + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    userNames.add(rs.getString("userName"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userNames;
    }

    public List<String> searchApprovedUserNames(String query) {
        List<String> approvedUserNames = new ArrayList<>();
        String sqlQuery = "SELECT userName FROM user WHERE status = 'approved' AND " +
                "(userName LIKE ?)";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(sqlQuery)) {

            String searchKeyword = "%" + query + "%";
            stmt.setString(1, searchKeyword);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    approvedUserNames.add(rs.getString("userName"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return approvedUserNames;
    }

    public User searchApprovedUserByExactName(String name) {
        String query = "SELECT * FROM user WHERE status = 'approved' AND userName = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, name);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return buildUserFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean doesUserNameExist(String userName) {
        String query = "SELECT COUNT(*) FROM user WHERE userName = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, userName);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean doesIdentityCardExist(String identityCard) {
        String query = "SELECT COUNT(*) FROM user WHERE identityCard = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, identityCard);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean doesEmailExist(String email) {
        String query = "SELECT COUNT(*) FROM user WHERE email = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public User checkUserLogin(String username, String password) {
        String query = "SELECT * FROM user WHERE userName = ? AND password = ? AND status = 'approved'";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, username);
            stmt.setString(2, password);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return buildUserFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
