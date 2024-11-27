import library.management.data.entity.Suggestion;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SuggestionTest {

    @Test
    public void testSuggestionConstructorWithAllParams() {
        Suggestion suggestion = new Suggestion(1, "Book", 10);

        assertEquals(1, suggestion.getId(), "ID should be 1");
        assertEquals("Book", suggestion.getValue(), "Value should be 'Book'");
        assertEquals(10, suggestion.getFrequency(), "Frequency should be 10");
    }

    @Test
    public void testSuggestionConstructorWithValueAndFrequency() {
        Suggestion suggestion = new Suggestion("Pen", 5);

        assertEquals("Pen", suggestion.getValue(), "Value should be 'Pen'");
        assertEquals(5, suggestion.getFrequency(), "Frequency should be 5");
    }

    @Test
    public void testSetGetId() {
        Suggestion suggestion = new Suggestion();
        suggestion.setId(123);

        assertEquals(123, suggestion.getId(), "ID should be 123");
    }

    @Test
    public void testSetGetValue() {
        Suggestion suggestion = new Suggestion();
        suggestion.setValue("Notebook");

        assertEquals("Notebook", suggestion.getValue(), "Value should be 'Notebook'");
    }

    @Test
    public void testSetGetFrequency() {
        Suggestion suggestion = new Suggestion();
        suggestion.setFrequency(20);

        assertEquals(20, suggestion.getFrequency(), "Frequency should be 20");
    }

}
