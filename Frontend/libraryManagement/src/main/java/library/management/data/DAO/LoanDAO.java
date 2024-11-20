package library.management.data.DAO;

import library.management.data.database.DatabaseConnection;
import library.management.data.entity.Loan;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LoanDAO implements DAOInterface<Loan> {
    private static LoanDAO instance;

    private LoanDAO() {
    }

    public static synchronized LoanDAO getInstance() {
        if (instance == null) {
            instance = new LoanDAO();
        }
        return instance;
    }

    @Override
    public int add(Loan loan) {
        String query = "INSERT INTO loans (userId, documentId, quantityOfBorrow, deposit, dateOfBorrow, requiredReturnDate, returnDate, status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setInt(1, loan.getIntUserId());
            stmt.setInt(2, loan.getIntDocumentId());
            stmt.setShort(3, loan.getQuantityOfBorrow());
            stmt.setDouble(4, loan.getDeposit());
            stmt.setTimestamp(5, Timestamp.valueOf(loan.getDateOfBorrow()));
            stmt.setTimestamp(6, Timestamp.valueOf(loan.getRequiredReturnDate()));
            stmt.setTimestamp(7, loan.getReturnDate() != null ? Timestamp.valueOf(loan.getReturnDate()) : null);
            stmt.setString(8, loan.getStatus());

            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int delete(Loan loan) {
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
    public int update(Loan loan) {
        String query = "UPDATE loans SET userId = ?, documentId = ?, quantityOfBorrow = ?, deposit = ?, " +
                "dateOfBorrow = ?, requiredReturnDate = ?, returnDate = ?, status = ? WHERE loanID = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setInt(1, loan.getIntUserId());
            stmt.setInt(2, loan.getIntDocumentId());
            stmt.setShort(3, loan.getQuantityOfBorrow());
            stmt.setDouble(4, loan.getDeposit());
            stmt.setTimestamp(5, Timestamp.valueOf(loan.getDateOfBorrow()));
            stmt.setTimestamp(6, Timestamp.valueOf(loan.getRequiredReturnDate()));
            stmt.setTimestamp(7, loan.getReturnDate() != null ? Timestamp.valueOf(loan.getReturnDate()) : null);
            stmt.setString(8, loan.getStatus());
            stmt.setInt(9, loan.getIntLoanID());

            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getTotalUsersWhoBorrowedBooks() {
        String query = "SELECT COUNT(DISTINCT userId) FROM loans WHERE status = 'borrowing'";
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

    public List<Loan> getPendingLoanList() {
        List<Loan> loanList = new ArrayList<>();
        String query = "SELECT * FROM loans WHERE status = 'pending'";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Loan loan = new Loan();
                loan.setLoanID(String.format("LOAN%d", rs.getInt("loanID")));
                loan.setUserId(String.format("USER%d", rs.getInt("userId")));
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

                loanList.add(loan);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return loanList;
    }

    public List<Loan> getHandledLoanList() {
        List<Loan> loanList = new ArrayList<>();
        String query = "SELECT * FROM loans WHERE (status = 'borrowing' or status = 'returned' or status = 'disapproved')";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Loan loan = new Loan();
                loan.setLoanID(String.format("LOAN%d", rs.getInt("loanID")));
                loan.setUserId(String.format("USER%d", rs.getInt("userId")));
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

                loanList.add(loan);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return loanList;
    }

    public int approve(Loan loan) {
        String query = "UPDATE loans SET status = 'borrowing' WHERE loanID = ? AND status = 'pending'";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setInt(1, loan.getIntLoanID());

            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int disapprove(Loan loan) {
        String query = "UPDATE loans SET status = 'disapproved' WHERE loanID = ? AND status = 'pending'";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setInt(1, loan.getIntLoanID());

            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Loan> searchPendingIssueByKeyWord(String keyword) {
        List<Loan> loanList = new ArrayList<>();
        String query = "SELECT * FROM loans " +
                "WHERE status = 'pending' " +
                "AND (CAST(loanID AS CHAR) LIKE ? " +
                "OR CAST(documentId AS CHAR) LIKE ? " +
                "OR CAST(userId AS CHAR) LIKE ?)";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {
            String searchPattern = "%" + keyword + "%";
            stmt.setString(1, searchPattern);
            stmt.setString(2, searchPattern);
            stmt.setString(3, searchPattern);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Loan loan = new Loan();
                    loan.setLoanID(String.format("LOAN%d", rs.getInt("loanID")));
                    loan.setUserId(String.format("USER%d", rs.getInt("userId")));
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
                    loanList.add(loan);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return loanList;
    }

    public List<Loan> searchHandledIssueByKeyWord(String keyword) {
        List<Loan> loanList = new ArrayList<>();
        String query = "SELECT * FROM loans " +
                "WHERE (status = 'borrowing' or status = 'returned' or status = 'disapproved')" +
                "AND (CAST(loanID AS CHAR) LIKE ? " +
                "OR CAST(documentId AS CHAR) LIKE ? " +
                "OR CAST(userId AS CHAR) LIKE ?)";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {
            String searchPattern = "%" + keyword + "%";
            stmt.setString(1, searchPattern);
            stmt.setString(2, searchPattern);
            stmt.setString(3, searchPattern);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Loan loan = new Loan();
                    loan.setLoanID(String.format("LOAN%d", rs.getInt("loanID")));
                    loan.setUserId(String.format("USER%d", rs.getInt("userId")));
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
                    loanList.add(loan);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return loanList;
    }

    public List<Loan> searchPendingByUserId(String userId) {
        List<Loan> loanList = new ArrayList<>();
        String query = "SELECT * FROM loans WHERE status = 'pending' AND CAST(userId AS CHAR) LIKE ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, "%" + userId + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Loan loan = mapLoan(rs);
                    loanList.add(loan);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return loanList;
    }

    public List<Loan> searchHandledByUserId(String userId) {
        List<Loan> loanList = new ArrayList<>();
        String query = "SELECT * FROM loans WHERE (status = 'borrowing' or status = 'returned' or status = 'disapproved') AND CAST(userId AS CHAR) LIKE ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, "%" + userId + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Loan loan = mapLoan(rs);
                    loanList.add(loan);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return loanList;
    }

    public List<Loan> searchPendingByLoanId(String loanId) {
        List<Loan> loanList = new ArrayList<>();
        String query = "SELECT * FROM loans WHERE status = 'pending' AND CAST(loanId AS CHAR) LIKE ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, "%" + loanId + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Loan loan = mapLoan(rs);
                    loanList.add(loan);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return loanList;
    }

    public List<Loan> searchHandledByLoanId(String loanId) {
        List<Loan> loanList = new ArrayList<>();
        String query = "SELECT * FROM loans WHERE (status = 'borrowing' or status = 'returned' or status = 'disapproved') AND CAST(loanId AS CHAR) LIKE ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, "%" + loanId + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Loan loan = mapLoan(rs);
                    loanList.add(loan);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return loanList;
    }

    public List<Loan> searchPendingByDocumentId(String documentId) {
        List<Loan> loanList = new ArrayList<>();
        String query = "SELECT * FROM loans WHERE status = 'pending' AND CAST(documentId AS CHAR) LIKE ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, "%" + documentId + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Loan loan = mapLoan(rs);
                    loanList.add(loan);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return loanList;
    }

    public List<Loan> searchHandledByDocumentId(String documentId) {
        List<Loan> loanList = new ArrayList<>();
        String query = "SELECT * FROM loans WHERE (status = 'borrowing' or status = 'returned' or status = 'disapproved') AND CAST(documentId AS CHAR) LIKE ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, "%" + documentId + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Loan loan = mapLoan(rs);
                    loanList.add(loan);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return loanList;
    }


    private Loan mapLoan(ResultSet rs) throws SQLException {
        Loan loan = new Loan();
        loan.setLoanID(String.format("LOAN%d", rs.getInt("loanID")));
        loan.setUserId(String.format("USER%d", rs.getInt("userId")));
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

}
