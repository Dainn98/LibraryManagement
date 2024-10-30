package library.management;

import java.io.IOException;
import java.util.Objects;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

@SuppressWarnings("CallToPrintStackTrace")
public class main extends Application {

  private static final int SCREEN_WIDTH = 1080;
  private static final int SCREEN_HEIGHT = 600;
  private static final String MAIN_SCREEN_SOURCES = "/ui/fxml/mainScreen.fxml";
  private static final String MAIN_STYLE_SOURCES = "/ui/css/pastel-theme.css";
  private static final String MAIN_TITLE = "Library Management System";

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource(MAIN_SCREEN_SOURCES));
      StackPane root = loader.load();
      Scene scene = new Scene(root);
      scene.getStylesheets()
          .add(Objects.requireNonNull(getClass().getResource(MAIN_STYLE_SOURCES)).toExternalForm());
      primaryStage.setScene(scene);
      primaryStage.setTitle(MAIN_TITLE);

      primaryStage.setMaximized(true);
      primaryStage.setResizable(true);
      primaryStage.setMinWidth(SCREEN_WIDTH);
      primaryStage.setMinHeight(SCREEN_HEIGHT);

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