package library.management.ui.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import library.management.data.entity.Loan;
import javafx.beans.property.SimpleStringProperty;
import library.management.data.DAO.LoanDAO;
public class historyController {
    private final fullUserController controller;
    private final ObservableList<Loan> list = FXCollections.observableArrayList();

    public historyController(fullUserController controller) {
        this.controller = controller;
    }
    public void initIssueDocumentView() {

    }

    public void handleSearchID() {
    }

}
