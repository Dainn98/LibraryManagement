package library.management.entity;

public class Book {
    private int STT;
    private String bookId;
    private String genrId;
    private String publisher;
    private String lgId;
    private String title;
    private String author;
    private int quantity;
    private int availableCopies;
    private String addDate;
    private double price;

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getGenrId() {
        return genrId;
    }

    public void setGenrId(String genrId) {
        this.genrId = genrId;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getLgId() {
        return lgId;
    }

    public void setLgId(String lgId) {
        this.lgId = lgId;
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

    public String getAddDate() {
        return addDate;
    }

    public void setAddDate(String addDate) {
        this.addDate = addDate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getSTT() {
        return STT;
    }

    public void setSTT(int STT) {
        this.STT = STT;
    }

    public Book() {
        super();
    }

    public Book(String bookId) {
        super();
        this.bookId = bookId;
    }

    public Book(String bookId, String genrId, String publisher, String lgId, String title, String author, int quantity, int availableCopies, String addDate, double price) {
        this.bookId = bookId;
        this.genrId = genrId;
        this.publisher = publisher;
        this.lgId = lgId;
        this.title = title;
        this.author = author;
        this.quantity = quantity;
        this.availableCopies = availableCopies;
        this.addDate = addDate;
        this.price = price;
    }

    public Book(String genrId, String publisher, String lgId, String title, String author, int quantity, int availableCopies, String addDate, double price) {
        super();
        this.genrId = genrId;
        this.publisher = publisher;
        this.lgId = lgId;
        this.title = title;
        this.author = author;
        this.quantity = quantity;
        this.availableCopies = availableCopies;
        this.addDate = addDate;
        this.price = price;
    }
}
