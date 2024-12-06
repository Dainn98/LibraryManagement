package library.management.data.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import library.management.data.database.DatabaseConnection;
import library.management.data.entity.Language;

public class LanguageDAO implements DAOInterface<Language> {

  private static LanguageDAO instance;

  private LanguageDAO() {
  }

  public static synchronized LanguageDAO getInstance() {
    if (instance == null) {
      instance = new LanguageDAO();
    }
    return instance;
  }

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

  @Override
  public int delete(Language language) {
    String query = "DELETE FROM language WHERE lgID = ?";
    try (Connection con = DatabaseConnection.getConnection();
        PreparedStatement stmt = con.prepareStatement(query)) {

      stmt.setInt(1, language.getIntLgID());
      int rowsDeleted = stmt.executeUpdate();
      return rowsDeleted;
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return 0;
  }

  @Override
  public int update(Language language) {
    String query = "UPDATE language SET lgName = ? WHERE lgID = ?";
    try (Connection con = DatabaseConnection.getConnection();
        PreparedStatement stmt = con.prepareStatement(query)) {

      stmt.setString(1, language.getLgName());
      stmt.setInt(2, language.getIntLgID());
      int rowsUpdated = stmt.executeUpdate();
      return rowsUpdated;
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return 0;
  }

  public List<String> getAllLanguages() {
    List<String> languages = new ArrayList<>();
    String query = "SELECT lgName FROM language";

    try (Connection con = DatabaseConnection.getConnection();
        PreparedStatement stmt = con.prepareStatement(query);
        ResultSet rs = stmt.executeQuery()) {

      while (rs.next()) {
        languages.add(rs.getString("lgName"));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return languages;
  }

  public List<Language> getLanguageList() {
    List<Language> languageList = new ArrayList<>();
    String query = "SELECT lgID, lgName FROM language";

    try (Connection con = DatabaseConnection.getConnection();
        PreparedStatement stmt = con.prepareStatement(query);
        ResultSet rs = stmt.executeQuery()) {

      while (rs.next()) {
        String lgID = rs.getString("lgID");
        String lgName = rs.getString("lgName");
        Language language = new Language(String.format("LANG%s", lgID), lgName);
        languageList.add(language);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return languageList;
  }

  public int getLanguageId(String lgName) {
    String query = "SELECT lgID FROM language WHERE lgName = ?";
    try (Connection con = DatabaseConnection.getConnection();
        PreparedStatement stmt = con.prepareStatement(query)) {

      stmt.setString(1, lgName);
      try (ResultSet rs = stmt.executeQuery()) {
        if (rs.next()) {
          return rs.getInt("lgID");
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return -1;
  }

  public String getLanguageName(int lgID) {
    String query = "SELECT lgName FROM language WHERE lgID = ?";
    try (Connection con = DatabaseConnection.getConnection();
        PreparedStatement stmt = con.prepareStatement(query)) {

      stmt.setInt(1, lgID);

      try (ResultSet rs = stmt.executeQuery()) {
        if (rs.next()) {
          return rs.getString("lgName");
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }
}
