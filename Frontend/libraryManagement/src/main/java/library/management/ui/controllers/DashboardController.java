package library.management.ui.controllers;

import javafx.scene.chart.XYChart;
import library.management.data.DAO.DocumentDAO;
import library.management.data.DAO.LoanDAO;
import library.management.data.DAO.UserDAO;

public class DashboardController {
    private final MainController controller;

    public DashboardController(MainController controller) {
        this.controller = controller;
    };

    public void loadDashBoardData() {
        // load docBChart
        controller.docBChart.getData().clear();
        controller.userBChart.getData().clear();
        XYChart.Series<String, Number> documentInformation = new XYChart.Series<>();
        documentInformation.setName("Document information");
        int docQuantity = DocumentDAO.getInstance().getTotalQuantity();
        int remainingDocQuantity = DocumentDAO.getInstance().getTotalAvailableCopies();
        documentInformation.getData().add(new XYChart.Data<>("All Documents", docQuantity));
        documentInformation.getData().add(new XYChart.Data<>("Remaining Documents", remainingDocQuantity));
        documentInformation.getData().add(new XYChart.Data<>("Issued Documents", docQuantity - remainingDocQuantity));
        controller.docBChart.getData().add(documentInformation);
        XYChart.Series<String, Number> studentInformation = new XYChart.Series<>();
        studentInformation.setName("Student information");
        int totalStudent = UserDAO.getInstance().getAllUserCount();
        int approvedStudent = UserDAO.getInstance().getTotalApprovedUsersCount();
        int studentHoldingBook = LoanDAO.getInstance().getTotalUsersWhoBorrowedBooks();
        studentInformation.getData().add(new XYChart.Data<>("All Students", totalStudent));
        studentInformation.getData().add(new XYChart.Data<>("Approved students", approvedStudent));
        studentInformation.getData().add(new XYChart.Data<>("Students holding documents", studentHoldingBook));
        controller.userBChart.getData().add(studentInformation);
        // load gauge
        controller.allDocsGauge.setMaxValue(docQuantity);
        controller.allDocsGauge.setValue(docQuantity);
        controller.remainingDocsGauge.setMaxValue(docQuantity);
        controller.remainingDocsGauge.setValue(remainingDocQuantity);
        controller.issuedDocsGauge.setMaxValue(docQuantity);
        controller.issuedDocsGauge.setValue(docQuantity - remainingDocQuantity);
        controller.allUsersGauge.setMaxValue(totalStudent);
        controller.allUsersGauge.setValue(approvedStudent);
        controller.docHoldersGauge.setMaxValue(totalStudent);
        controller.docHoldersGauge.setValue(studentHoldingBook);
    }

}
