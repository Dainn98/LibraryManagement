package library.management.ui.login;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import library.management.ui.UI;

@SuppressWarnings("CallToPrintStackTrace")
public class loginController implements UI {

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


  @FXML
  private void login(ActionEvent event) {
    String username = usernameField.getText();
    String password = passwordField.getText();
    if(username.equals(userAdmin) && password.equals(passwordAdmin)){
      showAlert("Login Success","Welcome " + username + "!");
    } else {
      showAlert("Login Failed", "Invalid username or password. Please try again.");
    }
  }
  @FXML
  public void register(ActionEvent event) {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/registerScreen/register.fxml"));
      AnchorPane registerScreen = loader.load();

      Scene scene = new Scene(registerScreen);
      Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
      stage.setScene(scene);
      stage.show();
    } catch (IOException e) {
      e.printStackTrace(); // Xử lý ngoại lệ nếu không thể tải FXML
    }
  }

  private void onMouseHover(Button button) {
    button.setScaleX(1.2);
    button.setScaleY(1.2);
  }
  private void onMouseExit(Button button) {
    button.setScaleX(1.0);
    button.setScaleY(1.0);
  }

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

  private void addZoomEffects(Button button) {
    button.setOnMouseEntered(event -> onMouseHover(button));
    button.setOnMouseExited(event -> onMouseExit(button));

  }

  @Override
  public void initialize() {
    soundButton.setOnAction(event ->toggleButton());
    addZoomEffects(loginButton);
    addZoomEffects(signUpButton);
    addZoomEffects(soundButton);
  }

  @Override
  public void showAlert(String title, String message) {
  Alert alert = new Alert(Alert.AlertType.INFORMATION);
  alert.setTitle(title);
  alert.setHeaderText(null);
  alert.setContentText(message);
  alert.showAndWait();
  }
}
