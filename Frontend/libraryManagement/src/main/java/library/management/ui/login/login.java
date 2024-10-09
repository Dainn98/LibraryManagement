package library.management.ui.login;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import library.management.ui.main.Main;

public class login extends Application {

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws IOException {
    FXMLLoader loader = new FXMLLoader(Main.class.getResource("/ui/LoginScreen/login.fxml"));
    Scene scene = new Scene(loader.load());
    primaryStage.setTitle("Library Management System");
    primaryStage.setScene(scene);
    primaryStage.show();

  }
}
