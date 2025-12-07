import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import library.management.data.entity.Language;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LanguageTest {

  private Language language;
  private Language lang1;
  private Language lang2;
  private Language lang3;

  @BeforeEach
  public void setUp() {
    language = new Language();
    lang1 = new Language("LANG001", "Tuấn Anh");
    lang2 = new Language("LANG002", "Phạm");
    lang3 = new Language("LANG003", "Dainn98");
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
  @Test
  public void testSetLgIDNull() {
    language.setLgID(null);
    assertEquals(0, language.getIntLgID(), "Int LgID should be 0 if null.");
    assertNotNull(language.getStringLgID(), "String LgID should be null if null.");
  }

  @Test
  public void testSetLgNameNull() {
    language.setLgName(null);
    assertNull(language.getLgName(), "Language name should be null if set to null.");
  }

  @Test
  public void testSetLgNameEmpty() {
    language.setLgName("");
    assertEquals("", language.getLgName(), "Language name can be empty string.");
  }

  @Test
  public void testEqualsSameObject() {
    assertEquals(language, language, "Language should be equal to itself.");
  }

  @Test
  public void testEqualsDifferentObject() {
    Language lang2 = new Language("LANG001", "English");
    language.setLgID("LANG002");
    assertNotEquals(language, lang2, "Languages with different IDs should not be equal.");
  }

  @Test
  public void testEqualsNull() {
    assertNotEquals(language, null, "Language should not be equal to null.");
  }

  @Test
  public void testEqualsDifferentType() {
    assertNotEquals(language, "string", "Language should not be equal to a different type.");
  }

  @Test
  public void testHashCodeConsistency() {
    language.setLgID("LANG100");
    int hash1 = language.hashCode();
    int hash2 = language.hashCode();
    assertEquals(hash1, hash2, "hashCode should be consistent.");
  }

  @Test
  public void testMultipleLgIDFormats() {
    language.setLgID("LANG001");
    assertEquals(1, language.getIntLgID());

    language.setLgID("LANG099");
    assertEquals(99, language.getIntLgID());

    language.setLgID("LANG000");
    assertEquals(0, language.getIntLgID());
  }

  @Test
  public void testToStringWithNullName() {
    language.setLgName(null);
    assertNull(language.toString(), "toString should return null if name is null.");
  }

  @Test
  public void testChainedSetters() {
    language.setLgID("LANG555");
    language.setLgName("German");

    assertEquals(555, language.getIntLgID());
    assertEquals("LANG555", language.getStringLgID());
    assertEquals("German", language.getLgName());
  }

  @Test
  public void testInvalidLgIDFormatThrows() {
    assertThrows(IllegalArgumentException.class, () -> language.setLgID("123LANG"),
        "Invalid LgID format should throw exception.");
  }

  @Test
  public void testIntLgIDParsing() {
    assertEquals(1, lang1.getIntLgID(), "ID LANG001 should parse to 1");
    assertEquals(2, lang2.getIntLgID(), "ID LANG002 should parse to 2");
    assertEquals(3, lang3.getIntLgID(), "ID LANG003 should parse to 3");
  }

  @Test
  public void testLgNameGetter() {
    assertEquals("Tuấn Anh", lang1.getLgName());
    assertEquals("Phạm", lang2.getLgName());
    assertEquals("Dainn98", lang3.getLgName());
  }

  @Test
  public void testSetLgName() {
    lang1.setLgName("UET");
    lang2.setLgName("VNU");
    lang3.setLgName("SE");

    assertEquals("UET", lang1.getLgName());
    assertEquals("VNU", lang2.getLgName());
    assertEquals("SE", lang3.getLgName());
  }

  @Test
  public void testSetLgIDValid() {
    lang1.setLgID("LANG2005");  // năm 2005
    lang2.setLgID("LANG1207");  // ngày 12/07
    lang3.setLgID("LANG2023");  // năm 2023

    assertEquals(2005, lang1.getIntLgID());
    assertEquals(1207, lang2.getIntLgID());
    assertEquals(2023, lang3.getIntLgID());
  }

  @Test
  public void testInvalidLgIDFormatThrowsMore() {
    assertThrows(IllegalArgumentException.class, () -> lang1.setLgID("TUAN001"));
    assertThrows(IllegalArgumentException.class, () -> lang2.setLgID("123LANG"));
    assertThrows(IllegalArgumentException.class, () -> lang3.setLgID("VNU-SE"));
  }

  @Test
  public void testToStringReturnsName() {
    assertEquals("Tuấn Anh", lang1.toString());
    assertEquals("Phạm", lang2.toString());
    assertEquals("Dainn98", lang3.toString());
  }

  @Test
  public void testResetLgName() {
    lang1.setLgName(null);
    lang2.setLgName(null);
    lang3.setLgName(null);

    assertNull(lang1.getLgName());
    assertNull(lang2.getLgName());
    assertNull(lang3.getLgName());
  }

  @Test
  public void testSetLgNameEmptyString() {
    lang1.setLgName("");
    lang2.setLgName("");
    lang3.setLgName("");

    assertEquals("", lang1.getLgName());
    assertEquals("", lang2.getLgName());
    assertEquals("", lang3.getLgName());
  }
}
