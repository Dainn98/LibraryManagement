package library.management.ui.controllers.user;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import library.management.data.DAO.LoanDAO;
import library.management.data.entity.Loan;

/**
 * Controller class to handle the history of document loans for the user. Manages the initialization
 * of the document table view and loading of the user's loan history.
 */
public class HistoryController extends UserSubController {

  private final ObservableList<Loan> list = FXCollections.observableArrayList();

  public HistoryController(FullUserController controller) {
    this.controller = controller;
  }

  /**
   * Initializes the table view to display the loaned documents and their details. Sets the cell
   * value factories for each column in the table.
   */
  public void initIssueDocumentView() {
    controller.docView.setEditable(false);
    controller.docIDDocView.setCellValueFactory(new PropertyValueFactory<>("documentId"));
    controller.docISBNDocView.setCellValueFactory(
        cellData -> new SimpleStringProperty(cellData.getValue().getISBN()));
    controller.docNameDocView.setCellValueFactory(
        cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
    controller.docAuthorDocView.setCellValueFactory(
        cellData -> new SimpleStringProperty(cellData.getValue().getAuthor()));
    controller.docPublisherDocView.setCellValueFactory(
        cellData -> new SimpleStringProperty(cellData.getValue().getPublisher()));
    controller.categoryDocDocView.setCellValueFactory(
        cellData -> new SimpleStringProperty(cellData.getValue().getCategory()));
    controller.quantityDocView.setCellValueFactory(new PropertyValueFactory<>("quantityOfBorrow"));
    controller.requestedDateView.setCellValueFactory(cellData -> new SimpleStringProperty(
        cellData.getValue().getDateOfBorrow().toLocalDate().toString()));
    controller.returnDateView.setCellValueFactory(cellData -> {
      if (cellData.getValue().getReturnDate() != null) {
        return new SimpleStringProperty(
            cellData.getValue().getReturnDate().toLocalDate().toString());
      } else {
        return new SimpleStringProperty("Not Returned Yet");
      }
    });
    controller.statusLoanView.setCellValueFactory(new PropertyValueFactory<>("status"));
  }

  /**
   * Loads the loan history for the current user. Clears the current list and populates it with the
   * user's loan history.
   */
  public void loadHistory() {
    list.clear();
    list.addAll(LoanDAO.getInstance().getHistoryLoan(controller.getMainUserName()));
    controller.docView.setItems(list);
    initFilterComboBox();
  }

  /**
   * Initializes the filter options for the loan history search. Adds options for filtering by "All
   * ID", "Loan ID", and "Document ID".
   */
  private void initFilterComboBox() {
    ObservableList<String> filters = FXCollections.observableArrayList("All ID", "Loan ID",
        "Document ID");
    controller.historyFilter.setItems(filters);
    controller.historyFilter.setValue("All ID");
  }

  /**
   * Handles the search functionality for the loan history. Filters the history based on the
   * selected filter criteria and search text.
   */
  public void handleSearchHistory() {
    String filterCriteria = controller.historyFilter.getValue();
    String searchText = controller.searchDocTField.getText().trim().toLowerCase();
    list.clear();
    switch (filterCriteria) {
      case "Loan ID":
        list.addAll(
            LoanDAO.getInstance().getHistoryByLoanID(searchText, controller.getMainUserName()));
        break;
      case "Document ID":
        list.addAll(LoanDAO.getInstance()
            .searchHistoryByDocumentId(searchText, controller.getMainUserName()));
        break;
      default:
        list.addAll(
            LoanDAO.getInstance().searchHistoryByKeyWord(searchText, controller.getMainUserName()));
    }
    controller.docView.setItems(list);
  }
}