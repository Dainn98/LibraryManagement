package library.management.ui.addBook;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

public class AddBookController {

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

