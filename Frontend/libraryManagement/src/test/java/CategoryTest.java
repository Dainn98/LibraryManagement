import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import library.management.data.entity.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CategoryTest {

  private Category category;

  @BeforeEach
  public void setUp() {
    category = new Category();
  }

  @Test
  public void testCategoryConstructorWithCategoryID() {
    String categoryID = "CAT123";
    Category category = new Category(categoryID);

    assertEquals(123, category.getIntCategoryID(),
        "Category ID should be parsed correctly from 'CAT123'.");
  }

  @Test
  public void testCategoryConstructorWithCategoryIDAndTag() {
    String categoryID = "CAT456";
    String tag = "Books";
    Category category = new Category(categoryID, tag);

    assertEquals(456, category.getIntCategoryID(),
        "Category ID should be parsed correctly from 'CAT456'.");
    assertEquals(tag, category.getTag(), "Tag should be 'Books'.");
  }

  @Test
  public void testSetCategoryID() {
    String categoryID = "CAT789";
    category.setCategoryID(categoryID);

    assertEquals(789, category.getIntCategoryID(), "Category ID should be set to 789.");
  }

  @Test
  public void testGetStringCategoryID() {
    category.setCategoryID("CAT101");
    assertEquals("CAT101", category.getStringCategoryID(),
        "String Category ID should return 'CAT101'.");
  }

  @Test
  public void testSetTag() {
    String tag = "Electronics";
    category.setTag(tag);

    assertEquals(tag, category.getTag(), "Tag should be 'Electronics'.");
  }

  @Test
  public void testToString() {
    String tag = "Furniture";
    category.setTag(tag);

    assertEquals(tag, category.toString(), "toString() should return the tag value.");
  }

  @Test
  public void testDefaultConstructor() {
    category = new Category();
    assertNotNull(category, "Category object should be created with default constructor.");
  }

  @Test
  public void testSetCategoryIDInvalidFormat() {
    String invalidCategoryID = "123";
    category.setCategoryID(invalidCategoryID);

    assertEquals(0, category.getIntCategoryID(), "Category ID should remain 0 for invalid format.");
  }

  @Test
  public void testSetCategoryIDNull() {
    category.setCategoryID(null);
    assertEquals(0, category.getIntCategoryID(), "Category ID should be 0 if set to null.");
    assertNull(category.getStringCategoryID(), "String Category ID should be null if set to null.");
  }

  @Test
  public void testSetTagNull() {
    category.setTag(null);
    assertNull(category.getTag(), "Tag should be null if set to null.");
  }

  @Test
  public void testSetTagEmptyString() {
    category.setTag("");
    assertEquals("", category.getTag(), "Tag should be empty string.");
  }

  @Test
  public void testEqualsSameObject() {
    assertEquals(category, category, "A category should be equal to itself.");
  }

  @Test
  public void testEqualsDifferentObject() {
    Category cat2 = new Category("CAT001", "Books");
    assertNotEquals(category, cat2, "Different categories should not be equal.");
  }

  @Test
  public void testEqualsNull() {
    assertNotEquals(category, null, "Category should not be equal to null.");
  }

  @Test
  public void testEqualsDifferentType() {
    assertNotEquals(category, "some string", "Category should not be equal to a different type.");
  }

  @Test
  public void testResetCategoryID() {
    category.setCategoryID("CAT123");
    category.setCategoryID(null);
    assertEquals(0, category.getIntCategoryID(), "Category ID should reset to 0 when null is set.");
  }

  @Test
  public void testMultipleCategoryIDFormats() {
    category.setCategoryID("CAT001");
    assertEquals(1, category.getIntCategoryID());

    category.setCategoryID("CAT099");
    assertEquals(99, category.getIntCategoryID());

    category.setCategoryID("CAT000");
    assertEquals(0, category.getIntCategoryID());
  }

  @Test
  public void testToStringWithNullTag() {
    category.setTag(null);
    assertNull(category.toString(), "toString() should return null if tag is null.");
  }

  @Test
  public void testChainedSetters() {
    category.setCategoryID("CAT555");
    category.setTag("Stationery");

    assertEquals(555, category.getIntCategoryID());
    assertEquals("CAT555", category.getStringCategoryID());
    assertEquals("Stationery", category.getTag());
  }
}
