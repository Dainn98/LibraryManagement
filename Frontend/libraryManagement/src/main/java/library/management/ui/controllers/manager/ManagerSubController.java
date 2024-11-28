package library.management.ui.controllers.manager;

abstract public class ManagerSubController {
    protected MainController controller;

    public MainController getController() {
        return controller;
    }

    public void setController(MainController controller) {
        this.controller = controller;
    }
}
