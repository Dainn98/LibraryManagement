package library.management.ui.controllers;

import static library.management.alert.AlertMaker.showAlertInformation;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import library.management.data.DAO.ManagerDAO;
import library.management.data.entity.Manager;
import library.management.main;

public class ModernLoginController implements Initializable, GeneralController {

  private static final int WIDTH = 780;
  private static final double rightTrans = (double) WIDTH / 2;
  private static final double leftTrans = -(double) WIDTH / 2;

  @FXML
  protected Pane demoPane;
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
  protected Pane transPane1;
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
    String userName = loginUsername.getText();
    String password = loginPassword.getText();
    if (ManagerDAO.getInstance().checkManager(userName, password)) {
      // Close the current login window
      Stage loginStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
      loginStage.close();

      // Launch the Main application
      main mainApp = new main();
      Stage mainStage = new Stage();
      mainApp.start(mainStage);
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

    if (ManagerDAO.getInstance().checkManagerByUserName(userName)) {
      showAlertInformation("Sign Up Failed", "Username is already exist.");
      return;
    }
    if (ManagerDAO.getInstance().checkManagerByIdentityCard(userIdentityCard)) {
      showAlertInformation("Sign Up Failed", "IdentityCard already used by another user.");
      return;
    }
    if (ManagerDAO.getInstance().checkManagerByEmail(userEmail)) {
      showAlertInformation("Sign Up Failed", "Email is already used by another user.");
    }
    if (!validateInputs(userName, userEmail, userPassword, confPassword)) {
      return;
    }
    if (ManagerDAO.getInstance()
        .add(new Manager(userName, userPassword, userIdentityCard, userEmail)) < 1) {
      showAlertInformation("Sign Up Failed", "Something went wrong, please try again.");
      return;
    }
    showAlertInformation("Sign Up Successful", "Sign Up Successful");
    showLoginForm(actionEvent);
  }

  final static Duration DURATION = Duration.millis(500);
  public void showRegisterForm(ActionEvent actionEvent) {

    transFade(registerPane, rightTrans, 0, 1, DURATION);
    transFade(registerSwitch, rightTrans, 0.5, 0, DURATION);

    transFade(loginSwitch, rightTrans, 0.5, 1, DURATION);
    transFade(loginPane, rightTrans, 1, 0, DURATION);

    transFade(transPane, leftTrans, 1, 1, DURATION);
  }

  public void showLoginForm(ActionEvent actionEvent) {

    transFade(registerPane, leftTrans, 0.5, 0, DURATION);
    transFade(registerSwitch, leftTrans, 0.5, 1, DURATION);

    transFade(loginSwitch, leftTrans, 0.5, 0, DURATION);
    transFade(loginPane, leftTrans, 0.5, 1, DURATION);

    transFade(transPane, rightTrans, 1, 1, DURATION);
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
  private boolean validateInputs(String username, String email,
      String password, String confirmPassword) {
    // Check empty fields
    if (username.isEmpty() || email.isEmpty()
        || password.isEmpty() || confirmPassword.isEmpty()) {
      showAlertInformation("Sign Up Failed", "All fields are required.");
      return false;
    }

    // Check username length
    if (username.length() < 3 || username.length() > 20) {
      showAlertInformation("Sign Up Failed", "Username must be between 3 and 20 characters.");
      return false;
    }

    // Check email format
    if (!isValidEmail(email)) {
      showAlertInformation("Sign Up Failed", "Invalid email format.");
      return false;
    }

public class ModernLoginController  implements Initializable,GeneralController {

    private static final int WIDTH = 780;
    private static final double rightTrans = (double) WIDTH / 2;
    private static final double leftTrans = -(double) WIDTH / 2;

    @FXML
    protected Pane demoPane;
    @FXML
    protected Pane logRegSprites;
    @FXML
    protected Pane registerNavigation;
    @FXML
    protected Pane loginNavigation;
    @FXML
    protected Pane loginForm;
    @FXML
    protected Pane registerForm;
    @FXML
    protected Pane switchForm;
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
        String userName = loginUsername.getText();
        String password = loginPassword.getText();
        if (ManagerDAO.getInstance().checkManager(userName, password)) {
            // Close the current login window
            Stage loginStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            loginStage.close();

            // Launch the Main application
            main mainApp = new main();
            Stage mainStage = new Stage();
            mainApp.start(mainStage);
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

        if (ManagerDAO.getInstance().checkManagerByUserName(userName)) {
            showAlertInformation("Sign Up Failed", "Username is already exist.");
            return;
        }
        if (ManagerDAO.getInstance().checkManagerByIdentityCard(userIdentityCard)) {
            showAlertInformation("Sign Up Failed", "IdentityCard already used by another user.");
            return;
        }
        if (ManagerDAO.getInstance().checkManagerByEmail(userEmail)) {
            showAlertInformation("Sign Up Failed", "Email is already used by another user.");
        }
        if (!validateInputs(userName, userEmail, userPassword, confPassword)) {
            return;
        }
        if (ManagerDAO.getInstance().add(new Manager(userName, userPassword, userIdentityCard, userEmail)) < 1) {
            showAlertInformation("Sign Up Failed", "Something went wrong, please try again.");
            return;
        }
        showAlertInformation("Sign Up Successful", "Sign Up Successful");
        showLoginForm(actionEvent);
    }

    public void showRegisterForm(ActionEvent actionEvent) {
        Timeline timeline = new Timeline();

        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(0.5), e -> {
            registerForm.setVisible(true);
            loginNavigation.setVisible(true);
            transFade(registerForm, rightTrans, 0, 1, Duration.seconds(0.5));
            transFade(loginForm, rightTrans, 1, 0, Duration.seconds(0.5));
            transFade(loginNavigation, rightTrans, 0.5, 1, Duration.seconds(0.5));
            transFade(registerNavigation, rightTrans, 0.5, 0, Duration.seconds(0.5));
            transFade(logRegSprites, leftTrans, 1, 1, Duration.seconds(0.5));
        }));

        timeline.setOnFinished(e -> {
            loginForm.setVisible(false);
            registerNavigation.setVisible(false);
        });
        timeline.play();
    }

    public void showLoginForm(ActionEvent actionEvent) {
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(0.5), e -> {
            loginForm.setVisible(true);
            registerNavigation.setVisible(true);
            transFade(registerForm, leftTrans, 1, 0, Duration.seconds(0.5));
            transFade(registerNavigation, leftTrans, 0, 1, Duration.seconds(0.5));
            transFade(loginForm, leftTrans, 0.5, 1, Duration.seconds(0.5));
            transFade(loginNavigation, leftTrans, 0.5, 0, Duration.seconds(0.5));
            transFade(logRegSprites, rightTrans, 1, 1, Duration.seconds(0.5));
        }));

        timeline.setOnFinished(e -> {
            registerForm.setVisible(false);
            loginNavigation.setVisible(false);
        });

        timeline.play();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loginForm.setVisible(true);
        registerNavigation.setVisible(true);
        registerForm.setVisible(false);
        loginNavigation.setVisible(false);

        registerForm.setLayoutX(0);
        loginForm.setLayoutX(0);

        loginNavigation.setLayoutX((double) -WIDTH / 2);
        registerNavigation.setLayoutX((double) WIDTH / 2);
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
    private boolean validateInputs(String username, String email,
                                   String password, String confirmPassword) {
        // Check empty fields
        if (username.isEmpty() || email.isEmpty()
                || password.isEmpty() || confirmPassword.isEmpty()) {
            showAlertInformation("Sign Up Failed", "All fields are required.");
            return false;
        }

        // Check username length
        if (username.length() < 3 || username.length() > 20) {
            showAlertInformation("Sign Up Failed", "Username must be between 3 and 20 characters.");
            return false;
        }

        // Check email format
        if (!isValidEmail(email)) {
            showAlertInformation("Sign Up Failed", "Invalid email format.");
            return false;
        }

        if (!isValidPassword(password)) {
            return false;
        }

        // Check password length
        if (password.length() < 6) {
            showAlertInformation("Sign Up Failed", "Password must be at least 6 characters.");
            return false;
        }

        // Check password match
        if (!password.equals(confirmPassword)) {
            showAlertInformation("Sign Up Failed", "Passwords do not match.");
            return false;
        }

        // Clear previous errors
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
        // Kiểm tra độ dài tối thiểu là 6 ký tự
        if (password.length() < 6) {
            showAlertInformation("Invalid Password",
                    "The password must be at least six characters long.");
            return false;
        }

        // Kiểm tra có ít nhất một ký tự chữ cái
        if (!password.matches(".*[a-zA-Z].*")) {
            showAlertInformation("Invalid Password",
                    "The password must contain at least one alphabetic character.");
            return false;
        }

        // Kiểm tra có ít nhất một chữ số
        if (!password.matches(".*[0-9].*")) {
            showAlertInformation("Invalid Password",
                    "The password must contain at least one numeric digit.");
            return false;
        }

        // Kiểm tra có ít nhất một ký tự đặc biệt
        if (!password.matches(".*[!@#$%^&*()_+\\-\\[\\]{};':\"\\\\|,.<>/?].*")) {
            showAlertInformation("Invalid Password",
                    "The password must contain at least one special character.");
            return false;
        }

        // Nếu thỏa mãn tất cả các điều kiện, trả về true
        return true;

    }

    // Check password length
    if (password.length() < 6) {
      showAlertInformation("Sign Up Failed", "Password must be at least 6 characters.");
      return false;
    }

    // Check password match
    if (!password.equals(confirmPassword)) {
      showAlertInformation("Sign Up Failed", "Passwords do not match.");
      return false;
    }

    // Clear previous errors
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
    // Kiểm tra độ dài tối thiểu là 6 ký tự
    if (password.length() < 6) {
      showAlertInformation("Invalid Password",
          "The password must be at least six characters long.");
      return false;
    }

    // Kiểm tra có ít nhất một ký tự chữ cái
    if (!password.matches(".*[a-zA-Z].*")) {
      showAlertInformation("Invalid Password",
          "The password must contain at least one alphabetic character.");
      return false;
    }

    // Kiểm tra có ít nhất một chữ số
    if (!password.matches(".*[0-9].*")) {
      showAlertInformation("Invalid Password",
          "The password must contain at least one numeric digit.");
      return false;
    }

    // Kiểm tra có ít nhất một ký tự đặc biệt
    if (!password.matches(".*[!@#$%^&*()_+\\-\\[\\]{};':\"\\\\|,.<>/?].*")) {
      showAlertInformation("Invalid Password",
          "The password must contain at least one special character.");
      return false;
    }

    // Nếu thỏa mãn tất cả các điều kiện, trả về true
    return true;
  }

}
