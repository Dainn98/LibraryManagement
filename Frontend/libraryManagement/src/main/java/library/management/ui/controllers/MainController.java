package library.management.ui.controllers;


import com.gluonhq.charm.glisten.control.AutoCompleteTextField;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;

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
import javafx.scene.chart.BarChart;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import javafx.scene.layout.VBox;
import jfxtras.scene.control.gauge.linear.SimpleMetroArcGauge;
import library.management.data.entity.Document;
import library.management.data.entity.Loan;
import library.management.data.entity.User;
import library.management.properties;
import library.management.ui.AbstractUI;
import org.controlsfx.control.CheckComboBox;

@SuppressWarnings("CallToPrintStackTrace")
public class MainController implements Initializable, AbstractUI, properties {
    // Dashboard Window
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

    // Document Window
    @FXML
    protected BorderPane docBPane;
    @FXML
    protected Label allDocs;
    @FXML
    protected Label remainDocs;
    @FXML
    protected TextField searchDocTField;
    @FXML
    protected JFXButton addDocButton;
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

    // Register Document
    @FXML
    protected BorderPane registerDocumentBPane;
    @FXML
    protected JFXButton backToDocs;
    @FXML
    protected TextField docISBNField;
    @FXML
    protected TextField docTitleField;
    @FXML
    protected TextField docAuthorField;
    @FXML
    protected TextField docPublisherField;
    @FXML
    protected TextField docPriceField;
    @FXML
    protected TextArea descriptionField;
    @FXML
    protected TextField numberOfIssueField;
    @FXML
    protected JFXButton cancelRegisterDocButton;
    @FXML
    protected Button submitDocButton;
    @FXML
    protected JFXComboBox<?> docCategory;
    @FXML
    protected JFXComboBox<?> docLanguage;

    // User
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
    protected TableColumn<User, String> userIDUserView;
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
    protected TextField userIDField;
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

    // Pending Approvals
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
    protected TableColumn<User, String> idApprovals;
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
    protected TableColumn<User, Void> approveApprovals;
    @FXML
    protected CheckBox checkAllApprovals;


    // Document Management
    @FXML
    protected BorderPane docManagementBPane;
    @FXML
    protected JFXButton issueDocSwitchButton;
    @FXML
    protected JFXButton returnDocSwitchButton;
    @FXML
    protected BorderPane returnDocBPane;
    @FXML
    protected JFXListView<?> listInfo;
    @FXML
    protected AutoCompleteTextField lateFeeField;
    @FXML
    protected JFXButton submitButton;
    @FXML
    protected JFXButton renewButton;
    @FXML
    protected JFXButton submitIssueDocButton;
    @FXML
    protected JFXButton cancelIssueDocButton;

    // All Issued Doc
    @FXML
    protected BorderPane allIssuedDocBPane;
    @FXML
    protected TextField issuedDocField;
    @FXML
    protected JFXComboBox<String> loanFilterComboBox;
    @FXML
    protected TableView<Loan> IDView;
    @FXML
    protected TableColumn<Loan, String> issuedIDIDView;
    @FXML
    protected TableColumn<Loan, String> docIDIDView;
    @FXML
    protected TableColumn<Loan, String> docTitleIDView;
    @FXML
    protected TableColumn<Loan, String> userIDIDView;
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
    protected JFXComboBox<String> pendingLoanFilterComboBox;
    @FXML
    protected TableView<Loan> loansView;
    @FXML
    protected TableColumn<Loan, Boolean> checkLoan;
    @FXML
    protected TableColumn<Loan, String> issuedIDLoansView;
    @FXML
    protected TableColumn<Loan, String> docISBNLoansView;
    @FXML
    protected TableColumn<Loan, String> docTitleLoansView;
    @FXML
    protected TableColumn<Loan, String> userIDLoansView;
    @FXML
    protected TableColumn<Loan, String> userNameLoansView;
    @FXML
    protected TableColumn<Loan, String> issuedDateAndTimeLoansView;
    @FXML
    protected TableColumn<Loan, String> dueDateIDLoansView;
    @FXML
    protected TableColumn<Loan, String> daysLoansView;
    @FXML
    protected TableColumn<Loan, String> feeIDLoansView;
    @FXML
    protected TableColumn<Loan, Void> approvalLoansView;
    // CATALOG
    @FXML
    protected BorderPane catalogBPane;
    @FXML
    protected GridPane apiViewGPane;
    @FXML
    protected GridPane localViewGPane;
    @FXML
    protected AutoCompleteTextField<String> catalogSearchField;


    private final DashboardController dashboardController = new DashboardController(this);
    private final UserController userController = new UserController(this);
    private final DocumentController documentController = new DocumentController(this);
    private final CatalogController catalogController = new CatalogController(this);
    private final PendingApprovalsController pendingApprovalsController = new PendingApprovalsController(this);
    private final PendingLoanController pendingLoanController = new PendingLoanController(this);
    private final IssuedDocument issuedDocument = new IssuedDocument(this);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dashboardController.loadDashBoardData();
        userController.initUsersView();
        documentController.initDocumentView();
        pendingApprovalsController.initApprovalsView();
        pendingLoanController.initPendingLoanView();
        issuedDocument.initIssueDocumentView();
        catalogController.initCatalog();
    }

    // MENU CONTROLLER
    @FXML
    protected void showSection(Object sectionToShow) {
        dashboardVBox.setVisible(sectionToShow == dashboardVBox);
        docBPane.setVisible(sectionToShow == docBPane);
        usersBPane.setVisible(sectionToShow == usersBPane);
        catalogBPane.setVisible(sectionToShow == catalogBPane);
        pendingApprovalsBPane.setVisible(sectionToShow == pendingApprovalsBPane);
        allIssuedDocBPane.setVisible(sectionToShow == allIssuedDocBPane);
        pendingLoansBPane.setVisible(sectionToShow == pendingLoansBPane);
    }

    @FXML
    private void handleDashboardButton(ActionEvent actionEvent) {
        dashboardController.loadDashBoardData();
        showSection(dashboardVBox);
    }

    public void handleDocButton(ActionEvent actionEvent) {
        documentController.loadDocumentData();
        showSection(docBPane);
    }

    public void handleUsersButton(ActionEvent actionEvent) {
        userController.loadUserViewData();
        showSection(usersBPane);
    }

    public void handleLibraryCatalogButton(ActionEvent actionEvent) {
        Task<Void> loadCatalog = new Task<>() {
            @Override
            protected Void call() throws Exception {
                catalogController.searchAPIDocument();
                return null;
            }
        };
        Thread thread = new Thread(loadCatalog);
        thread.setDaemon(true);
        thread.start();
        showSection(catalogBPane);
    }

    public void handlePendingApprovalsButton(ActionEvent actionEvent) {
        pendingApprovalsController.loadApprovalsData();
        showSection(pendingApprovalsBPane);
    }

    // PENDING ISSUE
    public void handlePendingLoansButton(ActionEvent actionEvent) {
        pendingLoanController.loadLoanData();
        showSection(pendingLoansBPane);
    }

    public void handleSearchPendingIssue(KeyEvent keyEvent) {
        pendingLoanController.handleSearchPendingIssue();
    }

    // ALL ISSUED DOCUMENT
    public void handleIssuedDocButton(ActionEvent actionEvent) {
        issuedDocument.loadLoanData();
        showSection(allIssuedDocBPane);
    }

    public void handleSearchID(KeyEvent keyEvent) {
        issuedDocument.handleSearchID();
    }

    public void handleClickAvatar(MouseEvent mouseEvent) {
    }

    public void handleExitAvatarInfo(MouseEvent mouseEvent) {
    }

    // DOCUMENT CONTROLLER

    @FXML
    public void handleSearchDocTField(KeyEvent actionEvent) {
        documentController.handleSearchDocTField();
    }

    public void checkAllDocs(ActionEvent actionEvent) {
        documentController.checkAllDocs();
    }

    public void handleAdvancedSearch(ActionEvent actionEvent) {
    }

    public void handleAddDocButton(ActionEvent actionEvent) {
    }

    public void handleImportDataButton(ActionEvent actionEvent) {
    }

    public void loadUpdateBook(ActionEvent actionEvent) {
    }

    public void DeleteBook(ActionEvent actionEvent) {
    }

    public void handleDeleteDocHyperlink(ActionEvent actionEvent) {
        documentController.handleDeleteDocHyperlink();
    }

    public void handleBackToDocs(ActionEvent actionEvent) {
    }

    public void handleSubmitDoc(ActionEvent actionEvent) {
    }

    //  USER CONTROLLER
    public void searchUserDetails(KeyEvent event) {
        userController.searchUserDetails();
    }

    public void importData(ActionEvent actionEvent) {
    }

    public void deleteUserRecord(ActionEvent actionEvent) {
        userController.deleteUserRecord();
    }

    public void handleCancelUserButton(ActionEvent actionEvent) {
        userController.handleCancelUserButton();
    }

    public void handleSaveUserButton(ActionEvent actionEvent) {
        userController.handleSaveUserButton();
    }

    public void handleUpdateUser(ActionEvent actionEvent) {
    }

    public void checkAllUsers(ActionEvent actionEvent) {
        userController.checkAllUsers();
    }

    //DOCUMENT MANAGEMENT
    public void handleIssueDocSwitch(ActionEvent actionEvent) {
    }

    public void handleReturnDocSwitch(ActionEvent actionEvent) {
    }

    public void handleBackToDoc(ActionEvent actionEvent) {
    }

    public void handleRenewDoc(ActionEvent actionEvent) {
    }

    public void handleSubmitIssueDoc(ActionEvent actionEvent) {
    }

    // PENDING APPROVAL
    public void disapprovePending(ActionEvent actionEvent) {
        pendingApprovalsController.disapprovePending();
    }

    public void checkAllPending(ActionEvent actionEvent) {
        pendingApprovalsController.checkAllPending();
    }

    public void approvePendingUsers(ActionEvent actionEvent) {
        pendingApprovalsController.approvePendingUsers();
    }

    public void handleSearchPendingUser(KeyEvent keyEvent) {
        pendingApprovalsController.handleSearchPendingUser();
    }

    //ANOTHER
    public void requestMenu(ContextMenuEvent contextMenuEvent) {
    }

    public void fetchUserWithKey(KeyEvent event) {
    }

    public void fetchUserFeesDetails(MouseEvent mouseEvent) {
        userController.fetchUserDetails();
    }
    //  SIGN OUT CONTROLLER

    /**
     * Handles the sign-out process for the user.
     */
    @FXML
    protected void handleSignOutButton(ActionEvent actionEvent) {
        signOutController.handleSignOut(actionEvent);
    }

    public void handleCancelRegisterDoc(ActionEvent actionEvent) {
    }

    // CATALOG
    public void handleSearchCatalog(KeyEvent keyEvent) {
        if (scheduler == null) {
            scheduler = Executors.newScheduledThreadPool(1);
        }

        if (scheduledTask != null && !scheduledTask.isDone()) {
            scheduledTask.cancel(false);
        }

        scheduledTask = scheduler.schedule(catalogController::searchAPIDocument, 400, TimeUnit.MILLISECONDS);
    }

    private ScheduledExecutorService scheduler;
    private ScheduledFuture<?> scheduledTask;
}



