package library.management.ui.controllers;

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
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import library.management.data.entity.Document;
import library.management.ui.applications.GoogleBooksAPI;

public class CatalogController {
    private final MainController controller;
    private final List<Document> documentList = new ArrayList<>();
    private final List<DocContainerController> docContainerControllerList = new ArrayList<>();
    private final ObservableList<String> documentTitleSuggestions = FXCollections.observableArrayList();
    private ContextMenu suggestionMenu;


    private static final int CATALOG_COLUMN_MAX = 6;
    private static final String DOCUMENT_CONTAINER_SOURCES = "/ui/fxml/docContainer.fxml";
    private static final int CATALOG_DOCUMENT_MAX = 18;

    public CatalogController(MainController controller) {
        this.controller = controller;
    }

    public MainController getController() {
        return controller;
    }

    protected void initCatalog() {
        int column = 0;
        int row = 1;
        try {
            for (int i = 0; i < CatalogController.CATALOG_DOCUMENT_MAX; i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource(DOCUMENT_CONTAINER_SOURCES));
                VBox docContainerVBox = fxmlLoader.load();
                DocContainerController docContainerController = fxmlLoader.getController();
                if(column == CATALOG_COLUMN_MAX) {
                    column = 0;
                    ++row;
                }
                controller.apiViewGPane.add(docContainerVBox, column++, row);

                GridPane.setMargin(docContainerVBox, new Insets(10));
                docContainerControllerList.add(docContainerController);
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
        initializeAutoComplete();
    }

    protected void searchAPIDocument() {
        loadDocument(controller.catalogSearchField.getText().trim());
        List<Task<Void>> tasks = new ArrayList<>();
        for (int i = 0; i < CatalogController.CATALOG_DOCUMENT_MAX; ++i) {
            final int index = i;
            Task<Void> loadController = new Task<>() {
                @Override
                protected Void call() throws Exception {
                    docContainerControllerList.get(index).setData(documentList.get(index));
                    return null;
                }
            };
            tasks.add(loadController);
        }
        ExecutorService executor = Executors.newFixedThreadPool(4);
        tasks.forEach(executor::execute);
        executor.shutdown();
    }

    private void loadDocument(String query) {
        documentList.clear();
        try {
            documentList.addAll(GoogleBooksAPI.searchDocument(query, CatalogController.CATALOG_DOCUMENT_MAX, 0));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initializeAutoComplete() {
        suggestionMenu = new ContextMenu();
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

    private void updateSuggestions(String query) {
        List<String> suggestions = new ArrayList<>();
        suggestions.add("Name");
        suggestions.add("Harry");
        suggestions.add("Adventure");
        suggestions.add("Fiction");
        suggestions.add("Doraemon");
        suggestions.add("Naruto");
        documentTitleSuggestions.setAll(suggestions);
        suggestionMenu.getItems().clear();
        for (String suggestion : suggestions) {
            MenuItem item = new MenuItem(suggestion);
            item.setOnAction(event -> {
                controller.catalogSearchField.setText(suggestion);
                suggestionMenu.hide();
                searchAPIDocument();
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
}

