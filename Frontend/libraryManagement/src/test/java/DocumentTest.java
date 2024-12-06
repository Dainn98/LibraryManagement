import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
}
