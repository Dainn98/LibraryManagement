package library.management.ui.controllers;


import static library.management.alert.AlertMaker.showAlertConfirmation;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import library.management.data.DAO.*;
import library.management.data.entity.Document;
import library.management.data.entity.Genre;
import library.management.data.entity.Language;
import library.management.data.entity.User;
import library.management.ui.AbstractUI;
import jfxtras.scene.control.gauge.linear.SimpleMetroArcGauge;


@SuppressWarnings("CallToPrintStackTrace")
public class MainController implements Initializable, AbstractUI {
    /**
     * Menu Button.
     */
    @FXML
    protected Button dashboardButton;
    @FXML
    protected Button documentsButton;
    @FXML
    protected Button studentsButton;
    @FXML
    protected Button libraryCatalogButton;
    @FXML
    protected Button pendingApprovalsButton;
    @FXML
    protected Button manageDocumentLoanButton;
    @FXML
    protected Button allIssuedDocumentButton;
    @FXML
    protected Button signOutButton;
    /**
     * Dashboard UI.
     */
    @FXML
    protected VBox dashboardVBox;
    @FXML
    protected Label minimise;
    @FXML
    protected Label fullscreen;
    @FXML
    protected Label unfullscreen;
    @FXML
    protected Label close;
    @FXML
    protected Button findBookButton;
    @FXML
    protected Button findBookIssueButton;
    @FXML
    protected Button findStudentButton;
    @FXML
    protected BarChart<String, Number> barchart;
    @FXML
    protected CategoryAxis xAxis;
    @FXML
    protected NumberAxis yAxis;
    @FXML
    protected SimpleMetroArcGauge allBooksGauge;
    @FXML
    protected SimpleMetroArcGauge remainingBooksGauge;
    @FXML
    protected SimpleMetroArcGauge issuedBooksGauge;
    @FXML
    protected SimpleMetroArcGauge allStudentsGauge;
    @FXML
    protected SimpleMetroArcGauge bookHoldersGauge;

    /**
     * Pending Approvals UI.
     */
    @FXML
    protected VBox pendingApprovalsVBox;
    @FXML
    protected ComboBox branchComboBoxPendingApprovals;
    @FXML
    protected ComboBox categoryComboBoxPendingApprovals;
    @FXML
    protected ComboBox yearComboBoxPendingApprovals;
    @FXML
    protected TableView tableViewPendingApprovals;
    @FXML
    protected TableColumn idColumnPendingApprovals;
    @FXML
    protected TableColumn usernameColumnPendingApprovals;
    @FXML
    protected TableColumn identityCardColumnPendingApprovals;
    @FXML
    protected TableColumn mobileColumnPendingApprovals;
    @FXML
    protected TableColumn categoryColumnPendingApprovals;
    @FXML
    protected TableColumn approveColumnPendingApprovals;

    /**
     * Registered new book UI.
     */
    @FXML
    protected VBox registerDocVBox;
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
    protected JFXComboBox<Genre> genreComboBox;
    @FXML
    protected JFXComboBox<Language> languageComboBox;
    @FXML
    protected TextField numberOfIssueField;
    @FXML
    protected JFXButton cancelButton;
    @FXML
    protected Button submitButton;

    /**
     * Library Catalog UI.
     */
    @FXML
    protected AnchorPane libraryCatalogAPane;
    @FXML
    protected TableView tableViewLibraryCatalog;
    @FXML
    protected TableColumn titleColumnLibraryCatalog;
    @FXML
    protected TableColumn bookIdColumnLibraryCatalog;
    @FXML
    protected TableColumn authorColumnLibraryCatalog;
    @FXML
    protected TableColumn publisherColumnLibraryCatalog;
    @FXML
    protected TableColumn availabilityColumnLibraryCatalog;

    /**
     * Registered Students UI.
     */
    @FXML
    protected BorderPane studentsBPane;
    @FXML
    protected HBox controlUserView;
    @FXML
    protected VBox registerUserVBox;
    @FXML
    protected VBox findUserVBox;
    @FXML
    protected TextField userIDField;
    @FXML
    protected TextField userNameField;
    @FXML
    protected TextField userEmailField;
    @FXML
    protected TextField userPhoneField;
    @FXML
    protected JFXButton cancel;
    @FXML
    protected JFXButton save;
    @FXML
    protected TableView<User> userView;
    @FXML
    protected TableColumn checkUserView;
    @FXML
    protected TableColumn<User, String> userIDUserView;
    @FXML
    protected TableColumn<User, String> userNameUserView;
    @FXML
    protected TableColumn<User, String> userPhoneUserView;
    @FXML
    protected TableColumn<User, String> userEmailUserView;

    /**
     * Manage Book Loans UI.
     */
    @FXML
    protected VBox docManagementVBox;

    /**
     * Issued Books UI.
     */
    @FXML
    protected VBox issuedBooksVBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadDashBoardData();
        // students
        initUserViewCol();
    }

    public void initUserViewCol() {
        userIDUserView.setCellValueFactory(new PropertyValueFactory<>("userId"));
        userNameUserView.setCellValueFactory(new PropertyValueFactory<>("userName"));
        userPhoneUserView.setCellValueFactory(new PropertyValueFactory<>("mobile"));
        userEmailUserView.setCellValueFactory(new PropertyValueFactory<>("email"));
    }

    public void loadUserViewData() {
        ObservableList<User> list = FXCollections.observableArrayList();
        list.addAll(UserDAO.getInstance().getAllUser());
        userView.setItems(list);
    }

    @FXML
    protected void showSection(Object sectionToShow) {
        studentsBPane.setVisible(sectionToShow == studentsBPane);
        dashboardVBox.setVisible(sectionToShow == dashboardVBox);
        registerDocVBox.setVisible(sectionToShow == registerDocVBox);
        libraryCatalogAPane.setVisible(sectionToShow == libraryCatalogAPane);
        pendingApprovalsVBox.setVisible(sectionToShow == pendingApprovalsVBox);
        issuedBooksVBox.setVisible(sectionToShow == issuedBooksVBox);
        docManagementVBox.setVisible(sectionToShow == docManagementVBox);
    }

    /**
     * Navigates the user to the dashboard.
     */
    @FXML
    private void navigateToDashboardButton(ActionEvent actionEvent) {
        loadDashBoardData();
        showSection(dashboardVBox);
    }

    /**
     * load dashboard data
     */
    private void loadDashBoardData() {
        // load barchart
        barchart.getData().clear();
        XYChart.Series<String, Number> documentInformation = new XYChart.Series<>();
        documentInformation.setName("Document information");
        int bookQuantity = DocumentDAO.getInstance().getTotalQuantity();
        int remainingBookQuantity = DocumentDAO.getInstance().getTotalAvailableCopies();
        documentInformation.getData().add(new XYChart.Data<>("All Documents", bookQuantity));
        documentInformation.getData().add(new XYChart.Data<>("Remaining Documents", remainingBookQuantity));
        documentInformation.getData().add(new XYChart.Data<>("Issued Documents", bookQuantity - remainingBookQuantity));
        XYChart.Series<String, Number> studentInformation = new XYChart.Series<>();
        studentInformation.setName("Student information");
        int totalStudent = UserDAO.getInstance().getTotalUsersCount();
        int studentHoldingBook = LoanDAO.getInstance().getTotalUsersWhoBorrowedBooks();
        studentInformation.getData().add(new XYChart.Data<>("All Student", totalStudent));
        studentInformation.getData().add(new XYChart.Data<>("Students holding documents", studentHoldingBook));
        barchart.getData().add(documentInformation);
        barchart.getData().add(studentInformation);
        // load gauge
        allBooksGauge.setMaxValue(bookQuantity);
        allBooksGauge.setValue(bookQuantity);
        remainingBooksGauge.setMaxValue(bookQuantity);
        remainingBooksGauge.setValue(remainingBookQuantity);
        issuedBooksGauge.setMaxValue(bookQuantity);
        issuedBooksGauge.setValue(bookQuantity - remainingBookQuantity);
        allStudentsGauge.setMaxValue(totalStudent);
        allStudentsGauge.setValue(totalStudent);
        bookHoldersGauge.setMaxValue(totalStudent);
        bookHoldersGauge.setValue(studentHoldingBook);
    }

    /**
     * Displays a list of students awaiting approval.
     */
    @FXML
    private void handlePendingApprovalsButton(ActionEvent actionEvent) {
        showSection(pendingApprovalsVBox);
    }

    /**
     * Shows all registered students in the system.
     */
    @FXML
    private void handleRegisteredStudentsButton(ActionEvent actionEvent) {
        loadUserViewData();
        showSection(studentsBPane);
    }

    /**
     * Displays all books currently available in the library.
     */
    @FXML
    private void handleLibraryCatalogButton(ActionEvent actionEvent) {
        showSection(libraryCatalogAPane);
    }

    /**
     * Adds a new book to the library's collection.
     */
    @FXML
    private void handleRegisterNewBookButton(ActionEvent actionEvent) {
        Task<Void> loadgenreComboBox = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                genreComboBox.getItems().addAll(GenreDAO.getInstance().getGenreList());
                return null;
            }
        };

        Task<Void> loadLanguageComboBox = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                languageComboBox.getItems().addAll(LanguageDAO.getInstance().getLanguageList());
                return null;
            }
        };
        new Thread(loadgenreComboBox).start();
        new Thread(loadLanguageComboBox).start();
        showSection(registerDocVBox);
    }

    /**
     * Handles the process of issuing or returning books in the library.
     */
    @FXML
    private void handleManageBookLoansButton(ActionEvent actionEvent) {
        showSection(docManagementVBox);
    }

    /**
     * Shows a list of books that are currently issued to users.
     */
    @FXML
    private void handleIssuedBooksButton(ActionEvent actionEvent) {
        showSection(issuedBooksVBox);
    }

    /**
     * Handles the sign-out process for the user.
     */
    @FXML
    private void handleSignOutButton(ActionEvent actionEvent) {
        Optional<ButtonType> result = showAlertConfirmation(
                "Sign Out",
                "Are you sure you want to sign out?");
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/fxml/login.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Handles the process of adding books to the library.
     */
    @FXML
    private void handleAddBooks(ActionEvent actionEvent) {
        //Database
    }

    /**
     * Handles the process of viewing the library catalog.
     */
    @FXML
    protected void handleRefresh(ActionEvent actionEvent) {
    }

    /**
     * Handles the process of editing a book in the library.
     *
     * @param actionEvent the event that triggered this action
     */
    @FXML
    protected void handleBookEditOption(ActionEvent actionEvent) {
    }

    /**
     * Handles the process of deleting a book from the library.
     */
    @FXML
    protected void handleBookDeleteOption(ActionEvent actionEvent) {
    }

    /**
     * Handles the process of exporting the library catalog as a PDF.
     */
    @FXML
    protected void exportAsPDF(ActionEvent actionEvent) {
    }

    /**
     * Handles the process of exporting the library catalog as an Excel file.
     */
    @FXML
    protected void closeStage(ActionEvent actionEvent) {
    }

    @FXML
    protected void handleIssueBookButton(ActionEvent actionEvent) {
    }

    @FXML
    protected void handlerReturnBook(ActionEvent actionEvent) {
    }

    protected TextField getTitleOfBookField() {
        return docTitleField;
    }

    protected void setTitleOfBookField(TextField titleOfBookField) {
        this.docTitleField = titleOfBookField;
    }

    @FXML
    protected void handleFindBookButton(ActionEvent actionEvent) {
        //To do in DashboardController
    }

    @FXML
    protected void handleFindBookIssueButton(ActionEvent actionEvent) {
        //To do in DashboardController
    }

    @FXML
    protected void handleFindStudentButton(ActionEvent actionEvent) {
        //To do in DashboardController
    }

    public void loadUpdateBook(ActionEvent actionEvent) {
    }

    public void DeleteBook(ActionEvent actionEvent) {
    }

    public void selectRecordsType(ActionEvent actionEvent) {
    }

    public void importData(ActionEvent actionEvent) {
    }

    @FXML
    public void cancel(ActionEvent actionEvent) {
        userIDField.clear();
        userNameField.clear();
        userEmailField.clear();
        userPhoneField.clear();
    }

    public void saveStudent(ActionEvent actionEvent) {
    }

    public void deleteStudent(ActionEvent actionEvent) {
    }

    public void updateStudent(ActionEvent actionEvent) {
    }

    public void requestMenu(ContextMenuEvent contextMenuEvent) {
    }

    public void fetchStudentWithKey(KeyEvent event) {
    }

    public void deleteStudentRecord(ActionEvent actionEvent) {
    }

    public void deleteselectedStudents(ActionEvent actionEvent) {
    }

    public void minimize(MouseEvent mouseEvent) {
    }

    public void fullscreen(MouseEvent mouseEvent) {
    }

    public void unfullscreen(MouseEvent mouseEvent) {
    }

    public void close(MouseEvent mouseEvent) {
    }

    public void searchBook(KeyEvent event) {
    }

    public void loadBookDataentry(ActionEvent actionEvent) {
    }

    public void searchStudentDeatails(KeyEvent event) {
    }

    public void fetchStudentFeesDetails(MouseEvent mouseEvent) {
    }

    public void handleIssueDocSwitch(ActionEvent actionEvent) {
    }

    public void handleReturnDocSwitch(ActionEvent actionEvent) {
    }

    public void handleCancel(ActionEvent actionEvent) {
        docISBNField.clear();
        docTitleField.clear();
        docAuthorField.clear();
        docPublisherField.clear();
        docPriceField.clear();
        descriptionField.clear();
        genreComboBox.getSelectionModel().clearSelection();
        languageComboBox.getSelectionModel().clearSelection();
        numberOfIssueField.clear();
    }

    public void handleSubmit(ActionEvent actionEvent) {
        try {
            String isbn = docISBNField.getText();
            String title = docTitleField.getText();
            String author = docAuthorField.getText();
            String publisher = docPublisherField.getText();
            String description = descriptionField.getText();
            double price = Double.parseDouble(docPriceField.getText());
            String genre = genreComboBox.getSelectionModel().getSelectedItem().getGenreID();
            String language = languageComboBox.getSelectionModel().getSelectedItem().getLgID();
            int numberOfIssues = Integer.parseInt(numberOfIssueField.getText());
            String addDate = java.time.LocalDate.now().toString();

            Document document = new Document(genre, publisher, language, title, author, isbn, numberOfIssues, numberOfIssues, addDate, price, description);
            // todo: xử lí them cac truong hop nhap sai gia tri, them sach da co, hien thi thong bao, ...
            if (DocumentDAO.getInstance().add(document) > 0) {
                System.out.println("Document registered successfully!");
                handleCancel(actionEvent);
            } else {
                System.out.println("Failed to register document.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter valid numerical values for price and number of issues.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}