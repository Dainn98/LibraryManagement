package library.management.ui.controllers;

import javafx.scene.control.ContextMenu;
import library.management.data.DataStructure.Trie;

public interface SuggestionSearch {
    ContextMenu suggestionMenu = new ContextMenu();
    Trie titleTrie = new Trie();
}
