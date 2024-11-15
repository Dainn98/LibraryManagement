package library.management;

import javafx.stage.Stage;

public interface properties {
  Stage window = null;
  static final int SCREEN_WIDTH = 1080;
  static final int SCREEN_HEIGHT = 600;
  static final String MAIN_SCREEN_SOURCES = "/ui/fxml/main.fxml";
  static final String MAIN_STYLE_SOURCES = "/ui/css/pastel-theme.css";
  static final String MAIN_TITLE = "Library Management System";
  static final String LOGIN_SOURCES ="/ui/fxml/modernLogin.fxml";
  static final String SIGN_OUT_TITLE = "Sign Out";
  static final String SIGN_OUT_MESSAGE = "Are you sure you want to sign out?";
}
