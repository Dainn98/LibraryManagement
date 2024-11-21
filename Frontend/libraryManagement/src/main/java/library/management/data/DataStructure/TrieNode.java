package library.management.data.DataStructure;

import java.util.HashMap;
import java.util.Map;

class TrieNode {
    Map<Character, TrieNode> children; // Các nút con
    boolean isWord; // Đánh dấu đây là một từ hoàn chỉnh
    int frequency; // Tần suất xuất hiện của từ
    String word; // Từ đầy đủ tại nút này

    public TrieNode() {
        children = new HashMap<>();
        isWord = false;
        frequency = 0;
        word = null;
    }
}