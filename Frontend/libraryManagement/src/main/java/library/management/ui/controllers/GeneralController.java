package library.management.ui.controllers;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;


public interface GeneralController {

  default void translate(Node node, double x, Duration duration) {
    TranslateTransition transition = new TranslateTransition(duration, node);
    transition.setByX(x);
    transition.setInterpolator(Interpolator.EASE_BOTH);
    transition.play();
  }

  default void fade(Node node, double fromValue, double toValue, Duration duration) {
    FadeTransition fade = new FadeTransition(duration, node);
    fade.setFromValue(fromValue);
    fade.setToValue(toValue);
    fade.setInterpolator(Interpolator.EASE_BOTH);
    fade.play();
  }

  default void transFade(Node pane, double translateX, double fromValue, double toValue,
      Duration duration) {
    translate(pane, translateX, duration);
    fade(pane, fromValue, toValue, duration);
  }

  default void transFade(Node pane, double translateX, double fromValue, double toValue,
      Duration duration, String style) {
    translate(pane, translateX, duration);
    fade(pane, fromValue, toValue, duration);
  }

  default void rotate3DBack(Node nodeS, Node nodeE, Duration duration) {


  }

 default void rotate3D(Node nodeS, Node nodeE, Duration duration) {
    // Set the pivot point to the center of the nodeS
    nodeS.setRotationAxis(Rotate.Y_AXIS);
    nodeS.setRotate(0);

    // Create a RotateTransition for nodeS
    RotateTransition rotateTransition = new RotateTransition(duration, nodeS);
    rotateTransition.setByAngle(180); // Rotate 180 degrees
    rotateTransition.setCycleCount(1); // Only rotate once
    rotateTransition.setInterpolator(Interpolator.EASE_BOTH);

    // Create a FadeTransition for nodeS
    FadeTransition fadeOutTransition = new FadeTransition(duration, nodeS);
    fadeOutTransition.setFromValue(1.0);
    fadeOutTransition.setToValue(0.0);
    fadeOutTransition.setInterpolator(Interpolator.EASE_BOTH);

    // Create a FadeTransition for nodeE
    FadeTransition fadeInTransition = new FadeTransition(duration, nodeE);
    fadeInTransition.setFromValue(0.0);
    fadeInTransition.setToValue(1.0);
    fadeInTransition.setInterpolator(Interpolator.EASE_BOTH);

    // Play the transitions sequentially
    rotateTransition.setOnFinished(event -> {
        fadeOutTransition.play();
        fadeOutTransition.setOnFinished(event2 -> fadeInTransition.play());
    });

    rotateTransition.play();
}

  /**
   * Adds zoom effects to a button when hovered.
   */
  default void addZoomEffects(Button button) {
    button.setOnMouseEntered(event -> onMouseHover(button));
    button.setOnMouseExited(event -> onMouseExit(button));
  }

  /**
   * Handles the mouse hover event to zoom in the button.
   */
  default void onMouseHover(Button button) {
    button.setScaleX(1.2);
    button.setScaleY(1.2);
  }

  /**
   * Handles the mouse exit event to zoom out the button.
   */
  default void onMouseExit(Button button) {
    button.setScaleX(1.0);
    button.setScaleY(1.0);
  }
}
