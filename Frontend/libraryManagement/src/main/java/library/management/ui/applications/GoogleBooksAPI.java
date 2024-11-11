package library.management.ui.applications;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import library.management.data.database.DatabaseConnection;

public class GoogleBooksAPI {
    private static final String API_KEY = "AIzaSyA-ISmMzbKBzb24boY2XF6ZzmvQbWpZSt4";

    public static void main(String[] args) {
        String query = "Harry Potter";
        try {
            JsonArray books = fetchBooks(query);
            if (books != null) {
                saveBooksToDatabase(books);
            } else {
                System.out.println("No books found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Phương thức tìm kiếm sách từ Google Books API
    public static JsonArray fetchBooks(String query) throws Exception {
        String urlString = "https://www.googleapis.com/books/v1/volumes?q="
                + query.replace(" ", "+") + "&key=" + API_KEY;
        URL url = new URL(urlString);

        // Mở kết nối và lấy phản hồi JSON
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");

        if (conn.getResponseCode() != 200) {
            throw new RuntimeException("HTTP error code : " + conn.getResponseCode());
        }

        BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
        StringBuilder response = new StringBuilder();
        String output;
        while ((output = br.readLine()) != null) {
            response.append(output);
        }
        conn.disconnect();

        JsonObject jsonResponse = JsonParser.parseString(response.toString()).getAsJsonObject();
        return jsonResponse.has("items") ? jsonResponse.getAsJsonArray("items") : null;
    }

    // Phương thức lưu thông tin sách vào cơ sở dữ liệu
    public static void saveBooksToDatabase(JsonArray books) {
        for (int i = 0; i < books.size(); i++) {
            JsonObject book = books.get(i).getAsJsonObject();
            JsonObject volumeInfo = book.getAsJsonObject("volumeInfo");

            String title = getTitle(volumeInfo);
            String authors = getAuthors(volumeInfo);
            String description = getDescription(volumeInfo);
            String genre = getGenre(volumeInfo);

            // Gọi phương thức saveBook từ DatabaseConnection để lưu vào database
            DatabaseConnection.saveBook(title, authors, description, genre);
        }
    }

    private static String getTitle(JsonObject volumeInfo) {
        return volumeInfo.has("title") ? volumeInfo.get("title").getAsString() : "No title available";
    }

    private static String getAuthors(JsonObject volumeInfo) {
        if (!volumeInfo.has("authors")) {
            return "Unknown author";
        }
        JsonArray authorsArray = volumeInfo.getAsJsonArray("authors");
        StringBuilder authors = new StringBuilder();
        for (int j = 0; j < authorsArray.size(); j++) {
            authors.append(authorsArray.get(j).getAsString());
            if (j < authorsArray.size() - 1) {
                authors.append(", ");
            }
        }
        return authors.toString();
    }

    private static String getDescription(JsonObject volumeInfo) {
        return volumeInfo.has("description") ? volumeInfo.get("description").getAsString() : "No description available";
    }

    private static String getGenre(JsonObject volumeInfo) {
        if (!volumeInfo.has("categories")) {
            return "No genre available";
        }
        JsonArray categories = volumeInfo.getAsJsonArray("categories");
        return categories.get(0).getAsString();
    }
}
