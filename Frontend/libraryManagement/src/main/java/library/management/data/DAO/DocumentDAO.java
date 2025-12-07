package library.management.data.DAO;

import com.almasb.fxgl.scene3d.Cone;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import library.management.data.database.DatabaseConnection;
import library.management.data.entity.Document;
import org.jetbrains.annotations.Nullable;

/**
 * Singleton class responsible for performing CRUD operations on the "document" table in the
 * database. This class provides methods for adding, deleting, updating, and retrieving documents.
 * It also provides utility methods to search and group documents.
 */
public class DocumentDAO implements DAOInterface<Document> {

  private static DocumentDAO instance;

  private DocumentDAO() {
  }

  /**
   * Retrieves the singleton instance of the DocumentDAO class.
   *
   * @return the singleton instance of DocumentDAO.
   */
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
        PreparedStatement stmt = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

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

      int affectedRows = stmt.executeUpdate();
      if (affectedRows == 0) {
        throw new SQLException("Creating document failed, no rows affected.");
      }

      try (ResultSet rs = stmt.getGeneratedKeys()) {
        if (rs.next()) {
          int generatedID = rs.getInt(1);
          System.out.println("Generated Document ID: " + generatedID);
          document.setDocumentID("DOC" + generatedID);
          return generatedID;
        } else {
          throw new SQLException("Creating document failed, no ID obtained.");
        }
      }
    } catch (SQLException e) {
      throw new RuntimeException(e.getMessage(), e);
    }
  }


  @Override
  public int delete(Document document) {
    String query = "UPDATE document SET availability = 'removed' WHERE documentId = ?";
    try (Connection con = DatabaseConnection.getConnection();
        PreparedStatement stmt = con.prepareStatement(query)) {

      stmt.setInt(1, document.getIntDocumentID());
      System.out.println("Deleting document with ID: " + document.getIntDocumentID());
      return stmt.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return 0;
  }

  public int deleteDocumentFromDatabase(Document document){
    String query = "DELETE FROM document WHERE documentId = ?";
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


  /**
   * Retrieves a document by its ISBN.
   */
  public Document getDocumentByIsbn(String isbn) {
    String query = "SELECT * FROM document WHERE isbn = ? AND availability != 'removed'";

    try (Connection con = DatabaseConnection.getConnection();
        PreparedStatement stmt = con.prepareStatement(query)) {

      stmt.setString(1, isbn);

      try (ResultSet rs = stmt.executeQuery()) {
        if (rs.next()) {
          return mapResultSetToDocument(rs);
        }
      }
    } catch (SQLException e) {
      System.err.println("Error while fetching document with ISBN: " + isbn);
      e.printStackTrace();
    }

    return null;
  }

  /**
   * Maps a {@link ResultSet} to a {@link Document} object.
   */
  private Document mapResultSetToDocument(ResultSet rs) throws SQLException {
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

    Timestamp timestamp = rs.getTimestamp("addDate");
    if (timestamp != null) {
      LocalDateTime addDate = timestamp.toLocalDateTime();
      document.setAddDate(addDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")));
    }

    document.setPrice(rs.getBigDecimal("price").doubleValue());
    document.setDescription(rs.getString("description"));
    document.setUrl(rs.getString("url"));
    document.setImage(rs.getString("image"));
    document.setAvailability(rs.getString("availability"));

    return document;
  }

  /**
   * Retrieves a list of all available documents.
   */
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

  /**
   * Retrieves the total quantity of all documents.
   */
  public int getTotalQuantity() {
    String query = "SELECT SUM(quantity) FROM document  where availability <> 'removed'";
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

  /**
   * Retrieves the total number of available copies of all documents.
   */
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

  /**
   * Searches for documents based on a keyword.
   */
  public List<Document> searchDocuments(String keyword) {
    List<Document> searchResults = new ArrayList<>();
    String query = "SELECT * FROM document WHERE " +
        "(CAST(documentId AS CHAR) LIKE ? OR " +
        "isbn LIKE ? OR " +
        "title LIKE ? OR " +
        "author LIKE ? OR " +
        "publisher LIKE ? OR " +
        "CAST(categoryID AS CHAR) LIKE ?) AND availability != 'removed'";

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

  /**
   * Searches for a document by its ID.
   */
  public Document searchDocumentById(int documentId) {
    String query = "SELECT * FROM document WHERE documentId = ? AND availability != 'removed'";
    return getDocument(documentId, query);
  }

  public Document searchDocumentFromDatabaseById(int documentId) {
    String query = "SELECT * FROM document WHERE documentId = ?";
    return getDocument(documentId, query);
  }

  @Nullable
  private Document getDocument(int documentId, String query) {
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
      throw new RuntimeException(e.getMessage(), e);
    }
    return null;
  }

  /**
   * Checks if a document can be borrowed.
   */
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

  /**
   * Decreases the available copies of a document.
   */
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

  public boolean increaseAvailableCopies(int documentId, int incrementQuantity) {
    String query = "UPDATE document SET availableCopies = availableCopies + ? WHERE documentId = ?";

    try (Connection con = DatabaseConnection.getConnection();
        PreparedStatement stmt = con.prepareStatement(query)) {

      stmt.setInt(1, incrementQuantity);
      stmt.setInt(2, documentId);
      return stmt.executeUpdate() > 0;

    } catch (SQLException e) {
      e.printStackTrace();
    }
    return false;
  }

  /**
   * Searches for documents in the database with pagination.
   */
  public List<Document> searchDocumentInDatabase(String query, int maxResults, int startIndex) {
    List<Document> documents = new ArrayList<>();
    String sqlQuery = "SELECT * FROM document WHERE " +
        "(title LIKE ? OR author LIKE ? OR isbn LIKE ? OR publisher LIKE ?) " +
        "AND availability != 'removed' LIMIT ? OFFSET ?";

    try (Connection con = DatabaseConnection.getConnection();
        PreparedStatement stmt = con.prepareStatement(sqlQuery)) {

      String searchKeyword = "%" + query + "%";
      stmt.setString(1, searchKeyword);
      stmt.setString(2, searchKeyword);
      stmt.setString(3, searchKeyword);
      stmt.setString(4, searchKeyword);
      stmt.setInt(5, maxResults);
      stmt.setInt(6, startIndex);

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

  /**
   * Searches for ISBNs by a keyword with a limit on the number of results.
   */
  public List<String> searchISBNByKeyword(String query, int limit) {
    List<String> isbns = new ArrayList<>();
    String sqlQuery = "SELECT isbn FROM document WHERE isbn LIKE ? AND availability != 'removed' LIMIT ?";
    try (Connection con = DatabaseConnection.getConnection();
        PreparedStatement stmt = con.prepareStatement(sqlQuery)) {
      String searchKeyword = "%" + query + "%";
      stmt.setString(1, searchKeyword);
      stmt.setInt(2, limit);
      try (ResultSet rs = stmt.executeQuery()) {
        while (rs.next()) {
          isbns.add(rs.getString("isbn"));
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return isbns;
  }

  /**
   * Searches for a document by its ISBN.
   */
  public Document searchDocumentByISBN(String isbn) {
    String query = "SELECT * FROM document WHERE isbn = ? AND availability != 'removed'";
    try (Connection con = DatabaseConnection.getConnection();
        PreparedStatement stmt = con.prepareStatement(query)) {
      stmt.setString(1, isbn);
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

  /**
   * Searches and groups documents based on a query and filters.
   */
  public List<List<Document>> searchAndGroupDocuments(String query, List<String> filters,
      int limitCategory, int limitBooksPerCategory) {
    if (filters.isEmpty()) {
      return new ArrayList<>();
    }
    List<List<Document>> groupedDocuments = new ArrayList<>();
    StringBuilder whereClause = new StringBuilder("WHERE availability != 'removed' ");
    List<String> filterColumns = new ArrayList<>();
    for (String filter : filters) {
      filterColumns.add(filter + " LIKE ?");
    }
    if (!filterColumns.isEmpty()) {
      whereClause.append("AND (").append(String.join(" OR ", filterColumns)).append(") ");
    }
    String categoryQuery = "SELECT categoryID, COUNT(*) AS totalBooks " +
        "FROM document " +
        whereClause +
        "GROUP BY categoryID " +
        "ORDER BY totalBooks DESC " +
        "LIMIT ?";

    try (Connection con = DatabaseConnection.getConnection();
        PreparedStatement categoryStmt = con.prepareStatement(categoryQuery)) {
      int paramIndex = 1;
      String searchKeyword = "%" + query + "%";
      for (int i = 0; i < filters.size(); i++) {
        categoryStmt.setString(paramIndex++, searchKeyword);
      }
      categoryStmt.setInt(paramIndex++, limitCategory);

      try (ResultSet categoryRs = categoryStmt.executeQuery()) {
        while (categoryRs.next()) {
          int categoryID = categoryRs.getInt("categoryID");
          List<Document> documents = new ArrayList<>();

          String documentQuery = "SELECT * FROM document " +
              whereClause +
              "AND categoryID = ? " +
              "ORDER BY title ASC " +
              "LIMIT ?";

          try (PreparedStatement documentStmt = con.prepareStatement(documentQuery)) {
            int docParamIndex = 1;
            for (int i = 0; i < filters.size(); i++) {
              documentStmt.setString(docParamIndex++, searchKeyword);
            }
            documentStmt.setInt(docParamIndex++, categoryID);
            documentStmt.setInt(docParamIndex++, limitBooksPerCategory);

            try (ResultSet documentRs = documentStmt.executeQuery()) {
              while (documentRs.next()) {
                Document document = new Document();
                document.setDocumentID(String.format("DOC%d", documentRs.getInt("documentId")));
                document.setCategoryID(String.format("CAT%d", documentRs.getInt("categoryID")));
                document.setPublisher(documentRs.getString("publisher"));
                document.setLgID(String.format("LANG%d", documentRs.getInt("lgID")));
                document.setTitle(documentRs.getString("title"));
                document.setAuthor(documentRs.getString("author"));
                document.setIsbn(documentRs.getString("isbn"));
                document.setQuantity(documentRs.getInt("quantity"));
                document.setAvailableCopies(documentRs.getInt("availableCopies"));
                document.setAddDate(
                    documentRs.getTimestamp("addDate").toLocalDateTime().toString());
                document.setPrice(documentRs.getBigDecimal("price").doubleValue());
                document.setDescription(documentRs.getString("description"));
                document.setUrl(documentRs.getString("url"));
                document.setImage(documentRs.getString("image"));
                document.setAvailability(documentRs.getString("availability"));

                documents.add(document);
              }
            }
          }

          if (!documents.isEmpty()) {
            groupedDocuments.add(documents);
          }
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return groupedDocuments;
  }

  public int getMaxDocumentId() {
    String query = "SELECT MAX(documentId) AS maxId FROM document";
    try (Connection con = DatabaseConnection.getConnection();
        PreparedStatement stmt = con.prepareStatement(query);
        ResultSet rs = stmt.executeQuery()) {

      if (rs.next()) {
        return rs.getInt("maxId");
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return 0;
  }

  public Document getDocumentByDocID(String docID) {
    String query = "SELECT * FROM document WHERE documentId = ? AND availability != 'removed'";
    try (Connection con = DatabaseConnection.getConnection();
        PreparedStatement stmt = con.prepareStatement(query)) {
      stmt.setInt(1, Integer.parseInt(docID.substring(3)));
      try (ResultSet rs = stmt.executeQuery()) {
        if (rs.next()) {
          return mapResultSetToDocument(rs);
        }
      }
    } catch (SQLException e) {
      System.err.println("Error while fetching document with ID: " + docID);
      e.printStackTrace();
    }
    if (docID == null || !docID.startsWith("DOC")) {
      throw new IllegalArgumentException("Invalid format for Document ID: " + docID);
    }
    return null;
  }

  public Document getIntDocumentByDocID(int docID) {
    String query = "SELECT * FROM document WHERE documentId = ? AND availability != 'removed'";
    try (Connection con = DatabaseConnection.getConnection();
        PreparedStatement stmt = con.prepareStatement(query)) {
      stmt.setInt(1, docID);
      try (ResultSet rs = stmt.executeQuery()) {
        if (rs.next()) {
          return mapResultSetToDocument(rs);
        }
      }
    } catch (SQLException e) {
      System.err.println("Error while fetching document with ID: " + docID);
      e.printStackTrace();
    }
    return null;
  }
}
