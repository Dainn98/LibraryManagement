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

  public ReturnDocumentController(MainController controller) {
    this.controller = controller;
  }

  public void initReturnDocument() {
    initComboBox();
    controller.issueTypeComboBox.getCheckModel().getCheckedItems()
        .addListener((ListChangeListener<String>) change -> {
          while (change.next()) {
            searchLoanByID();
          }
        });
  }

  private void initComboBox() {
    controller.issueTypeComboBox.getItems().addAll("pendingReturn", "borrowing", "late");
    controller.issueTypeComboBox.getCheckModel().checkAll();
  }


  public void loadListView() {
    list.clear();
    list.addAll(LoanDAO.getInstance().getActiveLoans());
    controller.listInfo.setItems(list);
  }

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

  public void searchLoanByID() {
    list.clear();
    list.addAll(LoanDAO.getInstance()
        .searchReturnLoanByLoanIdAndStatus(controller.searchLoanID.getText(),
            controller.issueTypeComboBox.getCheckModel().getCheckedItems()));
    controller.listInfo.setItems(list);
  }
}
