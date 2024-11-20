package library.management.ui.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import library.management.data.DAO.LoanDAO;
import library.management.data.entity.Loan;


public class IssuedDocument {
    private final MainController controller;
    private final ObservableList<Loan> list = FXCollections.observableArrayList();

    public IssuedDocument(MainController controller) {
        this.controller = controller;
    }

    public void initIssueDocumentView() {
        controller.IDView.setEditable(true);
        controller.issuedIDIDView.setCellValueFactory(new PropertyValueFactory<>("loanID"));
        controller.docIDIDView.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getIssuedISBN()))
        );
        controller.docTitleIDView.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getIssuedTitle()))
        );
        controller.userIDIDView.setCellValueFactory(new PropertyValueFactory<>("userId"));
        controller.userNameIDView.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getUserName()))
        );
        controller.issuedDateAndTimeIDView.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getDateOfBorrow().toLocalDate().toString())
        );
        controller.dueDateIDView.setCellValueFactory(cellData -> {
            if (cellData.getValue().getReturnDate() != null) {
                return new SimpleStringProperty(cellData.getValue().getReturnDate().toLocalDate().toString());
            } else {
                return new SimpleStringProperty("Not Yet");
            }
        });
        controller.daysIDView.setCellValueFactory(cellData -> {
            long days = java.time.temporal.ChronoUnit.DAYS.between(
                    cellData.getValue().getDateOfBorrow(),
                    cellData.getValue().getRequiredReturnDate()
            );
            return new SimpleStringProperty(String.valueOf(days));
        });
        controller.feeIDView.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.format("%.2f", cellData.getValue().getDeposit()))
        );
        controller.statusIDView.setCellValueFactory(new PropertyValueFactory<>("status"));
        initFilterComboBox();
    }

    public void handleSearchID() {
        String filterCriteria = controller.issueTypeIssuedDoc.getValue();
        String searchText = controller.issuedDocField.getText().trim().toLowerCase();
        list.clear();
        switch (filterCriteria) {
            case "Loan ID":
                list.addAll(LoanDAO.getInstance().searchHandledByLoanId(searchText));
                break;
            case "User ID":
                list.addAll(LoanDAO.getInstance().searchHandledByUserId(searchText));
                break;
            case "Document ID":
                list.addAll(LoanDAO.getInstance().searchHandledByDocumentId(searchText));
                break;
            default:
                list.addAll(LoanDAO.getInstance().searchHandledIssueByKeyWord(searchText));
        }
        controller.IDView.setItems(list);
    }

    private void initFilterComboBox() {
        ObservableList<String> userFilters = FXCollections.observableArrayList("All ID", "Loan ID", "User ID", "Document ID");
        controller.issueTypeIssuedDoc.setItems(userFilters);
        controller.issueTypeIssuedDoc.setValue("All ID");
    }

    public void loadLoanData() {
        list.clear();
        list.addAll(LoanDAO.getInstance().getHandledLoanList());
        controller.IDView.setItems(list);
    }
}
