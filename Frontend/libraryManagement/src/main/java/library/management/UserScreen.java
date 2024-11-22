package library.management;

import java.io.IOException;
import java.util.Objects;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

@SuppressWarnings("CallToPrintStackTrace")
public class UserScreen extends Application implements properties {

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