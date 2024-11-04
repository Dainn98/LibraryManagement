package library.management.data.DAO;

import library.management.data.database.KetNoiCSDL;
import library.management.data.entity.Loan;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LoanDAO implements DAOInterface<Loan> {

    private LoanDAO() {}

    public static LoanDAO getInstance() {
        return new LoanDAO();
    }

    @Override
    public int them(Loan loan) {
        Connection con = KetNoiCSDL.getConnection();
        String query = "INSERT INTO loans (userId, quantityOfBorrow, deposit, dateOfBorrow, requiredReturnDate) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = con.prepareStatement(query)) {
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

    @Override
    public int xoa(Loan loan) {
        Connection con = KetNoiCSDL.getConnection();
        String query = "DELETE FROM loans WHERE loanID = ?";
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, loan.getLoanID());

            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int capNhat(Loan loan) {
        Connection con = KetNoiCSDL.getConnection();
        String query = "UPDATE loans SET userId = ?, quantityOfBorrow = ?, deposit = ?, dateOfBorrow = ?, requiredReturnDate = ? WHERE loanID = ?";
        try (PreparedStatement stmt = con.prepareStatement(query)) {
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

    public List<Loan> layTatCa() {
        Connection con = KetNoiCSDL.getConnection();
        String query = "SELECT * FROM loans";
        List<Loan> list = new ArrayList<>();
        try (PreparedStatement stmt = con.prepareStatement(query);
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

    public Loan layTheoId(int STT) {
        Connection con = KetNoiCSDL.getConnection();
        String query = "SELECT * FROM loans WHERE STT = ?";
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setInt(1, STT);
            ResultSet rs = stmt.executeQuery();

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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
