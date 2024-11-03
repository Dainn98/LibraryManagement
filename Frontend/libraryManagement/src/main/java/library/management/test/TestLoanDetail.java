package library.management.test;

import library.management.DAO.LoanDetailDAO;
import library.management.entity.LoanDetail;

import java.util.List;

public class TestLoanDetail {

    public static void addLoanDetail() {
        LoanDetail loanDetail = new LoanDetail("LOAN1", "BOOK1", (short)3);

        if (LoanDetailDAO.getInstance().them(loanDetail) > 0) {
            System.out.println("Thêm chi tiết khoản vay thành công!");
        } else {
            System.out.println("Thêm chi tiết khoản vay thất bại!");
        }
    }

    public static void deleteLoanDetail() {
        LoanDetail loanDetail = new LoanDetail();
        loanDetail.setLoanDetailID("DETAIL1");

        if (LoanDetailDAO.getInstance().xoa(loanDetail) > 0) {
            System.out.println("Xóa chi tiết khoản vay thành công!");
        } else {
            System.out.println("Xóa chi tiết khoản vay thất bại!");
        }
    }

    public static void updateLoanDetail() {
        LoanDetail loanDetail = new LoanDetail("LOAN2", "BOOK2", (short)5, "DETAIL12");

        if (LoanDetailDAO.getInstance().capNhat(loanDetail) > 0) {
            System.out.println("Cập nhật chi tiết khoản vay thành công!");
        } else {
            System.out.println("Cập nhật chi tiết khoản vay thất bại!");
        }
    }

    public static void getAllLoanDetails() {
        List<LoanDetail> loanDetails = LoanDetailDAO.getInstance().layTatCa();

        if (loanDetails != null && !loanDetails.isEmpty()) {
            System.out.println("Danh sách chi tiết khoản vay:");
            for (LoanDetail loanDetail : loanDetails) {
                System.out.println("STT: " + loanDetail.getSTT() +
                        ", LoanID: " + loanDetail.getLoanId() +
                        ", BookID: " + loanDetail.getBookId() +
                        ", Quantity: " + loanDetail.getQuantity() +
                        ", LoanDetailID: " + loanDetail.getLoanDetailID());
            }
        } else {
            System.out.println("Không có chi tiết khoản vay nào trong cơ sở dữ liệu.");
        }
    }

    public static void main(String[] args) {
        updateLoanDetail();       // Kiểm tra thêm chi tiết khoản vay
    }
}
