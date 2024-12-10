package library.management.ui.controllers.user;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import library.management.properties;

/**
 * The {@code UserAvatarController} is responsible for managing the user's avatar
 * and related information in the user interface. It handles the initialization of
 * the avatar component, which includes loading and displaying additional user information.
 * <p>
 * This controller uses an FXML layout for the avatar view and interacts with other
 * controllers to display detailed user information.
 */
public class UserAvatarController extends UserSubController implements properties {
    public UserAvatarController() {
        // No-argument constructor
    }

    public UserAvatarController(FullUserController controller) {
        this.controller = controller;
    }

    /**
     * Initializes the user avatar by loading the avatar information FXML view
     * and adding it to the provided {@code VBox} container.
     * This method sets up the user avatar and any related information within
     * the given container.
     *
     * @param vBox the {@code VBox} container where the avatar information should be added.
     */
    public void initAvatar(VBox vBox) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource(USER_AVATAR_INFO_SOURCE));
            VBox vboxLoad = fxmlLoader.load();
            UserAvatarInfoController userAvatarInfoController = fxmlLoader.getController();
            userAvatarInfoController.initialize(controller);
            vBox.getChildren().add(vboxLoad);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
