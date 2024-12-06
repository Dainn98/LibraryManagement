package library.management.ui.controllers.manager;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import library.management.properties;
import library.management.ui.controllers.SettingsController;


public class AvatarInfoController implements properties {

  @FXML
  public VBox avatarVBox;
  @FXML
  public Label manaName;
  @FXML
  public JFXButton settingsButton;
  @FXML
  public ToggleButton themeButton;
  @FXML
  private Circle Circle;

  private MainController controller;
  private boolean isOn = false;

  public void initialize(MainController controller) {
    this.controller = controller;
    setData();
  }

  public void setData() {
    this.manaName.setText(controller.getManagerName());
  }

  @FXML
  private void handleThemeButton(ActionEvent actionEvent) {
    TranslateTransition transition = new TranslateTransition();
    transition.setNode(Circle);
    transition.setDuration(Duration.millis(500));
    transition.setFromX(Circle.getTranslateX());

    if (themeButton.isSelected()) {
      System.out.println("Toggle is ON");
      transition.setToX(150 - Circle.getRadius() * 2);
      controller.path = getClass().getResource("/ui/css/myDarkTheme.css").toExternalForm();
      controller.mainStackPane.getStylesheets().clear();
      controller.mainStackPane.getStylesheets().add(controller.path);
      isOn = true;
    } else {
      System.out.println("Toggle is OFF");
      transition.setToX(0);
      controller.path = getClass().getResource("/ui/css/myTheme.css").toExternalForm();
      controller.mainStackPane.getStylesheets().clear();
      controller.mainStackPane.getStylesheets().add(controller.path);
      isOn = false;
    }
    transition.play();
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
