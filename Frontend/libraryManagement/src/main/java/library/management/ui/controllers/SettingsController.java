package library.management.ui.controllers;

import static library.management.alert.AlertMaker.showAlertInformation;

import java.util.regex.Pattern;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.SVGPath;
import library.management.data.DAO.ManagerDAO;
import library.management.data.DAO.UserDAO;
import library.management.data.entity.Manager;
import library.management.data.entity.User;
import library.management.ui.applications.EmailSender;
import library.management.ui.applications.SecurityCodeGenerator;
import library.management.ui.controllers.manager.MainController;
import library.management.ui.controllers.user.FullUserController;
import org.controlsfx.control.textfield.CustomPasswordField;

public class SettingsController {

  /**
   * The length of the security code used for verification.
   */
  private final int CODE_LENGTH = 6;

  /**
   * Identifier for manager settings type.
   */
  private final int MANAGER_SETTING = 100;
  @FXML
  private Label nameTitle;
  @FXML
  private SVGPath sendCode;
  @FXML
  private TextField phoneNumberTextField;
  @FXML
  private CustomPasswordField confirmPasswordTextField;
  @FXML
  private CustomPasswordField newPasswordTextField;
  @FXML
  private CustomPasswordField currentPassword;
  @FXML
  private TextField secondCode;
  @FXML
  private TextField Phone;
  @FXML
  private BorderPane profileBPane;
  @FXML
  private BorderPane signInOptionsBPane;
  @FXML
  private BorderPane privacyPolicyBPane;
  @FXML
  private TextField email;
  @FXML
  private Label identityCard;
  @FXML
  private TextField name;
  @FXML
  private TextField code;
  @FXML
  private TextField emailTextField;
  private MainController mainController;
  private Manager manager;
  private User user;
  private String securityCode = "Invalid Code";
  private boolean canChange = false;
  private boolean canChangeSecurity = false;
  private int type;

  /**
   * Validates if a password meets security requirements.
   *
   * @param password the password to validate
   * @return {@code true} if the password meets all requirements, otherwise {@code false}
   */
  static boolean checkPassWord(String password) {
    if (password.length() < 6) {
      showAlertInformation("Invalid Password",
          "The password must be at least six characters long.");
      return false;
    }
    if (!password.matches(".*[a-zA-Z].*")) {
      showAlertInformation("Invalid Password",
          "The password must contain at least one alphabetic character.");
      return false;
    }
    if (!password.matches(".*[0-9].*")) {
      showAlertInformation("Invalid Password",
          "The password must contain at least one numeric digit.");
      return false;
    }
    if (!password.matches(".*[!@#$%^&*()_+\\-\\[\\]{};':\"\\\\|,.<>/?].*")) {
      showAlertInformation("Invalid Password",
          "The password must contain at least one special character.");
      return false;
    }
    return true;
  }

  /**
   * Sets the MainController instance for this controller. Used when the settings are for a
   * manager.
   *
   * @param mainController the main controller instance
   */
  public void setMainController(MainController mainController) {
    type = MANAGER_SETTING;
    this.mainController = mainController;
    email.setDisable(true);
    Phone.setDisable(true);
    name.setDisable(true);
  }

  /**
   * Sets the FullUserController instance for this controller. Used when the settings are for a
   * user.
   *
   * @param controller the FullUserController instance
   */
  public void setFullUserControllerController(FullUserController controller) {
    type = 200;
    email.setDisable(true);
    Phone.setDisable(true);
    name.setDisable(true);
  }

  /**
   * Resets the displayed information to the current user's or manager's data.
   */
  public void resetInformation() {
    String identityCard;
    String email;
    String phone;
    if (this.type == MANAGER_SETTING) {
      identityCard = manager.getIdentityCard();
      email = manager.getEmail();
      phone = manager.getPhoneNumber();
    } else {
      identityCard = user.getIdentityCard();
      email = user.getEmail();
      phone = user.getPhoneNumber();
    }
    if (identityCard == null || identityCard.isEmpty()) {
      this.identityCard.setText("Not set");
    } else {
      this.identityCard.setText(identityCard);
    }
    if (email == null || email.isEmpty()) {
      this.email.setText("Not set");
    } else {
      this.email.setText(email);
    }
    if (phone == null || phone.isEmpty()) {
      this.Phone.setText("Not set");
    } else {
      this.Phone.setText(phone);
    }
  }

  /**
   * Resets the security code to its initial state.
   */
  public void resetCode() {
    securityCode = "Invalid Code";
  }

  /**
   * Resets the fields for security-related settings.
   */
  public void resetSecurityInformation() {
    if (type == MANAGER_SETTING) {
      currentPassword.setText("");
      newPasswordTextField.setText("");
      confirmPasswordTextField.setText("");
      phoneNumberTextField.setText(manager.getPhoneNumber());
      emailTextField.setText(manager.getEmail());
    } else {
      currentPassword.setText("");
      newPasswordTextField.setText("");
      confirmPasswordTextField.setText("");
      phoneNumberTextField.setText(user.getPhoneNumber());
      emailTextField.setText(user.getEmail());
    }
  }

  /**
   * Displays a specific section in the settings page and hides the others.
   *
   * @param sectionToShow the section to display
   */
  private void showSection(Object sectionToShow) {
    profileBPane.setVisible(sectionToShow == profileBPane);
    signInOptionsBPane.setVisible(sectionToShow == signInOptionsBPane);
    privacyPolicyBPane.setVisible(sectionToShow == privacyPolicyBPane);
  }

  /**
   * Initializes and sets the data for the user or manager.
   */
  public void setData() {
    if (type == MANAGER_SETTING) {
      this.manager = mainController.getMainManager();
      this.name.setText(manager.getManagerName());
      this.nameTitle.setText(manager.getManagerName());
      resetInformation();
    } else {
      this.user = FullUserController.getMainUser();
      this.name.setText(user.getUserName());
      this.nameTitle.setText(user.getUserName());
      resetInformation();
    }
  }

  /**
   * Handles the action of opening the Profile section.
   *
   * @param actionEvent the action event triggered by the user
   */
  @FXML
  private void handleProfileButton(ActionEvent actionEvent) {
    resetInformation();
    showSection(profileBPane);
  }

  /**
   * Handles the action of opening the Sign-In Options section.
   *
   * @param actionEvent the action event triggered by the user
   */
  @FXML
  private void handleSignInOptionsButton(ActionEvent actionEvent) {
    resetSecurityInformation();
    showSection(signInOptionsBPane);
  }

  /**
   * Handles the action of opening the Privacy Policy section.
   *
   * @param actionEvent the action event triggered by the user
   */
  @FXML
  private void handlePrivacyPolicy(ActionEvent actionEvent) {
    showSection(privacyPolicyBPane);
  }

  /**
   * Saves changes to the user's or manager's profile information.
   *
   * @param actionEvent the action event triggered by the user
   */
  @FXML
  private void handleSave(ActionEvent actionEvent) {
    if (canChange) {
      String newPhone = Phone.getText();
      String newEmail = email.getText();
      if (isValidPhoneNumber(newPhone)) {
        showAlertInformation("Error", "Phone number is invalid");
        return;
      }
      if (isValidEmail(newEmail)) {
        showAlertInformation("Error", "Email is invalid");
        return;
      }
      if (type == MANAGER_SETTING) {
        if (!newPhone.equals(manager.getPhoneNumber())) {
          if (ManagerDAO.getInstance().checkManagerByPhoneNumber(newPhone)) {
            showAlertInformation("Error", "Phone number is already in use");
            return;
          }
        }
        if (!newEmail.equals(manager.getEmail())) {
          if (ManagerDAO.getInstance().checkManagerByEmail(newEmail)) {
            showAlertInformation("Error", "Email is already in use");
            return;
          }
        }
        manager.setPhoneNumber(newPhone);
        manager.setEmail(newEmail);
        ManagerDAO.getInstance().update(manager);
      } else {
        if (checkUserRegisterInformation(newPhone, newEmail)) {
          return;
        }
      }
      showAlertInformation("Success!", "Your information has been saved");
      Phone.setDisable(true);
      email.setDisable(true);
      canChange = false;
    } else {
      showAlertInformation("Error", "Please enter correct code");
    }
  }

  /**
   * Validates and checks for conflicts in user registration information.
   *
   * @param newPhone the new phone number to validate
   * @param newEmail the new email to validate
   * @return {@code true} if conflicts are found, otherwise {@code false}
   */
  private boolean checkUserRegisterInformation(String newPhone, String newEmail) {
    if (!newPhone.equals(user.getPhoneNumber())) {
      if (UserDAO.getInstance().doesPhoneNumberExist(newPhone)) {
        showAlertInformation("Error", "Phone number is already in use");
        return true;
      }
    }
    if (!newEmail.equals(user.getEmail())) {
      if (UserDAO.getInstance().doesEmailExist(newEmail)) {
        showAlertInformation("Error", "Email is already in use");
        return true;
      }
    }
    user.setPhoneNumber(newPhone);
    user.setEmail(newEmail);
    UserDAO.getInstance().update(user);
    return false;
  }

  /**
   * Validates if the given email address is in the correct format.
   *
   * @param email the email address to validate
   * @return {@code true} if the email is invalid, otherwise {@code false}
   */
  private boolean isValidEmail(String email) {
    String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(gmail\\.com|vnu\\.edu\\.vn)$";
    Pattern pattern = Pattern.compile(emailRegex);
    return !pattern.matcher(email).matches();
  }

  /**
   * Validates if the given phone number is in the correct format.
   *
   * @param phoneNumber the phone number to validate
   * @return {@code true} if the phone number is invalid, otherwise {@code false}
   */
  private boolean isValidPhoneNumber(String phoneNumber) {
    if (phoneNumber == null || phoneNumber.isEmpty()) {
      return true;
    }
    String phoneNumberPattern = "^(0)(\\d{9})$";

    return !phoneNumber.matches(phoneNumberPattern);
  }

  /**
   * Handles the action of editing profile information.
   *
   * @param actionEvent the action event triggered by the user
   */
  @FXML
  private void handleEditProfile(ActionEvent actionEvent) {
    handleProfileButton(actionEvent);
  }

  /**
   * Handles the action of sending a verification security code via email.
   *
   * @param actionEvent the action event triggered by the user
   */
  @FXML
  private void handleSendCode(ActionEvent actionEvent) {
    sendSecurityCode();
  }

  /**
   * Verifies the entered security code for profile changes.
   *
   * @param keyEvent the key event triggered by the user
   */
  @FXML
  private void handleCheckCode(KeyEvent keyEvent) {
    if (code.getText().length() != CODE_LENGTH) {
      return;
    }
    if (securityCode.equals("Invalid Code") || !code.getText().equals(securityCode)) {
      showAlertInformation("Incorrect code!", "Please check your code and try again.");
      return;
    }
    if (code.getText().equals(securityCode)) {
      showAlertInformation("Correct code!", "You can change your information now.");
      email.setDisable(false);
      Phone.setDisable(false);
      canChange = true;
      resetCode();
    }
  }

  /**
   * Verifies the entered security code for security-related changes.
   *
   * @param keyEvent the key event triggered by the user
   */
  @FXML
  private void handleCheckSecurityCode(KeyEvent keyEvent) {
    if (secondCode.getText().length() != CODE_LENGTH) {
      return;
    }
    if (securityCode.equals("Invalid Code") || !secondCode.getText().equals(securityCode)) {
      showAlertInformation("Incorrect code!", "Please check your code and try again.");
      return;
    }
    if (secondCode.getText().equals(securityCode)) {
      showAlertInformation("Correct code!", "You can change your information now.");
      canChangeSecurity = true;
      resetCode();
    }
  }

  public void handleMouseEnter(MouseEvent mouseEvent) {
    sendSecurityCode();
  }

  /**
   * Sends a verification security code to the user's or manager's email.
   */
  private void sendSecurityCode() {
    if (type == MANAGER_SETTING) {
      if (manager.getEmail().equals("Not set")) {
        showAlertInformation("Error", "Email not available.");
        return;
      }
    } else {
      if (user.getEmail().equals("Not set")) {
        showAlertInformation("Error", "Email not available.");
        return;
      }
    }
    securityCode = SecurityCodeGenerator.generateCode(CODE_LENGTH);
    Task<Void> sendEmail = new Task<>() {
      @Override
      protected Void call() throws Exception {
        EmailSender.sendEmail(email.getText(), "Library Management System",
            "Thank you to use our application. To edit your information, please enter the verification securityCode on the setting screen."
                +
                "\nVerification Code: " + securityCode + "." +
                "\nPlease note that this securityCode can only be used once.");
        return null;
      }
    };
    Thread thread = new Thread(sendEmail);
    thread.setDaemon(true);
    thread.start();
    showAlertInformation("Sending code.", "Your code is going to be sent.");
    sendEmail.setOnSucceeded(e -> {
      showAlertInformation("Send code successfully", "Please check your email.");
    });
  }

  /**
   * Handles changes to security details like password, phone number, and email.
   *
   * @param actionEvent the action event triggered by the user
   */
  @FXML
  private void handleChangeSecurity(ActionEvent actionEvent) {
    if (canChangeSecurity) {
      if (type == MANAGER_SETTING) {
        if (ManagerDAO.getInstance()
            .checkManager(manager.getManagerName(), currentPassword.getText()) == null) {
          showAlertInformation("Error", "Password is incorrect");
          return;
        }
        if (!newPasswordTextField.getText().isEmpty() || !confirmPasswordTextField.getText()
            .isEmpty()) {
          if (isValidPassword(newPasswordTextField.getText())) {
            showAlertInformation("Error", "Password is invalid");
            return;
          }
          if (!newPasswordTextField.getText().equals(confirmPasswordTextField.getText())) {
            showAlertInformation("Error", "Confirm password is not match");
            return;
          }
          manager.setPassword(newPasswordTextField.getText());
        }
        if (isValidPhoneNumber(phoneNumberTextField.getText())) {
          showAlertInformation("Error", "Phone number is invalid");
          return;
        }
        if (isValidEmail(emailTextField.getText())) {
          showAlertInformation("Error", "Email is invalid");
          return;
        }
        String newPhone = phoneNumberTextField.getText();
        String newEmail = emailTextField.getText();
        if (!newPhone.equals(manager.getPhoneNumber())) {
          if (ManagerDAO.getInstance().checkManagerByPhoneNumber(newPhone)) {
            showAlertInformation("Error", "Phone number is already in use");
            return;
          }
        }
        if (!newEmail.equals(manager.getEmail())) {
          if (ManagerDAO.getInstance().checkManagerByEmail(newEmail)) {
            showAlertInformation("Error", "Email is already in use");
            return;
          }
        }
        manager.setPhoneNumber(newPhone);
        manager.setEmail(newEmail);
        ManagerDAO.getInstance().update(manager);
      } else {
        if (UserDAO.getInstance().checkUserLogin(user.getUserName(), currentPassword.getText())
            == null) {
          showAlertInformation("Error", "Password is incorrect");
          return;
        }
        if (!newPasswordTextField.getText().isEmpty() || !confirmPasswordTextField.getText()
            .isEmpty()) {
          if (isValidPassword(newPasswordTextField.getText())) {
            showAlertInformation("Error", "Password is invalid");
            return;
          }
          if (!newPasswordTextField.getText().equals(confirmPasswordTextField.getText())) {
            showAlertInformation("Error", "Confirm password is not match");
            return;
          }
          user.setPassword(newPasswordTextField.getText());
        }
        if (isValidPhoneNumber(phoneNumberTextField.getText())) {
          showAlertInformation("Error", "Phone number is invalid");
          return;
        }
        if (isValidEmail(emailTextField.getText())) {
          showAlertInformation("Error", "Email is invalid");
          return;
        }
        String newPhone = phoneNumberTextField.getText();
        String newEmail = emailTextField.getText();
        if (checkUserRegisterInformation(newPhone, newEmail)) {
          return;
        }
      }

      showAlertInformation("Success!", "Your information has been saved");
      canChangeSecurity = false;
    } else {
      showAlertInformation("Error", "Please enter correct code");
    }
  }

  private boolean isValidPassword(String password) {
    return !checkPassWord(password);
  }
}
