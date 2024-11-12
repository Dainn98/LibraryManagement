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

            stmt.setInt(1, loanDetail.getIntLoanId());
            stmt.setInt(2, loanDetail.getIntDocumentID());
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

            stmt.setInt(1, loanDetail.getIntLoanDetailID());
            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int update(LoanDetail loanDetail) {
        String query = "UPDATE loandetail SET loanId = ?, documentID = ?, quantity = ? WHERE loanDetailID = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setInt(1, loanDetail.getIntLoanId());
            stmt.setInt(2, loanDetail.getIntDocumentID());
            stmt.setShort(3, loanDetail.getQuantity());
            stmt.setInt(4, loanDetail.getIntLoanDetailID());

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
