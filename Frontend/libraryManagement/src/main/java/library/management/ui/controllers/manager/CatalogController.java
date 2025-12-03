package library.management.ui.controllers.manager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import library.management.data.DAO.DocumentDAO;
import library.management.data.DAO.SuggestionDAO;
import library.management.data.entity.Document;
import library.management.properties;
import library.management.ui.applications.GoogleBooksAPI;
import library.management.ui.controllers.SuggestionSearch;

/**
 * CatalogController is responsible for managing the catalog of documents, both from an external API
 * and a local database. It provides functionalities for initializing the catalog view, searching
 * for documents, and managing autocomplete suggestions based on document titles.
 * <p>
 * It extends ManagerSubController and implements properties, SuggestionSearch. This class maintains
 * separate lists for API and local documents, as well as their corresponding controllers for UI
 * management.
 */
public class CatalogController extends ManagerSubController implements properties,
    SuggestionSearch {

  /**
   * Maintains a list of documents available through the API for the catalog. This list is intended
   * to be accessed and potentially modified by various operations within the CatalogController to
   * facilitate document management.
   */
  private final List<Document> APIdocumentList = new ArrayList<>();
  /**
   * A list to store Document objects that are specific to the local catalog. This list is used to
   * manage and manipulate documents available within the local system, distinct from any documents
   * retrieved from an external API. It is primarily utilized within the CatalogController to handle
   * operations such as searching, loading, and updating suggestions for local documents.
   */
  private final List<Document> localDocumentList = new ArrayList<>();
  /**
   * A list of DocContainerController instances used for managing and organizing individual document
   * views within the CatalogController. Each DocContainerController instance handles the UI
   * representation and logic associated with a specific document, including interactions such as
   * displaying document details and loading document images.
   */
  private final List<DocContainerController> APIDocContainerControllerList = new ArrayList<>();
  /**
   * A list that holds instances of {@link DocContainerController} representing local document
   * containers within the catalog context of the application. This list is used to manage the
   * controllers associated with the various document containers displayed in the catalog view,
   * facilitating interaction such as displaying document details, handling mouse events, and
   * updating document information.
   * <p>
   * The list is initialized as an empty {@code ArrayList} and is expected to be populated typically
   * during the initialization or update processes of the catalog, where document controllers need
   * to be dynamically added and managed. By maintaining this list, the application can efficiently
   * handle user interactions with various document elements within the UI.
   */
  private final List<DocContainerController> localDocContainerControllerList = new ArrayList<>();
  /**
   * A list of suggested document titles for autocompletion features in the catalog. This list is
   * observable, meaning it can be dynamically updated and observed for changes, allowing UI
   * components to react when new suggestions are added or existing ones are modified.
   */
  private final ObservableList<String> documentTitleSuggestions = FXCollections.observableArrayList();

  /**
   * Constructs a CatalogController with the specified main controller.
   *
   * @param controller the main controller that manages the catalog operations
   */
  public CatalogController(MainController controller) {
    this.controller = controller;
  }

  /**
   * Initializes the catalog by loading document containers and setting them in the grid layout.
   * This method creates two grids, one for API documents and one for local documents, by using FXML
   * loaders to create view components. The method sets the FXML location to the specified document
   * container source and adds loaded document containers to respective grid panes with specified
   * margins. The document containers are organized in a grid layout defined by the maximum column
   * and row constraints.
   * <ul>
   *   <li>API document containers are added to the `apiViewGPane` pane.</li>
   *   <li>Local document containers are added to the `localViewGPane` pane.</li>
   * </ul>
   * If an IOException occurs during loading, it will be printed in the stack trace.
   * Additionally, this method calls `initializeAutoComplete()` to set up search field behaviors
   * for auto-complete functionality and populates the title suggestion trie with all known
   * document titles.
   * <p>
   * This method interacts heavily with the graphical layout of the application, specifically
   * involving the `DocContainerController` class and the manipulation of the GUI components
   * represented by these controllers. Ensures the preparation of UI components for both types
   * of document views.
   */
  protected void initCatalog() {
    int column = 0;
    int row = 1;
    try {
      for (int i = 0; i < CatalogController.CATALOG_DOCUMENT_MAX; i++) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource(DOCUMENT_CONTAINER_SOURCES));
        VBox docContainerVBox = fxmlLoader.load();
        DocContainerController docContainerController = fxmlLoader.getController();
        if (column == CATALOG_COLUMN_MAX) {
          column = 0;
          ++row;
        }
        controller.apiViewGPane.add(docContainerVBox, column++, row);

        GridPane.setMargin(docContainerVBox, new Insets(10));
        APIDocContainerControllerList.add(docContainerController);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    column = 0;
    row = 1;
    try {
      for (int i = 0; i < CatalogController.CATALOG_DOCUMENT_MAX; i++) {
        var fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource(DOCUMENT_CONTAINER_SOURCES));
        VBox docContainerVBox = fxmlLoader.load();
        DocContainerController docContainerController = fxmlLoader.getController();
        if (column == CATALOG_COLUMN_MAX) {
          column = 0;
          ++row;
        }
        controller.localViewGPane.add(docContainerVBox, column++, row);
        GridPane.setMargin(docContainerVBox, new Insets(10));
        localDocContainerControllerList.add(docContainerController);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    initializeAutoComplete();
    titleTrie.addAll(SuggestionDAO.getInstance().getAll());
  }

  /**
   * Initiates a document search based on user input from the catalog search field. The method
   * synchronizes API and local document data to their respective controllers.
   * <p>
   * This involves:
   * 1. Loading documents from an external API and local database using the user-specified query.
   * 2. Creating tasks for managing the loading and updating of document data in controllers.
   * 3. Executing tasks concurrently using a fixed thread pool for efficiency.
   * <p>
   * The method utilizes a set number of maximum documents it can handle (defined by
   * CATALOG_DOCUMENT_MAX) to limit the size of the data operation.
   */
  protected void searchDocument() {
    loadDocument(controller.catalogSearchField.getText().trim());
    List<Task<Void>> tasks = new ArrayList<>();
    for (int i = 0; i < CatalogController.CATALOG_DOCUMENT_MAX; ++i) {
      final int index = i;
      Task<Void> loadController = new Task<>() {
        @Override
        protected Void call() {
          APIDocContainerControllerList.get(index).setData(APIdocumentList.get(index));
          return null;
        }
      };
      tasks.add(loadController);
    }
    for (int i = 0; i < CatalogController.CATALOG_DOCUMENT_MAX; ++i) {
      final int index = i;
      Task<Void> loadController = new Task<>() {
        @Override
        protected Void call() {
          localDocContainerControllerList.get(index).setData(localDocumentList.get(index));
          return null;
        }
      };
      tasks.add(loadController);
    }
    ExecutorService executor = Executors.newFixedThreadPool(6);
    tasks.forEach(executor::execute);
    executor.shutdown();
  }

  /**
   * Loads documents from both an external API and the local database based on the provided query.
   * The method clears existing lists of documents and populates them with new search results.
   *
   * @param query the search query used to retrieve documents from the API and the local database
   */
  private void loadDocument(String query) {
    APIdocumentList.clear();
    localDocumentList.clear();
    try {
      APIdocumentList.addAll(
          GoogleBooksAPI.searchDocument(query, CatalogController.CATALOG_DOCUMENT_MAX, 0));
      localDocumentList.addAll(DocumentDAO.getInstance()
          .searchDocumentInDatabase(query, CatalogController.CATALOG_DOCUMENT_MAX, 0));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Initializes the auto-complete functionality for the catalog search field. This method sets up
   * an event filter to handle key releases in the search field. When the user types a query, it
   * trims and checks if the query is empty. If the query is non-empty, the auto-complete
   * suggestions are updated. If the query is empty, any visible suggestion menu is hidden. The
   * suggestions are displayed using a suggestion menu, which auto-hides when the focus is lost or
   * the selection is made.
   */
  public void initializeAutoComplete() {
    suggestionMenu.setAutoHide(true);
    controller.catalogSearchField.addEventFilter(KeyEvent.KEY_RELEASED, _ -> {
      String query = controller.catalogSearchField.getText().trim();
      if (query.isEmpty()) {
        suggestionMenu.hide();
      } else {
        updateSuggestions(query);
      }
    });
  }

  /**
   * Updates the suggestion menu based on the given query string. Searches for suggestions using the
   * query, updates the document title suggestions, clears existing suggestion menu items, and adds
   * new ones derived from the query results. Each suggestion item, when clicked, sets the text of
   * the catalog search field to the suggestion, hides the suggestion menu, increments the
   * suggestion's frequency, and triggers a document search. If there are available document title
   * suggestions, the suggestion menu is displayed below the catalog search field.
   *
   * @param query the input string used to search and update the suggestion list.
   */
  private void updateSuggestions(String query) {
    List<String> suggestions = titleTrie.searchSuggestions(query);
    documentTitleSuggestions.setAll(suggestions);
    suggestionMenu.getItems().clear();
    for (String suggestion : suggestions) {
      MenuItem item = new MenuItem(suggestion);
      item.setOnAction(_ -> {
        controller.catalogSearchField.setText(suggestion);
        suggestionMenu.hide();
        titleTrie.incrementFrequency(suggestion);
        searchDocument();
      });
      suggestionMenu.getItems().add(item);
    }
    if (!documentTitleSuggestions.isEmpty()) {
      double screenX = controller.catalogSearchField.localToScreen(0, 0).getX();
      double screenY = controller.catalogSearchField.localToScreen(0, 0).getY()
          + controller.catalogSearchField.getHeight();
      suggestionMenu.hide();
      suggestionMenu.show(controller.catalogSearchField, screenX, screenY);
    } else {
      suggestionMenu.hide();
    }
  }

  /**
   * Adds a search query suggestion to a titleTrie and updates its frequency.
   * <ul>
   *   <li>If the query is not empty, the query is inserted into the titleTrie with an initial frequency of 1.</li>
   *   <li>The frequency of the query in the titleTrie is then incremented.</li>
   * </ul>
   */
  public void addSuggestion() {
    String query = controller.catalogSearchField.getText().trim();
    if (!query.isEmpty()) {
      titleTrie.insert(query, 1);
      titleTrie.incrementFrequency(query);
    }
  }

}

