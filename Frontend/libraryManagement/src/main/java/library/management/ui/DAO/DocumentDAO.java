package library.management.ui.DAO;

import library.management.ui.database.databaseConnection;
import library.management.ui.entity.Document;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DocumentDAO implements DAOInterface<Document> {
    private DocumentDAO(){}

    public static DocumentDAO getInstance(){
        return new DocumentDAO();
    }
    @Override
    public int add(Document document) {
        Connection con = databaseConnection.getConnection();
        String query = "INSERT INTO document (genrId, publisher, lgID, title, author, quantity, availableCopies, addDate, price) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, document.getGenrId()); // genrId liên kết với bảng genrebook
            stmt.setString(2, document.getPublisher()); // publisher là tên nhà xuất bản
            stmt.setString(3, document.getLgId()); // lgID liên kết với bảng language
            stmt.setString(4, document.getTitle()); // title là tên sách
            stmt.setString(5, document.getAuthor()); // author là tên tác giả
            stmt.setInt(6, document.getQuantity()); // quantity là số lượng sách
            stmt.setInt(7, document.getAvailableCopies()); // availableCopies là số bản sao sẵn có
            stmt.setDate(8, Date.valueOf(document.getAddDate())); // addDate là ngày thêm sách
            stmt.setBigDecimal(9, java.math.BigDecimal.valueOf(document.getPrice())); // price là giá sách

            int rowsInserted = stmt.executeUpdate(); // Thực thi lệnh và lấy số dòng đã chèn
            return rowsInserted;
        } catch (SQLException e) {
            e.printStackTrace(); // Xử lý ngoại lệ SQL
        }
        return 0; // Trả về 0 nếu thêm thất bại
    }



    @Override
    public int delete(Document document) {
        Connection con = databaseConnection.getConnection();
        String query = "DELETE FROM document WHERE documentId = ?";
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, document.getDocumentID()); // Thiết lập bookId cho điều kiện xóa

            int rowsDeleted = stmt.executeUpdate(); // Thực thi lệnh và lấy số dòng đã xóa
            return rowsDeleted;
        } catch (SQLException e) {
            e.printStackTrace(); // Xử lý ngoại lệ SQL
        }
        return 0; // Trả về 0 nếu xóa thất bại
    }


    @Override
    public int update(Document document) {
        Connection con = databaseConnection.getConnection();
        String query = "UPDATE document SET genrId = ?, publisher = ?, lgID = ?, title = ?, author = ?, quantity = ?, availableCopies = ?, addDate = ?, price = ? WHERE documentId = ?";
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, document.getGenrId()); // genrId liên kết với bảng genrebook
            stmt.setString(2, document.getPublisher()); // publisher là tên nhà xuất bản
            stmt.setString(3, document.getLgId()); // lgID liên kết với bảng language
            stmt.setString(4, document.getTitle()); // title là tên sách
            stmt.setString(5, document.getAuthor()); // author là tên tác giả
            stmt.setInt(6, document.getQuantity()); // quantity là số lượng sách
            stmt.setInt(7, document.getAvailableCopies()); // availableCopies là số bản sao sẵn có
            stmt.setDate(8, Date.valueOf(document.getAddDate())); // addDate là ngày thêm sách
            stmt.setBigDecimal(9, java.math.BigDecimal.valueOf(document.getPrice())); // price là giá sách
            stmt.setString(10, document.getDocumentID()); // bookId là ID của sách để xác định bản ghi cần cập nhật

            int rowsUpdated = stmt.executeUpdate(); // Thực thi lệnh và lấy số dòng đã cập nhật
            return rowsUpdated;
        } catch (SQLException e) {
            e.printStackTrace(); // Xử lý ngoại lệ SQL
        }
        return 0; // Trả về 0 nếu cập nhật thất bại
    }

}
