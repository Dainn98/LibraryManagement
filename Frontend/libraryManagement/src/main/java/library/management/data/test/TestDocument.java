package library.management.data.test;

import library.management.data.DAO.DocumentDAO;
import library.management.data.entity.Document;

import java.util.List;

public class TestDocument {

    public static void addDocument() {
        String categoryID = "CAT2";
        String publisher = "publisher";
        String lgID = "LANG4";
        String title = "Doraemon";
        String author = "Fujiko F. Fujio";
        String isbn = "9783161489425";
        int quantity = 12;
        int availableCopies = 12;
        String addDate = "2024-10-20 00:00:00";
        double price = 12.0;
        String description = "A famous manga series about a robotic cat named Doraemon and his adventures.";
        String url = "https://example.com/doraemon";
        String image = "https://example.com/doraemon.jpg";

        Document document = new Document(categoryID, publisher, lgID, title, author, isbn, quantity, availableCopies, addDate, price, description, url, image);

        if (DocumentDAO.getInstance().add(document) > 0) {
            System.out.println("Thêm sách thành công!");
        } else {
            System.out.println("Thêm sách thất bại!");
        }
    }

    public static void deleteDocument() {
        Document document = new Document("DOC20");
        if (DocumentDAO.getInstance().delete(document) > 0) {
            System.out.println("Xóa sách thành công");
        } else {
            System.out.println("Xóa sách thất bại");
        }
    }

    public static void updateDocument() {
        String documentID = "DOC20";
        String categoryID = "CAT3";
        String publisher = "Updated Publisher";
        String lgID = "LANG5";
        String title = "Updated Title";
        String author = "Updated Author";
        String isbn = "9783161484101";
        int quantity = 20;
        int availableCopies = 15;
        String addDate = "2024-11-01 00:00:00";
        double price = 15.0;
        String description = "Updated description for the document.";
        String url = "https://example.com/updated-document";
        String image = "https://example.com/updated-image.jpg";

        Document document = new Document(documentID, categoryID, publisher, lgID, title, author, isbn, quantity, availableCopies, addDate, price, description, url, image);

        if (DocumentDAO.getInstance().update(document) > 0) {
            System.out.println("Cập nhật sách thành công!");
        } else {
            System.out.println("Cập nhật sách thất bại!");
        }
    }

    public static void getAllDocuments() {
        List<Document> list = DocumentDAO.getInstance().getBookList();
        for (Document document : list) {
            System.out.println("Document ID: " + document.getStringDocumentID());
            System.out.println("Title: " + document.getTitle());
            System.out.println("Author: " + document.getAuthor());
            System.out.println("ISBN: " + document.getIsbn());
            System.out.println("Publisher: " + document.getPublisher());
            System.out.println("Category ID: " + document.getStringCategoryID());
            System.out.println("Language ID: " + document.getStringLgID());
            System.out.println("Quantity: " + document.getQuantity());
            System.out.println("Available Copies: " + document.getAvailableCopies());
            System.out.println("Add Date: " + document.getAddDate());
            System.out.println("Price: " + document.getPrice());
            System.out.println("Description: " + document.getDescription());
            System.out.println("URL: " + document.getUrl());
            System.out.println("Image: " + document.getImage());
            System.out.println("-----------");
        }
    }

    public static void main(String[] args) {
//        addDocument();
//        updateDocument();
//        deleteDocument();
        getAllDocuments();
    }
}
