package library.management.ui.RegisterNewBook;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class RegisterNewBookController {

  /**
   * handleAddBooks method is used to handle the event when the user clicks on the Add Books
   * button.
   *
   * @param actionEvent - The event when the user clicks on the Add Books button.
   */
  @FXML
  public void handleAddBooks(ActionEvent actionEvent) {
    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle("Book Added");
    alert.setHeaderText(null);
    alert.setContentText("Book has been added successfully!");
    alert.showAndWait();
  }

}

