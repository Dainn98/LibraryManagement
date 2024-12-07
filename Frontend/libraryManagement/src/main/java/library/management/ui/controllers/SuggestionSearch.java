package library.management.ui.controllers;

import javafx.scene.control.ContextMenu;
import library.management.data.DataStructure.Trie;

/**
 * Interface for implementing suggestion-based search functionality.
 * This interface defines the structure for providing suggestions to the user,
 * including a context menu and a Trie data structure to store and search for titles.
 */
public interface SuggestionSearch {

    ContextMenu suggestionMenu = new ContextMenu();
    Trie titleTrie = new Trie();
}
