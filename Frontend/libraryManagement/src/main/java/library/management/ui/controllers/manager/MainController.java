package library.management.ui.controllers.manager;


import static library.management.alert.AlertMaker.showAlertConfirmation;

import com.gluonhq.charm.glisten.control.AutoCompleteTextField;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import jfxtras.scene.control.ImageViewButton;
import jfxtras.scene.control.gauge.linear.SimpleMetroArcGauge;
import library.management.data.entity.Document;
import library.management.data.entity.Loan;
import library.management.data.entity.Manager;
import library.management.data.entity.User;
import library.management.properties;
import library.management.ui.applications.SpeechToText;
import library.management.ui.controllers.FAQsController;
import library.management.ui.controllers.GeneralController;
import library.management.ui.controllers.SignOutController;
import org.controlsfx.control.CheckComboBox;

/**
 * MainController manages the interactions between the user interface components
 * and the application's backend logic. It serves as a central point for handling
 * user actions and managing views related to the library management system.
 *
 * Fields represent various controllers and UI components that are part of
 * the library management system, such as document controllers, user controllers,
 * and pending approval controllers, along with various charts, gauges, and text
 * fields used throughout the application.
 *
 * Methods within this class provide functionality to initialize the controller,
 * handle different sections of the interface corresponding to different actions
 * such as viewing documents, managing users, dealing with pending approvals,
 * and issuing or returning documents. The controller also handles tasks related
 * to searching, updating, and deleting records, and managing user sessions and approvals.
 */
@SuppressWarnings("CallToPrintStackTrace")
public class MainController extends GeneralController implements Initializable, properties {

  private final DashboardController dashboardController = new DashboardController(this);
  private final DocumentManagementController.UserController userController = new DocumentManagementController.UserController(
      this);
  private final DocumentController documentController = new DocumentController(this);
  private final CatalogController catalogController = new CatalogController(this);
  private final PendingApprovalsController pendingApprovalsController = new PendingApprovalsController(
      this);
  private final AvatarController avatarController = new AvatarController(this);
  private final PendingLoanController pendingLoanController = new PendingLoanController(this);
  private final IssuedDocument issuedDocument = new IssuedDocument(this);
  private final DocumentManagementController documentManagementController = new DocumentManagementController(
      this);
  private final ReturnDocumentController returnDocumentController = new ReturnDocumentController(
      this);
  private final FAQsController faqsController = new FAQsController(this);

  // DASHBOARD PROPERTIES
  @FXML
  protected VBox infoVBox;
  @FXML
  protected ImageView pic;
  @FXML
  protected VBox dashboardVBox;
  @FXML
  protected SimpleMetroArcGauge allDocsGauge;
  @FXML
  protected SimpleMetroArcGauge remainingDocsGauge;
  @FXML
  protected SimpleMetroArcGauge issuedDocsGauge;
  @FXML
  protected SimpleMetroArcGauge allUsersGauge;
  @FXML
  protected SimpleMetroArcGauge docHoldersGauge;
  @FXML
  protected BarChart<String, Number> docBChart;
  @FXML
  protected BarChart<String, Number> userBChart;

  // DOCUMENT PROPERTIES
  @FXML
  protected BorderPane docBPane;
  @FXML
  protected Label allDocs;
  @FXML
  protected Label remainDocs;
  @FXML
  protected TextField searchDocTField;
  @FXML
  protected TableView<Document> docView;
  @FXML
  protected TableColumn<Document, Boolean> checkDocView;
  @FXML
  protected TableColumn<Document, String> docIDDocView;
  @FXML
  protected TableColumn<Document, String> docISBNDocView;
  @FXML
  protected TableColumn<Document, String> docNameDocView;
  @FXML
  protected TableColumn<Document, String> docAuthorDocView;
  @FXML
  protected TableColumn<Document, String> docPublisherDocView;
  @FXML
  protected TableColumn<Document, String> categoryDocView;
  @FXML
  protected TableColumn<Document, Integer> quantityDocView;
  @FXML
  protected TableColumn<Document, Integer> remainingDocsDocView;
  @FXML
  protected TableColumn<Document, String> availabilityDocView;
  @FXML
  protected HBox controlBoxDocView;
  @FXML
  protected CheckBox checkAllDocsView;
  @FXML
  protected Hyperlink deleteDocs;

  // USER PROPERTIES
  @FXML
  protected BorderPane usersBPane;
  @FXML
  protected TextField searchUserField;
  @FXML
  protected JFXComboBox<String> userFilterComboBox;
  @FXML
  protected JFXButton importDataUserButton;
  @FXML
  protected TableView<User> userView;
  @FXML
  protected TableColumn<User, Boolean> checkUserView;
  @FXML
  protected TableColumn<User, String> userNameUserView;
  @FXML
  protected TableColumn<User, String> userPhoneUserView;
  @FXML
  protected TableColumn<User, String> userEmailUserView;
  @FXML
  protected ContextMenu selectUserContext;
  @FXML
  protected MenuItem selectMenu;
  @FXML
  protected TextField userNameField;
  @FXML
  protected TextField userEmailField;
  @FXML
  protected TextField userPhoneField;
  @FXML
  protected JFXButton cancelUserButton;
  @FXML
  protected JFXButton saveUserButton;
  @FXML
  protected JFXButton deleteUser;
  @FXML
  protected JFXButton updateUser;
  @FXML
  protected HBox controlUserView;
  @FXML
  protected CheckBox checkAllUsersView;

  // PENDING APPROVALS PROPERTIES
  @FXML
  protected BorderPane pendingApprovalsBPane;
  @FXML
  protected AutoCompleteTextField<String> checkUsername;
  @FXML
  protected CheckComboBox<String> checkCountry;
  @FXML
  protected CheckComboBox<String> checkState;
  @FXML
  protected CheckComboBox<String> checkYear;
  @FXML
  protected TableView<User> approvalsTView;
  @FXML
  protected TableColumn<User, Boolean> checkBoxApproval;
  @FXML
  protected TableColumn<User, String> usernameApprovals;
  @FXML
  protected TableColumn<User, String> phoneNumberApprovals;
  @FXML
  protected TableColumn<User, String> emailApprovals;
  @FXML
  protected TableColumn<User, String> countryApprovals;
  @FXML
  protected TableColumn<User, String> stateApprovals;
  @FXML
  protected TableColumn<User, String> yearApprovals;
  @FXML
  protected TableColumn<User, Void> approvalApprovals;
  @FXML
  protected CheckBox checkApprovals;

  // DOCUMENT MANAGEMENT PROPERTIES
  @FXML
  protected BorderPane docManagementBPane;
  @FXML
  protected JFXButton issueDocSwitchButton;
  @FXML
  protected JFXButton returnDocSwitchButton;
  @FXML
  protected BorderPane returnDocBPane;
  @FXML
  protected BorderPane issueDocBPane;
  @FXML
  protected AutoCompleteTextField<String> userIDIssue;
  @FXML
  protected AutoCompleteTextField<String> docISBNIssue;
  @FXML
  protected AutoCompleteTextField<String> quantityDoc;
  @FXML
  protected Text userNameInfo;
  @FXML
  protected Text emailInfo;
  @FXML
  protected Text phoneNumberInfo;
  @FXML
  protected JFXListView<Loan> listInfo;
  @FXML
  protected AutoCompleteTextField<String> lateFeeField;
  @FXML
  protected JFXButton submitButton;
  @FXML
  protected JFXButton renewButton;
  @FXML
  protected JFXButton submitIssueDocButton;
  @FXML
  protected JFXButton cancelIssueDocButton;
  @FXML
  protected Text docTitleInfo;
  @FXML
  protected Text docAuthorInfo;
  @FXML
  protected Text docPublisherInfo;
  @FXML
  protected Text price;
  @FXML
  protected Text availability;
  @FXML
  protected TextField searchLoanID;
  @FXML
  protected CheckComboBox<String> issueTypeComboBox;

  // All Issued Doc
  @FXML
  protected BorderPane allIssuedDocBPane;
  @FXML
  protected TextField issuedDocField;
  @FXML
  protected JFXComboBox<String> issueTypeIssuedDoc;
  @FXML
  protected TableView<Loan> IDView;
  @FXML
  protected TableColumn<Loan, String> issuedIDIDView;
  @FXML
  protected TableColumn<Loan, String> docIDIDView;
  @FXML
  protected TableColumn<Loan, String> docTitleIDView;
  @FXML
  protected TableColumn<Loan, String> userNameIDView;
  @FXML
  protected TableColumn<Loan, String> dueDateIDView;
  @FXML
  protected TableColumn<Loan, String> issuedDateAndTimeIDView;
  @FXML
  protected TableColumn<Loan, String> daysIDView;
  @FXML
  protected TableColumn<Loan, String> feeIDView;
  @FXML
  protected TableColumn<Loan, String> statusIDView;

  // pending issue
  @FXML
  protected BorderPane pendingLoansBPane;
  @FXML
  protected TextField loansField;
  @FXML
  protected JFXComboBox<String> typeLoans;
  @FXML
  protected TableView<Loan> loansView;
  @FXML
  protected TableColumn<Loan, Boolean> checkLoan;
  @FXML
  protected TableColumn<Loan, String> issuedIDLoansView;
  @FXML
  protected TableColumn<Loan, String> docIDLoansView;
  @FXML
  protected TableColumn<Loan, String> docStatusLoansView;
  @FXML
  protected TableColumn<Loan, String> userNameLoansView;
  @FXML
  protected TableColumn<Loan, String> issuedDateAndTimeLoansView;
  @FXML
  protected TableColumn<Loan, String> returnDateIDLoansView;
  @FXML
  protected TableColumn<Loan, String> requiredReturnLoansView;
  @FXML
  protected TableColumn<Loan, String> feeIDLoansView;
  @FXML
  protected TableColumn<Loan, Void> approvalLoansView;
  @FXML
  protected TableColumn<Loan, Integer> quantityLoansView;
  @FXML
  protected CheckBox checkLoans;
  @FXML
  protected Hyperlink deleteLoans;
  @FXML
  protected Button disapproveLoansButton;
  @FXML
  protected Button approveLoansButton;

  // CATALOG PROPERTIES
  @FXML
  protected BorderPane catalogBPane;
  @FXML
  protected GridPane apiViewGPane;
  @FXML
  protected GridPane localViewGPane;
  @FXML
  protected AutoCompleteTextField<String> catalogSearchField;
  @FXML
  protected StackPane mainStackPane;

  protected String path = getClass().getResource("/ui/css/myTheme.css").toExternalForm();

  //FAQs
  @FXML
  protected BorderPane FAQsBPane;
  @FXML
  protected GridPane FAQsGPane;
  @FXML
  protected ScrollPane faqSPane;
  @FXML
  protected ImageViewButton recordButton;
  @FXML
  protected ImageViewButton sendTextButton;
  @FXML
  public JFXTextArea faqRequestContainer;
  //    private Timeline shakeAnimation;
  @FXML
  protected BorderPane chatbotPane;
  @FXML
  protected HBox faqContainer;
  @FXML
  protected JFXButton newChatButton;
  private Manager mainManager;
  private ScheduledExecutorService scheduler;
  private ScheduledFuture<?> scheduledTask;

  /**
   * Initializes the MainController by setting up various controllers and UI elements.
   *
   * @param location   The location used to resolve relative paths for the root object, or null if the location is not known.
   * @param resources  The resources used to localize the root object, or null if the root object was not localized.
   */
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    dashboardController.loadDashBoardData();
    userController.initUsersView();
    documentController.initDocumentView();
    pendingApprovalsController.initApprovalsView();
    pendingLoanController.initPendingLoanView();
    issuedDocument.initIssueDocumentView();
    catalogController.initCatalog();
    documentManagementController.initDocumentManagement();
    returnDocumentController.initReturnDocument();
    mainStackPane.getStylesheets().add(path);
    faqRequestContainer.setOnKeyPressed(event -> {
      if (event.getCode() == KeyCode.ENTER) {
        if (event.isShiftDown()) {
          faqRequestContainer.appendText("\n");
        } else {
          handleSendText();
          event.consume();
        }
      }
    });
  }

  /**
   * Retrieves the name of the manager.
   *
   * @return the manager's name as a String.
   */
  public String getManagerName() {
    return mainManager.getManagerName();
  }

  /**
   * Retrieves the main manager instance associated with the MainController.
   *
   * @return the main manager object
   */
  public Manager getMainManager() {
    return this.mainManager;
  }

  /**
   * Sets the main manager for this controller and initializes the avatar section.
   *
   * @param manager the {@link Manager} to be set as the main manager.
   */
  public void setMainManager(Manager manager) {
    this.mainManager = manager;
    avatarController.initAvatar(infoVBox);
  }

  /**
   * Controls the visibility of various sections within the user interface.
   *
   * @param sectionToShow The section object that should be made visible.
   *                       This object is compared against predefined section objects
   *                       to set their visibility accordingly.
   */
  // MENU CONTROLLER
  @FXML
  private void showSection(Object sectionToShow) {
    dashboardVBox.setVisible(sectionToShow == dashboardVBox);
    docBPane.setVisible(sectionToShow == docBPane);
    usersBPane.setVisible(sectionToShow == usersBPane);
    catalogBPane.setVisible(sectionToShow == catalogBPane);
    pendingApprovalsBPane.setVisible(sectionToShow == pendingApprovalsBPane);
    allIssuedDocBPane.setVisible(sectionToShow == allIssuedDocBPane);
    docManagementBPane.setVisible(sectionToShow == docManagementBPane);
    pendingLoansBPane.setVisible(sectionToShow == pendingLoansBPane);
    pendingApprovalsBPane.setVisible(sectionToShow == pendingApprovalsBPane);
    FAQsBPane.setVisible(sectionToShow == FAQsBPane);
    chatbotPane.setVisible(sectionToShow == chatbotPane);
  }

  /**
   * Handles the action event triggered by the dashboard button. This method
   * is responsible for loading the dashboard data by delegating to the
   * dashboard controller and displaying the dashboard section within the
   * application's view.
   *
   * @param actionEvent the action event triggered when the dashboard button
   *        is pressed.
   */
  @FXML
  private void handleDashboardButton(ActionEvent actionEvent) {
    dashboardController.loadDashBoardData();
    showSection(dashboardVBox);
  }

  /**
   * Handles the action event triggered by the document button. This method is responsible
   * for loading the document data using the documentController and displaying the document
   * section in the user interface.
   *
   * @param actionEvent the action event that initiated the call of this method, typically
   *                    a user interaction with the document button.
   */
  @FXML
  private void handleDocButton(ActionEvent actionEvent) {
    documentController.loadDocumentData();
    showSection(docBPane);
  }

  /**
   * Handles the event when the user button is clicked. This method loads the user view data
   * and displays the user section.
   *
   * @param actionEvent the ActionEvent triggered by clicking the users button
   */
  @FXML
  private void handleUsersButton(ActionEvent actionEvent) {
    userController.loadUserViewData();
    showSection(usersBPane);
  }

  /**
   * Handles the action of clicking the Library FAQs button. This method shows the FAQs section and,
   * if necessary, applies fade transitions to make the chatbot panel and FAQ container visible.
   *
   * @param actionEvent the event triggered by clicking the Library FAQs button
   */
  @FXML
  private void handleLibFAQsButton(ActionEvent actionEvent) {
    showSection(FAQsBPane);
    if (!newChatButton.isVisible() && !faqSPane.isVisible()) {
      fade(chatbotPane, 0, 1, Duration.millis(500));
      fade(faqContainer, 0, 1, Duration.millis(500));
    }
  }

  /**
   * Handles the action of pressing the library catalog button. This method initiates
   * a search for documents in the library catalog, running the search operation
   * in a separate thread to prevent blocking the UI. After starting the search
   * task, it switches the view to display the catalog section.
   *
   * @param actionEvent the event triggered by the library catalog button press.
   */
  @FXML
  private void handleLibraryCatalogButton(ActionEvent actionEvent) {
    Task<Void> loadCatalog = new Task<>() {
      @Override
      protected Void call() throws Exception {
        catalogController.searchDocument();
        return null;
      }
    };
    Thread thread = new Thread(loadCatalog);
    thread.setDaemon(true);
    thread.start();

    showSection(catalogBPane);
  }

  // ALL ISSUED DOCUMENT

  /**
   * Handles the action event triggered by clicking the Pending Approvals button.
   * This method loads the pending approvals data and displays
   * the pending approvals section in the UI.
   *
   * @param actionEvent the ActionEvent triggered when the Pending Approvals button is clicked
   */
  @FXML
  private void handlePendingApprovalsButton(ActionEvent actionEvent) {
    pendingApprovalsController.loadApprovalsData();
    showSection(pendingApprovalsBPane);
  }

  /**
   * Handles the action event triggered by clicking the pending loans button.
   * Loads the pending loan data and displays the pending loans section.
   *
   * @param actionEvent the event triggered when the pending loans button is clicked
   */
  // PENDING ISSUE
  @FXML
  private void handlePendingLoansButton(ActionEvent actionEvent) {
    pendingLoanController.loadLoanData();
    showSection(pendingLoansBPane);
  }

  /**
   * Handles the event of searching for a pending issue when a key event is triggered.
   *
   * @param keyEvent The KeyEvent that triggers the search action.
   */
  @FXML
  private void handleSearchPendingIssue(KeyEvent keyEvent) {
    pendingLoanController.handleSearchPendingIssue();
  }

  /**
   * Handles the action event triggered to check all pending issues in the system.
   * This method delegates the task to the pendingLoanController to handle
   * the selection of all pending issues.
   *
   * @param actionEvent the event that triggered this action
   */
  @FXML
  private void checkAllPendingIssue(ActionEvent actionEvent) {
    pendingLoanController.checkAllPendingIssue();
  }

  /**
   * Deletes a pending issue by disapproving it through the pendingLoanController.
   * This action is triggered by a user event.
   *
   * @param actionEvent the ActionEvent that triggers this method
   */
  @FXML
  private void deletePendingIssue(ActionEvent actionEvent) {
    pendingLoanController.disapprovePendingIssue();
  }

  /**
   * Handles the disapproval process for a pending loan issue when triggered by a UI action event.
   *
   * @param actionEvent the event that triggers the disapproval of a pending loan issue
   */
  @FXML
  private void disapprovePendingIssue(ActionEvent actionEvent) {
    pendingLoanController.disapprovePendingIssue();
  }

  /**
   * Approves the selected pending issue loans. This action is triggered by a UI event.
   * It calls the `approvePendingIssue` method of `pendingLoanController` to initiate the approval
   * process for loans that are in a pending state.
   *
   * @param actionEvent the event object representing the user's action that
   *        triggered this method, typically a button click.
   */
  @FXML
  private void approvePendingIssue(ActionEvent actionEvent) {
    pendingLoanController.approvePendingIssue();
  }

  /**
   * Handles the action event triggered by the "Issued Documents" button.
   *
   * This method is responsible for loading the data related to issued documents
   * and displaying the relevant user interface section.
   *
   * @param actionEvent the action event that triggers this method when the
   *                    "Issued Documents" button is pressed.
   */
  // ALL ISSUED DOCUMENT
  @FXML
  private void handleIssuedDocButton(ActionEvent actionEvent) {
    issuedDocument.loadLoanData();
    showSection(allIssuedDocBPane);
  }

  /**
   * Handles the search operation when the user types in the search field for issued documents.
   * This method is triggered by a key event from the user interface.
   *
   * @param keyEvent the key event that triggers the search operation, usually when the user types in a search field.
   */
  @FXML
  private void handleSearchID(KeyEvent keyEvent) {
    issuedDocument.handleSearchID();
  }

  // DOCUMENT CONTROLLER

  /**
   * Handles the click event on the avatar image. Triggers an animation effect transitioning
   * between the avatar image and a VBox containing additional information.
   *
   * @param mouseEvent the MouseEvent that triggered the click action on the avatar
   */
  @FXML
  private void handleClickAvatar(MouseEvent mouseEvent) {
    dashboardController.handleClickAvatar(pic, infoVBox);
  }

  /**
   * Handles the mouse exit event for the avatar information display.
   *
   * @param mouseEvent The MouseEvent triggering the avatar exit animation.
   */
  @FXML
  private void handleExitAvatarInfo(MouseEvent mouseEvent) {
    dashboardController.handleExitAvatarInfo(infoVBox, pic);
  }

  /**
   * Handles the search event for the document text field. This method is triggered when the
   * user interacts with the document search field, typically by typing a query.
   * It delegates the search functionality to the documentController.
   *
   * @param actionEvent the key event that triggered the search action
   */
  @FXML
  private void handleSearchDocTField(KeyEvent actionEvent) {
    documentController.handleSearchDocTField();
  }

  /**
   * Checks and updates the status of all document checkboxes based on a triggered event.
   *
   * @param actionEvent the event that triggers the checking of all document checkboxes
   */
  @FXML
  private void checkAllDocs(ActionEvent actionEvent) {
    documentController.checkAllDocs();
  }

  /**
   * Handles the advanced search functionality by showing the appropriate catalog section.
   *
   * @param actionEvent the event that triggers the advanced search; this represents a user's
   * interaction with a UI control that is configured to trigger the action.
   */
  @FXML
  private void handleAdvancedSearch(ActionEvent actionEvent) {
    showSection(catalogBPane);
  }

  /**
   * Loads book data into the document controller for updating purposes.
   * This method is triggered by a user action, typically to refresh or reload the book information
   * displayed in the user interface.
   *
   * @param actionEvent the event that triggered this method, typically from a user interface control.
   */
  @FXML
  private void loadUpdateBook(ActionEvent actionEvent) {
    documentController.loadDocumentData();
  }

  /**
   * Deletes a book from the system.
   *
   * This method interacts with the documentController to remove a book
   * that is currently selected by the user. It listens for an ActionEvent,
   * which is typically triggered by a user action, such as clicking a delete button.
   *
   * @param actionEvent the event that triggered the deletion, typically a user action.
   */
  @FXML
  private void DeleteBook(ActionEvent actionEvent) {
    documentController.DeleteBook();
  }

  /**
   * Handles the event triggered by clicking the delete document hyperlink.
   * This method delegates the action to the documentController's handleDeleteDocHyperlink method.
   *
   * @param actionEvent the action event that occurs when the delete hyperlink is activated
   */
  @FXML
  private void handleDeleteDocHyperlink(ActionEvent actionEvent) {
    documentController.handleDeleteDocHyperlink();
  }

  /**
   * Handles the action event for navigating back to the document section.
   *
   * @param actionEvent the ActionEvent triggered by the user's interface interaction
   */
  //DOCUMENT MANAGEMENT CONTROLLER
  @FXML
  private void handleBackToDoc(ActionEvent actionEvent) {
    showSection(docBPane);
  }

  /**
   * Handles the action event triggered for renewing a document.
   * Loads the list view to reflect any changes related to the document renewal process.
   *
   * @param actionEvent The action event that triggers the document renewal process.
   */
  @FXML
  private void handleRenewDoc(ActionEvent actionEvent) {
    returnDocumentController.loadListView();
  }

  /**
   * Handles the submission of an issue document action triggered by the user interface.
   * Delegates the handling process to the document management controller for further processing.
   *
   * @param actionEvent the action event that triggered this handler, typically associated
   *                    with a user interface component interaction such as pressing a button
   */
  @FXML
  private void handleSubmitIssueDoc(ActionEvent actionEvent) {
    documentManagementController.handleSubmitIssueDoc();
  }

  /**
   * Handles the event triggered when the user intends to search for user information.
   *
   * This method is invoked when the ENTER key is pressed while focusing on the relevant
   * user search field. It delegates the search operation to the DocumentManagementController's
   * handleSearchUserInformation method.
   *
   * @param keyEvent the KeyEvent generated by the user interaction, used to detect the ENTER key press.
   */
  @FXML
  private void handleSearchUserInformation(KeyEvent keyEvent) {
    if (keyEvent.getCode() == KeyCode.ENTER) {
      documentManagementController.handleSearchUserInformation();
    }
  }

  /**
   * Handles the action event where a loan is searched by its unique identifier.
   * This method is triggered when a key event occurs, specifically for searching a loan based on the ID provided.
   *
   * @param keyEvent The key event that triggers the search. It contains information about the key press action
   *                 which initiates the loan search process.
   */
  @FXML
  private void searchLoanByID(KeyEvent keyEvent) {
    returnDocumentController.searchLoanByID();
  }

  /**
   * Handles the event of a key being pressed in relation to the search document information functionality.
   * Specifically, it listens for the Enter key press event to trigger the search operation.
   *
   * @param keyEvent the key event associated with the key press action
   */
  @FXML
  private void handleSearchDocInformation(KeyEvent keyEvent) {
    if (keyEvent.getCode() == KeyCode.ENTER) {
      documentManagementController.handleSearchDocInformation();
    }
  }

  /**
   * Handles the action event for canceling an issue.
   *
   * This method is triggered when the cancel action is performed in the user
   * interface. It delegates the cancel issue handling to the
   * documentManagementController.
   *
   * @param actionEvent the event object containing details about the action event
   *                    that triggered this handler, such as the source of the event
   *                    and any additional parameters
   */
  @FXML
  private void handleCancelIssue(ActionEvent actionEvent) {
    documentManagementController.handleCancelIssue();
  }

  /**
   * Handles the action triggered by clicking the 'Return Document' button.
   *
   * @param actionEvent the event triggered when the 'Return Document' button is clicked
   */
  @FXML
  private void handleReturnDocButton(ActionEvent actionEvent) {
    returnDocumentController.loadListView();
    showSection(docManagementBPane);
    issueDocBPane.setVisible(false);
    returnDocBPane.setVisible(true);
  }

  /**
   * Handles the action event for the "Issue Document" button.
   * When triggered, this method updates the user interface to show the document management section,
   * makes the issue document panel visible, and hides the return document panel.
   *
   * @param actionEvent the action event triggered by the "Issue Document" button
   */
  @FXML
  private void handleIssueDocButton(ActionEvent actionEvent) {
    showSection(docManagementBPane);
    issueDocBPane.setVisible(true);
    returnDocBPane.setVisible(false);
  }

  /**
   * Handles the submission of a document when triggered by a specific action event.
   *
   * @param actionEvent the event that triggers the document submission, typically associated with a user interaction such as a button click.
   */
  @FXML
  private void handleSubmitDoc(ActionEvent actionEvent) {
    returnDocumentController.handleSubmitDoc();
  }

  /**
   * Handles the search user details operation triggered by a key event.
   * This method invokes the searchUserDetails method from the userController
   * to perform a search operation based on the user's input.
   *
   * @param event the KeyEvent triggered by the user's key press, providing
   *              information about the typed key and the state of the keyboard
   */
  //  USER CONTROLLER
  @FXML
  private void searchUserDetails(KeyEvent event) {
    userController.searchUserDetails();
  }

  /**
   * Handles the import of data when triggered by an action event.
   *
   * @param actionEvent the event that triggers the data import
   */
  @FXML
  private void importData(ActionEvent actionEvent) {
    //To Do
  }

  /**
   * Deletes a user's record in response to an action event.
   *
   * @param actionEvent the event that triggers the deletion of the user's record
   */
  @FXML
  private void deleteUsersRecord(ActionEvent actionEvent) {
    userController.deleteUsersRecord();
  }

  /**
   * Deletes a user record from the system.
   *
   * @param actionEvent the event that triggers the deletion of the user record
   */
  @FXML
  private void deleteUserRecord(ActionEvent actionEvent) {
    userController.deleteOneUserRecord();
  }

  /**
   * Handles the event when the "Cancel" button for a user is clicked.
   * This method delegates the action to the userController.
   *
   * @param actionEvent the event triggered by clicking the "Cancel" button
   */
  @FXML
  private void handleCancelUserButton(ActionEvent actionEvent) {
    userController.handleCancelUserButton();
  }

  /**
   * Handles the action event triggered when the save user button is clicked.
   * Delegates the handling logic to the userController's handleSaveUserButton method.
   *
   * @param actionEvent the ActionEvent instance representing the button click event
   */
  @FXML
  private void handleSaveUserButton(ActionEvent actionEvent) {
    userController.handleSaveUserButton();
  }

  /**
   * Handles the update user action event triggered from the user interface.
   *
   * @param actionEvent the action event triggering the update user action
   */
  @FXML
  private void handleUpdateUser(ActionEvent actionEvent) {
  }

  /**
   * Handles an action event to check all users using the userController.
   *
   * @param actionEvent the event triggered by user interaction, typically a button press
   */
  @FXML
  private void checkAllUsers(ActionEvent actionEvent) {
    userController.checkAllUsers();
  }

  /**
   * Invokes the disapproval of a pending request.
   *
   * @param actionEvent the event that triggered the disapproval action
   */
  // PENDING APPROVAL
  @FXML
  private void disapprovePending(ActionEvent actionEvent) {
    pendingApprovalsController.disapprovePending();
  }

  /**
   * Handles the action event triggered when the user requests to check all pending items.
   * Invokes the method on the pendingApprovalsController to perform the necessary checks.
   *
   * @param actionEvent the action event that triggers this handler, typically associated with a user interface element such as a button
   */
  @FXML
  private void checkAllPending(ActionEvent actionEvent) {
    pendingApprovalsController.checkAllPending();
  }

  /**
   * Handles the action event to approve users who are pending approval.
   * This method is typically triggered by a user interface event.
   *
   * @param actionEvent the action event that triggers the approval process,
   *                    typically from a user interface component such as a button.
   */
  @FXML
  private void approvePendingUsers(ActionEvent actionEvent) {
    pendingApprovalsController.approvePendingUsers();
  }

  /**
   * Handles the action of searching for a pending user based on the input key event.
   * This method is triggered by a key event within the associated user interface element,
   * and it delegates the search operation to the pendingApprovalsController.
   *
   * @param keyEvent the KeyEvent triggered by user interaction, representing the key press input
   */
  @FXML
  private void handleSearchPendingUser(KeyEvent keyEvent) {
    pendingApprovalsController.handleSearchPendingUser();
  }

  /**
   * Handles the ContextMenuEvent to request a context menu based on the event details.
   *
   * @param contextMenuEvent The ContextMenuEvent that triggers this method.
   */
  //ANOTHER
  @FXML
  private void requestMenu(ContextMenuEvent contextMenuEvent) {
    //To Do
  }

  /**
   * Fetches the user associated with the specified keyboard event. This method
   * is typically triggered when a key is pressed, and it processes the event
   * to retrieve and handle the associated user's data.
   *
   * @param event the KeyEvent that triggers this method, containing details
   *              of the key press event, including the specific keycode and
   *              any modifier keys used.
   */
  @FXML
  private void fetchUserWithKey(KeyEvent event) {
    //To Do
  }

  /**
   * Handles the mouse event for fetching user fees details.
   * This method invokes the userController's method to fetch user details.
   *
   * @param mouseEvent the MouseEvent that triggers the fetchUserFeesDetails action
   */
  @FXML
  private void fetchUserFeesDetails(MouseEvent mouseEvent) {
    userController.fetchUserDetails();
  }

  /**
   * Handles the search operation within a catalog when a key event is detected.
   * This method responds to key input, initiating a scheduled search after
   * a delay unless the Enter key is pressed.
   *
   * @param keyEvent the KeyEvent triggered by user interaction, used to determine the
   *                 type of key press and execute the corresponding search logic.
   */
  // CATALOG
  @FXML
  private void handleSearchCatalog(KeyEvent keyEvent) {
    if (keyEvent.getCode() == KeyCode.ENTER) {
      return;
    }
    if (scheduler == null) {
      scheduler = Executors.newScheduledThreadPool(1);
    }

    if (scheduledTask != null && !scheduledTask.isDone()) {
      scheduledTask.cancel(false);
    }

    scheduledTask = scheduler.schedule(catalogController::searchDocument, 400,
        TimeUnit.MILLISECONDS);
  }

  /**
   * Handles the event when a key is pressed during catalog search.
   * This method specifically checks for the ENTER key press and triggers the search
   * document and add suggestion operations in the catalog controller.
   *
   * @param keyEvent the KeyEvent triggered when a key is pressed
   */
  @FXML
  private void handleSearchCatalogPressed(KeyEvent keyEvent) {
    switch (keyEvent.getCode()) {
      case ENTER -> {
        if (scheduledTask != null && !scheduledTask.isDone()) {
          scheduledTask.cancel(false);
        }
        catalogController.searchDocument();
        catalogController.addSuggestion();
      }
    }
  }
  //  SIGN OUT CONTROLLER

  /**
   * Handles the sign-out button action event. Displays a confirmation dialog
   * to the user and performs sign-out operations if the user confirms.
   * Closes the current window upon successful sign-out.
   *
   * @param actionEvent the action event triggered by the sign-out button
   */
  @FXML
  public void handleSignOutButton(ActionEvent actionEvent) {
    Optional<ButtonType> result = showAlertConfirmation("Sign Out",
        "Are you sure you want to sign out?");
    if (result.isPresent() && result.get() == ButtonType.OK) {
      SignOutController.handleSignOut();
      Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
      currentStage.close();
    }
  }

  /**
   * Handles the recording action triggered by a mouse event. The method updates the UI to reflect the
   * recording state by changing the button image and starting or stopping an animation. It also
   * toggles the speech recognition process.
   *
   * @param mouseEvent the mouse event that initiates the recording action
   */
  //    FAQs
  @FXML
  private void handleRecord(MouseEvent mouseEvent) {
    recordButton.setImage(new Image(
        Objects.requireNonNull(getClass().getResourceAsStream(RECORD_SOURCE))));
    startShakingAnimation(recordButton);

    SpeechToText.stopRecognition = !SpeechToText.stopRecognition;
    if (!SpeechToText.stopRecognition) {
      System.out.println("Start");
      Task<Void> record = new Task<>() {
        @Override
        protected Void call() throws Exception {
          faqsController.record();
          return null;
        }
      };
      Thread thread = new Thread(record);
      thread.setDaemon(true);
      thread.start();
    } else {
      System.out.println("Stop");
      stopShakingAnimation(recordButton);
      recordButton.setImage(new Image(
          Objects.requireNonNull(getClass().getResourceAsStream(MIRCO_SOURCE))));
    }
  }

  /**
   * Handles the action of sending text when a mouse event is triggered.
   *
   * @param mouseEvent the mouse event that triggered the send action
   */
  public void handleSendText(MouseEvent mouseEvent) {
    handleSendText();
  }

  /**
   * Handles the mouse enter event on the send button, changing its image to the hover state.
   *
   * @param mouseEvent the MouseEvent triggered when the mouse enters the send button area
   */
  @FXML
  private void handleMouseEnterSend(MouseEvent mouseEvent) {
    sendTextButton.setImage(new Image(
        Objects.requireNonNull(getClass().getResourceAsStream(SEND_HOVER_SOURCE))));
  }

  /**
   * Event handler that is triggered when the mouse exits the 'send' button area.
   * It resets the image of the send button to its default state.
   *
   * @param mouseEvent the mouse event that triggers this handler when the mouse exits the button
   */
  @FXML
  private void handleMouseExitSend(MouseEvent mouseEvent) {
    sendTextButton.setImage(new Image(
        Objects.requireNonNull(getClass().getResourceAsStream(SEND_SOURCE))));
  }

  /**
   * Handles the reset action for the FAQ section of the application.
   * This method fades out specific elements including the FAQ scroll pane
   * and the new chat button, while simultaneously fading in the chatbot pane
   * and FAQ container. It also clears the content of the FAQs grid pane
   * and sets it to the FAQ scroll pane's content.
   *
   * @param actionEvent the event triggered by the action, typically the user
   *                    interacting with a UI component, such as a button.
   */
  @FXML
  private void handleResetFAQs(ActionEvent actionEvent) {
    fade(faqSPane, 0.5, 0, Duration.millis(500));
    fade(newChatButton, 0.5, 0, Duration.millis(500));
    // CHATBOT BPane
    fade(chatbotPane, 0.5, 1, Duration.millis(500));
    fade(faqContainer, 0.5, 1, Duration.millis(500));
    FAQsGPane.getChildren().clear();
    faqSPane.setContent(FAQsGPane);
  }

  /**
   * Handles the process of sending text by managing the visibility and animations
   * of various user interface components related to FAQs and chat functionality.
   *
   * This method performs the following actions:
   * - Loads FAQs into the specified graphical pane.
   * - Fades in the new chat button if it is not currently visible.
   * - Fades in the FAQ scroll pane if it is not currently visible.
   * - Fades out the chatbot pane if it is currently visible.
   *
   * It utilizes the `fade` method to apply animation transitions to the
   * components with a duration of 500 milliseconds.
   */
  private void handleSendText() {
    faqsController.loadFAQs(FAQsGPane, faqSPane);
    if (!newChatButton.isVisible()) {
      fade(newChatButton, 0.5, 1, Duration.millis(500));
    }
    if (!faqSPane.isVisible()) {
      fade(faqSPane, 0.5, 1, Duration.millis(500));
    }
    if (chatbotPane.isVisible()) {
      fade(chatbotPane, 0.5, 0, Duration.millis(500));
    }
  }
}

