package library.management.ui.controllers.user;

abstract public class UserSubController {
    FullUserController controller;

    public FullUserController getController() {
        return controller;
    }

    public void setController(FullUserController controller) {
        this.controller = controller;
    }
}
