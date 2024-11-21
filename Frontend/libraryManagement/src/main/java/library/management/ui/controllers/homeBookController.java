package library.management.ui.controllers;

import com.gluonhq.charm.glisten.control.AutoCompleteTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.ContextMenu;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import library.management.data.entity.Document;

public class homeBookController {

  private final fullUserController controller;
  private
  List<Document> documentList;
  private final List<DocContainerController> docContainerControllerList = new ArrayList<>();
  private final ObservableList<String> documentTitleSuggestions = FXCollections.observableArrayList();
  private ContextMenu suggestionMenu;


  private static final int CATALOG_COLUMN_MAX = 6;
  private static final String DOCUMENT_CONTAINER_SOURCES = "/ui/fxml/docContainer.fxml";
  private static final int CATALOG_DOCUMENT_MAX = 18;

  public homeBookController(fullUserController controller) {
    this.controller = controller;
  }
  public fullUserController getController() {
    return controller;
  }
  protected void initHomeData() {
    documentList = controller.getDocumentList();
    if(documentList != null) documentList.clear();

    documentList = new ArrayList<>(loadDocument());
    int column = 0;
    int row = 0;
    try {
      // Load documents data
      System.out.println(documentList.size());
      for (int i = 0; i < documentList.size(); i++) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource(DOCUMENT_CONTAINER_SOURCES));
        VBox docContainerVBox = fxmlLoader.load();
        DocContainerController docContainerController = fxmlLoader.getController();

        // Set data for each document container
        docContainerController.setData(documentList.get(i));

        // Assigning documents to the GridPanes based on the index
        if (column == CATALOG_COLUMN_MAX) {
          column = 0;
        }

        // Decide which GridPane to add the document based on the index
        if (i < 25) {
          controller.oneGrid.add(docContainerVBox, column++, row);
        } else if (i < 50) {
          controller.twoGrid.add(docContainerVBox, column++, row);
        }

        // Set margin around each document container
        GridPane.setMargin(docContainerVBox, new Insets(10));
      }
    } catch (IOException e) {
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

  private void initializeAutoComplete() {
    // Code to initialize autocomplete for catalogSearchField, if necessary
  }

}
