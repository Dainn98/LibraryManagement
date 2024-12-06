package library.management;

import javafx.stage.Stage;
import javafx.util.Duration;

public interface properties {

  Stage window = null;

  // SCREEN CONTROLLER PROPERTIES
  int SCREEN_WIDTH = 1080;
  int SCREEN_HEIGHT = 600;

  // MAIN CONTROLLER PROPERTIES
  String MAIN_SCREEN_SOURCES = "/ui/fxml/main.fxml";
  String MAIN_TITLE = "Library Management System";

  // SETTINGS PROPERTIES
  String SETTINGS_TITLE = "Settings";
  String SETTINGS_SOURCE = "/ui/fxml/settings.fxml";

  // BORROWED CONTROLLER PROPERTIES
  int BORROWED_COLUMN_MAX = 6;
  String DOCUMENT_USER_CONTAINER_SOURCES = "/ui/fxml/userDocContainer.fxml";

  // FAQs CONTROLLER PROPERTIES
  String FAQS_CONTAINER_SOURCE = "/ui/fxml/faqContainer.fxml";
  boolean RIGHT = false;
  boolean LEFT = true;
  String RECORD_SOURCE = "/ui/sprites/recordButton.png";
  String SEND_HOVER_SOURCE = "/ui/sprites/sendButtonHover.png";
  String SEND_SOURCE = "/ui/sprites/sendButton.png";
  String MIRCO_SOURCE = "/ui/sprites/mircoButton.png";
  String MIRCO_HOVER_SOURCE = "/ui/sprites/mircoButtonHover.png";
  Double SHAKING_ANIMATION_DX = 1.0;

  // AVATAR CONTROLLER PROPERTIES
  String AVATAR_INFO_SOURCE = "/ui/fxml/avatarInfo.fxml";
  String USER_AVATAR_INFO_SOURCE = "/ui/fxml/avatarInfo1.fxml";

  //CATALOG CONTROLLER PROPERTIES
  int CATALOG_COLUMN_MAX = 6;
  String DOCUMENT_CONTAINER_SOURCES = "/ui/fxml/docContainer.fxml";
  String HOME_DOCUMENT_CONTAINER_SOURCES = "/ui/fxml/userDocContainer.fxml";
  int CATALOG_DOCUMENT_MAX = 18;

  // DOCUMENT CONTAINER PROPERTIES
  String DEMO_IMAGE_SOURCE = "/ui/sprites/demoDoc.gif";
  String DOC_INFORMATION_SOURCE = "/ui/fxml/docInformation.fxml";
  String DOC_INFORMATION_TITLE = "Document Information";

  String ICON_SOURCE = "/ui/sprites/icon.png";

  // USER DOC INFORMATION CONTROLLER
  double DX = 800;
  Duration DURATION = Duration.millis(1000);
  int QR_HEIGHT = 150;
  int QR_WIDTH = 150;
  int BARCODE_HEIGHT = 100;
  int BARCODE_WIDTH = 250;

  // USER CONTAINER CONTROLLER
  int HOME_DOCUMENT = 100;
  int BORROWING_DOCUMENT = 200;
  int PROCESSING_DOCUMENT = 300;
  String[] colors = {"D1E8FF", // Light Blue
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

  // PROCESSING CONTROLLER
  int PROCESS_COLUMN_MAX = 6;

}
