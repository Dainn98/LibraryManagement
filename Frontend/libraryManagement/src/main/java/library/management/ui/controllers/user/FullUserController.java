package library.management.ui.controllers.user;

import com.gluonhq.charm.glisten.control.AutoCompleteTextField;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import jfxtras.scene.control.ImageViewButton;
import library.management.data.entity.Loan;
import library.management.data.entity.User;
import library.management.properties;
import library.management.ui.applications.SpeechToText;
import library.management.ui.controllers.FAQsController;
import library.management.ui.controllers.GeneralController;
import library.management.ui.controllers.SignOutController;
import org.controlsfx.control.CheckComboBox;

import static library.management.alert.AlertMaker.showAlertConfirmation;

/**
 * Controller class for managing the full user interface, including navigation between sections such as catalog, borrowed documents, processing, and FAQs.
 * This class handles user interactions, UI updates, and manages sub-controllers for various sections.
 */
public class FullUserController extends GeneralController implements Initializable, properties {

    // Sub-controllers for different sections of the user interface
    private final HomeController homeController = new HomeController(this);
    private final BorrowedController borrowedController = new BorrowedController(this);
    private final ProcessingController processingController = new ProcessingController(this);
    private final HistoryController historyController = new HistoryController(this);
    private final UserAvatarController avatarController = new UserAvatarController(this);
    private final FAQsController faqsController = new FAQsController(this);

    /**
     * Main user object that represents the currently logged-in user.
     */
    public static User mainUser;
    // FXML UI components
    @FXML
    protected BorderPane FAQsBPane;
    @FXML
    protected GridPane FAQsGPane;
    @FXML
    protected HBox faqContainer;
    @FXML
    protected BorderPane chatbotPane;
    @FXML
    protected JFXButton newChatButton;
    @FXML
    protected ScrollPane faqSPane;
    @FXML
    protected ImageViewButton sendTextButton;
    @FXML
    public JFXTextArea faqRequestContainer;
    @FXML
    protected ImageViewButton recordButton;

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
    protected Button FAQsButton;

    @FXML
    protected Button signOutButton;

    @FXML
    protected StackPane stackFull;

    public String path = Objects.requireNonNull(getClass().getResource("/ui/css/theme.css"))
            .toExternalForm();

    /**
     * Sets the main user and initializes the user avatar.
     *
     * @param mainUser The user to set as the current user.
     */
    public void setMainUser(User mainUser) {
        this.mainUser = mainUser;
        avatarController.initAvatar(infoVBox);
    }

    /**
     * Returns the username of the main user.
     *
     * @return The username of the main user.
     */
    public String getMainUserName() {
        return mainUser.getUserName();
    }

    /**
     * Returns the main user object.
     *
     * @return The main user object.
     */
    public static User getMainUser() {
        return mainUser;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        homeController.initHome();
        borrowedController.initBorrowedDocuments();
        processingController.initProcess();
        historyController.initIssueDocumentView();
        stackFull.getStylesheets().add(path);
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

    // MENU CONTROLLER

    /**
     * Toggles visibility between different sections of the user interface.
     *
     * @param sectionToShow The section to display.
     */
    @FXML
    private void showSection(Object sectionToShow) {
        catalogBPane.setVisible(sectionToShow == catalogBPane);
        docBPane.setVisible(sectionToShow == docBPane);
        processingPane.setVisible(sectionToShow == processingPane);
        borrowedPane.setVisible(sectionToShow == borrowedPane);
        FAQsBPane.setVisible(sectionToShow == FAQsBPane);
    }

    @FXML
    private void handleAlert(MouseDragEvent event) {

    }

    /**
     * Handles the borrow button click event, loading borrowed documents.
     *
     * @param event The action event triggered by the button click.
     */
    @FXML
    private void handleBorrowedDocButton(ActionEvent event) {
        borrowedController.loadBorrowingDocument();
        showSection(borrowedPane);
    }

    /**
     * Handles the avatar click event, showing avatar information.
     *
     * @param mouseEvent The mouse event triggered by the avatar click.
     */
    @FXML
    private void handleClickAvatar(MouseEvent mouseEvent) {
        homeController.handleClickAvatar(pic, infoVBox);
    }

    /**
     * Handles the exit of avatar information on mouse exit event.
     *
     * @param mouseEvent The mouse event triggered by the exit of avatar information.
     */
    @FXML
    private void handleExitAvatarInfo(MouseEvent mouseEvent) {
        homeController.handleExitAvatarInfo(infoVBox, pic);
    }

    /**
     * Handles the event when the user clicks the History button.
     * Loads the history of documents and displays the document section.
     *
     * @param event The button click event.
     */
    @FXML
    private void handleHistoryButton(ActionEvent event) {
        historyController.loadHistory();
        showSection(docBPane);
    }

    /**
     * Handles the event when the user clicks the Home button.
     * Searches for documents in the catalog and displays the catalog section.
     *
     * @param event The button click event.
     */
    @FXML
    private void handleHomeButton(ActionEvent event) {
        homeController.searchDocument();
        showSection(catalogBPane);
    }

    /**
     * Handles the event when the user types in the catalog search field.
     *
     * @param keyEvent The key event when the user types.
     */
    @FXML
    private void handleSearchCatalog(KeyEvent keyEvent) {
        homeController.searchDocument();
    }

    /**
     * Handles the event when the user presses the Enter key in the catalog search field.
     *
     * @param keyEvent The key event when the user presses the Enter key.
     */
    @FXML
    private void handleSearchCatalogPressed(KeyEvent keyEvent) {
        if (Objects.requireNonNull(keyEvent.getCode()) == KeyCode.ENTER) {
            homeController.searchDocument();
            homeController.addSuggestion();
        }
    }

    /**
     * Handles the event when the user types in the history search field.
     *
     * @param keyEvent The key event when the user types.
     */
    @FXML
    private void searchHistory(KeyEvent keyEvent) {
        historyController.handleSearchHistory();
    }

    /**
     * Handles the event when the user clicks the Policy button.
     * Opens the policy window in a new Stage.
     *
     * @param event The button click event.
     */
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

    /**
     * Handles the event when the user clicks the Processing button.
     * Loads the pending documents and displays the processing section.
     *
     * @param event The button click event.
     */
    @FXML
    private void handleProcessingButton(ActionEvent event) {
        processingController.loadPendingDocument();
        showSection(processingPane);
    }

    /**
     * Handles the event when the user clicks the Sign Out button.
     * Displays a confirmation alert and signs the user out if confirmed.
     *
     * @param event The button click event.
     */
    @FXML
    private void handleSignOutButton(ActionEvent event) {
        Optional<ButtonType> result = showAlertConfirmation("Sign Out",
                "Are you sure you want to sign out?");
        if (result.isPresent() && result.get() == ButtonType.OK) {
            SignOutController.handleSignOut();
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();
        }
    }

    /**
     * Handles the event when the user clicks the FAQs button.
     * Displays the FAQ section and the chatbot.
     *
     * @param actionEvent The button click event.
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
     * Handles the event when the user clicks the Reset button in the FAQ section.
     * Resets the chatbot and FAQ sections.
     *
     * @param actionEvent The button click event.
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
     * Handles the event when the user clicks the Record button in the FAQ section.
     * Starts or stops recording and converting speech to text.
     *
     * @param mouseEvent The mouse click event.
     */
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
                protected Void call() {
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
     * Handles the event when the mouse enters the Send button.
     * Changes the image of the Send button when the mouse enters.
     *
     * @param mouseEvent The mouse enter event.
     */
    @FXML
    private void handleMouseEnterSend(MouseEvent mouseEvent) {
        sendTextButton.setImage(new Image(
                Objects.requireNonNull(getClass().getResourceAsStream(SEND_HOVER_SOURCE))));
    }

    /**
     * Handles the event when the mouse exits the Send button.
     * Changes the image of the Send button when the mouse exits.
     *
     * @param mouseEvent The mouse exit event.
     */
    @FXML
    private void handleMouseExitSend(MouseEvent mouseEvent) {
        sendTextButton.setImage(new Image(
                Objects.requireNonNull(getClass().getResourceAsStream(SEND_SOURCE))));
    }

    /**
     * Handles the event when the user clicks the Send button.
     * Sends the question and displays the result from the chatbot.
     *
     * @param mouseEvent The mouse click event.
     */
    @FXML
    private void handleSendText(MouseEvent mouseEvent) {
        handleSendText();
    }

    /**
     * Handles sending the user's question to the chatbot.
     * Updates the UI and displays the new question.
     */
    private void handleSendText() {
        faqsController.loadFAQs(FAQsGPane, faqSPane);
        if (!newChatButton.isVisible()) fade(newChatButton, 0.5, 1, Duration.millis(500));
        if (!faqSPane.isVisible()) fade(faqSPane, 0.5, 1, Duration.millis(500));
        if (chatbotPane.isVisible()) fade(chatbotPane, 0.5, 0, Duration.millis(500));
    }
}
