import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import library.management.data.entity.Loan;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LoanTest {

  private Loan loan;
  private Loan loan1;
  private Loan loan2;
  private Loan loan3;
  private Loan loan4;

  @BeforeEach
  public void setUp() {
    loan1 = new Loan("TuấnAnh", 1, 1, 50.0);
    loan2 = new Loan("Pham", 2, 2, 100.0);
    loan3 = new Loan("Dainn98", 3, 3, 150.0);
    loan4 = new Loan("Student2005", 4, 4, 200.0);
    loan = new Loan("user2", 10, 5, 200.0);

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

  @Test
  public void testLoansCreation() {
    assertNotNull(loan1);
    assertNotNull(loan2);
    assertNotNull(loan3);
    assertNotNull(loan4);

    assertEquals("TuấnAnh", loan1.getUserName());
    assertEquals("Pham", loan2.getUserName());
    assertEquals("Dainn98", loan3.getUserName());
    assertEquals("Student2005", loan4.getUserName());
  }

  @Test
  public void testLoanIDsParsing() {
    loan1.setLoanID("LOAN001");
    loan2.setLoanID("LOAN002");
    loan3.setLoanID("LOAN003");
    loan4.setLoanID("LOAN004");

    assertEquals(1, loan1.getIntLoanID());
    assertEquals(2, loan2.getIntLoanID());
    assertEquals(3, loan3.getIntLoanID());
    assertEquals(4, loan4.getIntLoanID());
  }

  @Test
  public void testDepositsAndQuantities() {
    assertEquals(50.0, loan1.getDeposit());
    assertEquals(100.0, loan2.getDeposit());
    assertEquals(150.0, loan3.getDeposit());
    assertEquals(200.0, loan4.getDeposit());

    assertEquals(1, loan1.getQuantityOfBorrow());
    assertEquals(2, loan2.getQuantityOfBorrow());
    assertEquals(3, loan3.getQuantityOfBorrow());
    assertEquals(4, loan4.getQuantityOfBorrow());
  }

  @Test
  public void testLoanDatesConsistency() {
    LocalDateTime borrow1 = loan1.getDateOfBorrow();
    LocalDateTime borrow2 = loan2.getDateOfBorrow();
    LocalDateTime borrow3 = loan3.getDateOfBorrow();
    LocalDateTime borrow4 = loan4.getDateOfBorrow();

    assertTrue(loan1.getRequiredReturnDate().isAfter(borrow1));
    assertTrue(loan2.getRequiredReturnDate().isAfter(borrow2));
    assertTrue(loan3.getRequiredReturnDate().isAfter(borrow3));
    assertTrue(loan4.getRequiredReturnDate().isAfter(borrow4));
  }

  @Test
  public void testSetQuantityOfBorrow() {
    loan.setQuantityOfBorrow(3);
    assertEquals(3, loan.getQuantityOfBorrow(), "Quantity of borrow should be updated to 3");
  }

  @Test
  public void testSetDepositZero() {
    loan.setDeposit(0.0);
    assertEquals(0.0, loan.getDeposit(), "Deposit can be zero");
  }

  @Test
  public void testSetNegativeDeposit() {
    loan.setDeposit(-50.0);
    assertEquals(-50.0, loan.getDeposit(), "Deposit can accept negative value (no validation)");
  }

  @Test
  public void testLoanIDNullInitially() {
    assertNull(loan.getLoanID(), "Loan ID should be null initially");
  }

  @Test
  public void testStatusDefault() {
    Loan newLoan = new Loan("user3", 5, 1, 50.0);
    assertEquals("borrowing", newLoan.getStatus(), "Default status should be 'borrowing'");
  }

  @Test
  public void testRequiredReturnDateAfterBorrowDate() {
    LocalDateTime borrow = loan.getDateOfBorrow();
    LocalDateTime returnDate = loan.getRequiredReturnDate();
    assertTrue(returnDate.isAfter(borrow), "Required return date must be after borrow date");
  }

  @Test
  public void testSetLoanIDInvalidFormat() {
    loan.setLoanID("INVALID");
    assertEquals(0, loan.getIntLoanID(), "Invalid Loan ID should parse to 0");
  }

  @Test
  public void testMultipleLoansUniqueBorrowDate() {
    Loan loan2 = new Loan("user4", 11, 1, 30.0);
    assertTrue(loan2.getDateOfBorrow().isAfter(loan.getDateOfBorrow().minusSeconds(1)),
        "New loan borrow date should be after previous loan borrow date");
  }

  @Test
  public void testToStringContainsAllFields() {
    loan.setLoanID("LOAN200");
    String str = loan.toString();
    assertTrue(str.contains("LOAN200"));
    assertTrue(str.contains("user2"));
    assertTrue(str.contains("borrowing"));
    assertTrue(str.contains("Quantity: 5"));
    assertTrue(str.contains("Deposit: 200.0"));
  }
}
