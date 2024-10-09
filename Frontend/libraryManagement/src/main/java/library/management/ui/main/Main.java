package library.management.ui.main;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws IOException {
    try {

      FXMLLoader loader = new FXMLLoader(Main.class.getResource("/ui/MainScreen/demo.fxml"));
      BorderPane root = loader.load();
      Scene scene = new Scene(root);
      primaryStage.setScene(scene);
      primaryStage.setTitle("Library Management System");

      primaryStage.setMaximized(true);
      primaryStage.setResizable(true);
      primaryStage.setMinWidth(800);
      primaryStage.setMinHeight(650);

      primaryStage.show();
    } catch (IOException e) {
      System.err.println("Failed to load FXML file.");
      e.printStackTrace();
    } catch (Exception e) {
      System.err.println("An unexpected error occurred.");
      e.printStackTrace();
    }
  }
}