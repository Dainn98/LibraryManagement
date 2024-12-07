package library.management.ui.controllers;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Controller for handling the sign-out action in the Library Management System.
 * This class is responsible for loading the login screen and closing the current window
 * when a user signs out of the application.
 */
public class SignOutController {

    /**
     * Handles the sign-out action by loading the login screen and closing the current window. It
     * creates a new stage (window) for the login screen and displays it. If an error occurs during
     * loading the FXML file, it prints the error message to the console.
     */
    public static void handleSignOut() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(SignOutController.class.getResource("/ui/fxml/modernLogin.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Library Management System");
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.setOnCloseRequest((WindowEvent event) -> {
                stage.close();
            });
            stage.show();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}