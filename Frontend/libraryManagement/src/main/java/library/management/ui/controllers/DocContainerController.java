package library.management.ui.controllers;

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

public class DocContainerController extends GeneralController implements properties {
  private Image image;
  private Document document;
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

    public void setData(Document doc) {
      this.document = doc;
      String imageUrl = document.getImage();
      if (Objects.equals(imageUrl, DEMO_IMAGE_SOURCE)) {
        image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(imageUrl)));
      } else {
        image = ImageDownloader.downloadImage(document.getImage());
      }
        Platform.runLater(()-> {
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
    PauseTransition pause = new PauseTransition(Duration.seconds(1));
    pause.setOnFinished(_ -> {
      if (!docCatalogView.isHover()) {
        rotate3D(docInfo, 0, 1, docThumbnail, 270, 1, 90, Duration.millis(1000));
      }
    });
    pause.play();
  }

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

  public Document getDocument() {
    return this.document;
  }

  public Image getImage() {
    return this.image;
  }

}
