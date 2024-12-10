package library.management.ui.controllers.user;

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
import library.management.data.entity.Loan;
import library.management.properties;
import library.management.ui.applications.ImageDownloader;
import library.management.ui.controllers.GeneralController;

import java.io.IOException;
import java.util.Objects;

/**
 * The {@code UserDocContainerController} class handles the display and interaction with document containers in the user interface.
 * It shows document information such as title, author, and description, and allows the user to interact with the document thumbnail.
 */
public class UserDocContainerController extends GeneralController implements properties {
    private Image image;
    private Document document;
    private Loan loan;
    private int type;

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
    private Label desDoc;

    /**
     * Sets the data for the document container.
     * This includes the document's thumbnail, title, author, description, and the loan (if applicable).
     *
     * @param doc  the document to display
     * @param loan the loan associated with the document (if applicable)
     * @param type the type of the document (e.g., borrowing or processing)
     */
    public void setDocData(Document doc, Loan loan, int type) {
        this.type = type;
        if (type == BORROWING_DOCUMENT || type == PROCESSING_DOCUMENT) {
            this.loan = loan;
        }
        this.document = doc;
        String imageUrl = document.getImage();
        if (Objects.equals(imageUrl, "/ui/sprites/demoDoc.gif")) {
            image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(imageUrl)));
        } else {
            image = ImageDownloader.downloadImage(document.getImage());
        }

        Platform.runLater(() -> {
            docThumbnail.setImage(image);
            docTitleCatalog.setText(document.getTitle());
            authorCatalog.setText(document.getAuthor());
            desDoc.setText(document.getDescription());

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
     * Handles mouse enter event on the document thumbnail.
     * Rotates the document thumbnail and starts an auto-rotate back after a pause.
     *
     * @param mouseEvent the mouse event triggered by entering the thumbnail
     */
    @FXML
    private void handleEnterDocThumbnail(MouseEvent mouseEvent) {
        rotate3D(docThumbnail, 0, 1, docInfo, 270, 1, 90, Duration.millis(1000));
        startAutoRotateBack();
    }

    /**
     * Handles mouse exit event on the document description.
     * Rotates the document information back to its original state.
     *
     * @param mouseEvent the mouse event triggered by exiting the description
     */
    @FXML
    private void handleExitDesDoc(MouseEvent mouseEvent) {
        rotate3D(docInfo, 0, 1, docThumbnail, 270, 1, 90, Duration.millis(1000));
    }

    /**
     * Starts a pause transition to auto-rotate the document back if the user stops hovering.
     */
    private void startAutoRotateBack() {
        PauseTransition pause = new PauseTransition(Duration.seconds(5));
        pause.setOnFinished(event -> {
            if (!docCatalogView.isHover()) {
                rotate3D(docInfo, 0, 1, docThumbnail, 270, 1, 90, Duration.millis(1000));
            }
        });
        pause.play();
    }

    /**
     * Handles the press event on the document container.
     * Opens a new window displaying detailed information about the document.
     *
     * @param mouseEvent the mouse event triggered by clicking on the document container
     * @throws IOException if an error occurs while loading the document information view
     */
    @FXML
    private void handlePressDocInfo(MouseEvent mouseEvent) throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/ui/fxml/userDocInformation.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Document Information");
            UserDocInformationController controller = fxmlLoader.getController();
            controller.loadDocData(document, image, loan, type);

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

    public Document getDocument() {
        return this.document;
    }

    public Image getImage() {
        return this.image;
    }


}
