package library.management.data.test;

import library.management.data.DAO.LoanDetailDAO;
import library.management.data.entity.LoanDetail;

import java.util.List;

public class TestLoanDetail {

    public static void addLoanDetail() {
        LoanDetail loanDetail = new LoanDetail("LOAN1", "DOC1", (short)3);

        if (LoanDetailDAO.getInstance().add(loanDetail) > 0) {
            System.out.println("Thêm chi tiết khoản vay thành công!");
        } else {
            System.out.println("Thêm chi tiết khoản vay thất bại!");
        }
    }

    public static void deleteLoanDetail() {
        LoanDetail loanDetail = new LoanDetail();
        loanDetail.setLoanDetailID("DETAIL11");

        if (LoanDetailDAO.getInstance().delete(loanDetail) > 0) {
            System.out.println("Xóa chi tiết khoản vay thành công!");
        } else {
            System.out.println("Xóa chi tiết khoản vay thất bại!");
        }
    }

    public static void updateLoanDetail() {
        LoanDetail loanDetail = new LoanDetail("LOAN2", "DOC2", (short)5, "DETAIL11");

        if (LoanDetailDAO.getInstance().update(loanDetail) > 0) {
            System.out.println("Cập nhật chi tiết khoản vay thành công!");
        } else {
            System.out.println("Cập nhật chi tiết khoản vay thất bại!");
        }
    }

    public static void main(String[] args) {
        addLoanDetail();
        updateLoanDetail();
        deleteLoanDetail();
    }
}
