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

    /**
     * Sets the data for the FAQ entry.
     * This method sets the name and text content for the FAQ and binds the height of the text container
     * to the size of the text inside it.
     *
     * @param name          The label containing the FAQ name.
     * @param textContainer The JFXTextArea containing the FAQ text.
     */
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
