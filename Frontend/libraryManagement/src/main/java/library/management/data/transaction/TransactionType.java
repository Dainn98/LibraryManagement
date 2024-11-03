package library.management.data.transaction;

/**
 * Represents the type of transaction that can be performed in the library management system.
 */
public enum TransactionType {
  ISSUE(1),
  RETURN(2),
  RENEW(3),
  RESERVE(4),
  CANCEL_RESERVE(5),
  ADD_MEMBER(6),
  REMOVE_MEMBER(7),
  UPDATE_MEMBER(8);

  private final int code;

  /**
   * Constructor.
   */
  TransactionType(int code) {
    this.code = code;
  }

  /**
   * Gets the code of the transaction type.
   */
  public int getCode() {
    return code;
  }
}
