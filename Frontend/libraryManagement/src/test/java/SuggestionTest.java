import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import library.management.data.entity.Suggestion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SuggestionTest {

  private Suggestion suggestion;

  @BeforeEach
  public void setUp() {
    suggestion = new Suggestion();
  }

  @Test
  public void testDefaultConstructor() {
    assertNotNull(suggestion);
    assertEquals(0, suggestion.getId());
    assertNull(suggestion.getValue());
    assertEquals(0, suggestion.getFrequency());
  }

  @Test
  public void testSetAndGetId() {
    suggestion.setId(123);
    assertEquals(123, suggestion.getId());
  }

  @Test
  public void testSetAndGetValue() {
    suggestion.setValue("Notebook");
    assertEquals("Notebook", suggestion.getValue());
  }

  @Test
  public void testSetAndGetFrequency() {
    suggestion.setFrequency(20);
    assertEquals(20, suggestion.getFrequency());
  }

  @Test
  public void testConstructorWithAllParams() {
    Suggestion s = new Suggestion(1, "Book", 10);
    assertEquals(1, s.getId());
    assertEquals("Book", s.getValue());
    assertEquals(10, s.getFrequency());
  }

  @Test
  public void testConstructorWithValueAndFrequency() {
    Suggestion s = new Suggestion("Pen", 5);
    assertEquals("Pen", s.getValue());
    assertEquals(5, s.getFrequency());
  }

  @Test
  public void testSetNegativeFrequency() {
    suggestion.setFrequency(-5);
    assertEquals(-5, suggestion.getFrequency());
  }

  @Test
  public void testEmptyValue() {
    suggestion.setValue("");
    assertEquals("", suggestion.getValue());
  }

  @Test
  public void testValueWithSpecialCharacters() {
    suggestion.setValue("@#%$^&*()");
    assertEquals("@#%$^&*()", suggestion.getValue());
  }

  @Test
  public void testUpdateFieldsMultipleTimes() {
    suggestion.setId(1);
    suggestion.setValue("Book");
    suggestion.setFrequency(10);

    assertEquals(1, suggestion.getId());
    assertEquals("Book", suggestion.getValue());
    assertEquals(10, suggestion.getFrequency());

    suggestion.setId(2);
    suggestion.setValue("Pen");
    suggestion.setFrequency(20);

    assertEquals(2, suggestion.getId());
    assertEquals("Pen", suggestion.getValue());
    assertEquals(20, suggestion.getFrequency());
  }

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
