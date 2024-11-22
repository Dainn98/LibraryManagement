package library.management.data.DAO;

import library.management.data.database.DatabaseConnection;
import library.management.data.entity.Manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ManagerDAO implements DAOInterface<Manager> {
    private static ManagerDAO instance;

    private ManagerDAO() {
    }

    public static ManagerDAO getInstance() {
        if (instance == null) {
            instance = new ManagerDAO();
        }
        return instance;
    }

    @Override
    public int add(Manager manager) {
        String query = "INSERT INTO manager (managerName, password, identityCard, email) VALUES (?, ?, ?, ?)";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, manager.getManagerName());
            stmt.setString(2, manager.getPassword());
            stmt.setString(3, manager.getIdentityCard());
            stmt.setString(4, manager.getEmail());

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int delete(Manager manager) {
        String query = "DELETE FROM manager WHERE managerID = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setInt(1, manager.getIntManagerID());

            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int update(Manager manager) {
        String query = "UPDATE manager SET managerName = ?, password = ?, identityCard = ?, email = ? WHERE managerID = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, manager.getManagerName());
            stmt.setString(2, manager.getPassword());
            stmt.setString(3, manager.getIdentityCard());
            stmt.setString(4, manager.getEmail());
            stmt.setInt(5, manager.getIntManagerID());

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public Manager checkManager(String managerName, String password) {
        String query = "SELECT * FROM manager WHERE managerName = ? AND password = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, managerName);
            stmt.setString(2, password);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return buildManagerFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    private Manager buildManagerFromResultSet(ResultSet rs) throws SQLException {
        Manager manager = new Manager();
        manager.setManagerID(rs.getInt("managerID"));
        manager.setManagerName(rs.getString("managerName"));
        manager.setPassword(rs.getString("password"));
        manager.setIdentityCard(rs.getString("identityCard"));
        manager.setEmail(rs.getString("email"));
        return manager;
    }



    public boolean checkManagerByUserName(String managerName) {
        String query = "SELECT * FROM manager WHERE managerName = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, managerName);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean checkManagerByIdentityCard(String identityCard) {
        String query = "SELECT * FROM manager WHERE identityCard = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, identityCard);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean checkManagerByEmail(String email) {
        String query = "SELECT * FROM manager WHERE email = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, email);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
