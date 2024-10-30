package library.management.ui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;

public class RegisterNewBookController {

  public void handleAddBooks(TextField titleOfBookField) {
    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle("Book Added");
    alert.setHeaderText(null);
    alert.setContentText("Book has been added successfully!");
    String title = titleOfBookField.getText();
    System.out.println("Book Title: " + title);
    alert.showAndWait();
  }

}
