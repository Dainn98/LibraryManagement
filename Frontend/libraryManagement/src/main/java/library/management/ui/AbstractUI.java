package library.management.ui;

import javafx.scene.control.Button;

public interface AbstractUI {

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