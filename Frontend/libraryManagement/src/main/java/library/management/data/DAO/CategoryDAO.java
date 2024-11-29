package library.management.data.DAO;

import library.management.data.database.DatabaseConnection;
import library.management.data.entity.Category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) class responsible for performing CRUD operations on the "category" table in the database.
 * This class provides methods for adding, deleting, updating, and retrieving categories.
 * It also provides utility methods to retrieve category tags and their corresponding IDs.
 */
public class CategoryDAO implements DAOInterface<Category> {

    private static CategoryDAO instance;

    /**
     * Private constructor to prevent instantiation from outside the class.
     */
    private CategoryDAO() {
    }

    /**
     * Retrieves the singleton instance of the CategoryDAO class.
     *
     * @return the singleton instance of CategoryDAO.
     */
    public static synchronized CategoryDAO getInstance() {
        if (instance == null) {
            instance = new CategoryDAO();
        }
        return instance;
    }

    /**
     * Adds a new category to the database.
     *
     * @param category the {@link Category} object to be added.
     * @return the number of rows inserted (1 if successful, 0 if failed).
     */
    @Override
    public int add(Category category) {
        String query = "INSERT INTO category (tag) VALUES (?)";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, category.getTag());
            int rowsInserted = stmt.executeUpdate();
            return rowsInserted;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Deletes a category from the database.
     *
     * @param category the {@link Category} object to be deleted.
     * @return the number of rows deleted (1 if successful, 0 if failed).
     */
    @Override
    public int delete(Category category) {
        String query = "DELETE FROM category WHERE categoryID = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setInt(1, category.getIntCategoryID());
            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Updates an existing category in the database.
     *
     * @param category the {@link Category} object containing the updated information.
     * @return the number of rows updated (1 if successful, 0 if failed).
     */
    @Override
    public int update(Category category) {
        String query = "UPDATE category SET tag = ? WHERE categoryID = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, category.getTag());
            stmt.setInt(2, category.getIntCategoryID());
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Retrieves a list of all category tags from the database.
     *
     * @return a list of category tags.
     */
    public List<String> getAllTags() {
        List<String> tags = new ArrayList<>();
        String query = "SELECT tag FROM category";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                tags.add(rs.getString("tag"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tags;
    }

    /**
     * Retrieves a list of all categories from the database.
     *
     * @return a list of {@link Category} objects.
     */
    public List<Category> getCategoryList() {
        List<Category> categories = new ArrayList<>();
        String query = "SELECT categoryID, tag FROM category";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String categoryID = rs.getString("categoryID");
                String tag = rs.getString("tag");
                Category category = new Category(categoryID, tag);
                categories.add(category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categories;
    }

    /**
     * Retrieves the category tag for a given category ID.
     *
     * @param categoryID the ID of the category.
     * @return the tag associated with the given category ID, or null if not found.
     */
    public String getTagByID(int categoryID) {
        String tag = null;
        String query = "SELECT tag FROM category WHERE categoryID = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setInt(1, categoryID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    tag = rs.getString("tag");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tag;
    }

    /**
     * Retrieves the category ID associated with a given category tag.
     *
     * @param tag the tag of the category.
     * @return the category ID associated with the given tag, or -1 if not found.
     */
    public int getTagId(String tag) {
        String query = "SELECT categoryID FROM category WHERE tag = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, tag);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("categoryID");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
