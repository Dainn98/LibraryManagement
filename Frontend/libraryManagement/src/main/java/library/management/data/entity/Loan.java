package library.management.data.entity;

import java.util.Date;

public class Loan {
    private int loanID;
    private int userId; //
    private int documentId;
    private short quantityOfBorrow;
    private double deposit;
    private Date dateOfBorrow;
    private Date requiredReturnDate;
    private String status;

    // Constructors
    public Loan() {
    }

    public Loan(String userId, String documentId, short quantityOfBorrow, double deposit, Date dateOfBorrow, Date requiredReturnDate, String loanID, String status) {
        this.userId = Integer.parseInt(userId.substring(4));
        this.documentId = Integer.parseInt(documentId.substring(3));
        this.quantityOfBorrow = quantityOfBorrow;
        this.deposit = deposit;
        this.dateOfBorrow = dateOfBorrow;
        this.requiredReturnDate = requiredReturnDate;
        this.loanID = Integer.parseInt(loanID.substring(4));
        this.status = status;
    }

    public Loan(String userId, String documentId, short quantityOfBorrow, double deposit, Date dateOfBorrow, Date requiredReturnDate) {
        this.userId = Integer.parseInt(userId.substring(4));
        this.documentId = Integer.parseInt(documentId.substring(3));
        this.quantityOfBorrow = quantityOfBorrow;
        this.deposit = deposit;
        this.dateOfBorrow = dateOfBorrow;
        this.requiredReturnDate = requiredReturnDate;
    }

    public Loan(String loanID) {
        this.loanID = Integer.parseInt(loanID.substring(4));
    }

    // Getter và Setter
    public String getStringUserId() {
        return String.format("USER%d", this.userId);
    }

    public int getIntUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = Integer.parseInt(userId.substring(4));
    }

    public String getStringDocumentId() {
        return String.format("DOC%d", this.documentId);
    }

    public int getIntDocumentId() {
        return this.documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = Integer.parseInt(documentId.substring(3));
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

    public String getStringLoanID() {
        return String.format("LOAN%d", this.loanID);
    }

    public int getIntLoanID() {
        return this.loanID;
    }

    public void setLoanID(String loanID) {
        this.loanID = Integer.parseInt(loanID.substring(4));
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
