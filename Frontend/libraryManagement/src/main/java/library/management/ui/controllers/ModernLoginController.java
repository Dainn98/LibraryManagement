package library.management.ui.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class ModernLoginController extends GeneralController implements Initializable {

  private static final int WIDTH = 780;
  private static final double rightTrans = (double) WIDTH / 2;
  private static final double leftTrans = -(double) WIDTH / 2;

  @FXML
  protected Pane demoPane;
  @FXML
  protected Pane logRegSprites;
  @FXML
  protected Pane registerNavigation;
  @FXML
  protected Pane loginNavigation;
  @FXML
  protected Pane loginForm;
  @FXML
  protected Pane registerForm;
  @FXML
  protected Pane switchForm;

  public void handleLogin(ActionEvent actionEvent) {
  }

  public void handleRegister(ActionEvent actionEvent) {
  }

  public void showRegisterForm(ActionEvent actionEvent) {
    Timeline timeline = new Timeline();

    timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(0.5), e -> {
      registerForm.setVisible(true);
      loginNavigation.setVisible(true);
      transFade(registerForm, rightTrans, 0, 1, Duration.seconds(0.5));
      transFade(loginForm, rightTrans, 1, 0, Duration.seconds(0.5));
      transFade(loginNavigation, rightTrans, 0.5, 1, Duration.seconds(0.5));
      transFade(registerNavigation, rightTrans, 0.5, 0, Duration.seconds(0.5));
      transFade(logRegSprites, leftTrans, 1, 1, Duration.seconds(0.5));
    }));

    timeline.setOnFinished(e -> {
      loginForm.setVisible(false);
      registerNavigation.setVisible(false);
    });
    timeline.play();
  }

  public void showLoginForm(ActionEvent actionEvent) {
    Timeline timeline = new Timeline();
    timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(0.5), e -> {
      loginForm.setVisible(true);
      registerNavigation.setVisible(true);
      transFade(registerForm, leftTrans, 1, 0, Duration.seconds(0.5));
      transFade(registerNavigation, leftTrans, 0, 1, Duration.seconds(0.5));
      transFade(loginForm, leftTrans, 0.5, 1, Duration.seconds(0.5));
      transFade(loginNavigation, leftTrans, 0.5, 0, Duration.seconds(0.5));
      transFade(logRegSprites, rightTrans, 1, 1, Duration.seconds(0.5));
    }));

    timeline.setOnFinished(e -> {
      registerForm.setVisible(false);
      loginNavigation.setVisible(false);
    });

    timeline.play();
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    loginForm.setVisible(true);
    registerNavigation.setVisible(true);
    registerForm.setVisible(false);
    loginNavigation.setVisible(false);

    registerForm.setLayoutX(0);
    loginForm.setLayoutX(0);

    loginNavigation.setLayoutX((double) -WIDTH / 2);
    registerNavigation.setLayoutX((double) WIDTH / 2);
  }
}
