package library.management.ui.controllers;


import java.io.IOException;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
import library.management.data.entity.Document;

public class DocContainerController implements GeneralController {

  private final static double DX = 800;
  private final static Duration DURATION = Duration.millis(1000);
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
  private TextField priceField;
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

  public void setData(Document document) {
    Image image = new Image(getClass().getResourceAsStream(document.getImageSrc()));
    docThumbnail.setImage(image);
    docTitleCatalog.setText(document.getTitle());
    authorCatalog.setText(document.getAuthor());

    //Style
    docTitleCatalog.setStyle("-fx-text-fill: #002B5B; -fx-font-size: 14px;");  // Navy Blue
    authorCatalog.setStyle("-fx-text-fill: #333333; -fx-padding: 5;");    // Dark Gray
    docCatalogView.setStyle(
        "-fx-background-color:#" + colors[(int) (Math.random() * colors.length)] + ";"
            + "-fx-background-radius: 15;"
            + "-fx-effect: dropShadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 10);");

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
      FXMLLoader fxmlLoader = new FXMLLoader(
          getClass().getResource("/ui/fxml/docInformation.fxml"));
      Parent root = fxmlLoader.load();
      Stage stage = new Stage();
      stage.setTitle("Document Information");
      stage.setScene(new Scene(root));
      stage.setOnCloseRequest((WindowEvent event) -> {
        stage.close();
      });
      stage.show();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }


  @FXML
  private void handleAddDoc(ActionEvent actionEvent) {
    if (check) {
      transFade(priceHBox, DX, 0.5, 1, DURATION);
      transFade(saveHBox, DX, 0.5, 1, DURATION);
      check = false;
    } else {
      transFade(priceHBox, -DX, 1, 0, DURATION);
      transFade(saveHBox, -DX, 1, 0, DURATION);
      check = true;
    }
  }

  @FXML
  private void handleSave(ActionEvent actionEvent) {
  }
}
