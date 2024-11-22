package library.management.ui.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.ContextMenu;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import library.management.data.entity.Document;

public class borrowedController {
  private final fullUserController controller;
  private List<Document> documentList;
  private final List<DocContainerController> docContainerControllerList = new ArrayList<>();
  private static final int BORROWED_COLUMN_MAX = 6;
  private static final String DOCUMENT_CONTAINER_SOURCES = "/ui/fxml/docContainer.fxml";
  private static final int BORROWED_DOCUMENT_MAX = 18;

  public borrowedController(fullUserController controller) {
    this.controller = controller;
  }

  public fullUserController getController() {
    return controller;
  }

  /**
   * control.
   */
  public void initBorrowed() {
    documentList = controller.getDocumentList();
    if(documentList != null) documentList.clear();

    documentList = new ArrayList<>(loadDocument());
    int column = 0;
    int row = 1;
    try {
      for (int i = 0; i < borrowedController.BORROWED_DOCUMENT_MAX; i++) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource(DOCUMENT_CONTAINER_SOURCES));
        VBox docContainerVBox = fxmlLoader.load();
        DocContainerController docContainerController = fxmlLoader.getController();
        if(column == BORROWED_COLUMN_MAX) {
          column = 0;
          ++row;
        }
        controller.borrowViewGPane.add(docContainerVBox, column++, row);

        GridPane.setMargin(docContainerVBox, new Insets(10));
        docContainerControllerList.add(docContainerController);
      }
    } catch(IOException e) {
      e.printStackTrace();
    }
    initializeAutoComplete();
  }

  private List<Document> loadDocument() {
    // Preprocessor data from Google Book API ( Title, author, thumbnail,...)

    //Load from database
    List<Document> list = new ArrayList<>();
    Document document;
    for (int i = 1; i <= 100; i++) {
      document = new Document();
      document.setTitle("Document Title " + i);
      document.setImageSrc("/ui/sprites/demoDoc.gif");
      document.setAuthor("Author " + i);
      list.add(document);
    }

    return list;
  }
  public void initializeAutoComplete() {
  }
}
