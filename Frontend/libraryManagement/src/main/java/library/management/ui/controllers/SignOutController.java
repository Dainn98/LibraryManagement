package library.management.ui.controllers;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class SignOutController {
  public static void handleSignOut() {
    try {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(SignOutController.class.getResource("/ui/fxml/modernLogin.fxml"));
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