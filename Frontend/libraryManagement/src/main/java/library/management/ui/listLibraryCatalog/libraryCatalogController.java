package library.management.ui.listLibraryCatalog;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import library.management.data.models.Documents.Document;
import javafx.stage.Stage;
import library.management.ui.database.DatabaseHandler;
import library.management.ui.addBook.AddBookController;

public class libraryCatalogController implements Initializable {

  ObservableList<Document> list = FXCollections.observableArrayList();

  @FXML
  private StackPane rootPane;
  @FXML
  private TableView<Document> tableView;
  @FXML
  private TableColumn<Document, String> titleColumn;
  @FXML
  private TableColumn<Document, String> idColumn;
  @FXML
  private TableColumn<Document, String> authorColumn;
  @FXML
  private TableColumn<Document, String> publisherColumn;
  @FXML
  private TableColumn<Document, Boolean> availabilityColumn;

  @FXML
  private void handleRefresh(ActionEvent event) {
    loadData();
  }

  @FXML
  private void handleBookEditOption(ActionEvent actionEvent) {

  }

  @FXML
  private void handleBookDeleteOption(ActionEvent actionEvent) {
  }

  @FXML
  private void exportAsPDF(ActionEvent actionEvent) {
  }

  @FXML
  private void closeStage(ActionEvent actionEvent) {
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    initCol();
    loadData();
  }

private Stage getStage() {
    return (Stage) tableView.getScene().getWindow();
  }

  private void initCol() {
    titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
    idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
    authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
    publisherColumn.setCellValueFactory(new PropertyValueFactory<>("publisher"));
    availabilityColumn.setCellValueFactory(new PropertyValueFactory<>("availabilty"));
  }

  /**
   * Load data from the database and display it in the table view.
   * Dịu làm phần này nha, thiết kế lớp DatabaseHandler và sử dụng nó để truy vấn dữ liệu từ cơ sở dữ liệu.
   */
  private void loadData() {
    list.clear();
    DatabaseHandler handler = DatabaseHandler.getInstance();
    String qu = "SELECT * FROM BOOK";
    ResultSet rs = handler.execQuery(qu);
    try {
      while (rs.next()) {

        /**
         * Read the data from the result set and add it to the list.
         * list.add(new Document(....);
          */
      }
    } catch (SQLException ex) {
      Logger.getLogger(AddBookController.class.getName()).log(Level.SEVERE, null, ex);
    }

    tableView.setItems(list);
  }
}
