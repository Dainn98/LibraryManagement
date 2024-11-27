import library.management.data.DataStructure.Trie;
import library.management.data.entity.Suggestion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TrieTest {

    private Trie trie;

    @BeforeEach
    public void setUp() {
        trie = new Trie();
    }

    @Test
    public void testInsertAndSearchSuggestions() {
        // Insert some words with frequencies
        trie.insert("apple", 5);
        trie.insert("app", 3);
        trie.insert("apricot", 2);
        List<String> suggestions = trie.searchSuggestions("ap");
        assertEquals(3, suggestions.size());
        assertTrue(suggestions.contains("apple"));
        assertTrue(suggestions.contains("app"));
        assertTrue(suggestions.contains("apricot"));
    }

    @Test
    public void testIncrementFrequency() {
        trie.insert("apple", 5);
        trie.incrementFrequency("apple");

        List<String> suggestions = trie.searchSuggestions("ap");

        assertTrue(suggestions.contains("apple"));
    }

    @Test
    public void testSearchSuggestionsWithNoMatch() {
        trie.insert("apple", 5);
        trie.insert("app", 3);
        trie.insert("apricot", 2);

        List<String> suggestions = trie.searchSuggestions("ban");

        assertTrue(suggestions.isEmpty());
    }

    @Test
    public void testAddAll() {
        List<Suggestion> suggestions = List.of(
                new Suggestion("apple", 5),
                new Suggestion("app", 3),
                new Suggestion("apricot", 2)
        );
        trie.addAll(suggestions);
        List<String> result = trie.searchSuggestions("ap");
        assertTrue(result.contains("apple"));
        assertTrue(result.contains("app"));
        assertTrue(result.contains("apricot"));
    }

    @Test
    public void testInsertAndIncrementFrequency() {
        trie.insert("apple", 5);
        trie.incrementFrequency("apple");
        List<String> result = trie.searchSuggestions("ap");
        assertTrue(result.contains("apple"));
    }
}
