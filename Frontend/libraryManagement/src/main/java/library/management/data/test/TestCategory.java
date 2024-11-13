package library.management.data.test;

import library.management.data.DAO.CategoryDAO;
import library.management.data.entity.Category;

public class TestCategory {

    public static void addCategory() {
        String tag = "thien";
        Category category = new Category();
        category.setTag(tag);

        if (CategoryDAO.getInstance().add(category) > 0) {
            System.out.println("Thêm thể loại thành công!");
        } else {
            System.out.println("Thêm thể loại thất bại!");
        }
    }

    public static void deleteCategory() {
        String categoryID = "CAT42";
        Category category = new Category(categoryID);

        if (CategoryDAO.getInstance().delete(category) > 0) {
            System.out.println("Xóa thể loại thành công!");
        } else {
            System.out.println("Xóa thể loại thất bại!");
        }
    }

    public static void updateCategory() {
        String tag = "Non-thien";
        String categoryID = "CAT42";
        Category category = new Category(categoryID, tag);

        if (CategoryDAO.getInstance().update(category) > 0) {
            System.out.println("Cập nhật thể loại thành công!");
        } else {
            System.out.println("Cập nhật thể loại thất bại!");
        }
    }

    public static void main(String[] args) {
        addCategory();
        updateCategory();
        deleteCategory();
    }
}
