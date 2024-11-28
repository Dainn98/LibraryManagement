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
import library.management.data.DAO.DocumentDAO;
import library.management.data.DAO.SuggestionDAO;
import library.management.data.DataStructure.Trie;
import library.management.data.entity.Document;
import library.management.properties;
import library.management.ui.applications.GoogleBooksAPI;

public class CatalogController implements properties {
    private final MainController controller;
    private final List<Document> APIdocumentList = new ArrayList<>();
    private final List<Document> localDocumentList = new ArrayList<>();
    private final List<DocContainerController> APIDocContainerControllerList = new ArrayList<>();
    private final List<DocContainerController> localDocContainerControllerList = new ArrayList<>();
    private final ObservableList<String> documentTitleSuggestions = FXCollections.observableArrayList();
    private ContextMenu suggestionMenu;
    private final Trie titleTrie = new Trie();

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
                APIDocContainerControllerList.add(docContainerController);
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
        column = 0;
        row = 1;
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
                controller.localViewGPane.add(docContainerVBox, column++, row);
                GridPane.setMargin(docContainerVBox, new Insets(10));
                localDocContainerControllerList.add(docContainerController);
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
        initializeAutoComplete();
        titleTrie.addAll(SuggestionDAO.getInstance().getAll());
    }

    protected void searchDocument() {
        loadDocument(controller.catalogSearchField.getText().trim());
        List<Task<Void>> tasks = new ArrayList<>();
        for (int i = 0; i < CatalogController.CATALOG_DOCUMENT_MAX; ++i) {
            final int index = i;
            Task<Void> loadController = new Task<>() {
                @Override
                protected Void call() throws Exception {
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
                protected Void call() throws Exception {
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

    private void loadDocument(String query) {
        APIdocumentList.clear();
        localDocumentList.clear();
        try {
            APIdocumentList.addAll(GoogleBooksAPI.searchDocument(query, CatalogController.CATALOG_DOCUMENT_MAX, 0));
            localDocumentList.addAll(DocumentDAO.getInstance().searchDocumentInDatabase(query, CatalogController.CATALOG_DOCUMENT_MAX, 0));
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

    public void addSuggestion() {
        String query = controller.catalogSearchField.getText().trim();
        if (!query.isEmpty()) {
            titleTrie.insert(query, 1);
            titleTrie.incrementFrequency(query);
        }
    }

}

