package library.management.DAO;

import library.management.database.KetNoiCSDL;
import library.management.entity.LoanDetail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LoanDetailDAO implements DAOInterface<LoanDetail> {

    private LoanDetailDAO() {}

    public static LoanDetailDAO getInstance() {
        return new LoanDetailDAO();
    }

    @Override
    public int them(LoanDetail loanDetail) {
        Connection con = KetNoiCSDL.getConnection();
        String query = "INSERT INTO loandetail (loanId, bookId, quantity) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, loanDetail.getLoanId());
            stmt.setString(2, loanDetail.getBookId());
            stmt.setShort(3, loanDetail.getQuantity());

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int xoa(LoanDetail loanDetail) {
        Connection con = KetNoiCSDL.getConnection();
        String query = "DELETE FROM loandetail WHERE loanDetailID = ?";
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, loanDetail.getLoanDetailID());

            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int capNhat(LoanDetail loanDetail) {
        Connection con = KetNoiCSDL.getConnection();
        String query = "UPDATE loandetail SET loanId = ?, bookId = ?, quantity = ? WHERE loanDetailID = ?";
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, loanDetail.getLoanId());
            stmt.setString(2, loanDetail.getBookId());
            stmt.setShort(3, loanDetail.getQuantity());
            stmt.setString(4, loanDetail.getLoanDetailID());

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<LoanDetail> layTatCa() {
        Connection con = KetNoiCSDL.getConnection();
        String query = "SELECT * FROM loandetail";
        List<LoanDetail> list = new ArrayList<>();
        try (PreparedStatement stmt = con.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                LoanDetail loanDetail = new LoanDetail();
                loanDetail.setSTT(rs.getInt("STT"));
                loanDetail.setLoanId(rs.getString("loanId"));
                loanDetail.setBookId(rs.getString("bookId"));
                loanDetail.setQuantity(rs.getShort("quantity"));
                loanDetail.setLoanDetailID(rs.getString("loanDetailID"));

                list.add(loanDetail);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public LoanDetail layTheoId(int STT) {
        Connection con = KetNoiCSDL.getConnection();
        String query = "SELECT * FROM loandetail WHERE STT = ?";
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setInt(1, STT);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                LoanDetail loanDetail = new LoanDetail();
                loanDetail.setSTT(rs.getInt("STT"));
                loanDetail.setLoanId(rs.getString("loanId"));
                loanDetail.setBookId(rs.getString("bookId"));
                loanDetail.setQuantity(rs.getShort("quantity"));
                loanDetail.setLoanDetailID(rs.getString("loanDetailID"));

                return loanDetail;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
