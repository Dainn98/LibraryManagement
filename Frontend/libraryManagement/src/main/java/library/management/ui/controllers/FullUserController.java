package library.management.ui.controllers;

import com.gluonhq.charm.glisten.control.AutoCompleteTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import library.management.data.entity.Loan;
import library.management.data.entity.User;
import library.management.properties;

public class FullUserController implements Initializable, properties, GeneralController {

  private final HomeController homeController = new HomeController(this);
  private final BorrowedController borrowedController = new BorrowedController(this);
  private final ProcessingController processingController = new ProcessingController(this);
  private final HistoryController historyController = new HistoryController(this);
  private final AvatarController2 avatarController = new AvatarController2(this);
  private User mainUser;

  @FXML
  protected GridPane borrowViewGPane;

  @FXML
  protected GridPane processViewGPane;

  @FXML
  protected FontAwesomeIconView alert;

  @FXML
  protected Button borrowButton;

  @FXML
  protected AutoCompleteTextField<?> catalogSearchField;

  @FXML
  protected ScrollPane borrowedPane;

  @FXML
  protected ScrollPane processingPane;

  @FXML
  protected HBox controlBoxDocView;

  @FXML
  protected BorderPane docBPane;

  // HOME
  @FXML
  protected BorderPane catalogBPane;

  @FXML
  protected GridPane oneGrid;

  @FXML
  protected GridPane twoGrid;

  // HISTORY
  @FXML
  protected TableView<Loan> docView;

  @FXML
  protected FontAwesomeIconView filter;

  @FXML
  protected TableColumn<Loan, String> docIDDocView;

  @FXML
  protected TableColumn<Loan, String> docISBNDocView;

  @FXML
  protected TableColumn<Loan, String> docNameDocView;

  @FXML
  protected TableColumn<Loan, String> docAuthorDocView;

  @FXML
  protected TableColumn<Loan, String> docPublisherDocView;

  @FXML
  protected TableColumn<Loan, String> categoryDocDocView;

  @FXML
  protected TableColumn<Loan, Integer> quantityDocView;

  @FXML
  protected TableColumn<Loan, String> requestedDateView;

  @FXML
  protected TableColumn<Loan, String> returnDateView;

  @FXML
  protected TableColumn<Loan, String> statusLoanView;

  @FXML
  protected VBox home;

  @FXML
  protected Button homeButton;

  @FXML
  protected BorderPane homePane;

  @FXML
  protected VBox infoVBox;

  @FXML
  protected Button pendingHistory;

  @FXML
  protected ImageView pic;

  @FXML
  protected Button policyButton;

  @FXML
  protected Button processingButton;

  @FXML
  protected TextField searchDocTField;

  @FXML
  protected Button settingButton;

  @FXML
  protected Button signOutButton;

  @FXML
  protected Button userInformationButton;

  public void setMainUser(User mainUser) {
    this.mainUser = mainUser;
  }

  public String getMainUserName() {
    return mainUser.getUserName();
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    homeController.initHome();
    borrowedController.initBorrowedDocuments();
    processingController.initProcess();
    historyController.initIssueDocumentView();
    avatarController.initAvatar(infoVBox);
  }

  // MENU CONTROLLER
  @FXML
  protected void showSection(Object sectionToShow) {
    catalogBPane.setVisible(sectionToShow == catalogBPane);
    docBPane.setVisible(sectionToShow == docBPane);
    processingPane.setVisible(sectionToShow == processingPane);
    borrowedPane.setVisible(sectionToShow == borrowedPane);
  }


  @FXML
  void handleAdvancedSearch(ActionEvent event) {
    showSection(catalogBPane);
  }

  @FXML
  void handleAlert(MouseDragEvent event) {

  }

  @FXML
  void handleBorrowedDocButton(ActionEvent event) {
    borrowedController.loadBorrowingDocument();
    showSection(borrowedPane);
  }

  @FXML
  void handleClickAvatar(MouseEvent event) {
  }

  @FXML
  void handleExitAvatarInfo(MouseEvent event) {

  }

  @FXML
  void handleFilter(MouseDragEvent event) {

  }

  @FXML
  void handleHistoryButton(ActionEvent event) {
    historyController.loadHistory();
    showSection(docBPane);
  }

  @FXML
  void handleHomeButton(ActionEvent event) {
    showSection(catalogBPane);
  }

  @FXML
  void handleInforButton(ActionEvent event) {

  }

  @FXML
  void handlePolicyButton(ActionEvent event) {

  }

  @FXML
  void handleProcessingButton(ActionEvent event) {
    processingController.loadPendingDocument();
    showSection(processingPane);
  }

  @FXML
  void handleSearchDocTField(ActionEvent event) {

  }

  @FXML
  void handleSettingButton(ActionEvent event) {

  }

  @FXML
  void handleSignOutButton(ActionEvent event) {
    SignOutController.handleUserSignOut(getClass());
    Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    currentStage.close();
  }

  @FXML
  void searchBook(KeyEvent event) {
  }

}
