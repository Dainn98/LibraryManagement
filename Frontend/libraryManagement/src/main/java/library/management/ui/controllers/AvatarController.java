package library.management.ui.controllers;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;


public class AvatarController {

  private static final String AVATAR_INFO_SOURCE = "/ui/fxml/avatarInfo.fxml";

  private MainController controller;

  public AvatarController() {
  }

  public AvatarController(MainController controller) {
    this.controller = controller;
  }

  public void initAvatar(VBox vBox) {
    try {
      FXMLLoader fxmlLoader = new FXMLLoader();
      fxmlLoader.setLocation(getClass().getResource(AVATAR_INFO_SOURCE));
      VBox vboxLoad = fxmlLoader.load();
      AvatarInfoController avatarinfoController = fxmlLoader.getController();
      avatarinfoController.initialize(controller);
      vBox.getChildren().add(vboxLoad);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
