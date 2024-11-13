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
        int bookQuantity = DocumentDAO.getInstance().getTotalQuantity();
        int remainingBookQuantity = DocumentDAO.getInstance().getTotalAvailableCopies();
        documentInformation.getData().add(new XYChart.Data<>("All Documents", bookQuantity));
        documentInformation.getData().add(new XYChart.Data<>("Remaining Documents", remainingBookQuantity));
        documentInformation.getData().add(new XYChart.Data<>("Issued Documents", bookQuantity - remainingBookQuantity));
        controller.docBChart.getData().add(documentInformation);
        XYChart.Series<String, Number> studentInformation = new XYChart.Series<>();
        studentInformation.setName("Student information");
        int totalStudent = UserDAO.getInstance().getTotalUsersCount();
        int studentHoldingBook = LoanDAO.getInstance().getTotalUsersWhoBorrowedBooks();
        studentInformation.getData().add(new XYChart.Data<>("All Student", totalStudent));
        studentInformation.getData().add(new XYChart.Data<>("Students holding documents", studentHoldingBook));
        controller.userBChart.getData().add(studentInformation);
        // load gauge
        controller.allDocsGauge.setMaxValue(bookQuantity);
        controller.allDocsGauge.setValue(bookQuantity);
        controller.remainingDocsGauge.setMaxValue(bookQuantity);
        controller.remainingDocsGauge.setValue(remainingBookQuantity);
        controller.issuedDocsGauge.setMaxValue(bookQuantity);
        controller.issuedDocsGauge.setValue(bookQuantity - remainingBookQuantity);
        controller.allUsersGauge.setMaxValue(totalStudent);
        controller.allUsersGauge.setValue(totalStudent);
        controller.docHoldersGauge.setMaxValue(totalStudent);
        controller.docHoldersGauge.setValue(studentHoldingBook);
    }
}
