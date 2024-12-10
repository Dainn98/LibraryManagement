package library.management.ui.controllers.user;

import com.jfoenix.controls.JFXButton;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import library.management.properties;
import library.management.ui.controllers.SettingsController;

import java.io.IOException;
/**
 * The {@code UserAvatarInfoController} class handles the user avatar information UI,
 * including displaying user details and managing the theme toggle and settings button actions.
 * <p>
 * It allows the user to toggle between light and dark themes and open the settings window.
 */
public class UserAvatarInfoController extends UserSubController implements properties {
    @FXML
    public VBox avatarVBox;
    @FXML
    public Label manaName;
    @FXML
    public JFXButton settingsButton;
    @FXML
    public ToggleButton themeButton;
    @FXML
    private javafx.scene.shape.Circle Circle;
    /**
     * Initializes the {@code UserAvatarInfoController} with the specified {@code FullUserController}.
     * This method sets up the user details and UI components based on the provided controller.
     *
     * @param controller the {@code FullUserController} to initialize with.
     */
    public void initialize(FullUserController controller) {
        this.controller = controller;
        setData();
    }

    public void setData() {
        this.manaName.setText(controller.getMainUserName());
    }

    private boolean isOn = false;
    /**
     * Handles the theme toggle button action.
     * When the toggle button is clicked, the UI theme is switched between light and dark mode.
     *
     * @param actionEvent the action event triggered by the toggle button.
     */
    @FXML
    private void handleThemeButton(ActionEvent actionEvent) {
        TranslateTransition transition = new TranslateTransition();
        transition.setNode(Circle);
        transition.setDuration(Duration.millis(500));
        transition.setFromX(Circle.getTranslateX());
        if (themeButton.isSelected()) {
            System.out.println("Toggle is ON"); // Khi bật
            transition.setToX(150- Circle.getRadius() * 2);
            controller.path = getClass().getResource("/ui/css/dark-theme.css").toExternalForm();
            controller.stackFull.getStylesheets().clear();
            controller.stackFull.getStylesheets().add(controller.path);
            isOn = true;
        } else {
            System.out.println("Toggle is OFF");
            transition.setToX(0);
            controller.path = getClass().getResource("/ui/css/theme.css").toExternalForm();
            controller.stackFull.getStylesheets().clear();
            controller.stackFull.getStylesheets().add(controller.path);
            isOn = false;
        }
        transition.play();
    }

    /**
     * Handles the settings button action.
     * Opens the settings window where the user can modify application settings.
     *
     * @param actionEvent the action event triggered by the settings button.
     */
    @FXML
    private void handleSettingsButton(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource(SETTINGS_SOURCE));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Settings");
            SettingsController controller = fxmlLoader.getController();
            controller.setFullUserControllerController(this.controller);
            controller.setData();

            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.setOnCloseRequest((WindowEvent windowEvent) -> {
                stage.close();
            });
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
