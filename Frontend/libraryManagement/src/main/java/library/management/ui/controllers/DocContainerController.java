package library.management.ui.controllers;


import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import library.management.data.DAO.DocumentDAO;
import library.management.data.entity.Document;
import library.management.ui.applications.ImageDownloader;
import library.management.ui.applications.CodeGenerator;


import static library.management.alert.AlertMaker.showAlertConfirmation;
import static library.management.alert.AlertMaker.showAlertInformation;

public class DocContainerController implements GeneralController {

  private final static double DX = 800;
  private final static Duration DURATION = Duration.millis(1000);
  private final static int QR_HEIGHT = 150;
  private final static int QR_WIDTH = 150;
  private final static int BARCODE_HEIGHT = 100;
  private final static int BARCODE_WIDTH = 250;

  private final String[] colors = {"D1E8FF", // Light Blue
      "FFF7D1", // Light Yellow
      "FFE4E6", // Light Pink
      "E8F0FE", // Lighter Blue
      "F0F8E8", // Light Green
      "FEE8F0", // Light Blush
      "FFF0E8", // Light Orange
      "E8FFE8", // Light Greenish
      "E8F7FF", // Ice Blue
      "F5E8FF", // Light Purple
      "FFFBE8", // Cream Yellow
      "E8FFF5", // Light Jade
      "F8E8FF", // Pastel Purple
      "E0BBE4", // Light Purple
      "957DAD", // Medium Purple
      "D291BC", // Light Pink
      "FEC8D8", // Light Coral
      "FFDFD3", // Light Peach
      "C8E6C9", // Light Green
      "B3E5FC", // Light Blue
      "FFCCBC", // Light Orange
      "D1C4E9", // Light Lavender
      "F8BBD0", // Light Pink
      "DCEDC8", // Light Lime
      "FFECB3", // Light Yellow
      "CFD8DC"  // Light Gray
  };
  boolean check = true;
  private Image image;
  private Document document;
  private Image QRImage;
  private Image barcodeImage;
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
  private VBox docInfo;
  @FXML
  private Label authorCatalog;
  @FXML
  private ImageView docThumbnail;
  @FXML
  private VBox docCatalogView;
  @FXML
  private Hyperlink docTitleCatalog;
  @FXML
  protected JFXButton addDocButton;

    public void setData(Document doc) {
      this.document = doc;
      String imageUrl = document.getImage();
      if (Objects.equals(imageUrl, "/ui/sprites/demoDoc.gif")) {
        image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(imageUrl)));
      } else {
        image = ImageDownloader.downloadImage(document.getImage());
      }
      try {
        QRImage = CodeGenerator.generateQRCode(document.getUrl(), QR_WIDTH, QR_HEIGHT);
        if (!document.getIsbn().equals("No ISBN available")) {
          barcodeImage = CodeGenerator.generateBarcodeWithText(document.getIsbn(), BARCODE_WIDTH, BARCODE_HEIGHT);
        }
      } catch (Exception e) {
        e.printStackTrace();
      }

        Platform.runLater(()-> {
            docThumbnail.setImage(image);
            docTitleCatalog.setText(document.getTitle());
            authorCatalog.setText(document.getAuthor());

            docTitleCatalog.setStyle("-fx-text-fill: #002B5B; -fx-font-size: 14px;");  // Navy Blue
            authorCatalog.setStyle("-fx-text-fill: #333333; -fx-padding: 5;");    // Dark Gray
            docCatalogView.setStyle(
                    "-fx-background-color:#" + colors[(int) (Math.random() * colors.length)] + ";"
                            + "-fx-background-radius: 15;"
                            + "-fx-effect: dropShadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 10);"
            );
        });
    }

  @FXML
  private void handleEnterDocThumbnail(MouseEvent mouseEvent) {
    rotate3D(docThumbnail, 0, 1, docInfo, 270, 1, 90, Duration.millis(1000));
    startAutoRotateBack();
  }

  @FXML
  private void handleExitDesDoc(MouseEvent mouseEvent) {
    rotate3D(docInfo, 0, 1, docThumbnail, 270, 1, 90, Duration.millis(1000));
  }

  private void startAutoRotateBack() {
    PauseTransition pause = new PauseTransition(Duration.seconds(5));
    pause.setOnFinished(event -> {
      if (!docCatalogView.isHover()) {
        rotate3D(docInfo, 0, 1, docThumbnail, 270, 1, 90, Duration.millis(1000));
      }
    });
    pause.play();
  }

  @FXML
  private void handlePressDocInfo(MouseEvent mouseEvent) throws IOException {
    try {
      FXMLLoader fxmlLoader = new FXMLLoader();
      fxmlLoader.setLocation(getClass().getResource("/ui/fxml/docInformation.fxml"));
      Parent root = fxmlLoader.load();
      Stage stage = new Stage();
      stage.setTitle("Document Information");
      DocContainerController controller = fxmlLoader.getController();
      controller.loadDocData(document, image, QRImage, barcodeImage);

      stage.setResizable(false);
      stage.setScene(new Scene(root));
      stage.setOnCloseRequest((WindowEvent event) -> {
        stage.close();
      });
      stage.show();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  private void setData() {
    this.titleInfo.setText("The Great Gatsby");
    this.authorInfo.setText("F. Scott Fitzgerald");
    this.publisherInfo.setText("Charles Scribner's Sons");
    this.categoryInfo.setText("Novel");
    this.languageInfo.setText("English");
    this.isbnInfo.setText("9780743273565");
    this.descriptionInfo.setText(
        "The Great Gatsby is a 1925 novel by American writer F. Scott Fitzgerald. Set in the Jazz Age on Long Island, near New York City, the novel depicts first-person narrator Nick Carraway's interactions with mysterious millionaire Jay Gatsby and Gatsby's obsession to reunite with his former lover, Daisy Buchanan.");
  }

  private void loadDocData(Document doc, Image thumbnail, Image QRImage, Image isbnImage) {
    titleInfo.setText(doc.getTitle());
    authorInfo.setText(doc.getAuthor());
    publisherInfo.setText(doc.getPublisher());
    categoryInfo.setText(doc.getCategory());
    languageInfo.setText(doc.getLanguage());
    isbnInfo.setText(doc.getIsbn());
    descriptionInfo.setText(doc.getDescription());
    thumbnailImageInfo.setImage(thumbnail);
    qrImageInfo.setImage(QRImage);
    titleHeading.textProperty().set(doc.getTitle());
    isbnImageInfo.imageProperty().set(isbnImage);
    addDocButton.setOnAction(event -> handleSave(doc));
  }

  public Document getDocument() {
      return this.document;
  }

  public Image getImage() {
      return this.image;
  }

  @FXML
  private void handleAddDoc(ActionEvent actionEvent) {
    if (check) {
      transFade(priceHBox, DX, 0.5, 1, DURATION);
      transFade(numberHBox, DX, 0.5, 1, DURATION);
      transFade(saveHBox, DX, 0.5, 1, DURATION);
      transFade(numberHBox, -DX, 0.5, 1, DURATION);
      check = false;
    } else {
      transFade(numberHBox, DX, 1, 0, DURATION);
      transFade(priceHBox, -DX, 1, 0, DURATION);
      transFade(numberHBox, -DX, 1, 0, DURATION);
      transFade(saveHBox, -DX, 1, 0, DURATION);
      check = true;
    }
  }

  private void handleSave(Document doc) {
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
      Document existingDoc = DocumentDAO.getInstance().getDocumentById(doc.getIntDocumentID());
      if (existingDoc == null) {
        DocumentDAO.getInstance().add(newDoc);
      } else {
        showAlertConfirmation("Document exists", "Document already exists!\n" +
                "Are you sure you want to update this document:\n"
                + "-Update new price:" + price + ".\n"
                + "-Add new copies:" + newDoc + ".\n"
                + "-Set availability to available.");
        if (result.isPresent() && result.get() == ButtonType.OK) {
          newDoc.setQuantity(existingDoc.getQuantity() + number);
          newDoc.setAvailableCopies(existingDoc.getAvailableCopies() + number);
          newDoc.setPrice(price);
          newDoc.setAvailability("available");
          DocumentDAO.getInstance().update(newDoc);
        }
      }
      showAlertInformation("Document Added", "Document added successfully!");
      priceField.setText("");
      numberField.setText("");
      handleAddDoc(null);
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
