package library.management.ui.controllers;

import com.jfoenix.controls.JFXTextArea;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import library.management.properties;

public class FAQsController implements properties {

//  private static final String FAQS_CONTAINER_SOURCE = "/ui/fxml/faqContainer.fxml";
//  private static final boolean RIGHT = false;
//  private static final boolean LEFT = true;
  @FXML
  public VBox faqVBox;
  @FXML
  public Label name;
  String response =
          "### Kết quả:\n"
          + "- `JFXTextArea` sẽ mở rộng tự động theo nội dung, không xuất hiện thanh cuộn.\n"
          + "- `VBox` chứa `TextArea` cũng tự động thay đổi kích thước theo.\n"
          + "\n"
          + "Hãy thử mã này và cho tôi biết nếu bạn cần điều chỉnh thêm! \uD83D\uDE0A"
              + "public void setData(Label name, JFXTextArea textContainer) {\n"
          ;
  @FXML
  private MainController mainController;
  public FAQsController(MainController mainController) {
    this.mainController = mainController;
  }

  private VBox createFAQsContainer(Label name, JFXTextArea textContainer, ScrollPane scrollPane,
      boolean checkAlignment) {
    try {
      //Load faqContainer.fxml
      FXMLLoader fxmlLoader = new FXMLLoader();
      fxmlLoader.setLocation(getClass().getResource(FAQS_CONTAINER_SOURCE));
      VBox faqContainer = fxmlLoader.load();

      // Load FAQsContainerController
      FAQsContainerController faqsContainerController = fxmlLoader.getController();
      faqContainer.prefWidthProperty().bind(scrollPane.widthProperty());

      if (checkAlignment) {
        faqContainer.setPadding(new Insets(0, 200, 0, 10));

        faqContainer.setAlignment(Pos.CENTER_LEFT);
        faqsContainerController.setFAQsOptionsAlignment(LEFT);
      } else {
        faqContainer.setPadding(new Insets(0, 30, 0, 200));
        faqContainer.setAlignment(Pos.CENTER_RIGHT);
        faqsContainerController.setFAQsOptionsAlignment(RIGHT);
      }

      // Set data
      faqsContainerController.setData(name, textContainer);
      return faqContainer;
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  public void loadFAQs(GridPane gPane, ScrollPane faqSPane) {

    VBox userInputSection = createFAQsContainer(new Label("User:"), new JFXTextArea(response),
        faqSPane, RIGHT);

    VBox responseSection = createFAQsContainer(new Label("Response:"), new JFXTextArea(response),
        faqSPane, LEFT);

    int rowCount = gPane.getRowCount();
    gPane.add(userInputSection, 0, rowCount);
    gPane.add(responseSection, 0, rowCount + 1);

    faqSPane.setContent(gPane);
  }

}
