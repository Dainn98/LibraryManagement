package library.management.ui.controllers;

import javafx.scene.control.TableCell;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import library.management.data.models.User.User;

public class buttonTableCell extends TableCell<User, Boolean>
    implements Callback<TableColumn<User, Boolean>, TableCell<User, Boolean>> {
  private final HBox box = new HBox();
  private final Button approveButton = new Button("Approve");
  private final Button rejectButton = new Button("Reject");

  public buttonTableCell() {
    box.getChildren().addAll(approveButton, rejectButton);

    approveButton.setStyle("-fx-background-color: green; -fx-text-fill: white;");
    rejectButton.setStyle("-fx-background-color: red; -fx-text-fill: white;");

    approveButton.setOnAction(event -> {
      // Xử lý hành động khi nhấn nút Approve
      User user = getTableView().getItems().get(getIndex());
      System.out.println("Approved: " + user);
    });

    rejectButton.setOnAction(event -> {
      // Xử lý hành động khi nhấn nút Reject
      User user = getTableView().getItems().get(getIndex());
      System.out.println("Rejected: " + user);
    });
  }

  @Override
  protected void updateItem(Boolean item, boolean empty) {
    super.updateItem(item, empty);
    if (empty) {
      setGraphic(null);
    } else {
      setGraphic(box);
    }
  }

  @Override
  public TableCell<User, Boolean> call(TableColumn<User, Boolean> userBooleanTableColumn) {
    return new buttonTableCell();
  }

  public static <S> Callback<TableColumn<S, Void>, TableCell<S, Void>> forTableColumn() {
//    return param -> new ButtonTableCell();
    return null;
  }
}