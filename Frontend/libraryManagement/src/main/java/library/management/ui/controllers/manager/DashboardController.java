package library.management.ui.controllers.manager;

import javafx.scene.chart.XYChart;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import library.management.data.DAO.DocumentDAO;
import library.management.data.DAO.LoanDAO;
import library.management.data.DAO.UserDAO;
import library.management.ui.controllers.GeneralController;


public class DashboardController extends GeneralController {

  private final MainController controller;

  public DashboardController(MainController controller) {
    this.controller = controller;
  }

  public void loadDashBoardData() {
    controller.docBChart.getData().clear();
    controller.userBChart.getData().clear();
    XYChart.Series<String, Number> docInfo = new XYChart.Series<>();
    docInfo.setName("Document information");
    int docQuantity = DocumentDAO.getInstance().getTotalQuantity();
    int remainingDocQuantity = DocumentDAO.getInstance().getTotalAvailableCopies();
    docInfo.getData().add(new XYChart.Data<>("All Documents", docQuantity));
    docInfo.getData()
        .add(new XYChart.Data<>("Remaining Documents", remainingDocQuantity));
    docInfo.getData()
        .add(new XYChart.Data<>("Issued Documents", docQuantity - remainingDocQuantity));
    controller.docBChart.getData().add(docInfo);
    XYChart.Series<String, Number> userInfo = new XYChart.Series<>();
    userInfo.setName("Users information");
    int totalStudent = UserDAO.getInstance().getAllUsersCount();
    int approvedStudent = UserDAO.getInstance().getApprovedUsersCount();
    int studentHoldingBook = LoanDAO.getInstance().getTotalUsersWhoBorrowedBooks();
    userInfo.getData().add(new XYChart.Data<>("All users", totalStudent));
    userInfo.getData().add(new XYChart.Data<>("Approved users", approvedStudent));
    userInfo.getData()
        .add(new XYChart.Data<>("Users holding documents", studentHoldingBook));
    controller.userBChart.getData().add(userInfo);
    // load gauge
    if (docQuantity > 0) {
      controller.allDocsGauge.setMaxValue(docQuantity);
      controller.allDocsGauge.setValue(0);
      controller.allDocsGauge.setValue(docQuantity);
    }
    if (remainingDocQuantity > 0) {
      controller.remainingDocsGauge.setMaxValue(docQuantity);
      controller.remainingDocsGauge.setValue(0);
      controller.remainingDocsGauge.setValue(remainingDocQuantity);
    }
    if ((docQuantity - remainingDocQuantity) > 0) {
      controller.issuedDocsGauge.setMaxValue(docQuantity);
      controller.issuedDocsGauge.setValue(0);
      controller.issuedDocsGauge.setValue(docQuantity - remainingDocQuantity);
    }
    if (totalStudent > 0) {
      controller.allUsersGauge.setMaxValue(totalStudent);
      controller.allUsersGauge.setValue(0);
      controller.allUsersGauge.setValue(approvedStudent);
    }
    if (studentHoldingBook > 0) {
      controller.docHoldersGauge.setMaxValue(totalStudent);
      controller.docHoldersGauge.setValue(0);
      controller.docHoldersGauge.setValue(studentHoldingBook);
    }
  }

  protected void handleClickAvatar(ImageView pic, VBox infoVBox) {
    rotate3D(pic, 0, 1, infoVBox, 270, 1, 90, Duration.millis(1000));
  }

  protected void handleExitAvatarInfo(VBox infoVBox, ImageView pic) {
    rotate3D(infoVBox, 0, 1, pic, 270, 1, 90, Duration.millis(1000));
  }
}
