package library.management.ui.controllers;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class AvatarController2 {
  private fullUserController controller;
  private static final String AVATAR_INFO_SOURCE = "/ui/fxml/avatarInfo2.fxml";
  @FXML
  public VBox avatarVBox;

  @FXML
  public JFXButton logOutButton;

  @FXML
  public Label manaName;

  @FXML
  public JFXButton settingsButton;

  public AvatarController2() {
    // No-argument constructor
  }

  public AvatarController2(fullUserController controller){
    this.controller = controller;
  }

  public void initAvatar(VBox vBox){
    try {
      FXMLLoader fxmlLoader = new FXMLLoader();
      fxmlLoader.setLocation(getClass().getResource(AVATAR_INFO_SOURCE));
      VBox vboxLoad = fxmlLoader.load();
      AvatarController2 avatarController = fxmlLoader.getController();
      avatarController.setData();
      vBox.getChildren().add(vboxLoad);
    } catch (IOException e){
      e.printStackTrace();
    }
  }

  public void setData() {
    this.manaName.setText("Admin");
  }


}
