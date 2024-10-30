package library.management.ui.applications;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

@SuppressWarnings("CallToPrintStackTrace")
public class login extends Application {

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/fxml/login.fxml"));
      Scene scene = new Scene(loader.load());
      scene.getStylesheets().add(getClass().getResource("/ui/css/login.css").toExternalForm());
      primaryStage.setTitle("Library Management System");
      primaryStage.setResizable(false);

      primaryStage.setScene(scene);
      primaryStage.show();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
