package library.management.data.DAO;

import library.management.data.database.DatabaseConnection;
import library.management.data.entity.Loan;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LoanDAO implements DAOInterface<Loan> {

    private LoanDAO() {
    }

    public static LoanDAO getInstance() {
        return new LoanDAO();
    }

    @Override
    public int add(Loan loan) {
        String query = "INSERT INTO loans (userId, documentId, quantityOfBorrow, deposit, dateOfBorrow, requiredReturnDate, status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setInt(1, loan.getIntUserId());
            stmt.setInt(2, loan.getIntDocumentId());
            stmt.setShort(3, loan.getQuantityOfBorrow());
            stmt.setDouble(4, loan.getDeposit());
            stmt.setTimestamp(5, new Timestamp(loan.getDateOfBorrow().getTime()));
            stmt.setTimestamp(6, new Timestamp(loan.getRequiredReturnDate().getTime()));
            stmt.setString(7, loan.getStatus());

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
                "dateOfBorrow = ?, requiredReturnDate = ?, status = ? WHERE loanID = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setInt(1, loan.getIntUserId());
            stmt.setInt(2, loan.getIntDocumentId());
            stmt.setShort(3, loan.getQuantityOfBorrow());
            stmt.setDouble(4, loan.getDeposit());
            stmt.setTimestamp(5, new Timestamp(loan.getDateOfBorrow().getTime()));
            stmt.setTimestamp(6, new Timestamp(loan.getRequiredReturnDate().getTime()));
            stmt.setString(7, loan.getStatus());
            stmt.setInt(8, loan.getIntLoanID());

            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getTotalUsersWhoBorrowedBooks() {
        String query = "SELECT COUNT(DISTINCT userId) FROM loans WHERE status = 'approved'";
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
