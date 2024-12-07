/**
 * The FAQsController class is responsible for managing the Frequently Asked Questions (FAQ) interface,
 * including handling user input, loading questions and answers, and integrating speech-to-text functionality.
 * This controller supports two modes:
 * 1. User-specific FAQs (handled via {@link FullUserController}).
 * 2. Main application FAQs (handled via {@link MainController}).
 */
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

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

import library.management.properties;
import library.management.ui.applications.ApiGoogleGemini;
import library.management.ui.applications.SpeechToText;
import library.management.ui.controllers.manager.MainController;
import library.management.ui.controllers.user.FullUserController;
import org.vosk.Recognizer;

public class FAQsController implements properties {

    private final FullUserController userController;
    private final MainController controller;
    @FXML
    public VBox faqVBox;
    @FXML
    public Label name;

    /**
     * Constructor for the FAQsController, used when managing user-specific FAQs.
     *
     * @param userController The controller associated with the user.
     */
    public FAQsController(FullUserController userController) {
        this.userController = userController;
        this.controller = null;
    }

    /**
     * Constructor for the FAQsController, used when managing main controller-specific FAQs.
     *
     * @param mainController The main controller associated with the application.
     */
    public FAQsController(MainController mainController) {
        this.controller = mainController;
        this.userController = null;
    }

    /**
     * Creates and returns a VBox containing the FAQ question and its corresponding answer. The VBox
     * is used to display the question and answer in the FAQ interface.
     *
     * @param name           The label for the FAQ (either "User" or "Response").
     * @param text           The text content for the FAQ (either user question or answer).
     * @param scrollPane     The ScrollPane that contains the FAQ content.
     * @param checkAlignment A boolean to check the alignment for the question/answer.
     * @return A VBox containing the FAQ question/answer.
     */
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
            } else {
                faqContainer.setPadding(new Insets(0, 30, 0, 200));
                faqContainer.setAlignment(Pos.CENTER_RIGHT);
            }
            faqsContainerController.setData(name, new JFXTextArea(text));
            return faqContainer;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Loads the FAQs into a GridPane and binds them to the provided ScrollPane. This method will load
     * either user-specific FAQs or main controller FAQs.
     *
     * @param gPane    The GridPane that will hold the FAQ content.
     * @param faqSPane The ScrollPane that holds the FAQ content.
     */
    public void loadFAQs(GridPane gPane, ScrollPane faqSPane) {
        if (controller != null) {
            loadQuestion(gPane, faqSPane, controller.faqRequestContainer);
        } else {
            loadQuestion(gPane, faqSPane, userController.faqRequestContainer);
        }
    }


    /**
     * Loads a question into the FAQ container and sends the question to the Google Gemini API to
     * retrieve an answer. The question and answer are displayed in the FAQ section.
     *
     * @param gPane               The GridPane that holds the FAQ content.
     * @param faqSPane            The ScrollPane that contains the FAQ section.
     * @param faqRequestContainer The text area where the question is input.
     */
    private void loadQuestion(GridPane gPane, ScrollPane faqSPane, JFXTextArea faqRequestContainer) {
        String question = faqRequestContainer.getText().trim();
        VBox userInputSection = createFAQsContainer(new Label("User:"), question,
                faqSPane, RIGHT);
        int rowCount = gPane.getRowCount();
        gPane.add(userInputSection, 0, rowCount);
        faqSPane.setContent(gPane);
        faqRequestContainer.clear();

        Task<VBox> getAnswer = new Task<>() {
            @Override
            protected VBox call() {
                String answer = ApiGoogleGemini.sendPostRequest(question);
                return createFAQsContainer(new Label("Response:"), answer,
                        faqSPane, LEFT);
            }
        };
        getAnswer.setOnSucceeded(_ -> {
            gPane.add(getAnswer.getValue(), 0, rowCount + 1);
            faqSPane.setContent(gPane);
        });
        Thread thread = new Thread(getAnswer);
        thread.setDaemon(true);
        thread.start();
    }

    /**
     * Records audio from the user's microphone and converts the speech to text using the Vosk
     * recognizer. The converted text is then placed in the FAQ request container.
     */
    public void record() {
        Recognizer recognizer = SpeechToText.getRecognizer();
        DataLine.Info info = new DataLine.Info(TargetDataLine.class, SpeechToText.format);
        if (!AudioSystem.isLineSupported(info)) {
            System.err.println("Micro is not supported.");
            return;
        }
        try (TargetDataLine microphone = (TargetDataLine) AudioSystem.getLine(info)) {
            if (controller != null) {
                initMicro(recognizer, microphone, controller.faqRequestContainer);
            } else {
                initMicro(recognizer, microphone, userController.faqRequestContainer);
            }
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Initializes the microphone for speech recognition and continuously records audio. It processes
     * the audio and updates the FAQ request container with recognized text.
     *
     * @param recognizer          The speech recognizer instance.
     * @param microphone          The microphone that captures the audio.
     * @param faqRequestContainer The text area where recognized speech is displayed.
     * @throws LineUnavailableException If the microphone cannot be accessed.
     */
    private void initMicro(Recognizer recognizer, TargetDataLine microphone,
                           JFXTextArea faqRequestContainer) throws LineUnavailableException {
        microphone.open(SpeechToText.format);
        microphone.start();
        byte[] buffer = new byte[4096];
        while (!SpeechToText.stopRecognition) {
            int bytesRead = microphone.read(buffer, 0, buffer.length);
            if (recognizer.acceptWaveForm(buffer, bytesRead)) {
                String result = recognizer.getResult();
                String text = SpeechToText.extractTextFromJson(result);
                if (!text.isEmpty()) {
                    faqRequestContainer.setText(
                            faqRequestContainer.getText() + " " + text);
                    System.out.println(text + " ");
                }
            }
        }
    }
}