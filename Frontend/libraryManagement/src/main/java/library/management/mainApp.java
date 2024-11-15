package library.management;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

@SuppressWarnings("CallToPrintStackTrace")
public class mainApp extends Application implements properties {
  public static Stage mainStage;
  public static void main(String[] args) {
    try {
      launch(args);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public void start(Stage primaryStage) {
    try {

      mainStage = primaryStage;
      FXMLLoader loader = new FXMLLoader(getClass().getResource(MAIN_SCREEN_SOURCES));
      StackPane root = loader.load();
      Scene scene = new Scene(root);
      mainStage.setScene(scene);
      mainStage.setTitle(MAIN_TITLE);

      mainStage.setMaximized(true);
      mainStage.setResizable(true);
      mainStage.setMinWidth(SCREEN_WIDTH);
      mainStage.setMinHeight(SCREEN_HEIGHT);

      mainStage.show();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}