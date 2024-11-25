package library.management.ui.applications;

import org.vosk.LibVosk;
import org.vosk.Model;
import org.vosk.Recognizer;

import javax.sound.sampled.*;

public class SpeechToText {
    private static volatile boolean stopRecognition = false;

    public static void main(String[] args) {
        // Đường dẫn đến mô hình ngôn ngữ
        String modelPath = "D:/UET/UET2th/OOP/vosk-model-en-us-0.22-lgraph";

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

    private static String extractTextFromJson(String json) {
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


