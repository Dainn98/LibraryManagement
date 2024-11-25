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
import library.management.data.entity.Document;
import library.management.data.entity.Loan;

public class ProcessingController {

  private final FullUserController controller;
  private final List<Loan> pendingLoanList = new ArrayList<>();
  private final List<UserDocContainerController> pendingDocContainerControllerList = new ArrayList<>();
  private final List<Document> pendingDocumentList = new ArrayList<>();
  private static final int PROCESS_COLUMN_MAX = 6;
  private static final String DOCUMENT_CONTAINER_SOURCES = "/ui/fxml/userDocContainer.fxml";


  public ProcessingController(FullUserController controller) {
    this.controller = controller;
  }

  public FullUserController getController() {
    return controller;
  }

  public void initProcess() {
  }

  public void loadPendingDocument() {
    pendingLoanList.clear();
    pendingDocumentList.clear();
    pendingDocContainerControllerList.clear();
    pendingLoanList.addAll(LoanDAO.getInstance().getPendingLoansByUsername(controller.getMainUserName()));
    int column = 0;
    int row = 1;
    try {
      for (int i = 0; i < pendingLoanList.size(); i++) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource(DOCUMENT_CONTAINER_SOURCES));
        VBox docContainerVBox = fxmlLoader.load();
        UserDocContainerController docContainerController = fxmlLoader.getController();
        if (column == PROCESS_COLUMN_MAX) {
          column = 0;
          ++row;
        }
        controller.processViewGPane.add(docContainerVBox, column++, row);
        GridPane.setMargin(docContainerVBox, new Insets(10));
        pendingDocContainerControllerList.add(docContainerController);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    try {
      for (Loan loan : pendingLoanList) {
        pendingDocumentList.add(loan.getDocument());
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    List<Task<Void>> tasks = new ArrayList<>();
    for (int i = 0; i < pendingDocumentList.size(); ++i) {
      final int index = i;
      Task<Void> loadController = new Task<>() {
        @Override
        protected Void call() throws Exception {
          pendingDocContainerControllerList.get(index).setData(pendingDocumentList.get(index));
          return null;
        }
      };
      tasks.add(loadController);
    }
    ExecutorService executor = Executors.newFixedThreadPool(6);
    tasks.forEach(executor::execute);
    executor.shutdown();
  }
}
