package library.management.data.DAO;

import library.management.data.database.DatabaseConnection;
import library.management.data.entity.Document;

import javax.print.Doc;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DocumentDAO implements DAOInterface<Document> {

    private static DocumentDAO instance;

    private DocumentDAO() {
    }

    public static synchronized DocumentDAO getInstance() {
        if (instance == null) {
            instance = new DocumentDAO();
        }
        return instance;
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

    public Document searchDocumentById(int documentId) {
        String query = "SELECT * FROM document WHERE documentId = ? AND availability != 'removed'";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setInt(1, documentId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
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

                    return document;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int canBeBorrowed(int documentId, int borrowQuantity) {
        String query = "SELECT availableCopies, availability FROM document WHERE documentId = ? AND availability = 'available'";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setInt(1, documentId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int availableCopies = rs.getInt("availableCopies");
                    String availability = rs.getString("availability");
                    if (!availability.equals("available")) {
                        return Document.NOTAVALABLETOBOROW;
                    } else if (availableCopies < borrowQuantity) {
                        return Document.NOTENOUGHCOPIES;
                    } else {
                        return 1;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Document.NOTAVALABLETOBOROW;
    }

    public boolean decreaseAvailableCopies(int documentId, int decrementQuantity) {
        String queryCheck = "SELECT availableCopies FROM document WHERE documentId = ?";
        String queryUpdate = "UPDATE document SET availableCopies = availableCopies - ? WHERE documentId = ? AND availableCopies >= ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement checkStmt = con.prepareStatement(queryCheck)) {
            checkStmt.setInt(1, documentId);
            try (ResultSet rs = checkStmt.executeQuery()) {
                if (rs.next()) {
                    int availableCopies = rs.getInt("availableCopies");
                    if (decrementQuantity > availableCopies) {
                        return false;
                    }
                } else {
                    return false;
                }
            }
            try (PreparedStatement updateStmt = con.prepareStatement(queryUpdate)) {
                updateStmt.setInt(1, decrementQuantity);
                updateStmt.setInt(2, documentId);
                updateStmt.setInt(3, decrementQuantity);
                return updateStmt.executeUpdate() > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Document> searchDocumentInDatabase(String query, int maxResults, int startIndex) {
        List<Document> documents = new ArrayList<>();
        String sqlQuery = "SELECT * FROM document WHERE " +
                "(title LIKE ? OR author LIKE ? OR isbn LIKE ? OR publisher LIKE ?) " +
                "AND availability != 'removed' LIMIT ? OFFSET ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(sqlQuery)) {

            String searchKeyword = "%" + query + "%";
            stmt.setString(1, searchKeyword); // title
            stmt.setString(2, searchKeyword); // author
            stmt.setString(3, searchKeyword); // isbn
            stmt.setString(4, searchKeyword); // publisher
            stmt.setInt(5, maxResults);       // max results
            stmt.setInt(6, startIndex);       // offset

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

                    documents.add(document);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return documents;
    }

}
