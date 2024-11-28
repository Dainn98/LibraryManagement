package library.management;

import javafx.stage.Stage;

public interface properties {

  Stage window = null;
  static final int SCREEN_WIDTH = 1080;
  static final int SCREEN_HEIGHT = 600;
  static final String MAIN_SCREEN_SOURCES = "/ui/fxml/main.fxml";
  static final String MAIN_STYLE_SOURCES = "/ui/css/theme.css";
  static final String MAIN_TITLE = "Library Management System";

  static final String FAQS_CONTAINER_SOURCE = "/ui/fxml/faqContainer.fxml";
  static final boolean RIGHT = false;
  static final boolean LEFT = true;
  static final String SETTINGS_SOURCE = "/ui/fxml/settings.fxml";

  final static String RECORD_SOURCE = "/ui/sprites/recordButton.png";
  final static String SEND_HOVER_SOURCE = "/ui/sprites/sendButtonHover.png";
  final static String SEND_SOURCE = "/ui/sprites/sendButton.png";
  final static String MIRCO_SOURCE = "/ui/sprites/mircoButton.png";
  final static String MIRCO_HOVER_SOURCE = "/ui/sprites/mircoButtonHover.png";

  final static Double SHAKING_ANIMATION_DX = 1.0;

}
