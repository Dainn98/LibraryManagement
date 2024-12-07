/**
 * This package contains the controllers for the user interface of the Library Management application.
 * The classes in this package handle interactions and events in the application's UI.
 */
package library.management.ui.controllers;

import com.jfoenix.controls.JFXTextArea;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * Controller for managing a single FAQ (Frequently Asked Questions) entry in the application.
 * This class is responsible for displaying the FAQ's name and content while ensuring dynamic resizing
 * of the content area based on the text size.
 */
public class FAQsContainerController {
    /**
     * A container that holds the FAQ entry.
     */
    @FXML
    private VBox faqContainer;
    /**
     * Label displaying the name/title of the FAQ entry.
     */
    @FXML
    private Label name;
    /**
     * Text area displaying the content of the FAQ entry.
     * This area dynamically adjusts its height based on the content size.
     */
    @FXML
    private JFXTextArea textContainer;

    /**
     * Default constructor for the FAQsContainerController.
     */
    public FAQsContainerController() {
    }

    /**
     * Sets the data for the FAQ entry. This method sets the name and text content for the FAQ and
     * binds the height of the text container to the size of the text inside it.
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
