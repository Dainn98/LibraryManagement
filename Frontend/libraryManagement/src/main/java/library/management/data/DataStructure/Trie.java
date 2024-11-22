package library.management.data.DataStructure;

import library.management.data.DAO.SuggestionDAO;
import library.management.data.entity.Suggestion;

import java.util.*;

public class Trie {
    private TrieNode root;

    public Trie() {
        root = new TrieNode();
    }

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

    public void incrementFrequency(String word) {
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            node = node.children.get(c);
            if (node == null) return;
        }
        if (node.isWord) {
            node.frequency++;
        }
        SuggestionDAO.getInstance().incrementFrequencyByValue(word);
    }

    public List<String> searchSuggestions(String prefix) {
        TrieNode node = root;
        for (char c : prefix.toCharArray()) {
            node = node.children.get(c);
            if (node == null) return new ArrayList<>();
        }

        PriorityQueue<TrieNode> pq = new PriorityQueue<>(
                (a, b) -> b.frequency - a.frequency
        );
        collectWords(node, pq, 5);

        List<String> suggestions = new ArrayList<>();
        while (!pq.isEmpty()) {
            suggestions.add(pq.poll().word);
        }
        Collections.reverse(suggestions); // Đảo ngược vì PriorityQueue trả theo thứ tự giảm dần
        return suggestions;
    }

    private void collectWords(TrieNode node, PriorityQueue<TrieNode> pq, int limit) {
        if (node == null) return;
        if (node.isWord) pq.add(node);

        if (pq.size() > limit) {
            pq.poll();
        }

        for (TrieNode child : node.children.values()) {
            collectWords(child, pq, limit);
        }
    }

    public void addAll(List<Suggestion> suggestions) {
        for (Suggestion suggestion : suggestions) {
            if (suggestion.getValue() != null && !suggestion.getValue().isEmpty()) {
                // Chèn giá trị của Suggestion với tần suất mặc định là 1
                insert(suggestion.getValue(), suggestion.getFrequency());
            }
        }
    }
}
