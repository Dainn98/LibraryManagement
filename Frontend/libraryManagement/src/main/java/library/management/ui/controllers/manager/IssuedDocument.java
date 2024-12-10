package library.management.ui.controllers.manager;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import library.management.data.DAO.LoanDAO;
import library.management.data.entity.Loan;

/**
 * The IssuedDocument class offers functionality to manage and display a set of loan
 * instances within a user interface, utilizing the functionalities of JavaFX for
 * dynamic content updates and interactions. It extends the ManagerSubController
 * to gain broader management capabilities alongside the MainController,
 * which it uses for initializing and controlling view components.
 */
public class IssuedDocument extends ManagerSubController {

  /**
   * Represents an observable list of Loan objects within the IssuedDocument class.
   * This list is used to store and manage Loan instances, allowing for dynamic updates
   * and observer notifications. It is initialized as an ObservableList using
   * FXCollections.observableArrayList().
   */
  private final ObservableList<Loan> list = FXCollections.observableArrayList();

  /**
   * Constructs an IssuedDocument object with the specified MainController.
   *
   * @param controller the main controller used to manage the issued document's operations
   */
  public IssuedDocument(MainController controller) {
    this.controller = controller;
  }

  /**
   * Initializes the issue document view by setting up the cell value factories
   * for various table columns with properties from the loan data model.
   * This method configures the table view to display relevant information about
   * issued documents including loan ID, document ISBN, title, user name,
   * dates of borrowing and due, the number of days for the loan, the fee,
   * and the loan status. Additionally, it initializes the filter combo box
   * for filtering the table view content.
   */
  public void initIssueDocumentView() {
    controller.IDView.setEditable(true);
    controller.issuedIDIDView.setCellValueFactory(new PropertyValueFactory<>("loanID"));
    controller.docIDIDView.setCellValueFactory(cellData ->
        new SimpleStringProperty(String.valueOf(cellData.getValue().getIssuedISBN()))
    );
    controller.docTitleIDView.setCellValueFactory(cellData ->
        new SimpleStringProperty(String.valueOf(cellData.getValue().getIssuedTitle()))
    );
    controller.userNameIDView.setCellValueFactory(cellData ->
        new SimpleStringProperty(cellData.getValue().getUserName())
    );
    controller.issuedDateAndTimeIDView.setCellValueFactory(cellData ->
        new SimpleStringProperty(cellData.getValue().getDateOfBorrow().toLocalDate().toString())
    );
    controller.dueDateIDView.setCellValueFactory(cellData -> {
      if (cellData.getValue().getReturnDate() != null) {
        return new SimpleStringProperty(
            cellData.getValue().getReturnDate().toLocalDate().toString());
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

  /**
   * Handles the search operation based on the selected filter criteria and search text.
   * The method retrieves the selected filter criteria from the user interface and
   * normalizes the search text by trimming and converting it to lowercase.
   * It then clears the current list and performs a search based on the filter criteria:
   *
   * - If the filter criteria is "Loan ID", it searches by loan ID.
   * - If the filter criteria is "User Name", it searches by user name.
   * - If the filter criteria is "Document ID", it searches by document ID.
   * - For any other filter criteria, it performs a keyword search.
   *
   * The search results are then added to the list, which updates the relevant view in the user interface.
   */
  public void handleSearchID() {
    String filterCriteria = controller.issueTypeIssuedDoc.getValue();
    String searchText = controller.issuedDocField.getText().trim().toLowerCase();
    list.clear();
    switch (filterCriteria) {
      case "Loan ID":
        list.addAll(LoanDAO.getInstance().searchHandledByLoanId(searchText));
        break;
      case "User Name":
        list.addAll(LoanDAO.getInstance().searchHandledByUserName(searchText));
        break;
      case "Document ID":
        list.addAll(LoanDAO.getInstance().searchHandledByDocumentId(searchText));
        break;
      default:
        list.addAll(LoanDAO.getInstance().searchHandledIssueByKeyWord(searchText));
    }
    controller.IDView.setItems(list);
  }

  /**
   * Initializes the filter combo box for the issued document view. This method sets up
   * an observable list with predefined filter options, which include various identifiers,
   * and assigns it to the combo box for issue type selection. The combo box is then set
   * to default to "All ID", providing a comprehensive view of all available options.
   */
  private void initFilterComboBox() {
    ObservableList<String> userFilters = FXCollections.observableArrayList("All ID", "Loan ID",
        "User Name", "Document ID");
    controller.issueTypeIssuedDoc.setItems(userFilters);
    controller.issueTypeIssuedDoc.setValue("All ID");
  }

  /**
   * Loads and updates the view with the current list of handled loans.
   *
   * Retrieves the list of handled loans from the data access object (DAO)
   * and updates the internal list storage. Subsequently, refreshes the user
   * interface to display the updated loan information.
   *
   * This method clears any existing data in the list before loading new data,
   * ensuring that the most recent and relevant loan information is displayed
   * in the `IDView` associated with the given controller.
   */
  public void loadLoanData() {
    list.clear();
    list.addAll(LoanDAO.getInstance().getHandledLoanList());
    controller.IDView.setItems(list);
  }
}
