package library.management.ui.controllers.manager;

import static library.management.alert.AlertMaker.showAlertConfirmation;

import java.util.List;
import java.util.Optional;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import library.management.data.DAO.UserDAO;
import library.management.data.entity.User;

/**
 * The PendingApprovalsController class manages the pending approvals view within the application.
 * It extends the ManagerSubController and interacts with a main controller to initialize and manage
 * user interface components related to pending user approvals, such as checkboxes, approval buttons,
 * and various user information columns.
 *
 * It facilitates functionalities like loading pending approval data, setting up various table columns,
 * providing approval/disapproval options, and enabling filtering and searching capabilities based on
 * different criteria such as country or state.
 */
public class PendingApprovalsController extends ManagerSubController {

  /**
   * A list that holds users pending approval. This list is observable which
   * means it can be used in UI components that automatically update when
   * the contents of the list change.
   */
  private final ObservableList<User> list = FXCollections.observableArrayList();
  /**
   * Represents an observable list of BooleanProperty objects used for managing the status
   * of checkboxes within the PendingApprovalsController. This list is intended to be observed
   * for changes in state, allowing for dynamic updates and interactions with the checkbox
   * elements in the user interface. It is initialized as an ObservableList using
   * FXCollections.observableArrayList().
   */
  private final ObservableList<BooleanProperty> checkBoxStatusList = FXCollections.observableArrayList();
  /**
   * Represents a list of username suggestions for user input fields within the
   * PendingApprovalsController class. This list is observable and can dynamically
   * update the user interface components that subscribe to changes in the contained
   * data. It is used to provide a set of suggested usernames for auto-completion
   * or search purposes, helping improve user experience by offering relevant options
   * based on the current input context.
   */
  private final ObservableList<String> usernameSuggestions = FXCollections.observableArrayList();
  /**
   * Represents a context menu for providing suggestion features within the
   * PendingApprovalsController. This menu facilitates user interaction by
   * offering suggestions based on the current context, enhancing user
   * experience through dynamic and context-sensitive options. It is utilized
   * primarily in scenarios like auto-completion or offering related actions
   * to improve efficiency in user operations.
   */
  private ContextMenu suggestionMenu;

  /**
   * Constructs a PendingApprovalsController with the specified MainController.
   *
   * @param controller the main controller used to manage the pending approvals
   */
  public PendingApprovalsController(MainController controller) {
    this.controller = controller;
  }

  /**
   * Initializes the approvals view by setting up the table view and its columns with relevant data.
   * The table is set to be editable and each column is configured with appropriate cell factories
   * and value factories to display user approval data such as username, phone number, email,
   * country, state, and registered year. It also sets up checkboxes for approval status tracking.
   * This method also initializes additional functionalities including approve/disapprove buttons
   * and auto-complete features for efficient management of pending approvals.
   */
  public void initApprovalsView() {
    controller.approvalsTView.setEditable(true);
    controller.checkBoxApproval.setCellValueFactory(cellData -> {
      int index = controller.approvalsTView.getItems().indexOf(cellData.getValue());
      return checkBoxStatusList.get(index);
    });
    controller.checkBoxApproval.setCellFactory(
        CheckBoxTableCell.forTableColumn(controller.checkBoxApproval));
    controller.usernameApprovals.setCellValueFactory(new PropertyValueFactory<>("userName"));
    controller.phoneNumberApprovals.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
    controller.emailApprovals.setCellValueFactory(new PropertyValueFactory<>("email"));
    controller.countryApprovals.setCellValueFactory(new PropertyValueFactory<>("country"));
    controller.stateApprovals.setCellValueFactory(new PropertyValueFactory<>("state"));
    controller.yearApprovals.setCellValueFactory(cellData ->
        new SimpleStringProperty(cellData.getValue().getRegisteredYear())
    );
    initApproveButtonsColumn();
    initializeCheckCountry();
    initializeCheckState();
    initializeCheckYear();
    initializeAutoComplete();
  }

  /**
   * Initializes the approve buttons column in a table view.
   * <p>
   * This method sets up a cell factory for the approval buttons column,
   * adding "Approve" and "Disapprove" buttons to each cell. The buttons
   * allow users to approve or disapprove user account requests.
   * <p>
   * Each button has its own event handler:
   * If the "Approve" button is clicked, it prompts the user with a confirmation dialog.
   * Upon confirmation, it invokes the UserDAO to approve the user and reloads the approvals data.
   * If the "Disapprove" button is clicked, it similarly prompts for confirmation
   * and then invokes the UserDAO to disapprove the user followed by reloading the approvals data.
   */
  private void initApproveButtonsColumn() {
    controller.approvalApprovals.setCellFactory(column -> new TableCell<User, Void>() {
      private final Button approveButton = new Button("Approve");
      private final Button disapproveButton = new Button("Disapprove");
      private final HBox buttonBox = new HBox(10, disapproveButton, approveButton);

      {
        approveButton.setStyle("-fx-background-color: green; -fx-text-fill: white;");
        disapproveButton.setStyle("-fx-background-color: red; -fx-text-fill: white;");

        approveButton.setOnAction(event -> {
          User user = getTableView().getItems().get(getIndex());
          Optional<ButtonType> result = showAlertConfirmation(
              "Approve pending account",
              "Are you sure you want to approve this account?");
          if (result.isPresent() && result.get() == ButtonType.OK) {
            if (UserDAO.getInstance().approve(user) > 0) {
              loadApprovalsData();
            } else {
              System.out.println("loi an approve");
            }
          }
        });

        disapproveButton.setOnAction(event -> {
          User user = getTableView().getItems().get(getIndex());
          Optional<ButtonType> result = showAlertConfirmation(
              "Disapprove pending account",
              "Are you sure you want to disapprove this account?");
          if (result.isPresent() && result.get() == ButtonType.OK) {
            if (UserDAO.getInstance().disapprove(user) > 0) {
              loadApprovalsData();
            } else {
              System.out.println("loi an disapprove");
            }
          }
        });
      }

      @Override
      protected void updateItem(Void item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
          setGraphic(null);
        } else {
          setGraphic(buttonBox);
        }
      }
    });
  }

  /**
   * Loads the data of pending user approvals into the corresponding view component.
   *
   * Clears the current list of approvals and retrieves the latest list of
   * pending users from the data source. Updates the TableView component
   * with this new list and initializes the state of the CheckBox controls
   * for filtering options. Resets the text in the username search field, and
   * ensures that the filter checkboxes for country, state, and year are set
   * to their default "Check All" status.
   */
  public void loadApprovalsData() {
    list.clear();
    list.addAll(UserDAO.getInstance().getPendingUsers());
    controller.approvalsTView.setItems(list);
    initializeCheckBox();
    controller.checkUsername.setText("");
    controller.checkCountry.getCheckModel().check("Check All");
    controller.checkState.getCheckModel().check("Check All");
    controller.checkYear.getCheckModel().check("Check All");
  }

  /**
   * Initializes the state of checkboxes associated with a list of items and sets up
   * a listener on each checkbox to update a controller's selection state.
   *
   * This method clears the existing list of checkbox statuses and reinitializes
   * it with new SimpleBooleanProperty objects, each set to false. It then adds
   * a listener to each of these properties that updates the `checkApprovals` checkbox
   * in the controller based on the checked state of all individual checkboxes.
   * If any checkbox is unchecked, `checkApprovals` is set to false.
   * If all are checked, it is set to true.
   */
  private void initializeCheckBox() {
    this.checkBoxStatusList.clear();
    for (int i = 0; i < list.size(); i++) {
      this.checkBoxStatusList.add(new SimpleBooleanProperty(false));
    }

    for (BooleanProperty checkBoxStatus : checkBoxStatusList) {
      checkBoxStatus.addListener((observable, oldValue, newValue) -> {
        if (!newValue) {
          controller.checkApprovals.setSelected(false);
        } else {
          boolean allSelected = checkBoxStatusList.stream().allMatch(BooleanProperty::get);
          if (allSelected) {
            controller.checkApprovals.setSelected(true);
          }
        }
      });
    }
  }


  /**
   * Toggles the selection status of all pending approval checkboxes.
   *
   * This method retrieves the selection status from the `checkApprovals`
   * checkbox in the controller. It then iterates over the list of checkbox
   * statuses (`checkBoxStatusList`) and updates each status to match the
   * selection state of `checkApprovals`.
   *
   * This is useful for batch operating on pending approval items where all
   * items need to be either selected or deselected based on a single master
   * control element.
   */
  public void checkAllPending() {
    boolean isSelected = controller.checkApprovals.isSelected();
    for (BooleanProperty checkBoxStatus : checkBoxStatusList) {
      checkBoxStatus.set(isSelected);
    }
  }

  /**
   * Disapproves pending user accounts that have been selected by the user.
   * This method displays a confirmation dialog to the user, asking for confirmation to proceed.
   * If the user confirms, it iterates through a list of user accounts paired with
   * their corresponding checkbox statuses. It then attempts to disapprove each selected account
   * through the UserDAO instance. If the disapproval fails, an error message is printed.
   * Finally, it refreshes the approval data.
   */
  public void disapprovePending() {
    Optional<ButtonType> result = showAlertConfirmation(
        "Disapprove pending accounts",
        "Are you sure you want to disapprove these accounts?");
    if (result.isPresent() && result.get() == ButtonType.OK) {
      for (int i = 0; i < list.size(); i++) {
        if (checkBoxStatusList.get(i).getValue()) {
          if (UserDAO.getInstance().disapprove(list.get(i)) < 0) {
            System.out.println("loi disapprove");
          }
        }
      }
    }
    loadApprovalsData();
  }

  /**
   * Approves pending user accounts if confirmed by the user. This method first
   * prompts the user with a confirmation dialog to approve pending accounts.
   * If the user confirms the action, the method iterates through a list of
   * user accounts and approves each one that is selected via a corresponding
   * checkbox. After attempting to approve each selected user account, it reloads
   * the approvals data to reflect any changes.
   *
   * If an approval operation fails, the method logs an error message to the console.
   */
  public void approvePendingUsers() {
    Optional<ButtonType> result = showAlertConfirmation(
        "Approve pending accounts",
        "Are you sure you want to approve these accounts?");
    if (result.isPresent() && result.get() == ButtonType.OK) {
      for (int i = 0; i < list.size(); i++) {
        if (checkBoxStatusList.get(i).getValue()) {
          if (UserDAO.getInstance().approve(list.get(i)) <= 0) {
            System.out.println("loi approve");
          }
        }
      }
    }
    loadApprovalsData();
  }


  /**
   * Initializes the country checklist used for filtering pending approvals.
   * The checklist is populated with a list of countries fetched from the database
   * via the UserDAO. An additional "Check All" option allows for bulk selection.
   *
   * The method sets up a change listener on the checklist to handle user
   * interactions. When "Check All" is selected, all other countries are checked;
   * when it is deselected, all are unchecked. If individual countries are
   * manually selected or deselected, the "Check All" option is automatically
   * updated to reflect whether all countries are currently selected.
   *
   * This setup ensures that the displayed list of pending approvals is updated
   * in response to changes in the checklist selection.
   */
  public void initializeCheckCountry() {
    ObservableList<String> countries = FXCollections.observableArrayList(
        UserDAO.getInstance().getAllPendingCountries());
    countries.addFirst("Check All");
    controller.checkCountry.getItems().clear();
    controller.checkCountry.getItems().addAll(countries);

    final boolean[] isProgrammaticallyChanging = {false};

    controller.checkCountry.getCheckModel().getCheckedItems()
        .addListener((ListChangeListener<String>) change -> {
            if (isProgrammaticallyChanging[0]) {
                return;
            }

          while (change.next()) {
            if (change.wasAdded() && change.getAddedSubList().contains("Check All")) {
              isProgrammaticallyChanging[0] = true;
              checkAllCountries(true);
              isProgrammaticallyChanging[0] = false;
            } else if (change.wasRemoved() && change.getRemoved().contains("Check All")) {
              isProgrammaticallyChanging[0] = true;
              checkAllCountries(false);
              isProgrammaticallyChanging[0] = false;
            } else {
              if (change.wasRemoved()) {
                isProgrammaticallyChanging[0] = true;
                controller.checkCountry.getCheckModel().clearCheck(0);
                isProgrammaticallyChanging[0] = false;
              }
            }

            boolean allSelected = true;
            for (int i = 1; i < controller.checkCountry.getItems().size(); i++) {
              if (!controller.checkCountry.getCheckModel().isChecked(i)) {
                allSelected = false;
                break;
              }
            }
            if (allSelected) {
              isProgrammaticallyChanging[0] = true;
              controller.checkCountry.getCheckModel().check(0);
              isProgrammaticallyChanging[0] = false;
            }
          }
          handleSearchPendingUser();
        });

    controller.checkCountry.getCheckModel().check("Check All");
  }

  /**
   * Checks or unchecks all countries in a toggle selection based on the provided parameter.
   * Iterates through all the country items in the controller's check model and updates
   * their checked state. If the parameter is true, all countries will be checked;
   * if false, all will be unchecked.
   *
   * @param check a boolean value indicating whether to check or clear the check
   *              on all countries. True to check all, false to uncheck all.
   */
  private void checkAllCountries(boolean check) {
    for (int i = 1; i < controller.checkCountry.getItems().size(); i++) {
      if (check) {
        controller.checkCountry.getCheckModel().check(i);
      } else {
        controller.checkCountry.getCheckModel().clearCheck(i);
      }
    }
    handleSearchPendingUser();
  }

  /**
   * Initializes the check state options by populating the checkable state list
   * with pending state items retrieved from the data source and a "Check All" option.
   * Adds a change listener to handle checking and unchecking operations which
   * automatically selects or deselects all state entries when "Check All" is toggled.
   * Updates the UI and internal state according to user interactions.
   */
  public void initializeCheckState() {
    ObservableList<String> states = FXCollections.observableArrayList(
        UserDAO.getInstance().getAllPendingStates());
    states.addFirst("Check All");
    controller.checkState.getItems().clear();
    controller.checkState.getItems().addAll(states);

    final boolean[] isProgrammaticallyChanging = {false};

    controller.checkState.getCheckModel().getCheckedItems()
        .addListener((ListChangeListener<String>) change -> {
            if (isProgrammaticallyChanging[0]) {
                return;
            }

          while (change.next()) {
            if (change.wasAdded() && change.getAddedSubList().contains("Check All")) {
              isProgrammaticallyChanging[0] = true;
              checkAllStates(true);
              isProgrammaticallyChanging[0] = false;
            } else if (change.wasRemoved() && change.getRemoved().contains("Check All")) {
              isProgrammaticallyChanging[0] = true;
              checkAllStates(false);
              isProgrammaticallyChanging[0] = false;
            } else {
              if (change.wasRemoved()) {
                isProgrammaticallyChanging[0] = true;
                controller.checkState.getCheckModel().clearCheck(0);
                isProgrammaticallyChanging[0] = false;
              }
            }

            boolean allSelected = true;
            for (int i = 1; i < controller.checkState.getItems().size(); i++) {
              if (!controller.checkState.getCheckModel().isChecked(i)) {
                allSelected = false;
                break;
              }
            }
            if (allSelected) {
              isProgrammaticallyChanging[0] = true;
              controller.checkState.getCheckModel().check(0);
              isProgrammaticallyChanging[0] = false;
            }
          }
          handleSearchPendingUser();
        });

    controller.checkState.getCheckModel().check("Check All");
  }

  /**
   * Toggles the check state for all items in the state checklist.
   *
   * This method iterates over all items in the checklist (except the first item)
   * and either checks or clears them based on the provided boolean flag. After
   * updating the check states, it invokes a method to handle search logic for
   * pending users.
   *
   * @param check if true, all items will be checked; otherwise, they will be cleared.
   */
  private void checkAllStates(boolean check) {
    for (int i = 1; i < controller.checkState.getItems().size(); i++) {
      if (check) {
        controller.checkState.getCheckModel().check(i);
      } else {
        controller.checkState.getCheckModel().clearCheck(i);
      }
    }
    handleSearchPendingUser();
  }

  /**
   * Initializes the year checkboxes in the user interface for pending approvals.
   * This method populates the year selection list with pending years retrieved
   * from the data access object and sets up listeners to handle user interaction
   * with the list. The method implements "select all" functionality by checking
   * all available years when "Check All" is selected, and vice versa.
   *
   * The state of the checkboxes is managed programmatically to avoid triggering
   * the listener during updates. If any year other than "Check All" is unchecked,
   * the "Check All" option is also unchecked automatically. Conversely, when all
   * years are checked individually, the "Check All" option is checked programmatically.
   *
   * It ensures that the state of the checkboxes always reflects the actual
   * selection status of the items and initiates a search for pending users based
   * on the selected criteria.
   */
  public void initializeCheckYear() {
    ObservableList<String> years = FXCollections.observableArrayList(
        UserDAO.getInstance().getAllPendingYears());
    years.add(0, "Check All");
    controller.checkYear.getItems().clear();
    controller.checkYear.getItems().addAll(years);

    final boolean[] isProgrammaticallyChanging = {false};

    controller.checkYear.getCheckModel().getCheckedItems()
        .addListener((ListChangeListener<String>) change -> {
            if (isProgrammaticallyChanging[0]) {
                return;
            }

          while (change.next()) {
            if (change.wasAdded() && change.getAddedSubList().contains("Check All")) {
              isProgrammaticallyChanging[0] = true;
              checkAllYears(true);
              isProgrammaticallyChanging[0] = false;
            } else if (change.wasRemoved() && change.getRemoved().contains("Check All")) {
              isProgrammaticallyChanging[0] = true;
              checkAllYears(false);
              isProgrammaticallyChanging[0] = false;
            } else {
              if (change.wasRemoved()) {
                isProgrammaticallyChanging[0] = true;
                controller.checkYear.getCheckModel().clearCheck(0);
                isProgrammaticallyChanging[0] = false;
              }
            }

            boolean allSelected = true;
            for (int i = 1; i < controller.checkYear.getItems().size(); i++) {
              if (!controller.checkYear.getCheckModel().isChecked(i)) {
                allSelected = false;
                break;
              }
            }
            if (allSelected) {
              isProgrammaticallyChanging[0] = true;
              controller.checkYear.getCheckModel().check(0);
              isProgrammaticallyChanging[0] = false;
            }
          }
          handleSearchPendingUser();
        });

    controller.checkYear.getCheckModel().check("Check All");
  }

  /**
   * Toggles the check state for all years in the year selection component.
   * If the parameter {@code check} is true, all years will be marked as checked.
   * If false, all will be unchecked. This method primarily updates the selection
   * state using the check model associated with the year items.
   * After updating the check state, it triggers a search for pending users with
   * the updated year filter.
   *
   * @param check a boolean indicating whether to check or uncheck all year items.
   */
  private void checkAllYears(boolean check) {
    for (int i = 1; i < controller.checkYear.getItems().size(); i++) {
      if (check) {
        controller.checkYear.getCheckModel().check(i);
      } else {
        controller.checkYear.getCheckModel().clearCheck(i);
      }
    }
    handleSearchPendingUser();
  }


  /**
   * Initializes the auto-complete functionality for the username input field.
   * This method sets up a context menu to provide suggestions as the user
   * types in the username field. The suggestion menu automatically hides when the
   * input field is empty or when suggestions are not applicable based on the current input.
   * An event filter is added to the username field to listen for key releases,
   * triggering a check on the input text. If the input text is not empty, it
   * calls the updateSuggestions method to retrieve and display relevant suggestions.
   */
  private void initializeAutoComplete() {
    suggestionMenu = new ContextMenu();
    suggestionMenu.setAutoHide(true);
    controller.checkUsername.addEventFilter(KeyEvent.KEY_RELEASED, event -> {
      String query = controller.checkUsername.getText().trim();
      if (query.isEmpty()) {
        suggestionMenu.hide();
      } else {
        updateSuggestions(query);
      }
    });
  }

  /**
   * Updates the list of username suggestions based on the provided query.
   * It retrieves all pending usernames matching the query and updates the suggestion menu.
   * If there are suggested usernames, it displays them in a menu below the search field.
   *
   * @param query the search query used to filter pending usernames and generate suggestions
   */
  private void updateSuggestions(String query) {
    List<String> suggestions = UserDAO.getInstance().getAllPendingUserName(query);
    usernameSuggestions.setAll(suggestions);
    suggestionMenu.getItems().clear();
    for (String suggestion : suggestions) {
      MenuItem item = new MenuItem(suggestion);
      item.setOnAction(event -> {
        controller.checkUsername.setText(suggestion);
        suggestionMenu.hide();
        handleSearchPendingUser();
      });
      suggestionMenu.getItems().add(item);
    }
    if (!usernameSuggestions.isEmpty()) {
      double screenX = controller.checkUsername.localToScreen(0, 0).getX();
      double screenY = controller.checkUsername.localToScreen(0, 0).getY()
          + controller.checkUsername.getHeight();
      suggestionMenu.hide();
      suggestionMenu.show(controller.checkUsername, screenX, screenY);
    } else {
      suggestionMenu.hide();
    }
  }

  /**
   * Handles the search for pending users based on a set of filters.
   *
   * This method retrieves the input from the user interface components for username, country,
   * state, and year filters, then uses these inputs to query the `UserDAO` for a list of
   * pending users matching the filters. The search results are then displayed in the `approvalsTView`
   * table view, updating the associated list with the filtered user data. Additionally,
   * initializes the checkboxes for user interaction post-search.
   *
   * The search is conducted taking into account:
   * - `nameQuery`: The trimmed text input representing the username filter.
   * - `selectedCountries`: A list of countries selected from the user interface.
   * - `selectedStates`: A list of states selected from the user interface.
   * - `selectedYears`: A list of years selected from the user interface.
   *
   * After fetching the filtered users, the method clears the current list,
   * populates it with the new filtered data and sets these items into the
   * table view `approvalsTView`. Lastly, it calls a method to initialize
   * or reset the checkboxes.
   */
  public void handleSearchPendingUser() {
    String nameQuery = controller.checkUsername.getText().trim();
    List<String> selectedCountries = controller.checkCountry.getCheckModel().getCheckedItems();
    List<String> selectedStates = controller.checkState.getCheckModel().getCheckedItems();
    List<String> selectedYears = controller.checkYear.getCheckModel().getCheckedItems();
    List<User> filteredUsers = UserDAO.getInstance()
        .searchPendingUserByFilter(nameQuery, selectedCountries, selectedStates, selectedYears);
    list.clear();
    list.addAll(filteredUsers);
    controller.approvalsTView.setItems(list);
    initializeCheckBox();
  }

}
