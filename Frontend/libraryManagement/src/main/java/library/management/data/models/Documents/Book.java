package library.management.data.models.Documents;

import java.util.HashSet;

/**
 * Represents a book in the library management system.
 */
public class Book extends Document {

  private String isbn;
  private String genre;

  /**
   * Default constructor initializing the book with default values.
   */
  public Book() {
    super();
    this.isbn = "UNKNOWN";
    this.genre = "UNKNOWN";
  }

  /**
   * Constructor initializing the book with specified values.
   *
   * @param id              the unique identifier of the book
   * @param title           the title of the book
   * @param type            the types associated with the book
   * @param author          the authors of the book
   * @param tag             the tags associated with the book
   * @param availableCopies the number of available copies of the book
   * @param isbn            the ISBN of the book
   * @param genre           the genre of the book
   */
  public Book(String id, String title, HashSet<String> type, HashSet<String> author,
      HashSet<String> tag, int availableCopies, String isbn, String genre) {
    super(id, title, type, author, tag, availableCopies);
    this.isbn = isbn != null ? isbn : "UNKNOWN";
    this.genre = genre != null ? genre : "UNKNOWN";
  }

  /**
   * Constructor initializing the book with specified values.
   *
   * @param title           the title of the book
   * @param type            the types associated with the book
   * @param author          the authors of the book
   * @param tag             the tags associated with the book
   * @param availableCopies the number of available copies of the book
   * @param isbn            the ISBN of the book
   * @param genre           the genre of the book
   */
  public Book(String title, HashSet<String> type, HashSet<String> author, HashSet<String> tag,
      int availableCopies, String isbn, String genre) {
    super(title, type, author, tag, availableCopies);
    this.isbn = isbn != null ? isbn : "UNKNOWN";
    this.genre = genre != null ? genre : "UNKNOWN";
  }

  /**
   * Gets the ISBN of the book.
   *
   * @return the ISBN of the book
   */
  public String getIsbn() {
    return isbn;
  }

  /**
   * Sets the ISBN of the book.
   *
   * @param isbn the new ISBN of the book
   */
  public void setIsbn(String isbn) {
    this.isbn = isbn;
  }

  /**
   * Gets the genre of the book.
   *
   * @return the genre of the book
   */
  public String getGenre() {
    return genre;
  }

  /**
   * Sets the genre of the book.
   *
   * @param genre the new genre of the book
   */
  public void setGenre(String genre) {
    this.genre = genre;
  }

  /**
   * Updates the current book with the values from another document.
   *
   * @param other the document to update from
   */
  @Override
  public void update(Document other) {
    super.update(other);
    if (other instanceof Book) {
      this.isbn = ((Book) other).isbn;
      this.genre = ((Book) other).genre;
    }
  }
}