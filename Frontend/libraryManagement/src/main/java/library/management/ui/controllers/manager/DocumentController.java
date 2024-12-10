package library.management.ui.controllers.manager;

import static library.management.alert.AlertMaker.showAlertConfirmation;

import java.util.Optional;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import library.management.data.DAO.DocumentDAO;
import library.management.data.entity.Document;

/**
 * The DocumentController class is responsible for managing and displaying a list of documents
 * within the application's user interface. It extends the functionality of ManagerSubController
 * and integrates with a MainController instance to manipulate and present document data.
 *
 * The class provides initialization of document view elements, loading and searching of document data,
 * management of selection checkboxes for batch operations, and facilitating document deletion.
 */
public class DocumentController extends ManagerSubController {

  /**
   * A list of Document objects that can be observed for changes.
   * This list is used to store and manage documents within the DocumentController.
   * It can be monitored by UI components for any modifications to dynamically update the user interface.
   */
  private final ObservableList<Document> list = FXCollections.observableArrayList();
  /**
   * Represents a list of BooleanProperty objects that track the status of checkboxes.
   *
   * This list is used to maintain an observable collection of checkbox states,
   * allowing the GUI elements to react to changes and update accordingly.
   * Typically associated with a user interface where multiple checkboxes
   * need to be monitored for changes in their states.
   *
   * The ObservableList ensures that any modifications to the list, such as
   * adding or removing elements, will be automatically reflected*/
  private final ObservableList<BooleanProperty> checkBoxStatusList = FXCollections.observableArrayList();

  /**
   * Constructs a new instance of DocumentController with the specified MainController.
   *
   * @param controller the MainController instance that this DocumentController will interact with
   */
  public DocumentController(MainController controller) {
    this.controller = controller;
  }

  /**
   * Initializes the document view table by setting up various columns with appropriate*/
  public void initDocumentView() {
    controller.checkDocView.setCellValueFactory(cellData -> {
      int index = controller.docView.getItems().indexOf(cellData.getValue());
      return checkBoxStatusList.get(index);
    });
    controller.checkDocView.setCellFactory(
        CheckBoxTableCell.forTableColumn(controller.checkDocView));
    controller.docIDDocView.setCellValueFactory(new PropertyValueFactory<>("documentID"));
    controller.docISBNDocView.setCellValueFactory(new PropertyValueFactory<>("isbn"));
    controller.docNameDocView.setCellValueFactory(new PropertyValueFactory<>("title"));
    controller.docAuthorDocView.setCellValueFactory(new PropertyValueFactory<>("author"));
    controller.docPublisherDocView.setCellValueFactory(new PropertyValueFactory<>("publisher"));
    controller.categoryDocView.setCellValueFactory(cellData ->
        new SimpleStringProperty(cellData.getValue().getCategory())
    );
    controller.quantityDocView.setCellValueFactory(new PropertyValueFactory<>("quantity"));
    controller.remainingDocsDocView.setCellValueFactory(
        new PropertyValueFactory<>("availableCopies"));
    controller.availabilityDocView.setCellValueFactory(new PropertyValueFactory<>("availability"));
    controller.availabilityDocView.setCellValueFactory(new PropertyValueFactory<>("availability"));
    controller.availabilityDocView.setCellFactory(_ -> new TableCell<>() {
      private final Button button = new Button();

      @Override
      protected void updateItem(String availability, boolean empty) {
        super.updateItem(availability, empty);

        if (empty || availability == null) {
          setGraphic(null);
          setText(null);
        } else {
          button.setText(availability.equals("available") ? "Available" : "Unavailable");
          button.setStyle(
              "-fx-background-color: " + (availability.equals("available") ? "green" : "red")
                  + "; -fx-text-fill: white;");

          button.setOnAction(_ -> {
            Document document = getTableRow().getItem();
            Optional<ButtonType> result = showAlertConfirmation(
                "Change availability of document",
                "Are you sure you want to change availability of this document: "
                    + document.getTitle() + "?");
            if (result.isPresent() && result.get() == ButtonType.OK) {
              document.setAvailability(
                  availability.equals("available") ? "unavailable" : "available");
              DocumentDAO.getInstance().update(document);
              controller.docView.refresh();
            }
          });

          setGraphic(button);
        }
      }
    });
    controller.allDocs.setText(String.valueOf(DocumentDAO.getInstance().getTotalQuantity()));
    controller.remainDocs.setText(
        String.valueOf(DocumentDAO.getInstance().getTotalAvailableCopies()));
  }

  /**
   * Loads document data into the controller's view. This method clears the current list of documents
   * and repopulates it by fetching a list of books from the DocumentDAO singleton instance. It then
   * sets the updated list to the document view's items. Additionally, it initializes the checkbox
   * elements in the view by calling the initializeCheckBox method.
   */
  public void loadDocumentData() {
    list.clear();
    list.addAll(DocumentDAO.getInstance().getBookList());
    controller.docView.setItems(list);
    initializeCheckBox();
  }

  /**
   * Handles the search functionality for the document text field within the document view.
   * This method clears the current list of documents and updates it with the search results
   * obtained from the DocumentDAO. It fetches the search term entered by the user in the document
   * text field, processes the search by converting the term to lowercase, and updates the document
   * view with the filtered list of documents. Additionally, it initializes the checkboxes related
   * to the documents in the view. This ensures that the document view reflects the current search
   * query and displays*/
  public void handleSearchDocTField() {
    list.clear();
    list.addAll(DocumentDAO.getInstance()
        .searchDocuments(controller.searchDocTField.getText().trim().toLowerCase()));
    controller.docView.setItems(list);
    initializeCheckBox();
  }

  /**
   * Initializes the state of checkboxes represented by a list of BooleanProperty objects.
   *
   * This method clears the existing checkbox status list and populates it with new
   * SimpleBooleanProperty instances set to false. It also adds a listener to each
   * BooleanProperty that ensures if any checkbox is deselected, a master checkbox
   * in the controller is also deselected.
   *
   **/
  private void initializeCheckBox() {
    this.checkBoxStatusList.clear();
    for (int i = 0; i < list.size(); i++) {
      this.checkBoxStatusList.add(new SimpleBooleanProperty(false));
    }
    for (BooleanProperty checkBoxStatus : checkBoxStatusList) {
      checkBoxStatus.addListener((_, _, newValue) -> {
        if (!newValue) {
          controller.checkAllDocsView.setSelected(false);
        }
      });
    }
  }

  /**
   * Updates the check box status for all documents based on the selection state
   * of the master check box in the view. If the master check box is selected,
   * all individual document check boxes will be set to selected, and vice versa.
   */
  public void checkAllDocs() {
    boolean isSelected = controller.checkAllDocsView.isSelected();
    for (BooleanProperty checkBoxStatus : checkBoxStatusList) {
      checkBoxStatus.set(isSelected);
    }
  }

  /**
   * Handles the deletion of documents when the delete hyperlink is activated.
   * This method prompts the user with a confirmation alert. If the user confirms
   * the deletion, it iterates over a list of documents and deletes those that are
   * selected (as indicated by the corresponding checkbox status). Post-deletion,
   * the document data is reloaded to reflect any changes.
   */
  public void handleDeleteDocHyperlink() {
    Optional<ButtonType> result = showAlertConfirmation(
        "Delete document",
        "Are you sure you want to delete these documents?");
    if (result.isPresent() && result.get() == ButtonType.OK) {
      for (int i = 0; i < list.size(); i++) {
        if (checkBoxStatusList.get(i).getValue()) {
          DocumentDAO.getInstance().delete(list.get(i));
        }
      }
      loadDocumentData();
    }
  }

  /**
   * Deletes the currently selected document from the system.
   *
   * This method retrieves the document currently selected by the user and prompts
   * a confirmation alert to ensure the user intends to delete the document.
   * If confirmed, the document is removed from the data store via the DocumentDAO.
   * After deletion, the document data is reloaded to reflect the current state.
   *
   * Preconditions:
   * - There must be a document*/
  public void DeleteBook() {
    Document document = controller.docView.getSelectionModel().getSelectedItem();
    if (document != null) {
      showAlertConfirmation("Delete document", "Are you sure you want to delete this document?");
      DocumentDAO.getInstance().delete(document);
    }
    loadDocumentData();
  }
}
