package library.management.ui.controllers;

import com.gluonhq.charm.glisten.control.AutoCompleteTextField;
import com.jfoenix.controls.JFXComboBox;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import library.management.data.entity.Loan;
import library.management.data.entity.User;
import library.management.properties;
import org.controlsfx.control.CheckComboBox;

import static library.management.alert.AlertMaker.showAlertConfirmation;

public class FullUserController implements Initializable, properties, GeneralController {

  private final HomeController homeController = new HomeController(this);
  private final BorrowedController borrowedController = new BorrowedController(this);
  private final ProcessingController processingController = new ProcessingController(this);
  private final HistoryController historyController = new HistoryController(this);
  private final UserAvatarController avatarController = new UserAvatarController(this);

  public static User mainUser;

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

  @FXML
  protected GridPane threeGrid;

  @FXML
  protected GridPane fourGrid;

  @FXML
  protected GridPane fiveGrid;

  @FXML
  protected Label firstLabel;

  @FXML
  protected Label secondLabel;

  @FXML
  protected Label thirdLabel;

  @FXML
  protected Label fourthLabel;

  @FXML
  protected Label fifthLabel;

  @FXML
  protected CheckComboBox<String> searchDocumentFilter;


  // HISTORY
  @FXML
  protected TableView<Loan> docView;

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
  protected JFXComboBox<String> historyFilter;

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
    FullUserController.mainUser = mainUser;
    avatarController.initAvatar(infoVBox);
  }

  public User getMainUser() {
    return mainUser;
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
  }

  // MENU CONTROLLER
  @FXML
  private void showSection(Object sectionToShow) {
    catalogBPane.setVisible(sectionToShow == catalogBPane);
    docBPane.setVisible(sectionToShow == docBPane);
    processingPane.setVisible(sectionToShow == processingPane);
    borrowedPane.setVisible(sectionToShow == borrowedPane);
  }

  @FXML
  private void handleAlert(MouseDragEvent event) {

  }

  @FXML
  private void handleBorrowedDocButton(ActionEvent event) {
    borrowedController.loadBorrowingDocument();
    showSection(borrowedPane);
  }

  @FXML
  private void handleClickAvatar(MouseEvent event) {
    rotate3D(pic, 0, 1, infoVBox, 270, 1, 90, Duration.millis(1000));
  }

  @FXML
  private void handleExitAvatarInfo(MouseEvent event) {

  }

  @FXML
  private void handleHistoryButton(ActionEvent event) {
    historyController.loadHistory();
    showSection(docBPane);
  }

  // HOME
  @FXML
  private void handleHomeButton(ActionEvent event) {
    homeController.searchDocument();
    showSection(catalogBPane);
  }

  @FXML
  private void handleSearchCatalog(KeyEvent keyEvent) {
    homeController.searchDocument();
  }

  @FXML
  private void handleSearchCatalogPressed(KeyEvent keyEvent) {
    switch (keyEvent.getCode()) {
      case ENTER -> {
        homeController.searchDocument();
        homeController.addSuggestion();
      }
    }
  }

  @FXML
  private void searchHistory(KeyEvent keyEvent) {
    historyController.handleSearchHistory();
  }

  @FXML
  private void handleInfoButton(ActionEvent event) {

  }

  @FXML
  private void handlePolicyButton(ActionEvent event) {
    try {
      FXMLLoader fxmlLoader = new FXMLLoader();
      fxmlLoader.setLocation(getClass().getResource("/ui/fxml/policy.fxml"));
      Parent root = fxmlLoader.load();
      Stage stage = new Stage();
      stage.setTitle("Policy");

      stage.setResizable(false);
      stage.setScene(new Scene(root));
      stage.show();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @FXML
  private void handleProcessingButton(ActionEvent event) {
    processingController.loadPendingDocument();
    showSection(processingPane);
  }

  @FXML
  private void handleSettingButton(ActionEvent event) {
    try {
      FXMLLoader fxmlLoader = new FXMLLoader();
      fxmlLoader.setLocation(getClass().getResource(SETTINGS_SOURCE));
      Parent root = fxmlLoader.load();
      Stage stage = new Stage();
      stage.setTitle(SETTINGS_TITLE);
      SettingsController controller = fxmlLoader.getController();
      controller.setFullUserControllerController(this);
      controller.setData();

      stage.setResizable(false);
      stage.setScene(new Scene(root));
      stage.setOnCloseRequest((WindowEvent windowEvent) -> {
        stage.close();
      });
      stage.show();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @FXML
  public void handleSignOutButton(ActionEvent event) {
    Optional<ButtonType> result = showAlertConfirmation("Sign Out",
            "Are you sure you want to sign out?");
    if (result.isPresent() && result.get() == ButtonType.OK) {
      SignOutController.handleUserSignOut(getClass());
      Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
      currentStage.close();
    }
  }
}
