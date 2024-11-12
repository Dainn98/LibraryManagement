package library.management.data.DAO;

import library.management.data.database.DatabaseConnection;
import library.management.data.entity.Category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO implements DAOInterface<Category> {

    private CategoryDAO() {}

    public static CategoryDAO getInstance() {
        return new CategoryDAO();
    }

    // Thêm một thể loại vào cơ sở dữ liệu
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

    // Xóa một thể loại khỏi cơ sở dữ liệu
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

    // Cập nhật thông tin của một thể loại trong cơ sở dữ liệu
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
}
