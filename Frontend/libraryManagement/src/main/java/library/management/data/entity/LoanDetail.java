package library.management.data.entity;

public class LoanDetail {
    private int loanDetailID;
    private int loanId;
    private int documentID;
    private short quantity;

    // Constructors
    public LoanDetail() {}

    public LoanDetail(String loanId, String documentID, short quantity, String loanDetailID) {
        this.loanId = parseId(loanId, "LOAN");
        this.documentID = parseId(documentID, "DOC");
        this.quantity = quantity;
        this.loanDetailID = Integer.parseInt(loanDetailID.substring(6));
    }

    public LoanDetail(String loanId, String documentID, short quantity) {
        this.documentID = parseId(documentID, "DOC");
        this.loanId = parseId(loanId, "LOAN");
        this.quantity = quantity;
    }

    public LoanDetail(String loanDetailID) {
        this.loanDetailID = parseId(loanDetailID, "DETAIL");
    }

    public String getStringLoanId() {
        return String.format("LOAN%d", loanId);
    }

    public int getIntLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = parseId(loanId, "LOAN");
    }

    public String getStringDocumentID() {
        return String.format("DOC%d", documentID);
    }

    public int getIntDocumentID() {
        return documentID;
    }

    public void setDocumentID(String documentID) {
        this.documentID = parseId(documentID, "DOC");
    }

    public short getQuantity() {
        return quantity;
    }

    public void setQuantity(short quantity) {
        this.quantity = quantity;
    }

    public String getStringLoanDetailID() {
        return String.format("DETAIL%d", loanDetailID);
    }

    public int getIntLoanDetailID() {
        return loanDetailID;
    }

    private int parseId(String input, String prefix) {
        if (input != null && input.startsWith(prefix)) {
            return Integer.parseInt(input.substring(prefix.length()));
        } else {
            throw new IllegalArgumentException("Invalid format for ID with prefix " + prefix);
        }
    }

    public void setLoanDetailID(String loanDetailID) {
        this.loanDetailID = parseId(loanDetailID, "DETAIL");
    }
}
