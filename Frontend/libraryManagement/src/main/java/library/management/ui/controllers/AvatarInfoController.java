package library.management.ui.controllers;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import library.management.properties;

public class AvatarInfoController implements properties {

  @FXML
  public VBox avatarVBox;
  @FXML
  public JFXButton logOutButton;
  @FXML
  public Label manaName;
  @FXML
  public JFXButton settingsButton;

  private MainController controller;

  public void initialize(MainController controller) {
    this.controller = controller;
    setData();
  }

  public void setData() {
    this.manaName.setText(controller.getManagerName());
  }

  @FXML
  private void handleSignOutButton(ActionEvent actionEvent) {
    controller.handleSignOutButton(actionEvent);
  }

  @FXML
  private void handleSettingsButton(ActionEvent actionEvent) {
    try {
      FXMLLoader fxmlLoader = new FXMLLoader();
      fxmlLoader.setLocation(getClass().getResource(SETTINGS_SOURCE));
      Parent root = fxmlLoader.load();
      Stage stage = new Stage();

      Image icon = new Image(getClass().getResourceAsStream(ICON_SOURCE));
      stage.getIcons().add(icon);

      stage.setTitle(SETTINGS_TITLE);
      SettingsController controller = fxmlLoader.getController();
      controller.setMainController(this.controller);
      controller.setData();

      stage.setResizable(false);
      stage.setScene(new Scene(root));
      stage.setOnCloseRequest((WindowEvent event) -> {
        stage.close();
      });
      stage.show();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
