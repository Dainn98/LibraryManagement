package library.management.ui.controllers.manager;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import library.management.data.DAO.DocumentDAO;
import library.management.data.entity.Document;
import library.management.properties;
import library.management.ui.applications.CodeGenerator;
import library.management.ui.controllers.GeneralController;

import java.time.LocalDateTime;
import java.util.Optional;

import static library.management.alert.AlertMaker.showAlertConfirmation;
import static library.management.alert.AlertMaker.showAlertInformation;

public class ManagerDocInformationController extends GeneralController implements properties {
    boolean check = true;
    private Image QRImage;
    private Image barcodeImage;
    private Document document;

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
    private TextField priceField;
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



    public void loadDocData(Document doc, Image thumbnail) {
        this.document = doc;
        titleInfo.setText(doc.getTitle());
        authorInfo.setText(doc.getAuthor());
        publisherInfo.setText(doc.getPublisher());
        categoryInfo.setText(doc.getCategory());
        languageInfo.setText(doc.getLanguage());
        isbnInfo.setText(doc.getIsbn());
        descriptionInfo.setText(doc.getDescription());
        thumbnailImageInfo.setImage(thumbnail);
        titleHeading.textProperty().set(doc.getTitle());
        setCodeImage();
        qrImageInfo.setImage(QRImage);
        isbnImageInfo.imageProperty().set(barcodeImage);
        addDocButton.setOnAction(event -> handleSave(event, doc));
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

    private void handleSave(ActionEvent actionEvent, Document doc) {
        if (doc == null) {
            System.out.println("null");
            return;
        }
        if (numberField.textProperty().getValue().isEmpty() || !isStringAnInteger(numberField.textProperty().getValue())) {
            showAlertInformation("Invalid Quantity", "Please enter a valid quantity!");
            return;
        }
        int number = Integer.parseInt(numberField.textProperty().getValue());
        if (number <= 0) {
            showAlertInformation("Invalid Quantity", "Please enter a valid number!");
            return;
        }
        if (priceField.textProperty().getValue().isEmpty() || !isStringAnDouble(priceField.textProperty().getValue())) {
            showAlertInformation("Invalid Price", "Please enter a valid price!");
            return;
        }
        double price = Double.parseDouble(priceField.textProperty().getValue());
        if (price <= 0) {
            showAlertInformation("Invalid Price", "Please enter a valid price!");
            return;
        }
        Optional<ButtonType> result = showAlertConfirmation("Add document", "Are you sure you want to add this document?");
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Document newDoc = new Document(doc);
            newDoc.setQuantity(number);
            newDoc.setAvailableCopies(number);
            newDoc.setPrice(price);
            newDoc.setAddDate(LocalDateTime.now());
            newDoc.setAvailability("available");
            if (newDoc.getIsbn().equals("No ISBN available")) {
                showAlertInformation("Illegal document!", "The document must have ISBN to add!");
                return;
            }
            Document existingDoc = DocumentDAO.getInstance().getDocumentByIsbn(doc.getIsbn());
            if (existingDoc == null) {
                DocumentDAO.getInstance().add(newDoc);
            } else {
                showAlertConfirmation("Document exists", "Document already exists!\n" +
                        "Are you sure you want to update this document:\n"
                        + "-Update new price:" + price + ".\n"
                        + "-Add :" + number + " new copies.\n"
                        + "-Set availability to available.");
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    newDoc.setQuantity(existingDoc.getQuantity() + number);
                    newDoc.setAvailableCopies(existingDoc.getAvailableCopies() + number);
                    newDoc.setPrice(price);
                    newDoc.setAvailability("available");
                    DocumentDAO.getInstance().update(newDoc);
                } else {
                    return;
                }
            }
            showAlertInformation("Document Added", "Document added successfully!");
            priceField.setText("");
            numberField.setText("");
            handleAddDoc(actionEvent);
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

    public boolean isStringAnDouble(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
