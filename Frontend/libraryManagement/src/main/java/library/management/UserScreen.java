package library.management;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import library.management.data.entity.User;
import library.management.ui.controllers.user.FullUserController;

@SuppressWarnings("CallToPrintStackTrace")
public class UserScreen extends Application implements properties {

  private User user;

  public static void main(String[] args) {
    try {
      launch(args);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void setUser(User user) {
    this.user = user;
  }

  @Override
  public void start(Stage primaryStage) {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/fxml/full.fxml"));
      StackPane root = loader.load();
      Scene scene = new Scene(root);
//      scene.getStylesheets()
//          .add(Objects.requireNonNull(getClass().getResource(MAIN_STYLE_SOURCES)).toExternalForm());
      primaryStage.setScene(scene);
      primaryStage.setTitle(MAIN_TITLE);

      primaryStage.setMaximized(true);
      primaryStage.setResizable(true);
      primaryStage.setMinWidth(SCREEN_WIDTH);
      primaryStage.setMinHeight(SCREEN_HEIGHT);

      FullUserController controller = loader.getController();
      if (user == null) {
        user = new User();
      }
      controller.setMainUser(user);

      primaryStage.show();
    } catch (IOException e) {
//      System.err.println("Failed to load FXML file.");
      System.out.println(e.getMessage());
      e.printStackTrace();

    } catch (Exception e) {
//      System.err.println("An unexpected error occurred.");
      System.out.println(e.getMessage());
      e.printStackTrace();
    }
  }
}