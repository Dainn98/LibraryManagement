package library.management.ui.applications;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import library.management.properties;

public class modernLogin extends Application implements properties {

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/fxml/modernLogin.fxml"));
      Scene scene = new Scene(loader.load());

      Image icon = new Image(getClass().getResourceAsStream(ICON_SOURCE));
      primaryStage.getIcons().add(icon);

      primaryStage.setTitle("Library Management System");
      primaryStage.setResizable(false);
      primaryStage.setScene(scene);
      primaryStage.show();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
