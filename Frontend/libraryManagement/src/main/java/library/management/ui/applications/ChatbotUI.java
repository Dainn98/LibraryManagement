package library.management.ui.applications;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class ChatbotUI extends Application {

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) {
    primaryStage.setTitle("Chatbot");

    // Khu vực chat
    TextArea chatArea = new TextArea();
    chatArea.setEditable(false);
    chatArea.setWrapText(true);

    // Ô nhập văn bản
    TextField inputField = new TextField();
    inputField.setPromptText("Nhập tin nhắn của bạn...");

    // Nút gửi
    Button sendButton = new Button("Gửi");
    sendButton.setOnAction(e -> {
      String userInput = inputField.getText();
      if (!userInput.isEmpty()) {
        chatArea.appendText("Bạn: " + userInput + "\n");
        // Giả lập phản hồi từ chatbot
        chatArea.appendText("Bot: " + "Tôi đã nhận được tin nhắn của bạn.\n");
        inputField.clear();
      }
    });

    // Bố trí giao diện
    HBox inputLayout = new HBox(10, inputField, sendButton);
    inputLayout.setPadding(new Insets(10));

    BorderPane layout = new BorderPane();
    layout.setCenter(chatArea);
    layout.setBottom(inputLayout);

    Scene scene = new Scene(layout, 400, 300);
    primaryStage.setScene(scene);
    primaryStage.show();
  }
}