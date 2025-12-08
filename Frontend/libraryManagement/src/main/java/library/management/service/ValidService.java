package library.management.service;

import static library.management.alert.AlertMaker.showAlertInformation;

import java.util.regex.Pattern;

public class ValidService {

  private static boolean isShowAlert;

  // ========================= PUBLIC API =============================== //

  /**
   * Validates all input fields.
   */
  public static boolean validateInputs(
      String username, String email, String identityCard,
      String password, String confirmPassword) {

    if (isEmpty(username, email, identityCard, password, confirmPassword)) {
      alert("Sign Up Failed", "All fields are required.");
      return false;
    }

    if (!isValidUsername(username)) {
      alert("Sign Up Failed", "Username must be between 3 and 20 characters.");
      return false;
    }

    if (!isValidEmail(email)) {
      alert("Sign Up Failed", "Invalid email format.");
      return false;
    }

    if (!isValidIdentityCard(identityCard)) {
      alert("Sign Up Failed", "Invalid IdentityCard format.");
      return false;
    }

    if (!isValidPassword(password)) {
      alert("Sign Up Failed", "Password does not meet requirements.");
      return false;
    }

    if (!password.equals(confirmPassword)) {
      alert("Sign Up Failed", "Passwords do not match.");
      return false;
    }

    return true;
  }

  /**
   * Username check for external usage
   */
  public static boolean isValidUsername(String username) {
    return username != null && username.length() >= 3 && username.length() <= 20;
  }

  /**
   * Valid password wrapper
   */
  public static boolean isValidPassword(String password) {
    return checkPassword(password);
  }

  /**
   * Identity Card validation
   */
  public static boolean isValidIdentityCard(String identityCard) {
    if (identityCard == null || identityCard.isEmpty()) {
      alert("Invalid Identity Card", "Identity card cannot be empty.");
      return false;
    }
    if (identityCard.length() < 9 || identityCard.length() > 12) {
      alert("Invalid Identity Card",
          "Identity card must be between 9 and 12 characters long.");
      return false;
    }
    if (!identityCard.matches("\\d+")) {
      alert("Invalid Identity Card",
          "Identity card must contain only numeric digits.");
      return false;
    }
    return true;
  }

  /**
   * Email validation
   */
  public static boolean isValidEmail(String email) {
    if (email == null) {
      return false;
    }

    String emailRegex =
        "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(gmail\\.com|vnu\\.edu\\.vn)$";

    return Pattern.compile(emailRegex).matcher(email).matches();
  }

  // ========================= PRIVATE UTILITIES =============================== //

  /**
   * Central alert controller
   */
  private static void alert(String title, String message) {
    if (isShowAlert) {
      showAlertInformation(title, message);
    }
    System.out.println(title + ": " + message);
  }

  /**
   * Empty check utility
   */
  private static boolean isEmpty(String... values) {
    for (String v : values) {
      if (v == null || v.isEmpty()) {
        return true;
      }
    }
    return false;
  }

  /**
   * Detailed password validator.
   */
  private static boolean checkPassword(String password) {
    if (password == null || password.length() < 6) {
      alert("Invalid Password",
          "The password must be at least six characters long.");
      return false;
    } else if (!password.matches(".*[a-zA-Z].*")) {
      alert("Invalid Password",
          "The password must contain at least one alphabetic character.");
      return false;
    } else if (!password.matches(".*[A-Z].*")) {
      alert("Invalid Password",
          "The password must contain at least one uppercase letter.");
      return false;
    } else if (!password.matches(".*\\d.*")) {
      alert("Invalid Password",
          "The password must contain at least one numeric digit.");
      return false;
    } else if (!password.matches(".*[!@#$%^&*()_+\\-\\[\\]{};':\"\\\\|,.<>/?].*")) {
      alert("Invalid Password",
          "The password must contain at least one special character.");
      return false;
    }

    return true;
  }

  // ========================= ALERT FLAG CONTROL =============================== //

  public static boolean isShowAlert() {
    return isShowAlert;
  }

  public static void setShowAlert(boolean showAlert) {
    isShowAlert = showAlert;
  }
}
