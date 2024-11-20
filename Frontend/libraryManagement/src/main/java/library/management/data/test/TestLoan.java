package library.management.data.test;

import library.management.data.DAO.LoanDAO;
import library.management.data.entity.Loan;

import java.util.Date;
import java.util.List;

public class TestLoan {

    public static void addLoan() {
        Loan loan = new Loan("USER1", (short) 2, 1000.0, new Date(), new Date());

        if (LoanDAO.getInstance().add(loan) > 0) {

            System.out.println("Thêm khoản vay thành công!");
        } else {
            System.out.println("Thêm khoản vay thất bại!");
        }
    }

    public static void deleteLoan() {
        Loan loan = new Loan();
        loan.setLoanID("LOAN11");

        if (LoanDAO.getInstance().delete(loan) > 0) {

            System.out.println("Xóa khoản vay thành công!");
        } else {
            System.out.println("Xóa khoản vay thất bại!");
        }
    }

    public static void updateLoan() {
        Loan loan = new Loan("USER2", (short) 3, 1500.0, new Date(), new Date(), "LOAN11");

        if (LoanDAO.getInstance().update(loan) > 0) {
            System.out.println("Cập nhật khoản vay thành công!");
        } else {
            System.out.println("Cập nhật khoản vay thất bại!");
        }
    }

    public static void main(String[] args) {
//        addLoan();
//        updateLoan();
//        deleteLoan();
    }
}
