package library.management.ui.applications;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
public class ApiGoogleGemini {

    private static final String API_KEY = "AIzaSyCHlwfVSZxl6ZBZdmJOnO45lzrQ0ppdsc8";
    private static final String ENDPOINT = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent";

    public static String sendPostRequest(String text) {
        try {
            String requestBody = """
            {
              "contents": [
                {
                  "parts": [
                    {
                      "text": "%s"
                    }
                  ]
                }
              ]
            }
            """.formatted(text);

            // Xây dựng HTTP request
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(ENDPOINT + "?key=" + API_KEY))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            // Gửi request
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Xử lý phản hồi từ API
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response.body());
            JsonNode textNode = rootNode.path("candidates").get(0).path("content").path("parts").get(0).path("text");

            // Trả về kết quả văn bản
            return textNode.asText();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return "Error: Unable to connect to the API.";
        }
    }

    // Hàm main để kiểm tra trực tiếp trong console
    public static void main(String[] args) {
        // Nhập văn bản muốn gửi tới API
        System.out.println("Nhập văn bản để gửi tới API Google Gemini:");
        String inputText = System.console().readLine();

        // Gọi phương thức sendPostRequest và nhận kết quả
        String response = sendPostRequest(inputText);

        // In phản hồi từ API
        System.out.println("Phản hồi từ API:");
        System.out.println(response);
    }
}
