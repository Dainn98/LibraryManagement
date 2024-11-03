package library.management.ui.test;

import library.management.ui.DAO.DocumentDAO;
import library.management.ui.entity.Document;

public class TestDocument {
    public static void addDocument() {
        String genreId = "GEN2"; // Đặt tên là genreId để phù hợp với genrId trong cơ sở dữ liệu
        String publisher = "publisher";
        String lgId = "LANG4"; // Đảm bảo rằng lgId là int theo đúng cấu trúc cơ sở dữ liệu
        String title = "doremon";
        String author = "thien";
        int quantity = 12;
        int availableCopies = 12;
        String addDate = "2024-10-20"; // Định dạng ngày theo chuẩn YYYY-MM-DD
        double price = 12.0;

        // Tạo đối tượng Document với các tham số
        Document document = new Document(genreId, publisher, lgId, title, author, quantity, availableCopies, addDate, price);

        // Gọi phương thức thêm sách từ DocumentManagement
        if (DocumentDAO.getInstance().add(document) > 0) {
            System.out.println("Thêm sách thành công!");
        } else {
            System.out.println("Thêm sách thất bại!");
        }
    }

    public static void deleteDocument() {
        Document document = new Document("DOC11");
        if (DocumentDAO.getInstance().delete(document) > 0) {
            System.out.println("Xóa sách thành công");
        } else {
            System.out.println("Xóa sách thất bại");
        }
    }

    public static void updateDocument() {
        String DocumentId = "DOC11";
        String genreId = "GEN3";
        String publisher = "new publisher";
        String lgId = "LANG5";
        String title = "Updated Title";
        String author = "Updated Author";
        int quantity = 20;
        int availableCopies = 15;
        String addDate = "2024-11-01";
        double price = 15.0;

        Document document = new Document(DocumentId, genreId, publisher, lgId, title, author, quantity, availableCopies, addDate, price);

        // Gọi phương thức cập nhật từ DocumentDAO
        if (DocumentDAO.getInstance().update(document) > 0) {
            System.out.println("Cập nhật sách thành công!");
        } else {
            System.out.println("Cập nhật sách thất bại!");
        }
    }


    public static void main(String[] args) {
        addDocument();
        updateDocument();
        deleteDocument();
    }
}
