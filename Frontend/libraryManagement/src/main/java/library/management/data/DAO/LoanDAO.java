package library.management.data.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import library.management.data.database.DatabaseConnection;
import library.management.data.entity.Loan;
import org.jetbrains.annotations.NotNull;

public class LoanDAO implements DAOInterface<Loan> {

  private static LoanDAO instance;

  private LoanDAO() {
  }

  /**
   * Retrieves the singleton instance of the LoanDAO class.
   */
  public static synchronized LoanDAO getInstance() {
    if (instance == null) {
      instance = new LoanDAO();
    }
    return instance;
  }

  @Override
  public int add(@NotNull Loan loan) {
    String query =
        "INSERT INTO loans (userName, documentId, quantityOfBorrow, deposit, dateOfBorrow, requiredReturnDate, returnDate, status) "
            +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    try (Connection con = DatabaseConnection.getConnection();
        PreparedStatement stmt = con.prepareStatement(query)) {

      stmt.setString(1, loan.getUserName());
      stmt.setInt(2, loan.getIntDocumentId());
      stmt.setInt(3, loan.getQuantityOfBorrow());
      stmt.setDouble(4, loan.getDeposit());
      stmt.setTimestamp(5, Timestamp.valueOf(loan.getDateOfBorrow()));
      stmt.setTimestamp(6, Timestamp.valueOf(loan.getRequiredReturnDate()));
      stmt.setTimestamp(7,
          loan.getReturnDate() != null ? Timestamp.valueOf(loan.getReturnDate()) : null);
      stmt.setString(8, loan.getStatus());

      return stmt.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return 0;
  }

  @Override
  public int delete(@NotNull Loan loan) {
    String query = "UPDATE loans SET status = 'removed' WHERE loanID = ?";
    try (Connection con = DatabaseConnection.getConnection();
        PreparedStatement stmt = con.prepareStatement(query)) {

      stmt.setInt(1, loan.getIntLoanID());
      return stmt.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return 0;
  }

  @Override
  public int update(@NotNull Loan loan) {
    String query =
        "UPDATE loans SET userName = ?, documentId = ?, quantityOfBorrow = ?, deposit = ?, " +
            "dateOfBorrow = ?, requiredReturnDate = ?, returnDate = ?, status = ? WHERE loanID = ?";
    try (Connection con = DatabaseConnection.getConnection();
        PreparedStatement stmt = con.prepareStatement(query)) {

      stmt.setString(1, loan.getUserName());
      stmt.setInt(2, loan.getIntDocumentId());
      stmt.setInt(3, loan.getQuantityOfBorrow());
      stmt.setDouble(4, loan.getDeposit());
      stmt.setTimestamp(5, Timestamp.valueOf(loan.getDateOfBorrow()));
      stmt.setTimestamp(6, Timestamp.valueOf(loan.getRequiredReturnDate()));
      stmt.setTimestamp(7,
          loan.getReturnDate() != null ? Timestamp.valueOf(loan.getReturnDate()) : null);
      stmt.setString(8, loan.getStatus());
      stmt.setInt(9, loan.getIntLoanID());

      return stmt.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return 0;
  }

  /**
   * Retrieves the total number of users who borrowed books.
   */
  public int getTotalUsersWhoBorrowedBooks() {
    String query = "SELECT COUNT(DISTINCT userName) FROM loans WHERE (status = 'borrowing' or status = 'pendingReturned')";
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
   * Retrieves a list of loans with a status of 'pending' or 'pendingReturned'.
   *
   * @return a list of {@link Loan} objects with a status of 'pending' or 'pendingReturned'.
   */
  public List<Loan> getPendingLoanList() {
    List<Loan> loanList = new ArrayList<>();
    String query = "SELECT * FROM loans WHERE status = 'pending' OR status = 'pendingReturned'";

    try (Connection con = DatabaseConnection.getConnection();
        PreparedStatement stmt = con.prepareStatement(query);
        ResultSet rs = stmt.executeQuery()) {

      while (rs.next()) {
        loanList.add(mapLoan(rs));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return loanList;
  }

  /**
   * Retrieves a list of loans with a status of 'borrowing', 'returned', or 'disapproved'.
   *
   * @return a list of {@link Loan} objects with a status of 'borrowing', 'returned', or
   * 'disapproved'.
   */
  public List<Loan> getHandledLoanList() {
    List<Loan> loanList = new ArrayList<>();
    String query = "SELECT * FROM loans WHERE (status = 'borrowing' or status = 'returned' or status = 'disapproved')";

    try (Connection con = DatabaseConnection.getConnection();
        PreparedStatement stmt = con.prepareStatement(query);
        ResultSet rs = stmt.executeQuery()) {

      while (rs.next()) {
        loanList.add(mapLoan(rs));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return loanList;
  }

  /**
   * Approves a loan by updating its status to 'borrowing'.
   *
   * @param loan the {@link Loan} object to be approved.
   * @return the number of rows updated (1 if successful, 0 if failed).
   */
  public int approve(@NotNull Loan loan) {
    String query = "UPDATE loans SET status = 'borrowing' WHERE loanID = ? AND (status = 'pending')";
    try (Connection con = DatabaseConnection.getConnection();
        PreparedStatement stmt = con.prepareStatement(query)) {

      stmt.setInt(1, loan.getIntLoanID());

      return stmt.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return 0;
  }

  /**
   * Disapproves a loan by updating its status to 'disapproved'.
   *
   * @param loan the {@link Loan} object to be disapproved.
   * @return the number of rows updated (1 if successful, 0 if failed).
   */
  public int disapprove(@NotNull Loan loan) {
    String query = "UPDATE loans SET status = 'disapproved' WHERE loanID = ? AND (status = 'pending')";
    try (Connection con = DatabaseConnection.getConnection();
        PreparedStatement stmt = con.prepareStatement(query)) {

      stmt.setInt(1, loan.getIntLoanID());

      return stmt.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return 0;
  }

  /**
   * Disapproves a return request by updating its status to 'borrowing'.
   *
   * @param loan the {@link Loan} object to be disapproved.
   * @return the number of rows updated (1 if successful, 0 if failed).
   */
  public int disapproveReturn(@NotNull Loan loan) {
    String query = "UPDATE loans SET status = 'borrowing' WHERE loanID = ? AND (status = 'pendingReturned')";
    try (Connection con = DatabaseConnection.getConnection();
        PreparedStatement stmt = con.prepareStatement(query)) {

      stmt.setInt(1, loan.getIntLoanID());

      return stmt.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return 0;
  }

  /**
   * Searches for pending loans by user name.
   *
   * @param userName the user name to search for.
   * @return a list of {@link Loan} objects with a status of 'pending' or 'pendingReturned' and
   * matching the user name.
   */
  public List<Loan> searchPendingByUserName(String userName) {
    List<Loan> loanList = new ArrayList<>();
    String query = "SELECT * FROM loans WHERE (status = 'pending' OR status = 'pendingReturned') AND userName LIKE ?";

    try (Connection con = DatabaseConnection.getConnection();
        PreparedStatement stmt = con.prepareStatement(query)) {

      stmt.setString(1, "%" + userName + "%");

      try (ResultSet rs = stmt.executeQuery()) {
        while (rs.next()) {
          loanList.add(mapLoan(rs));
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return loanList;
  }

  /**
   * Searches for handled loans by user name.
   *
   * @param userName the user name to search for.
   * @return a list of {@link Loan} objects with a status of 'borrowing', 'returned', or
   * 'disapproved' and matching the user name.
   */
  public List<Loan> searchHandledByUserName(String userName) {
    List<Loan> loanList = new ArrayList<>();
    String query = "SELECT * FROM loans WHERE (status = 'borrowing' or status = 'returned' or status = 'disapproved') AND userName LIKE ?";

    try (Connection con = DatabaseConnection.getConnection();
        PreparedStatement stmt = con.prepareStatement(query)) {

      stmt.setString(1, "%" + userName + "%");

      try (ResultSet rs = stmt.executeQuery()) {
        while (rs.next()) {
          loanList.add(mapLoan(rs));
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return loanList;
  }

  /**
   * Searches for handled loans by loan ID.
   *
   * @param loanId the loan ID to search for.
   * @return a list of {@link Loan} objects with a status of 'borrowing', 'returned', or
   * 'disapproved' and matching the loan ID.
   */
  public List<Loan> searchHandledByLoanId(String loanId) {
    List<Loan> loanList = new ArrayList<>();
    String query = "SELECT * FROM loans WHERE (status = 'borrowing' OR status = 'returned' OR status = 'disapproved') AND CAST(loanID AS CHAR) LIKE ?";

    try (Connection con = DatabaseConnection.getConnection();
        PreparedStatement stmt = con.prepareStatement(query)) {
      stmt.setString(1, "%" + loanId + "%");

      try (ResultSet rs = stmt.executeQuery()) {
        while (rs.next()) {
          loanList.add(mapLoan(rs));
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return loanList;
  }

  /**
   * Searches for handled loans by document ID.
   *
   * @param documentId the document ID to search for.
   * @return a list of {@link Loan} objects with a status of 'borrowing', 'returned', or
   * 'disapproved' and matching the document ID.
   */
  public List<Loan> searchHandledByDocumentId(String documentId) {
    List<Loan> loanList = new ArrayList<>();
    String query = "SELECT * FROM loans WHERE (status = 'borrowing' OR status = 'returned' OR status = 'disapproved') AND CAST(documentId AS CHAR) LIKE ?";

    try (Connection con = DatabaseConnection.getConnection();
        PreparedStatement stmt = con.prepareStatement(query)) {
      stmt.setString(1, "%" + documentId + "%");

      try (ResultSet rs = stmt.executeQuery()) {
        while (rs.next()) {
          loanList.add(mapLoan(rs));
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return loanList;
  }

  /**
   * Searches for handled loans by a keyword.
   *
   * @param keyword the keyword to search for.
   * @return a list of {@link Loan} objects with a status of 'borrowing', 'returned', or
   * 'disapproved' and matching the keyword.
   */
  public List<Loan> searchHandledIssueByKeyWord(String keyword) {
    List<Loan> loanList = new ArrayList<>();
    String query =
        "SELECT * FROM loans WHERE (status = 'borrowing' OR status = 'returned' OR status = 'disapproved') "
            +
            "AND (CAST(loanID AS CHAR) LIKE ? OR CAST(documentId AS CHAR) LIKE ? OR userName LIKE ?)";

    return getLoans(keyword, query, loanList);
  }

  /**
   * Maps a {@link ResultSet} to a {@link Loan} object.
   *
   * @param rs the {@link ResultSet} to map.
   * @return a {@link Loan} object.
   * @throws SQLException if a database access error occurs.
   */
  @NotNull
  private Loan mapLoan(@NotNull ResultSet rs) throws SQLException {
    Loan loan = new Loan();
    loan.setLoanID(String.format("LOAN%d", rs.getInt("loanID")));
    loan.setUserName(rs.getString("userName"));
    loan.setDocumentId(String.format("DOC%d", rs.getInt("documentId")));
    loan.setQuantityOfBorrow(rs.getShort("quantityOfBorrow"));
    loan.setDeposit(rs.getDouble("deposit"));
    loan.setDateOfBorrow(rs.getTimestamp("dateOfBorrow").toLocalDateTime());
    loan.setRequiredReturnDate(rs.getTimestamp("requiredReturnDate").toLocalDateTime());
    Timestamp returnDate = rs.getTimestamp("returnDate");
    if (returnDate != null) {
      loan.setReturnDate(returnDate.toLocalDateTime());
    }
    loan.setStatus(rs.getString("status"));
    return loan;
  }

  /**
   * Searches for pending loans by loan ID.
   *
   * @param loanId the loan ID to search for.
   * @return a list of {@link Loan} objects with a status of 'pending' or 'pendingReturned' and
   * matching the loan ID.
   */
  public List<Loan> searchPendingByLoanId(String loanId) {
    String query = "SELECT * FROM loans WHERE (status = 'pending' OR status = 'pendingReturned') AND CAST(loanID AS CHAR) LIKE ?";
    List<Loan> loanList = new ArrayList<>();

    try (Connection con = DatabaseConnection.getConnection();
        PreparedStatement stmt = con.prepareStatement(query)) {

      stmt.setString(1, "%" + loanId + "%");

      try (ResultSet rs = stmt.executeQuery()) {
        while (rs.next()) {
          loanList.add(mapLoan(rs));
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return loanList;
  }

  /**
   * Searches for pending loans by document ID.
   *
   * @param documentId the document ID to search for.
   * @return a list of {@link Loan} objects with a status of 'pending' or 'pendingReturned' and
   * matching the document ID.
   */
  public List<Loan> searchPendingByDocumentId(String documentId) {
    String query = "SELECT * FROM loans WHERE (status = 'pending' OR status = 'pendingReturned') AND CAST(documentId AS CHAR) LIKE ?";
    List<Loan> loanList = new ArrayList<>();

    try (Connection con = DatabaseConnection.getConnection();
        PreparedStatement stmt = con.prepareStatement(query)) {

      stmt.setString(1, "%" + documentId + "%");

      try (ResultSet rs = stmt.executeQuery()) {
        while (rs.next()) {
          loanList.add(mapLoan(rs));
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return loanList;
  }

  /**
   * Searches for pending loans by a keyword.
   *
   * @param keyword the keyword to search for.
   * @return a list of {@link Loan} objects with a status of 'pending' or 'pendingReturned' and
   * matching the keyword.
   */
  public List<Loan> searchPendingIssueByKeyWord(String keyword) {
    String query = "SELECT * FROM loans l " + "JOIN user u ON l.userName = u.userName " +
        "WHERE (l.status = 'pending' OR l.status = 'pendingReturned') " +
        "AND (CAST(l.loanID AS CHAR) LIKE ? " +
        "OR CAST(l.documentId AS CHAR) LIKE ? " +
        "OR u.userName LIKE ?)";
    List<Loan> loanList = new ArrayList<>();

    return getLoans(keyword, query, loanList);
  }

  private List<Loan> getLoans(String keyword, String query, List<Loan> loanList) {
    try (Connection con = DatabaseConnection.getConnection();
        PreparedStatement stmt = con.prepareStatement(query)) {

      String searchPattern = "%" + keyword + "%";
      stmt.setString(1, searchPattern);
      stmt.setString(2, searchPattern);
      stmt.setString(3, searchPattern);

      try (ResultSet rs = stmt.executeQuery()) {
        while (rs.next()) {
          loanList.add(mapLoan(rs));
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return loanList;
  }

  /**
   * Retrieves a list of active loans.
   *
   * @return a list of {@link Loan} objects with a status not in 'removed', 'disapproved',
   * 'returned', or 'pending'.
   */
  public List<Loan> getActiveLoans() {
    List<Loan> loanList = new ArrayList<>();
    String query = "SELECT * FROM loans WHERE status NOT IN ('removed', 'disapproved', 'returned', 'pending')";

    try (Connection con = DatabaseConnection.getConnection();
        PreparedStatement stmt = con.prepareStatement(query);
        ResultSet rs = stmt.executeQuery()) {

      while (rs.next()) {
        loanList.add(mapLoan(rs));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return loanList;
  }

  /**
   * Updates the status of a loan to 'returned' and sets the return date.
   *
   * @param loan the {@link Loan} object to be updated.
   * @return true if the update was successful, false otherwise.
   */
  public boolean returnDocument(@NotNull Loan loan) {
    String query = "UPDATE loans SET status = 'returned', returnDate = ? WHERE loanID = ? AND status NOT IN ('removed', 'disapproved', 'returned', 'pending')";
    try (Connection con = DatabaseConnection.getConnection();
        PreparedStatement stmt = con.prepareStatement(query)) {

      stmt.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
      stmt.setInt(2, loan.getIntLoanID());

      if (stmt.executeUpdate() > 0) {
        if (DocumentDAO.getInstance()
            .decreaseAvailableCopies(loan.getIntDocumentId(), -loan.getQuantityOfBorrow())) {
          return true;
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return false;
  }

  /**
   * Updates the status of a loan to 'pendingReturned'.
   *
   * @param loan the {@link Loan} object to be updated.
   * @return true if the update was successful, false otherwise.
   */
  public boolean userReturnDocument(@NotNull Loan loan) {
    String query = "UPDATE loans SET status = 'pendingReturned' WHERE loanID = ? AND status NOT IN ('removed', 'disapproved', 'returned', 'pending')";
    try (Connection con = DatabaseConnection.getConnection();
        PreparedStatement stmt = con.prepareStatement(query)) {

      stmt.setInt(1, loan.getIntLoanID());

      if (stmt.executeUpdate() > 0) {
        return true;
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return false;
  }

  /**
   * Updates the status of a loan to 'removed' if it is currently 'pending'.
   *
   * @param loan the {@link Loan} object to be updated.
   * @return the number of rows updated (1 if successful, 0 if failed).
   */
  public int undoPending(@NotNull Loan loan) {
    String query = "UPDATE loans SET status = 'removed' WHERE loanID = ? AND status = 'pending'";
    try (Connection con = DatabaseConnection.getConnection();
        PreparedStatement stmt = con.prepareStatement(query)) {

      stmt.setInt(1, loan.getIntLoanID());
      return stmt.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return 0;
  }

  /**
   * Updates the status of a loan to 'late' or 'borrowing' based on the required return date.
   *
   * @param loan the {@link Loan} object to be updated.
   * @return the number of rows updated (1 if successful, 0 if failed).
   */
  public int undoPendingReturn(Loan loan) {
    String query;
    if (loan != null && loan.getRequiredReturnDate() != null
        && loan.getRequiredReturnDate().isBefore(LocalDateTime.now())) {
      query = "UPDATE loans SET status = 'late' WHERE loanID = ? AND status = 'pendingReturned'";
    } else {
      query = "UPDATE loans SET status = 'borrowing' WHERE loanID = ? AND status = 'pendingReturned'";
    }
    try (Connection con = DatabaseConnection.getConnection();
        PreparedStatement stmt = con.prepareStatement(query)) {

      stmt.setInt(1, loan.getIntLoanID());
      return stmt.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return 0;
  }

  /**
   * Searches for loans by loan ID and status.
   *
   * @param loanId   the loan ID to search for.
   * @param statuses the list of statuses to filter by.
   * @return a list of {@link Loan} objects matching the loan ID and statuses.
   */
  public List<Loan> searchReturnLoanByLoanIdAndStatus(String loanId, List<String> statuses) {
    if (statuses == null || statuses.isEmpty()) {
      return new ArrayList<>();
    }
    StringBuilder query = new StringBuilder("SELECT * FROM loans WHERE status IN (");
    for (int i = 0; i < statuses.size(); i++) {
      query.append("?");
      if (i < statuses.size() - 1) {
        query.append(", ");
      }
    }
    query.append(") AND CAST(loanID AS CHAR) LIKE ?");
    List<Loan> loanList = new ArrayList<>();
    try (Connection con = DatabaseConnection.getConnection();
        PreparedStatement stmt = con.prepareStatement(query.toString())) {
      for (int i = 0; i < statuses.size(); i++) {
        stmt.setString(i + 1, statuses.get(i));
      }
      stmt.setString(statuses.size() + 1, "%" + loanId + "%");
      try (ResultSet rs = stmt.executeQuery()) {
        while (rs.next()) {
          loanList.add(mapLoan(rs));
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return loanList;
  }

  /**
   * Retrieves the loan history for a specific user.
   *
   * @param userName the user name to search for.
   * @return a list of {@link Loan} objects with a status of 'returned' or 'disapproved' for the
   * user.
   */
  public List<Loan> getHistoryLoan(String userName) {
    List<Loan> loanList = new ArrayList<>();
    String query = "SELECT * FROM loans WHERE status IN ('returned', 'disapproved') AND userName = ?";
    try (Connection con = DatabaseConnection.getConnection();
        PreparedStatement stmt = con.prepareStatement(query)) {
      stmt.setString(1, userName);
      try (ResultSet rs = stmt.executeQuery()) {
        while (rs.next()) {
          loanList.add(mapLoan(rs));
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return loanList;
  }

  /**
   * Retrieves a list of pending loans for a specific user with a limit on the number of results.
   *
   * @param userName the user name to search for.
   * @param limit    the maximum number of results to return.
   * @return a list of {@link Loan} objects with a status of 'pending' for the user.
   */
  public List<Loan> getPendingLoansByUsername(String userName, int limit) {
    List<Loan> loanList = new ArrayList<>();
    String query = "SELECT * FROM loans WHERE userName = ? AND status = 'pending' ORDER BY dateOfBorrow DESC LIMIT ?";
    try (Connection con = DatabaseConnection.getConnection();
        PreparedStatement stmt = con.prepareStatement(query)) {
      stmt.setString(1, userName);
      stmt.setInt(2, limit);
      try (ResultSet rs = stmt.executeQuery()) {
        while (rs.next()) {
          loanList.add(mapLoan(rs));
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return loanList;
  }

  /**
   * Retrieves a list of pending loans for a specific user.
   *
   * @param userName the user name to search for.
   * @return a list of {@link Loan} objects with a status of 'pending' or 'pendingReturned' for the
   * user.
   */
  public List<Loan> getPendingLoansByUsername(String userName) {
    List<Loan> loanList = new ArrayList<>();
    String query = "SELECT * FROM loans WHERE userName = ? AND (status = 'pending' OR status = 'pendingReturned') ORDER BY dateOfBorrow DESC";
    try (Connection con = DatabaseConnection.getConnection();
        PreparedStatement stmt = con.prepareStatement(query)) {
      stmt.setString(1, userName);
      try (ResultSet rs = stmt.executeQuery()) {
        while (rs.next()) {
          loanList.add(mapLoan(rs));
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return loanList;
  }

  /**
   * Retrieves a list of borrowing loans for a specific user with a limit on the number of results.
   *
   * @param userName the user name to search for.
   * @param limit    the maximum number of results to return.
   * @return a list of {@link Loan} objects with a status of 'borrowing', 'late', or
   * 'pendingReturned' for the user.
   */
  public List<Loan> getBorrowingLoanByUserName(String userName, int limit) {
    List<Loan> loanList = new ArrayList<>();
    String query = "SELECT * FROM loans WHERE userName = ? AND (status = 'borrowing' OR status = 'late' OR status = 'pendingReturned') ORDER BY dateOfBorrow DESC LIMIT ?";
    try (Connection con = DatabaseConnection.getConnection();
        PreparedStatement stmt = con.prepareStatement(query)) {
      stmt.setString(1, userName);
      stmt.setInt(2, limit);
      try (ResultSet rs = stmt.executeQuery()) {
        while (rs.next()) {
          loanList.add(mapLoan(rs));
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return loanList;
  }

  /**
   * Retrieves a list of borrowing loans for a specific user.
   *
   * @param userName the user name to search for.
   * @return a list of {@link Loan} objects with a status of 'borrowing' or 'late' for the user.
   */
  public List<Loan> getBorrowingLoanByUserName(String userName) {
    List<Loan> loanList = new ArrayList<>();
    String query = "SELECT * FROM loans WHERE userName = ? AND (status = 'borrowing' OR status = 'late') ORDER BY dateOfBorrow DESC";
    try (Connection con = DatabaseConnection.getConnection();
        PreparedStatement stmt = con.prepareStatement(query)) {
      stmt.setString(1, userName);
      try (ResultSet rs = stmt.executeQuery()) {
        while (rs.next()) {
          loanList.add(mapLoan(rs));
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return loanList;
  }

  /**
   * Retrieves the loan history for a specific loan ID and user.
   *
   * @param loanId   the loan ID to search for.
   * @param userName the user name to search for.
   * @return a list of {@link Loan} objects with a status of 'returned' for the loan ID and user.
   */
  public List<Loan> getHistoryByLoanID(String loanId, String userName) {
    List<Loan> loanList = new ArrayList<>();
    String query = "SELECT * FROM loans WHERE status = 'returned' AND loanID LIKE ? AND userName = ?";

    try (Connection con = DatabaseConnection.getConnection();
        PreparedStatement stmt = con.prepareStatement(query)) {

      stmt.setString(1, "%" + loanId + "%");
      stmt.setString(2, userName);

      try (ResultSet rs = stmt.executeQuery()) {
        while (rs.next()) {
          loanList.add(mapLoan(rs));
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return loanList;
  }

  /**
   * Searches for loan history by document ID and user name.
   *
   * @param documentId the document ID to search for.
   * @param userName   the user name to search for.
   * @return a list of {@link Loan} objects with a status of 'returned' for the document ID and
   * user.
   */
  public List<Loan> searchHistoryByDocumentId(String documentId, String userName) {
    List<Loan> loanList = new ArrayList<>();
    String query = "SELECT * FROM loans WHERE status = 'returned' AND documentId LIKE ? AND userName = ?";

    try (Connection con = DatabaseConnection.getConnection();
        PreparedStatement stmt = con.prepareStatement(query)) {

      stmt.setString(1, "%" + documentId + "%"); // Tìm kiếm documentId chứa chuỗi được nhập
      stmt.setString(2, userName);              // Điều kiện username chính xác

      try (ResultSet rs = stmt.executeQuery()) {
        while (rs.next()) {
          loanList.add(mapLoan(rs));        // Mapping kết quả vào đối tượng Loan
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return loanList;
  }

  /**
   * Searches for loan history by a keyword and user name.
   *
   * @param keyword  the keyword to search for.
   * @param userName the user name to search for.
   * @return a list of {@link Loan} objects with a status of 'returned' for the keyword and user.
   */
  public List<Loan> searchHistoryByKeyWord(String keyword, String userName) {
    List<Loan> loanList = new ArrayList<>();
    String query = "SELECT * FROM loans WHERE status = 'returned' AND userName = ? " +
        "AND (CAST(loanID AS CHAR) LIKE ? OR CAST(documentId AS CHAR) LIKE ?)";

    try (Connection con = DatabaseConnection.getConnection();
        PreparedStatement stmt = con.prepareStatement(query)) {

      stmt.setString(1, userName); // Điều kiện chính xác theo username
      String searchPattern = "%" + keyword + "%";
      stmt.setString(2, searchPattern); // Tìm kiếm loanID chứa từ khóa
      stmt.setString(3, searchPattern); // Tìm kiếm documentId chứa từ khóa

      try (ResultSet rs = stmt.executeQuery()) {
        while (rs.next()) {
          loanList.add(mapLoan(rs)); // Mapping kết quả vào đối tượng Loan
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return loanList;
  }

  /**
   * Updates the status of loans to 'late' if the required return date has passed.
   */
  public void updateLateLoans() {
    System.out.println("late");
    String query = "SELECT * FROM loans WHERE status = 'borrowing'";
    try (Connection con = DatabaseConnection.getConnection();
        PreparedStatement stmt = con.prepareStatement(query);
        ResultSet rs = stmt.executeQuery()) {
      while (rs.next()) {
        Loan loan = mapLoan(rs);
        if (loan != null && loan.getRequiredReturnDate() != null
            && loan.getRequiredReturnDate().isBefore(LocalDateTime.now())) {
          String updateQuery = "UPDATE loans SET status = 'late' WHERE loanID = ?";
          try (PreparedStatement updateStmt = con.prepareStatement(updateQuery)) {
            updateStmt.setInt(1, rs.getInt("loanID"));
            int rowsUpdated = updateStmt.executeUpdate();
            if (rowsUpdated > 0) {
              System.out.println("Loan ID " + rs.getInt("loanID") + " updated to 'late'.");
            } else {
              System.out.println("Loan ID " + rs.getInt("loanID") + " was already 'late'.");
            }
          }
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public Loan getLoanByLoanID(int loanID) {
    String query = "SELECT * FROM loans WHERE loanID = ?";
    try (Connection con = DatabaseConnection.getConnection();
        PreparedStatement stmt = con.prepareStatement(query)) {
      stmt.setInt(1, loanID);
      try (ResultSet rs = stmt.executeQuery()) {
        if (rs.next()) {
          return mapLoan(rs);
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

  public void deleteLoanByLoanID(int loanID) {
    String query = "DELETE FROM loans WHERE loanID = ?";
    try (Connection con = DatabaseConnection.getConnection();
        PreparedStatement stmt = con.prepareStatement(query)) {
      stmt.setInt(1, loanID);
      stmt.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

}
