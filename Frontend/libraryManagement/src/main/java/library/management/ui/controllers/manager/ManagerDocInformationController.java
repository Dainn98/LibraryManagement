package library.management.ui.controllers.manager;

import static library.management.alert.AlertMaker.showAlertConfirmation;
import static library.management.alert.AlertMaker.showAlertInformation;

import com.jfoenix.controls.JFXButton;
import java.time.LocalDateTime;
import java.util.Optional;
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

/**
 * ManagerDocInformationController manages the UI interactions related to displaying and saving
 * document information within a management system.
 * <p>
 * This controller class extends GeneralController and implements the properties interface to
 * provide a set of functionalities needed for document management, including displaying document
 * information, handling add or save document actions, and generating QR codes and barcodes.
 */
public class ManagerDocInformationController extends GeneralController implements properties {

  /**
   * Indicates whether a specific condition or state is enabled or true. This variable may be used
   * to control the flow of logic, enabling or disabling certain features or processes within the
   * application.
   */
  boolean check = true;
  /**
   * Represents the image of a QR code used within the ManagerDocInformationController class. This
   * image is typically generated based on document metadata or other relevant information stored in
   * the system. The QRImage is designed for use in displaying or processing QR codes associated
   * with documentary data, facilitating quick scanning and retrieval of information.
   */
  private Image QRImage;
  /**
   * Represents an image containing the barcode representation of a document within the
   * ManagerDocInformationController. This image is used to visually identify or store the encoded
   * barcode information related to the document details being managed or processed. The barcode
   * image can be utilized for various purposes, such as scanning, displaying, or archiving within
   * the application's document management functionality.
   */
  private Image barcodeImage;
  /**
   * Represents the document object that contains various pieces of information regarding a specific
   * document managed by the ManagerDocInformationController. It is used internally to handle
   * operations related to document processing such as loading document data or handling save
   * operations.
   */
  private Document document;

  /**
   * The Label `titleInfo` is used to display the title information of a document within the user
   * interface of the ManagerDocInformationController class. It is associated with the JavaFX
   * framework through the `@FXML` annotation, which allows it to be manipulated in the
   * corresponding FXML layout.
   */
  @FXML
  private Label titleInfo;
  /**
   * Represents a Label UI component used to display information about the author of the document
   * currently being managed. This Label is part of the document information interface managed by
   * the ManagerDocInformationController class, providing a dedicated space for showing the author's
   * name or related details.
   */
  @FXML
  private Label authorInfo;
  /**
   * The Label component responsible for displaying publisher information within the user interface.
   * It is part of the document metadata display and may contain the publisher's name or other
   * related details associated with the document being viewed or edited.
   */
  @FXML
  private Label publisherInfo;
  /**
   * Represents a label UI component intended for displaying category information about a document
   * in the user interface within the context of a document management controller. This label is
   * part of the JavaFX framework and is annotated with
   *
   * @FXML to allow for interaction and manipulation during the runtime of the application,
   * typically within an FXML layout.
   */
  @FXML
  private Label categoryInfo;
  /**
   * The Label element used to display information about the language of the document. This field is
   * intended to be populated with the language details for a document within the user interface
   * managed by the ManagerDocInformationController.
   */
  @FXML
  private Label languageInfo;
  /**
   * Label component in the user interface responsible for displaying information related to the
   * ISBN (International Standard Book Number) of a document. This label is utilized within the
   * ManagerDocInformationController class to show details pertinent to the ISBN of a document being
   * viewed or managed, allowing for user interaction and visualization of essential bibliographic
   * data.
   */
  @FXML
  private Label isbnInfo;
  /**
   * The descriptionInfo variable represents a JavaFX Label that is used within the
   * ManagerDocInformationController to display information or details related to a specific
   * document's description. This label is part of the user interface and is updated to provide
   * users with relevant data regarding the document being managed or viewed.
   */
  @FXML
  private Label descriptionInfo;
  /**
   * The priceHBox is a layout container in the form of an HBox, used to horizontally arrange its
   * child nodes. It is part of the user interface in the ManagerDocInformationController, where it
   * may be utilized to group components related to pricing information visually in the UI. Being an
   * FXML annotated private field, it's linked with the corresponding FXML file to facilitate its
   * initialization and configuration during the UI loading process.
   */
  @FXML
  private HBox priceHBox;

  /**
   * Represents an HBox layout container for holding numeric data inputs or displays within the user
   * interface of the ManagerDocInformationController. Provides a horizontal alignment for its child
   * nodes, which may include text fields, labels, or other graphic elements associated with numeric
   * information of a document. The HBox is typically used for arranging multiple elements, ensuring
   * they are laid out within a single row.
   */
  @FXML
  private HBox numberHBox;
  /**
   * The priceField represents a text input field within a graphical user interface for entering and
   * displaying the price of a document. This field is part of the ManagerDocInformationController
   * and is linked to the user interface using JavaFX's FXML. Users interact with this field to
   * input or view the document's pricing information, which can further be processed or stored by
   * the application.
   */
  @FXML
  private TextField priceField;
  /**
   * A TextField component in the UI responsible for capturing and displaying numerical data related
   * to document management operations. This field is expected to be used within the context of
   * managing document information, possibly handling quantities, numbers, or other numeric entries
   * required by the ManagerDocInformationController.
   */
  @FXML
  private TextField numberField;
  /**
   * Represents a thumbnail image view within the user interface for managing document information.
   * This ImageView is utilized to display the thumbnail of a document, providing a visual
   * representation of the document's cover or main image. The thumbnail image enhances the user
   * experience by giving users a quick visual reference for the document being managed or viewed.
   * <p>
   * This field is marked with the `@FXML` annotation, indicating its linkage to the corresponding
   * FXML file where the UI components are defined. It is part of the
   * `ManagerDocInformationController` class, which handles the management of document details in
   * the library management system.
   */
  @FXML
  private ImageView thumbnailImageInfo;
  /**
   * Represents the ImageView component in the user interface that is used to display a QR code
   * image. This component is part of the ManagerDocInformationController and is initialized and
   * managed through JavaFX's FXML annotations, facilitating the dynamic updating and rendering of
   * QR code images related to the manager's document information.
   */
  @FXML
  private ImageView qrImageInfo;
  /**
   * The ImageView element in the user interface that displays the ISBN-related QR code or barcode
   * image for a document. This visual component provides users with a graphical representation to
   * identify the document via its ISBN during document management tasks.
   */
  @FXML
  private ImageView isbnImageInfo;
  /**
   * The titleHeading variable is a private member of the ManagerDocInformationController class. It
   * represents a Label object, which is typically used to display a short text or message in a
   * JavaFX application. This Label is marked with the @FXML annotation, indicating that it is
   * associated with an element defined in an FXML file, where the visual arrangement of this
   * component within the application's user interface is specified.
   * <p>
   * As a user interface element, titleHeading is likely utilized to display the title of a document
   * or any relevant heading information within the context managed by the
   * ManagerDocInformationController. It serves as a visual indicator or descriptor for the section
   * of the UI that this Label pertains to, enhancing the user's understanding and navigation of the
   * application's document management features.
   */
  @FXML
  private Label titleHeading;
  /**
   * Represents an HBox UI component related to saving functionality within the
   * ManagerDocInformationController class. The saveHBox is used in conjunction with the document
   * management interface to visually structure and align widgets or controls that pertain to the
   * saving of document-related data.
   */
  @FXML
  private HBox saveHBox;
  /**
   * The addDocButton is a JavaFX button component defined in the ManagerDocInformationController
   * class. It is used to trigger the addition of a new document in the application.
   * <p>
   * It is associated with the @FXML annotation, indicating its usage within a JavaFX application
   * where it is likely tied to a UI component in an FXML file. The button's specific action handler
   * can be linked in the controller class to define what happens when the button is clicked by a
   * user.
   */
  @FXML
  private JFXButton addDocButton;


  /**
   * Loads the document data into the user interface components and sets up event handling for the
   * document.
   *
   * @param doc       the Document object containing the details to be displayed
   * @param thumbnail the Image object representing the thumbnail of the document
   */
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

  /**
   * Sets the code images for the document by generating a QR code and, if applicable, a barcode
   * image.
   * <p>
   * This method generates a QR code image using the document's URL and assigns it to the QRImage.
   * If the document's ISBN is available, it generates a barcode image from the ISBN and assigns it
   * to the barcodeImage. In case of exceptions during image generation, the stack trace is printed
   * for debugging purposes.
   */
  private void setCodeImage() {
    try {
      QRImage = CodeGenerator.generateQRCode(document.getUrl(), QR_WIDTH, QR_HEIGHT);
      if (!document.getIsbn().equals("No ISBN available")) {
        barcodeImage = CodeGenerator.generateBarcodeWithText(document.getIsbn(), BARCODE_WIDTH,
            BARCODE_HEIGHT);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Handles showing and hiding components for adding a document in the UI with animation
   * transitions. Depending on the current state indicated by the 'check' flag, it will either
   * display or hide certain UI elements with a fade and translation animation effect.
   *
   * @param actionEvent the event triggered by the UI interaction, typically an action on a button,
   *                    that invokes this method.
   */
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

  /**
   * Handles the save operation for a document. Validates the input fields for quantity and price,
   * confirming the operation with the user and interacting with the document database accordingly.
   *
   * @param actionEvent The action event that triggered the save operation.
   * @param doc         The document to be saved or updated. If null, the function will exit early.
   */
  private void handleSave(ActionEvent actionEvent, Document doc) {
    if (doc == null) {
      System.out.println("null");
      return;
    }
    if (numberField.textProperty().getValue().isEmpty() || !isStringAnInteger(
        numberField.textProperty().getValue())) {
      showAlertInformation("Invalid Quantity", "Please enter a valid quantity!");
      return;
    }
    int number = Integer.parseInt(numberField.textProperty().getValue());
    if (number <= 0) {
      showAlertInformation("Invalid Quantity", "Please enter a valid number!");
      return;
    }
    if (priceField.textProperty().getValue().isEmpty() || !isStringAnDouble(
        priceField.textProperty().getValue())) {
      showAlertInformation("Invalid Price", "Please enter a valid price!");
      return;
    }
    double price = Double.parseDouble(priceField.textProperty().getValue());
    if (price <= 0) {
      showAlertInformation("Invalid Price", "Please enter a valid price!");
      return;
    }
    Optional<ButtonType> result = showAlertConfirmation("Add document",
        "Are you sure you want to add this document?");
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
        showAlertConfirmation("Document exists",
            "Document already exists!\n" + "Are you sure you want to update this document:\n"
                + "-Update new price:" + price + ".\n" + "-Add :" + number + " new copies.\n"
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

  /**
   * Determines whether a given string can be parsed as an integer.
   *
   * @param str the string to evaluate as an integer
   * @return true if the string is non-null, non-empty, and can be parsed as an integer; false
   * otherwise
   */
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

  /**
   * Determines if the given string can be parsed into a double.
   *
   * @param str the string to be checked
   * @return true if the string can be parsed as a double, false otherwise
   */
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
