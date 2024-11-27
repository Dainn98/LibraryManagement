package library.management.ui.controllers;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import library.management.data.DAO.UserDAO;
import library.management.data.entity.User;

import java.util.List;
import java.util.Optional;

import static library.management.alert.AlertMaker.showAlertConfirmation;

public class PendingApprovalsController {
    private final MainController controller;
    private final ObservableList<User> list = FXCollections.observableArrayList();
    private final ObservableList<BooleanProperty> checkBoxStatusList = FXCollections.observableArrayList();
    private final ObservableList<String> usernameSuggestions = FXCollections.observableArrayList();
    private ContextMenu suggestionMenu;

    public PendingApprovalsController(MainController controller) {
        this.controller = controller;
    }

    public void initApprovalsView() {
        controller.approvalsTView.setEditable(true);
        controller.checkBoxApproval.setCellValueFactory(cellData -> {
            int index = controller.approvalsTView.getItems().indexOf(cellData.getValue());
            return checkBoxStatusList.get(index);
        });
        controller.checkBoxApproval.setCellFactory(CheckBoxTableCell.forTableColumn(controller.checkBoxApproval));
        controller.usernameApprovals.setCellValueFactory(new PropertyValueFactory<>("userName"));
        controller.phoneNumberApprovals.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        controller.emailApprovals.setCellValueFactory(new PropertyValueFactory<>("email"));
        controller.countryApprovals.setCellValueFactory(new PropertyValueFactory<>("country"));
        controller.stateApprovals.setCellValueFactory(new PropertyValueFactory<>("state"));
        controller.yearApprovals.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getRegisteredYear())
        );
        initApproveButtonsColumn();
        initializeCheckCountry();
        initializeCheckState();
        initializeCheckYear();
        initializeAutoComplete();
    }

    private void initApproveButtonsColumn() {
        controller.approvalApprovals.setCellFactory(column -> new TableCell<User, Void>() {
            private final Button approveButton = new Button("Approve");
            private final Button disapproveButton = new Button("Disapprove");

            {
                approveButton.setStyle("-fx-background-color: green; -fx-text-fill: white;");
                disapproveButton.setStyle("-fx-background-color: red; -fx-text-fill: white;");

                approveButton.setOnAction(event -> {
                    User user = getTableView().getItems().get(getIndex());
                    Optional<ButtonType> result = showAlertConfirmation(
                            "Approve pending account",
                            "Are you sure you want to approve this account?");
                    if (result.isPresent() && result.get() == ButtonType.OK) {
                        if (UserDAO.getInstance().approve(user) > 0) {
                            loadApprovalsData();
                        } else {
                            System.out.println("loi an approve");
                        }
                    }
                });

                disapproveButton.setOnAction(event -> {
                    User user = getTableView().getItems().get(getIndex());
                    Optional<ButtonType> result = showAlertConfirmation(
                            "Disapprove pending account",
                            "Are you sure you want to disapprove this account?");
                    if (result.isPresent() && result.get() == ButtonType.OK) {
                        if (UserDAO.getInstance().disapprove(user) > 0) {
                            loadApprovalsData();
                        } else {
                            System.out.println("loi an disapprove");
                        }
                    }
                });
            }

            private final HBox buttonBox = new HBox(10, disapproveButton, approveButton);

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(buttonBox);
                }
            }
        });
    }

    public void loadApprovalsData() {
        list.clear();
        list.addAll(UserDAO.getInstance().getPendingUsers());
        controller.approvalsTView.setItems(list);
        initializeCheckBox();
        controller.checkUsername.setText("");
        controller.checkCountry.getCheckModel().check("Check All");
        controller.checkState.getCheckModel().check("Check All");
        controller.checkYear.getCheckModel().check("Check All");
    }

    private void initializeCheckBox() {
        this.checkBoxStatusList.clear();
        for (int i = 0; i < list.size(); i++) {
            this.checkBoxStatusList.add(new SimpleBooleanProperty(false));
        }

        for (BooleanProperty checkBoxStatus : checkBoxStatusList) {
            checkBoxStatus.addListener((observable, oldValue, newValue) -> {
                if (!newValue) {
                    controller.checkApprovals.setSelected(false);
                } else {
                    boolean allSelected = checkBoxStatusList.stream().allMatch(BooleanProperty::get);
                    if (allSelected) {
                        controller.checkApprovals.setSelected(true);
                    }
                }
            });
        }
    }


    public void checkAllPending() {
        boolean isSelected = controller.checkApprovals.isSelected();
        for (BooleanProperty checkBoxStatus : checkBoxStatusList) {
            checkBoxStatus.set(isSelected);
        }
    }

    public void disapprovePending() {
        Optional<ButtonType> result = showAlertConfirmation(
                "Disapprove pending accounts",
                "Are you sure you want to disapprove these accounts?");
        if (result.isPresent() && result.get() == ButtonType.OK) {
            for (int i = 0; i < list.size(); i++) {
                if (checkBoxStatusList.get(i).getValue()) {
                    if (UserDAO.getInstance().disapprove(list.get(i)) < 0) {
                        System.out.println("loi disapprove");
                    }
                }
            }
        }
        loadApprovalsData();
    }

    public void approvePendingUsers() {
        Optional<ButtonType> result = showAlertConfirmation(
                "Approve pending accounts",
                "Are you sure you want to approve these accounts?");
        if (result.isPresent() && result.get() == ButtonType.OK) {
            for (int i = 0; i < list.size(); i++) {
                if (checkBoxStatusList.get(i).getValue()) {
                    if (UserDAO.getInstance().approve(list.get(i)) <= 0) {
                        // todo: co the hien thong bao ra man hinh
                        System.out.println("loi approve");
                    }
                }
            }
        }
        loadApprovalsData();
    }


    public void initializeCheckCountry() {
        ObservableList<String> countries = FXCollections.observableArrayList(UserDAO.getInstance().getAllPendingCountries());
        countries.addFirst("Check All");
        controller.checkCountry.getItems().clear();
        controller.checkCountry.getItems().addAll(countries);

        final boolean[] isProgrammaticallyChanging = {false};

        controller.checkCountry.getCheckModel().getCheckedItems().addListener((ListChangeListener<String>) change -> {
            if (isProgrammaticallyChanging[0]) return;

            while (change.next()) {
                if (change.wasAdded() && change.getAddedSubList().contains("Check All")) {
                    isProgrammaticallyChanging[0] = true;
                    checkAllCountries(true);
                    isProgrammaticallyChanging[0] = false;
                } else if (change.wasRemoved() && change.getRemoved().contains("Check All")) {
                    isProgrammaticallyChanging[0] = true;
                    checkAllCountries(false);
                    isProgrammaticallyChanging[0] = false;
                } else {
                    if (change.wasRemoved()) {
                        isProgrammaticallyChanging[0] = true;
                        controller.checkCountry.getCheckModel().clearCheck(0);
                        isProgrammaticallyChanging[0] = false;
                    }
                }

                boolean allSelected = true;
                for (int i = 1; i < controller.checkCountry.getItems().size(); i++) {
                    if (!controller.checkCountry.getCheckModel().isChecked(i)) {
                        allSelected = false;
                        break;
                    }
                }
                if (allSelected) {
                    isProgrammaticallyChanging[0] = true;
                    controller.checkCountry.getCheckModel().check(0);
                    isProgrammaticallyChanging[0] = false;
                }
            }
            handleSearchPendingUser();
        });

        controller.checkCountry.getCheckModel().check("Check All");
    }

    private void checkAllCountries(boolean check) {
        for (int i = 1; i < controller.checkCountry.getItems().size(); i++) {
            if (check) {
                controller.checkCountry.getCheckModel().check(i);
            } else {
                controller.checkCountry.getCheckModel().clearCheck(i);
            }
        }
        handleSearchPendingUser();
    }

    public void initializeCheckState() {
        ObservableList<String> states = FXCollections.observableArrayList(UserDAO.getInstance().getAllPendingStates());
        states.addFirst("Check All");
        controller.checkState.getItems().clear();
        controller.checkState.getItems().addAll(states);

        final boolean[] isProgrammaticallyChanging = {false};

        controller.checkState.getCheckModel().getCheckedItems().addListener((ListChangeListener<String>) change -> {
            if (isProgrammaticallyChanging[0]) return;

            while (change.next()) {
                if (change.wasAdded() && change.getAddedSubList().contains("Check All")) {
                    isProgrammaticallyChanging[0] = true;
                    checkAllStates(true);
                    isProgrammaticallyChanging[0] = false;
                } else if (change.wasRemoved() && change.getRemoved().contains("Check All")) {
                    isProgrammaticallyChanging[0] = true;
                    checkAllStates(false);
                    isProgrammaticallyChanging[0] = false;
                } else {
                    if (change.wasRemoved()) {
                        isProgrammaticallyChanging[0] = true;
                        controller.checkState.getCheckModel().clearCheck(0);
                        isProgrammaticallyChanging[0] = false;
                    }
                }

                boolean allSelected = true;
                for (int i = 1; i < controller.checkState.getItems().size(); i++) {
                    if (!controller.checkState.getCheckModel().isChecked(i)) {
                        allSelected = false;
                        break;
                    }
                }
                if (allSelected) {
                    isProgrammaticallyChanging[0] = true;
                    controller.checkState.getCheckModel().check(0);
                    isProgrammaticallyChanging[0] = false;
                }
            }
            handleSearchPendingUser();
        });

        controller.checkState.getCheckModel().check("Check All");
    }

    private void checkAllStates(boolean check) {
        for (int i = 1; i < controller.checkState.getItems().size(); i++) {
            if (check) {
                controller.checkState.getCheckModel().check(i);
            } else {
                controller.checkState.getCheckModel().clearCheck(i);
            }
        }
        handleSearchPendingUser();
    }

    public void initializeCheckYear() {
        ObservableList<String> years = FXCollections.observableArrayList(UserDAO.getInstance().getAllPendingYears());
        years.add(0, "Check All");
        controller.checkYear.getItems().clear();
        controller.checkYear.getItems().addAll(years);

        final boolean[] isProgrammaticallyChanging = {false};

        controller.checkYear.getCheckModel().getCheckedItems().addListener((ListChangeListener<String>) change -> {
            if (isProgrammaticallyChanging[0]) return;

            while (change.next()) {
                if (change.wasAdded() && change.getAddedSubList().contains("Check All")) {
                    isProgrammaticallyChanging[0] = true;
                    checkAllYears(true);
                    isProgrammaticallyChanging[0] = false;
                } else if (change.wasRemoved() && change.getRemoved().contains("Check All")) {
                    isProgrammaticallyChanging[0] = true;
                    checkAllYears(false);
                    isProgrammaticallyChanging[0] = false;
                } else {
                    if (change.wasRemoved()) {
                        isProgrammaticallyChanging[0] = true;
                        controller.checkYear.getCheckModel().clearCheck(0);
                        isProgrammaticallyChanging[0] = false;
                    }
                }

                boolean allSelected = true;
                for (int i = 1; i < controller.checkYear.getItems().size(); i++) {
                    if (!controller.checkYear.getCheckModel().isChecked(i)) {
                        allSelected = false;
                        break;
                    }
                }
                if (allSelected) {
                    isProgrammaticallyChanging[0] = true;
                    controller.checkYear.getCheckModel().check(0);
                    isProgrammaticallyChanging[0] = false;
                }
            }
            handleSearchPendingUser();
        });

        controller.checkYear.getCheckModel().check("Check All");
    }

    private void checkAllYears(boolean check) {
        for (int i = 1; i < controller.checkYear.getItems().size(); i++) {
            if (check) {
                controller.checkYear.getCheckModel().check(i);
            } else {
                controller.checkYear.getCheckModel().clearCheck(i);
            }
        }
        handleSearchPendingUser();
    }


    private void initializeAutoComplete() {
        suggestionMenu = new ContextMenu();
        suggestionMenu.setAutoHide(true);
        controller.checkUsername.addEventFilter(KeyEvent.KEY_RELEASED, event -> {
            String query = controller.checkUsername.getText().trim();
            if (query.isEmpty()) {
                suggestionMenu.hide();
            } else {
                updateSuggestions(query);
            }
        });
    }

    private void updateSuggestions(String query) {
        List<String> suggestions = UserDAO.getInstance().getAllPendingUserName(query);
        usernameSuggestions.setAll(suggestions);
        suggestionMenu.getItems().clear();
        for (String suggestion : suggestions) {
            MenuItem item = new MenuItem(suggestion);
            item.setOnAction(event -> {
                controller.checkUsername.setText(suggestion);
                suggestionMenu.hide();
                handleSearchPendingUser();
            });
            suggestionMenu.getItems().add(item);
        }
        if (!usernameSuggestions.isEmpty()) {
            double screenX = controller.checkUsername.localToScreen(0, 0).getX();
            double screenY = controller.checkUsername.localToScreen(0, 0).getY() + controller.checkUsername.getHeight();
            suggestionMenu.hide();
            suggestionMenu.show(controller.checkUsername, screenX, screenY);
        } else {
            suggestionMenu.hide();
        }
    }

    public void handleSearchPendingUser() {
        String nameQuery = controller.checkUsername.getText().trim();
        List<String> selectedCountries = controller.checkCountry.getCheckModel().getCheckedItems();
        List<String> selectedStates = controller.checkState.getCheckModel().getCheckedItems();
        List<String> selectedYears = controller.checkYear.getCheckModel().getCheckedItems();
        List<User> filteredUsers = UserDAO.getInstance().searchPendingUserByFilter(nameQuery, selectedCountries, selectedStates, selectedYears);
        list.clear();
        list.addAll(filteredUsers);
        controller.approvalsTView.setItems(list);
        initializeCheckBox();
    }

}
