package library.management;

import javafx.stage.Stage;

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

  //CATALOG CONTROLLER PROPERTIES
  int CATALOG_COLUMN_MAX = 6;
  String DOCUMENT_CONTAINER_SOURCES = "/ui/fxml/docContainer.fxml";
  int CATALOG_DOCUMENT_MAX = 18;

  // DOCUMENT CONTAINER PROPERTIES
  String DEMO_IMAGE_SOURCE = "/ui/sprites/demoDoc.gif";
  String DOC_INFORMATION_SOURCE = "/ui/fxml/docInformation.fxml";
  String DOC_INFORMATION_TITLE = "Document Information";
}
