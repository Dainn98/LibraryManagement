package library.management.ui.controllers;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import library.management.data.entity.User;

public class AvatarController {
  private final MainController controller;
  private static final String AVATAR_INFO_SOURCE = "/ui/fxml/avatarInfo.fxml";
  @FXML
  public VBox avatarVBox;

  @FXML
  public JFXButton logOutButton;

  @FXML
  public Label manaName;

  @FXML
  public JFXButton settingsButton;

  public AvatarController(MainController controller){
    this.controller = controller;
  }

  public void initAvatar(){
//    try {
//      FXMLLoader fxmlLoader = new FXMLLoader();
//      fxmlLoader.setLocation(getClass().getResource(AVATAR_INFO_SOURCE));
//      avatarVBox = fxmlLoader.load();
//      AvatarController avatarController = fxmlLoader.getController();
//      avatarController.setData();
//      avatarVBox.getChildren().add(avatarVBox);
//    } catch (IOException e){
//      e.printStackTrace();
//    }
  }

  public void setData() {
    this.manaName.setText("Admin");
  }


}
