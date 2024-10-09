package library.management.ui.register;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import library.management.ui.UI;

public class registerController implements UI {

  @FXML
  private Label errorLabel;
  @FXML
  private TextField fullNameField;
  @FXML
  private TextField passwordField;
  @FXML
  private TextField usernameField;
  @FXML
  private TextField emailField;
  @FXML
  private TextField phoneField;
  @FXML
  private TextField dobField;
  @FXML
  private TextField confirmPasswordField;

  @Override
  public void initialize() {

  }

  @Override
  public void showAlert(String title, String message) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
  }

  @FXML
  private void handleRegister() {
    String fullName = fullNameField.getText();
    String username = usernameField.getText();
    String email = emailField.getText();
    String phone = phoneField.getText();
    String dob = dobField.getText();
    String password = passwordField.getText();
    String confirmPassword = confirmPasswordField.getText();

    // Validate inputs
    if (!validateInputs(fullName, username, email, phone, dob, password, confirmPassword)) {
      return;
    }

    // If valid, perform registration (mockup or connect to database)
    showAlert("Registration Successful", "User registered successfully!");
  }

  // Validate all input fields
  private boolean validateInputs(String fullName, String username, String email, String phone, String dob, String password, String confirmPassword) {
    // Check empty fields
    if (fullName.isEmpty() || username.isEmpty() || email.isEmpty() || phone.isEmpty() || dob.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
      setError("All fields are required.");
      return false;
    }

    // Check username length
    if (username.length() < 3 || username.length() > 20) {
      setError("Username must be between 3 and 20 characters.");
      return false;
    }

    // Check email format
    if (!isValidEmail(email)) {
      setError("Invalid email format.");
      return false;
    }

    // Check phone format (basic numeric check, length)
    if (!isValidPhone(phone)) {
      setError("Invalid phone number.");
      return false;
    }

    if(!isValidPassword(password)) {
      setError("Invalid password.");
      return false;
    }

    // Check date of birth format
    if (!isValidDate(dob)) {
      setError("Invalid date of birth format. Use YYYY-MM-DD.");
      return false;
    }

    // Check password length
    if (password.length() < 6) {
      setError("Password must be at least 6 characters.");
      return false;
    }

    // Check password match
    if (!password.equals(confirmPassword)) {
      setError("Passwords do not match.");
      return false;
    }

    // Clear previous errors
    errorLabel.setText("");
    return true;
  }

  // Validate email format
  private boolean isValidEmail(String email) {
    String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(gmail\\.com|vnu\\.edu\\.vn)$";
    Pattern pattern = Pattern.compile(emailRegex);
    return pattern.matcher(email).matches();
  }

  // Validate phone format
  private boolean isValidPhone(String phone) {
    String phoneRegex = "^0[0-9]{9}$"; // Example: assuming phone numbers should be 10 digits
    return phone.matches(phoneRegex);
  }

  // Validate date format
  //Scene => Select DOB.
  private boolean isValidDate(String dob) {
    try {
      LocalDate.parse(dob); // Check if it's a valid date (uses format YYYY-MM-DD)
      return true;
    } catch (DateTimeParseException e) {
      return false;
    }
  }

  private boolean isValidPassword(String password) {
    // Regex to check password criteria
    String passwordRegex = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*()_+\\-\\[\\]{};':\"\\\\|,.<>/?])(?=.{6,})$";
    if (!password.matches(passwordRegex)) {
      showAlert("Invalid Password", "The password must contain a minimum of six characters and include at least one alphabetic character, one numeric digit, and one special character.");
      return false;
    }
    return true;
  }

  // Set error message
  private void setError(String message) {
    errorLabel.setText(message);
  }

}
