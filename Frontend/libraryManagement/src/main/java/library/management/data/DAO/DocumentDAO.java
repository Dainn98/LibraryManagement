package library.management.data.DAO;

import library.management.data.database.DatabaseConnection;
import library.management.data.entity.Document;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DocumentDAO implements DAOInterface<Document> {

    private DocumentDAO() {
    }

    public static DocumentDAO getInstance() {
        return new DocumentDAO();
    }

    @Override
    public int add(Document document) {
        String query = "INSERT INTO document (categoryID, publisher, lgID, title, author, isbn, quantity, availableCopies, addDate, price, description, url, image, availability) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setInt(1, document.getIntCategoryID());
            stmt.setString(2, document.getPublisher());
            stmt.setInt(3, document.getIntLgID());
            stmt.setString(4, document.getTitle());
            stmt.setString(5, document.getAuthor());
            stmt.setString(6, document.getIsbn());
            stmt.setInt(7, document.getQuantity());
            stmt.setInt(8, document.getAvailableCopies());
            stmt.setTimestamp(9, Timestamp.valueOf(document.getAddDate()));
            stmt.setBigDecimal(10, java.math.BigDecimal.valueOf(document.getPrice()));
            stmt.setString(11, document.getDescription());
            stmt.setString(12, document.getUrl());
            stmt.setString(13, document.getImage());
            stmt.setString(14, document.getAvailability());

            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }


    @Override
    public int delete(Document document) {
        String query = "UPDATE document SET availability = 'removed' WHERE documentId = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setInt(1, document.getIntDocumentID());
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }


    @Override
    public int update(Document document) {
        String query = "UPDATE document SET categoryID = ?, publisher = ?, lgID = ?, title = ?, author = ?, isbn = ?, quantity = ?, availableCopies = ?, addDate = ?, price = ?, description = ?, url = ?, image = ?, availability = ? WHERE documentId = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setInt(1, document.getIntCategoryID());
            stmt.setString(2, document.getPublisher());
            stmt.setInt(3, document.getIntLgID());
            stmt.setString(4, document.getTitle());
            stmt.setString(5, document.getAuthor());
            stmt.setString(6, document.getIsbn());
            stmt.setInt(7, document.getQuantity());
            stmt.setInt(8, document.getAvailableCopies());
            stmt.setTimestamp(9, Timestamp.valueOf(document.getAddDate()));
            stmt.setBigDecimal(10, java.math.BigDecimal.valueOf(document.getPrice()));
            stmt.setString(11, document.getDescription());
            stmt.setString(12, document.getUrl());
            stmt.setString(13, document.getImage());
            stmt.setString(14, document.getAvailability());
            stmt.setInt(15, document.getIntDocumentID());

            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }


    public List<Document> getBookList() {
        List<Document> bookList = new ArrayList<>();
        String query = "SELECT * FROM document WHERE availability != 'removed'";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Document document = new Document();
                document.setDocumentID(String.format("DOC%d", rs.getInt("documentId")));
                document.setCategoryID(String.format("CAT%d", rs.getInt("categoryID")));
                document.setPublisher(rs.getString("publisher"));
                document.setLgID(String.format("LANG%d", rs.getInt("lgID")));
                document.setTitle(rs.getString("title"));
                document.setAuthor(rs.getString("author"));
                document.setIsbn(rs.getString("isbn"));
                document.setQuantity(rs.getInt("quantity"));
                document.setAvailableCopies(rs.getInt("availableCopies"));
                document.setAddDate(rs.getTimestamp("addDate").toLocalDateTime().toString());
                document.setPrice(rs.getBigDecimal("price").doubleValue());
                document.setDescription(rs.getString("description"));
                document.setUrl(rs.getString("url"));
                document.setImage(rs.getString("image"));
                document.setAvailability(rs.getString("availability"));

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
        String query = "SELECT SUM(availableCopies) FROM document WHERE availability = 'available'";
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

    public List<Document> searchDocuments(String keyword) {
        List<Document> searchResults = new ArrayList<>();
        String query = "SELECT * FROM document WHERE " +
                "CAST(documentId AS CHAR) LIKE ? OR " +
                "isbn LIKE ? OR " +
                "title LIKE ? OR " +
                "author LIKE ? OR " +
                "publisher LIKE ? OR " +
                "CAST(categoryID AS CHAR) LIKE ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            String searchKeyword = "%" + keyword + "%";
            stmt.setString(1, searchKeyword);
            stmt.setString(2, searchKeyword);
            stmt.setString(3, searchKeyword);
            stmt.setString(4, searchKeyword);
            stmt.setString(5, searchKeyword);
            stmt.setString(6, searchKeyword);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Document document = new Document();
                    document.setDocumentID(String.format("DOC%d", rs.getInt("documentId")));
                    document.setCategoryID(String.format("CAT%d", rs.getInt("categoryID")));
                    document.setPublisher(rs.getString("publisher"));
                    document.setLgID(String.format("LANG%d", rs.getInt("lgID")));
                    document.setTitle(rs.getString("title"));
                    document.setAuthor(rs.getString("author"));
                    document.setIsbn(rs.getString("isbn"));
                    document.setQuantity(rs.getInt("quantity"));
                    document.setAvailableCopies(rs.getInt("availableCopies"));
                    document.setAddDate(rs.getTimestamp("addDate").toLocalDateTime().toString());
                    document.setPrice(rs.getBigDecimal("price").doubleValue());
                    document.setDescription(rs.getString("description"));
                    document.setUrl(rs.getString("url"));
                    document.setImage(rs.getString("image"));
                    document.setAvailability(rs.getString("availability"));

                    searchResults.add(document);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return searchResults;
    }

}
