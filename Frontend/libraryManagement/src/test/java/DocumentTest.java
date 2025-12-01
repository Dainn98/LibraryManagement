import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import library.management.data.entity.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DocumentTest {

  private Document document;

  @BeforeEach
  public void setUp() {
    document = new Document();
  }

  @Test
  public void testSetAndGetDocumentID() {
    document.setDocumentID("DOC123");
    assertEquals(123, document.getIntDocumentID(), "Document ID should be correctly parsed.");
    assertEquals("DOC123", document.getDocumentID(), "Document ID should be formatted correctly.");
  }

  @Test
  public void testSetAndGetCategoryID() {
    document.setCategoryID("CAT456");
    assertEquals(456, document.getIntCategoryID(), "Category ID should be correctly parsed.");
    assertEquals("CAT456", document.getStringCategoryID(),
        "Category ID should be formatted correctly.");
  }

  @Test
  public void testSetAndGetPublisher() {
    document.setPublisher("Sample Publisher");
    assertEquals("Sample Publisher", document.getPublisher(),
        "Publisher should be correctly set and retrieved.");
  }

  @Test
  public void testSetAndGetLgID() {
    document.setLgID("LANG789");
    assertEquals(789, document.getIntLgID(), "Language ID should be correctly parsed.");
    assertEquals("LANG789", document.getStringLgID(), "Language ID should be formatted correctly.");
  }

  @Test
  public void testSetAndGetTitle() {
    document.setTitle("Sample Document Title");
    assertEquals("Sample Document Title", document.getTitle(),
        "Title should be correctly set and retrieved.");
  }

  @Test
  public void testSetAndGetAuthor() {
    document.setAuthor("John Doe");
    assertEquals("John Doe", document.getAuthor(), "Author should be correctly set and retrieved.");
  }

  @Test
  public void testSetAndGetIsbn() {
    document.setIsbn("978-3-16-148410-0");
    assertEquals("978-3-16-148410-0", document.getIsbn(),
        "ISBN should be correctly set and retrieved.");
  }

  @Test
  public void testSetAndGetQuantity() {
    document.setQuantity(10);
    assertEquals(10, document.getQuantity(), "Quantity should be correctly set and retrieved.");
  }

  @Test
  public void testSetAndGetAvailableCopies() {
    document.setAvailableCopies(5);
    assertEquals(5, document.getAvailableCopies(),
        "Available copies should be correctly set and retrieved.");
  }

  @Test
  public void testSetAddDate() {
    String validDate = "2024-11-27T12:00:00";
    document.setAddDate(validDate);
    assertNotNull(document.getAddDate(),
        "Add date should be set correctly for a valid date format.");

    // Test invalid date
    assertThrows(IllegalArgumentException.class, () -> document.setAddDate("invalid-date"),
        "Setting invalid date should throw IllegalArgumentException.");
  }

  @Test
  public void testParseId() {
    assertThrows(IllegalArgumentException.class, () -> new Document("INVALID_ID"),
        "Parsing invalid ID format should throw IllegalArgumentException.");
  }

  @Test
  public void testSetAndGetPrice() {
    document.setPrice(100.0);
    assertEquals(100.0, document.getPrice(), "Price should be correctly set and retrieved.");
  }

  @Test
  public void testSetAndGetDescription() {
    document.setDescription("Sample document description.");
    assertEquals("Sample document description.", document.getDescription(),
        "Description should be correctly set and retrieved.");
  }

  @Test
  public void testSetAndGetUrl() {
    document.setUrl("http://example.com");
    assertEquals("http://example.com", document.getUrl(),
        "URL should be correctly set and retrieved.");
  }

  @Test
  public void testSetAndGetImage() {
    document.setImage("http://example.com/image.jpg");
    assertEquals("http://example.com/image.jpg", document.getImage(),
        "Image URL should be correctly set and retrieved.");
  }

  @Test
  public void testSetAndGetAvailability() {
    document.setAvailability("Available");
    assertEquals("Available", document.getAvailability(),
        "Availability should be correctly set and retrieved.");
  }

  @Test
  public void testSetDocumentIDNull() {
    document.setDocumentID(null);
    assertEquals(0, document.getIntDocumentID(), "Int ID should be 0 if set to null.");
    assertNull(document.getDocumentID(), "DocumentID string should be null if set to null.");
  }

  @Test
  public void testSetCategoryIDNull() {
    document.setCategoryID(null);
    assertEquals(0, document.getIntCategoryID(), "Int CategoryID should be 0 if null.");
    assertNull(document.getStringCategoryID(), "CategoryID string should be null if null.");
  }

  @Test
  public void testSetLgIDNull() {
    document.setLgID(null);
    assertEquals(0, document.getIntLgID(), "Int LgID should be 0 if null.");
    assertNull(document.getStringLgID(), "LgID string should be null if null.");
  }

  @Test
  public void testEqualsSameObject() {
    assertEquals(document, document, "Document should be equal to itself.");
  }

  @Test
  public void testEqualsDifferentObject() {
    Document doc2 = new Document();
    doc2.setDocumentID("DOC001");
    document.setDocumentID("DOC002");
    assertNotEquals(document, doc2, "Different Document IDs should not be equal.");
  }

  @Test
  public void testEqualsNull() {
    assertNotEquals(document, null, "Document should not be equal to null.");
  }

  @Test
  public void testEqualsDifferentType() {
    assertNotEquals(document, "string", "Document should not be equal to a different type.");
  }

  @Test
  public void testHashCodeConsistency() {
    document.setDocumentID("DOC100");
    int hash1 = document.hashCode();
    int hash2 = document.hashCode();
    assertEquals(hash1, hash2, "hashCode should be consistent.");
  }

  @Test
  public void testTitleEmptyString() {
    document.setTitle("");
    assertEquals("", document.getTitle(), "Title can be empty string.");
  }

  @Test
  public void testDescriptionEmptyString() {
    document.setDescription("");
    assertEquals("", document.getDescription(), "Description can be empty string.");
  }

  @Test
  public void testAvailabilityCaseInsensitive() {
    document.setAvailability("available");
    assertTrue(document.getAvailability().equalsIgnoreCase("Available"));
    document.setAvailability("UNAVAILABLE");
    assertTrue(document.getAvailability().equalsIgnoreCase("Unavailable"));
  }

  @Test
  public void testPriceZero() {
    document.setPrice(0.0);
    assertEquals(0.0, document.getPrice(), "Price can be 0.0");
  }

  @Test
  public void testAvailableCopiesMoreThanQuantity() {
    document.setQuantity(5);
    document.setAvailableCopies(10);
    assertEquals(10, document.getAvailableCopies(),
        "Available copies can be greater than total quantity (no validation).");
  }

  @Test
  public void testSetAndGetUrlEmpty() {
    document.setUrl("");
    assertEquals("", document.getUrl(), "URL can be empty string.");
  }

  @Test
  public void testSetAndGetImageEmpty() {
    document.setImage("");
    assertEquals("", document.getImage(), "Image can be empty string.");
  }
}
