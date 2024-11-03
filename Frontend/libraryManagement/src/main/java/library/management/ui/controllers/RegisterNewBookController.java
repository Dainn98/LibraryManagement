package library.management.ui.controllers;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
public class RegisterNewBookController {

  public void handleAddBooks(TextField titleOfBookField, TextField authorNameField,
      TextField publisherNameField, TextField descriptionField, TextField categoryComboBox,
      TextField numberOfIssueField) {

    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle("Document Added");
    alert.setHeaderText(null);
    alert.setContentText("Document has been added successfully!");
    String title = titleOfBookField.getText();
    String author = authorNameField.getText();
    String publisher = publisherNameField.getText();
    String description = descriptionField.getText();
    String category = categoryComboBox.getText();
    String numberOfIssues = numberOfIssueField.getText();
    alert.showAndWait();
  }

}
