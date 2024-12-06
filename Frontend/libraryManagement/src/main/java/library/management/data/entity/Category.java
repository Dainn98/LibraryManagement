package library.management.data.entity;

public class Category extends Detail {

  public Category(String categoryID) {
    this.ID = Integer.parseInt(categoryID.substring(3));
  }

  public Category() {
  }

  public Category(String categoryID, String tag) {
    this.ID = Integer.parseInt(categoryID.substring(3));
    this.tag = tag;
  }

  public String getStringCategoryID() {
    return String.format("CAT%d", ID);
  }

  public int getIntCategoryID() {
    return ID;
  }

  public void setCategoryID(String categoryID) {
    if (categoryID != null && categoryID.startsWith("CAT") && categoryID.length() > 3) {
      try {
        this.ID = Integer.parseInt(categoryID.substring(3));
      } catch (NumberFormatException e) {
        System.out.println("Invalid category ID format: " + categoryID);
        this.ID = 0;
      }
    } else {
      this.ID = 0;
    }
  }

  @Override
  public String toString() {
    return tag;
  }
}
