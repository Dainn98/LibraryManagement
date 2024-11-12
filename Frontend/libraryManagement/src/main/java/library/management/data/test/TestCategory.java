package library.management.data.test;

import library.management.data.DAO.CategoryDAO;
import library.management.data.entity.Category;

public class TestCategory {

    public static void addCategory() {
        String tag = "thien"; // Thêm thể loại

        // Tạo đối tượng Category với các tham số

        Category Category = new Category();
        Category.setTag(tag);

        // Gọi phương thức thêm thể loại từ CategoryDAO
        if (CategoryDAO.getInstance().add(Category) > 0) {
            System.out.println("Thêm thể loại thành công!");
        } else {
            System.out.println("Thêm thể loại thất bại!");
        }
    }

    public static void deleteCategory() {
        String CategoryID = "CAT42";
        // Tạo đối tượng Category với STT cần xóa
        Category Category = new Category(CategoryID);

        // Gọi phương thức xóa thể loại từ CategoryDAO
        if (CategoryDAO.getInstance().delete(Category) > 0) {
            System.out.println("Xóa thể loại thành công!");
        } else {
            System.out.println("Xóa thể loại thất bại!");
        }
    }

    public static void updateCategory() {
        String tag = "Non-thien"; // Thể loại mới
        String categoryID = "CAT42"; // ID thể loại mới

        // Tạo đối tượng Category với thông tin cập nhật
        Category Category = new Category(categoryID, tag);

        // Gọi phương thức cập nhật thể loại từ CategoryDAO
        if (CategoryDAO.getInstance().update(Category) > 0) {
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
