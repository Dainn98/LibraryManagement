package library.management.ui.controllers;

import com.jfoenix.controls.JFXTextArea;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class FAQsContainerController {

  @FXML
  private VBox faqContainer;
  @FXML
  private Label name;
  @FXML
  private JFXTextArea textContainer;

  public FAQsContainerController() {
  }

  public void setData(Label name, JFXTextArea textContainer) {
    this.name.setText(name.getText());
    this.textContainer.setText(textContainer.getText());
    this.textContainer.setWrapText(false);
    this.textContainer.prefHeightProperty().bind(Bindings.createDoubleBinding(() -> {
      Text text = new Text(textContainer.getText());
      text.setWrappingWidth(textContainer.getWidth());
      return text.getLayoutBounds().getHeight() * (Math.PI / 2);
    }, textContainer.widthProperty()));
    this.faqContainer.prefHeightProperty().bind(textContainer.prefHeightProperty());
  }
}
