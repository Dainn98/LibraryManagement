package library.management.ui.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ContextMenu;
import javafx.scene.layout.GridPane;
import library.management.data.DataStructure.Trie;
import library.management.data.entity.Document;

import java.util.ArrayList;
import java.util.List;

public class HomeController {
    private final FullUserController controller;
    private final List<List<Document>> documentList = new ArrayList<>();
    private final List<List<DocContainerController>> docContainerControllerList = new ArrayList<>();
    private final List<GridPane> gridPaneList = new ArrayList<>();
    private final ObservableList<String> documentTitleSuggestions = FXCollections.observableArrayList();
    private ContextMenu suggestionMenu;
    private final Trie titleTrie = new Trie();
    private final int MAX_DOCUMENT_ROW = 2;
    private final int MAX_DOCUMENT_COL = 5;

    public HomeController(FullUserController controller) {
        this.controller = controller;
    }

    public void initHome() {
        gridPaneList.clear();
        gridPaneList.add(controller.oneGrid);
        gridPaneList.add(controller.twoGrid);
        documentList.clear();
        documentList.add(new ArrayList<>());
        documentList.add(new ArrayList<>());
        docContainerControllerList.clear();
        docContainerControllerList.add(new ArrayList<>());
        docContainerControllerList.add(new ArrayList<>());
        for (int i = 0; i < MAX_DOCUMENT_ROW; i++) {

        }
    }
}
