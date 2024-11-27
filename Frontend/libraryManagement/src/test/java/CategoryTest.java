import library.management.data.entity.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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

        assertEquals(123, category.getIntCategoryID(), "Category ID should be parsed correctly from 'CAT123'.");
    }

    @Test
    public void testCategoryConstructorWithCategoryIDAndTag() {
        String categoryID = "CAT456";
        String tag = "Books";
        Category category = new Category(categoryID, tag);

        assertEquals(456, category.getIntCategoryID(), "Category ID should be parsed correctly from 'CAT456'.");
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
        assertEquals("CAT101", category.getStringCategoryID(), "String Category ID should return 'CAT101'.");
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
}
