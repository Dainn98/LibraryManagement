package library.management.ui.controllers;

import com.jfoenix.controls.JFXTextArea;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class FAQsContainerController {

  public VBox faqContainer;
  public Label name;
  public JFXTextArea textContainer;

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
