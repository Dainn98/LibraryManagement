package library.management.ui.controllers.manager;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import library.management.properties;


public class AvatarController extends ManagerSubController implements properties {
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
