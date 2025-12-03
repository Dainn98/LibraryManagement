/**
 * The GeneralController class provides a collection of utility methods for animating JavaFX nodes.
 * It includes features such as translations, fades, rotations, 3D effects, zoom interactions, and
 * shaking animations to enhance user interface elements.
 *
 * <p>This class is designed to be extended by specific controllers that require advanced animation
 * effects. It implements shared behaviors defined in the {@link library.management.properties}
 * interface.
 */
package library.management.ui.controllers;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import library.management.properties;


abstract public class GeneralController implements properties {

  Timeline shakeAnimation = new Timeline();
  boolean isRotating = true;

  /**
   * Applies a translation animation to a node along the X-axis.
   *
   * @param node     the node to animate
   * @param x        the distance to move the node along the X-axis
   * @param duration the duration of the translation
   */
  protected void translate(Node node, double x, Duration duration) {
    TranslateTransition transition = new TranslateTransition(duration, node);
    transition.setByX(x);
    transition.setInterpolator(Interpolator.EASE_BOTH);
    transition.play();
  }

  /**
   * Applies a fade transition to a node, changing its opacity from a starting value to an ending
   * value.
   *
   * @param node      the node to animate
   * @param fromValue the starting opacity value
   * @param toValue   the ending opacity value
   * @param duration  the duration of the fade transition
   */
  protected void fade(Node node, double fromValue, double toValue, Duration duration) {

    if (toValue != 0.0) {
      node.setVisible(true);
    }
    FadeTransition fade = new FadeTransition(duration, node);
    fade.setFromValue(fromValue);
    fade.setToValue(toValue);
    fade.setInterpolator(Interpolator.EASE_BOTH);
    fade.setOnFinished(_ -> {
      if (toValue == 0.0) {
        node.setVisible(false);
      }
    });
    fade.play();
  }

  /**
   * Applies both a translation and a fade transition to a node sequentially.
   *
   * @param node       the node to animate
   * @param translateX the translation distance along the X-axis
   * @param fromValue  the starting opacity value
   * @param toValue    the ending opacity value
   * @param duration   the duration of the animation
   */
  protected void transFade(Node node, double translateX, double fromValue, double toValue,
      Duration duration) {
    translate(node, translateX, duration);
    fade(node, fromValue, toValue, duration);
  }

  /**
   * Applies both a translation and a fade transition to a node sequentially, with additional style
   * modification for customization.
   *
   * @param pane       the node to animate
   * @param translateX the translation distance along the X-axis
   * @param fromValue  the starting opacity value
   * @param toValue    the ending opacity value
   * @param duration   the duration of the animation
   * @param style      the CSS style to be applied during the transition
   */
  protected void transFade(Node pane, double translateX, double fromValue, double toValue,
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
  protected void rotate3D(Node nodeS, double rotateS, int cycleS,
      Node nodeE, double rotateE, int cycleE,
      double angle, Duration duration) {
    // Define a rotation axis and set the initial rotation angle for nodeS
    nodeS.setRotationAxis(Rotate.Y_AXIS); // Rotate around the Y axis
    nodeS.setRotate(rotateS); //  Set the initial rotation angle

    RotateTransition roS = new RotateTransition(duration, nodeS);
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

    RotateTransition roE = new RotateTransition(duration, nodeE);
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

    roE.setOnFinished(event -> {
      isRotating = true;
      checkVisible(nodeE);
    });

    roS.play();
  }

  /**
   * Checks and ensures that a node is visible and fully opaque.
   *
   * @param node the node to check
   */
  private void checkVisible(Node node) {
    if (!node.isVisible()) {
      node.setVisible(true);
      node.setOpacity(1.0);
    }
  }

  /**
   * Adds zoom effects to a button when hovered.
   */
  protected void addZoomEffects(Button button) {
    button.setOnMouseEntered(event -> onMouseHover(button));
    button.setOnMouseExited(event -> onMouseExit(button));
  }

  /**
   * Handles the mouse hover event to zoom in the button.
   */
  protected void onMouseHover(Button button) {
    button.setScaleX(1.2);
    button.setScaleY(1.2);
  }

  /**
   * Handles the mouse exit event to zoom out the button.
   */
  protected void onMouseExit(Button button) {
    button.setScaleX(1.0);
    button.setScaleY(1.0);
  }

  /**
   * Starts the shaking animation on a node, simulating a shaking effect. The node shakes back and
   * forth along the X-axis.
   *
   * @param node the node to shake
   */
  protected void startShakingAnimation(Node node) {
    shakeAnimation.getKeyFrames().setAll(
        new KeyFrame(Duration.ZERO, new KeyValue(node.translateXProperty(), 0)),
        new KeyFrame(Duration.millis(50),
            new KeyValue(node.translateXProperty(), -SHAKING_ANIMATION_DX)),
        new KeyFrame(Duration.millis(100),
            new KeyValue(node.translateXProperty(), SHAKING_ANIMATION_DX)),
        new KeyFrame(Duration.millis(150),
            new KeyValue(node.translateXProperty(), -SHAKING_ANIMATION_DX)),
        new KeyFrame(Duration.millis(200),
            new KeyValue(node.translateXProperty(), SHAKING_ANIMATION_DX)),
        new KeyFrame(Duration.millis(250), new KeyValue(node.translateXProperty(), 0))
    );
    shakeAnimation.setCycleCount(Timeline.INDEFINITE);
    shakeAnimation.play();
  }

  /**
   * Stops the shaking animation on a node and resets its position.
   *
   * @param node the node to stop shaking
   */
  protected void stopShakingAnimation(Node node) {
    shakeAnimation.stop();
    node.setTranslateX(0);
  }
}
