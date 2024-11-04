package library.management.data.DAO;

import library.management.data.database.DatabaseConnection;
import library.management.data.entity.LoanDetail;

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

    // Thêm một chi tiết khoản vay vào cơ sở dữ liệu
    @Override
    public int add(LoanDetail loanDetail) {
        String query = "INSERT INTO loandetail (loanId, documentID, quantity) VALUES (?, ?, ?)";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, loanDetail.getLoanId());
            stmt.setString(2, loanDetail.getDocumentID());
            stmt.setShort(3, loanDetail.getQuantity());

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Xóa một chi tiết khoản vay khỏi cơ sở dữ liệu
    @Override
    public int delete(LoanDetail loanDetail) {
        String query = "DELETE FROM loandetail WHERE loanDetailID = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, loanDetail.getLoanDetailID());
            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Cập nhật thông tin của một chi tiết khoản vay trong cơ sở dữ liệu
    @Override
    public int update(LoanDetail loanDetail) {
        String query = "UPDATE loandetail SET loanId = ?, documentID = ?, quantity = ? WHERE loanDetailID = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, loanDetail.getLoanId());
            stmt.setString(2, loanDetail.getDocumentID());
            stmt.setShort(3, loanDetail.getQuantity());
            stmt.setString(4, loanDetail.getLoanDetailID());

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Lấy danh sách tất cả các chi tiết khoản vay
    public List<LoanDetail> layTatCa() {
        String query = "SELECT * FROM loandetail";
        List<LoanDetail> list = new ArrayList<>();
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                LoanDetail loanDetail = new LoanDetail();
                loanDetail.setSTT(rs.getInt("STT"));
                loanDetail.setLoanId(rs.getString("loanId"));
                loanDetail.setDocumentID(rs.getString("documentID"));
                loanDetail.setQuantity(rs.getShort("quantity"));
                loanDetail.setLoanDetailID(rs.getString("loanDetailID"));

                list.add(loanDetail);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Lấy thông tin chi tiết khoản vay theo STT
    public LoanDetail layTheoId(int STT) {
        String query = "SELECT * FROM loandetail WHERE STT = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setInt(1, STT);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    LoanDetail loanDetail = new LoanDetail();
                    loanDetail.setSTT(rs.getInt("STT"));
                    loanDetail.setLoanId(rs.getString("loanId"));
                    loanDetail.setDocumentID(rs.getString("documentID"));
                    loanDetail.setQuantity(rs.getShort("quantity"));
                    loanDetail.setLoanDetailID(rs.getString("loanDetailID"));

                    return loanDetail;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
