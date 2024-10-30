package library.management.ui.controllers;


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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import library.management.ui.AbstractUI;

@SuppressWarnings("CallToPrintStackTrace")
public class MainController extends AbstractUI {

  /**
   * Menu Button.
   */
  public Button dashboardButton;
  public Button pendingApprovalsButton;
  public Button registeredStudentsButton;
  public Button libraryCatalogButton;
  public Button registerNewBookButton;
  public Button manageBookLoansButton;
  public Button IssuedBooksButton;
  public Button signOutButton;
  /**
   * Dashboard UI.
   */
  public VBox dashboardVBox;
  public Label totalBooksLabel;
  public Label numberTotalBooksLabel;
  public Label borrowedBooksLabel;
  public Label numberBorrowedBooksLabel;
  public Label overdueReturnsLabel;
  public Label numberOverdueReturnsLabel;
  public Label totalUsersLabel;
  public Label numberTotalUsersLabel;
  public Button findBookButton;
  public Button findBookIssueButton;
  public Button findStudentButton;
  public TableView recentActivitiesTable;
  public TableColumn userColumnDashboard;
  public TableColumn bookColumnDashboard;
  public TableColumn dueDateColumnDashboard;
  /**
   * Pending Approvals UI.
   */
  public VBox pendingApprovalsVBox;
  public ComboBox branchComboBoxPendingApprovals;
  public ComboBox categoryComboBoxPendingApprovals;
  public ComboBox yearComboBoxPendingApprovals;
  public TableView tableViewPendingApprovals;
  public TableColumn idColumnPendingApprovals;
  public TableColumn usernameColumnPendingApprovals;
  public TableColumn identityCardColumnPendingApprovals;
  public TableColumn mobileColumnPendingApprovals;
  public TableColumn categoryColumnPendingApprovals;
  public TableColumn approveColumnPendingApprovals;

  /**
   * Registered new book UI.
   */
  public VBox registerBookVBox;
  public TextField titleOfBookField;
  public TextField authorNameField;
  public TextArea descriptionField;
  public ComboBox categoryComboBox;
  public TextField numberOfIssueField;
  @FXML
  private RegisterNewBookController registerNewBookController;

  /**
   * Library Catalog UI.
   */
  public AnchorPane libraryCatalogAnchorPane;
  public TableView tableViewLibraryCatalog;
  public TableColumn titleColumnLibraryCatalog;
  public TableColumn bookIdColumnLibraryCatalog;
  public TableColumn authorColumnLibraryCatalog;
  public TableColumn publisherColumnLibraryCatalog;
  public TableColumn availabilityColumnLibraryCatalog;

  /**
   * Registered Students UI.
   */
  public AnchorPane registeredStudentsAnchorPane;
  public ComboBox branchComboBoxRegisteredStudents;
  public ComboBox categoryComboBoxRegisteredStudents;
  public ComboBox yearComboBoxRegisteredStudents;
  public TableView tableViewRegisteredStudents;
  public TableColumn IdColumnRegisteredStudents;
  public TableColumn usernameColumnRegisteredStudents;
  public TableColumn identityCardColumnRegisteredStudents;
  public TableColumn branchColumnRegisteredStudents;
  public TableColumn catagoryColumnRegisteredStudents;
  public TableColumn bookIssueColumnRegisteredStudents;

  /**
   * Manage Book Loans UI.
   */
  public VBox manageBookLoansVBox;
  public ComboBox studentIdComboBoxManageBookLoans;
  public TextField bookIdTextFieldManageBookLoans;
  public TextField returnBookIdTextFieldManageBookLoans;

  /**
   * Issued Books UI.
   */
  public VBox issuedBooksVBox;
  public TableView logsTableIssuedBooks;
  public TableColumn logIdColumnIssuedBooks;
  public TableColumn bookIssueIdColumnIssuedBooks;
  public TableColumn bookNameColumnIssuedBooks;
  public TableColumn studentIdColumnIssuedBooks;
  public TableColumn studentNameColumnIssuedBooks;
  public TableColumn IssuedOnColumnIssuedBooks;
  public TableColumn returnDateColumnIssuedBooks;



  @Override
  public void initialize() {
    registerNewBookController = new RegisterNewBookController();
  }

  protected void showSection(Object sectionToShow) {
    dashboardVBox.setVisible(sectionToShow == dashboardVBox);
    registerBookVBox.setVisible(sectionToShow == registerBookVBox);
    libraryCatalogAnchorPane.setVisible(sectionToShow == libraryCatalogAnchorPane);
    pendingApprovalsVBox.setVisible(sectionToShow == pendingApprovalsVBox);
    registeredStudentsAnchorPane.setVisible(sectionToShow == registeredStudentsAnchorPane);
    manageBookLoansVBox.setVisible(sectionToShow == manageBookLoansVBox);
    issuedBooksVBox.setVisible(sectionToShow == issuedBooksVBox);
  }

  /**
   * Navigates the user to the dashboard.
   */
  @FXML
  private void navigateToDashboardButton(ActionEvent actionEvent) {
    showSection(dashboardVBox);
  }

  /**
   * Displays a list of students awaiting approval.
   */
  @FXML
  private void handlePendingApprovalsButton(ActionEvent actionEvent) {
    showSection(pendingApprovalsVBox);
  }

  /**
   * Shows all registered students in the system.
   */
  @FXML
  private void handleRegisteredStudentsButton(ActionEvent actionEvent) {
    showSection(registeredStudentsAnchorPane);
  }

  /**
   * Displays all books currently available in the library.
   */
  @FXML
  private void handleLibraryCatalogButton(ActionEvent actionEvent) {
    showSection(libraryCatalogAnchorPane);
  }

  /**
   * Adds a new book to the library's collection.
   */
  @FXML
  private void handleRegisterNewBookButton(ActionEvent actionEvent) {
    showSection(registerBookVBox);
  }

  /**
   * Handles the process of issuing or returning books in the library.
   */
  @FXML
  private void handleManageBookLoansButton(ActionEvent actionEvent) {
    showSection(manageBookLoansVBox);
  }

  /**
   * Shows a list of books that are currently issued to users.
   */
  @FXML
  private void handleIssuedBooksButton(ActionEvent actionEvent) {
    showSection(issuedBooksVBox);
  }

  /**
   * Handles the sign-out process for the user.
   */
  @FXML
  private void handleSignOutButton(ActionEvent actionEvent) {
    Optional<ButtonType> result = showAlertConfirmation(
        "Sign Out",
        "Are you sure you want to sign out?");
    if (result.isPresent() && result.get() == ButtonType.OK) {
      try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/fxml/login.fxml"));
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
   */
  @FXML
  private void handleAddBooks(ActionEvent actionEvent) {
    registerNewBookController.handleAddBooks(titleOfBookField);
    //Database
  }

  /**
   * Handles the process of viewing the library catalog.
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
   */
  public void handleBookDeleteOption(ActionEvent actionEvent) {
  }

  /**
   * Handles the process of exporting the library catalog as a PDF.
   */
  public void exportAsPDF(ActionEvent actionEvent) {
  }

  /**
   * Handles the process of exporting the library catalog as an Excel file.
   */
  public void closeStage(ActionEvent actionEvent) {
  }

  public void handleIssueBookButton(ActionEvent actionEvent) {
  }

  public void handlerReturnBook(ActionEvent actionEvent) {
  }

  public TextField getTitleOfBookField() {
    return titleOfBookField;
  }

  public void setTitleOfBookField(TextField titleOfBookField) {
    this.titleOfBookField = titleOfBookField;
  }

  public void handleFindBookButton(ActionEvent actionEvent) {
    //To do in DashboardController
  }

  public void handleFindBookIssueButton(ActionEvent actionEvent) {
    //To do in DashboardController
  }

  public void handleFindStudentButton(ActionEvent actionEvent) {
    //To do in DashboardController
  }
}



