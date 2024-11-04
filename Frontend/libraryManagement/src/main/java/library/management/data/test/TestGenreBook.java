package library.management.data.test;

import library.management.data.DAO.GenreBookDAO;
import library.management.data.entity.GenreBook;

import java.util.List;

public class TestGenreBook {

    public static void addGenreBook() {
        String tag = "Fiction"; // Thêm thể loại

        // Tạo đối tượng GenreBook với các tham số

        GenreBook genreBook = new GenreBook();
        genreBook.setTag(tag);

        // Gọi phương thức thêm thể loại từ GenreBookDAO
        if (GenreBookDAO.getInstance().them(genreBook) > 0) {
            System.out.println("Thêm thể loại thành công!");
        } else {
            System.out.println("Thêm thể loại thất bại!");
        }
    }

    public static void deleteGenreBook() {
        String genreBookID = "GEN11";
        // Tạo đối tượng GenreBook với STT cần xóa
        GenreBook genreBook = new GenreBook(genreBookID);

        // Gọi phương thức xóa thể loại từ GenreBookDAO
        if (GenreBookDAO.getInstance().xoa(genreBook) > 0) {
            System.out.println("Xóa thể loại thành công!");
        } else {
            System.out.println("Xóa thể loại thất bại!");
        }
    }

    public static void updateGenreBook() {
        String tag = "Non-Fiction"; // Thể loại mới
        String genreID = "GEN11"; // ID thể loại mới

        // Tạo đối tượng GenreBook với thông tin cập nhật
        GenreBook genreBook = new GenreBook(genreID, tag);

        // Gọi phương thức cập nhật thể loại từ GenreBookDAO
        if (GenreBookDAO.getInstance().capNhat(genreBook) > 0) {
            System.out.println("Cập nhật thể loại thành công!");
        } else {
            System.out.println("Cập nhật thể loại thất bại!");
        }
    }

    public static void getAllGenreBooks() {
        // Lấy danh sách tất cả thể loại sách từ GenreBookDAO
        List<GenreBook> genreBooks = GenreBookDAO.getInstance().layTatCa();

        if (genreBooks != null && !genreBooks.isEmpty()) {
            System.out.println("Danh sách thể loại sách:");
            for (GenreBook genreBook : genreBooks) {
                System.out.println("STT: " + genreBook.getSTT() +
                        ", Tag: " + genreBook.getTag() +
                        ", GenreID: " + genreBook.getGenreID());
            }
        } else {
            System.out.println("Không có thể loại sách nào trong cơ sở dữ liệu.");
        }
    }

    public static void main(String[] args) {
        updateGenreBook();
    }
}
