package library.management.ui.test;

import library.management.ui.DAO.LoanDAO;
import library.management.ui.entity.Loan;

import java.util.Date;
import java.util.List;

public class TestLoan {

    public static void addLoan() {
        Loan loan = new Loan("USER1", (short)2, 1000.0, new Date(), new Date());

        if (LoanDAO.getInstance().add(loan) > 0) {
            System.out.println("Thêm khoản vay thành công!");
        } else {
            System.out.println("Thêm khoản vay thất bại!");
        }
    }

    public static void deleteLoan() {
        Loan loan = new Loan();
        loan.setLoanID("LOAN12");

        if (LoanDAO.getInstance().delete(loan) > 0) {
            System.out.println("Xóa khoản vay thành công!");
        } else {
            System.out.println("Xóa khoản vay thất bại!");
        }
    }

    public static void updateLoan() {
        Loan loan = new Loan("USER2", (short)3, 1500.0, new Date(), new Date(), "LOAN13");

        if (LoanDAO.getInstance().update(loan) > 0) {
            System.out.println("Cập nhật khoản vay thành công!");
        } else {
            System.out.println("Cập nhật khoản vay thất bại!");
        }
    }

    public static void getAllLoans() {
        List<Loan> loans = LoanDAO.getInstance().layTatCa();

        if (loans != null && !loans.isEmpty()) {
            System.out.println("Danh sách khoản vay:");
            for (Loan loan : loans) {
                System.out.println("STT: " + loan.getSTT() +
                        ", UserID: " + loan.getUserId() +
                        ", Quantity of Borrow: " + loan.getQuantityOfBorrow() +
                        ", Deposit: " + loan.getDeposit() +
                        ", Date of Borrow: " + loan.getDateOfBorrow() +
                        ", Required Return Date: " + loan.getRequiredReturnDate() +
                        ", LoanID: " + loan.getLoanID());
            }
        } else {
            System.out.println("Không có khoản vay nào trong cơ sở dữ liệu.");
        }
    }

    public static void main(String[] args) {
        updateLoan();           // Kiểm tra thêm khoản vay
    }
}
