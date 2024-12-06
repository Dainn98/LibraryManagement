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

  public String getManagerName() {
    return mainManager.getManagerName();
  }

  public Manager getMainManager() {
    return this.mainManager;
  }

  public void setMainManager(Manager manager) {
    this.mainManager = manager;
    avatarController.initAvatar(infoVBox);
  }

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

  @FXML
  private void handleDashboardButton(ActionEvent actionEvent) {
    dashboardController.loadDashBoardData();
    showSection(dashboardVBox);
  }

  @FXML
  private void handleDocButton(ActionEvent actionEvent) {
    documentController.loadDocumentData();
    showSection(docBPane);
  }

  @FXML
  private void handleUsersButton(ActionEvent actionEvent) {
    userController.loadUserViewData();
    showSection(usersBPane);
  }

  @FXML
  private void handleLibFAQsButton(ActionEvent actionEvent) {
    showSection(FAQsBPane);
    if (!newChatButton.isVisible() && !faqSPane.isVisible()) {
      fade(chatbotPane, 0, 1, Duration.millis(500));
      fade(faqContainer, 0, 1, Duration.millis(500));
    }
  }

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

  @FXML
  private void handlePendingApprovalsButton(ActionEvent actionEvent) {
    pendingApprovalsController.loadApprovalsData();
    showSection(pendingApprovalsBPane);
  }

  // PENDING ISSUE
  @FXML
  private void handlePendingLoansButton(ActionEvent actionEvent) {
    pendingLoanController.loadLoanData();
    showSection(pendingLoansBPane);
  }

  @FXML
  private void handleSearchPendingIssue(KeyEvent keyEvent) {
    pendingLoanController.handleSearchPendingIssue();
  }

  @FXML
  private void checkAllPendingIssue(ActionEvent actionEvent) {
    pendingLoanController.checkAllPendingIssue();
  }

  @FXML
  private void deletePendingIssue(ActionEvent actionEvent) {
    pendingLoanController.disapprovePendingIssue();
  }

  @FXML
  private void disapprovePendingIssue(ActionEvent actionEvent) {
    pendingLoanController.disapprovePendingIssue();
  }

  @FXML
  private void approvePendingIssue(ActionEvent actionEvent) {
    pendingLoanController.approvePendingIssue();
  }

  // ALL ISSUED DOCUMENT
  @FXML
  private void handleIssuedDocButton(ActionEvent actionEvent) {
    issuedDocument.loadLoanData();
    showSection(allIssuedDocBPane);
  }

  @FXML
  private void handleSearchID(KeyEvent keyEvent) {
    issuedDocument.handleSearchID();
  }

  // DOCUMENT CONTROLLER

  @FXML
  private void handleClickAvatar(MouseEvent mouseEvent) {
    dashboardController.handleClickAvatar(pic, infoVBox);
  }

  @FXML
  private void handleExitAvatarInfo(MouseEvent mouseEvent) {
    dashboardController.handleExitAvatarInfo(infoVBox, pic);
  }

  @FXML
  private void handleSearchDocTField(KeyEvent actionEvent) {
    documentController.handleSearchDocTField();
  }

  @FXML
  private void checkAllDocs(ActionEvent actionEvent) {
    documentController.checkAllDocs();
  }

  @FXML
  private void handleAdvancedSearch(ActionEvent actionEvent) {
    showSection(catalogBPane);
  }

  @FXML
  private void loadUpdateBook(ActionEvent actionEvent) {
    documentController.loadDocumentData();
  }

  @FXML
  private void DeleteBook(ActionEvent actionEvent) {
    documentController.DeleteBook();
  }

  @FXML
  private void handleDeleteDocHyperlink(ActionEvent actionEvent) {
    documentController.handleDeleteDocHyperlink();
  }

  //DOCUMENT MANAGEMENT CONTROLLER
  @FXML
  private void handleBackToDoc(ActionEvent actionEvent) {
    showSection(docBPane);
  }

  @FXML
  private void handleRenewDoc(ActionEvent actionEvent) {
    returnDocumentController.loadListView();
  }

  @FXML
  private void handleSubmitIssueDoc(ActionEvent actionEvent) {
    documentManagementController.handleSubmitIssueDoc();
  }

  @FXML
  private void handleSearchUserInformation(KeyEvent keyEvent) {
    if (keyEvent.getCode() == KeyCode.ENTER) {
      documentManagementController.handleSearchUserInformation();
    }
  }

  @FXML
  private void searchLoanByID(KeyEvent keyEvent) {
    returnDocumentController.searchLoanByID();
  }

  @FXML
  private void handleSearchDocInformation(KeyEvent keyEvent) {
    if (keyEvent.getCode() == KeyCode.ENTER) {
      documentManagementController.handleSearchDocInformation();
    }
  }

  @FXML
  private void handleCancelIssue(ActionEvent actionEvent) {
    documentManagementController.handleCancelIssue();
  }

  @FXML
  private void handleReturnDocButton(ActionEvent actionEvent) {
    returnDocumentController.loadListView();
    showSection(docManagementBPane);
    issueDocBPane.setVisible(false);
    returnDocBPane.setVisible(true);
  }

  @FXML
  private void handleIssueDocButton(ActionEvent actionEvent) {
    showSection(docManagementBPane);
    issueDocBPane.setVisible(true);
    returnDocBPane.setVisible(false);
  }

  @FXML
  private void handleSubmitDoc(ActionEvent actionEvent) {
    returnDocumentController.handleSubmitDoc();
  }

  //  USER CONTROLLER
  @FXML
  private void searchUserDetails(KeyEvent event) {
    userController.searchUserDetails();
  }

  @FXML
  private void importData(ActionEvent actionEvent) {
    //To Do
  }

  @FXML
  private void deleteUsersRecord(ActionEvent actionEvent) {
    userController.deleteUsersRecord();
  }

  @FXML
  private void deleteUserRecord(ActionEvent actionEvent) {
    userController.deleteOneUserRecord();
  }

  @FXML
  private void handleCancelUserButton(ActionEvent actionEvent) {
    userController.handleCancelUserButton();
  }

  @FXML
  private void handleSaveUserButton(ActionEvent actionEvent) {
    userController.handleSaveUserButton();
  }

  @FXML
  private void handleUpdateUser(ActionEvent actionEvent) {
  }

  @FXML
  private void checkAllUsers(ActionEvent actionEvent) {
    userController.checkAllUsers();
  }

  // PENDING APPROVAL
  @FXML
  private void disapprovePending(ActionEvent actionEvent) {
    pendingApprovalsController.disapprovePending();
  }

  @FXML
  private void checkAllPending(ActionEvent actionEvent) {
    pendingApprovalsController.checkAllPending();
  }

  @FXML
  private void approvePendingUsers(ActionEvent actionEvent) {
    pendingApprovalsController.approvePendingUsers();
  }

  @FXML
  private void handleSearchPendingUser(KeyEvent keyEvent) {
    pendingApprovalsController.handleSearchPendingUser();
  }

  //ANOTHER
  @FXML
  private void requestMenu(ContextMenuEvent contextMenuEvent) {
    //To Do
  }

  @FXML
  private void fetchUserWithKey(KeyEvent event) {
    //To Do
  }

  @FXML
  private void fetchUserFeesDetails(MouseEvent mouseEvent) {
    userController.fetchUserDetails();
  }

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
   * Handles the sign-out process for the user.
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

  public void handleSendText(MouseEvent mouseEvent) {
    handleSendText();
  }

  @FXML
  private void handleMouseEnterSend(MouseEvent mouseEvent) {
    sendTextButton.setImage(new Image(
        Objects.requireNonNull(getClass().getResourceAsStream(SEND_HOVER_SOURCE))));
  }

  @FXML
  private void handleMouseExitSend(MouseEvent mouseEvent) {
    sendTextButton.setImage(new Image(
        Objects.requireNonNull(getClass().getResourceAsStream(SEND_SOURCE))));
  }

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

