package controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
import library.management.ui.controllers.manager.DocumentController;
import library.management.ui.controllers.manager.MainController;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StatisticsTest {

  private static final String NUMERIC = "0123456789";
  private static final int numLength = 5;
  private int docQuantity;
  private int remainingDocQuantity;
  private int totalStudent;
  private int approvedStudent;
  private int studentHoldingBook;
  private Document doc;
  private User user;
  private Category cat;
  private Language lang;
  private DocumentController docController;
  private MainController mainController;
  private AuthService authService;
  private int catID;
  private int langID;
  private int initial;
  private int current;

  private static String randomId(String prefix) {
    StringBuilder sb = new StringBuilder(prefix);
    ThreadLocalRandom random = ThreadLocalRandom.current();
    for (int i = 0; i < numLength; i++) {
      sb.append(NUMERIC.charAt(random.nextInt(NUMERIC.length())));
    }
    return sb.toString();
  }


  @BeforeEach
  void setUp() {
    docQuantity = DocumentDAO.getInstance().getTotalQuantity();
    remainingDocQuantity = DocumentDAO.getInstance().getTotalAvailableCopies();
    totalStudent = UserDAO.getInstance().getAllUsersCount();
    approvedStudent = UserDAO.getInstance().getApprovedUsersCount();
    studentHoldingBook = LoanDAO.getInstance().getTotalUsersWhoBorrowedBooks();

    mainController = new MainController();
    docController = new DocumentController(mainController);
    authService = new AuthService();

    cat = new Category("CAT0", "TESTING CATEGORY" + randomId(""));
    lang = new Language("LANG0", "TESTING LANGUAGE" + randomId(""));
    CategoryDAO.getInstance().add(cat);
    LanguageDAO.getInstance().add(lang);
    catID = CategoryDAO.getInstance().getTagId(cat.getTag());
    langID = LanguageDAO.getInstance().getLanguageId(lang.getLgName());

    ValidService.setShowAlert(false);
  }


  @Test
  void testBasicValidity() {
    assertTrue(docQuantity >= 0);
    assertTrue(remainingDocQuantity >= 0 && remainingDocQuantity <= docQuantity);
    assertTrue(totalStudent >= 0);
    assertTrue(approvedStudent >= 0 && approvedStudent <= totalStudent);
    assertTrue(studentHoldingBook >= 0 && studentHoldingBook <= totalStudent);
  }

  @Test
  void testIssuedDocuments() {
    int issued = docQuantity - remainingDocQuantity;
    assertTrue(issued >= 0 && issued <= docQuantity);
  }

  @Test
  void testPendingUsers() {
    int pending = totalStudent - approvedStudent;
    assertTrue(pending >= 0 && pending <= totalStudent);
  }

  @Test
  void testConsistency() {
    int issued = docQuantity - remainingDocQuantity;
    int pending = totalStudent - approvedStudent;
    assertTrue(issued + remainingDocQuantity == docQuantity);
    assertTrue(pending + approvedStudent == totalStudent);
  }

  @Test
  void testAddDocumentIncreasesQuantity() {
    int initialDocQuantity = docQuantity;
    int copiesToAdd = 5;
    doc = new Document("CAT" + catID, "Test publisher", "LANG" + langID, "Test title",
        "Test author", "ISBN-" + randomId(""), copiesToAdd, copiesToAdd, "2025-12-04T11:36:11",
        120000.00,
        "Test description...", "Test url", "Test image", "available");
    DocumentDAO.getInstance().add(doc);
    docQuantity = DocumentDAO.getInstance().getTotalQuantity();
    assertEquals(docQuantity, initialDocQuantity + copiesToAdd);
  }

  @Test
  void testDeleteDocumentDecreasesQuantity() {
    int copiesToAdd = 4;
    doc = new Document("CAT" + catID, "Test publisher", "LANG" + langID, "Test title",
        "Test author", "ISBN-" + randomId(""), copiesToAdd, copiesToAdd, "2025-12-04T11:36:11",
        120000.00,
        "Test description...", "Test url", "Test image", "available");
    DocumentDAO.getInstance().add(doc);
    int initialDocQuantity = DocumentDAO.getInstance().getTotalQuantity();

    DocumentDAO.getInstance().delete(doc);
    docQuantity = DocumentDAO.getInstance().getTotalQuantity();
    assertEquals(docQuantity, initialDocQuantity - doc.getQuantity());
  }

  @Test
  void testAddUserIncreasePendingApprovals(){
    initial = UserDAO.getInstance().getPendingUsers().size();

    user = new User(
        "validUser",        // username: hợp lệ
        "098706951",        // identityCard: 9 số
        "valid@gmail.com",  // email: đúng regex
        "Pass@123"          // password: có chữ, số, special char
    );

    int added = UserDAO.getInstance().add(user);
    assertTrue(added > 0, "User should be added successfully");
    current = UserDAO.getInstance().getPendingUsers().size();
    assertEquals(current, initial + 1);
  }

  @Test
  void testDeleteUserDecreasePendingApprovals(){
    initial = UserDAO.getInstance().getPendingUsers().size();

    user = new User(
        "validUser",        // username: hợp lệ
        "098706951",        // identityCard: 9 số
        "valid@gmail.com",  // email: đúng regex
        "Pass@123"          // password: có chữ, số, special char
    );

    int added = UserDAO.getInstance().add(user);
    assertTrue(added > 0, "User should be added successfully");
    current = UserDAO.getInstance().getPendingUsers().size();
    assertEquals(current, initial + 1);

    int deleted = UserDAO.getInstance().deleteUserFromDatabase(user);
    assertTrue(deleted > 0, "User should be deleted successfully");
    current = UserDAO.getInstance().getPendingUsers().size();
    assertEquals(current, initial);
  }

  @Test
  void testApproveUserIncreaseApprovedUser(){
    initial = UserDAO.getInstance().getApprovedUsersCount();
    user = new User(
        "validUser",        // username: hợp lệ
        "098706951",        // identityCard: 9 số
        "valid@gmail.com",  // email: đúng regex
        "Pass@123"          // password: có chữ, số, special char
    );

    int added = UserDAO.getInstance().add(user);
    current = UserDAO.getInstance().getApprovedUsersCount();
    int anotherInitial = UserDAO.getInstance().getPendingUsers().size();

    assertEquals(current, initial);
    assertTrue(added > 0, "User should be added before approval");
    int updated = UserDAO.getInstance().updateUserStatus(user, "approved");
    assertTrue(updated > 0, "User status should be updated");
    assertNotNull(authService.signIn(user.getUserName(), user.getPassword()));
    current = UserDAO.getInstance().getApprovedUsersCount();
    assertEquals(current, initial + 1);

    current = UserDAO.getInstance().getPendingUsers().size();
    assertEquals(current, anotherInitial-1);
  }

  @Test
  void testDisapproveUserDecreaseTotalUser(){
    user = new User(
        "validUser",        // username: hợp lệ
        "098706951",        // identityCard: 9 số
        "valid@gmail.com",  // email: đúng regex
        "Pass@123"          // password: có chữ, số, special char
    );

    int added = UserDAO.getInstance().add(user);
    assertTrue(added > 0);

    initial = UserDAO.getInstance().getAllUsersCount();
    int updated = UserDAO.getInstance().updateUserStatus(user,"disapproved");
    assertTrue(updated > 0, "User status should be updated");
    assertNull(authService.signIn(user.getUserName(), user.getPassword()));
    current = UserDAO.getInstance().getAllUsersCount();
    assertEquals(current + 1, initial);
  }

  @Test
  void testGetTotalUsersCountMatchesApprovedPlusPending() {
    int total = UserDAO.getInstance().getAllUsersCount();
    int approved = UserDAO.getInstance().getApprovedUsersCount();
    int pending = UserDAO.getInstance().getPendingUsers().size();
    assertEquals(total, approved + pending);
  }

  @Test
  void testNewUserIsPendingNotApproved() {
    int pendingBefore = UserDAO.getInstance().getPendingUsers().size();
    int approvedBefore = UserDAO.getInstance().getApprovedUsersCount();

    user = new User("validUser", "098706951", "valid@gmail.com", "Pass@123");
    int added = UserDAO.getInstance().add(user);
    assertTrue(added > 0);

    int pendingAfter = UserDAO.getInstance().getPendingUsers().size();
    int approvedAfter = UserDAO.getInstance().getApprovedUsersCount();

    assertEquals(pendingBefore + 1, pendingAfter);
    assertEquals(approvedBefore, approvedAfter);
  }

  @Test
  void testDisapprovedUserNotCountedAsPendingOrApproved() {
    user = new User("validUser", "098706951", "valid@gmail.com", "Pass@123");
    int added = UserDAO.getInstance().add(user);
    assertTrue(added > 0);

    int pendingBefore = UserDAO.getInstance().getPendingUsers().size();
    int approvedBefore = UserDAO.getInstance().getApprovedUsersCount();

    int updated = UserDAO.getInstance().updateUserStatus(user, "disapproved");
    assertTrue(updated > 0);

    int pendingAfter = UserDAO.getInstance().getPendingUsers().size();
    int approvedAfter = UserDAO.getInstance().getApprovedUsersCount();

    assertEquals(pendingAfter, pendingBefore - 1);
    assertEquals(approvedAfter, approvedBefore);
  }

  @Test
  void testApproveThenDisapproveDecreaseApprovedCount() {
    user = new User("validUser", "098706951", "valid@gmail.com", "Pass@123");
    int added = UserDAO.getInstance().add(user);
    assertTrue(added > 0);

    int approvedBefore = UserDAO.getInstance().getApprovedUsersCount();
    int pendingBefore = UserDAO.getInstance().getPendingUsers().size();

    int updated1 = UserDAO.getInstance().updateUserStatus(user, "approved");
    assertTrue(updated1 > 0);

    int approvedMid = UserDAO.getInstance().getApprovedUsersCount();
    int pendingMid = UserDAO.getInstance().getPendingUsers().size();

    assertEquals(approvedMid, approvedBefore + 1);
    assertEquals(pendingMid, pendingBefore - 1);

    int updated2 = UserDAO.getInstance().updateUserStatus(user, "disapproved");
    assertTrue(updated2 > 0);

    int approvedAfter = UserDAO.getInstance().getApprovedUsersCount();

    assertEquals(approvedAfter, approvedBefore);
  }


  @Test
  void testAddDocumentDoesNotChangeAvailableCopiesIncorrectly() {
    int beforeTotal = DocumentDAO.getInstance().getTotalQuantity();
    int beforeAvailable = DocumentDAO.getInstance().getTotalAvailableCopies();

    doc = new Document("CAT" + catID, "Test publisher", "LANG" + langID, "Test title",
        "Test author", "ISBN-" + randomId(""), 3, 3,
        "2025-12-04T11:36:11", 1000.0,
        "desc", "url", "image", "available");

    int added = DocumentDAO.getInstance().add(doc);
    assertTrue(added > 0);

    int afterTotal = DocumentDAO.getInstance().getTotalQuantity();
    int afterAvailable = DocumentDAO.getInstance().getTotalAvailableCopies();

    assertEquals(beforeTotal + 3, afterTotal);
    assertEquals(beforeAvailable + 3, afterAvailable);
  }




  @AfterEach
  void cleanUp() {
    if (doc != null){
      if (DocumentDAO.getInstance().searchDocumentFromDatabaseById(doc.getIntDocumentID()) != null) {
        DocumentDAO.getInstance().deleteDocumentFromDatabase(doc);
      }
      CategoryDAO.getInstance().deleteByTag(doc.getCategory());
      LanguageDAO.getInstance().deleteByName(doc.getLanguage());
    }

    if (user != null){
      if (UserDAO.getInstance().searchApprovedUserByName(user.getUserName()) != null||
          UserDAO.getInstance().searchPendingUserByName(user.getUserName()) != null) {
        UserDAO.getInstance().deleteUserFromDatabase(user);
      }
    }
    ValidService.setShowAlert(true);
  }

}
