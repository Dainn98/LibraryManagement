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

/**
 * The {@code ProcessingController} is responsible for managing the processing of loan requests for
 * a user. It handles the display and management of documents that are currently pending to be
 * processed.
 * <p>
 * This class interacts with the LoanDAO to fetch pending loans and displays the related document
 * information in the UI. The documents are displayed in a grid layout and are updated
 * asynchronously to improve performance.
 */
public class ProcessingController extends UserSubController implements properties {

  private final List<Loan> pendingLoanList = new ArrayList<>();
  private final List<UserDocContainerController> pendingDocContainerControllerList = new ArrayList<>();
  private final List<Document> pendingDocumentList = new ArrayList<>();

  public ProcessingController(FullUserController controller) {
    this.controller = controller;
  }

  public FullUserController getController() {
    return controller;
  }

  public void initProcess() {
  }

  /**
   * Loads the list of pending documents that need to be processed by the user. It fetches pending
   * loans for the logged-in user, creates UI elements for each document, and asynchronously loads
   * the document data into the UI.
   */
  public void loadPendingDocument() {
    controller.processViewGPane.getChildren().clear();
    pendingLoanList.clear();
    pendingDocumentList.clear();
    pendingDocContainerControllerList.clear();
    pendingLoanList.addAll(
        LoanDAO.getInstance().getPendingLoansByUsername(controller.getMainUserName()));
    int column = 0;
    int row = 1;
    try {
      for (int i = 0; i < pendingLoanList.size(); i++) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource(HOME_DOCUMENT_CONTAINER_SOURCES));
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
        protected Void call() {
          pendingDocContainerControllerList.get(index)
              .setDocData(pendingDocumentList.get(index), pendingLoanList.get(index),
                  UserDocContainerController.PROCESSING_DOCUMENT);
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