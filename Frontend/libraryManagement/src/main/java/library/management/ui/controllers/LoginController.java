package library.management.ui.controllers;

import static library.management.alert.AlertMaker.showAlertInformation;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import library.management.main;
import library.management.ui.AbstractUI;

@SuppressWarnings("CallToPrintStackTrace")
public class LoginController implements AbstractUI {

  private boolean isSoundOn = true;
  private final static String userAdmin = "admin";
  private final static String passwordAdmin = "admin";

  @FXML
  private Button soundButton;
  @FXML
  private TextField usernameField;
  @FXML
  private TextField passwordField;
  @FXML
  private Button loginButton;
  @FXML
  private Button signUpButton;
  @FXML
  private FontAwesomeIconView soundIcon;


  /**
   * <p>initialize method is used to initialize the Login screen.</p>
   */
  public void initialize() {
    soundButton.setOnAction(event -> toggleButton());
    addZoomEffects(loginButton);
    addZoomEffects(signUpButton);
    addZoomEffects(soundButton);
  }

  /**
   * <p>login method is used to handle the event when the user clicks on the Login button.</p>
   */
  @FXML
  private void login(ActionEvent event) {
    String username = usernameField.getText();
    String password = passwordField.getText();

    if (username.equals(userAdmin) && password.equals(passwordAdmin)) {

      // Close the current login window
      Stage loginStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
      loginStage.close();

      // Launch the Main application
      main mainApp = new main();
      Stage mainStage = new Stage();
      mainApp.start(mainStage);

    } else {
      showAlertInformation("Login Failed", "Invalid username or password. Please try again.");
    }
  }

  /**
   * <p>register method is used to handle the event when the user clicks on the Register
   * button.</p>
   */
  @FXML
  private void register(ActionEvent event) {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/fxml/register.fxml"));
      AnchorPane registerScreen = loader.load();

      Scene scene = new Scene(registerScreen);
      Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
      stage.setScene(scene);
      stage.show();
    } catch (IOException e) {
      e.printStackTrace(); // Xử lý ngoại lệ nếu không thể tải FXML
    }
  }

  /**
   * <p>toggleButton method is used to toggle the sound button.</p>
   */
  private void toggleButton() {
    if (isSoundOn) {
      soundIcon.setGlyphName("VOLUME_OFF");
      System.out.println("Sound is off");  // Debugging print
    } else {
      soundIcon.setGlyphName("VOLUME_UP");
      System.out.println("Sound is on");  // Debugging print
    }
    isSoundOn = !isSoundOn;
  }
}