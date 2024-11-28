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
        setupTableColumns();
        initApproveButtonsColumn();
        initFilterComboBox();
    }

    private void setupTableColumns() {
        controller.checkLoan.setCellValueFactory(cellData -> {
            int index = controller.loansView.getItems().indexOf(cellData.getValue());
            return index >= 0 && index < checkBoxStatusList.size() ?
                    checkBoxStatusList.get(index) :
                    new SimpleBooleanProperty(false);
        });
        controller.checkLoan.setCellFactory(CheckBoxTableCell.forTableColumn(controller.checkLoan));
        controller.issuedIDLoansView.setCellValueFactory(new PropertyValueFactory<>("loanID"));
        controller.quantityLoansView.setCellValueFactory(new PropertyValueFactory<>("quantityOfBorrow"));
        controller.docIDLoansView.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getIssuedISBN()));
        controller.docStatusLoansView.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getStatus()));
        controller.userNameLoansView.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getUserName()));
        controller.issuedDateAndTimeLoansView.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getDateOfBorrow().toLocalDate().toString()));
        controller.returnDateIDLoansView.setCellValueFactory(cellData -> {
            if (cellData.getValue().getReturnDate() != null) {
                return new SimpleStringProperty(cellData.getValue().getReturnDate().toLocalDate().toString());
            } else {
                return new SimpleStringProperty("Not Returned Yet");
            }
        });
        controller.requiredReturnLoansView.setCellValueFactory(cellData -> {
            if (cellData.getValue().getRequiredReturnDate() != null) {
                return new SimpleStringProperty(cellData.getValue().getRequiredReturnDate().toLocalDate().toString());
            } else {
                return new SimpleStringProperty("N/A");
            }
        });
        controller.feeIDLoansView.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.format("%.2f", cellData.getValue().getDeposit())));
    }

    public void handleSearchPendingIssue() {
        String filterCriteria = controller.typeLoans.getValue();
        String searchText = controller.loansField.getText().trim().toLowerCase();
        list.clear();
        switch (filterCriteria) {
            case "Loan ID":
                list.addAll(LoanDAO.getInstance().searchPendingByLoanId(searchText));
                break;
            case "User Name":
                list.addAll(LoanDAO.getInstance().searchPendingByUserName(searchText));
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
        ObservableList<String> filters = FXCollections.observableArrayList("All ID", "Loan ID", "User Name", "Document ID");
        controller.typeLoans.setItems(filters);
        controller.typeLoans.setValue("All ID");
    }

    private void initApproveButtonsColumn() {
        controller.approvalLoansView.setCellFactory(column -> new TableCell<Loan, Void>() {
            private final Button approveButton = createApproveButton();
            private final Button disapproveButton = createDisapproveButton();
            private final HBox buttonBox = new HBox(10, disapproveButton, approveButton);

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : buttonBox);
            }

            private Button createApproveButton() {
                Button button = new Button("Approve");
                button.setStyle("-fx-background-color: green; -fx-text-fill: white;");
                button.setOnAction(event -> handleApproveLoan(getIndex()));
                return button;
            }

            private Button createDisapproveButton() {
                Button button = new Button("Disapprove");
                button.setStyle("-fx-background-color: red; -fx-text-fill: white;");
                button.setOnAction(event -> handleDisapproveLoan(getIndex()));
                return button;
            }
        });
    }

    private void handleApproveLoan(int index) {
        Loan loan = list.get(index);
        Optional<ButtonType> result = showAlertConfirmation("Approve Loan", "Are you sure?");
        if (result.isPresent() && result.get() == ButtonType.OK) {
            if (loan.getStatus().equals("pending")) {
                int canBorrow = DocumentDAO.getInstance().canBeBorrowed(loan.getIntDocumentId(), loan.getQuantityOfBorrow());
                if (canBorrow == Document.NOTAVALABLETOBOROW) {
                    showAlertInformation("Cannot Approve", "Document not available.");
                } else if (canBorrow == Document.NOTENOUGHCOPIES) {
                    showAlertInformation("Cannot Approve", "Not enough copies.");
                } else if (!DocumentDAO.getInstance().decreaseAvailableCopies(loan.getIntDocumentId(), loan.getQuantityOfBorrow())) {
                    showAlertInformation("Cannot Approve", "Error updating document copies.");
                } else if (LoanDAO.getInstance().approve(loan) <= 0) {
                    showAlertInformation("Cannot Approve", "Error approving loan.");
                    DocumentDAO.getInstance().decreaseAvailableCopies(loan.getIntDocumentId(), -loan.getQuantityOfBorrow());
                } else {
                    loadLoanData();
                }
            } else if (loan.getStatus().equals("pendingReturned")) {
                if (!LoanDAO.getInstance().returnDocument(loan)) {
                    showAlertConfirmation("Cannot Approve", "Error approving pending returned loan for loan ID: " + loan.getLoanID());
                } else {
                    loadLoanData();
                }
            }
        }
    }

    private void handleDisapproveLoan(int index) {
        Loan loan = list.get(index);
        Optional<ButtonType> result = showAlertConfirmation("Disapprove Loan", "Are you sure?");
        if (result.isPresent() && result.get() == ButtonType.OK) {
            if (loan.getStatus().equals("pending")) {
                if (LoanDAO.getInstance().disapprove(loan) <= 0) {
                    showAlertInformation("Cannot Disapprove", "Error disapproving pending loan.");
                } else {
                    loadLoanData();
                }
            } else if (loan.getStatus().equals("pendingReturned")) {
                if (LoanDAO.getInstance().disapproveReturn(loan) <= 0) {
                    showAlertInformation("Cannot Disapprove", "Error disapproving pending returned loan.");
                } else {
                    loadLoanData();
                }
            }
        }
    }

    public void loadLoanData() {
        list.clear();
        list.addAll(LoanDAO.getInstance().getPendingLoanList());
        controller.loansView.setItems(list);
        initializeCheckBox();
    }

    private void initializeCheckBox() {
        checkBoxStatusList.clear();
        for (int i = 0; i < list.size(); i++) {
            checkBoxStatusList.add(new SimpleBooleanProperty(false));
        }
        for (BooleanProperty checkBoxStatus : checkBoxStatusList) {
            checkBoxStatus.addListener((observable, oldValue, newValue) -> {
                if (!newValue) {
                    controller.checkLoans.setSelected(false);
                } else if (checkBoxStatusList.stream().allMatch(BooleanProperty::get)) {
                    controller.checkLoans.setSelected(true);
                }
            });
        }
    }

    public void checkAllPendingIssue() {
        boolean isSelected = controller.checkLoans.isSelected();
        checkBoxStatusList.forEach(checkBoxStatus -> checkBoxStatus.set(isSelected));
    }

    public void disapprovePendingIssue() {
        Optional<ButtonType> result = showAlertConfirmation("Disapprove Loans", "Are you sure?");
        if (result.isPresent() && result.get() == ButtonType.OK) {
            list.stream()
                    .filter(loan -> checkBoxStatusList.get(list.indexOf(loan)).get())
                    .forEach(loan -> {
                        if (loan.getStatus().equals("pending")) {
                            LoanDAO.getInstance().disapprove(loan);
                        } else if (loan.getStatus().equals("pendingReturned")) {
                            LoanDAO.getInstance().disapproveReturn(loan);
                        }
                    });
        }
        loadLoanData();
    }

    public void approvePendingIssue() {
        Optional<ButtonType> result = showAlertConfirmation("Approve Loans", "Are you sure?");
        if (result.isPresent() && result.get() == ButtonType.OK) {
            list.stream()
                    .filter(loan -> checkBoxStatusList.get(list.indexOf(loan)).get())
                    .forEach(loan -> {
                        if (loan.getStatus().equals("pending")) {
                            int canBorrow = DocumentDAO.getInstance().canBeBorrowed(loan.getIntDocumentId(), loan.getQuantityOfBorrow());
                            if (canBorrow == Document.NOTAVALABLETOBOROW) {
                                showAlertInformation("Cannot Approve", "Document not available for loan ID: " + loan.getLoanID());
                            } else if (canBorrow == Document.NOTENOUGHCOPIES) {
                                showAlertInformation("Cannot Approve", "Not enough copies for loan ID: " + loan.getLoanID());
                            } else if (!DocumentDAO.getInstance().decreaseAvailableCopies(loan.getIntDocumentId(), loan.getQuantityOfBorrow())) {
                                showAlertInformation("Cannot Approve", "Error updating document copies for loan ID: " + loan.getLoanID());
                            } else if (LoanDAO.getInstance().approve(loan) <= 0) {
                                showAlertInformation("Cannot Approve", "Error approving loan for loan ID: " + loan.getLoanID());
                                DocumentDAO.getInstance().decreaseAvailableCopies(loan.getIntDocumentId(), -loan.getQuantityOfBorrow());
                            }
                        } else if (loan.getStatus().equals("pendingReturned")) {
                            if (!LoanDAO.getInstance().returnDocument(loan)) {
                                showAlertConfirmation("Cannot Approve", "Error approving pending returned loan for loan ID: " + loan.getLoanID());
                            }
                        }
                    });
        }
        loadLoanData();
    }

}
