package library.management.ui.controllers.manager;

import static library.management.alert.AlertMaker.showAlertConfirmation;
import static library.management.alert.AlertMaker.showAlertInformation;

import java.util.Optional;
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

/**
 * The PendingLoanController class extends ManagerSubController and is responsible
 * for managing the pending loans within the system. It provides the necessary
 * methods to initialize the view, handle searches, and manage approval or disapproval
 * actions on loans. The controller interacts with the underlying model to update
 * the loan data and refresh the displayed information in the UI.
 */
public class PendingLoanController extends ManagerSubController {

  /**
   * A list that holds Loan objects, representing the collection of loans
   * being observed for changes, additions, or removals. This list is
   * used within the PendingLoanController to manage loans that are pending
   * approval or disapproval.
   */
  private final ObservableList<Loan> list = FXCollections.observableArrayList();
  /**
   * Represents a list of BooleanProperty objects that track the selection status
   * of checkboxes in a user interface, specifically within the context of managing
   * pending loans. Each BooleanProperty in the list corresponds to a checkbox and
   * is used to observe and modify the state, allowing for interaction with the UI
   * elements that represent pending loan statuses.
   *
   * This observable list facilitates dynamic updates and notifications, ensuring
   * that any change in the checkbox status is immediately reflected in the associated
   * view components. It is particularly useful for batch operations such as approving
   * or disapproving selected loans based on the status of these checkboxes.
   */
  private final ObservableList<BooleanProperty> checkBoxStatusList = FXCollections.observableArrayList();

  /**
   * Constructs a PendingLoanController object that manages the pending loan view.
   *
   * @param controller the main controller that this pending loan controller is part of
   */
  public PendingLoanController(MainController controller) {
    this.controller = controller;
  }

  /**
   * Initializes the Pending Loan View in the user interface.
   *
   * This method configures the loans view table, specifically setting it to be editable,
   * setting up its columns, and initializing control elements such as the approve buttons column
   * and the filter combo box to manage and filter the displayed loan data effectively.
   *
   * It performs the following actions:
   * - Sets the loans view table to be editable to allow user interactions.
   * - Initializes the table columns with appropriate data mappings by invoking setupTableColumns().
   * - Configures the approve and disapprove buttons within the approve buttons column by invoking initApproveButtonsColumn().
   * - Sets up the filter combo box with predefined filter options by invoking initFilterComboBox().
   */
  public void initPendingLoanView() {
    controller.loansView.setEditable(true);
    setupTableColumns();
    initApproveButtonsColumn();
    initFilterComboBox();
  }

  /**
   * Configures the table columns for the loans view in the application. Each column is
   * set up with appropriate cell value and cell factory strategies to display loan
   * information such as loan ID, quantity, document ID, status, user name, borrow date,
   * return date, required return date, and deposit amount.
   *
   * Specifically, it initializes:
   * - A checkbox column for selecting loans, using a cell value factory that links checkboxes
   *   to their corresponding loan entries based on their index within the view.
   * - Several columns with string representations of different loan-related properties
   *   fetched from loan objects, including loan ID, issued ISBN, user status, borrower’s name,
   *   loan date-time, return date, required return date, and any deposit or fee.
   *
   * The column for checking loans also uses a custom cell factory to ensure these cells
   * contain checkboxes.
   */
  private void setupTableColumns() {
    controller.checkLoan.setCellValueFactory(cellData -> {
      int index = controller.loansView.getItems().indexOf(cellData.getValue());
      return index >= 0 && index < checkBoxStatusList.size() ?
          checkBoxStatusList.get(index) :
          new SimpleBooleanProperty(false);
    });
    controller.checkLoan.setCellFactory(CheckBoxTableCell.forTableColumn(controller.checkLoan));
    controller.issuedIDLoansView.setCellValueFactory(new PropertyValueFactory<>("loanID"));
    controller.quantityLoansView.setCellValueFactory(
        new PropertyValueFactory<>("quantityOfBorrow"));
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
        return new SimpleStringProperty(
            cellData.getValue().getReturnDate().toLocalDate().toString());
      } else {
        return new SimpleStringProperty("Not Returned Yet");
      }
    });
    controller.requiredReturnLoansView.setCellValueFactory(cellData -> {
      if (cellData.getValue().getRequiredReturnDate() != null) {
        return new SimpleStringProperty(
            cellData.getValue().getRequiredReturnDate().toLocalDate().toString());
      } else {
        return new SimpleStringProperty("N/A");
      }
    });
    controller.feeIDLoansView.setCellValueFactory(cellData ->
        new SimpleStringProperty(String.format("%.2f", cellData.getValue().getDeposit())));
  }

  /**
   * Handles the action of searching for pending loan issues based on specified filter criteria.
   * The search is performed using the selected filter type and the entered search text.
   * Supported filter types are "Loan ID", "User Name", and "Document ID".
   * If no valid filter type is selected, a default keyword-based search is used.
   * Updates the loans view with the filtered list of pending issues and initializes the associated checkboxes.
   */
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

  /**
   * Initializes the filter combo box used in the pending loans view. This method sets the
   * combo box with a predefined list of filter criteria that can be used for filtering
   * loans. The options include "All ID", "Loan ID", "User Name", and "Document ID".
   * The combo box default selection is set to "All ID".
   */
  private void initFilterComboBox() {
    ObservableList<String> filters = FXCollections.observableArrayList("All ID", "Loan ID",
        "User Name", "Document ID");
    controller.typeLoans.setItems(filters);
    controller.typeLoans.setValue("All ID");
  }

  /**
   * Initializes the column in the loan approval table that contains
   * approve and disapprove buttons for each loan entry.
   * The column is customized to render a set of buttons within an HBox
   * layout for each row in the table.
   * When the approve button is clicked, the corresponding loan's
   * approval is handled. Similarly, the disapprove button triggers
   * the handling of loan rejection.
   */
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

  /**
   * Handles the approval process of a loan based on its current status.
   * If the loan's status is "pending", it checks if the requested document can be borrowed
   * and updates the available copies accordingly. If the loan's status is "pendingReturned",
   * it handles the return process. Alerts are displayed in case of any issues during the process.
   *
   * @param index the index of the loan in the list to be approved
   */
  private void handleApproveLoan(int index) {
    Loan loan = list.get(index);
    Optional<ButtonType> result = showAlertConfirmation("Approve Loan", "Are you sure?");
    if (result.isPresent() && result.get() == ButtonType.OK) {
      if (loan.getStatus().equals("pending")) {
        int canBorrow = DocumentDAO.getInstance()
            .canBeBorrowed(loan.getIntDocumentId(), loan.getQuantityOfBorrow());
        if (canBorrow == Document.NOTAVALABLETOBOROW) {
          showAlertInformation("Cannot Approve", "Document not available.");
        } else if (canBorrow == Document.NOTENOUGHCOPIES) {
          showAlertInformation("Cannot Approve", "Not enough copies.");
        } else if (!DocumentDAO.getInstance()
            .decreaseAvailableCopies(loan.getIntDocumentId(), loan.getQuantityOfBorrow())) {
          showAlertInformation("Cannot Approve", "Error updating document copies.");
        } else if (LoanDAO.getInstance().approve(loan) <= 0) {
          showAlertInformation("Cannot Approve", "Error approving loan.");
          DocumentDAO.getInstance()
              .decreaseAvailableCopies(loan.getIntDocumentId(), -loan.getQuantityOfBorrow());
        } else {
          loadLoanData();
        }
      } else if (loan.getStatus().equals("pendingReturned")) {
        if (!LoanDAO.getInstance().returnDocument(loan)) {
          showAlertConfirmation("Cannot Approve",
              "Error approving pending returned loan for loan ID: " + loan.getLoanID());
        } else {
          loadLoanData();
        }
      }
    }
  }

  /**
   * Handles the disapproval of a loan request. This method checks whether the loan
   * status is "pending" or "pendingReturned" and attempts to disapprove it
   * accordingly. If a loan is disapproved successfully, the loan data is reloaded.
   * If the disapproval fails, an informational alert is displayed.
   *
   * @param index the index of the loan in the list to be disapproved
   */
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

  /**
   * Loads the pending loan data into the user interface.
   *
   * This method clears the current loan list and populates it with data
   * retrieved from the pending loan list available through the LoanDAO instance.
   * It then updates the loans view with this new data set. Additionally,
   * the method initializes checkboxes for further interactions.
   */
  public void loadLoanData() {
    list.clear();
    list.addAll(LoanDAO.getInstance().getPendingLoanList());
    controller.loansView.setItems(list);
    initializeCheckBox();
  }

  /**
   * Initializes the checkBoxStatusList to represent the selection status of check boxes
   * corresponding to items in a list. This method clears any existing statuses in the
   * checkBoxStatusList and populates it with new SimpleBooleanProperty instances, each
   * initialized to 'false', representing an unselected state.
   *
   * Additionally, listeners are added to each BooleanProperty in checkBoxStatusList
   * to monitor changes in selection status. When all check boxes are selected, the
   * 'checkLoans' control in the 'controller' is also set to selected. Conversely, if any
   * check box is deselected, 'checkLoans' is set to deselected.
   */
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

  /**
   * Updates the status of all checkboxes in the checkBoxStatusList based on the state
   * of the `checkLoans` checkbox in the controller. If `checkLoans` is selected,
   * all checkboxes in the list will be marked as selected. Otherwise, they will be
   * deselected.
   */
  public void checkAllPendingIssue() {
    boolean isSelected = controller.checkLoans.isSelected();
    checkBoxStatusList.forEach(checkBoxStatus -> checkBoxStatus.set(isSelected));
  }

  /**
   * Disapproves loans from the list that have a "pending" or "pendingReturned" status.
   * The method prompts the user with a confirmation dialog, and if the user confirms,
   * it iterates over the list of loans and updates their status based on their current
   * status and associated checkbox selection.
   *
   * The method processes the loans as follows:
   * - If the loan status is "pending", it calls the disapprove method from LoanDAO to
   *   disapprove the loan.
   * - If the loan status is "pendingReturned", it calls the disapproveReturn method from
   *   LoanDAO to disapprove the return of the loan.
   *
   * After processing all applicable loans, the method reloads the loan data.
   */
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

  /**
   * Approves pending loan issues for books. This method checks for user confirmation
   * before proceeding with the approval process. It evaluates each loan in the list
   * and attempts to approve it if it is marked as "pending" and the user has selected
   * it. The approval process includes checking the availability of the document for
   * borrowing and decreasing the number of available copies.
   *
   * The method also handles loans marked as "pendingReturned", where it attempts
   * to complete the return process. Various alerts are shown to the user to indicate
   * the success or failure of each operation.
   *
   * The method will reload loan data after attempting the approval process.
   *
   * This method relies on external DAOs (DocumentDAO and LoanDAO) to handle
   * interactions with the data source.
   *
   * Alerts are shown to the user to provide information about each approval attempt.
   * If a loan cannot be approved, specific messages are displayed indicating why
   * the approval failed, such as the document not being available or not having
   * enough copies.
   */
  public void approvePendingIssue() {
    Optional<ButtonType> result = showAlertConfirmation("Approve Loans", "Are you sure?");
    if (result.isPresent() && result.get() == ButtonType.OK) {
      list.stream()
          .filter(loan -> checkBoxStatusList.get(list.indexOf(loan)).get())
          .forEach(loan -> {
            if (loan.getStatus().equals("pending")) {
              int canBorrow = DocumentDAO.getInstance()
                  .canBeBorrowed(loan.getIntDocumentId(), loan.getQuantityOfBorrow());
              if (canBorrow == Document.NOTAVALABLETOBOROW) {
                showAlertInformation("Cannot Approve",
                    "Document not available for loan ID: " + loan.getLoanID());
              } else if (canBorrow == Document.NOTENOUGHCOPIES) {
                showAlertInformation("Cannot Approve",
                    "Not enough copies for loan ID: " + loan.getLoanID());
              } else if (!DocumentDAO.getInstance()
                  .decreaseAvailableCopies(loan.getIntDocumentId(), loan.getQuantityOfBorrow())) {
                showAlertInformation("Cannot Approve",
                    "Error updating document copies for loan ID: " + loan.getLoanID());
              } else if (LoanDAO.getInstance().approve(loan) <= 0) {
                showAlertInformation("Cannot Approve",
                    "Error approving loan for loan ID: " + loan.getLoanID());
                DocumentDAO.getInstance()
                    .decreaseAvailableCopies(loan.getIntDocumentId(), -loan.getQuantityOfBorrow());
              }
            } else if (loan.getStatus().equals("pendingReturned")) {
              if (!LoanDAO.getInstance().returnDocument(loan)) {
                showAlertConfirmation("Cannot Approve",
                    "Error approving pending returned loan for loan ID: " + loan.getLoanID());
              }
            }
          });
    }
    loadLoanData();
  }

}
