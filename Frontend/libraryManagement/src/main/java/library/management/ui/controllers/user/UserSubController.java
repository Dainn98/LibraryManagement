package library.management.ui.controllers.user;

/**
 * Abstract base class for controllers that are part of a user interface in the library management
 * system. This class provides a reference to the main controller and methods to get and set it.
 */
abstract public class UserSubController {

  FullUserController controller;

  public FullUserController getController() {
    return controller;
  }

  public void setController(FullUserController controller) {
    this.controller = controller;
  }
}
