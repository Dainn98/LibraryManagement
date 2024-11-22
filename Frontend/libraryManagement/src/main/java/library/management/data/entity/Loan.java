package library.management.data.entity;

import library.management.data.DAO.DocumentDAO;
import library.management.data.DAO.UserDAO;

import java.time.LocalDateTime;

public class Loan {
    private int loanID;
    private String userName; // Replaced userId with userName as primary identifier
    private int documentId;
    private int quantityOfBorrow;
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

    public Loan(String userName, int documentId, int quantityOfBorrow, double deposit) {
        this.userName = userName;
        this.documentId = documentId;
        this.quantityOfBorrow = quantityOfBorrow;
        this.deposit = deposit;
        this.dateOfBorrow = LocalDateTime.now();
        this.requiredReturnDate = this.dateOfBorrow.plusDays(30);
        this.status = "borrowing";
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public int getQuantityOfBorrow() {
        return quantityOfBorrow;
    }

    public void setQuantityOfBorrow(int quantityOfBorrow) {
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

    public String getUserNameFromDAO() {
        return UserDAO.getInstance().searchApprovedUserByName(this.userName).stream()
                .findFirst()
                .map(User::getUserName)
                .orElse("Unknown User");
    }

    @Override
    public String toString() {
        return String.format("Loan ID: %s, User Name: %s, Document ID: %s, Quantity Borrowed: %d, Deposit: %.2f, Required Return Date: %s, Status: %s",
                getLoanID(),
                getUserName(),
                getDocumentId(),
                getQuantityOfBorrow(),
                getDeposit(),
                getRequiredReturnDate() != null ? getRequiredReturnDate().toString() : "N/A",
                getStatus());
    }

}
