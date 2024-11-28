package library.management;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import library.management.data.entity.Manager;
import library.management.ui.controllers.MainController;

@SuppressWarnings("CallToPrintStackTrace")
public class main extends Application implements properties {
    private Manager manager;

    public void setManager(Manager manager) {
        this.manager = manager;
    }

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
            FXMLLoader loader = new FXMLLoader(getClass().getResource(MAIN_SCREEN_SOURCES));
            StackPane root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle(MAIN_TITLE);

            Image icon = new Image(getClass().getResourceAsStream(ICON_SOURCE));
            primaryStage.getIcons().add(icon);

            primaryStage.setMaximized(true);
            primaryStage.setResizable(true);
            primaryStage.setMinWidth(SCREEN_WIDTH);
            primaryStage.setMinHeight(SCREEN_HEIGHT);
            
            MainController controller = loader.getController();
            controller.setMainManager(manager);

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