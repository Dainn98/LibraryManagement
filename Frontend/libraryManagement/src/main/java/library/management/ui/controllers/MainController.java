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
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import jfxtras.scene.control.gauge.linear.SimpleMetroArcGauge;
import library.management.data.entity.Document;
import library.management.data.entity.Loan;

import library.management.data.entity.User;
import library.management.properties;
import org.controlsfx.control.CheckComboBox;

@SuppressWarnings("CallToPrintStackTrace")

    private final DashboardController dashboardController = new DashboardController(this);
    private final UserController userController = new UserController(this);
    private final DocumentController documentController = new DocumentController(this);
    private final CatalogController catalogController = new CatalogController(this);
    private final PendingApprovalsController pendingApprovalsController = new PendingApprovalsController(this);
    private final AvatarController avatarController = new AvatarController(this);
    private final PendingLoanController pendingLoanController = new PendingLoanController(this);
    private final IssuedDocument issuedDocument = new IssuedDocument(this);


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
    protected TableColumn<Loan, String> docTitleLoansView;
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

    // DOCUMENT INFORMATION PROPERTIES
    @FXML
    protected BorderPane docPropertiesBPane;
    @FXML
    protected Label titleInfo;
    @FXML
    protected Label authorInfo;
    @FXML
    protected Label publisherInfo;
    @FXML
    protected Label categoryInfo;
    @FXML
    protected Label languageInfo;
    @FXML
    protected Label isbnInfo;
    @FXML
    protected Label descriptionInfo;
    @FXML
    protected ImageView qrImageInfo;
    @FXML
    protected ImageView isbnImageInfo;
    @FXML
    protected Label titleHeading;
    @FXML
    protected ImageView thumbnailImageInfo;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dashboardController.loadDashBoardData();
        userController.initUsersView();
        documentController.initDocumentView();
        pendingApprovalsController.initApprovalsView();
        pendingLoanController.initPendingLoanView();
        issuedDocument.initIssueDocumentView();
        catalogController.initCatalog();
        avatarController.initAvatar(infoVBox);

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
        docManagementBPane.setVisible(sectionToShow == docManagementBPane);
        pendingLoansBPane.setVisible(sectionToShow == pendingLoansBPane);
        pendingApprovalsBPane.setVisible(sectionToShow == pendingApprovalsBPane);
    }

    @FXML
    protected void handleDashboardButton(ActionEvent actionEvent) {
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
                catalogController.searchDocument();
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

    public void checkAllPendingIssue(ActionEvent actionEvent) {
        pendingLoanController.checkAllPendingIssue();
    }

    public void deletePendingIssue(ActionEvent actionEvent) {
        pendingLoanController.disapprovePendingIssue();
    }

    public void disapprovePendingIssue(ActionEvent actionEvent) {
        pendingLoanController.disapprovePendingIssue();
    }

    public void approvePendingIssue(ActionEvent actionEvent) {
        pendingLoanController.approvePendingIssue();
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
        dashboardController.handleClickAvatar(pic, infoVBox);
    }

    public void handleExitAvatarInfo(MouseEvent mouseEvent) {
        dashboardController.handleExitAvatarInfo(infoVBox, pic);
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
        //To Do
    }

    public void handleAddDocButton(ActionEvent actionEvent) {
        //To Do
    }

    public void handleImportDataButton(ActionEvent actionEvent) {
        //To Do
    }

    public void loadUpdateBook(ActionEvent actionEvent) {
        //To Do
    }

    public void DeleteBook(ActionEvent actionEvent) {
        //To Do
    }

    public void handleDeleteDocHyperlink(ActionEvent actionEvent) {
        documentController.handleDeleteDocHyperlink();
    }

    //DOCUMENT MANAGEMENT CONTROLLER
    public void handleBackToDoc(ActionEvent actionEvent) {
        showSection(docBPane);
    }

    public void handleRenewDoc(ActionEvent actionEvent) {
    }

    public void handleSubmitIssueDoc(ActionEvent actionEvent) {
    }

    @FXML
    protected void handleReturnDocButton(ActionEvent actionEvent) {
        showSection(docManagementBPane);
        issueDocBPane.setVisible(false);
        returnDocBPane.setVisible(true);
    }

    @FXML
    protected void handleIssueDocButton(ActionEvent actionEvent) {
        showSection(docManagementBPane);
        issueDocBPane.setVisible(true);
        returnDocBPane.setVisible(false);
    }

    // DOCUMENT INFORMATION CONTROLLER
    @FXML
    private void handleBackToCatalog(ActionEvent actionEvent) {
    }

    @FXML
    private void handleAddDoc(ActionEvent actionEvent) {
    }

    @FXML
    private void handleCancelAddDoc(ActionEvent actionEvent) {
    }

    @FXML
    private void handleSubmitDoc(ActionEvent actionEvent) {
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


    public void handleCancelRegisterDoc(ActionEvent actionEvent) {
    }

    // CATALOG
    @FXML
    private void handleSearchCatalog(KeyEvent keyEvent) {
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
    protected void handleSignOutButton(ActionEvent actionEvent) {
        signOutController.handleSignOut(getClass());
        Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        currentStage.close();
    }


}



