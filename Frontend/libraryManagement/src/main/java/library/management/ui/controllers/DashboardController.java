package library.management.ui.controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

public class DashboardController extends MainController{

  @FXML
  private VBox dashboardVBox;

  public DashboardController(VBox dashboardVBox) {
    this.dashboardVBox = dashboardVBox;
  }

  public void show() {
    showSection(dashboardVBox);
    System.out.println("Dashboard shown");
  }

  public void hide() {
    dashboardVBox.setVisible(false);
  }
}