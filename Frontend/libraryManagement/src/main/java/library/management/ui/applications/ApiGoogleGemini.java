package library.management.ui.applications;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ApiGoogleGemini {

  /**
   * The API key used to authenticate requests to the Google Gemini API.
   */
  private static final String API_KEY = "AIzaSyCHlwfVSZxl6ZBZdmJOnO45lzrQ0ppdsc8";
  /**
   * The endpoint URL for the Google Gemini API.
   */
  private static final String ENDPOINT = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent";

  /**
   * Sends a POST request to the Google Gemini API with the specified text and retrieves the
   * generated content from the API response.
   *
   * @param text The input text to send to the API for content generation.
   * @return The content generated by the API as a {@code String}, or an error message if the
   * connection fails.
   */
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
      JsonNode textNode = rootNode.path("candidates").get(0).path("content").path("parts").get(0)
          .path("text");

      // Trả về kết quả văn bản
      return textNode.asText();
    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
      return "Error: Unable to connect to the API.";
    }
  }


  /**
   * The main method to test the API integration via the console.
   *
   * <p>This method prompts the user to input text, sends the text to the API for processing,
   * and prints the API's response.
   *
   * @param args Command-line arguments (not used in this implementation).
   */
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
