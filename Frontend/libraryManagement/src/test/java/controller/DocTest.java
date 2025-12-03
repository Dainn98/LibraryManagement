package controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import library.management.data.DAO.DocumentDAO;
import library.management.data.entity.Document;
import library.management.ui.controllers.manager.DocumentController;
import library.management.ui.controllers.manager.MainController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DocTest {
  private Document doc1;
  private Document doc2;
  private Document doc3;
  private DocumentController documentController;
  private MainController mainController ;

  @BeforeEach
  void setUp() {
//    mockMainController = mock(MainController.class);
    mainController = new MainController();
    documentController = new DocumentController(mainController);
//    mockMainController.setDocView(new TableView<>());
//    mockMainController.setCheckAllDocsView(new CheckBox());
//    mockMainController.setSearchDocTField(new TextField());
//    documentController = new DocumentController(mockMainController);


    doc1 = new Document("DOC001","CAT001","Scribner","LANG001","The Great Gatsby",
        "F. Scott Fitzgerald","9780743273565",10,10,
        "1925-04-10T00:00:00",15.99,"A classic American novel.",
        "http://example.com/gatsby","gatsby.jpg","Available");
    doc2 = new Document("DOC002","CAT002","J.B. Lippincott & Co.","LANG002","To Kill a Mockingbird",
        "Harper Lee","9780061120084",12,12,
        "1960-07-11T00:00:00",18.50,"Pulitzer Prize-winning novel.",
        "http://example.com/mockingbird","mockingbird.jpg","Available");
    doc3 = new Document("DOC003","CAT003","Secker & Warburg","LANG003","1984",
        "George Orwell","9780451524935",15,15,
        "1949-06-08T00:00:00",14.99,"Dystopian social science fiction novel.",
        "http://example.com/1984","1984.jpg","Available");
  }
  @Test
  void testDocumentCreation() {
    assert doc1.getTitle().equals("The Great Gatsby");
    assert doc2.getAuthor().equals("Harper Lee");
    assert doc3.getIsbn().equals("9780451524935");
  }
  @Test
  void testGetters() {
    assertEquals("The Great Gatsby", doc1.getTitle());
    assertEquals("Harper Lee", doc2.getAuthor());
    assertEquals("9780451524935", doc3.getIsbn());
    assertEquals(12, doc2.getQuantity());
  }

  @Test
  void testSetters() {
    doc1.setTitle("New Title");
    doc1.setAuthor("New Author");
    doc1.setQuantity(20);
    doc1.setAvailability("Unavailable");

    assertEquals("New Title", doc1.getTitle());
    assertEquals("New Author", doc1.getAuthor());
    assertEquals(20, doc1.getQuantity());
    assertEquals("Unavailable", doc1.getAvailability());
  }

  @Test
  void testIsAvailable() {
    assertTrue(doc1.getAvailability().equalsIgnoreCase("Available"));
    doc1.setAvailability("Unavailable");
    assertFalse(doc1.getAvailability().equalsIgnoreCase("Available"));
  }

  @Test
  void testPrice() {
    assertEquals(15.99, doc1.getPrice());
    doc1.setPrice(20.0);
    assertEquals(20.0, doc1.getPrice());
  }

  @Test
  void testUrlAndImage() {
    assertEquals("http://example.com/gatsby", doc1.getUrl());
    assertEquals("gatsby.jpg", doc1.getImage());
    doc1.setUrl("http://newurl.com");
    doc1.setImage("new.jpg");
    assertEquals("http://newurl.com", doc1.getUrl());
    assertEquals("new.jpg", doc1.getImage());
  }

  @Test
  void testEqualsAndHashCode() {
    Document docCopy = new Document("DOC001","CAT001","Scribner","LANG001","The Great Gatsby",
        "F. Scott Fitzgerald","9780743273565",10,10,
        "1925-04-10T00:00:00",15.99,"A classic American novel.",
        "http://example.com/gatsby","gatsby.jpg","Available");

    assertEquals(doc1, docCopy);
    assertEquals(doc1.hashCode(), docCopy.hashCode());

    assertNotEquals(doc1, doc2);
  }

  @Test
  void testDescriptionField() {
    assertEquals("A classic American novel.", doc1.getDescription());
    doc1.setDescription("Updated description");
    assertEquals("Updated description", doc1.getDescription());
  }

  @Test
  void testAvailableCopies() {
    assertEquals(10, doc1.getAvailableCopies());
    doc1.setAvailableCopies(5);
    assertEquals(5, doc1.getAvailableCopies());
  }

  @Test
  void testISBNValidation() {
    assertTrue(doc1.getIsbn().matches("\\d+"));
    doc1.setIsbn("ABC123");
    assertEquals("ABC123", doc1.getIsbn());
  }

}
