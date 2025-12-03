package library.management.ui.controllers.manager;

import static library.management.alert.AlertMaker.showAlertConfirmation;
import static library.management.alert.AlertMaker.showAlertInformation;

import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.ButtonType;
import library.management.data.DAO.LoanDAO;
import library.management.data.entity.Loan;

public class ReturnDocumentController extends ManagerSubController {

  private final ObservableList<Loan> list = FXCollections.observableArrayList();

  /**
   * Constructs a new ReturnDocumentController with the specified main controller.
   *
   * @param controller the MainController instance that this ReturnDocumentController will interact
   *                   with
   */
  public ReturnDocumentController(MainController controller) {
    this.controller = controller;
  }

  /**
   * Initializes the return document setup by configuring the combo box and setting up a listener
   * for changes in checked items within the issue type combo box. When an item in the combo box is
   * checked or unchecked, the method triggers a search for loans based on the selected criteria.
   * <p>
   * This method relies on the `initComboBox` method to set up the combo box with predefined loan
   * issue types and marks all of them as initially checked. It also registers a listener on the
   * checked items collection of the combo box to invoke the `searchLoanByID` method whenever the
   * collection changes.
   */
  public void initReturnDocument() {
    initComboBox();
    controller.issueTypeComboBox.getCheckModel().getCheckedItems()
        .addListener((ListChangeListener<String>) change -> {
          while (change.next()) {
            searchLoanByID();
          }
        });
  }

  /**
   * Initializes the issue type combo box within the UI. This method populates the combo box with
   * predefined issue types, which include "pendingReturn", "borrowing", and "late". After adding
   * these items to the combo box, it sets all items as checked by default, allowing initial state
   * selection for the combo box in the UI.
   */
  private void initComboBox() {
    controller.issueTypeComboBox.getItems().addAll("pendingReturn", "borrowing", "late");
    controller.issueTypeComboBox.getCheckModel().checkAll();
  }


  /**
   * Loads the ListView with active loans.
   * <p>
   * This method clears the current contents of the list and repopulates it with active loan data
   * retrieved from the LoanDAO. It then updates the ListView in the controller with the refreshed
   * list data.
   */
  public void loadListView() {
    list.clear();
    list.addAll(LoanDAO.getInstance().getActiveLoans());
    controller.listInfo.setItems(list);
  }

  /**
   * Handles the submission action for returning a loaned document.
   * <p>
   * This method processes the return of a selected loaned document. If the document is marked as
   * "late", it checks if a valid late fee is entered. Displays an alert to confirm the return
   * action and another to indicate success or failure.
   * <p>
   * The method operates in the following steps:
   * 1. Retrieves the selected Loan object from the list.
   * 2. If the loan status is "late", checks the late fee field for a valid integer value.
   * 3. Displays an alert if the late fee is invalid or prompts confirmation to proceed with
   * return.
   * 4. Updates the loan with the late fee if applicable and attempts to return the document.
   * 5. Notifies the user of the outcome, whether successful return or failure.
   * 6. Reloads the list view of loans upon successful document return.
   */
  public void handleSubmitDoc() {
    Loan loan = controller.listInfo.getSelectionModel().getSelectedItem();
    if (loan != null) {
      if (loan.getStatus().equals("late")) {
        if (controller.lateFeeField.getText().isEmpty() || !isStringAnInteger(
            controller.lateFeeField.getText())) {
          showAlertInformation("Return late document", "Please enter a valid late fee!");
        } else {
          Optional<ButtonType> result = showAlertConfirmation("Return document",
              "Are you sure you want to return this document?");
          if (result.isPresent() && result.get() == ButtonType.OK) {
            loan.setDeposit(Integer.parseInt(controller.lateFeeField.getText()));
            if (LoanDAO.getInstance().returnDocument(loan)) {
              showAlertInformation("Return document", "Document successfully returned!");
              loadListView();
            } else {
              showAlertInformation("Return document fail!", "Document could not be returned!");
            }
          }
        }
        controller.lateFeeField.setText("");
      } else {
        Optional<ButtonType> result = showAlertConfirmation("Return document",
            "Are you sure you want to return this document?");
        if (result.isPresent() && result.get() == ButtonType.OK) {
          if (LoanDAO.getInstance().returnDocument(loan)) {
            showAlertInformation("Return document", "Document successfully returned!");
            loadListView();
          } else {
            showAlertInformation("Return document fail!", "Document could not be returned!");
          }
        }
      }
    }
  }

  /**
   * Determines whether a given string can be parsed as an integer.
   *
   * @param str the string to be checked for integer representation
   * @return true if the string is a valid integer, false otherwise
   */
  public boolean isStringAnInteger(String str) {
    if (str == null || str.isEmpty()) {
      return false;
    }
    try {
      Integer.parseInt(str);
      return true;
    } catch (NumberFormatException e) {
      return false;
    }
  }

  /**
   * Searches for loans by Loan ID and updates the display list with the results.
   * <p>
   * This method clears the current list of loans and then repopulates it by querying for loans
   * based on the text input from the searchLoanID field and the checked items in the
   * issueTypeComboBox. The retrieved loans are then set to the listInfo display component.
   */
  public void searchLoanByID() {
    list.clear();
    list.addAll(LoanDAO.getInstance()
        .searchReturnLoanByLoanIdAndStatus(controller.searchLoanID.getText(),
            controller.issueTypeComboBox.getCheckModel().getCheckedItems()));
    controller.listInfo.setItems(list);
  }
}
