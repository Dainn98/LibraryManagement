package library.management.ui.applications;

import org.vosk.LibVosk;
import org.vosk.Model;
import org.vosk.Recognizer;

import javax.sound.sampled.*;
import java.io.InputStream;

public class SpeechToText {
    public static void main(String[] args) {
        // Đường dẫn đến mô hình ngôn ngữ
        String modelPath = "D:/UET/UET2th/OOP/vosk-model-vn-0.4";

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

                try (TargetDataLine microphone = (TargetDataLine) AudioSystem.getLine(info)) {
                    microphone.open(format);
                    microphone.start();
                    System.out.println("Bắt đầu nhận diện giọng nói...");

                    byte[] buffer = new byte[8192]; // Tăng buffer
                    while (true) {
                        int bytesRead = microphone.read(buffer, 0, buffer.length);

                        if (recognizer.acceptWaveForm(buffer, bytesRead)) {
                            System.out.print(recognizer.getResult());
                        }
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

