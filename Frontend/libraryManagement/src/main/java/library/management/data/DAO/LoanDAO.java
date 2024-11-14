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
        String query = "INSERT INTO loans (userId, quantityOfBorrow, deposit, dateOfBorrow, requiredReturnDate) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setInt(1, loan.getIntUserId());

            stmt.setShort(2, loan.getQuantityOfBorrow());
            stmt.setDouble(3, loan.getDeposit());
            stmt.setTimestamp(4, new Timestamp(loan.getDateOfBorrow().getTime()));
            stmt.setTimestamp(5, new Timestamp(loan.getRequiredReturnDate().getTime()));

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int delete(Loan loan) {
        String query = "DELETE FROM loans WHERE loanID = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setInt(1, loan.getIntLoanID());

            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int update(Loan loan) {
        String query = "UPDATE loans SET userId = ?, quantityOfBorrow = ?, deposit = ?, dateOfBorrow = ?, requiredReturnDate = ? WHERE loanID = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setInt(1, loan.getIntUserId());

            stmt.setShort(2, loan.getQuantityOfBorrow());
            stmt.setDouble(3, loan.getDeposit());
            stmt.setTimestamp(4, new Timestamp(loan.getDateOfBorrow().getTime()));
            stmt.setTimestamp(5, new Timestamp(loan.getRequiredReturnDate().getTime()));
            stmt.setInt(6, loan.getIntLoanID());


            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }


    public int getTotalUsersWhoBorrowedBooks() {
        String query = "SELECT COUNT(DISTINCT userId) FROM loans";
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
