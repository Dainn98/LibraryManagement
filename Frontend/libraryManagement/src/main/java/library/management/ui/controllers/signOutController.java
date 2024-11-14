package library.management.ui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

import static library.management.alert.AlertMaker.showAlertConfirmation;

public class signOutController {

  public static void handleSignOut(ActionEvent actionEvent) {
    Optional<ButtonType> result = showAlertConfirmation(
        "Sign Out",
        "Are you sure you want to sign out?");
    if (result.isPresent() && result.get() == ButtonType.OK) {
      try {
        FXMLLoader loader = new FXMLLoader(
            signOutController.class.getResource("/ui/fxml/modernLogin.fxml"));
        Stage stage = (Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(loader.load());
        scene.getStylesheets()
            .add(signOutController.class.getResource("/ui/css/style.css").toExternalForm());
        stage.setTitle("Library Management System");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
      } catch (IOException e) {
        System.err.println(e.getMessage());
      }
    }
  }
}
