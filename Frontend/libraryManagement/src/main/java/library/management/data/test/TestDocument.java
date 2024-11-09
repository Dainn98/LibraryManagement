package library.management.data.test;

import library.management.data.DAO.DocumentDAO;
import library.management.data.entity.Document;

import java.util.List;

public class TestDocument {

    public static void addDocument() {
        String genreId = "GEN2";
        String publisher = "publisher";
        String lgId = "LANG4";
        String title = "doremon";
        String author = "thien";
        String isbn = "9783161489425";
        int quantity = 12;
        int availableCopies = 12;
        String addDate = "2024-10-20";
        double price = 12.0;
        String description = "A famous manga series about a robotic cat named Doraemon and his adventures.";

        Document document = new Document(genreId, publisher, lgId, title, author, isbn, quantity, availableCopies, addDate, price, description);

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
        String documentId = "DOC11";
        String genreId = "GEN3";
        String publisher = "new publisher";
        String lgId = "LANG5";
        String title = "Updated Title";
        String author = "Updated Author";
        String isbn = "9783161484101";
        int quantity = 20;
        int availableCopies = 15;
        String addDate = "2024-11-01";
        double price = 15.0;
        String description = "Updated description for the document.";

        Document document = new Document(documentId, genreId, publisher, lgId, title, author, isbn, quantity, availableCopies, addDate, price, description);

        if (DocumentDAO.getInstance().update(document) > 0) {
            System.out.println("Cập nhật sách thành công!");
        } else {
            System.out.println("Cập nhật sách thất bại!");
        }
    }

    public static void getAllDocuments() {
        List<Document> list = DocumentDAO.getInstance().getBookList();
        for (Document document : list) {
            System.out.println("Document ID: " + document.getDocumentID());
            System.out.println("Title: " + document.getTitle());
            System.out.println("Author: " + document.getAuthor());
            System.out.println("ISBN: " + document.getIsbn());
            System.out.println("Publisher: " + document.getPublisher());
            System.out.println("Genre ID: " + document.getGenrId());
            System.out.println("Language ID: " + document.getLgId());
            System.out.println("Quantity: " + document.getQuantity());
            System.out.println("Available Copies: " + document.getAvailableCopies());
            System.out.println("Add Date: " + document.getAddDate());
            System.out.println("Price: " + document.getPrice());
            System.out.println("Description: " + document.getDescription());
            System.out.println("-----------");
        }
    }

    public static void main(String[] args) {
        addDocument();
        updateDocument();
        deleteDocument();
    }
}
