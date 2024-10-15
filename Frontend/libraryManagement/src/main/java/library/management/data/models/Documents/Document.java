package library.management.data.models.Documents;

import java.util.HashSet;

public class Document {

  private String id;
  private String title;
  private int availableCopies;
  private HashSet<String> type;
  private HashSet<String> author;
  private HashSet<String> tag;

  /**
   * Default constructor.
   */
  public Document() {
    this("00000000", "manager", new HashSet<>(), new HashSet<>(), new HashSet<>(), 0);
  }

  /**
   * Constructor with all fields.
   *
   * @param id              The document's id.
   * @param title           The document's title.
   * @param type            The document's type.
   * @param author          The document's author.
   * @param tag             The document's tag.
   * @param availableCopies The document's available copies.
   */
  public Document(String id, String title, HashSet<String> type, HashSet<String> author,
      HashSet<String> tag, int availableCopies) {
    this.id = id != null ? id : "UNKNOWN";
    this.title = title != null ? title : "UNKNOWN";
    this.type = type != null ? type : new HashSet<>();
    this.author = author != null ? author : new HashSet<>();
    this.tag = tag != null ? tag : new HashSet<>();
    this.availableCopies = availableCopies;
  }

  /**
   * Copy constructor.
   *
   * @param doc The document to copy.
   */
  public Document(Document doc) {
    this(doc.id, doc.title, doc.type, doc.author, doc.tag, doc.availableCopies);
  }

  /**
   * Constructor with all fields except availableCopies.
   *
   * @param id     The document's id.
   * @param title  The document's title.
   * @param type   The document's type.
   * @param author The document's author.
   * @param tag    The document's tag.
   */
  public Document(String id, String title, HashSet<String> type, HashSet<String> author,
      HashSet<String> tag) {
    this(id, title, type, author, tag, 0);
  }

  /**
   * Constructor with all fields except tag.
   *
   * @param id     The document's id.
   * @param title  The document's title.
   * @param type   The document's type.
   * @param author The document's author.
   */
  public Document(String id, String title, HashSet<String> type, HashSet<String> author) {
    this(id, title, type, author, new HashSet<>(), 0);
  }

  /**
   * Constructor with all fields except id.
   *
   * @param title           The document's title.
   * @param type            The document's type.
   * @param author          The document's author.
   * @param tag             The document's tag.
   * @param availableCopies The document's available copies.
   */
  public Document(String title, HashSet<String> type, HashSet<String> author, HashSet<String> tag,
      int availableCopies) {
    this(null, title, type, author, tag, availableCopies);

  }

  /**
   * Constructor with all fields except id, availableCopies.
   *
   * @param title  The document's title.
   * @param type   The document's type.
   * @param author The document's author.
   * @param tag    The document's tag.
   */
  public Document(String title, HashSet<String> type, HashSet<String> author, HashSet<String> tag) {
    this(null, title, type, author, tag, 0);
  }

  /**
   * Gets the document's ID.
   *
   * @return the document's ID
   */
  public String getId() {
    return id;
  }

  /**
   * Sets the document's ID.
   *
   * @param id the new ID of the document
   */
  public void setId(String id) {
    this.id = id;
  }

  /**
   * Gets the document's title.
   *
   * @return the document's title
   */
  public String getTitle() {
    return title;
  }

  /**
   * Sets the document's title.
   *
   * @param title the new title of the document
   */
  public void setTitle(String title) {
    this.title = title;
  }

  /**
   * Gets the document's type.
   *
   * @return the document's type
   */
  public HashSet<String> getType() {
    return type;
  }

  /**
   * Sets the document's type.
   *
   * @param type the new type of the document
   */
  public void setType(HashSet<String> type) {
    this.type = type;
  }

  /**
   * Gets the document's tags.
   *
   * @return the document's tags
   */
  public HashSet<String> getTag() {
    return tag;
  }

  /**
   * Sets the document's tags.
   *
   * @param tag the new tags of the document
   */
  public void setTag(HashSet<String> tag) {
    this.tag = tag;
  }

  /**
   * Gets the document's authors.
   *
   * @return the document's authors
   */
  public HashSet<String> getAuthor() {
    return author;
  }

  /**
   * Sets the document's authors.
   *
   * @param author the new authors of the document
   */
  public void setAuthor(HashSet<String> author) {
    this.author = author;
  }

  /**
   * Gets the number of available copies of the document.
   *
   * @return the number of available copies
   */
  public int getAvailableCopies() {
    return availableCopies;
  }

  /**
   * Sets the number of available copies of the document.
   *
   * @param availableCopies the new number of available copies
   */
  public void setAvailableCopies(int availableCopies) {
    this.availableCopies = availableCopies;
  }

  /**
   * Update the document with the information from another document.
   *
   * @param other The document to update from.
   */
  public void update(Document other) {
    this.title = other.title;
    this.author = other.author;
    this.id = other.id;
    this.type = other.type;
    this.availableCopies = other.availableCopies;
  }
}
