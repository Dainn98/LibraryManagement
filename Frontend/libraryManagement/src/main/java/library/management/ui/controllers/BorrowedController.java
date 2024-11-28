package library.management.ui.controllers;

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
import library.management.data.entity.Loan;
import library.management.properties;

public record BorrowedController(FullUserController controller) implements properties, UserVBoxDocument {

  public void initBorrowedDocuments() {
  }

  public void loadBorrowingDocument() {
    controller.borrowViewGPane.getChildren().clear();
    loanList.clear();
    documentList.clear();
    docContainerControllerList.clear();
    loanList.addAll(
            LoanDAO.getInstance().getBorrowingLoanByUserName(controller.getMainUserName()));
    int column = 0;
    int row = 1;
    try {
      for (int i = 0; i < loanList.size(); i++) {
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
      for (Loan loan : loanList) {
        documentList.add(loan.getDocument());
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    List<Task<Void>> tasks = new ArrayList<>();
    for (int i = 0; i < documentList.size(); ++i) {
      final int index = i;
      Task<Void> loadController = new Task<>() {
        @Override
        protected Void call() throws Exception {
          docContainerControllerList.get(index)
                  .setDocData(documentList.get(index), loanList.get(index),
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