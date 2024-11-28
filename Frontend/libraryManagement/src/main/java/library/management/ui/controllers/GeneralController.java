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
  Timeline currentRotationAnimation = new Timeline();
  boolean isRotating = true;

  protected void translate(Node node, double x, Duration duration) {
    TranslateTransition transition = new TranslateTransition(duration, node);
    transition.setByX(x);
    transition.setInterpolator(Interpolator.EASE_BOTH);
    transition.play();
  }

  protected void fade(Node node, double fromValue, double toValue, Duration duration) {

    if (toValue != 0.0) {
      node.setVisible(true);
    }
    FadeTransition fade = new FadeTransition(duration, node);
    fade.setFromValue(fromValue);
    fade.setToValue(toValue);
    fade.setInterpolator(Interpolator.EASE_BOTH);
    fade.setOnFinished(e -> {
      if (toValue == 0.0) {
        node.setVisible(false);
      }
    });
    fade.play();
  }

  protected void transFade(Node node, double translateX, double fromValue, double toValue,
      Duration duration) {
    translate(node, translateX, duration);
    fade(node, fromValue, toValue, duration);
  }

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
        isRotating = true; // Quá trình quay hoàn tất
        checkVisible(nodeE); // Đảm bảo nodeE được hiển thị
      });

      roS.play(); // Bắt đầu quay nodeS
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

  protected void stopShakingAnimation(Node node) {
    shakeAnimation.stop();
    node.setTranslateX(0);
  }
}
