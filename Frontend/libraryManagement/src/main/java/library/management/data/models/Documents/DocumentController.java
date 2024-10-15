package library.management.data.models.Documents;

import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * Controller class for managing documents in the library system.
 */
public class DocumentController {

  private ArrayList<Document> documentList = new ArrayList<>();

  /**
   * Adds a document to the document list.
   *
   * @param doc the document to be added
   */
  public void addDocument(Document doc) {
    documentList.add(doc);
    System.out.printf("Document added successfully! [%s] [%s] %s %s %s [%d]%n",
        doc.getId(), doc.getTitle(), doc.getAuthor(), doc.getTag(),
        doc.getType(), doc.getAvailableCopies());
  }

  /**
   * Removes a document from the document list by its ID.
   *
   * @param id the ID of the document to be removed
   */
  public void removeDocument(String id) {
    Optional<Document> documentToRemove = documentList.stream()
        .filter(document -> document.getId().equals(id)).findFirst();

    if (documentToRemove.isPresent()) {
      documentList.remove(documentToRemove.get());
      System.out.printf("Document [%s] [%s] removed successfully!",
          documentToRemove.get().getId(), documentToRemove.get().getTitle());
    } else {
      System.out.println("Document not found!");
    }
  }

  /**
   * Updates the information of an existing document.
   *
   * @param doc the document with updated information
   */
  public void updateDocument(Document doc) {
    Optional<Document> documentToUpdate = documentList.stream()
        .filter(document -> document.getTitle().equals(doc.getTitle()))
        .findFirst();

    if (documentToUpdate.isPresent()) {
      documentToUpdate.get().update(doc);
      System.out.printf("The information of the document: [%s] [%s] %s %s %s [%d]%n",
          doc.getId(), doc.getTitle(), doc.getAuthor(), doc.getTag(), doc.getType(),
          doc.getAvailableCopies());
    } else {
      System.out.println("Document not found!");
    }
  }

  /**
   * Finds a document based on a given predicate.
   *
   * @param predicate       the condition to match
   * @param notFoundMessage the message to display if the document is not found
   * @return the found document or null if not found
   */
  private Document findDocument(Predicate<Document> predicate, String notFoundMessage) {
    return documentList.stream()
        .filter(predicate)
        .findFirst()
        .orElseGet(() -> {
          System.out.println(notFoundMessage);
          return null;
        });
  }

  /**
   * Finds a document by its title.
   *
   * @param title the title of the document
   * @return the found document or null if not found
   */
  public Document findDocumentByTitle(String title) {
    return findDocument(doc -> doc.getTitle().equals(title), "Document " + title + " not found.");
  }

  /**
   * Finds a document by its ID.
   *
   * @param id the ID of the document
   * @return the found document or null if not found
   */
  public Document findDocumentById(String id) {
    return findDocument(doc -> doc.getId().equals(id), "Document " + id + " not found.");
  }

  /**
   * Finds a document by its author.
   *
   * @param author the author of the document
   * @return the found document or null if not found
   */
  public Document findDocumentByAuthor(String author) {
    return findDocument(doc -> doc.getAuthor().contains(author),
        "Document by " + author + " not found.");
  }

  /**
   * Displays the details of a document.
   *
   * @param doc the document to be displayed
   */
  public void displayDocument(Document doc) {
    System.out.printf("ID: %s%nTitle: %s%nType: %s%nTags: %s%nAuthor: %s%nAvailable Copies: %d%n",
        doc.getId(), doc.getTitle(), doc.getType(), doc.getTag(), doc.getAuthor(),
        doc.getAvailableCopies());
  }

  /**
   * Displays the details of all documents in the document list.
   */
  public void displayDocument() {
    documentList.forEach(this::displayDocument);
  }
}