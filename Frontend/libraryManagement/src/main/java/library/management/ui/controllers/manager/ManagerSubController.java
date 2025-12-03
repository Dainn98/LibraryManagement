package library.management.ui.controllers.manager;

/**
 * The ManagerSubController serves as an abstract base class for controllers that are part of a
 * larger management system. These controllers are designed to handle specific management tasks and
 * operations by interacting with a centralized {@link MainController}.
 * <p>
 * This class provides a basic structure and functionality for sub-controllers, including access to
 * the main controller. It allows derived classes to focus on implementing task-specific behaviors
 * while maintaining a consistent interface for communication with the main controller.
 */
abstract public class ManagerSubController {

  protected MainController controller;

  public MainController getController() {
    return controller;
  }

  public void setController(MainController controller) {
    this.controller = controller;
  }
}
