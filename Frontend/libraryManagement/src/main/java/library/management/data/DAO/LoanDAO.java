package library.management.data.DAO;

import library.management.data.database.DatabaseConnection;
import library.management.data.entity.Loan;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LoanDAO implements DAOInterface<Loan> {

    private LoanDAO() {}

    public static LoanDAO getInstance() {
        return new LoanDAO();
    }

    // Thêm một khoản vay vào cơ sở dữ liệu
    @Override
    public int add(Loan loan) {
        String query = "INSERT INTO loans (userId, quantityOfBorrow, deposit, dateOfBorrow, requiredReturnDate) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, loan.getUserId());
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

    // Xóa một khoản vay khỏi cơ sở dữ liệu
    @Override
    public int delete(Loan loan) {
        String query = "DELETE FROM loans WHERE loanID = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, loan.getLoanID());
            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Cập nhật thông tin của một khoản vay trong cơ sở dữ liệu
    @Override
    public int update(Loan loan) {
        String query = "UPDATE loans SET userId = ?, quantityOfBorrow = ?, deposit = ?, dateOfBorrow = ?, requiredReturnDate = ? WHERE loanID = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, loan.getUserId());
            stmt.setShort(2, loan.getQuantityOfBorrow());
            stmt.setDouble(3, loan.getDeposit());
            stmt.setTimestamp(4, new Timestamp(loan.getDateOfBorrow().getTime()));
            stmt.setTimestamp(5, new Timestamp(loan.getRequiredReturnDate().getTime()));
            stmt.setString(6, loan.getLoanID());

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Lấy danh sách tất cả các khoản vay
    public List<Loan> layTatCa() {
        String query = "SELECT * FROM loans";
        List<Loan> list = new ArrayList<>();
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Loan loan = new Loan();
                loan.setSTT(rs.getInt("STT"));
                loan.setUserId(rs.getString("userId"));
                loan.setQuantityOfBorrow(rs.getShort("quantityOfBorrow"));
                loan.setDeposit(rs.getDouble("deposit"));
                loan.setDateOfBorrow(rs.getTimestamp("dateOfBorrow"));
                loan.setRequiredReturnDate(rs.getTimestamp("requiredReturnDate"));
                loan.setLoanID(rs.getString("loanID"));

                list.add(loan);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Lấy thông tin khoản vay theo STT
    public Loan layTheoId(int STT) {
        String query = "SELECT * FROM loans WHERE STT = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setInt(1, STT);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Loan loan = new Loan();
                    loan.setSTT(rs.getInt("STT"));
                    loan.setUserId(rs.getString("userId"));
                    loan.setQuantityOfBorrow(rs.getShort("quantityOfBorrow"));
                    loan.setDeposit(rs.getDouble("deposit"));
                    loan.setDateOfBorrow(rs.getTimestamp("dateOfBorrow"));
                    loan.setRequiredReturnDate(rs.getTimestamp("requiredReturnDate"));
                    loan.setLoanID(rs.getString("loanID"));
                    return loan;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
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
