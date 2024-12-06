package library.management.data.DataStructure;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import library.management.data.DAO.SuggestionDAO;
import library.management.data.entity.Suggestion;

public class Trie {

  private final TrieNode root;

  public Trie() {
    root = new TrieNode();
  }

  /**
   * Inserts a word into the Trie with a given frequency.
   *
   * @param word      the word to insert.
   * @param frequency the frequency of the word.
   */
  public void insert(String word, int frequency) {
    TrieNode node = root;
    for (char c : word.toCharArray()) {
      node.children.putIfAbsent(c, new TrieNode());
      node = node.children.get(c);
    }
    if (node.isWord) {
      node.frequency += frequency;
    } else {
      node.isWord = true;
      node.frequency = frequency;
      node.word = word;
    }
  }

  /**
   * Increments the frequency of a word in the Trie. If the word does not exist, it does nothing.
   *
   * @param word the word whose frequency is to be incremented.
   */
  public void incrementFrequency(String word) {
    TrieNode node = root;
    for (char c : word.toCharArray()) {
      node = node.children.get(c);
      if (node == null) {
        return;
      }
    }
    if (node.isWord) {
      node.frequency++;
    }
    SuggestionDAO.getInstance().incrementFrequencyByValue(word);
  }

  /**
   * Searches for suggestions based on a given prefix. The suggestions are sorted by frequency in
   * descending order.
   *
   * @param prefix the prefix to search for.
   * @return a list of suggestions sorted by frequency.
   */
  public List<String> searchSuggestions(String prefix) {
    TrieNode node = root;
    for (char c : prefix.toCharArray()) {
      node = node.children.get(c);
      if (node == null) {
        return new ArrayList<>();
      }
    }

    PriorityQueue<TrieNode> pq = new PriorityQueue<>(
        (a, b) -> b.frequency - a.frequency
    );
    collectWords(node, pq, 5);

    List<String> suggestions = new ArrayList<>();
    while (!pq.isEmpty()) {
      suggestions.add(pq.poll().word);
    }
    Collections.reverse(suggestions);
    return suggestions;
  }

  /**
   * Collects words from the Trie starting from a given node and adds them to a priority queue.
   *
   * @param node  the starting node.
   * @param pq    the priority queue to collect words into.
   * @param limit the maximum number of words to collect.
   */
  private void collectWords(TrieNode node, PriorityQueue<TrieNode> pq, int limit) {
    if (node == null) {
      return;
    }
    if (node.isWord) {
      pq.add(node);
    }

    if (pq.size() > limit) {
      pq.poll();
    }

    for (TrieNode child : node.children.values()) {
      collectWords(child, pq, limit);
    }
  }

  /**
   * Adds all suggestions from a list to the Trie.
   *
   * @param suggestions the list of suggestions to add.
   */
  public void addAll(List<Suggestion> suggestions) {
    for (Suggestion suggestion : suggestions) {
      if (suggestion.getValue() != null && !suggestion.getValue().isEmpty()) {
        // Chèn giá trị của Suggestion với tần suất mặc định là 1
        insert(suggestion.getValue(), suggestion.getFrequency());
      }
    }
  }
}
