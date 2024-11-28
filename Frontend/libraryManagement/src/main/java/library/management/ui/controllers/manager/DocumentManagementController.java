package library.management.ui.controllers.manager;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
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

public class DocumentManagementController extends ManagerSubController {
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

    public static class UserController {
        private final MainController controller;
        private final ObservableList<User> list = FXCollections.observableArrayList();
        private final ObservableList<BooleanProperty> checkBoxStatusList = FXCollections.observableArrayList();

        public UserController(MainController controller) {
            this.controller = controller;
        }

        public void initUsersView() {
            controller.userNameUserView.setCellValueFactory(new PropertyValueFactory<>("userName"));
            controller.userPhoneUserView.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
            controller.userEmailUserView.setCellValueFactory(new PropertyValueFactory<>("email"));
            controller.checkUserView.setCellValueFactory(cellData -> {
                int index = controller.userView.getItems().indexOf(cellData.getValue());
                return checkBoxStatusList.get(index);
            });
            controller.checkUserView.setCellFactory(CheckBoxTableCell.forTableColumn(controller.checkUserView));
            initFilterComboBox();
        }

        private void initFilterComboBox() {
            ObservableList<String> userFilters = FXCollections.observableArrayList("All", "ID", "Name", "Phone Number", "Email");
            controller.userFilterComboBox.setItems(userFilters);
            controller.userFilterComboBox.setValue("All");
        }

        public void loadUserViewData() {
            list.clear();
            list.addAll(UserDAO.getInstance().getAllApprovedUsers());
            controller.userView.setItems(list);
            initializeCheckBox();
        }

        public void fetchUserDetails() {
            User user = controller.userView.getSelectionModel().getSelectedItem();
            if (user != null) {
                controller.userNameField.setText(user.getUserName());
                controller.userPhoneField.setText(user.getPhoneNumber());
                controller.userEmailField.setText(user.getEmail());
            }
        }

        public void handleCancelUserButton() {
            controller.userNameField.setText("");
            controller.userPhoneField.setText("");
            controller.userEmailField.setText("");
        }

        public void checkAllUsers() {
            boolean isSelected = controller.checkAllUsersView.isSelected();
            for (BooleanProperty checkBoxStatus : checkBoxStatusList) {
                checkBoxStatus.set(isSelected);
            }
        }

        private void initializeCheckBox() {
            checkBoxStatusList.clear();
            for (int i = 0; i < list.size(); i++) {
                checkBoxStatusList.add(new SimpleBooleanProperty(false));
            }
            for (BooleanProperty checkBoxStatus : checkBoxStatusList) {
                checkBoxStatus.addListener((observable, oldValue, newValue) -> {
                    if (!newValue) {
                        controller.checkAllUsersView.setSelected(false);
                    } else {
                        boolean allSelected = checkBoxStatusList.stream().allMatch(BooleanProperty::get);
                        if (allSelected) {
                            controller.checkAllUsersView.setSelected(true);
                        }
                    }
                });
            }
        }


        public void searchUserDetails() {
            String filterCriteria = controller.userFilterComboBox.getValue();
            String searchText = controller.searchUserField.getText().trim().toLowerCase();
            list.clear();
            switch (filterCriteria) {
                case "Name":
                    list.addAll(UserDAO.getInstance().searchApprovedUserByName(searchText));
                    break;
                case "Phone Number":
                    list.addAll(UserDAO.getInstance().searchApprovedUserByPhoneNumber(searchText));
                    break;
                case "Email":
                    list.addAll(UserDAO.getInstance().searchApprovedUserByEmail(searchText));
                    break;
                default:
                    list.addAll(UserDAO.getInstance().searchApprovedUsersByKeyword(searchText));
            }
            controller.userView.setItems(list);
            initializeCheckBox();
        }

        public void deleteUsersRecord() {
            // todo: xu ly truong hop user dang giu sach thi phai lam sao
            Optional<ButtonType> result = showAlertConfirmation(
                    "Delete user",
                    "Are you sure you want to delete these users?");
            if (result.isPresent() && result.get() == ButtonType.OK) {
                for (int i = 0; i < list.size(); i++) {
                    if (checkBoxStatusList.get(i).getValue()) {
                        UserDAO.getInstance().delete(list.get(i));
                    }
                }
                loadUserViewData();
                handleCancelUserButton();
            }
        }

        public void deleteOneUserRecord() {
            // todo: xu ly truong hop user dang giu sach thi phai lam sao
            Optional<ButtonType> result = showAlertConfirmation(
                    "Delete user",
                    "Are you sure you want to delete this user?");
            if (result.isPresent() && result.get() == ButtonType.OK) {
                User user = controller.userView.getSelectionModel().getSelectedItem();
                UserDAO.getInstance().delete(user);
                loadUserViewData();
                handleCancelUserButton();
            }
        }

        public void handleSaveUserButton() {
            // todo: kiem tra thong tin thay doi co dung cac yeu cau du lieu khong
            Optional<ButtonType> result = showAlertConfirmation(
                    "Update user",
                    "Are you sure you want to change user's information?");
            if (result.isPresent() && result.get() == ButtonType.OK) {
                User changedUser = new User(controller.userView.getSelectionModel().getSelectedItem());
                changedUser.setUserName(controller.userNameField.getText());
                changedUser.setPhoneNumber(controller.userPhoneField.getText());
                changedUser.setEmail(controller.userEmailField.getText());
                if (UserDAO.getInstance().doesUserNameExist(changedUser.getUserName())) {
                    showAlertInformation("Update user information fail!", "This user already exists.");
                } else {
                    UserDAO.getInstance().update(changedUser);
                    showAlertInformation("Update user information!", "User updated successfully.");
                    handleCancelUserButton();
                    loadUserViewData();
                }
            }
        }
    }
}
