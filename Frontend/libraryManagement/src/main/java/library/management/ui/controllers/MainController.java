package library.management.ui.controllers;


import com.gluonhq.charm.glisten.control.AutoCompleteTextField;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;

import com.jfoenix.controls.JFXTextArea;
import java.net.URL;
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
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import jfxtras.scene.control.gauge.linear.SimpleMetroArcGauge;
import library.management.data.entity.Document;
import library.management.data.entity.Loan;

import library.management.data.entity.Manager;
import library.management.data.entity.User;
import library.management.properties;
import org.controlsfx.control.CheckComboBox;

@SuppressWarnings("CallToPrintStackTrace")
public class MainController implements Initializable, properties, GeneralController {

    private final DashboardController dashboardController = new DashboardController(this);
    private final UserController userController = new UserController(this);
    private final DocumentController documentController = new DocumentController(this);
    private final CatalogController catalogController = new CatalogController(this);
    private final PendingApprovalsController pendingApprovalsController = new PendingApprovalsController(this);
    private final AvatarController avatarController = new AvatarController(this);
    private final PendingLoanController pendingLoanController = new PendingLoanController(this);
    private final IssuedDocument issuedDocument = new IssuedDocument(this);
    private final DocumentManagementController documentManagementController = new DocumentManagementController(this);
    private final ReturnDocumentController returnDocumentController = new ReturnDocumentController(this);
    private final FAQsController faqsController = new FAQsController(this);
    private Manager mainManager;
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
    protected JFXButton importDataButton;
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

    //FAQs
    @FXML
    protected BorderPane FAQsBPane;
    @FXML
    protected GridPane FAQsGPane;
    @FXML
    protected ScrollPane faqSPane;
    @FXML
    protected JFXTextArea faqRequestContainer;

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
    }

    public void setMainManager(Manager manager) {
        this.mainManager = manager;
        avatarController.initAvatar(infoVBox);
    }

    public String getManagerName() {
        return mainManager.getManagerName();
    }

    public Manager getMainManager() {
        return this.mainManager;
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

    @FXML
    private void handleClickAvatar(MouseEvent mouseEvent) {
        dashboardController.handleClickAvatar(pic, infoVBox);
    }

    @FXML
    private void handleExitAvatarInfo(MouseEvent mouseEvent) {
        dashboardController.handleExitAvatarInfo(infoVBox, pic);
    }

    // DOCUMENT CONTROLLER

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
    private void handleImportDataButton(ActionEvent actionEvent) {
        //To Do
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
        if (keyEvent.getCode() == KeyCode.ENTER) return;
        if (scheduler == null) {
            scheduler = Executors.newScheduledThreadPool(1);
        }

        if (scheduledTask != null && !scheduledTask.isDone()) {
            scheduledTask.cancel(false);
        }

        scheduledTask = scheduler.schedule(catalogController::searchDocument, 400, TimeUnit.MILLISECONDS);
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

    private ScheduledExecutorService scheduler;
    private ScheduledFuture<?> scheduledTask;
    //  SIGN OUT CONTROLLER

    /**
     * Handles the sign-out process for the user.
     */
    @FXML
    public void handleSignOutButton(ActionEvent actionEvent) {
        SignOutController.handleManagerSignOut(getClass());
        Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        currentStage.close();
    }

//    FAQs
  public void handleRecord(MouseEvent mouseEvent) {
      //To Do
  }

  public void handleSendText(MouseEvent mouseEvent) {
     faqsController.loadFAQs(FAQsGPane, faqSPane);
  }
}

