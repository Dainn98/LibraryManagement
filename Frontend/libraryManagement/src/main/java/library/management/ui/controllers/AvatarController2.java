package library.management.ui.controllers;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class AvatarController2 {
  private FullUserController controller;
  private static final String AVATAR_INFO_SOURCE = "/ui/fxml/avatarInfo1.fxml";

  public AvatarController2() {
    // No-argument constructor
  }

  public AvatarController2(FullUserController controller){
    this.controller = controller;
  }

  public void initAvatar(VBox vBox){
    try {
      FXMLLoader fxmlLoader = new FXMLLoader();
      fxmlLoader.setLocation(getClass().getResource(AVATAR_INFO_SOURCE));
      VBox vboxLoad = fxmlLoader.load();
      AvatarInfoController2 avatarinfoController = fxmlLoader.getController();
      avatarinfoController.initialize(controller);
      vBox.getChildren().add(vboxLoad);
    } catch (IOException e){
      e.printStackTrace();
    }
  }

}
