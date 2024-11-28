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

        Platform.runLater(()-> {
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
