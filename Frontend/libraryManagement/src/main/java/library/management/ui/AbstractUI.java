package library.management.ui;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;

/**
 * Abstract class representing the base UI functionality.
 */
public abstract class AbstractUI {

  /**
   * Initializes the UI components.
   */
  public abstract void initialize();

  /**
   * Adds zoom effects to a button when hovered.
   *
   * @param button the button to add zoom effects to
   */
  public void addZoomEffects(Button button) {
    button.setOnMouseEntered(event -> onMouseHover(button));
    button.setOnMouseExited(event -> onMouseExit(button));
  }

  /**
   * Handles the mouse hover event to zoom in the button.
   *
   * @param button the button to zoom in
   */
  public void onMouseHover(Button button) {
    button.setScaleX(1.2);
    button.setScaleY(1.2);
  }

  /**
   * Handles the mouse exit event to zoom out the button.
   *
   * @param button the button to zoom out
   */
  public void onMouseExit(Button button) {
    button.setScaleX(1.0);
    button.setScaleY(1.0);
  }
}