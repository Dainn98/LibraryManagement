package controller;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javafx.event.ActionEvent;
import library.management.data.DAO.LoanDAO;
import library.management.data.DAO.UserDAO;
import library.management.data.entity.Manager;
import library.management.data.entity.User;
import library.management.service.AuthService;
import library.management.ui.controllers.ModernLoginController;
import library.management.ui.controllers.manager.PendingApprovalsController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SignInTest {

  private ActionEvent actionEvent;
  private Manager manager;
  private User user;
  private ModernLoginController modernLoginController;
  private AuthService authService;
  private String username;
  private String password;
  private PendingApprovalsController pendingApprovalsController;
//  TextField usernameField = new TextField("username");
//  TextField passwordField = new TextField("password");

  @BeforeEach
  void setUp() {
    LoanDAO.getInstance().updateLateLoans();

    manager = new Manager();
    user = new User();
    modernLoginController = new ModernLoginController();
    actionEvent = new ActionEvent();
    authService = new AuthService();
//    usernameField = new TextField("username");
//    passwordField = new TextField("password");
//    modernLoginController.setLoginUsername(usernameField);
//    modernLoginController.setLoginPassword(passwordField);
    modernLoginController.setAuthService(authService);

  }

  @Test
  void testLoginAsManagerSuccess() {
    username = "tuan anh";
    password = "123";

    Manager manager = new Manager();
    manager.setName(username);
    manager.setPassword(password);

    assertTrue(authService.signIn(manager.getName(), manager.getPassword()) instanceof Manager);
  }

  @Test
  void testLoginAsManagerFailureVer1() {
    username = "tuan an";
    password = "123";

    Manager manager = new Manager();
    manager.setName(username);
    manager.setPassword(password);

    assertFalse(authService.signIn(manager.getName(), manager.getPassword()) instanceof Manager);
  }

  @Test
  void testLoginAsManagerFailureVer2() {
    username = "tuan anh";
    password = "12345";

    Manager manager = new Manager();
    manager.setName(username);
    manager.setPassword(password);

    assertFalse(authService.signIn(manager.getName(), manager.getPassword()) instanceof Manager);
  }

  @Test
  void testLoginAsUserSuccess() {
    username = "test05";
    password = "test";

    User user = new User();
    user.setName(username);
    user.setPassword(password);
    assertTrue(authService.signIn(user.getName(), user.getPassword()) instanceof User);
  }

  @Test
  void testApproveUserAndUserLogin() {

  }

}