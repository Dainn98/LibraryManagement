package library.management.data.entity;

public class Category {
    private String tag;
    private int categoryID;

    public String getStringCategoryID() {
        return String.format("CAT%d", categoryID);
    }

    public int getIntCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        if (categoryID != null && categoryID.startsWith("CAT") && categoryID.length() > 3) {
            try {
                this.categoryID = Integer.parseInt(categoryID.substring(3));
            } catch (NumberFormatException e) {
                System.out.println("Invalid category ID format: " + categoryID);
                this.categoryID = 0;
            }
        } else {
            this.categoryID = 0;
        }
    }


    public String getTag() {
        return tag;
    }

    public Category(String categoryID) {
        this.categoryID = Integer.parseInt(categoryID.substring(3));
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Category() {
    }

    public Category(String categoryID, String tag) {
        this.categoryID = Integer.parseInt(categoryID.substring(3));
        this.tag = tag;
    }

    @Override
    public String toString() {
        return tag;
    }
}
