package library.management.ui.controllers;

import static library.management.alert.AlertMaker.showAlertConfirmation;
import static library.management.mainApp.mainStage;

import java.io.IOException;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import library.management.properties;

public class signOutController extends MainController{

  public static void handleSignOut(ActionEvent actionEvent) {
    Optional<ButtonType> result = showAlertConfirmation(
        SIGN_OUT_TITLE, SIGN_OUT_MESSAGE);
    if (result.isPresent() && result.get() == ButtonType.OK) {
      try {
        FXMLLoader loader = new FXMLLoader(
            signOutController.class.getResource(LOGIN_SOURCES));
        Scene scene = new Scene(loader.load());
        mainStage.setTitle(MAIN_TITLE);
        mainStage.setResizable(false);
        mainStage.setScene(scene);
        mainStage.show();
      } catch (IOException e) {
        System.err.println(e.getMessage());
      }
    }
  }
}
