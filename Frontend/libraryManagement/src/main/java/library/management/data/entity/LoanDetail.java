package library.management.data.entity;

public class LoanDetail {
    private int STT;
    private String loanId;
    private String documentID;
    private short quantity;
    private String loanDetailID;

    // Constructors
    public LoanDetail() {}

    public LoanDetail(String loanId, String documentID, short quantity, String loanDetailID) {
        this.loanId = loanId;
        this.documentID = documentID;
        this.quantity = quantity;
        this.loanDetailID = loanDetailID;
    }

    public LoanDetail(String loanId, String documentID, short quantity) {
        this.documentID = documentID;
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

    public String getDocumentID() {
        return documentID;
    }

    public void setDocumentID(String documentID) {
        this.documentID = documentID;
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
