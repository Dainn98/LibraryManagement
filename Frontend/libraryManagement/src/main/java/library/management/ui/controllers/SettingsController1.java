package library.management.ui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import library.management.data.entity.Manager;
import library.management.data.entity.User;

public class SettingsController1 {

    @FXML
    private TextField email;
    @FXML
    private Label identityCard;
    @FXML
    private TextField name;

    private FullUserController controller;
    private User user;

    public void setController(FullUserController controller) {
        this.controller = controller;
    }

    public void setData() {
        this.user = controller.getMainUser();
        this.name.setText(user.getUserName());
        String identityCard = user.getIdentityCard();
        String email = user.getEmail();
        if (identityCard == null || identityCard.isEmpty()) {
            this.identityCard.setText("Not set");
        } else {
            this.identityCard.setText(identityCard);
        }
        if (email == null || email.isEmpty()) {
            this.email.setText("Not set");
        } else {
            this.email.setText(email);
        }
    }

    @FXML
    private void handleProfileButton(ActionEvent actionEvent) {
    }

    @FXML
    private void handleSignInOptionsButton(ActionEvent actionEvent) {
    }

    @FXML
    private void handleHistory(ActionEvent actionEvent) {
    }

    @FXML
    private void handlePrivacyPolicy(ActionEvent actionEvent) {
    }

    @FXML
    private void handleSave(ActionEvent actionEvent) {
    }
}
