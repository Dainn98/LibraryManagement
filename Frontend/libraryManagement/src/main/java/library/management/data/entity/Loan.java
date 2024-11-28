package library.management.data.entity;

import library.management.data.DAO.DocumentDAO;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Loan {
    private int loanID;
    private String userName;
    private int documentId;
    private int quantityOfBorrow;
    private double deposit;
    private LocalDateTime dateOfBorrow;
    private LocalDateTime requiredReturnDate;
    private LocalDateTime returnDate;
    private String status;
    private Document document;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final double LATE_FEE = 0.5;

    public Loan() {
        super();
        setDocument();
    }

    public Loan(String userName, int documentId, int quantityOfBorrow, double deposit) {
        this.userName = userName;
        this.documentId = documentId;
        this.quantityOfBorrow = quantityOfBorrow;
        this.deposit = deposit;
        this.dateOfBorrow = LocalDateTime.now();
        this.requiredReturnDate = this.dateOfBorrow.plusDays(30);
        this.status = "borrowing";
        setDocument();
    }

    public Document getDocument() {
        setDocument();
        return this.document;
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

    public String getDateOfBorrowAsString() {
        return dateOfBorrow.format(formatter);
    }

    public String getRequiredReturnDateAsString() {
        return requiredReturnDate.format(formatter);
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

    public void setDocument() {
        if (document == null) {
            this.document = DocumentDAO.getInstance().searchDocumentById(this.documentId);
        }
    }

    public String getISBN() {
        if (document != null) {
            return document.getIsbn();
        } else {
            setDocument();
            if (document != null) {
                return document.getIsbn();
            } else {
                return "N/A";
            }
        }
    }

    public String getTitle() {
        if (document != null) {
            return document.getTitle();
        } else {
            setDocument();
            if (document != null) {
                return document.getTitle();
            } else {
                return "N/A";
            }
        }
    }

    public String getAuthor() {
        if (document != null) {
            return document.getAuthor();
        } else {
            setDocument();
            if (document != null) {
                return document.getAuthor();
            } else {
                return "N/A";
            }
        }
    }

    public String getPublisher() {
        if (document != null) {
            return document.getPublisher();
        } else {
            setDocument();
            if (document != null) {
                return document.getPublisher();
            } else {
                return "N/A";
            }
        }
    }

    public String getCategory() {
        if (document != null) {
            return document.getCategory();
        } else {
            setDocument();
            if (document != null) {
                return document.getCategory();
            } else {
                return "N/A";
            }
        }
    }
}
