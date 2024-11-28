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
import library.management.ui.applications.SpeechToText;
import org.vosk.Recognizer;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

public class FAQsController implements properties {

  private final FullUserController userController;
  private final MainController controller;
  @FXML
  public VBox faqVBox;
  @FXML
  public Label name;

  public FAQsController(FullUserController userController) {
    this.userController = userController;
    this.controller = null;
  }

  public FAQsController(MainController mainController) {
    this.controller = mainController;
    this.userController = null;
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

  public void loadFAQs(GridPane gPane, ScrollPane faqSPane) {
    if (controller != null) {
      loadQuestion(gPane, faqSPane, controller.faqRequestContainer);
    } else {
      loadQuestion(gPane, faqSPane, userController.faqRequestContainer);
    }
  }

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

  private void initMicro(Recognizer recognizer, TargetDataLine microphone, JFXTextArea faqRequestContainer) throws LineUnavailableException {
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
