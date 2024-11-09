package library.management.data.DAO;

import library.management.data.database.DatabaseConnection;
import library.management.data.entity.Document;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DocumentDAO implements DAOInterface<Document> {

    private DocumentDAO() {}

    public static DocumentDAO getInstance() {
        return new DocumentDAO();
    }

    @Override
    public int add(Document document) {
        String query = "INSERT INTO document (genrId, publisher, lgID, title, author, isbn, quantity, availableCopies, addDate, price, description) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, document.getGenrId());
            stmt.setString(2, document.getPublisher());
            stmt.setString(3, document.getLgId());
            stmt.setString(4, document.getTitle());
            stmt.setString(5, document.getAuthor());
            stmt.setString(6, document.getIsbn());
            stmt.setInt(7, document.getQuantity());
            stmt.setInt(8, document.getAvailableCopies());
            stmt.setDate(9, Date.valueOf(document.getAddDate()));
            stmt.setBigDecimal(10, java.math.BigDecimal.valueOf(document.getPrice()));
            stmt.setString(11, document.getDescription());

            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int delete(Document document) {
        String query = "DELETE FROM document WHERE documentId = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, document.getDocumentID());
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int update(Document document) {
        String query = "UPDATE document SET genrId = ?, publisher = ?, lgID = ?, title = ?, author = ?, isbn = ?, quantity = ?, availableCopies = ?, addDate = ?, price = ?, description = ? WHERE documentId = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, document.getGenrId());
            stmt.setString(2, document.getPublisher());
            stmt.setString(3, document.getLgId());
            stmt.setString(4, document.getTitle());
            stmt.setString(5, document.getAuthor());
            stmt.setString(6, document.getIsbn());
            stmt.setInt(7, document.getQuantity());
            stmt.setInt(8, document.getAvailableCopies());
            stmt.setDate(9, Date.valueOf(document.getAddDate()));
            stmt.setBigDecimal(10, java.math.BigDecimal.valueOf(document.getPrice()));
            stmt.setString(11, document.getDescription());
            stmt.setString(12, document.getDocumentID());

            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Document> getBookList() {
        List<Document> bookList = new ArrayList<>();
        String query = "SELECT * FROM document";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Document document = new Document();
                document.setDocumentID(rs.getString("documentId"));
                document.setGenrId(rs.getString("genrId"));
                document.setPublisher(rs.getString("publisher"));
                document.setLgId(rs.getString("lgID"));
                document.setTitle(rs.getString("title"));
                document.setAuthor(rs.getString("author"));
                document.setIsbn(rs.getString("isbn"));
                document.setQuantity(rs.getInt("quantity"));
                document.setAvailableCopies(rs.getInt("availableCopies"));
                document.setAddDate(rs.getDate("addDate").toString());
                document.setPrice(rs.getBigDecimal("price").doubleValue());
                document.setDescription(rs.getString("description"));

                bookList.add(document);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bookList;
    }

    public int getTotalQuantity() {
        String query = "SELECT SUM(quantity) FROM document";
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

    public int getTotalAvailableCopies() {
        String query = "SELECT SUM(availableCopies) FROM document";
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
