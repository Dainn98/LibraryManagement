package library.management.ui.applications;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import library.management.properties;

public class modernLogin extends Application implements properties {

    /**
     * Main entry point of the JavaFX application.
     *
     * @param args The command-line arguments passed to the application.
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Starts the JavaFX application by setting up the primary stage with the login scene.
     *
     * @param primaryStage The primary stage for this application.
     */
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
