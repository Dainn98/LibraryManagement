package library.management.data.entity;

import library.management.data.DAO.DocumentDAO;
import library.management.data.DAO.UserDAO;

import java.time.LocalDateTime;

public class Loan {
    private int loanID;
    private int userId;
    private int documentId;
    private short quantityOfBorrow;
    private double deposit;
    private LocalDateTime dateOfBorrow;
    private LocalDateTime requiredReturnDate;
    private LocalDateTime returnDate;
    private String status;

    public Loan() {
    }

    public Loan(String loanID) {
        this.loanID = Integer.parseInt(loanID.substring(4));
    }

    public String getUserId() {
        return String.format("USER%d", this.userId);
    }

    public int getIntUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = Integer.parseInt(userId.substring(4));
    }

    public String getDocumentId() {
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

    public LocalDateTime getDateOfBorrow() {
        return dateOfBorrow;
    }

    public void setDateOfBorrow(LocalDateTime dateOfBorrow) {
        this.dateOfBorrow = dateOfBorrow;
    }

    public LocalDateTime getRequiredReturnDate() {
        return requiredReturnDate;
    }

    public void setRequiredReturnDate(LocalDateTime requiredReturnDate) {
        this.requiredReturnDate = requiredReturnDate;
    }

    public LocalDateTime getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDateTime returnDate) {
        this.returnDate = returnDate;
    }

    public String getLoanID() {
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

    public String getIssuedISBN() {
        return DocumentDAO.getInstance().searchDocumentById(this.documentId).getIsbn();
    }

    public String getIssuedTitle() {
        return DocumentDAO.getInstance().searchDocumentById(this.documentId).getTitle();
    }

    public String getUserName() {
        return UserDAO.getInstance().searchUserByID(this.userId).getUserName();
    }
}
