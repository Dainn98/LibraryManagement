package controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.List;
import javafx.embed.swing.JFXPanel;
import library.management.data.DAO.UserDAO;
import library.management.data.entity.Loan;
import library.management.data.entity.User;
import library.management.service.AuthService;
import library.management.service.ValidService;
import library.management.ui.controllers.manager.MainController;
import library.management.ui.controllers.manager.PendingApprovalsController;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

class PendingApprovalsTest {

  private static JFXPanel fxPanel; // Để khởi tạo JavaFX
//  private MainController mainController;
//  private PendingApprovalsController pendingApprovalsController;
  private User pendingUser;
  private AuthService authService;
  private String username;
  private String password;

//  @BeforeAll
//  static void initToolkit() {
//    fxPanel = new JFXPanel();
//  }

  @BeforeEach
  void setUp() {
    authService = new AuthService();
//    mainController = new MainController();
//    pendingApprovalsController = new PendingApprovalsController(mainController);
    pendingUser = new User(
        "validUser",        // username: hợp lệ
        "098706951",        // identityCard: 9 số
        "valid@gmail.com",  // email: đúng regex
        "Pass@123"          // password: có chữ, số, special char
    );
    username = pendingUser.getUserName();
    password = pendingUser.getPassword();
    ValidService.setShowAlert(false);

  }

  @Test
  @Order(1)
  void testAddPendingUser() {
    // Thêm user
    int added = UserDAO.getInstance().add(pendingUser);
    assertTrue(added > 0, "User should be added successfully");
    assertNull(authService.signIn(pendingUser.getUserName(), pendingUser.getPassword()));
  }


  @Test
  @Order(2)
  void testApproveUser() {
    int added = UserDAO.getInstance().add(pendingUser);
    assertTrue(added > 0, "User should be added before approval");
    int updated = UserDAO.getInstance().updateUserStatus(pendingUser, "approved");
    assertTrue(updated > 0, "User status should be updated");
    assertNotNull(authService.signIn(pendingUser.getUserName(), pendingUser.getPassword()));
  }

  @Test
  @Order(3)
  void testPreventDeleteUserWithBorrowedBooks() {
    int docId = 1; // Giả sử documentId 1 tồn tại trong DB
    int quantity = 1;
    int deposit = 100;
    Loan loan = new Loan(pendingUser,docId, quantity, deposit);
    // Giả lập user đang mượn sách
    pendingUser.setStatus("borrowed"); // giả lập trạng thái đang mượn
    UserDAO.getInstance().updateUserStatus(pendingUser, "borrowed");

    // Không thể xóa user đang mượn
    int deleted = UserDAO.getInstance().deleteUserFromDatabase(pendingUser);
    assertEquals(0, deleted, "Cannot delete user with borrowed books");
  }

  @Test
  @Order(4)
  void testDeleteApprovedUser() {
    // Chuyển trạng thái về approved và xóa
    UserDAO.getInstance().add(pendingUser);
    UserDAO.getInstance().updateUserStatus(pendingUser, "approved");
    int deleted = UserDAO.getInstance().deleteUserFromDatabase(pendingUser);
    assertTrue(deleted > 0, "Approved user should be deletable");

    // Kiểm tra không còn trong DB
    List<User> users = UserDAO.getInstance().getPendingUsers();
    assertFalse(users.stream().anyMatch(u -> u.getUserName().equals(pendingUser.getUserName())));
  }

  @Test
  @Order(5)
  void testInvalidEmailAndPhoneFormat() {
    pendingUser.setEmail("invalidEmail");
    pendingUser.setPassword("1234"); // quá ngắn

    int added = UserDAO.getInstance().add(pendingUser);
    assertEquals(0, added, "User with invalid email or phone should not be added");
  }


  @Test
  @Order(6)
  void testDuplicateIdentityOrEmail() {
    UserDAO.getInstance().add(pendingUser);
    User duplicateUser = new User("anotherUser", pendingUser.getIdentityCard(),
        "0987654321", pendingUser.getEmail(), "pass", "VN", "HN", LocalDateTime.now());

    int added = UserDAO.getInstance().add(duplicateUser);
    assertEquals(0, added, "Duplicate identityCard or email should fail");
  }


  @Test
  @Order(7)
  void testApprovePendingUser() {
    UserDAO.getInstance().add(pendingUser);
    username = pendingUser.getUserName();
    password = pendingUser.getPassword();
    assertFalse(authService.signIn(username, password) instanceof User);
    System.out.println((UserDAO.getInstance().updateUserStatus(pendingUser, "approved")));

    assertTrue(authService.signIn(username, password) instanceof User);
  }

  @Test
  @Order(8)
  void testValidEmailFormat() {
    pendingUser.setEmail("tuananh@gmail.com");
    int added = UserDAO.getInstance().add(pendingUser);
    assertEquals(1, added, "User with valid email and phone should be added");
  }

  @Test
  @Order(8)
  void testInvalidEmailFormat() {
    pendingUser.setEmail("tuananh@gmail.co");
    int added = UserDAO.getInstance().add(pendingUser);
    assertEquals(0, added, "User with valid email should be added");
  }

  @Test
  void testValidEmailAndPasswordFormat() {
    pendingUser.setEmail("tuananh@gmail.com");
    pendingUser.setPassword("Passwo@123");
    int added = UserDAO.getInstance().add(pendingUser);
    assertEquals(1, added, "User with valid email or phone should be added");
  }

  @Test
  void testInvalidEmailAndPasswordFormatVer1() {
    pendingUser.setEmail("tuananh@gmail.com");
    pendingUser.setPassword("123");
    int added = UserDAO.getInstance().add(pendingUser);
    assertEquals(0, added, "User with invalid email or password should not be added");
  }

  @Test
  void testInvalidEmailAndPasswordFormatVer2() {
    pendingUser.setEmail("tuananh@gmail.com");
    pendingUser.setPassword("Pass123");
    int added = UserDAO.getInstance().add(pendingUser);
    assertEquals(0, added, "User with invalid email or password should not be added");
  }

  @Test
  void testInvalidEmailAndPasswordFormatVer3() {
    pendingUser.setEmail("tuananh@gmail.com");
    pendingUser.setPassword("pass#123");
    int added = UserDAO.getInstance().add(pendingUser);
    assertEquals(0, added, "User with invalid email or password should not be added");
  }

  @Test
  void testInvalidEmailAndPhoneFormatVer2() {
    pendingUser.setEmail("tuananh@gmail.co");
    pendingUser.setPhoneNumber("password123");

    int added = UserDAO.getInstance().add(pendingUser);
    assertEquals(0, added, "User with invalid email or phone should not be added");
  }

  @AfterEach
  void cleanUp() {
    // Xóa pendingUser nếu còn tồn tại
    ValidService.setShowAlert(true);
    UserDAO.getInstance().deleteUserFromDatabase(pendingUser);
  }

}

