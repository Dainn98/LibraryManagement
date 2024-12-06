import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import library.management.data.entity.Loan;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LoanTest {

  private Loan loan;

  @BeforeEach
  public void setUp() {
    loan = new Loan("user1", 1, 2, 100.0);
  }

  @Test
  public void testLoanConstructor() {
    assertNotNull(loan, "Loan object should be created.");
    assertEquals("user1", loan.getUserName(), "User name should be 'user1'.");
    assertEquals(1, loan.getIntDocumentId(), "Document ID should be 1.");
    assertEquals(2, loan.getQuantityOfBorrow(), "Quantity of borrow should be 2.");
    assertEquals(100.0, loan.getDeposit(), "Deposit should be 100.0.");
    assertEquals("borrowing", loan.getStatus(), "Loan status should be 'borrowing'.");
    assertNotNull(loan.getDateOfBorrow(), "Date of borrow should not be null.");
    assertNotNull(loan.getRequiredReturnDate(), "Required return date should not be null.");
  }

  @Test
  public void testSetAndGetStatus() {
    loan.setStatus("returned");
    assertEquals("returned", loan.getStatus(), "Status should be 'returned'.");
  }

  @Test
  public void testGetDocumentId() {
    assertEquals("DOC1", loan.getDocumentId(), "Document ID should be formatted as 'DOC1'.");
  }

  @Test
  public void testGetRequiredReturnDateAsString() {
    LocalDateTime requiredReturnDate = loan.getRequiredReturnDate();
    assertNotNull(requiredReturnDate, "Required return date should not be null.");
    assertTrue(requiredReturnDate.isAfter(loan.getDateOfBorrow()),
        "Required return date should be after borrow date.");
  }

  @Test
  public void testSetAndGetLoanID() {
    loan.setLoanID("LOAN123");
    assertEquals(123, loan.getIntLoanID(), "Loan ID should be 123.");
  }

  @Test
  public void testGetDocumentInfoWhenDocumentNotFound() {
    loan.setDocument();
    assertEquals("N/A", loan.getISBN(), "ISBN should be 'N/A' when document is not found.");
    assertEquals("N/A", loan.getTitle(), "Title should be 'N/A' when document is not found.");
    assertEquals("N/A", loan.getAuthor(), "Author should be 'N/A' when document is not found.");
    assertEquals("N/A", loan.getPublisher(),
        "Publisher should be 'N/A' when document is not found.");
    assertEquals("N/A", loan.getCategory(), "Category should be 'N/A' when document is not found.");
  }

  @Test
  public void testLoanToString() {
    loan.setStatus("returned");
    String loanString = loan.toString();
    assertTrue(loanString.contains("Loan ID: LOAN0"), "toString should contain the loan ID.");
    assertTrue(loanString.contains("User Name: user1"), "toString should contain the user name.");
    assertTrue(loanString.contains("Status: returned"), "toString should contain the status.");
  }
}
