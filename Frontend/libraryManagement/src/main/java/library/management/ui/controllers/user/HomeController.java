package library.management.ui.controllers.user;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import library.management.data.DAO.DocumentDAO;
import library.management.data.DAO.SuggestionDAO;
import library.management.data.entity.Document;
import library.management.properties;
import library.management.ui.controllers.GeneralController;
import library.management.ui.controllers.SuggestionSearch;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Controller class for the home screen that manages document search, suggestions, and displays documents
 * grouped by categories in a grid view.
 */
public class HomeController extends GeneralController implements SuggestionSearch, properties {
    private final FullUserController controller;
    private final List<List<Document>> documentList = new ArrayList<>();
    private final List<List<UserDocContainerController>> docContainerControllerList = new ArrayList<>();
    private final List<GridPane> gridPaneList = new ArrayList<>();
    private final List<Label> labelList = new ArrayList<>();
    private final ObservableList<String> documentTitleSuggestions = FXCollections.observableArrayList();
    private final int MAX_DOCUMENT_ROW = 5;
    ObservableList<String> filterChoices = FXCollections.observableArrayList(
            "title", "author", "publisher", "isbn"
    );

    public HomeController(FullUserController controller) {
        this.controller = controller;
    }

    /**
     * Initializes the home screen with document grids, labels, and auto-completion functionality.
     * Sets up the filter options and loads the initial document search results.
     */
    public void initHome() {
        gridPaneList.clear();
        gridPaneList.add(controller.oneGrid);
        gridPaneList.add(controller.twoGrid);
        gridPaneList.add(controller.threeGrid);
        gridPaneList.add(controller.fourGrid);
        gridPaneList.add(controller.fiveGrid);

        labelList.clear();
        labelList.add(controller.firstLabel);
        labelList.add(controller.secondLabel);
        labelList.add(controller.thirdLabel);
        labelList.add(controller.fourthLabel);
        labelList.add(controller.fifthLabel);

        documentList.clear();
        for (int i = 0; i < MAX_DOCUMENT_ROW; i++) {
            documentList.add(new ArrayList<>());
        }
        docContainerControllerList.clear();
        for (int i = 0; i < MAX_DOCUMENT_ROW; i++) {
            docContainerControllerList.add(new ArrayList<>());
        }
        initializeAutoComplete();
        titleTrie.addAll(SuggestionDAO.getInstance().getAll());
        initFilter();

        searchDocument();
    }

    /**
     * Initializes the filter options for document search, allowing the user to filter by title, author, publisher, or ISBN.
     */
    private void initFilter() {
        controller.searchDocumentFilter.getItems().clear();
        controller.searchDocumentFilter.getItems().addAll(filterChoices);
        controller.searchDocumentFilter.getCheckModel().checkAll();
        controller.searchDocumentFilter.getCheckModel().getCheckedItems().addListener((ListChangeListener<String>) change -> {
            while (change.next()) {
                if (change.wasAdded() || change.wasRemoved()) {
                    searchDocument();
                }
            }
        });
    }

    /**
     * Initializes the auto-complete functionality for the document search field.
     * Displays suggestions as the user types in the search field.
     */
    public void initializeAutoComplete() {
        suggestionMenu.setAutoHide(true);
        controller.catalogSearchField.addEventFilter(KeyEvent.KEY_RELEASED, event -> {
            String query = controller.catalogSearchField.getText().trim();
            if (query.isEmpty()) {
                suggestionMenu.hide();
            } else {
                updateSuggestions(query);
            }
        });
    }

    /**
     * Updates the list of suggestions based on the user's query in the search field.
     * Displays the suggestions in a menu.
     *
     * @param query The search query entered by the user.
     */
    private void updateSuggestions(String query) {
        List<String> suggestions = titleTrie.searchSuggestions(query);
        documentTitleSuggestions.setAll(suggestions);
        suggestionMenu.getItems().clear();
        for (String suggestion : suggestions) {
            MenuItem item = new MenuItem(suggestion);
            item.setOnAction(event -> {
                controller.catalogSearchField.setText(suggestion);
                suggestionMenu.hide();
                titleTrie.incrementFrequency(suggestion);
                searchDocument();
            });
            suggestionMenu.getItems().add(item);
        }
        if (!documentTitleSuggestions.isEmpty()) {
            double screenX = controller.catalogSearchField.localToScreen(0, 0).getX();
            double screenY = controller.catalogSearchField.localToScreen(0, 0).getY() + controller.catalogSearchField.getHeight();
            suggestionMenu.hide();
            suggestionMenu.show(controller.catalogSearchField, screenX, screenY);
        } else {
            suggestionMenu.hide();
        }
    }

    /**
     * Searches for documents based on the user's input and displays them in categorized grid panes.
     * The method also manages the loading of document data and populating grid views asynchronously.
     */
    protected void searchDocument() {
        for (int i = 0; i < documentList.size(); i++) {
            gridPaneList.get(i).getChildren().clear();
            docContainerControllerList.get(i).clear();
            labelList.get(i).setText("Category");
        }
        loadDocument(controller.catalogSearchField.getText().trim());
        try {
            for (int i = 0; i < documentList.size(); i++) {
                final int index = i;
                Platform.runLater(() -> {
                    labelList.get(index).setText(documentList.get(index).getFirst().getCategory());
                });
                for (int j = 0; j < documentList.get(i).size(); j++) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource(HOME_DOCUMENT_CONTAINER_SOURCES));
                    VBox docContainerVBox = fxmlLoader.load();
                    UserDocContainerController docContainerController = fxmlLoader.getController();
                    gridPaneList.get(i).add(docContainerVBox, j, 0);
                    GridPane.setMargin(docContainerVBox, new Insets(10));
                    docContainerControllerList.get(i).add(docContainerController);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Task<Void>> tasks = new ArrayList<>();
        for (int i = 0; i < documentList.size(); i++) {
            for (int j = 0; j < documentList.get(i).size(); j++) {
                final int row = i;
                final int col = j;
                Task<Void> loadController = new Task<>() {
                    @Override
                    protected Void call() throws Exception {
                        docContainerControllerList.get(row).get(col).setDocData(documentList.get(row).get(col), null, UserDocContainerController.HOME_DOCUMENT);
                        return null;
                    }
                };
                tasks.add(loadController);
            }
        }
        ExecutorService executor = Executors.newFixedThreadPool(6);
        tasks.forEach(executor::execute);
        executor.shutdown();
    }

    /**
     * Loads documents based on the given search query and selected filter options.
     *
     * @param query The search query entered by the user.
     */
    private void loadDocument(String query) {
        documentList.clear();
        try {
            int MAX_DOCUMENT_COL = 10;
            documentList.addAll(DocumentDAO.getInstance().searchAndGroupDocuments(query, controller.searchDocumentFilter.getCheckModel().getCheckedItems(), MAX_DOCUMENT_ROW, MAX_DOCUMENT_COL));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds the current query from the search field as a suggestion.
     */
    public void addSuggestion() {
        String query = controller.catalogSearchField.getText().trim();
        if (!query.isEmpty()) {
            titleTrie.insert(query, 1);
            titleTrie.incrementFrequency(query);
        }
    }

    /**
     * Handles the event when the user clicks on their avatar image.
     * Triggers a 3D rotation animation for the avatar and related information.
     *
     * @param pic      The avatar image view.
     * @param infoVBox The VBox containing additional information about the user.
     */
    protected void handleClickAvatar(ImageView pic, VBox infoVBox) {
        rotate3D(pic, 0, 1, infoVBox, 270, 1, 90, Duration.millis(1000));
    }

    /**
     * Handles the event when the user exits the avatar info area.
     * Reverses the 3D rotation animation.
     *
     * @param infoVBox The VBox containing additional information about the user.
     * @param pic      The avatar image view.
     */
    protected void handleExitAvatarInfo(VBox infoVBox, ImageView pic) {
        rotate3D(infoVBox, 0, 1, pic, 270, 1, 90, Duration.millis(1000));
    }
}
