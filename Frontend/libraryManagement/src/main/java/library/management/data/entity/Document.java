package library.management.data.entity;

import library.management.data.DAO.CategoryDAO;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
    private boolean availability;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    public Document() {
        super();
    }

    public Document(String documentID) {
        super();
        this.documentID = parseId(documentID, "DOC");
    }

    public Document(String documentID, String categoryID, String publisher, String lgId, String title,
                    String author, String isbn, int quantity, int availableCopies, String addDate, double price, String description, String url, String image, boolean availability) {
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

    public Document(String categoryID, String publisher, String lgID, String title,
                    String author, String isbn, int quantity, int availableCopies, String addDate, double price, String description, String url, String image, boolean availability) {
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
        this.addDate = LocalDateTime.parse(addDate, DATE_FORMATTER);
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

    public boolean isAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }
}
