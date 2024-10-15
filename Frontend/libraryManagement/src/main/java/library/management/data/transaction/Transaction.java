package library.management.data.transaction;

import library.management.data.models.Documents.Document;
import library.management.data.models.User.Member;
import java.util.Date;

/**
 * Represents a transaction in the library management system.
 */
public class Transaction {

    private TransactionType transactionType;
    private String transactionId;
    private Document document;
    private Member member;
    private Date transactionDate;

    /**
     * Constructs a new Transaction with the specified details.
     *
     * @param transactionType the type of the transaction
     * @param transactionId the ID of the transaction
     * @param document the document involved in the transaction
     * @param member the member involved in the transaction
     * @param transactionDate the date of the transaction
     */
    public Transaction(TransactionType transactionType, String transactionId, Document document, Member member, Date transactionDate) {
        this.transactionType = transactionType;
        this.transactionId = transactionId;
        this.document = document;
        this.member = member;
        this.transactionDate = transactionDate;
    }

    /**
     * Gets the type of the transaction.
     *
     * @return the type of the transaction
     */
    public TransactionType getTransactionType() {
        return transactionType;
    }

    /**
     * Sets the type of the transaction.
     *
     * @param transactionType the new type of the transaction
     */
    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    /**
     * Gets the ID of the transaction.
     *
     * @return the ID of the transaction
     */
    public String getTransactionId() {
        return transactionId;
    }

    /**
     * Sets the ID of the transaction.
     *
     * @param transactionId the new ID of the transaction
     */
    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    /**
     * Gets the document involved in the transaction.
     *
     * @return the document involved in the transaction
     */
    public Document getDocument() {
        return document;
    }

    /**
     * Sets the document involved in the transaction.
     *
     * @param document the new document involved in the transaction
     */
    public void setDocument(Document document) {
        this.document = document;
    }

    /**
     * Gets the member involved in the transaction.
     *
     * @return the member involved in the transaction
     */
    public Member getMember() {
        return member;
    }

    /**
     * Sets the member involved in the transaction.
     *
     * @param member the new member involved in the transaction
     */
    public void setMember(Member member) {
        this.member = member;
    }

    /**
     * Gets the date of the transaction.
     *
     * @return the date of the transaction
     */
    public Date getTransactionDate() {
        return transactionDate;
    }

    /**
     * Sets the date of the transaction.
     *
     * @param transactionDate the new date of the transaction
     */
    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }
}