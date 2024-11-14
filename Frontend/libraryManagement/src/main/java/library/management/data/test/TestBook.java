package library.management.data.test;

import library.management.data.DAO.BookDAO;
import library.management.data.entity.Book;

public class TestBook {
    public static void addBook() {
        String genreId = "GEN2"; // Đặt tên là genreId để phù hợp với genrId trong cơ sở dữ liệu
        String publisher = "publisher";
        String lgId = "LANG4"; // Đảm bảo rằng lgId là int theo đúng cấu trúc cơ sở dữ liệu
        String title = "doremon";
        String author = "thien";
        int quantity = 12;
        int availableCopies = 12;
        String addDate = "2024-10-20"; // Định dạng ngày theo chuẩn YYYY-MM-DD
        double price = 12.0;

        // Tạo đối tượng Book với các tham số
        Book book = new Book(genreId, publisher, lgId, title, author, quantity, availableCopies, addDate, price);

        // Gọi phương thức thêm sách từ BookManagement
        if (BookDAO.getInstance().them(book) > 0) {
            System.out.println("Thêm sách thành công!");
        } else {
            System.out.println("Thêm sách thất bại!");
        }
    }

    public static void deleteBook() {
        Book book = new Book("BOOK11");
        if (BookDAO.getInstance().xoa(book) > 0) {
            System.out.println("Xóa sách thành công");
        } else {
            System.out.println("Xóa sách thất bại");
        }
    }

    public static void updateBook() {
        String bookId = "BOOK11"; // Đảm bảo bookId đã tồn tại trong cơ sở dữ liệu để cập nhật
        String genreId = "GEN3"; // Giá trị mới cho genrId
        String publisher = "new publisher";
        String lgId = "LANG5"; // Giá trị mới cho lgID
        String title = "Updated Title"; // Tên sách mới
        String author = "Updated Author"; // Tác giả mới
        int quantity = 20; // Số lượng sách mới
        int availableCopies = 15; // Số bản sao sẵn có mới
        String addDate = "2024-11-01"; // Ngày thêm sách mới
        double price = 15.0; // Giá mới

        // Tạo đối tượng Book với các thông tin cập nhật
        Book book = new Book(bookId, genreId, publisher, lgId, title, author, quantity, availableCopies, addDate, price);

        // Gọi phương thức cập nhật từ BookDAO
        if (BookDAO.getInstance().capNhat(book) > 0) {
            System.out.println("Cập nhật sách thành công!");
        } else {
            System.out.println("Cập nhật sách thất bại!");
        }
    }


    public static void main(String[] args) {
        deleteBook();
    }
}
