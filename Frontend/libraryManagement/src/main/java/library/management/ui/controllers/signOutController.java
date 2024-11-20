package library.management.ui.controllers;

import static library.management.alert.AlertMaker.showAlertConfirmation;

import java.io.IOException;
import java.util.Optional;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class signOutController {

  public static void handleSignOut(Class<? extends MainController> aClass) {
    Optional<ButtonType> result = showAlertConfirmation(
        "Sign Out",
        "Are you sure you want to sign out?");
    if (result.isPresent() && result.get() == ButtonType.OK) {
      try {
        FXMLLoader loader = new FXMLLoader(signOutController.class.getResource("/ui/fxml/modernLogin.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setTitle("Library Management System");
        stage.setResizable(false);
        stage.setScene(new Scene(root));
        stage.setOnCloseRequest((WindowEvent event) -> {
          stage.close();
        });
        stage.show();
      } catch (IOException e) {
        System.err.println(e.getMessage());
      }
    }
  }
}
