package library.management.ui.DAO;

import library.management.ui.database.DatabaseConnection;
import library.management.ui.entity.Document;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DocumentDAO implements DAOInterface<Document> {

    private DocumentDAO(){}

    public static DocumentDAO getInstance(){
        return new DocumentDAO();
    }

    // Thêm một tài liệu vào cơ sở dữ liệu
    @Override
    public int add(Document document) {
        String query = "INSERT INTO document (genrId, publisher, lgID, title, author, quantity, availableCopies, addDate, price) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, document.getGenrId());
            stmt.setString(2, document.getPublisher());
            stmt.setString(3, document.getLgId());
            stmt.setString(4, document.getTitle());
            stmt.setString(5, document.getAuthor());
            stmt.setInt(6, document.getQuantity());
            stmt.setInt(7, document.getAvailableCopies());
            stmt.setDate(8, Date.valueOf(document.getAddDate()));
            stmt.setBigDecimal(9, java.math.BigDecimal.valueOf(document.getPrice()));

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Xóa một tài liệu khỏi cơ sở dữ liệu
    @Override
    public int delete(Document document) {
        String query = "DELETE FROM document WHERE documentId = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, document.getDocumentID());
            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Cập nhật thông tin của một tài liệu trong cơ sở dữ liệu
    @Override
    public int update(Document document) {
        String query = "UPDATE document SET genrId = ?, publisher = ?, lgID = ?, title = ?, author = ?, quantity = ?, availableCopies = ?, addDate = ?, price = ? WHERE documentId = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, document.getGenrId());
            stmt.setString(2, document.getPublisher());
            stmt.setString(3, document.getLgId());
            stmt.setString(4, document.getTitle());
            stmt.setString(5, document.getAuthor());
            stmt.setInt(6, document.getQuantity());
            stmt.setInt(7, document.getAvailableCopies());
            stmt.setDate(8, Date.valueOf(document.getAddDate()));
            stmt.setBigDecimal(9, java.math.BigDecimal.valueOf(document.getPrice()));
            stmt.setString(10, document.getDocumentID());

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
