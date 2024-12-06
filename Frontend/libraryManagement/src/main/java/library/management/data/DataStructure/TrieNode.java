package library.management.data.DataStructure;

import java.util.HashMap;
import java.util.Map;

class TrieNode {

  Map<Character, TrieNode> children;
  boolean isWord;
  int frequency;
  String word;

  public TrieNode() {
    children = new HashMap<>();
    isWord = false;
    frequency = 0;
    word = null;
  }
}