package library.management.ui.applications;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import library.management.data.DAO.DocumentDAO;
import library.management.data.entity.Document;

public class GoogleBooksAPI {
    private static final String API_KEY = "AIzaSyDw67-cnEd_AzpPTg9lkfrIZDzIXIl4ius";

    public static void main(String[] args) {
        String query = "Harry potter";
        try {
            JsonArray books = fetchBooks(query);
            if (books != null) {
                for (int i = 0; i < books.size(); i++) {
                    System.out.println(getImageLink(books.get(i).getAsJsonObject()));
                }
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
        System.out.println("Request URL: " + urlString);
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

            String categoryID = getGenre(volumeInfo);
            String publisher = volumeInfo.has("publisher") ? volumeInfo.get("publisher").getAsString() : "Unknown Publisher";
            String lgID = "LANG4";
            String isbn = getISBN(volumeInfo);
            int quantity = 12;
            int availableCopies = 12;
            String addDate = "2024-10-20 00:00:00"; // Định dạng ngày tháng đầy đủ
            double price = 12.0;
            String title = getTitle(volumeInfo);
            String author = getAuthors(volumeInfo);
            String description = getDescription(volumeInfo);
            String url = getInfoLink(volumeInfo);
            String image = getImageLink(volumeInfo);

            Document document = new Document(categoryID, publisher, lgID, title, author, isbn, quantity, availableCopies, addDate, price, description, url, image, "Available");

            if (DocumentDAO.getInstance().add(document) > 0) {
                System.out.println("Thêm sách thành công!");
            } else {
                System.out.println("Thêm sách thất bại!");
            }
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
            return "CAT1"; // Giá trị mặc định cho categoryID nếu không có thể loại
        }
        JsonArray categories = volumeInfo.getAsJsonArray("categories");
        return "CAT" + categories.get(0).getAsString().hashCode(); // Sử dụng mã băm làm ID thể loại
    }

    private static String getInfoLink(JsonObject volumeInfo) {
        return volumeInfo.has("infoLink") ? volumeInfo.get("infoLink").getAsString() : "No URL available";
    }

    private static String getImageLink(JsonObject volumeInfo) {
        if (!volumeInfo.has("imageLinks")) {
            return "No thumbnail available";
        }
        JsonObject imageLinks = volumeInfo.getAsJsonObject("imageLinks");
        if (imageLinks.has("thumbnail")) {
            return imageLinks.get("thumbnail").getAsString();
        } else if (imageLinks.has("smallThumbnail")) {
            return imageLinks.get("smallThumbnail").getAsString();
        }
        return "No thumbnail available";
    }

    private static String getISBN(JsonObject volumeInfo) {
        if (volumeInfo.has("industryIdentifiers")) {
            JsonArray identifiers = volumeInfo.getAsJsonArray("industryIdentifiers");
            for (int i = 0; i < identifiers.size(); i++) {
                JsonObject identifier = identifiers.get(i).getAsJsonObject();
                if (identifier.get("type").getAsString().equals("ISBN_13")) {
                    return identifier.get("identifier").getAsString();
                }
            }
        }
        return "No ISBN available";
    }
}
