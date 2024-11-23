package library.management.ui.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import library.management.data.DAO.LoanDAO;
import library.management.data.entity.Loan;

public class HistoryController {
    private final FullUserController controller;
    private final ObservableList<Loan> list = FXCollections.observableArrayList();

    public HistoryController(FullUserController controller) {
        this.controller = controller;
    }

    public void initIssueDocumentView() {
        controller.docView.setEditable(false);
        controller.docIDDocView.setCellValueFactory(new PropertyValueFactory<>("documentId"));
        controller.docISBNDocView.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getISBN()));
        controller.docNameDocView.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
        controller.docAuthorDocView.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAuthor()));
        controller.docPublisherDocView.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPublisher()));
        controller.categoryDocDocView.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCategory()));
        controller.quantityDocView.setCellValueFactory(new PropertyValueFactory<>("quantityOfBorrow"));
        controller.requestedDateView.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDateOfBorrow().toLocalDate().toString()));
        controller.returnDateView.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getReturnDate().toLocalDate().toString()));
        controller.statusLoanView.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    public void loadHistory() {
        list.clear();
        list.addAll(LoanDAO.getInstance().getHistoryLoan(controller.getMainUserName()));
        controller.docView.setItems(list);
    }
}
