package library.management.data.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import library.management.data.database.DatabaseConnection;
import library.management.data.entity.Manager;

/**
 * Singleton class responsible for performing CRUD operations on the "manager" table in the
 * database. This class provides methods for adding, deleting, updating, and retrieving managers.
 */
public class ManagerDAO implements DAOInterface<Manager> {

  private static ManagerDAO instance;

  private ManagerDAO() {
  }

  /**
   * Retrieves the singleton instance of the ManagerDAO class.
   *
   * @return the singleton instance of ManagerDAO.
   */
  public static ManagerDAO getInstance() {
    if (instance == null) {
      instance = new ManagerDAO();
    }
    return instance;
  }

  @Override
  public int add(Manager manager) {
    String query = "INSERT INTO manager (managerName, password, identityCard, email, phoneNumber) VALUES (?, ?, ?, ?, ?)";
    try (Connection con = DatabaseConnection.getConnection();
        PreparedStatement stmt = con.prepareStatement(query)) {
      stmt.setString(1, manager.getManagerName());
      stmt.setString(2, manager.getPassword());
      stmt.setString(3, manager.getIdentityCard());
      stmt.setString(4, manager.getEmail());
      stmt.setString(5, manager.getPhoneNumber());
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
    String query = "UPDATE manager SET managerName = ?, password = ?, identityCard = ?, email = ?, phoneNumber = ? WHERE managerID = ?";
    try (Connection con = DatabaseConnection.getConnection();
        PreparedStatement stmt = con.prepareStatement(query)) {

      stmt.setString(1, manager.getManagerName());
      stmt.setString(2, manager.getPassword());
      stmt.setString(3, manager.getIdentityCard());
      stmt.setString(4, manager.getEmail());
      stmt.setString(5, manager.getPhoneNumber());
      stmt.setInt(6, manager.getIntManagerID());

      return stmt.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return 0;
  }

  /**
   * Checks if a manager exists in the database with the given manager name and password.
   *
   * @param managerName the manager name to check.
   * @param password    the password to check.
   * @return the {@link Manager} object if found, null otherwise.
   */
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

  /**
   * Builds a {@link Manager} object from a {@link ResultSet}.
   *
   * @param rs the {@link ResultSet} to build the manager from.
   * @return the {@link Manager} object.
   * @throws SQLException if a database access error occurs.
   */
  private Manager buildManagerFromResultSet(ResultSet rs) throws SQLException {
    Manager manager = new Manager();
    manager.setManagerID(rs.getInt("managerID"));
    manager.setManagerName(rs.getString("managerName"));
    manager.setPassword(rs.getString("password"));
    manager.setIdentityCard(rs.getString("identityCard"));
    manager.setEmail(rs.getString("email"));
    manager.setPhoneNumber(rs.getString("phoneNumber"));
    return manager;
  }

  /**
   * Checks if a manager exists in the database with the given manager name.
   *
   * @param managerName the manager name to check.
   * @return true if the manager exists, false otherwise.
   */
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

  /**
   * Checks if a manager exists in the database with the given identity card.
   *
   * @param identityCard the identity card to check.
   * @return true if the manager exists, false otherwise.
   */
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

  /**
   * Checks if a manager exists in the database with the given phone number.
   *
   * @param phoneNumber the phone number to check.
   * @return true if the manager exists, false otherwise.
   */
  public boolean checkManagerByPhoneNumber(String phoneNumber) {
    String query = "SELECT * FROM manager WHERE phoneNumber = ?";
    try (Connection con = DatabaseConnection.getConnection();
        PreparedStatement stmt = con.prepareStatement(query)) {
      stmt.setString(1, phoneNumber);

      try (ResultSet rs = stmt.executeQuery()) {
        return rs.next();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return false;
  }

  /**
   * Checks if a manager exists in the database with the given email.
   *
   * @param email the email to check.
   * @return true if the manager exists, false otherwise.
   */
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
