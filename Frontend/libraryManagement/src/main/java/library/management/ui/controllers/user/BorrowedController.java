package library.management.ui.controllers.user;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import library.management.data.DAO.LoanDAO;
import library.management.data.entity.Document;
import library.management.data.entity.Loan;
import library.management.properties;

public class BorrowedController extends UserSubController implements properties {
    private final List<Loan> borrowingLoanList = new ArrayList<>();
    private final List<Document> borrowingDocumentList = new ArrayList<>();
    private final List<UserDocContainerController> docContainerControllerList = new ArrayList<>();


    public BorrowedController(FullUserController controller) {
        this.controller = controller;
    }

    public void initBorrowedDocuments() {
    }

    public void loadBorrowingDocument() {
        controller.borrowViewGPane.getChildren().clear();
        borrowingLoanList.clear();
        borrowingDocumentList.clear();
        docContainerControllerList.clear();
        borrowingLoanList.addAll(
                LoanDAO.getInstance().getBorrowingLoanByUserName(controller.getMainUserName()));
        int column = 0;
        int row = 1;
        try {
            for (int i = 0; i < borrowingLoanList.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource(DOCUMENT_USER_CONTAINER_SOURCES));
                VBox docContainerVBox = fxmlLoader.load();
                UserDocContainerController docContainerController = fxmlLoader.getController();
                if (column >= BORROWED_COLUMN_MAX) {
                    column = 0;
                    ++row;
                }
                controller.borrowViewGPane.add(docContainerVBox, column++, row);
                GridPane.setMargin(docContainerVBox, new Insets(10));
                docContainerControllerList.add(docContainerController);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            for (Loan loan : borrowingLoanList) {
                borrowingDocumentList.add(loan.getDocument());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<Task<Void>> tasks = new ArrayList<>();
        for (int i = 0; i < borrowingDocumentList.size(); ++i) {
            final int index = i;
            Task<Void> loadController = new Task<>() {
                @Override
                protected Void call() throws Exception {
                    docContainerControllerList.get(index)
                            .setDocData(borrowingDocumentList.get(index), borrowingLoanList.get(index),
                                    UserDocContainerController.BORROWING_DOCUMENT);
                    return null;
                }
            };
            tasks.add(loadController);
        }
        ExecutorService executor = Executors.newFixedThreadPool(4);
        tasks.forEach(executor::execute);
        executor.shutdown();
    }
}