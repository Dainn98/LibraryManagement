package library.management.ui.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import library.management.data.entity.Document;

public class CatalogController {

  private final MainController controller;
  private List<Document> documentList;

  private static final int CATALOG_COLUMN_MAX = 6;
  private static final String DOCUMENT_CONTAINER_SOURCES = "/ui/fxml/docContainer.fxml";

  public CatalogController(MainController controller) {
    this.controller = controller;
  }

  protected void loadCatalogData(GridPane apiView, GridPane localView) {
    documentList = controller.getDocumentList();
    if(documentList != null) documentList.clear();

    documentList = new ArrayList<>(loadDocument());

    int column = 0;
    int row = 1;
    try{
        for(Document doc : documentList) {
          FXMLLoader fxmlLoader = new FXMLLoader();
          fxmlLoader.setLocation(getClass().getResource(DOCUMENT_CONTAINER_SOURCES));
          VBox docContainerVBox = fxmlLoader.load();
          DocContainerController docContainerController = fxmlLoader.getController();

          docContainerController.setData(doc);

          if(column == CATALOG_COLUMN_MAX) {
            column = 0;
            ++row;
          }
          apiView.add(docContainerVBox, column++, row);
          GridPane.setMargin(docContainerVBox, new Insets(10));
        }
    } catch(IOException e) {
      e.printStackTrace();
    }

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


}
