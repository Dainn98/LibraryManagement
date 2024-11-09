package library.management.data.test;

import library.management.data.DAO.GenreDAO;
import library.management.data.entity.Genre;

import java.util.List;

public class TestGenre {

    public static void addGenre() {
        String tag = "Fiction"; // Thêm thể loại

        // Tạo đối tượng Genre với các tham số

        Genre Genre = new Genre();
        Genre.setTag(tag);

        // Gọi phương thức thêm thể loại từ GenreDAO
        if (GenreDAO.getInstance().add(Genre) > 0) {
            System.out.println("Thêm thể loại thành công!");
        } else {
            System.out.println("Thêm thể loại thất bại!");
        }
    }

    public static void deleteGenre() {
        String GenreID = "GEN11";
        // Tạo đối tượng Genre với STT cần xóa
        Genre Genre = new Genre(GenreID);

        // Gọi phương thức xóa thể loại từ GenreDAO
        if (GenreDAO.getInstance().delete(Genre) > 0) {
            System.out.println("Xóa thể loại thành công!");
        } else {
            System.out.println("Xóa thể loại thất bại!");
        }
    }

    public static void updateGenre() {
        String tag = "Non-Fiction"; // Thể loại mới
        String genreID = "GEN11"; // ID thể loại mới

        // Tạo đối tượng Genre với thông tin cập nhật
        Genre Genre = new Genre(genreID, tag);

        // Gọi phương thức cập nhật thể loại từ GenreDAO
        if (GenreDAO.getInstance().update(Genre) > 0) {
            System.out.println("Cập nhật thể loại thành công!");
        } else {
            System.out.println("Cập nhật thể loại thất bại!");
        }
    }

    public static void main(String[] args) {
        addGenre();
        updateGenre();
        deleteGenre();
    }
}
