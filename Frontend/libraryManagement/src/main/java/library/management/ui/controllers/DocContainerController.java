package library.management.ui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import library.management.data.entity.Document;

public class DocContainerController {

  @FXML
  private Label authorCatalog;
  @FXML
  private ImageView docCatalogImage;
  @FXML
  private VBox docCatalogView;
  @FXML
  private Hyperlink docTitleCatalog;

  private final String[] colors = {
    "D1E8FF", // Light Blue
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

  public void setData(Document document) {
    Image image = new Image(getClass().getResourceAsStream(document.getImageSrc()));
    docCatalogImage.setImage(image);
    docTitleCatalog.setText(document.getTitle());
    authorCatalog.setText(document.getAuthor());

    //Style
    docTitleCatalog.setStyle("-fx-text-fill: #002B5B; -fx-font-size: 14px;");  // Navy Blue
    authorCatalog.setStyle("-fx-text-fill: #333333; -fx-padding: 5;");    // Dark Gray
    docCatalogView.setStyle(
              "-fx-background-color:#" + colors[(int) (Math.random() * colors.length)] + ";"
            + "-fx-background-radius: 15;"
            + "-fx-effect: dropShadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 10);"
    );

  }
}
