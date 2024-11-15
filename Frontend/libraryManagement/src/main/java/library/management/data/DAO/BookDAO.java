package library.management.data.DAO;

import library.management.data.database.KetNoiCSDL;
import library.management.data.entity.Book;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BookDAO implements DAOInterface<Book> {
    private BookDAO(){}

    public static BookDAO getInstance(){
        return new BookDAO();
    }
    @Override
    public int them(Book book) {
        Connection con = KetNoiCSDL.getConnection();
        String query = "INSERT INTO book (genrId, publisher, lgID, title, author, quantity, availableCopies, addDate, price) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, book.getGenrId()); // genrId liên kết với bảng genrebook
            stmt.setString(2, book.getPublisher()); // publisher là tên nhà xuất bản
            stmt.setString(3, book.getLgId()); // lgID liên kết với bảng language
            stmt.setString(4, book.getTitle()); // title là tên sách
            stmt.setString(5, book.getAuthor()); // author là tên tác giả
            stmt.setInt(6, book.getQuantity()); // quantity là số lượng sách
            stmt.setInt(7, book.getAvailableCopies()); // availableCopies là số bản sao sẵn có
            stmt.setDate(8, Date.valueOf(book.getAddDate())); // addDate là ngày thêm sách
            stmt.setBigDecimal(9, java.math.BigDecimal.valueOf(book.getPrice())); // price là giá sách

            int rowsInserted = stmt.executeUpdate(); // Thực thi lệnh và lấy số dòng đã chèn
            return rowsInserted;
        } catch (SQLException e) {
            e.printStackTrace(); // Xử lý ngoại lệ SQL
        }
        return 0; // Trả về 0 nếu thêm thất bại
    }



    @Override
    public int xoa(Book book) {
        Connection con = KetNoiCSDL.getConnection();
        String query = "DELETE FROM book WHERE bookId = ?";
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, book.getBookId()); // Thiết lập bookId cho điều kiện xóa

            int rowsDeleted = stmt.executeUpdate(); // Thực thi lệnh và lấy số dòng đã xóa
            return rowsDeleted;
        } catch (SQLException e) {
            e.printStackTrace(); // Xử lý ngoại lệ SQL
        }
        return 0; // Trả về 0 nếu xóa thất bại
    }


    @Override
    public int capNhat(Book book) {
        Connection con = KetNoiCSDL.getConnection();
        String query = "UPDATE book SET genrId = ?, publisher = ?, lgID = ?, title = ?, author = ?, quantity = ?, availableCopies = ?, addDate = ?, price = ? WHERE bookId = ?";
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, book.getGenrId()); // genrId liên kết với bảng genrebook
            stmt.setString(2, book.getPublisher()); // publisher là tên nhà xuất bản
            stmt.setString(3, book.getLgId()); // lgID liên kết với bảng language
            stmt.setString(4, book.getTitle()); // title là tên sách
            stmt.setString(5, book.getAuthor()); // author là tên tác giả
            stmt.setInt(6, book.getQuantity()); // quantity là số lượng sách
            stmt.setInt(7, book.getAvailableCopies()); // availableCopies là số bản sao sẵn có
            stmt.setDate(8, Date.valueOf(book.getAddDate())); // addDate là ngày thêm sách
            stmt.setBigDecimal(9, java.math.BigDecimal.valueOf(book.getPrice())); // price là giá sách
            stmt.setString(10, book.getBookId()); // bookId là ID của sách để xác định bản ghi cần cập nhật

            int rowsUpdated = stmt.executeUpdate(); // Thực thi lệnh và lấy số dòng đã cập nhật
            return rowsUpdated;
        } catch (SQLException e) {
            e.printStackTrace(); // Xử lý ngoại lệ SQL
        }
        return 0; // Trả về 0 nếu cập nhật thất bại
    }

}
