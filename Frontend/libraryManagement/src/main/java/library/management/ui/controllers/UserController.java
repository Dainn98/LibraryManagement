package library.management.ui.controllers;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ButtonType;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import library.management.data.DAO.UserDAO;
import library.management.data.entity.User;

import java.util.Optional;

import static library.management.alert.AlertMaker.showAlertConfirmation;
import static library.management.alert.AlertMaker.showAlertInformation;

public class UserController {
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
