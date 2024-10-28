package library.management.ui.main;


import static library.management.alert.AlertMaker.showAlertConfirmation;

import java.io.IOException;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import library.management.ui.AbstractUI;
import library.management.ui.RegisterNewBook.RegisterNewBookController;

@SuppressWarnings("CallToPrintStackTrace")
public class MainController extends AbstractUI {

  @FXML
  private VBox IssuedBooksVBox;
  @FXML
  private VBox ManageBookLoansVBox;
  @FXML
  private AnchorPane LibraryCatalogAnchorPane;
  @FXML
  private AnchorPane RegisteredStudentsAnchorPane;
  @FXML
  private VBox dashboardVBox;
  @FXML
  private VBox registerBookVBox;
  @FXML
  private VBox pendingApprovalsVBox;
  @FXML
  private Button dashboardButton;
  @FXML
  private Button pendingApprovalsButton;
  @FXML
  private Button registeredStudentsButton;
  @FXML
  private Button libraryCatalogButton;
  @FXML
  private Button registerNewBookButton;
  @FXML
  private Button manageBookLoansButton;
  @FXML
  private Button IssuedBooksButton;
  @FXML
  private Button signOutButton;

  @Override
  public void initialize() {}

  /**
   * Navigates the user to the dashboard.
   *
   * @param actionEvent the event that triggered this action
   */
  @FXML
  private void navigateToDashboardButton(ActionEvent actionEvent) {
    dashboardVBox.setVisible(true);
    registerBookVBox.setVisible(false);
    LibraryCatalogAnchorPane.setVisible(false);
    pendingApprovalsVBox.setVisible(false);
    RegisteredStudentsAnchorPane.setVisible(false);
    ManageBookLoansVBox.setVisible(false);
    IssuedBooksVBox.setVisible(false);
  }

  /**
   * Displays a list of students awaiting approval.
   *
   * @param actionEvent the event that triggered this action
   */
  @FXML
  private void handlePendingApprovalsButton(ActionEvent actionEvent) {
    dashboardVBox.setVisible(false);
    registerBookVBox.setVisible(false);
    LibraryCatalogAnchorPane.setVisible(false);
    pendingApprovalsVBox.setVisible(true);
    RegisteredStudentsAnchorPane.setVisible(false);
    ManageBookLoansVBox.setVisible(false);
    IssuedBooksVBox.setVisible(false);
  }

  /**
   * Shows all registered students in the system.
   *
   * @param actionEvent the event that triggered this action
   */
  @FXML
  private void handleRegisteredStudentsButton(ActionEvent actionEvent) {
    dashboardVBox.setVisible(false);
    registerBookVBox.setVisible(false);
    LibraryCatalogAnchorPane.setVisible(false);
    pendingApprovalsVBox.setVisible(false);
    RegisteredStudentsAnchorPane.setVisible(true);
    ManageBookLoansVBox.setVisible(false);
    IssuedBooksVBox.setVisible(false);
  }

  /**
   * Displays all books currently available in the library.
   *
   * @param actionEvent the event that triggered this action
   */
  @FXML
  private void handleLibraryCatalogButton(ActionEvent actionEvent) {
    dashboardVBox.setVisible(false);
    registerBookVBox.setVisible(false);
    LibraryCatalogAnchorPane.setVisible(true);
    pendingApprovalsVBox.setVisible(false);
    RegisteredStudentsAnchorPane.setVisible(false);
    ManageBookLoansVBox.setVisible(false);
    IssuedBooksVBox.setVisible(false);
  }

  /**
   * Adds a new book to the library's collection.
   *
   * @param actionEvent the event that triggered this action
   */
  @FXML
  private void handleRegisterNewBookButton(ActionEvent actionEvent) {
    ManageBookLoansVBox.setVisible(false);
    dashboardVBox.setVisible(false);
    registerBookVBox.setVisible(true);
    LibraryCatalogAnchorPane.setVisible(false);
    pendingApprovalsVBox.setVisible(false);
    RegisteredStudentsAnchorPane.setVisible(false);
    IssuedBooksVBox.setVisible(false);

  }

  /**
   * Handles the process of issuing or returning books in the library.
   *
   * @param actionEvent the event that triggered this action
   */
  @FXML
  private void handleManageBookLoansButton(ActionEvent actionEvent) {
    dashboardVBox.setVisible(false);
    registerBookVBox.setVisible(false);
    LibraryCatalogAnchorPane.setVisible(false);
    pendingApprovalsVBox.setVisible(false);
    RegisteredStudentsAnchorPane.setVisible(false);
    ManageBookLoansVBox.setVisible(true);
    IssuedBooksVBox.setVisible(false);
  }

  /**
   * Shows a list of books that are currently issued to users.
   *
   * @param actionEvent the event that triggered this action
   */
  @FXML
  private void handleIssuedBooksButton(ActionEvent actionEvent) {
    dashboardVBox.setVisible(false);
    registerBookVBox.setVisible(false);
    LibraryCatalogAnchorPane.setVisible(false);
    pendingApprovalsVBox.setVisible(false);
    RegisteredStudentsAnchorPane.setVisible(false);
    ManageBookLoansVBox.setVisible(false);
    IssuedBooksVBox.setVisible(true);
  }

  /**
   * Handles the sign-out process for the user.
   *
   * @param actionEvent the event that triggered this action
   */
  @FXML
  private void handleSignOutButton(ActionEvent actionEvent) {
    Optional<ButtonType> result = showAlertConfirmation(
        "Sign Out",
        "Are you sure you want to sign out?");
    if (result.isPresent() && result.get() == ButtonType.OK) {
      try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/login/login.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

  }

  /**
   * Handles the process of adding books to the library.
   *
   * @param actionEvent the event that triggered this action
   */
  @FXML
  private void handleAddBooks(ActionEvent actionEvent) {
    RegisterNewBookController registerNewBookController = new RegisterNewBookController();
    registerNewBookController.handleAddBooks(actionEvent);

    //Database

  }

  /**
   * Handles the process of viewing the library catalog.
   *
   * @param actionEvent the event that triggered this action
   */
  public void handleRefresh(ActionEvent actionEvent) {
  }

  /**
   * Handles the process of editing a book in the library.
   *
   * @param actionEvent the event that triggered this action
   */
  public void handleBookEditOption(ActionEvent actionEvent) {
  }

  /**
   * Handles the process of deleting a book from the library.
   *
   * @param actionEvent the event that triggered this action
   */
  public void handleBookDeleteOption(ActionEvent actionEvent) {
  }

  /**
   * Handles the process of exporting the library catalog as a PDF.
   *
   * @param actionEvent the event that triggered this action
   */
  public void exportAsPDF(ActionEvent actionEvent) {
  }

  /**
   * Handles the process of exporting the library catalog as an Excel file.
   *
   * @param actionEvent the event that triggered this action
   */
  public void closeStage(ActionEvent actionEvent) {
  }
}



