import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import library.management.data.entity.Language;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LanguageTest {

  private Language language;

  @BeforeEach
  public void setUp() {
    language = new Language();
  }

  @Test
  public void testConstructorWithLgIDAndName() {
    Language language = new Language("LANG123", "English");
    assertEquals(123, language.getIntLgID(), "Language ID should be correctly parsed.");
    assertEquals("English", language.getLgName(), "Language name should be correctly set.");
  }

  @Test
  public void testConstructorWithLgIDOnly() {
    Language language = new Language("LANG456");
    assertEquals(456, language.getIntLgID(), "Language ID should be correctly parsed.");
    assertNull(language.getLgName(), "Language name should be null when not provided.");
  }

  @Test
  public void testSetAndGetLgID() {
    language.setLgID("LANG789");
    assertEquals(789, language.getIntLgID(), "Language ID should be correctly parsed.");
    assertEquals("LANG789", language.getStringLgID(), "Language ID should be formatted correctly.");
  }

  @Test
  public void testSetAndGetLgName() {
    language.setLgName("French");
    assertEquals("French", language.getLgName(),
        "Language name should be correctly set and retrieved.");
  }

  @Test
  public void testSetLgIDWithInvalidFormat() {
    assertThrows(IllegalArgumentException.class, () -> language.setLgID("INVALID123"),
        "Setting an invalid ID format should throw IllegalArgumentException.");
  }

  @Test
  public void testToString() {
    language.setLgName("Spanish");
    assertEquals("Spanish", language.toString(), "toString should return the language name.");
  }
}
