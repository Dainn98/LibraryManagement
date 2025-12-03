package library.management.ui.controllers.manager;

import static library.management.alert.AlertMaker.showAlertConfirmation;
import static library.management.alert.AlertMaker.showAlertInformation;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import library.management.data.DAO.DocumentDAO;
import library.management.data.DAO.LoanDAO;
import library.management.data.DAO.UserDAO;
import library.management.data.entity.Document;
import library.management.data.entity.Loan;
import library.management.data.entity.User;

/**
 * The DocumentManagementController class manages the interaction between the main application and
 * the functionality related to document management. It includes operations for initializing
 * document management, handling user and document searches, and processing document issues.
 */
public class DocumentManagementController extends ManagerSubController {

  /**
   * Defines the maximum number of suggestions that can be displayed in the suggestion menu. This
   * limit is used to restrict the number of autocomplete suggestions shown to the user in order to
   * enhance performance and user experience within the document management system.
   */
  private static final int MAX_SUGGESTIONS = 5;
  /**
   * Holds a list of string suggestions used for auto-completion features in the document management
   * system. The suggestions are observable, allowing UI components to automatically update when the
   * list changes. Typically updated based on user input to provide relevant suggestions such as
   * usernames or ISBNs.
   */
  private final ObservableList<String> Suggestions = FXCollections.observableArrayList();
  /**
   * The suggestionMenu is a private instance of ContextMenu used within the
   * DocumentManagementController class to display suggestion options for user input fields.
   * <p>
   * This menu typically displays a list of suggestions dynamically generated based on user input,
   * such as user names or ISBNs, to assist the user in selecting the correct option without having
   * to type the entire name or number. It is utilized in methods like updateUserNameSuggestions and
   * updateISBNSuggestions for better user interaction in the user interface.
   */
  private ContextMenu suggestionMenu;
  /**
   * Represents the current user in the Document Management System. It is used to handle and manage
   * user-related actions and information within the DocumentManagementController.
   */
  private User user;
  /**
   * Represents the current document being managed within the DocumentManagementController. This
   * variable is used to store and manipulate the document data during various operations such as
   * search and issue handling within the controller.
   */
  private Document document;


  /**
   * Constructs a DocumentManagementController with a specified MainController.
   *
   * @param controller the MainController instance used to manage and coordinate interactions
   *                   between different parts of the application. This controller serves as a
   *                   central point of access for shared resources and actions.
   */
  public DocumentManagementController(MainController controller) {
    this.controller = controller;
  }

  /**
   * Initializes the document management system by setting up autocomplete functionalities. This
   * method configures user interface elements to provide suggestions for user names and ISBN
   * numbers as users type in the relevant fields.
   * <p>
   * The initialization ensures that user interactions with these fields are enhanced with
   * autoComplete features, improving the efficiency of document management tasks.
   * <p>
   * It specifically calls:
   * - {@link #initializeAutoCompleteUserName()} to enable suggestions for user names.
   * - {@link #initializeAutoCompleteISBN()} to enable suggestions for ISBN numbers.
   */
  public void initDocumentManagement() {
    initializeAutoCompleteUserName();
    initializeAutoCompleteISBN();
  }

  /**
   * Initializes the autocomplete feature for the user name input field. Sets up an event filter on
   * the user ID input field to detect key releases. Updates and shows autocomplete suggestions as
   * the user types. If the input field is empty, the suggestions menu is hidden.
   */
  private void initializeAutoCompleteUserName() {
    suggestionMenu = new ContextMenu();
    suggestionMenu.setAutoHide(true);
    controller.userIDIssue.addEventFilter(KeyEvent.KEY_RELEASED, _ -> {
      String query = controller.userIDIssue.getText().trim();
      if (query.isEmpty()) {
        suggestionMenu.hide();
      } else {
        updateUserNameSuggestions(query);
      }
    });
  }

  /**
   * Updates the username suggestions based on the provided query. It retrieves the list of approved
   * usernames from the UserDAO and updates the suggestion menu with the obtained suggestions. The
   * suggestion menu is displayed right below the user ID input field if there are suggestions.
   *
   * @param query the query string used to search for approved usernames that match or are similar
   *              to the input.
   */
  private void updateUserNameSuggestions(String query) {
    List<String> suggestions = UserDAO.getInstance().searchApprovedUserNames(query);
    Suggestions.setAll(suggestions);
    suggestionMenu.getItems().clear();
    for (String suggestion : suggestions) {
      MenuItem item = new MenuItem(suggestion);
      item.setOnAction(_ -> {
        controller.userIDIssue.setText(suggestion);
        suggestionMenu.hide();
        handleSearchUserInformation();
      });
      suggestionMenu.getItems().add(item);
    }
    if (!Suggestions.isEmpty()) {
      double screenX = controller.userIDIssue.localToScreen(0, 0).getX();
      double screenY =
          controller.userIDIssue.localToScreen(0, 0).getY() + controller.userIDIssue.getHeight();
      suggestionMenu.hide();
      suggestionMenu.show(controller.userIDIssue, screenX, screenY);
    } else {
      suggestionMenu.hide();
    }
  }

  /**
   * Initializes the auto-complete feature for the ISBN input field. The method sets up a context
   * menu to display suggestions as the user types in the ISBN field. The context menu is populated
   * based on the current text in the ISBN input field every time a key is released. If the input
   * field is empty, the suggestion menu will be hidden. Otherwise, it triggers the update of ISBN
   * suggestions.
   * <p>
   * The suggestion list is displayed as a drop-down using a context menu attached to the input
   * field. The menu automatically hides when not in focus. The method listens for keyboard events
   * specifically on the ISBN field of the document controller, and updates suggestions dynamically
   * as the user input changes.
   */
  private void initializeAutoCompleteISBN() {
    suggestionMenu = new ContextMenu();
    suggestionMenu.setAutoHide(true);
    controller.docISBNIssue.addEventFilter(KeyEvent.KEY_RELEASED, _ -> {
      String query = controller.docISBNIssue.getText().trim();
      if (query.isEmpty()) {
        suggestionMenu.hide();
      } else {
        updateISBNSuggestions(query);
      }
    });
  }

  /**
   * Updates the ISBN suggestions based on the provided query. It retrieves relevant ISBN
   * suggestions from the database and populates the suggestion menu with these options. When an
   * item is selected from the menu, it updates the relevant text field and performs a document
   * information search.
   *
   * @param query the input string used to search and filter the ISBN suggestions.
   */
  private void updateISBNSuggestions(String query) {
    List<String> suggestions = DocumentDAO.getInstance()
        .searchISBNByKeyword(query, MAX_SUGGESTIONS);
    Suggestions.setAll(suggestions);
    suggestionMenu.getItems().clear();
    for (String suggestion : suggestions) {
      MenuItem item = new MenuItem(suggestion);
      item.setOnAction(_ -> {
        controller.docISBNIssue.setText(suggestion);
        suggestionMenu.hide();
        handleSearchDocInformation();
      });
      suggestionMenu.getItems().add(item);
    }
    if (!Suggestions.isEmpty()) {
      double screenX = controller.docISBNIssue.localToScreen(0, 0).getX();
      double screenY =
          controller.docISBNIssue.localToScreen(0, 0).getY() + controller.docISBNIssue.getHeight();
      suggestionMenu.hide();
      suggestionMenu.show(controller.docISBNIssue, screenX, screenY);
    } else {
      suggestionMenu.hide();
    }
  }

  /**
   * Searches for a user with an exact name match based on the input from the user ID text field.
   * <p>
   * This method retrieves user information if a user with the specified name exists and updates the
   * related fields in the UI with the user's information. If no such user is found, it displays an
   * alert with an appropriate message. The suggestion menu is hidden upon a successful search.
   * <p>
   * The method utilizes UserDAO to perform the search operation and accesses various UI elements
   * (e.g., user ID input, suggestion menu, user information fields) through the controller
   * instance.
   */
  public void handleSearchUserInformation() {
    user = UserDAO.getInstance().searchApprovedUserByExactName(controller.userIDIssue.getText());
    if (user == null) {
      showAlertInformation("Can not find user",
          "There is no user have user name:" + controller.userIDIssue.getText());
    } else {
      suggestionMenu.hide();
      controller.userNameInfo.setText(user.getUserName());
      controller.emailInfo.setText(user.getEmail());
      controller.phoneNumberInfo.setText(user.getPhoneNumber());
    }
  }

  /**
   * Handles the search for document information based on the ISBN. This method queries the document
   * database using the ISBN provided by the user through the controller. If the document is found,
   * it populates the related information fields in the user interface, such as title, author,
   * publisher, price, and availability. If the document is not found, it displays an alert message
   * informing the user that no document with the specified ISBN is available.
   * <p>
   * This method also manages the visibility of suggestion menus and updates the availability status
   * based on the document's availability state.
   */
  public void handleSearchDocInformation() {
    document = DocumentDAO.getInstance().searchDocumentByISBN(controller.docISBNIssue.getText());
    if (document == null) {
      showAlertInformation("Can not find document",
          "There is no document have ISBN:" + controller.docISBNIssue.getText());
    } else {
      suggestionMenu.hide();
      controller.docTitleInfo.setText(document.getTitle());
      controller.docAuthorInfo.setText(document.getTitle());
      controller.docPublisherInfo.setText(document.getPublisher());
      controller.price.setText(String.valueOf(document.getPrice()));
      if (!Objects.equals(document.getAvailability(), "available")) {
        controller.availability.setText("Not available");
      } else {
        controller.availability.setText(String.valueOf(document.getAvailableCopies()));
      }
    }
  }

  /**
   * Handles the action of canceling an issue operation within the document management system.
   * <p>
   * This method resets the text of various UI components related to user and document information
   * to their default labels or empty states. It effectively clears the input fields and labels,
   * reverting the interface to its initial state before any user or document details were entered
   * or manipulated. The method also sets the internal references to the current user and document
   * to null, indicating no active user or document selection.
   * <p>
   * Specifically, the following UI components are reset:
   * - User information: name, email, phone number.
   * - Document information: title, author, publisher, price, and availability.
   * - User and document identifiers for issuing, and the quantity field are cleared.
   */
  public void handleCancelIssue() {
    controller.userNameInfo.setText("User Name");
    controller.emailInfo.setText("Email Address");
    controller.phoneNumberInfo.setText("Phone Number");
    controller.docTitleInfo.setText("Document Title");
    controller.docAuthorInfo.setText("Document Author");
    controller.docPublisherInfo.setText("Document Publisher");
    controller.price.setText("Price");
    controller.availability.setText("Availability");
    controller.userIDIssue.setText("");
    controller.docISBNIssue.setText("");
    controller.quantityDoc.setText("");
    user = null;
    document = null;
  }

  /**
   * Handles the submission of a document issue request by validating the input data and updating
   * the loan records if the input data is valid. This method performs several checks:
   * 1. Ensures that the user is valid and non-null.
   * 2. Ensures that the document is valid and non-null.
   * 3. Ensures that the document is available for lending.
   * 4. Validates that the quantity entered is a valid integer and within the permissible range.
   * <p>
   * If any of these checks fail, an informational alert is shown to the user with the relevant
   * error message. If all checks pass, asks the user for confirmation to proceed with lending the
   * document. Upon confirmation, creates a Loan object, updates the loan records, decreases the
   * available copies of the document, and displays a success message.
   */
  public void handleSubmitIssueDoc() {
    if (user == null) {
      showAlertInformation("Invalid User", "Please enter a valid user!");
      return;
    }
    if (document == null) {
      showAlertInformation("Invalid Document", "Please enter a valid document!");
      return;
    }
    if (controller.availability.getText().equals("Not available")) {
      showAlertInformation("Invalid Document", "This document is not available!");
      return;
    }
    if (controller.quantityDoc.getText().isEmpty() || !isStringAnInteger(
        controller.quantityDoc.getText())) {
      showAlertInformation("Invalid Quantity", "Please enter a valid quantity!");
      return;
    }
    int borrowedQuantity = Integer.parseInt(controller.quantityDoc.getText());
    if (borrowedQuantity <= 0 || borrowedQuantity > Integer.parseInt(
        controller.availability.getText())) {
      showAlertInformation("Invalid Quantity", "Please enter a valid quantity!");
      return;
    }
    Optional<ButtonType> result = showAlertConfirmation("Lend document",
        "Are you sure you want to lend the document?");
    if (result.isPresent() && result.get() == ButtonType.OK) {
      Loan loan = new Loan(user.getUserName(), document.getIntDocumentID(), borrowedQuantity,
          borrowedQuantity * document.getPrice());
      LoanDAO.getInstance().add(loan);
      DocumentDAO.getInstance()
          .decreaseAvailableCopies(document.getIntDocumentID(), borrowedQuantity);
      showAlertInformation("", "Borrow successfully!");
    }
  }

  /**
   * Determines whether the given string can be parsed as an integer.
   *
   * @param str the string to be checked
   * @return true if the string can be parsed as an integer, false otherwise
   */
  public boolean isStringAnInteger(String str) {
    if (str == null || str.isEmpty()) {
      return false;
    }
    try {
      Integer.parseInt(str);
      return true;
    } catch (NumberFormatException e) {
      return false;
    }
  }

  public static class UserController {

    private final MainController controller;
    private final ObservableList<User> list = FXCollections.observableArrayList();
    private final ObservableList<BooleanProperty> checkBoxStatusList = FXCollections.observableArrayList();

    public UserController(MainController controller) {
      this.controller = controller;
    }

    public void initUsersView() {
      controller.userNameUserView.setCellValueFactory(new PropertyValueFactory<>("userName"));
      controller.userPhoneUserView.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
      controller.userEmailUserView.setCellValueFactory(new PropertyValueFactory<>("email"));
      controller.checkUserView.setCellValueFactory(cellData -> {
        int index = controller.userView.getItems().indexOf(cellData.getValue());
        return checkBoxStatusList.get(index);
      });
      controller.checkUserView.setCellFactory(
          CheckBoxTableCell.forTableColumn(controller.checkUserView));
      initFilterComboBox();
    }

    private void initFilterComboBox() {
      ObservableList<String> userFilters = FXCollections.observableArrayList("All", "ID", "Name",
          "Phone Number", "Email");
      controller.userFilterComboBox.setItems(userFilters);
      controller.userFilterComboBox.setValue("All");
    }

    public void loadUserViewData() {
      list.clear();
      list.addAll(UserDAO.getInstance().getAllApprovedUsers());
      controller.userView.setItems(list);
      initializeCheckBox();
    }

    public void fetchUserDetails() {
      User user = controller.userView.getSelectionModel().getSelectedItem();
      if (user != null) {
        controller.userNameField.setText(user.getUserName());
        controller.userPhoneField.setText(user.getPhoneNumber());
        controller.userEmailField.setText(user.getEmail());
      }
    }

    public void handleCancelUserButton() {
      controller.userNameField.setText("");
      controller.userPhoneField.setText("");
      controller.userEmailField.setText("");
    }

    public void checkAllUsers() {
      boolean isSelected = controller.checkAllUsersView.isSelected();
      for (BooleanProperty checkBoxStatus : checkBoxStatusList) {
        checkBoxStatus.set(isSelected);
      }
    }

    private void initializeCheckBox() {
      checkBoxStatusList.clear();
      for (int i = 0; i < list.size(); i++) {
        checkBoxStatusList.add(new SimpleBooleanProperty(false));
      }
      for (BooleanProperty checkBoxStatus : checkBoxStatusList) {
        checkBoxStatus.addListener((observable, oldValue, newValue) -> {
          if (!newValue) {
            controller.checkAllUsersView.setSelected(false);
          } else {
            boolean allSelected = checkBoxStatusList.stream().allMatch(BooleanProperty::get);
            if (allSelected) {
              controller.checkAllUsersView.setSelected(true);
            }
          }
        });
      }
    }


    public void searchUserDetails() {
      String filterCriteria = controller.userFilterComboBox.getValue();
      String searchText = controller.searchUserField.getText().trim().toLowerCase();
      list.clear();
      switch (filterCriteria) {
        case "Name":
          list.addAll(UserDAO.getInstance().searchApprovedUserByName(searchText));
          break;
        case "Phone Number":
          list.addAll(UserDAO.getInstance().searchApprovedUserByPhoneNumber(searchText));
          break;
        case "Email":
          list.addAll(UserDAO.getInstance().searchApprovedUserByEmail(searchText));
          break;
        default:
          list.addAll(UserDAO.getInstance().searchApprovedUsersByKeyword(searchText));
      }
      controller.userView.setItems(list);
      initializeCheckBox();
    }

    public void deleteUsersRecord() {
      Optional<ButtonType> result = showAlertConfirmation(
          "Delete user",
          "Are you sure you want to delete these users?");
      if (result.isPresent() && result.get() == ButtonType.OK) {
        for (int i = 0; i < list.size(); i++) {
          if (checkBoxStatusList.get(i).getValue()) {
            UserDAO.getInstance().delete(list.get(i));
          }
        }
        loadUserViewData();
        handleCancelUserButton();
      }
    }

    public void deleteOneUserRecord() {
      Optional<ButtonType> result = showAlertConfirmation(
          "Delete user",
          "Are you sure you want to delete this user?");
      if (result.isPresent() && result.get() == ButtonType.OK) {
        User user = controller.userView.getSelectionModel().getSelectedItem();
        UserDAO.getInstance().delete(user);
        loadUserViewData();
        handleCancelUserButton();
      }
    }

    public void handleSaveUserButton() {
      Optional<ButtonType> result = showAlertConfirmation(
          "Update user",
          "Are you sure you want to change user's information?");
      if (result.isPresent() && result.get() == ButtonType.OK) {
        User changedUser = new User(controller.userView.getSelectionModel().getSelectedItem());
        changedUser.setUserName(controller.userNameField.getText());
        changedUser.setPhoneNumber(controller.userPhoneField.getText());
        changedUser.setEmail(controller.userEmailField.getText());
        if (UserDAO.getInstance().doesUserNameExist(changedUser.getUserName())) {
          showAlertInformation("Update user information fail!", "This user already exists.");
        } else {
          UserDAO.getInstance().update(changedUser);
          showAlertInformation("Update user information!", "User updated successfully.");
          handleCancelUserButton();
          loadUserViewData();
        }
      }
    }
  }
}
