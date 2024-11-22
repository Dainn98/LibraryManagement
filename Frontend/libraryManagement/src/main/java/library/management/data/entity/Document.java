package library.management.data.entity;

import library.management.data.DAO.CategoryDAO;
import library.management.data.DAO.LanguageDAO;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Document {
    private int documentID;
    private int categoryID;
    private String publisher;
    private int lgID;
    private String title;
    private String author;
    private String isbn;
    private int quantity;
    private int availableCopies;
    private LocalDateTime addDate;
    private double price;
    private String description;
    private String url;
    private String image;
    private String availability;

    public static int NOTAVALABLETOBOROW = 0;
    public static int NOTENOUGHCOPIES = -1;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    public Document() {
        super();
    }

    public Document(String documentID) {
        super();
        this.documentID = parseId(documentID, "DOC");
    }

    public Document(String documentID, String categoryID, String publisher, String lgId, String title,
                    String author, String isbn, int quantity, int availableCopies, String addDate, double price, String description, String url, String image, String availability) {
        this.documentID = parseId(documentID, "DOC");
        this.categoryID = parseId(categoryID, "CAT");
        this.publisher = publisher;
        this.lgID = parseId(lgId, "LANG");
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.quantity = quantity;
        this.availableCopies = availableCopies;
        this.addDate = LocalDateTime.parse(addDate, DATE_FORMATTER);
        this.price = price;
        this.description = description;
        this.url = url;
        this.image = image;
        this.availability = availability;
    }

    public Document(int categoryID, String publisher, int lgId, String title,
                    String author, String isbn, String description, String url, String image) {
        this.categoryID = categoryID;
        this.publisher = publisher;
        this.lgID = lgId;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.description = description;
        this.url = url;
        this.image = image;
    }

    public Document(String categoryID, String publisher, String lgID, String title,
                    String author, String isbn, int quantity, int availableCopies, String addDate, double price, String description, String url, String image, String availability) {
        super();
        this.categoryID = parseId(categoryID, "CAT");
        this.publisher = publisher;
        this.lgID = parseId(lgID, "LANG");
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.quantity = quantity;
        this.availableCopies = availableCopies;
        this.addDate = LocalDateTime.parse(addDate, DATE_FORMATTER);
        this.price = price;
        this.description = description;
        this.url = url;
        this.image = image;
        this.availability = availability;
    }

    public Document(Document document) {
        super();
        this.documentID = document.documentID;
        this.categoryID = document.categoryID;
        this.publisher = document.publisher;
        this.lgID = document.lgID;
        this.title = document.title;
        this.author = document.author;
        this.isbn = document.isbn;
        this.quantity = document.quantity;
        this.availableCopies = document.availableCopies;
        this.addDate = document.addDate;
        this.price = document.price;
        this.description = document.description;
        this.url = document.url;
        this.image = document.image;
        this.availability = document.availability;
    }

    private int parseId(String input, String prefix) {
        if (input != null && input.startsWith(prefix)) {
            return Integer.parseInt(input.substring(prefix.length()));
        } else {
            throw new IllegalArgumentException("Invalid format for ID with prefix " + prefix);
        }
    }

    public String getDocumentID() {
        return String.format("DOC%d", documentID);
    }

    public int getIntDocumentID() {
        return documentID;
    }

    public void setDocumentID(String documentID) {
        this.documentID = parseId(documentID, "DOC");
    }

    public String getStringCategoryID() {
        return String.format("CAT%d", categoryID);
    }

    public int getIntCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = parseId(categoryID, "CAT");
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getStringLgID() {
        return String.format("LANG%d", lgID);
    }

    public int getIntLgID() {
        return lgID;
    }

    public void setLgID(String lgID) {
        this.lgID = parseId(lgID, "LANG");
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getAvailableCopies() {
        return availableCopies;
    }

    public void setAvailableCopies(int availableCopies) {
        this.availableCopies = availableCopies;
    }

    public LocalDateTime getAddDate() {
        return addDate;
    }

    public void setAddDate(String addDate) {
        if (addDate == null || addDate.isEmpty()) {
            throw new IllegalArgumentException("addDate cannot be null or empty");
        }

        if (addDate.matches("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}")) {
            addDate += ":00"; // Thêm giây mặc định
        }

        try {
            this.addDate = LocalDateTime.parse(addDate, DATE_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format for addDate: " + addDate, e);
        }
    }


    public void setAddDate(LocalDateTime time) {
        this.addDate = time;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCategory() {
        return CategoryDAO.getInstance().getTagByID(getIntCategoryID());
    }

    public String getLanguage() {
        return LanguageDAO.getInstance().getLanguageName(this.getIntLgID());
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }
}
