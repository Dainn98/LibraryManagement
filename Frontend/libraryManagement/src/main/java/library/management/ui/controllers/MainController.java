package library.management.ui.controllers;


import com.gluonhq.charm.glisten.control.AutoCompleteTextField;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
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
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import jfxtras.scene.control.gauge.linear.SimpleMetroArcGauge;
import library.management.data.entity.Document;
import library.management.data.entity.User;
import library.management.properties;
import org.controlsfx.control.CheckComboBox;

@SuppressWarnings("CallToPrintStackTrace")
public class MainController implements Initializable, properties, GeneralController {


  private final DashboardController dashboardController = new DashboardController(this);
  private final UserController userController = new UserController(this);
  private final CatalogController catalogController = new CatalogController(this);
  private final AvatarController avatarController = new AvatarController(this);
  // Dashboard Window
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
  // Document Window
  @FXML
  protected BorderPane docBPane;
  @FXML
  protected Label allUsers;
  @FXML
  protected Label remainDocs;
  @FXML
  protected TextField searchDocTField;
  @FXML
  protected JFXButton addDocButton;
  @FXML
  protected JFXButton importDataButton;
  @FXML
  protected TableView<?> docView;
  @FXML
  protected TableColumn<?, ?> checkDocView;
  @FXML
  protected TableColumn<?, ?> docIDDocView;
  @FXML
  protected TableColumn<?, ?> docISBNDocView;
  @FXML
  protected TableColumn<?, ?> docNameDocView;
  @FXML
  protected TableColumn<?, ?> docAuthorDocView;
  @FXML
  protected TableColumn<?, ?> docPublisherDocView;
  @FXML
  protected TableColumn<?, ?> genreDocDocView;
  @FXML
  protected TableColumn<?, ?> quantityDocView;
  @FXML
  protected TableColumn<?, ?> remainingDocsDocView;
  @FXML
  protected TableColumn<?, ?> availabilityDocView;
  @FXML
  protected HBox controlBoxDocView;
  @FXML
  protected CheckBox checkAllDocs;
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
  protected AutoCompleteTextField<?> checkUsername;
  @FXML
  protected CheckComboBox<?> checkCountry;
  @FXML
  protected CheckComboBox<?> checkState;
  @FXML
  protected CheckComboBox<?> checkYear;
  @FXML
  protected TableView<?> approvalsTView;
  @FXML
  protected TableColumn<?, ?> idApprovals;
  @FXML
  protected TableColumn<?, ?> usernameApprovals;
  @FXML
  protected TableColumn<?, ?> phoneNumberApprovals;
  @FXML
  protected TableColumn<?, ?> emailApprovals;
  @FXML
  protected TableColumn<?, ?> countryApprovals;
  @FXML
  protected TableColumn<?, ?> stateApprovals;
  @FXML
  protected TableColumn<?, ?> yearApprovals;
  @FXML
  protected TableColumn<?, ?> approvalApprovals;
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
  protected JFXComboBox<?> issueTypeIssuedDoc;
  @FXML
  protected TableView<?> IDView;
  @FXML
  protected TableColumn<?, ?> issuedIDIDView;
  @FXML
  protected TableColumn<?, ?> docIDIDView;
  @FXML
  protected TableColumn<?, ?> docTitleIDView;
  @FXML
  protected TableColumn<?, ?> userIDIDView;
  @FXML
  protected TableColumn<?, ?> userNameIDView;
  @FXML
  protected TableColumn<?, ?> dueDateIDView;
  @FXML
  protected TableColumn<?, ?> issuedDateAndTimeIDView;
  @FXML
  protected TableColumn<?, ?> daysIDView;
  @FXML
  protected TableColumn<?, ?> feeIDView;
  // CATALOG
  @FXML
  protected BorderPane catalogBPane;
  @FXML
  protected GridPane apiViewGPane;
  @FXML
  protected GridPane localViewGPane;
  private List<Document> documentList;

  public List<Document> getDocumentList() {
    return documentList;
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    dashboardController.loadDashBoardData();
    userController.initUsersView();
    catalogController.loadCatalogData(apiViewGPane, localViewGPane);
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
  }

  @FXML
  protected void handleDashboardButton(ActionEvent actionEvent) {
    dashboardController.loadDashBoardData();
    showSection(dashboardVBox);
  }

  public void handleDocButton(ActionEvent actionEvent) {
    showSection(docBPane);
  }

  public void handleUsersButton(ActionEvent actionEvent) {
    userController.loadUserViewData();
    showSection(usersBPane);
  }

  public void handleLibraryCatalogButton(ActionEvent actionEvent) {
    showSection(catalogBPane);
  }

  public void handlePendingApprovalsButton(ActionEvent actionEvent) {
    showSection(pendingApprovalsBPane);
  }

  public void handlePendingLoansButton(ActionEvent actionEvent) {
  }

  public void handleIssuedDocButton(ActionEvent actionEvent) {
    showSection(allIssuedDocBPane);
  }

  public void handleClickAvatar(MouseEvent mouseEvent) {
    dashboardController.handleClickAvatar(pic, infoVBox);
  }

  public void handleExitAvatarInfo(MouseEvent mouseEvent) {
    dashboardController.handleExitAvatarInfo(infoVBox, pic);
  }


  // DOCUMENT CONTROLLER

  public void handleSearchDocTField(ActionEvent actionEvent) {
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
  }

  public void handleBackToDocs(ActionEvent actionEvent) {
  }

  public void handleCancelRegisterDoc(ActionEvent actionEvent) {
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

  //ANOTHER
  public void searchBook(KeyEvent event) {
  }

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
}



