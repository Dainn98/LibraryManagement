package library.management.ui.applications;

import java.io.IOException;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.TargetDataLine;
import org.vosk.Model;
import org.vosk.Recognizer;

public final class SpeechToText {


  /**
   * The audio format used for microphone input.
   * 44.1kHz sample rate, 16-bit sample size, mono channel, signed PCM, little-endian.
   */
  public static final AudioFormat format = new AudioFormat(44100, 16, 1, true, false);

  /**
   * A flag to control the recognition process. Setting it to true stops the recognition.
   */
  public static volatile boolean stopRecognition = true;
  private static Model model;
  private static Recognizer recognizer;

  /**
   * Private constructor to prevent instantiation of this utility class.
   */
  private SpeechToText() {
  }

  /**
   * Lazily initializes and returns the Vosk Model.
   *
   * @return The Vosk Model instance.
   */
  public static synchronized Model getModel() {
    if (model == null) {
      try {
        model = new Model("C:/Users/admin/Downloads/vosk-model-en-us-0.22-lgraph");
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return model;
  }

  /**
   * Lazily initializes and returns the Vosk Recognizer.
   *
   * @return The Vosk Recognizer instance.
   */
  public static synchronized Recognizer getRecognizer() {
    if (recognizer == null) {
      try {
        recognizer = new Recognizer(getModel(), 44100);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return recognizer;
  }

  /**
   * Main method for running real-time speech recognition.
   *
   * @param args Command-line arguments (not used).
   */
  public static void main(String[] args) {
    stopRecognition = false;
    // Đường dẫn đến mô hình ngôn ngữ
    String modelPath = "C:/Users/admin/Downloads/vosk-model-en-us-0.22-lgraph";

    try (Model model = new Model(modelPath)) {
      // Khởi tạo recognizer
      try (Recognizer recognizer = new Recognizer(model, 44100)) {
        // Cấu hình micro
        AudioFormat format = new AudioFormat(44100, 16, 1, true, false);
        DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);

        if (!AudioSystem.isLineSupported(info)) {
          System.err.println("Micro không được hỗ trợ.");
          return;
        }

        // Khởi chạy luồng xử lý nhấn phím
        Thread inputThread = new Thread(() -> {
          System.out.println("Nhấn 'Enter' để dừng nhận diện giọng nói...");
          try {
            System.in.read(); // Chờ người dùng nhấn Enter
            stopRecognition = true;
          } catch (Exception e) {
            e.printStackTrace();
          }
        });
        inputThread.start();

        // Bắt đầu nhận diện giọng nói
        try (TargetDataLine microphone = (TargetDataLine) AudioSystem.getLine(info)) {
          microphone.open(format);
          microphone.start();

          byte[] buffer = new byte[4096];

          while (!stopRecognition) {
            int bytesRead = microphone.read(buffer, 0, buffer.length);

            if (recognizer.acceptWaveForm(buffer, bytesRead)) {
              String result = recognizer.getResult();
              String text = extractTextFromJson(result);
              if (!text.isEmpty()) {
                System.out.print(text + " "); // In từng chữ ngay khi nhận diện được
              }
            }
          }
        }

      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Extracts the recognized text from the JSON response returned by the Vosk Recognizer.
   *
   * @param json The JSON string returned by the recognizer.
   * @return The recognized text, or an empty string if not found.
   */
  public static String extractTextFromJson(String json) {
    // Trích xuất giá trị của "text" trong JSON
    String textKey = "\"text\" : \"";
    int startIndex = json.indexOf(textKey);
    if (startIndex == -1) {
      return ""; // Không tìm thấy trường "text"
    }

    startIndex += textKey.length();
    int endIndex = json.indexOf("\"", startIndex);
    if (endIndex == -1) {
      return ""; // Không tìm thấy kết thúc của chuỗi "text"
    }

    return json.substring(startIndex, endIndex).trim();
  }
}