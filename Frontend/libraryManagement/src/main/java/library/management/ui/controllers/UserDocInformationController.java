package library.management.ui.controllers;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import library.management.data.DAO.LoanDAO;
import library.management.data.entity.Document;
import library.management.data.entity.Loan;
import library.management.data.entity.User;
import library.management.ui.applications.CodeGenerator;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import static library.management.alert.AlertMaker.showAlertConfirmation;
import static library.management.alert.AlertMaker.showAlertInformation;

public class UserDocInformationController implements GeneralController {
    private final static double DX = 800;
    private final static Duration DURATION = Duration.millis(1000);
    private final static int QR_HEIGHT = 150;
    private final static int QR_WIDTH = 150;
    private final static int BARCODE_HEIGHT = 100;
    private final static int BARCODE_WIDTH = 250;

    boolean check = true;
    private Image QRImage;
    private Image barcodeImage;
    private Image image;
    private Document document;
    private Loan loan;
    private int type;
    private User user;

    @FXML
    private Label titleInfo;
    @FXML
    private Label authorInfo;
    @FXML
    private Label publisherInfo;
    @FXML
    private Label categoryInfo;
    @FXML
    private Label languageInfo;
    @FXML
    private Label isbnInfo;
    @FXML
    private Label descriptionInfo;
    @FXML
    private HBox priceHBox;

    @FXML
    private HBox numberHBox;
    @FXML
    private TextField numberField;
    @FXML
    private ImageView thumbnailImageInfo;
    @FXML
    private ImageView qrImageInfo;
    @FXML
    private ImageView isbnImageInfo;
    @FXML
    private Label titleHeading;
    @FXML
    private HBox saveHBox;
    @FXML
    private JFXButton addDocButton;

    @FXML
    private Label borrowingDateTitle;

    @FXML
    private Label borrowingDateLabel;

    @FXML
    private Label dueDateTitle;

    @FXML
    private Label dueDateLabel;

    @FXML
    private Label quantityTitle;

    @FXML
    private Label quantityLabel;

    @FXML
    private Label lastFeeTitle;

    @FXML
    private Label lateFeeLabel;

    @FXML
    private Label priceTextField;

    @FXML
    private JFXButton mainButton;

    public void loadDocData(Document doc, Image thumbnail, Loan loan, int type) {
        this.type = type;
        this.document = doc;
        this.image = thumbnail;
        titleInfo.setText(doc.getTitle());
        authorInfo.setText(doc.getAuthor());
        publisherInfo.setText(doc.getPublisher());
        categoryInfo.setText(doc.getCategory());
        languageInfo.setText(doc.getLanguage());
        isbnInfo.setText(doc.getIsbn());
        descriptionInfo.setText(doc.getDescription());
        thumbnailImageInfo.setImage(image);
        titleHeading.textProperty().set(doc.getTitle());
        setCodeImage();
        qrImageInfo.setImage(QRImage);
        isbnImageInfo.imageProperty().set(barcodeImage);
        if (this.type == UserDocContainerController.HOME_DOCUMENT) {
            addDocButton.setText("Confirm");
            addDocButton.setOnAction(event -> handleBorrow(event, doc));
            borrowingDateTitle.setVisible(false);
            borrowingDateLabel.setVisible(false);
            dueDateTitle.setVisible(false);
            dueDateLabel.setVisible(false);
            quantityLabel.setText(document.getAvailableCopies() + " copies");
            lastFeeTitle.setVisible(false);
            lateFeeLabel.setVisible(false);
            priceTextField.setText(doc.getPrice() + "$");
        } else if (this.type == UserDocContainerController.BORROWING_DOCUMENT) {
            this.loan = loan;
            borrowingDateLabel.setText(this.loan.getDateOfBorrowAsString());
            dueDateLabel.setText(this.loan.getRequiredReturnDateAsString());
            quantityLabel.setText(String.valueOf(this.loan.getQuantityOfBorrow()));
            lateFeeLabel.setText(Loan.LATEFEE + "$/day");
            mainButton.setText("Return");
            mainButton.setOnAction(this::handleReturn);
        } else if (this.type == UserDocContainerController.PROCESSING_DOCUMENT) {
            this.loan = loan;
            mainButton.setText("Undo");
            mainButton.setOnAction(this::handleUndo);
            if (loan.getStatus().equals("pending")) {
                borrowingDateLabel.setText(this.loan.getDateOfBorrowAsString());
                quantityLabel.setText(String.valueOf(this.loan.getQuantityOfBorrow()));
                dueDateTitle.setText("Status");
                dueDateLabel.setText(this.loan.getStatus());
                lateFeeLabel.setText(Loan.LATEFEE + "$/day");
            } else if (loan.getStatus().equals("pendingReturned")) {
                borrowingDateLabel.setText(this.loan.getDateOfBorrowAsString());
                quantityLabel.setText(String.valueOf(this.loan.getQuantityOfBorrow()));
                dueDateTitle.setText("Status");
                dueDateLabel.setText("Pending Returned");
                lateFeeLabel.setText(String.valueOf(loan.getDeposit()));
            }
        }
    }

    private void setCodeImage() {
        try {
            QRImage = CodeGenerator.generateQRCode(document.getUrl(), QR_WIDTH, QR_HEIGHT);
            if (!document.getIsbn().equals("No ISBN available")) {
                barcodeImage = CodeGenerator.generateBarcodeWithText(document.getIsbn(), BARCODE_WIDTH, BARCODE_HEIGHT);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAddDoc(ActionEvent actionEvent) {
        if (check) {
            transFade(priceHBox, DX, 0.5, 1, DURATION);
            transFade(saveHBox, DX, 0.5, 1, DURATION);
            transFade(numberHBox, -DX, 0.5, 1, DURATION);
            check = false;
        } else {
            transFade(numberHBox, DX, 1, 0, DURATION);
            transFade(priceHBox, -DX, 1, 0, DURATION);
            transFade(saveHBox, -DX, 1, 0, DURATION);
            check = true;
        }
    }

    private void handleBorrow(ActionEvent actionEvent, Document doc) {
        if (doc == null) {
            System.out.println("null");
            showAlertInformation("Error", "This book is not available to be borrowed.");
            return;
        }
        if (numberField.textProperty().getValue().isEmpty() || !isStringAnInteger(numberField.textProperty().getValue())) {
            showAlertInformation("Invalid Quantity", "Please enter a valid quantity!");
            return;
        }
        if (document.getAvailability().equals("Not available")) {
            showAlertInformation("Invalid Document", "This document is not available!");
            return;
        }
        int number = Integer.parseInt(numberField.textProperty().getValue());
        if (number <= 0 || number > document.getAvailableCopies()) {
            showAlertInformation("Invalid Quantity", "Please enter a valid quantity!");
            return;
        }
        Optional<ButtonType> result = showAlertConfirmation("Borrowing document", "Are you sure you want to borrow this document?");
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Loan loan = new Loan(FullUserController.mainUser.getUserName(), document.getIntDocumentID(), number, 0);
            loan.setStatus("pending");
            LoanDAO.getInstance().add(loan);
            showAlertInformation("Borrowing successfully", "Please wait for manager's approval!");
        }
    }

    private void handleReturn(ActionEvent actionEvent) {
        if (loan == null) {
            showAlertInformation("Error", "This book is not available to be returned.");
            return;
        }
        if (loan.getStatus().equals("late")) {
            double lateFee = Loan.LATEFEE * ChronoUnit.DAYS.between(loan.getRequiredReturnDate(), LocalDateTime.now());
            Optional<ButtonType> result = showAlertConfirmation("Return document", "This document is overdue. You have to pay a late fee of " +
                                                                lateFee + " $.\n" +
                                                                "Are you sure you want to return this document?");
            if (result.isPresent() && result.get() == ButtonType.OK) {
                loan.setDeposit(Integer.parseInt(String.valueOf(lateFee)));
                if (LoanDAO.getInstance().userReturnDocument(loan)) {
                    showAlertInformation("Return document", "Your document is pending return!");
                    Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    currentStage.close();
                } else {
                    showAlertInformation("Return document fail!", "Document could not be returned!");
                }
            }
        } else {
            Optional<ButtonType> result = showAlertConfirmation("Return document", "Are you sure you want to return this document?");
            if (result.isPresent() && result.get() == ButtonType.OK) {
                if (LoanDAO.getInstance().userReturnDocument(loan)) {
                    showAlertInformation("Return document", "Your document is pending return!");
                    Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    currentStage.close();
                } else {
                    showAlertInformation("Return document fail!", "Document could not be returned!");
                }
            }
        }
    }

    private void handleUndo(ActionEvent actionEvent) {
        Optional<ButtonType> result = showAlertConfirmation("Return document", "Are you sure you want to return this document?");
        if (result.isPresent() && result.get() == ButtonType.OK) {
            if (loan == null) {
                showAlertInformation("Error", "This document is not available to be undored.");
                return;
            }
            if (loan.getStatus().equals("pending")) {
                if (LoanDAO.getInstance().undoPending(loan) > 0) {
                    showAlertInformation("Undo successfully", "Your loan is deleted.");
                    Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    currentStage.close();
                } else {
                    showAlertInformation("Undo fail!", "Your loan is not deleted.");
                }
                return;
            }
            if (loan.getStatus().equals("pendingReturned")) {
                if (LoanDAO.getInstance().undoPendingReturn(loan) > 0) {
                    showAlertInformation("Undo successfully", "Your loan is continue.");
                    Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    currentStage.close();
                } else {
                    showAlertInformation("Undo fail!", "Something went wrong!.");
                }
            }
        }
    }

    public boolean isStringAnInteger(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
