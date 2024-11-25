package library.management.ui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import library.management.data.entity.Manager;

public class SettingsController {

    @FXML
    private TextField email;
    @FXML
    private Label identityCard;
    @FXML
    private TextField name;

    private MainController controller;
    private Manager manager;

    public void setController(MainController controller) {
        this.controller = controller;
    }

    public void setData() {
        this.manager = controller.getMainManager();
        this.name.setText(manager.getManagerName());
        String identityCard = manager.getIdentityCard();
        String email = manager.getEmail();
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
