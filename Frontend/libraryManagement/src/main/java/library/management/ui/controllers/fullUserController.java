package library.management.ui.controllers;

import com.gluonhq.charm.glisten.control.AutoCompleteTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
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
import library.management.data.entity.Document;
import library.management.properties;

public class fullUserController implements Initializable, properties, GeneralController {

  private final homeBookController homeController = new homeBookController(this);
  private final borrowedController borrowedController = new borrowedController(this);
  private final processingController processingController = new processingController(this);
  private final historyController historyController = new historyController(this);
  private final AvatarController2 avatarController = new AvatarController2(this);

  @FXML
  protected GridPane borrowViewGPane;

  @FXML
  protected GridPane processViewGPane;

  @FXML
  protected FontAwesomeIconView alert;

  @FXML
  protected TableColumn<?, ?> availabilityDocView;

  @FXML
  protected Button borrowButton;

  @FXML
  protected BorderPane catalogBPane;

  @FXML
  protected AutoCompleteTextField<?> catalogSearchField;

  @FXML
  protected CheckBox checkAllDocs;

  @FXML
  protected TableColumn<?, ?> checkDocView;

  @FXML
  protected ScrollPane borrowedPane;

  @FXML
  protected ScrollPane processingPane;

  @FXML
  protected HBox controlBoxDocView;

  @FXML
  protected Hyperlink deleteDocs;

  @FXML
  protected TableColumn<?, ?> docAuthorDocView;

  @FXML
  protected BorderPane docBPane;

  @FXML
  protected TableColumn<?, ?> docIDDocView;

  @FXML
  protected TableColumn<?, ?> docISBNDocView;

  @FXML
  protected TableColumn<?, ?> docNameDocView;

  @FXML
  protected TableColumn<?, ?> docPublisherDocView;

  @FXML
  protected TableView<?> docView;

  @FXML
  protected FontAwesomeIconView filter;

  @FXML
  protected GridPane fineGrid;

  @FXML
  protected GridPane fourGrid;

  @FXML
  protected TableColumn<?, ?> genreDocDocView;

  @FXML
  protected VBox home;

  @FXML
  protected Button homeButton;

  @FXML
  protected BorderPane homePane;

  @FXML
  protected VBox infoVBox;

  @FXML
  protected GridPane oneGrid;

  @FXML
  protected Button pendingHistory;

  @FXML
  protected ImageView pic;

  @FXML
  protected Button policyButton;

  @FXML
  protected Button processingButton;

  @FXML
  protected TableColumn<?, ?> quantityDocView;

  @FXML
  protected TableColumn<?, ?> remainingDocsDocView;

  @FXML
  protected TextField searchDocTField;

  @FXML
  protected Button settingButton;

  @FXML
  protected Button signOutButton;

  @FXML
  protected GridPane threeGrid;

  @FXML
  protected GridPane twoGrid;

  @FXML
  protected Button userInformationButton;
  private List<Document> documentList;

  public List<Document> getDocumentList() {
    return documentList;
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    homeController.initHomeData();
    borrowedController.initBorrowed();
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

  }

  @FXML
  void handleAlert(MouseDragEvent event) {

  }

  @FXML
  void handleBorrowedDocButton(ActionEvent event) {
    Task<Void> loadHome = new Task<Void>() {
      @Override
      protected Void call() throws Exception {
        borrowedController.initBorrowed();
        return null;
      }
    };
    Thread thread = new Thread(loadHome);
    thread.setDaemon(true);
    thread.start();
    showSection(borrowedPane);
  }

  @FXML
  void handleClickAvatar(MouseEvent event) {

  }

  @FXML
  void handleDeleteDocHyperlink(ActionEvent event) {

  }

  @FXML
  void handleExitAvatarInfo(MouseEvent event) {

  }

  @FXML
  void handleFilter(MouseDragEvent event) {

  }

  @FXML
  void handleHistoryButton(ActionEvent event) {
    Task<Void> loadHistory = new Task<Void>() {
      @Override
      protected Void call() throws Exception {
        historyController.initIssueDocumentView();
        return null;
      }
    };
    Thread thread = new Thread(loadHistory);
    thread.setDaemon(true);
    thread.start();
    showSection(docBPane);
  }

  @FXML
  void handleHomeButton(ActionEvent event) {
    Task<Void> loadHome = new Task<Void>() {
      @Override
      protected Void call() throws Exception {
        homeController.initHomeData();
        return null;
      }
    };
    Thread thread = new Thread(loadHome);
    thread.setDaemon(true);
    thread.start();
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
    Task<Void> loadHome = new Task<Void>() {
      @Override
      protected Void call() throws Exception {
        processingController.initProcess();
        return null;
      }
    };
    Thread thread = new Thread(loadHome);
    thread.setDaemon(true);
    thread.start();
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

  }

  @FXML
  void searchBook(KeyEvent event) {

  }

}
