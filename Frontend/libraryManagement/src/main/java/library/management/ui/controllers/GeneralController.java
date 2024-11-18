package library.management.ui.controllers;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.scene.control.Button;
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

  /**
   * Applies a 3D rotation and fade transition to two nodes sequentially.
   *
   * @param nodeS    the first node to apply the rotation and fade transition
   * @param rotateS  the initial rotation angle for the first node
   * @param cycleS   the number of cycles for the rotation of the first node
   * @param nodeE    the second node to apply the rotation and fade transition
   * @param rotateE  the initial rotation angle for the second node
   * @param cycleE   the number of cycles for the rotation of the second node
   * @param angle    the angle by which to rotate the nodes
   * @param duration the duration of the transitions
   */
  default void rotate3D(Node nodeS, double rotateS, int cycleS,
                        Node nodeE, double rotateE, int cycleE,
                        double angle, Duration duration) {
    // Create a RotateTransition for nodeS,nodeE.
    RotateTransition roS, roE;

    nodeS.setRotationAxis(Rotate.Y_AXIS); // Rotate around the Y axis
    nodeS.setRotate(rotateS); //  Set the initial rotation angle

    roS = new RotateTransition(duration, nodeS);
    roS.setByAngle(angle); // Rotate by the specified angle
    roS.setCycleCount(cycleS); // Set the number of cycles
    roS.setInterpolator(Interpolator.EASE_BOTH);

    // Create a FadeTransition for nodeS.
    FadeTransition fOutS = new FadeTransition(duration, nodeS);
    fOutS.setFromValue(1.0); // Set the initial opacity
    fOutS.setToValue(0.0); // Set the final opacity
    fOutS.setInterpolator(Interpolator.EASE_BOTH);

    nodeE.setRotationAxis(Rotate.Y_AXIS);
    nodeE.setRotate(rotateE);

    roE = new RotateTransition(duration, nodeE);
    roE.setByAngle(angle);
    roE.setCycleCount(cycleE);
    roE.setInterpolator(Interpolator.EASE_BOTH);

// Ensure the ImageView is visible before starting the transition
    checkVisible(nodeE);

    // Create a FadeTransition for nodeE
    FadeTransition fInE = new FadeTransition(duration, nodeE);
    fInE.setFromValue(0.0);
    fInE.setToValue(1.0);
    fInE.setInterpolator(Interpolator.EASE_BOTH);

    // Play the transitions sequentially
    roS.setOnFinished(event -> {
      fOutS.play();
      fOutS.setOnFinished(event2 -> {
        nodeS.setVisible(false);
        checkVisible(nodeE);
      });
      roE.play();
      checkVisible(nodeE);

    });
    roS.play();
  }

  private void checkVisible(Node node) {
    if (!node.isVisible()) {
      node.setVisible(true);
      node.setOpacity(1.0);
    }
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
