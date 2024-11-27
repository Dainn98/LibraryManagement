package library.management.ui.controllers;

import com.jfoenix.controls.JFXTextArea;
import javafx.beans.InvalidationListener;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import library.management.properties;

public class FAQsContainerController {

    public VBox faqContainer;
    public Label name;
    public JFXTextArea textContainer;
    public HBox faqOptions;

    public FAQsContainerController() {
    }

    public void setData(VBox faqContainer, Label name, JFXTextArea textContainer) {
//    this.faqContainer = faqContainer;
        this.name.setText(name.getText());
        this.textContainer.setText(textContainer.getText());
        faqContainer.prefHeightProperty().bind(textContainer.prefHeightProperty());
    }

    public void setData(Label name, JFXTextArea textContainer) {
        this.name.setText(name.getText());
        this.textContainer.setText(textContainer.getText());
        this.textContainer.setWrapText(true);
        double ratio = getRatio(textContainer.getText().length());
        textContainer.prefHeightProperty().bind(textContainer.textProperty().length().multiply(ratio));
        faqContainer.prefHeightProperty().bind(textContainer.prefHeightProperty());
    }

    private double getRatio(int length) {
        if (length <= 300) {
            return 0.7;
        } else if (length <= 500) {
            return 0.55;
        } else if (length <= 1000) {
            return 0.6;
        } else if (length <= 1500) {
            return 0.62;
        } else if (length <= 2000) {
            return 0.6;
        } else if (length <= 2400) {
            return 0.6;
        } else if (length <= 2700) {
            return 0.55;
        } else if (length <= 3000) {
            return 0.5;
        } else {
            return 0.45;
        }
    }

    public JFXTextArea getTextContainer() {
        return textContainer;
    }

    public void setFAQsOptionsAlignment(boolean checkAlignment) {
        if (checkAlignment) {
            faqOptions.setAlignment(Pos.CENTER_LEFT);
        } else {
            faqOptions.setAlignment(Pos.CENTER_RIGHT);
        }
    }


}
