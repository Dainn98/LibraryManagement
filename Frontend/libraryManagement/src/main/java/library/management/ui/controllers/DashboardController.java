package library.management.ui.controllers;

import javafx.scene.chart.XYChart;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import library.management.data.DAO.DocumentDAO;
import library.management.data.DAO.LoanDAO;
import library.management.data.DAO.UserDAO;


public class DashboardController implements GeneralController {

  private final MainController controller;

  public DashboardController(MainController controller) {
    this.controller = controller;
  }

  ;

  public void loadDashBoardData() {
    // load docBChart
    controller.docBChart.getData().clear();
    controller.userBChart.getData().clear();
    XYChart.Series<String, Number> docInfo = new XYChart.Series<>();
    docInfo.setName("Document information");
    int docQuantity = DocumentDAO.getInstance().getTotalQuantity();
    int remainingDocQuantity = DocumentDAO.getInstance().getTotalAvailableCopies();
    docInfo.getData().add(new XYChart.Data<>("All Documents", docQuantity));
    docInfo.getData().add(new XYChart.Data<>("Remaining Documents", remainingDocQuantity));
    docInfo.getData()
        .add(new XYChart.Data<>("Issued Documents", docQuantity - remainingDocQuantity));
    controller.docBChart.getData().add(docInfo);

    XYChart.Series<String, Number> userInfo = new XYChart.Series<>();
    userInfo.setName("User information");
    int totalUser = UserDAO.getInstance().getTotalUsersCount();
    int userHoldingDocs = LoanDAO.getInstance().getTotalUsersWhoBorrowedBooks();
    userInfo.getData().add(new XYChart.Data<>("All Users", totalUser));
    userInfo.getData().add(new XYChart.Data<>("Users holding documents", userHoldingDocs));
    controller.userBChart.getData().add(userInfo);
    // load gauge
    controller.allDocsGauge.setMaxValue(docQuantity);
    controller.allDocsGauge.setValue(docQuantity);
    controller.remainingDocsGauge.setMaxValue(docQuantity);
    controller.remainingDocsGauge.setValue(remainingDocQuantity);
    controller.issuedDocsGauge.setMaxValue(docQuantity);
    controller.issuedDocsGauge.setValue(docQuantity - remainingDocQuantity);
    controller.allUsersGauge.setMaxValue(totalUser);
    controller.allUsersGauge.setValue(totalUser);
    controller.docHoldersGauge.setMaxValue(totalUser);
    controller.docHoldersGauge.setValue(userHoldingDocs);
  }

  protected void handleClickAvatar(ImageView pic, VBox infoVBox) {
    rotate3D(pic, 0, 1, infoVBox, 270, 1, 90, Duration.millis(1000));
  }

  protected void handleExitAvatarInfo(VBox infoVBox, ImageView pic) {
    rotate3D(infoVBox, 0, 1, pic, 270, 1, 90, Duration.millis(1000));
  }
}
