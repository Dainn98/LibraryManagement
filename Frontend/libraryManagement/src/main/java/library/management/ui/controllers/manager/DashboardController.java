package library.management.ui.controllers.manager;

import javafx.scene.chart.XYChart;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import library.management.data.DAO.DocumentDAO;
import library.management.data.DAO.LoanDAO;
import library.management.data.DAO.UserDAO;
import library.management.ui.controllers.GeneralController;


/**
 * The DashboardController class is responsible for controlling the dashboard view of the
 * application. It extends the GeneralController and interacts with the MainController to update and
 * display information about documents and users using charts and gauges.
 */
public class DashboardController extends GeneralController {

  /**
   * The controller responsible for managing the main operations and interactions related to the
   * dashboard's functionality. It acts as a central point for controlling the flow of the
   * application and managing communication between different components within the dashboard.
   */
  private final MainController controller;

  /**
   * Constructs a new DashboardController with a specified MainController.
   *
   * @param controller the MainController to be used by this DashboardController
   */
  public DashboardController(MainController controller) {
    this.controller = controller;
  }

  /**
   * Loads and updates the dashboard data for document and user statistics. This method clears
   * existing data in document and user bar charts, retrieves updated information regarding document
   * quantities, user statistics, and updates the respective graphical components on the dashboard.
   * <p>
   * The data includes:
   * - Total number of documents and their availability status.
   * - Total number of users, approved users, and users currently holding documents.
   * <p>
   * Additionally, it updates the gauges on the dashboard to reflect the current statistics of
   * documents and users.
   */
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
    userInfo.getData().add(new XYChart.Data<>("Pending users", totalStudent));
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

  /**
   * Handles the click event on the avatar image. This method triggers a 3D rotation and fade
   * transition effect between the given image and a VBox containing information.
   *
   * @param pic      the ImageView representing the avatar picture that will undergo the rotation
   *                 and fade-out effect
   * @param infoVBox the VBox containing additional information to be displayed, which will undergo
   *                 a rotation and fade-in effect
   */
  protected void handleClickAvatar(ImageView pic, VBox infoVBox) {
    rotate3D(pic, 0, 1, infoVBox, 270, 1, 90, Duration.millis(1000));
  }

  /**
   * Handles the exit animation effect on avatar information display components.
   *
   * @param infoVBox The VBox component containing avatar information to which the rotation effect
   *                 is applied.
   * @param pic      The ImageView avatar image to which the rotation and fade effect is applied.
   */
  protected void handleExitAvatarInfo(VBox infoVBox, ImageView pic) {
    rotate3D(infoVBox, 0, 1, pic, 270, 1, 90, Duration.millis(1000));
  }
}
