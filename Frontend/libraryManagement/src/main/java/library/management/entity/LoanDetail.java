package library.management.entity;

public class LoanDetail {
    private int STT;
    private String loanId;
    private String bookId;
    private short quantity;
    private String loanDetailID;

    // Constructors
    public LoanDetail() {}

    public LoanDetail(String loanId, String bookId, short quantity, String loanDetailID) {
        this.loanId = loanId;
        this.bookId = bookId;
        this.quantity = quantity;
        this.loanDetailID = loanDetailID;
    }

    public LoanDetail(String loanId, String bookId, short quantity) {
        this.bookId = bookId;
        this.loanId = loanId;
        this.quantity = quantity;
    }

    public LoanDetail(String loanDetailID) {
        this.loanDetailID = loanDetailID;
    }

    // Getters and Setters
    public int getSTT() {
        return STT;
    }

    public void setSTT(int STT) {
        this.STT = STT;
    }

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public short getQuantity() {
        return quantity;
    }

    public void setQuantity(short quantity) {
        this.quantity = quantity;
    }

    public String getLoanDetailID() {
        return loanDetailID;
    }

    public void setLoanDetailID(String loanDetailID) {
        this.loanDetailID = loanDetailID;
    }
}
