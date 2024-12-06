package library.management.ui.controllers;

import static library.management.alert.AlertMaker.showAlertInformation;
import static library.management.ui.controllers.SettingsController.checkPassWord;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import library.management.UserScreen;
import library.management.data.DAO.LoanDAO;
import library.management.data.DAO.ManagerDAO;
import library.management.data.DAO.UserDAO;
import library.management.data.entity.Manager;
import library.management.data.entity.User;
import library.management.main;

public class ModernLoginController extends GeneralController implements Initializable {

  private static final int WIDTH = 780;
  private static final double rightTrans = (double) WIDTH / 2;
  private static final double leftTrans = -(double) WIDTH / 2;

  @FXML
  protected Pane transPane;
  @FXML
  protected Pane registerSwitch;
  @FXML
  protected Pane loginSwitch;
  @FXML
  protected Pane loginPane;
  @FXML
  protected Pane registerPane;
  @FXML
  protected TextField loginUsername;
  @FXML
  protected TextField loginPassword;
  @FXML
  protected TextField registerUsername;
  @FXML
  protected TextField registerEmail;
  @FXML
  protected TextField registerIdentityCard;
  @FXML
  protected TextField registerPassword;
  @FXML
  protected TextField confirmPassword;

  public void handleLogin(ActionEvent actionEvent) {
    LoanDAO.getInstance().updateLateLoans();
    String userName = loginUsername.getText();
    String password = loginPassword.getText();
    Manager mainManager = ManagerDAO.getInstance().checkManager(userName, password);
    if (mainManager != null) {
      Stage loginStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
      loginStage.close();
      main mainApp = new main();
      mainApp.setManager(mainManager);
      Stage mainStage = new Stage();
      mainApp.start(mainStage);
      return;
    }
    User mainUser = UserDAO.getInstance().checkUserLogin(userName, password);
    if (mainUser != null) {
      Stage loginStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
      loginStage.close();
      UserScreen userScreen = new UserScreen();
      userScreen.setUser(mainUser);
      Stage mainStage = new Stage();
      userScreen.start(mainStage);
    } else {
      showAlertInformation("Login Failed", "Invalid username or password. Please try again.");
    }
  }

  public void handleRegister(ActionEvent actionEvent) {
    String userName = registerUsername.getText();
    String userEmail = registerEmail.getText();
    String userIdentityCard = registerIdentityCard.getText();
    String userPassword = registerPassword.getText();
    String confPassword = confirmPassword.getText();

    if (UserDAO.getInstance().doesUserNameExist(userName)) {
      showAlertInformation("Sign Up Failed", "Username is already exist.");
      return;
    }
    if (UserDAO.getInstance().doesIdentityCardExist(userIdentityCard)) {
      showAlertInformation("Sign Up Failed", "IdentityCard already used by another user.");
      return;
    }
    if (UserDAO.getInstance().doesEmailExist(userEmail)) {
      showAlertInformation("Sign Up Failed", "Email is already used by another user.");
      return;
    }
    if (!validateInputs(userName, userEmail, userIdentityCard, userPassword, confPassword)) {
      return;
    }
    if (UserDAO.getInstance().add(new User(userName, userIdentityCard, userEmail, userPassword))
        < 1) {
      showAlertInformation("Sign Up Failed", "Something went wrong, please try again.");
      return;
    }
    showAlertInformation("Sign Up Successful", "Your account is pending manager's approval");
    showLoginForm(actionEvent);
  }

  public void showRegisterForm(ActionEvent ignoredActionEvent) {
    registerPane.setVisible(true);
    loginSwitch.setVisible(true);
    transFade(registerPane, rightTrans, 0, 1, Duration.seconds(0.5));
    transFade(loginPane, rightTrans, 1, 0, Duration.seconds(0.5));
    transFade(loginSwitch, rightTrans, 0.5, 1, Duration.seconds(0.5));
    transFade(registerSwitch, rightTrans, 0.5, 0, Duration.seconds(0.5));
    transFade(transPane, leftTrans, 1, 1, Duration.seconds(0.5));
  }

  public void showLoginForm(ActionEvent ignoredActionEvent) {
    loginPane.setVisible(true);
    registerSwitch.setVisible(true);
    transFade(registerPane, leftTrans, 1, 0, Duration.seconds(0.5));
    transFade(registerSwitch, leftTrans, 0, 1, Duration.seconds(0.5));
    transFade(loginPane, leftTrans, 0.5, 1, Duration.seconds(0.5));
    transFade(loginSwitch, leftTrans, 0.5, 0, Duration.seconds(0.5));
    transFade(transPane, rightTrans, 1, 1, Duration.seconds(0.5));
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    loginPane.setVisible(true);
    registerSwitch.setVisible(true);
    registerPane.setVisible(false);
    loginSwitch.setVisible(false);

    registerPane.setLayoutX(0);
    loginPane.setLayoutX(0);

    loginSwitch.setLayoutX((double) -WIDTH / 2);
    registerSwitch.setLayoutX((double) WIDTH / 2);

    loginUsername.setOnKeyPressed(e -> {
      if (e.getCode().toString().equals("ENTER")) {
        handleLogin(new ActionEvent(loginUsername, null));
      }
    });

    loginPassword.setOnKeyPressed(e -> {
      if (e.getCode().toString().equals("ENTER")) {
        handleLogin(new ActionEvent(loginPassword, null));
      }
    });

    registerUsername.setOnKeyPressed(e -> {
      if (e.getCode().toString().equals("ENTER")) {
        handleRegister(new ActionEvent(registerUsername, null));
      }
    });

    registerEmail.setOnKeyPressed(e -> {
      if (e.getCode().toString().equals("ENTER")) {
        handleRegister(new ActionEvent(registerEmail, null));
      }
    });

    registerIdentityCard.setOnKeyPressed(e -> {
      if (e.getCode().toString().equals("ENTER")) {
        handleRegister(new ActionEvent(registerIdentityCard, null));
      }
    });

    registerPassword.setOnKeyPressed(e -> {
      if (e.getCode().toString().equals("ENTER")) {
        handleRegister(new ActionEvent(registerPassword, null));
      }
    });

    confirmPassword.setOnKeyPressed(e -> {
      if (e.getCode().toString().equals("ENTER")) {
        handleRegister(new ActionEvent(confirmPassword, null));
      }
    });
  }

  /**
   * Validates all input fields.
   *
   * @param username        the username of the user
   * @param email           the email of the user
   * @param password        the password of the user
   * @param confirmPassword the confirmation password
   * @return true if all inputs are valid, false otherwise
   */
  private boolean validateInputs(String username, String email, String identityCard,
      String password, String confirmPassword) {
    if (username.isEmpty() || email.isEmpty()
        || password.isEmpty() || confirmPassword.isEmpty()) {
      showAlertInformation("Sign Up Failed", "All fields are required.");
      return false;
    }
    if (username.length() < 3 || username.length() > 20) {
      showAlertInformation("Sign Up Failed", "Username must be between 3 and 20 characters.");
      return false;
    }
    if (!isValidEmail(email)) {
      showAlertInformation("Sign Up Failed", "Invalid email format.");
      return false;
    }
    if (!isValidIdentityCard(identityCard)) {
      showAlertInformation("Sign Up Failed", "IdentityCard does not match.");
      return false;
    }
    if (!isValidPassword(password)) {
      return false;
    }
    if (password.length() < 6) {
      showAlertInformation("Sign Up Failed", "Password must be at least 6 characters.");
      return false;
    }
    if (!password.equals(confirmPassword)) {
      showAlertInformation("Sign Up Failed", "Passwords do not match.");
      return false;
    }
    return true;
  }

  private boolean isValidIdentityCard(String identityCard) {
    if (identityCard == null || identityCard.isEmpty()) {
      showAlertInformation("Invalid Identity Card", "Identity card cannot be empty.");
      return false;
    }
    if (identityCard.length() < 9 || identityCard.length() > 12) {
      showAlertInformation("Invalid Identity Card",
          "Identity card must be between 9 and 12 characters long.");
      return false;
    }
    if (!identityCard.matches("[0-9]+")) {
      showAlertInformation("Invalid Identity Card",
          "Identity card must contain only numeric digits.");
      return false;
    }
    return true;
  }

  /**
   * Validates the email format.
   */
  private boolean isValidEmail(String email) {
    String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(gmail\\.com|vnu\\.edu\\.vn)$";
    Pattern pattern = Pattern.compile(emailRegex);
    return pattern.matcher(email).matches();
  }

  /**
   * Validates the password format.
   */
  private boolean isValidPassword(String password) {
    return checkPassWord(password);
  }

}
