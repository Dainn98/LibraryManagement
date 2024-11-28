package library.management.alert;

import java.util.Optional;
import javafx.scene.control.ButtonType;

/**
 * Utility class for creating and displaying different types of alerts.
 */
public class AlertMaker {

  /**
   * Default constructor.
   */
  public AlertMaker() {
  }

  /**
   * Displays an information alert with the specified title and message.
   *
   * @param title   the title of the alert
   * @param message the message to be displayed in the alert
   */
  public static void showAlertInformation(String title, String message) {
    javafx.scene.control.Alert alert = new javafx.scene.control.Alert(
            javafx.scene.control.Alert.AlertType.INFORMATION);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
  }

  /**
   * Displays a confirmation alert with the specified title and message.
   *
   * @param title   the title of the alert
   * @param message the message to be displayed in the alert
   * @return an Optional containing the user's response
   */
  public static Optional<ButtonType> showAlertConfirmation(String title, String message) {
    javafx.scene.control.Alert alert = new javafx.scene.control.Alert(
            javafx.scene.control.Alert.AlertType.CONFIRMATION);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    return alert.showAndWait();
  }

  /**
   * Displays an error alert with the specified title and message.
   *
   * @param title   the title of the alert
   * @param message the message to be displayed in the alert
   */
  public static void showAlertError(String title, String message) {
    javafx.scene.control.Alert alert = new javafx.scene.control.Alert(
            javafx.scene.control.Alert.AlertType.ERROR);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
  }
}