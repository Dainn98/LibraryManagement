package library.management.data.entity;

import java.util.Date;

public class Loan {
    private int STT;
    private String userId;
    private short quantityOfBorrow;
    private double deposit;
    private Date dateOfBorrow;
    private Date requiredReturnDate;
    private String loanID;

    // Constructors
    public Loan() {}

    public Loan(String userId, short quantityOfBorrow, double deposit, Date dateOfBorrow, Date requiredReturnDate, String loanID) {
        this.userId = userId;
        this.quantityOfBorrow = quantityOfBorrow;
        this.deposit = deposit;
        this.dateOfBorrow = dateOfBorrow;
        this.requiredReturnDate = requiredReturnDate;
        this.loanID = loanID;
    }

    public Loan(String userId, short quantityOfBorrow, double deposit, Date dateOfBorrow, Date requiredReturnDate) {
        this.userId = userId;
        this.quantityOfBorrow = quantityOfBorrow;
        this.deposit = deposit;
        this.dateOfBorrow = dateOfBorrow;
        this.requiredReturnDate = requiredReturnDate;
    }

    public Loan(String loanID) {
        this.loanID = loanID;
    }

    // Getters and Setters
    public int getSTT() {
        return STT;
    }

    public void setSTT(int STT) {
        this.STT = STT;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public short getQuantityOfBorrow() {
        return quantityOfBorrow;
    }

    public void setQuantityOfBorrow(short quantityOfBorrow) {
        this.quantityOfBorrow = quantityOfBorrow;
    }

    public double getDeposit() {
        return deposit;
    }

    public void setDeposit(double deposit) {
        this.deposit = deposit;
    }

    public Date getDateOfBorrow() {
        return dateOfBorrow;
    }

    public void setDateOfBorrow(Date dateOfBorrow) {
        this.dateOfBorrow = dateOfBorrow;
    }

    public Date getRequiredReturnDate() {
        return requiredReturnDate;
    }

    public void setRequiredReturnDate(Date requiredReturnDate) {
        this.requiredReturnDate = requiredReturnDate;
    }

    public String getLoanID() {
        return loanID;
    }

    public void setLoanID(String loanID) {
        this.loanID = loanID;
    }
}
