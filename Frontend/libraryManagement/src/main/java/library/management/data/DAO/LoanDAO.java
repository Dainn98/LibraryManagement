package library.management.data.DAO;

import library.management.data.database.DatabaseConnection;
import library.management.data.entity.Loan;

import java.sql.*;
import java.time.LocalDateTime;
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
            stmt.setInt(3, loan.getQuantityOfBorrow());
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
            stmt.setInt(3, loan.getQuantityOfBorrow());
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

    public boolean returnDocument(Loan loan) {
        String query = "UPDATE loans SET status = 'returned', returnDate = ? WHERE loanID = ? AND status NOT IN ('removed', 'disapproved', 'returned', 'pending')";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setInt(2, loan.getIntLoanID());

            if (stmt.executeUpdate() > 0) {
                if (DocumentDAO.getInstance().decreaseAvailableCopies(loan.getIntDocumentId(), -loan.getQuantityOfBorrow())) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

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

    public List<Loan> getHistoryLoan(String userName) {
        List<Loan> loanList = new ArrayList<>();
        String query = "SELECT * FROM loans WHERE status IN ('returned', 'late') AND userName = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, userName); // Gán giá trị userName vào câu truy vấn

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    loanList.add(mapLoan(rs)); // Ánh xạ từng bản ghi thành đối tượng Loan
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return loanList;
    }


}
