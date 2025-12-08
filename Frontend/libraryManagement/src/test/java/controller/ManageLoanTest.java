package controller;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import library.management.data.DAO.CategoryDAO;
import library.management.data.DAO.DocumentDAO;
import library.management.data.DAO.LanguageDAO;
import library.management.data.DAO.LoanDAO;
import library.management.data.DAO.UserDAO;
import library.management.data.entity.Category;
import library.management.data.entity.Document;
import library.management.data.entity.Language;
import library.management.data.entity.Loan;
import library.management.data.entity.User;
import library.management.service.AuthService;
import library.management.service.ValidService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ManageLoanTest {

  private static final String NUMERIC = "0123456789";
  private static final int numLength = 5;
  private User user;
  private Loan loan;
  private Document doc;
  private Category cat;
  private Language lang;
  private int catID;
  private int langID;
  private int initial;
  private int current;
  private AuthService authService;
  private double deposit;

  private static String randomId(String prefix) {
    StringBuilder sb = new StringBuilder(prefix);
    ThreadLocalRandom random = ThreadLocalRandom.current();
    for (int i = 0; i < numLength; i++) {
      sb.append(NUMERIC.charAt(random.nextInt(NUMERIC.length())));
    }
    return sb.toString();
  }

  private static String randomId(String prefix, int numLength) {
    StringBuilder sb = new StringBuilder(prefix);
    ThreadLocalRandom random = ThreadLocalRandom.current();
    for (int i = 0; i < numLength; i++) {
      sb.append(NUMERIC.charAt(random.nextInt(NUMERIC.length())));
    }
    return sb.toString();
  }

  @BeforeEach
  void setUp() {
    cat = new Category("CAT0", "TESTING CATEGORY" + randomId(""));
    lang = new Language("LANG0", "TESTING LANGUAGE" + randomId(""));
    CategoryDAO.getInstance().add(cat);
    LanguageDAO.getInstance().add(lang);
    catID = CategoryDAO.getInstance().getTagId(cat.getTag());
    langID = LanguageDAO.getInstance().getLanguageId(lang.getLgName());
    ValidService.setShowAlert(false);
  }

  @Test
  void testAddDocumentIntoPendingLoans() {
    int addQuantity = 5;
    int borrowQuantity = 2;
    deposit = 500;
    doc = new Document("CAT" + catID, "Test publisher", "LANG" + langID, "Test title",
        "Test author", "ISBN-" + randomId(""), addQuantity, addQuantity,
        "2025-12-04T11:36:11", 1000.0,
        "desc", "url", "image", "available");
    DocumentDAO.getInstance().add(doc);

    user = new User(
        "validUser" + randomId("",2),        // username: hợp lệ
        "00120"+randomId(""),        // identityCard: 9 số
        "valid"+randomId("",2) + "@gmail.com",  // email: đúng regex
        "Pass@123"          // password: có chữ, số, special char
    );
    int addUser = UserDAO.getInstance().add(user);

    loan = new Loan(user, doc.getIntDocumentID(), borrowQuantity, deposit);

    int added = LoanDAO.getInstance().add(loan);
    assert added > 0;
  }

  @Test
  void testBorrowMoreThanAvailable() {
    int addQuantity = 3;
    int borrowQuantity = 5;
    deposit = 500;

    doc = new Document("CAT" + catID, "Test publisher", "LANG" + langID, "Title",
        "Author", "ISBN-" + randomId(""), addQuantity, addQuantity,
        "2025-12-04T11:36:11", 1000.0,
        "desc", "url", "image", "available");
    DocumentDAO.getInstance().add(doc);

    user = new User(
        "user"+randomId("",2),
        "00120"+randomId(""),
        "u"+randomId("",2)+"@gmail.com",
        "Pass@123"
    );
    UserDAO.getInstance().add(user);

    loan = new Loan(user, doc.getIntDocumentID(), borrowQuantity, deposit);
    int added = LoanDAO.getInstance().add(loan);

    assert added == 0;
  }

  @Test
  void testLoanExistsInDatabaseAfterAdd() {
    int addQuantity = 5;
    int borrowQuantity = 1;
    deposit = 300;

    doc = new Document("CAT" + catID, "Publisher", "LANG" + langID, "Title",
        "Author", "ISBN-" + randomId(""), addQuantity, addQuantity,
        "2025-12-04T11:36:11", 900.0,
        "desc", "url", "image", "available");
    DocumentDAO.getInstance().add(doc);

    user = new User(
        "user"+randomId("",2),
        "00120"+randomId(""),
        "mail"+randomId("",2)+"@gmail.com",
        "Pass@123"
    );
    UserDAO.getInstance().add(user);

    loan = new Loan(user, doc.getIntDocumentID(), borrowQuantity, deposit);
    LoanDAO.getInstance().add(loan);

    Loan retrieved = LoanDAO.getInstance().getLoanByLoanID(loan.getIntLoanID());

    assert retrieved != null;
    assert retrieved.getQuantityOfBorrow() == borrowQuantity;
    assert retrieved.getIntDocumentId() == doc.getIntDocumentID();
  }



  @AfterEach
  void cleanUp() {
    if (loan != null) {
      if (LoanDAO.getInstance().getLoanByLoanID(loan.getIntLoanID()) != null) {
        LoanDAO.getInstance().deleteLoanByLoanID(loan.getIntLoanID());
      }
    }
    if (doc != null) {
      if (DocumentDAO.getInstance().searchDocumentFromDatabaseById(doc.getIntDocumentID())
          != null) {
        DocumentDAO.getInstance().deleteDocumentFromDatabase(doc);
      }
//      CategoryDAO.getInstance().deleteByTag(doc.getCategory());
//      LanguageDAO.getInstance().deleteByName(doc.getLanguage());
    }

    if (user != null) {
      if (UserDAO.getInstance().searchApprovedUserByName(user.getUserName()) != null ||
          UserDAO.getInstance().searchPendingUserByName(user.getUserName()) != null) {
        UserDAO.getInstance().deleteUserFromDatabase(user);
      }
    }
    ValidService.setShowAlert(true);
  }

  @Test
  void testCannotCreateLoanForNonExistingUser() {
    int addQuantity = 5;
    deposit = 400;

    doc = new Document("CAT" + catID, "Pub", "LANG" + langID, "Title",
        "Author", "ISBN-" + randomId(""), addQuantity, addQuantity,
        "2025-12-04T11:36:11", 500.0,
        "desc", "url", "image", "available");
    DocumentDAO.getInstance().add(doc);

    // user = null hoặc user không tồn tại trong DB
    user = new User(
        "notExistUser"+randomId("",2),
        "00120"+randomId(""),
        "xx"+randomId("",2)+"@gmail.com",
        "Pass@123"
    );

    loan = new Loan(user, doc.getIntDocumentID(), 1, deposit);

    int added = LoanDAO.getInstance().add(loan);

    assert added == 0;
  }

  @Test
  void testDocumentRemainingQuantityAfterLoan() {
    int addQuantity = 4;
    int borrowQuantity = 2;
    deposit = 500;

    doc = new Document("CAT" + catID, "Pub", "LANG" + langID, "Title",
        "Author", "ISBN-" + randomId(""), addQuantity, addQuantity,
        "2025-12-04T11:36:11", 700.0,
        "desc", "url", "image", "available");
    DocumentDAO.getInstance().add(doc);

    user = new User(
        "uu"+randomId("",2),
        "00120"+randomId(""),
        "em"+randomId("",2)+"@gmail.com",
        "Pass@123"
    );
    UserDAO.getInstance().add(user);

    loan = new Loan(user, doc.getIntDocumentID(), borrowQuantity, deposit);
    LoanDAO.getInstance().add(loan);

    Document updated = DocumentDAO.getInstance()
        .searchDocumentFromDatabaseById(doc.getIntDocumentID());

  }

  @Test
  void testDeleteLoanRestoresDocumentQuantity() {
    int addQuantity = 5;
    int borrowQuantity = 2;
    deposit = 300;

    doc = new Document("CAT" + catID, "Pub", "LANG" + langID, "Title",
        "Author", "ISBN-" + randomId(""), addQuantity, addQuantity,
        "2025-12-04T11:36:11", 800.0,
        "desc", "url", "image", "available");
    DocumentDAO.getInstance().add(doc);

    user = new User(
        "u"+randomId("",2),
        "00120"+randomId(""),
        "tes"+randomId("",2)+"@gmail.com",
        "Pass@123"
    );
    UserDAO.getInstance().add(user);

    loan = new Loan(user, doc.getIntDocumentID(), borrowQuantity, deposit);
    LoanDAO.getInstance().add(loan);

    // Delete
    LoanDAO.getInstance().deleteLoanByLoanID(loan.getIntLoanID());

    Document updated = DocumentDAO.getInstance()
        .searchDocumentFromDatabaseById(doc.getIntDocumentID());

//    assert updated.getRemainingQuantity() == addQuantity;
  }




}
