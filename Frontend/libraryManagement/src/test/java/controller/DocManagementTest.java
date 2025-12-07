package controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.ThreadLocalRandom;
import library.management.data.DAO.CategoryDAO;
import library.management.data.DAO.DocumentDAO;
import library.management.data.DAO.LanguageDAO;
import library.management.data.entity.Category;
import library.management.data.entity.Document;
import library.management.data.entity.Language;
import library.management.ui.controllers.manager.DocumentController;
import library.management.ui.controllers.manager.MainController;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DocManagementTest {

  private static final String ALPHANUMERIC = "0123456789";
  private static int numLength = 7;
  private Document doc;
  private MainController mainController;
  private DocumentController docController;
  private Category category;
  private Language language;
  private int catID;
  private int langID;

  private static String randomId(String prefix) {
    StringBuilder sb = new StringBuilder(prefix);
    ThreadLocalRandom random = ThreadLocalRandom.current();
    for (int i = 0; i < numLength; i++) {
      sb.append(ALPHANUMERIC.charAt(random.nextInt(ALPHANUMERIC.length())));
    }
    return sb.toString();
  }


  @BeforeEach
  void setUp() {
    category = new Category("CAT0", "TESTING CATEGORY" + randomId(""));
    language = new Language("LANG0", "TESTING LANGUAGE" + randomId(""));
    mainController = new MainController();
    docController = new DocumentController(mainController);
    CategoryDAO.getInstance().add(category);
    LanguageDAO.getInstance().add(language);
    catID = CategoryDAO.getInstance().getTagId(category.getTag());
    langID = LanguageDAO.getInstance().getLanguageId(language.getLgName());
  }

  @Test
  void testDocumentCreation() {

//    catID = CategoryDAO.getInstance().getTagId(category.getTag());
//    langID = LanguageDAO.getInstance().getLanguageId(language.getLgName());
//    System.out.println("Real Category ID: " + realCatId);
//    System.out.println("Real Language ID: " + realLangId);

//    System.out.println("Category ID from object: " + catID);
//    System.out.println("Language ID from object: " + langID);

    doc = new Document("CAT" + catID, "Test publisher", "LANG" + langID, "Test title",
        "Test author", "ISBN-" + randomId(""), 10, 10, "2025-12-04T11:36:11", 120000.00,
        "Test description...", "Test url", "Test image", "available");
//    System.out.println("Document created with ISBN: " + DocumentDAO.getInstance().searchDocumentByISBN(doc.getIsbn()));

//    System.out.println(doc.getIntDocumentID());
//    System.out.println(doc.getIsbn());

    DocumentDAO.getInstance().add(doc);
//    int test = DocumentDAO.getInstance().getMaxDocumentId();
//    doc.setDocumentID("DOC"+ DocumentDAO.getInstance().getMaxDocumentId());
//    System.out.println(doc.getDocumentID());

    Document retrievedDoc = DocumentDAO.getInstance().searchDocumentByISBN(doc.getIsbn());
    assertNotNull(retrievedDoc);
    assertEquals(doc.getIsbn(), retrievedDoc.getIsbn());
    assertEquals("Test title", retrievedDoc.getTitle());
    assertEquals("Test author", retrievedDoc.getAuthor());
    assertEquals("Test publisher", retrievedDoc.getPublisher());
    assertEquals("Test description...", retrievedDoc.getDescription());
  }

  @Test
  void testAddDocument_ShouldPersistCorrectly() {
//    catID = CategoryDAO.getInstance().getTagId(category.getTag());
//    langID = LanguageDAO.getInstance().getLanguageId(language.getLgName());
    String isbn = "ISBN-" + randomId("");

    doc = new Document("CAT" + catID, "Publisher ABC", "LANG" + langID, "Test Title", "Test Author",
        isbn, 5, 5, "2025-12-04T10:00:00", 50000.0, "Unit Test Desc", "testUrl", "testImg",
        "available");

    DocumentDAO.getInstance().add(doc);

    Document result = DocumentDAO.getInstance().searchDocumentByISBN(isbn);

    assertNotNull(result);
    assertEquals("Test Title", result.getTitle());
    assertEquals("Test Author", result.getAuthor());
    assertEquals("Publisher ABC", result.getPublisher());
    assertEquals(isbn, result.getIsbn());
    assertEquals("available", result.getAvailability());
  }

  @Test
  void testDeleteDocument_ShouldMarkAsRemoved() {
//    catID = CategoryDAO.getInstance().add(category);
//    langID = LanguageDAO.getInstance().add(language);
    catID = CategoryDAO.getInstance().getTagId(category.getTag());
    langID = LanguageDAO.getInstance().getLanguageId(language.getLgName());

    doc = new Document("CAT" + catID, "Pub", "LANG" + langID, "Delete Test", "Author X",
        "ISBN-" + randomId(""), 3, 3, "2025-12-01T12:00:00", 30000.0, "desc", "url", "img",
        "available");

    DocumentDAO.getInstance().add(doc);
//    doc.setDocumentID("DOC" + DocumentDAO.getInstance().getMaxDocumentId());

    DocumentDAO.getInstance().delete(doc);

    Document deleted = DocumentDAO.getInstance().searchDocumentByISBN(doc.getIsbn());

    assertNull(deleted);
  }

  @Test
  void testGetDocumentByIsbn() {
//    catID = CategoryDAO.getInstance().getTagId(category.getTag());
//    langID = LanguageDAO.getInstance().getLanguageId(language.getLgName());
    String isbn = "ISBN-" + randomId("");

    doc = new Document("CAT" + catID, "PubTest", "LANG" + langID, "FindMe", "Author T", isbn, 7, 7,
        "2025-12-04T11:30:00", 70000, "des", "u", "img", "available");

    DocumentDAO.getInstance().add(doc);

    Document found = DocumentDAO.getInstance().getDocumentByIsbn(isbn);

    assertNotNull(found);
    assertEquals("FindMe", found.getTitle());
    assertEquals(isbn, found.getIsbn());
  }

  @Test
  void testCanBeBorrowed() {
//    catID = CategoryDAO.getInstance().getTagId(category.getTag());
//    langID = LanguageDAO.getInstance().getLanguageId(language.getLgName());
    doc = new Document("CAT" + catID, "PubTest", "LANG" + langID, "Borrow Book", "A",
        randomId("ISBN"), 10, 5, "2025-12-04T09:00:00", 20000, "", "", "", "available");

    DocumentDAO.getInstance().add(doc);
    doc.setDocumentID("DOC" + DocumentDAO.getInstance().getMaxDocumentId());

    int result = DocumentDAO.getInstance().canBeBorrowed(doc.getIntDocumentID(), 3);
    assertEquals(1, result);

    result = DocumentDAO.getInstance().canBeBorrowed(doc.getIntDocumentID(), 10);
    assertEquals(Document.NOTENOUGHCOPIES, result);
  }

  @Test
  void testDecreaseAvailableCopies() {
//    catID = CategoryDAO.getInstance().getTagId(category.getTag());
//    langID = LanguageDAO.getInstance().getLanguageId(language.getLgName());
    doc = new Document("CAT" + catID, "Publisher", "LANG" + langID, "Decrease Test", "Author Z",
        randomId("ISBN"), 10, 5, "2025-12-04T10:00:00", 50000, "", "", "", "available");

    DocumentDAO.getInstance().add(doc);
    doc.setDocumentID("DOC" + DocumentDAO.getInstance().getMaxDocumentId());

    boolean ok = DocumentDAO.getInstance().decreaseAvailableCopies(doc.getIntDocumentID(), 3);
    assertTrue(ok);

    Document after = DocumentDAO.getInstance().searchDocumentById(doc.getIntDocumentID());
    assertEquals(2, after.getAvailableCopies());
  }

  @Test
  void testUpdateDocument() {
//    catID = CategoryDAO.getInstance().getTagId(category.getTag());
//    langID = LanguageDAO.getInstance().getLanguageId(language.getLgName());
    doc = new Document("CAT" + catID, "OldPub", "LANG" + langID, "OldTitle", "OldAuthor",
        randomId("ISBN-"), 10, 10, "2025-12-04T10:00:00", 99999, "OldDesc", "url", "img",
        "available");

    DocumentDAO.getInstance().add(doc);

    Document saved = DocumentDAO.getInstance().searchDocumentByISBN(doc.getIsbn());
    saved.setTitle("NewTitle");
    saved.setAuthor("NewAuthor");
    saved.setPublisher("NewPub");

    int ok = DocumentDAO.getInstance().update(saved);
    assertEquals(1, ok);

    Document updated = DocumentDAO.getInstance().searchDocumentByISBN(doc.getIsbn());
    assertEquals("NewTitle", updated.getTitle());
    assertEquals("NewPub", updated.getPublisher());
    assertEquals("NewAuthor", updated.getAuthor());
  }

  @Test
  void testIncreaseAvailableCopies() {
//    catID = CategoryDAO.getInstance().getTagId(category.getTag());
//    langID = LanguageDAO.getInstance().getLanguageId(language.getLgName());
    doc = new Document("CAT" + catID, "Publisher", "LANG" + langID, "IncreaseTest", "AA",
        randomId("ISBN-"), 10, 5, "2025-12-04T10:00:00", 20000, "", "", "", "available");

    DocumentDAO.getInstance().add(doc);
    int id = DocumentDAO.getInstance().getDocumentByIsbn(doc.getIsbn()).getIntDocumentID();

    boolean ok = DocumentDAO.getInstance().increaseAvailableCopies(id, 2);
    assertTrue(ok);

    Document after = DocumentDAO.getInstance().searchDocumentById(id);
    assertEquals(7, after.getAvailableCopies());
  }

  @Test
  void testSoftDeleteDocument() {
//    catID = CategoryDAO.getInstance().getTagId(category.getTag());
//    langID = LanguageDAO.getInstance().getLanguageId(language.getLgName());
    doc = new Document("CAT" + catID, "TestPub", "LANG" + langID, "SoftDelete", "Author",
        randomId("ISBN-"), 10, 10, "2025-12-04T10:00:00", 30000, "d", "u", "i", "available");

    DocumentDAO.getInstance().add(doc);

    DocumentDAO.getInstance().delete(doc);

    Document after = DocumentDAO.getInstance().searchAllDocumentById(doc.getIntDocumentID());
    assertNotNull(after);
    assertEquals("removed", after.getAvailability());

    DocumentDAO.getInstance().deleteDocumentFromDatabase(after);
  }


  @Test
  void testGetAllDocuments() {
//    catID = CategoryDAO.getInstance().getTagId(category.getTag());
//    langID = LanguageDAO.getInstance().getLanguageId(language.getLgName());

    int before = DocumentDAO.getInstance().getTotalQuantity();
    int newAdditions = 10;

    doc = new Document("CAT" + catID, "Pub", "LANG" + langID, "AllDocs", "A", randomId("ISBN-"),
        newAdditions, newAdditions, "2025-12-04T10:00:00", 30000, "", "", "", "available");

    DocumentDAO.getInstance().add(doc);

    int after = DocumentDAO.getInstance().getTotalQuantity();

    assertEquals(before + newAdditions, after);
  }

  @Test
  void testDuplicateISBN_ShouldFail() {
//    catID = CategoryDAO.getInstance().getTagId(category.getTag());
//    langID = LanguageDAO.getInstance().getLanguageId(language.getLgName());

    String isbn = randomId("ISBN-");

    doc = new Document("CAT" + catID, "Pub", "LANG" + langID, "T1", "A", isbn, 5, 5,
        "2025-12-04T10:00:00", 40000, "", "", "", "available");

    DocumentDAO.getInstance().add(doc);

    Document anotherDoc = new Document("CAT" + catID, "Pub", "LANG" + langID, "T2", "B", isbn, 5, 5,
        "2025-12-04T10:00:00", 45000, "", "", "", "available");

    boolean thrown = false;
    try {
      DocumentDAO.getInstance().add(anotherDoc);
    } catch (Exception e) {
      thrown = true;
    }

    assertTrue(thrown);
  }

  @Test
  void testAddWithInvalidCategory_ShouldFail() {
//    catID = CategoryDAO.getInstance().getTagId(category.getTag());
//    langID = LanguageDAO.getInstance().getLanguageId(language.getLgName());

    doc = new Document("CAT999999", "Pub", "LANG" + langID, "InvalidCat", "A", randomId("ISBN-"), 5,
        5, "2025-12-04T10:00:00", 30000, "", "", "", "available");

    boolean failed = false;
    try {
      DocumentDAO.getInstance().add(doc);
    } catch (Exception e) {
      failed = true;
    }

    assertTrue(failed);
  }

  @Test
  void testAddWithInvalidLanguage_ShouldFail() {
//    catID = CategoryDAO.getInstance().getTagId(category.getTag());
//    langID = LanguageDAO.getInstance().getLanguageId(language.getLgName());

    doc = new Document("CAT" + catID, "Pub", "LANG999999", "InvalidLang", "A", randomId("ISBN-"), 5,
        5, "2025-12-04T10:00:00", 30000, "", "", "", "available");

    boolean failed = false;
    try {
      DocumentDAO.getInstance().add(doc);
    } catch (Exception e) {
      failed = true;
    }

    assertTrue(failed);
  }

  @AfterEach
  void cleanUp() {
    if (DocumentDAO.getInstance().getDocumentByIsbn(doc.getIsbn()) != null
        || DocumentDAO.getInstance().getDocumentByDocID(doc.getDocumentID()) != null
        || DocumentDAO.getInstance().getIntDocumentByDocID(doc.getIntDocumentID()) != null) {
      DocumentDAO.getInstance().deleteDocumentFromDatabase(doc);
    }
    CategoryDAO.getInstance().deleteByTag(doc.getCategory());
    LanguageDAO.getInstance().deleteByName(doc.getLanguage());
  }

}
