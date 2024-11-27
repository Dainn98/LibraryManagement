package library.management.ui.controllers;

import com.jfoenix.controls.JFXTextArea;

import java.io.IOException;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import library.management.properties;
import library.management.ui.applications.ApiGoogleGemini;

public class FAQsController implements properties {


    @FXML
    public VBox faqVBox;
    @FXML
    public Label name;
    private final MainController controller;

    public FAQsController(MainController mainController) {
        this.controller = mainController;
    }

    private VBox createFAQsContainer(Label name, String text, ScrollPane scrollPane,
                                     boolean checkAlignment) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource(FAQS_CONTAINER_SOURCE));
            VBox faqContainer = fxmlLoader.load();
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
            faqsContainerController.setData(name, new JFXTextArea(text));
            return faqContainer;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void loadFAQs(GridPane gPane, ScrollPane faqSPane) {

        String question = controller.faqRequestContainer.getText().trim();
        VBox userInputSection = createFAQsContainer(new Label("User:"), question,
                faqSPane, RIGHT);
        int rowCount = gPane.getRowCount();
        gPane.add(userInputSection, 0, rowCount);
        faqSPane.setContent(gPane);

        Task<VBox> getAnswer = new Task<>() {
            @Override
            protected VBox call() throws Exception {
                String answer = ApiGoogleGemini.sendPostRequest(question);
                return createFAQsContainer(new Label("Response:"), answer,
                        faqSPane, LEFT);
            }
        };
        getAnswer.setOnSucceeded(event -> {
            gPane.add(getAnswer.getValue(), 0, rowCount + 1);
            faqSPane.setContent(gPane);
        });
        Thread thread = new Thread(getAnswer);
        thread.setDaemon(true);
        thread.start();
    }
}
