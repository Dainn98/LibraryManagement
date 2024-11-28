package library.management.ui.controllers.user;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import library.management.properties;


public class UserAvatarController extends UserSubController implements properties {
  public UserAvatarController() {
    // No-argument constructor
  }

  public UserAvatarController(FullUserController controller){
    this.controller = controller;
  }

  public void initAvatar(VBox vBox) {
    try {
      FXMLLoader fxmlLoader = new FXMLLoader();
      fxmlLoader.setLocation(getClass().getResource(USER_AVATAR_INFO_SOURCE));
      VBox vboxLoad = fxmlLoader.load();
      UserAvatarInfoController userAvatarInfoController = fxmlLoader.getController();
      userAvatarInfoController.initialize(controller);
      vBox.getChildren().add(vboxLoad);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
