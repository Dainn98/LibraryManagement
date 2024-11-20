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
        String query = "INSERT INTO user (userName, identityCard, phoneNumber, email, country, state, registeredDate) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, user.getUserName());
            stmt.setString(2, user.getIdentityCard());
            stmt.setString(3, user.getPhoneNumber());
            stmt.setString(4, user.getEmail());
            stmt.setString(5, user.getCountry());
            stmt.setString(6, user.getState());
            stmt.setObject(7, user.getRegisteredDate());

            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int delete(User user) {
        String query = "UPDATE user SET status = 'removed' WHERE userId = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setInt(1, user.getIntUserId());
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }


    @Override
    public int update(User user) {
        String query = "UPDATE user SET userName = ?, identityCard = ?, phoneNumber = ?, email = ?, country = ?, state = ?, registeredDate = ?, status = ? WHERE userId = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, user.getUserName());
            stmt.setString(2, user.getIdentityCard());
            stmt.setString(3, user.getPhoneNumber());
            stmt.setString(4, user.getEmail());
            stmt.setString(5, user.getCountry());
            stmt.setString(6, user.getState());
            stmt.setObject(7, user.getRegisteredDate());
            stmt.setString(8, user.getStatus());
            stmt.setInt(9, user.getIntUserId());

            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getAllUserCount() {
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


    public List<User> getAllApprovedUser() {
        String query = "SELECT * FROM user WHERE status = 'approved'";
        List<User> list = new ArrayList<>();
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                User user = new User();
                user.setUserId(String.format("USER%s", rs.getInt("userId")));
                user.setUserName(rs.getString("userName"));
                user.setIdentityCard(rs.getString("identityCard"));
                user.setPhoneNumber(rs.getString("phoneNumber"));
                user.setEmail(rs.getString("email"));
                user.setCountry(rs.getString("country"));
                user.setState(rs.getString("state"));
                user.setStatus(rs.getString("status"));
                user.setRegisteredDate(rs.getObject("registeredDate", LocalDateTime.class));
                list.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<User> getAllPendingUser() {
        String query = "SELECT * FROM user WHERE status = 'pending'";
        List<User> list = new ArrayList<>();
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                User user = new User();
                user.setUserId(String.format("USER%s", rs.getInt("userId")));
                user.setUserName(rs.getString("userName"));
                user.setIdentityCard(rs.getString("identityCard"));
                user.setPhoneNumber(rs.getString("phoneNumber"));
                user.setEmail(rs.getString("email"));
                user.setCountry(rs.getString("country"));
                user.setState(rs.getString("state"));
                user.setStatus(rs.getString("status"));
                user.setRegisteredDate(rs.getObject("registeredDate", LocalDateTime.class));
                list.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public int getTotalApprovedUsersCount() {
        String query = "SELECT COUNT(*) FROM user WHERE status = 'approved'";
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


    // return all users that have keyword in name, email, phone number, id.
    public List<User> searchAllApprovedUserByKeyword(String keyword) {
        String query = "SELECT * FROM user WHERE status = 'approved' AND (userName LIKE ? OR email LIKE ? OR phoneNumber LIKE ? OR userId LIKE ?)";
        List<User> list = new ArrayList<>();

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            String searchPattern = "%" + keyword + "%";

            stmt.setString(1, searchPattern);
            stmt.setString(2, searchPattern);
            stmt.setString(3, searchPattern);
            stmt.setString(4, searchPattern);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    User user = new User();
                    user.setUserId(String.format("USER%s", rs.getInt("userId")));
                    user.setUserName(rs.getString("userName"));
                    user.setIdentityCard(rs.getString("identityCard"));
                    user.setPhoneNumber(rs.getString("phoneNumber"));
                    user.setEmail(rs.getString("email"));
                    user.setCountry(rs.getString("country"));
                    user.setState(rs.getString("state"));
                    user.setStatus(rs.getString("status"));
                    user.setRegisteredDate(rs.getObject("registeredDate", LocalDateTime.class));
                    list.add(user);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<User> searchApprovedUserByName(String name) {
        String query = "SELECT * FROM user WHERE status = 'approved' AND userName LIKE ?";
        List<User> list = new ArrayList<>();

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, "%" + name + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    User user = new User();
                    user.setUserId(String.format("USER%s", rs.getInt("userId")));
                    user.setUserName(rs.getString("userName"));
                    user.setIdentityCard(rs.getString("identityCard"));
                    user.setPhoneNumber(rs.getString("phoneNumber"));
                    user.setEmail(rs.getString("email"));
                    user.setCountry(rs.getString("country"));
                    user.setState(rs.getString("state"));
                    user.setStatus(rs.getString("status"));
                    user.setRegisteredDate(rs.getObject("registeredDate", LocalDateTime.class));
                    list.add(user);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }


    public List<User> searchApprovedUserById(String userId) {
        String query = "SELECT * FROM user WHERE status = 'approved' AND userId LIKE ?";
        List<User> list = new ArrayList<>();

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, "%" + userId + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    User user = new User();
                    user.setUserId(String.format("USER%s", rs.getInt("userId")));
                    user.setUserName(rs.getString("userName"));
                    user.setIdentityCard(rs.getString("identityCard"));
                    user.setPhoneNumber(rs.getString("phoneNumber"));
                    user.setEmail(rs.getString("email"));
                    user.setCountry(rs.getString("country"));
                    user.setState(rs.getString("state"));
                    user.setStatus(rs.getString("status"));
                    user.setRegisteredDate(rs.getObject("registeredDate", LocalDateTime.class));
                    list.add(user);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public User searchUserByID(int userId) {
        String query = "SELECT * FROM user WHERE userId = ? AND status != 'removed'";
        User user = null;

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setInt(1, userId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    user = new User();
                    user.setUserId(String.format("USER%s", rs.getInt("userId")));
                    user.setUserName(rs.getString("userName"));
                    user.setIdentityCard(rs.getString("identityCard"));
                    user.setPhoneNumber(rs.getString("phoneNumber"));
                    user.setEmail(rs.getString("email"));
                    user.setCountry(rs.getString("country"));
                    user.setState(rs.getString("state"));
                    user.setStatus(rs.getString("status"));
                    user.setRegisteredDate(rs.getObject("registeredDate", LocalDateTime.class));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }


    public List<User> searchApprovedUserByPhoneNumber(String phoneNumber) {
        String query = "SELECT * FROM user WHERE status = 'approved' AND phoneNumber LIKE ?";
        List<User> list = new ArrayList<>();

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, "%" + phoneNumber + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    User user = new User();
                    user.setUserId(String.format("USER%s", rs.getInt("userId")));
                    user.setUserName(rs.getString("userName"));
                    user.setIdentityCard(rs.getString("identityCard"));
                    user.setPhoneNumber(rs.getString("phoneNumber"));
                    user.setEmail(rs.getString("email"));
                    user.setCountry(rs.getString("country"));
                    user.setState(rs.getString("state"));
                    user.setStatus(rs.getString("status"));
                    user.setRegisteredDate(rs.getObject("registeredDate", LocalDateTime.class));
                    list.add(user);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }


    public List<User> searchApprovedUserByEmail(String email) {
        String query = "SELECT * FROM user WHERE status = 'approved' AND email LIKE ?";
        List<User> list = new ArrayList<>();

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, "%" + email + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    User user = new User();
                    user.setUserId(String.format("USER%s", rs.getInt("userId")));
                    user.setUserName(rs.getString("userName"));
                    user.setIdentityCard(rs.getString("identityCard"));
                    user.setPhoneNumber(rs.getString("phoneNumber"));
                    user.setEmail(rs.getString("email"));
                    user.setCountry(rs.getString("country"));
                    user.setState(rs.getString("state"));
                    user.setStatus(rs.getString("status"));
                    user.setRegisteredDate(rs.getObject("registeredDate", LocalDateTime.class));
                    list.add(user);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public int disapprove(User user) {
        String query = "UPDATE user SET status = 'disapprove' WHERE userId = ? AND status = 'pending'";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setInt(1, user.getIntUserId());

            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int approve(User user) {
        String query = "UPDATE user SET status = 'approved' WHERE userId = ? AND status = 'pending'";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setInt(1, user.getIntUserId());

            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
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

    public List<String> getAllPendingUserName(String keyword) {
        String query = "SELECT userName FROM user WHERE status = 'pending' AND userName LIKE ?";
        List<String> usernames = new ArrayList<>();
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, "%" + keyword + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    usernames.add(rs.getString("userName"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usernames;
    }

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
            if (countries != null && !countries.isEmpty()) {
                for (String country : countries) {
                    stmt.setString(paramIndex++, country);
                }
            }
            if (states != null && !states.isEmpty()) {
                for (String state : states) {
                    stmt.setString(paramIndex++, state);
                }
            }
            if (years != null && !years.isEmpty()) {
                for (String year : years) {
                    stmt.setString(paramIndex++, year);
                }
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    User user = new User();
                    user.setUserId(String.format("USER%s", rs.getInt("userId")));
                    user.setUserName(rs.getString("userName"));
                    user.setIdentityCard(rs.getString("identityCard"));
                    user.setPhoneNumber(rs.getString("phoneNumber"));
                    user.setEmail(rs.getString("email"));
                    user.setCountry(rs.getString("country"));
                    user.setState(rs.getString("state"));
                    user.setStatus(rs.getString("status"));
                    user.setRegisteredDate(rs.getObject("registeredDate", LocalDateTime.class));
                    users.add(user);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }


}
