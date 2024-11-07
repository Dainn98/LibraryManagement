package library.management.ui.controllers;


import static library.management.alert.AlertMaker.showAlertConfirmation;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import library.management.ui.AbstractUI;

@SuppressWarnings("CallToPrintStackTrace")
public class MainController implements Initializable, AbstractUI {

  /**
   * Menu Button.
   */
  @FXML
  protected Button dashboardButton;
  @FXML
  protected Button pendingApprovalsButton;
  @FXML
  protected Button registeredStudentsButton;
  @FXML
  protected Button libraryCatalogButton;
  @FXML
  protected Button registerNewBookButton;
  @FXML
  protected Button manageBookLoansButton;
  @FXML
  protected Button IssuedBooksButton;
  @FXML
  protected Button signOutButton;
  /**
   * Dashboard UI.
   */
  @FXML
  protected VBox dashboardVBox;
  @FXML
  protected Label totalBooksLabel;
  @FXML
  protected Label numberTotalBooksLabel;
  @FXML
  protected Label borrowedBooksLabel;
  @FXML
  protected Label numberBorrowedBooksLabel;
  @FXML
  protected Label overdueReturnsLabel;
  @FXML
  protected Label numberOverdueReturnsLabel;
  @FXML
  protected Label totalUsersLabel;
  @FXML
  protected Label numberTotalUsersLabel;
  @FXML
  protected Button findBookButton;
  @FXML
  protected Button findBookIssueButton;
  @FXML
  protected Button findStudentButton;
  @FXML
  protected TableView recentActivitiesTable;
  @FXML
  protected TableColumn userColumnDashboard;
  @FXML
  protected TableColumn bookColumnDashboard;
  @FXML
  protected TableColumn dueDateColumnDashboard;
  /**
   * Pending Approvals UI.
   */
  @FXML
  protected VBox pendingApprovalsVBox;
  @FXML
  protected ComboBox branchComboBoxPendingApprovals;
  @FXML
  protected ComboBox categoryComboBoxPendingApprovals;
  @FXML
  protected ComboBox yearComboBoxPendingApprovals;
  @FXML
  protected TableView tableViewPendingApprovals;
  @FXML
  protected TableColumn idColumnPendingApprovals;
  @FXML
  protected TableColumn usernameColumnPendingApprovals;
  @FXML
  protected TableColumn identityCardColumnPendingApprovals;
  @FXML
  protected TableColumn mobileColumnPendingApprovals;
  @FXML
  protected TableColumn categoryColumnPendingApprovals;
  @FXML
  protected TableColumn approveColumnPendingApprovals;

  /**
   * Registered new book UI.
   */
  @FXML
  protected VBox registerBookVBox;
  @FXML
  protected TextField titleOfBookField;
  @FXML
  protected TextField authorNameField;
  @FXML
  protected TextArea descriptionField;
  @FXML
  protected ComboBox categoryComboBox;
  @FXML
  protected TextField numberOfIssueField;

  /**
   * Library Catalog UI.
   */
  @FXML
  protected AnchorPane libraryCatalogAnchorPane;
  @FXML
  protected TableView tableViewLibraryCatalog;
  @FXML
  protected TableColumn titleColumnLibraryCatalog;
  @FXML
  protected TableColumn bookIdColumnLibraryCatalog;
  @FXML
  protected TableColumn authorColumnLibraryCatalog;
  @FXML
  protected TableColumn publisherColumnLibraryCatalog;
  @FXML
  protected TableColumn availabilityColumnLibraryCatalog;

  /**
   * Registered Students UI.
   */
  @FXML
  protected AnchorPane registeredStudentsAnchorPane;
  @FXML
  protected ComboBox branchComboBoxRegisteredStudents;
  @FXML
  protected ComboBox categoryComboBoxRegisteredStudents;
  @FXML
  protected ComboBox yearComboBoxRegisteredStudents;
  @FXML
  protected TableView tableViewRegisteredStudents;
  @FXML
  protected TableColumn IdColumnRegisteredStudents;
  @FXML
  protected TableColumn usernameColumnRegisteredStudents;
  @FXML
  protected TableColumn identityCardColumnRegisteredStudents;
  @FXML
  protected TableColumn branchColumnRegisteredStudents;
  @FXML
  protected TableColumn catagoryColumnRegisteredStudents;
  @FXML
  protected TableColumn bookIssueColumnRegisteredStudents;

  /**
   * Manage Book Loans UI.
   */
  @FXML
  protected VBox manageBookLoansVBox;
  @FXML
  protected ComboBox studentIdComboBoxManageBookLoans;
  @FXML
  protected TextField bookIdTextFieldManageBookLoans;
  @FXML
  protected TextField returnBookIdTextFieldManageBookLoans;

  /**
   * Issued Books UI.
   */
  @FXML
  protected VBox issuedBooksVBox;
  @FXML
  protected TableView logsTableIssuedBooks;
  @FXML
  protected TableColumn logIdColumnIssuedBooks;
  @FXML
  protected TableColumn bookIssueIdColumnIssuedBooks;
  @FXML
  protected TableColumn bookNameColumnIssuedBooks;
  @FXML
  protected TableColumn studentIdColumnIssuedBooks;
  @FXML
  protected TableColumn studentNameColumnIssuedBooks;
  @FXML
  protected TableColumn IssuedOnColumnIssuedBooks;
  @FXML
  protected TableColumn returnDateColumnIssuedBooks;

  @Override
  public void initialize(URL location, ResourceBundle resources) {}

  @FXML
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
    //Database
  }

  /**
   * Handles the process of viewing the library catalog.
   */
  @FXML
  protected void handleRefresh(ActionEvent actionEvent) {
  }

  /**
   * Handles the process of editing a book in the library.
   *
   * @param actionEvent the event that triggered this action
   */
  @FXML
  protected void handleBookEditOption(ActionEvent actionEvent) {
  }

  /**
   * Handles the process of deleting a book from the library.
   */
  @FXML
  protected void handleBookDeleteOption(ActionEvent actionEvent) {
  }

  /**
   * Handles the process of exporting the library catalog as a PDF.
   */
  @FXML
  protected void exportAsPDF(ActionEvent actionEvent) {
  }

  /**
   * Handles the process of exporting the library catalog as an Excel file.
   */
  @FXML
  protected void closeStage(ActionEvent actionEvent) {
  }

  @FXML
  protected void handleIssueBookButton(ActionEvent actionEvent) {
  }

  @FXML
  protected void handlerReturnBook(ActionEvent actionEvent) {
  }

  protected TextField getTitleOfBookField() {
    return titleOfBookField;
  }

  protected void setTitleOfBookField(TextField titleOfBookField) {
    this.titleOfBookField = titleOfBookField;
  }

  @FXML
  protected void handleFindBookButton(ActionEvent actionEvent) {
    //To do in DashboardController
  }

  @FXML
  protected void handleFindBookIssueButton(ActionEvent actionEvent) {
    //To do in DashboardController
  }

  @FXML
  protected void handleFindStudentButton(ActionEvent actionEvent) {
    //To do in DashboardController
  }

  public void loadUpdateBook(ActionEvent actionEvent) {
  }

  public void DeleteBook(ActionEvent actionEvent) {
  }

  public void selectRecordsType(ActionEvent actionEvent) {
  }

  public void importData(ActionEvent actionEvent) {
  }

  public void cancel(ActionEvent actionEvent) {
  }

  public void saveStudent(ActionEvent actionEvent) {
  }

  public void deleteStudent(ActionEvent actionEvent) {
  }

  public void updateStudent(ActionEvent actionEvent) {
  }

  public void requestMenu(ContextMenuEvent contextMenuEvent) {
  }

  public void fetchStudentWithKey(KeyEvent event) {
  }

  public void deleteStudentRecord(ActionEvent actionEvent) {
  }

  public void deleteselectedStudents(ActionEvent actionEvent) {
  }

  public void minimize(MouseEvent mouseEvent) {
  }

  public void fullscreen(MouseEvent mouseEvent) {
  }

  public void unfullscreen(MouseEvent mouseEvent) {
  }

  public void close(MouseEvent mouseEvent) {
  }

  public void searchBook(KeyEvent event) {
  }

  public void loadBookDataentry(ActionEvent actionEvent) {
  }

  public void searchStudentDeatails(KeyEvent event) {
  }

  public void fetchStudentFeesDetails(MouseEvent mouseEvent) {
  }

  public void handleIssueDocSwitch(ActionEvent actionEvent) {
  }

  public void handleReturnDocSwitch(ActionEvent actionEvent) {
  }
}



