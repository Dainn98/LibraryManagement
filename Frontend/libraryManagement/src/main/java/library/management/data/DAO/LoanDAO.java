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
        String query = "INSERT INTO loans (userName, documentId, quantityOfBorrow, deposit, dateOfBorrow, requiredReturnDate, returnDate, status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, loan.getUserName());
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
        String query = "UPDATE loans SET userName = ?, documentId = ?, quantityOfBorrow = ?, deposit = ?, " +
                "dateOfBorrow = ?, requiredReturnDate = ?, returnDate = ?, status = ? WHERE loanID = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, loan.getUserName());
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
        String query = "SELECT COUNT(DISTINCT userName) FROM loans WHERE status = 'borrowing'";
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
                loanList.add(mapLoan(rs));
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
                loanList.add(mapLoan(rs));
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

    public List<Loan> searchPendingByUserName(String userName) {
        List<Loan> loanList = new ArrayList<>();
        String query = "SELECT * FROM loans WHERE status = 'pending' AND userName LIKE ?";

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

    public List<Loan> searchHandledIssueByKeyWord(String keyword) {
        List<Loan> loanList = new ArrayList<>();
        String query = "SELECT * FROM loans WHERE (status = 'borrowing' OR status = 'returned' OR status = 'disapproved') " +
                "AND (CAST(loanID AS CHAR) LIKE ? OR CAST(documentId AS CHAR) LIKE ? OR userName LIKE ?)";

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

    private Loan mapLoan(ResultSet rs) throws SQLException {
        Loan loan = new Loan();
        loan.setLoanID(String.format("LOAN%d", rs.getInt("loanID")));
        loan.setUserName(rs.getString("userName")); // Sử dụng userName thay vì userId
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
     * Tìm kiếm các khoản vay (Loan) đang ở trạng thái 'pending' theo Loan ID.
     * @param loanId Loan ID cần tìm kiếm.
     * @return List<Loan> - Danh sách các khoản vay tìm thấy.
     */
    public List<Loan> searchPendingByLoanId(String loanId) {
        String query = "SELECT * FROM loans WHERE status = 'pending' AND CAST(loanID AS CHAR) LIKE ?";
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
     * Tìm kiếm các khoản vay (Loan) đang ở trạng thái 'pending' theo Document ID.
     * @param documentId Document ID cần tìm kiếm.
     * @return List<Loan> - Danh sách các khoản vay tìm thấy.
     */
    public List<Loan> searchPendingByDocumentId(String documentId) {
        String query = "SELECT * FROM loans WHERE status = 'pending' AND CAST(documentId AS CHAR) LIKE ?";
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
     * Tìm kiếm các khoản vay (Loan) đang ở trạng thái 'pending' theo từ khóa.
     * Tìm kiếm trong Loan ID, Document ID, và User Name.
     * @param keyword Từ khóa tìm kiếm.
     * @return List<Loan> - Danh sách các khoản vay tìm thấy.
     */
    public List<Loan> searchPendingIssueByKeyWord(String keyword) {
        String query = "SELECT * FROM loans l " +
                "JOIN user u ON l.userId = u.userName " +
                "WHERE l.status = 'pending' " +
                "AND (CAST(l.loanID AS CHAR) LIKE ? " +
                "OR CAST(l.documentId AS CHAR) LIKE ? " +
                "OR u.userName LIKE ?)";
        List<Loan> loanList = new ArrayList<>();

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

}
