package library.management.ui.main;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

@SuppressWarnings("CallToPrintStackTrace")
public class Main extends Application {

  private final static int screenWidth = 1080;
  private final static int screenHeight = 600;
  private final static String mainScreenSources = "/ui/main/mainScreen.fxml";
  private final static String mainStyleSources = "/ui/stylesheets/dark-theme.css";
  private final static String mainTitle = "Library Management System";

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource(mainScreenSources));
      BorderPane root = loader.load();
      Scene scene = new Scene(root);
      scene.getStylesheets().add(getClass().getResource(mainStyleSources).toExternalForm());
      primaryStage.setScene(scene);
      primaryStage.setTitle(mainTitle);

      primaryStage.setMaximized(true);
      primaryStage.setResizable(true);
      primaryStage.setMinWidth(screenWidth);
      primaryStage.setMinHeight(screenHeight);

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