package library.management.ui.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyEvent;
import library.management.data.DAO.DocumentDAO;
import library.management.data.DAO.LoanDAO;
import library.management.data.DAO.UserDAO;
import library.management.data.entity.Document;
import library.management.data.entity.Loan;
import library.management.data.entity.User;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static library.management.alert.AlertMaker.showAlertConfirmation;
import static library.management.alert.AlertMaker.showAlertInformation;

public class DocumentManagementController {
    private final MainController controller;
    private ContextMenu suggestionMenu;
    private final ObservableList<String> Suggestions = FXCollections.observableArrayList();
    private static final int MAX_SUGGESTIONS = 5;
    private User user;
    private Document document;


    public DocumentManagementController(MainController controller) {
        this.controller = controller;
    }

    public void initDocumentManagement() {
        initializeAutoCompleteUserName();
        initializeAutoCompleteISBN();
    }

    private void initializeAutoCompleteUserName() {
        suggestionMenu = new ContextMenu();
        suggestionMenu.setAutoHide(true);
        controller.userIDIssue.addEventFilter(KeyEvent.KEY_RELEASED, _ -> {
            String query = controller.userIDIssue.getText().trim();
            if (query.isEmpty()) {
                suggestionMenu.hide();
            } else {
                updateUserNameSuggestions(query);
            }
        });
    }

    private void updateUserNameSuggestions(String query) {
        List<String> suggestions = UserDAO.getInstance().searchApprovedUserNames(query);
        Suggestions.setAll(suggestions);
        suggestionMenu.getItems().clear();
        for (String suggestion : suggestions) {
            MenuItem item = new MenuItem(suggestion);
            item.setOnAction(_ -> {
                controller.userIDIssue.setText(suggestion);
                suggestionMenu.hide();
                handleSearchUserInformation();
            });
            suggestionMenu.getItems().add(item);
        }
        if (!Suggestions.isEmpty()) {
            double screenX = controller.userIDIssue.localToScreen(0, 0).getX();
            double screenY = controller.userIDIssue.localToScreen(0, 0).getY() + controller.userIDIssue.getHeight();
            suggestionMenu.hide();
            suggestionMenu.show(controller.userIDIssue, screenX, screenY);
        } else {
            suggestionMenu.hide();
        }
    }

    private void initializeAutoCompleteISBN() {
        suggestionMenu = new ContextMenu();
        suggestionMenu.setAutoHide(true);
        controller.docISBNIssue.addEventFilter(KeyEvent.KEY_RELEASED, _ -> {
            String query = controller.docISBNIssue.getText().trim();
            if (query.isEmpty()) {
                suggestionMenu.hide();
            } else {
                updateISBNSuggestions(query);
            }
        });
    }

    private void updateISBNSuggestions(String query) {
        List<String> suggestions = DocumentDAO.getInstance().searchISBNByKeyword(query, MAX_SUGGESTIONS);
        Suggestions.setAll(suggestions);
        suggestionMenu.getItems().clear();
        for (String suggestion : suggestions) {
            MenuItem item = new MenuItem(suggestion);
            item.setOnAction(_ -> {
                controller.docISBNIssue.setText(suggestion);
                suggestionMenu.hide();
                handleSearchDocInformation();
            });
            suggestionMenu.getItems().add(item);
        }
        if (!Suggestions.isEmpty()) {
            double screenX = controller.docISBNIssue.localToScreen(0, 0).getX();
            double screenY = controller.docISBNIssue.localToScreen(0, 0).getY() + controller.docISBNIssue.getHeight();
            suggestionMenu.hide();
            suggestionMenu.show(controller.docISBNIssue, screenX, screenY);
        } else {
            suggestionMenu.hide();
        }
    }

    public void handleSearchUserInformation() {
        user = UserDAO.getInstance().searchApprovedUserByExactName(controller.userIDIssue.getText());
        if (user == null) {
            showAlertInformation("Can not find user", "There is no user have user name:" + controller.userIDIssue.getText());
        } else {
            suggestionMenu.hide();
            controller.userNameInfo.setText(user.getUserName());
            controller.emailInfo.setText(user.getEmail());
            controller.phoneNumberInfo.setText(user.getPhoneNumber());
        }
    }

    public void handleSearchDocInformation() {
        document = DocumentDAO.getInstance().searchDocumentByISBN(controller.docISBNIssue.getText());
        if (document == null) {
            showAlertInformation("Can not find document", "There is no document have ISBN:" + controller.docISBNIssue.getText());
        } else {
            suggestionMenu.hide();
            controller.docTitleInfo.setText(document.getTitle());
            controller.docAuthorInfo.setText(document.getTitle());
            controller.docPublisherInfo.setText(document.getPublisher());
            controller.price.setText(String.valueOf(document.getPrice()));
            if (!Objects.equals(document.getAvailability(), "available")) {
                controller.availability.setText("Not available");
            } else {
                controller.availability.setText(String.valueOf(document.getAvailableCopies()));
            }
        }
    }

    public void handleCancelIssue() {
        controller.userNameInfo.setText("User Name");
        controller.emailInfo.setText("Email Address");
        controller.phoneNumberInfo.setText("Phone Number");
        controller.docTitleInfo.setText("Document Title");
        controller.docAuthorInfo.setText("Document Author");
        controller.docPublisherInfo.setText("Document Publisher");
        controller.price.setText("Price");
        controller.availability.setText("Availability");
        controller.userIDIssue.setText("");
        controller.docISBNIssue.setText("");
        controller.quantityDoc.setText("");
        user = null;
        document = null;
    }

    public void handleSubmitIssueDoc() {
        if (user == null) {
            showAlertInformation("Invalid User", "Please enter a valid user!");
            return;
        }
        if (document == null) {
            showAlertInformation("Invalid Document", "Please enter a valid document!");
            return;
        }
        if (controller.availability.getText().equals("Not available")) {
            showAlertInformation("Invalid Document", "This document is not available!");
            return;
        }
        if (controller.quantityDoc.getText().isEmpty() || !isStringAnInteger(controller.quantityDoc.getText())) {
            showAlertInformation("Invalid Quantity", "Please enter a valid quantity!");
            return;
        }
        int borrowedQuantity = Integer.parseInt(controller.quantityDoc.getText());
        if (borrowedQuantity <= 0 || borrowedQuantity > Integer.parseInt(controller.availability.getText())) {
            showAlertInformation("Invalid Quantity", "Please enter a valid quantity!");
            return;
        }
        Optional<ButtonType> result = showAlertConfirmation("Lend document", "Are you sure you want to lend the document?");
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Loan loan = new Loan(user.getUserName(), document.getIntDocumentID(), borrowedQuantity, borrowedQuantity*document.getPrice());
            LoanDAO.getInstance().add(loan);
            DocumentDAO.getInstance().decreaseAvailableCopies(document.getIntDocumentID(), borrowedQuantity);
            showAlertInformation("", "Borrow successfully!");
        }
    }

    public boolean isStringAnInteger(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
