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

  /**
   * Initializes the avatar section on the UI by loading the FXML file and adding it to the provided
   * {@link VBox}. This method attempts to load the FXML file from the location specified in
   *
   * @param vBox the {@link VBox} to which the avatar UI components will be added.
   * @throws IOException if there is an error loading the FXML or adding the UI component.
   * @link AVATAR_INFO_SOURCE, then initializes its controller and adds the loaded UI component to
   * the given {@link VBox}.
   */
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
