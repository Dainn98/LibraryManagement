package library.management.ui.controllers;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import library.management.data.DAO.ManagerDAO;
import library.management.data.entity.Manager;
import library.management.ui.applications.EmailSender;
import library.management.ui.applications.SecurityCodeGenerator;
import org.controlsfx.control.textfield.CustomPasswordField;

import java.util.regex.Pattern;

import static library.management.alert.AlertMaker.showAlertInformation;

public class SettingsController {

    @FXML
    private TextField phoneNumberTextField;
    @FXML
    private CustomPasswordField confirmPasswordTextField;
    @FXML
    private CustomPasswordField newPasswordTextField;
    @FXML
    private CustomPasswordField currentPassword;
    @FXML
    private TextField secondCode;
    @FXML
    private TextField Phone;
    @FXML
    private BorderPane profileBPane;
    @FXML
    private BorderPane signInOptionsBPane;
    @FXML
    private BorderPane privacyPolicyBPane;
    @FXML
    private TextField email;
    @FXML
    private Label identityCard;
    @FXML
    private TextField name;
    @FXML
    private TextField code;
    @FXML
    private TextField emailTextField;



    private MainController controller;
    private Manager manager;
    private String securityCode = "Invalid Code";
    private boolean canChange = false;
    private boolean canChangeSecurity = false;

    private final int CODE_LENGTH = 6;

    public void setController(MainController controller) {
        this.controller = controller;
        email.setDisable(true);
        Phone.setDisable(true);
        name.setDisable(true);
    }

    public void resetInformation() {
        String identityCard = manager.getIdentityCard();
        String email = manager.getEmail();
        String phone = manager.getPhoneNumber();
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
        if (phone == null || phone.isEmpty()) {
            this.Phone.setText("Not set");
        } else {
            this.Phone.setText(phone);
        }
    }

    public void resetCode() {
        securityCode = "Invalid Code";
    }

    public void resetSecurityInformation() {
        currentPassword.setText(manager.getPassword());
        newPasswordTextField.setText("");
        confirmPasswordTextField.setText("");
        phoneNumberTextField.setText(manager.getPhoneNumber());
        emailTextField.setText(manager.getEmail());
    }

    private void showSection(Object sectionToShow) {
        profileBPane.setVisible(sectionToShow == profileBPane);
        signInOptionsBPane.setVisible(sectionToShow == signInOptionsBPane);
        privacyPolicyBPane.setVisible(sectionToShow == privacyPolicyBPane);
    }

    public void setData() {
        this.manager = controller.getMainManager();
        this.name.setText(manager.getManagerName());
        resetInformation();
    }

    @FXML
    private void handleProfileButton(ActionEvent actionEvent) {
        resetInformation();
        showSection(profileBPane);
    }

    @FXML
    private void handleSignInOptionsButton(ActionEvent actionEvent) {
        resetSecurityInformation();
        showSection(signInOptionsBPane);
    }

    @FXML
    private void handlePrivacyPolicy(ActionEvent actionEvent) {
        showSection(privacyPolicyBPane);
    }

    @FXML
    private void handleSave(ActionEvent actionEvent) {
        if (canChange) {
            String newPhone = Phone.getText();
            String newEmail = email.getText();
            if (!isValidPhoneNumber(newPhone)) {
                showAlertInformation("Error", "Phone number is invalid");
                return;
            }
            if (!newPhone.equals(manager.getPhoneNumber())) {
                if (ManagerDAO.getInstance().checkManagerByPhoneNumber(newPhone)) {
                    showAlertInformation("Error", "Phone number is already in use");
                    return;
                }
            }
            if (!isValidEmail(newEmail)) {
                showAlertInformation("Error", "Email is invalid");
                return;
            }
            if (!newEmail.equals(manager.getEmail())) {
                if (ManagerDAO.getInstance().checkManagerByEmail(newEmail)) {
                    showAlertInformation("Error", "Email is already in use");
                    return;
                }
            }
            manager.setPhoneNumber(newPhone);
            manager.setEmail(newEmail);
            ManagerDAO.getInstance().update(manager);
            showAlertInformation("Success!", "Your information has been saved");
            Phone.setDisable(true);
            email.setDisable(true);
            canChange = false;
        } else {
            showAlertInformation("Error", "Please enter correct code");
        }
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(gmail\\.com|vnu\\.edu\\.vn)$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            return false;
        }
        String phoneNumberPattern = "^(0)(\\d{9})$";

        return phoneNumber.matches(phoneNumberPattern);
    }


    @FXML
    private void handleEditProfile(ActionEvent actionEvent) {
        handleProfileButton(actionEvent);
    }

    @FXML
    private void handleSendCode(ActionEvent actionEvent) {
        sendSecurityCode();
    }

    @FXML
    private void handleCheckCode(KeyEvent keyEvent) {
        if (code.getText().length() != CODE_LENGTH) {
            return;
        }
        if (securityCode.equals("Invalid Code") || !code.getText().equals(securityCode)) {
            showAlertInformation("Incorrect code!", "Please check your code and try again.");
            return;
        }
        if (code.getText().equals(securityCode)) {
            showAlertInformation("Correct code!", "You can change your information now.");
            email.setDisable(false);
            Phone.setDisable(false);
            canChange = true;
            resetCode();
        }
    }

    @FXML
    private void handleCheckSecurityCode(KeyEvent keyEvent) {
        if (secondCode.getText().length() != CODE_LENGTH) {
            return;
        }
        if (securityCode.equals("Invalid Code") || !secondCode.getText().equals(securityCode)) {
            showAlertInformation("Incorrect code!", "Please check your code and try again.");
            return;
        }
        if (secondCode.getText().equals(securityCode)) {
            showAlertInformation("Correct code!", "You can change your information now.");
            canChangeSecurity = true;
            resetCode();
        }
    }

    public void handleMouseEnter(MouseEvent mouseEvent) {
        sendSecurityCode();
    }

    private void sendSecurityCode() {
        if (manager.getEmail().equals("Not set")) {
            showAlertInformation("Error", "Email not available.");
            return;
        }
        securityCode = SecurityCodeGenerator.generateCode(CODE_LENGTH);
        Task<Void> sendEmail = new Task<>() {
            @Override
            protected Void call() throws Exception {
                EmailSender.sendEmail(email.getText(), "Library Management System", "Thank you to use our application. To edit your information, please enter the verification securityCode on the setting screen." +
                        "\nVerification Code: " + securityCode + "." +
                        "\nPlease note that this securityCode can only be used once.");
                return null;
            }
        };
        Thread thread = new Thread(sendEmail);
        thread.setDaemon(true);
        thread.start();
        showAlertInformation("Sending code.", "Your code is going to be sent.");
        sendEmail.setOnSucceeded(e -> {
            showAlertInformation("Send code successfully", "Please check your email.");
        });
    }

    @FXML
    private void handleChangeSecurity(ActionEvent actionEvent) {
        if (canChangeSecurity) {
            if (ManagerDAO.getInstance().checkManager(manager.getManagerName(), currentPassword.getText()) == null) {
                showAlertInformation("Error", "Password is incorrect");
                return;
            }
            if (!newPasswordTextField.getText().isEmpty() || !confirmPasswordTextField.getText().isEmpty()) {
                if (!isValidPassword(newPasswordTextField.getText())) {
                    showAlertInformation("Error", "Password is invalid");
                    return;
                }
                if (!newPasswordTextField.getText().equals(confirmPasswordTextField.getText())) {
                    showAlertInformation("Error", "Confirm password is not match");
                    return;
                }
                manager.setPassword(newPasswordTextField.getText());
            }
            if (!isValidPhoneNumber(phoneNumberTextField.getText())) {
                showAlertInformation("Error", "Phone number is invalid");
                return;
            }
            if (!isValidEmail(emailTextField.getText())) {
                showAlertInformation("Error", "Email is invalid");
                return;
            }
            String newPhone = phoneNumberTextField.getText();
            String newEmail = emailTextField.getText();
            if (!newPhone.equals(manager.getPhoneNumber())) {
                if (ManagerDAO.getInstance().checkManagerByPhoneNumber(newPhone)) {
                    showAlertInformation("Error", "Phone number is already in use");
                    return;
                }
            }
            if (!newEmail.equals(manager.getEmail())) {
                if (ManagerDAO.getInstance().checkManagerByEmail(newEmail)) {
                    showAlertInformation("Error", "Email is already in use");
                    return;
                }
            }
            manager.setPhoneNumber(newPhone);
            manager.setEmail(newEmail);
            ManagerDAO.getInstance().update(manager);
            showAlertInformation("Success!", "Your information has been saved");
            canChangeSecurity = false;
        } else {
            showAlertInformation("Error", "Please enter correct code");
        }
    }

    private boolean isValidPassword(String password) {
        if (password.length() < 6) {
            showAlertInformation("Invalid Password",
                    "The password must be at least six characters long.");
            return false;
        }
        if (!password.matches(".*[a-zA-Z].*")) {
            showAlertInformation("Invalid Password",
                    "The password must contain at least one alphabetic character.");
            return false;
        }
        if (!password.matches(".*[0-9].*")) {
            showAlertInformation("Invalid Password",
                    "The password must contain at least one numeric digit.");
            return false;
        }
        if (!password.matches(".*[!@#$%^&*()_+\\-\\[\\]{};':\"\\\\|,.<>/?].*")) {
            showAlertInformation("Invalid Password",
                    "The password must contain at least one special character.");
            return false;
        }
        return true;
    }
}
