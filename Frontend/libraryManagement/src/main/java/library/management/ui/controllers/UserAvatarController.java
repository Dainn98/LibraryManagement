package library.management.ui.controllers;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;


public class UserAvatarController {

  private static final String AVATAR_INFO_SOURCE = "/ui/fxml/avatarInfo2.fxml";

  private FullUserController controller;

  public UserAvatarController() {
  }

  public UserAvatarController(FullUserController controller) {
    this.controller = controller;
  }

  public void initAvatar(VBox vBox) {
    try {
      FXMLLoader fxmlLoader = new FXMLLoader();
      fxmlLoader.setLocation(getClass().getResource(AVATAR_INFO_SOURCE));
      VBox vboxLoad = fxmlLoader.load();
      UserAvatarInfoController userAvatarInfoController = fxmlLoader.getController();
      userAvatarInfoController.initialize(controller);
      vBox.getChildren().add(vboxLoad);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
