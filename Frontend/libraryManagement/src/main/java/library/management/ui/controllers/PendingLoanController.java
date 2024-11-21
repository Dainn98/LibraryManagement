package library.management.ui.controllers;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import library.management.data.DAO.DocumentDAO;
import library.management.data.DAO.LoanDAO;
import library.management.data.entity.Document;
import library.management.data.entity.Loan;

import java.util.Optional;

import static library.management.alert.AlertMaker.showAlertConfirmation;
import static library.management.alert.AlertMaker.showAlertInformation;

public class PendingLoanController {
    private final MainController controller;
    private final ObservableList<Loan> list = FXCollections.observableArrayList();
    private final ObservableList<BooleanProperty> checkBoxStatusList = FXCollections.observableArrayList();

    public PendingLoanController(MainController controller) {
        this.controller = controller;
    }

    public void initPendingLoanView() {
        controller.loansView.setEditable(true);
        controller.checkLoan.setCellValueFactory(cellData -> {
            int index = controller.loansView.getItems().indexOf(cellData.getValue());
            if (index >= 0 && index < checkBoxStatusList.size()) {
                return checkBoxStatusList.get(index);
            } else {
                return new SimpleBooleanProperty(false);
            }
        });

        controller.checkLoan.setCellFactory(CheckBoxTableCell.forTableColumn(controller.checkLoan));
        controller.issuedIDLoansView.setCellValueFactory(new PropertyValueFactory<>("loanID"));
        controller.docIDLoansView.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getIssuedISBN()))
        );
        controller.docTitleLoansView.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getIssuedTitle()))
        );
        controller.userIDLoansView.setCellValueFactory(new PropertyValueFactory<>("userId"));
        controller.userNameLoansView.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getUserName()))
        );
        controller.issuedDateAndTimeLoansView.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getDateOfBorrow().toLocalDate().toString())
        );
        controller.dueDateIDLoansView.setCellValueFactory(cellData -> {
            if (cellData.getValue().getReturnDate() != null) {
                return new SimpleStringProperty(cellData.getValue().getReturnDate().toLocalDate().toString());
            } else {
                return new SimpleStringProperty("Not Returned Yet");
            }
        });
        controller.daysLoansView.setCellValueFactory(cellData -> {
            long days = java.time.temporal.ChronoUnit.DAYS.between(
                    cellData.getValue().getDateOfBorrow(),
                    cellData.getValue().getRequiredReturnDate()
            );
            return new SimpleStringProperty(String.valueOf(days));
        });
        controller.feeIDLoansView.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.format("%.2f", cellData.getValue().getDeposit()))
        );
        initApproveButtonsColumn();
        initFilterComboBox();
    }

    public void handleSearchPendingIssue() {
        String filterCriteria = controller.typeLoans.getValue();
        String searchText = controller.loansField.getText().trim().toLowerCase();
        list.clear();
        switch (filterCriteria) {
            case "Loan ID":
                list.addAll(LoanDAO.getInstance().searchPendingByLoanId(searchText));
                break;
            case "User ID":
                list.addAll(LoanDAO.getInstance().searchPendingByUserId(searchText));
                break;
            case "Document ID":
                list.addAll(LoanDAO.getInstance().searchPendingByDocumentId(searchText));
                break;
            default:
                list.addAll(LoanDAO.getInstance().searchPendingIssueByKeyWord(searchText));
        }
        controller.loansView.setItems(list);
        initializeCheckBox();
    }

    private void initFilterComboBox() {
        ObservableList<String> userFilters = FXCollections.observableArrayList("All ID", "Loan ID", "User ID", "Document ID");
        controller.typeLoans.setItems(userFilters);
        controller.typeLoans.setValue("All ID");
    }

    private void initApproveButtonsColumn() {
        controller.approvalLoansView.setCellFactory(column -> new TableCell<Loan, Void>() {
            private final Button approveButton = new Button("Approve");
            private final Button disapproveButton = new Button("Disapprove");

            {
                approveButton.setStyle("-fx-background-color: green; -fx-text-fill: white;");
                disapproveButton.setStyle("-fx-background-color: red; -fx-text-fill: white;");

                approveButton.setOnAction(event -> {
                    Loan loan = getTableView().getItems().get(getIndex());
                    Optional<ButtonType> result = showAlertConfirmation(
                            "Approve pending issue",
                            "Are you sure you want to approve this issue?");
                    if (result.isPresent() && result.get() == ButtonType.OK) {
                        int canBoBorrowed = DocumentDAO.getInstance().canBeBorrowed(loan.getIntDocumentId(), loan.getQuantityOfBorrow());
                        if (canBoBorrowed == Document.NOTAVALABLETOBOROW) {
                            showAlertInformation("Unable to lend this document!",
                                    "This document is not available to be borrowed!");
                        } else if (canBoBorrowed == Document.NOTENOUGHCOPIES) {
                            showAlertInformation("Unable to lend this document!",
                                    "There are not enough copies of this document to be borrowed!");
                        } else if (!DocumentDAO.getInstance().decreaseAvailableCopies(loan.getIntDocumentId(), loan.getQuantityOfBorrow())) {
                            showAlertInformation("Unable to lend this document!",
                                    "Something went wrong while trying to lend this document!");
                        } else if (LoanDAO.getInstance().approve(loan) <= 0) {
                            showAlertInformation("Unable to lend this document!",
                                    "Something went wrong!");
                            DocumentDAO.getInstance().decreaseAvailableCopies(loan.getIntDocumentId(), -loan.getQuantityOfBorrow());
                        } else {
                            loadLoanData();
                        }
                    }
                });

                disapproveButton.setOnAction(event -> {
                    Loan loan = getTableView().getItems().get(getIndex());
                    Optional<ButtonType> result = showAlertConfirmation(
                            "Disapprove pending issue",
                            "Are you sure you want to disapprove this issue?");
                    if (result.isPresent() && result.get() == ButtonType.OK) {
                        if (LoanDAO.getInstance().disapprove(loan) <= 0) {
                            showAlertInformation("Unable to disapprove this document!",
                                    "Something went wrong!");
                        } else {
                            loadLoanData();
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

    public void loadLoanData() {
        list.clear();
        list.addAll(LoanDAO.getInstance().getPendingLoanList());
        controller.loansView.setItems(list);
        initializeCheckBox();
    }

    private void initializeCheckBox() {
        this.checkBoxStatusList.clear();
        for (int i = 0; i < list.size(); i++) {
            this.checkBoxStatusList.add(new SimpleBooleanProperty(false));
        }
        for (BooleanProperty checkBoxStatus : checkBoxStatusList) {
            checkBoxStatus.addListener((observable, oldValue, newValue) -> {
                if (!newValue) {
                    controller.checkLoans.setSelected(false);
                } else {
                    boolean allSelected = checkBoxStatusList.stream().allMatch(BooleanProperty::get);
                    if (allSelected) {
                        controller.checkLoans.setSelected(true);
                    }
                }
            });
        }
    }

    public void checkAllPendingIssue() {
        boolean isSelected = controller.checkLoans.isSelected();
        for (BooleanProperty checkBoxStatus : checkBoxStatusList) {
            checkBoxStatus.set(isSelected);
        }
    }

    public void disapprovePendingIssue() {
        Optional<ButtonType> result = showAlertConfirmation(
                "Disapprove pending loans",
                "Are you sure you want to disapprove these loans?");
        if (result.isPresent() && result.get() == ButtonType.OK) {
            for (int i = 0; i < list.size(); i++) {
                if (checkBoxStatusList.get(i).getValue()) {
                    if (LoanDAO.getInstance().disapprove(list.get(i)) < 0) {
                        System.out.println("loi disapprove loan");
                    }
                }
            }
        }
        loadLoanData();
    }

    public void approvePendingIssue() {
        Optional<ButtonType> result = showAlertConfirmation(
                "Approve pending loans",
                "Are you sure you want to approve these loans?");
        if (result.isPresent() && result.get() == ButtonType.OK) {
            for (int i = 0; i < list.size(); i++) {
                if (checkBoxStatusList.get(i).getValue()) {
                    if (LoanDAO.getInstance().approve(list.get(i)) < 0) {
                        System.out.println("loi approve loan");
                    }
                }
            }
        }
        loadLoanData();
    }
}
