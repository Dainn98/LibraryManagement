package library.management.ui.controllers.manager;

import java.io.IOException;
import java.util.Objects;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import library.management.data.entity.Document;
import library.management.properties;
import library.management.ui.applications.ImageDownloader;
import library.management.ui.controllers.GeneralController;

/**
 * The DocContainerController class is responsible for managing the presentation and interaction
 * with a document container in a UI. Extends GeneralController and implements properties
 * interface.
 * <p>
 * This controller handles the display of document information such as title, author, description,
 * and thumbnail image. It also manages the transitions and animations associated with user
 * interactions.
 */
public class DocContainerController extends GeneralController implements properties {

  /**
   * Represents an image associated with the document in the DocContainerController. This image is
   * used within the user interface to display visual content related to the current document being
   * handled.
   */
  private Image image;
  /**
   * Represents the document currently being managed or displayed by the DocContainerController.
   * This variable holds a reference to a Document object, which encapsulates the data and
   * properties relevant to the document handling operations such as displaying, modifying, or
   * processing within the application.
   */
  private Document document;
  /**
   * The docInfo variable represents a VBox container in the JavaFX UI. It is used within the
   * DocContainerController to display information related to a document. The controller utilizes
   * this VBox to manage and organize various pieces of visual content and layout regarding the
   * document details.
   */
  @FXML
  private VBox docInfo;
  /**
   * Label component for displaying the author's name or identifier in the document catalog view.
   * This UI element is utilized to present the author information associated with a document within
   * the application's document management interface.
   */
  @FXML
  private Label authorCatalog;
  /**
   * Represents the ImageView component used to display the thumbnail of a document within the
   * DocContainerController. This variable is used to display a visual representation of the
   * document that is being managed or viewed in the application. It may facilitate user
   * interaction, such as handling mouse events to provide additional document details or actions.
   */
  @FXML
  private ImageView docThumbnail;
  /**
   * VBox element used to display the catalog of documents within the user interface. This visual
   * component is part of the DocContainerController class and is responsible for the layout and
   * arrangement of document items presented to the user. It may contain various document-related
   * nodes that facilitate user interaction.
   */
  @FXML
  private VBox docCatalogView;
  /**
   * Represents a hyperlink component that displays the title of a document within the document
   * catalog in the user interface. It is part of the DocContainerController class and is likely
   * used to interact with the document's title, allowing users to view or select documents by
   * clicking on their titles. The hyperlink may be linked with event handlers or actions that
   * respond to user interactions.
   */
  @FXML
  private Hyperlink docTitleCatalog;
  /**
   * Represents the label that displays the description of the document in the user interface. It is
   * part of the graphical user interface managed by the {@code DocContainerController} class.
   */
  @FXML
  private Label desDoc;

  /**
   * Sets the data for the document and updates the UI elements with the document's details. If the
   * document's image source is the demo image source, it loads the image from resources; otherwise,
   * it downloads the image. UI components such as document description, title, author, and
   * thumbnail are updated accordingly.
   *
   * @param doc the Document object containing the data to be set and displayed
   */
  public void setData(Document doc) {
    this.document = doc;
    String imageUrl = document.getImage();
    if (Objects.equals(imageUrl, DEMO_IMAGE_SOURCE)) {
      image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(imageUrl)));
    } else {
      image = ImageDownloader.downloadImage(document.getImage());
    }
    Platform.runLater(() -> {
      desDoc.setText(document.getDescription());
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

  /**
   * Handles the mouse enter event on the document thumbnail, triggering a 3D rotation and fade
   * transition to visually indicate interaction, and subsequently starts an automatic rotation back
   * to the initial view after a delay.
   *
   * @param mouseEvent the MouseEvent that occurs when the mouse enters the document thumbnail area
   */
  @FXML
  private void handleEnterDocThumbnail(MouseEvent mouseEvent) {
    rotate3D(docThumbnail, 0, 1, docInfo, 270, 1, 90, Duration.millis(1000));
    startAutoRotateBack();
  }

  /**
   * Handles the mouse exit event on the descriptive document node. This method triggers a 3D
   * rotation effect on the descriptive document's information node and its thumbnail using
   * specified angles and duration.
   *
   * @param mouseEvent the MouseEvent object containing details about the mouse exit event that
   *                   triggered this handler.
   */
  @FXML
  private void handleExitDesDoc(MouseEvent mouseEvent) {
    rotate3D(docInfo, 0, 1, docThumbnail, 270, 1, 90, Duration.millis(1000));
  }

  /**
   * Initiates an automatic 3D rotation transition for document view elements when certain
   * conditions are met.
   *
   * <p>The method creates a pause of one second before executing a rotation and fade transition on
   * the specified document view elements using the {@code rotate3D} method. The rotation only
   * occurs if the {@code docCatalogView} is not currently being hovered over by the user. This
   * creates a visual effect where the document view elements rotate back to an original state
   * automatically under the described conditions.</p>
   *
   * <p>It involves a visual rotation of the {@code docInfo} and {@code docThumbnail} nodes to
   * specified angles over a duration of 1000 milliseconds, facilitating a smooth transition back to
   * a default or original display orientation.</p>
   */
  private void startAutoRotateBack() {
    PauseTransition pause = new PauseTransition(Duration.seconds(1));
    pause.setOnFinished(_ -> {
      if (!docCatalogView.isHover()) {
        rotate3D(docInfo, 0, 1, docThumbnail, 270, 1, 90, Duration.millis(1000));
      }
    });
    pause.play();
  }

  /**
   * Handles the event when the document information is requested by pressing with a mouse action.
   * It initializes and displays a new window containing the document information.
   *
   * @param mouseEvent the mouse event that triggered this handler
   */
  @FXML
  private void handlePressDocInfo(MouseEvent mouseEvent) {
    if (this.document == null) {
      return;
    }
    try {
      FXMLLoader fxmlLoader = new FXMLLoader();
      fxmlLoader.setLocation(getClass().getResource(DOC_INFORMATION_SOURCE));
      Parent root = fxmlLoader.load();
      Stage stage = new Stage();
      stage.setTitle(DOC_INFORMATION_TITLE);

      Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream(ICON_SOURCE)));
      stage.getIcons().add(icon);

      ManagerDocInformationController controller = fxmlLoader.getController();
      controller.loadDocData(document, image);

      stage.setResizable(false);
      stage.setScene(new Scene(root));
      stage.setOnCloseRequest((WindowEvent _) -> stage.close());
      stage.show();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Retrieves the document associated with this controller.
   *
   * @return the document object held by this controller
   */
  public Document getDocument() {
    return this.document;
  }

  /**
   * Retrieves the current image associated with the document.
   *
   * @return the image object representing the visual content associated with the document.
   */
  public Image getImage() {
    return this.image;
  }

}
