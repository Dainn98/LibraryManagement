package library.management.ui.applications;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import library.management.data.DAO.CategoryDAO;
import library.management.data.DAO.LanguageDAO;
import library.management.data.entity.Category;
import library.management.data.entity.Document;
import library.management.data.entity.Language;

public class GoogleBooksAPI {

    private static final String API_KEY = "AIzaSyA-ISmMzbKBzb24boY2XF6ZzmvQbWpZSt4";

    /**
     * Searches for books using the Google Books API and processes the results into a list of {@link Document}.
     *
     * @param query      The search query. Defaults to "bestseller" if null or empty.
     * @param maxResults The maximum number of results to fetch.
     * @param startIndex The index of the first result to fetch.
     * @return A list of {@link Document} objects containing book metadata.
     * @throws Exception If an error occurs while connecting to the API or parsing the response.
     */
    public static List<Document> searchDocument(String query, int maxResults, int startIndex)
            throws Exception {
        if (query == null || query.trim().isEmpty()) {
            query = "bestseller";
        }
        List<Document> documents = new ArrayList<>();

        JsonArray books = fetchBooks(query, maxResults, startIndex);
        if (books == null || books.size() == 0) {
            return documents;
        }

        for (int i = 0; i < books.size(); i++) {
            JsonObject book = books.get(i).getAsJsonObject();
            JsonObject volumeInfo = book.getAsJsonObject("volumeInfo");

            String title = getTitle(volumeInfo);
            String authors = getAuthors(volumeInfo);
            String isbn = getISBN(volumeInfo);
            String language = getLanguage(volumeInfo);
            int languageID = LanguageDAO.getInstance().getLanguageId(language);
            if (languageID < 0) {
                Language lg = new Language();
                lg.setLgName(language);
                LanguageDAO.getInstance().add(lg);
                languageID = LanguageDAO.getInstance().getLanguageId(language);
            }
            String description = getDescription(volumeInfo);
            String thumbnail = getImageLink(volumeInfo);
            if (Objects.equals(thumbnail, "No thumbnail available")) {
                thumbnail = "/ui/sprites/demoDoc.gif";
            }
            String infoLink = getInfoLink(volumeInfo);
            String categotyTag = getGenre(volumeInfo).getFirst();
            String publisher = getPublisher(volumeInfo);
            int categoryID = CategoryDAO.getInstance().getTagId(categotyTag);
            if (categoryID < 0) {
                Category category = new Category();
                category.setTag(categotyTag);
                CategoryDAO.getInstance().add(category);
                categoryID = CategoryDAO.getInstance().getTagId(categotyTag);
            }

            // Tạo đối tượng Document và thêm vào danh sách
            Document document = new Document(categoryID, publisher, languageID, title, authors, isbn,
                    description, infoLink, thumbnail);
            documents.add(document);
        }
        return documents;
    }


    /**
     * Fetches book data from the Google Books API.
     *
     * @param query      The search query.
     * @param maxResults The maximum number of results to fetch.
     * @param startIndex The index of the first result to fetch.
     * @return A {@link JsonArray} containing book data from the API response.
     * @throws Exception If an error occurs during the API request or response processing.
     */
    // Phương thức tìm kiếm sách từ Google Books API
    public static JsonArray fetchBooks(String query, int maxResults, int startIndex)
            throws Exception {
        String urlString = "https://www.googleapis.com/books/v1/volumes?q="
                + query.replace(" ", "+")
                + "&maxResults=" + maxResults
                + "&startIndex=" + startIndex
                + "&key=" + API_KEY;
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
    /*public static void saveBooksToDatabase(JsonArray books) {
        for (int i = 0; i < books.size(); i++) {
            JsonObject book = books.get(i).getAsJsonObject();
            JsonObject volumeInfo = book.getAsJsonObject("volumeInfo");

            List<String> categoryID = getGenre(volumeInfo);
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

            Document document = new Document(String.valueOf(categoryID), publisher, lgID, title, author, isbn, quantity, availableCopies, addDate, price, description, url, image);

            if (DocumentDAO.getInstance().add(document) > 0) {
                System.out.println("Thêm sách thành công!");
            } else {
                System.out.println("Thêm sách thất bại!");
            }
        }
    }*/

    /**
     * Retrieves the title of the book from the provided JSON object.
     *
     * @param volumeInfo The {@link JsonObject} containing book information.
     * @return The title of the book, or "No title available" if not present.
     */
    private static String getTitle(JsonObject volumeInfo) {
        return volumeInfo.has("title") ? volumeInfo.get("title").getAsString() : "No title available";
    }

    /**
     * Retrieves the authors of the book from the provided JSON object.
     *
     * @param volumeInfo The {@link JsonObject} containing book information.
     * @return A comma-separated string of authors, or "Unknown author" if not present.
     */
    public static String getAuthors(JsonObject volumeInfo) {
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

    // phương thức lấy bìa sách


    /**
     * Retrieves the thumbnail link of the book's cover image.
     *
     * @param volumeInfo The {@link JsonObject} containing book information.
     * @return The thumbnail URL, or "No thumbnail available" if not present.
     */
    private static String getImageLink(JsonObject volumeInfo) {
        if (!volumeInfo.has("imageLinks")) {
            return "No thumbnail available";
        }
        JsonObject imageLinks = volumeInfo.getAsJsonObject("imageLinks");
        return imageLinks.has("thumbnail") ? imageLinks.get("thumbnail").getAsString()
                : "No thumbnail available";
    }

    /**
     * Retrieves the language of the book from the provided JSON object.
     *
     * @param volumeInfo The {@link JsonObject} containing book information.
     * @return The language code of the book, or "Unknown language" if not present.
     */
    // phương thư lấy ngôn ngữ của cuốn sách
    private static String getLanguage(JsonObject volumeInfo) {
        return volumeInfo.has("language") ? volumeInfo.get("language").getAsString()
                : "Unknown language";
    }


    /**
     * Retrieves the description of the book from the provided JSON object.
     *
     * @param volumeInfo The {@link JsonObject} containing book information.
     * @return The description of the book, or "No description available" if not present.
     */
    private static String getDescription(JsonObject volumeInfo) {
        return volumeInfo.has("description") ? volumeInfo.get("description").getAsString()
                : "No description available";
    }

    /**
     * Retrieves the genres (categories) of the book from the provided JSON object.
     *
     * @param volumeInfo The {@link JsonObject} containing book information.
     * @return A list of genres, or a list containing "No genre available" if not present.
     */  // lấy đc nhiều thể loại
    private static List<String> getGenre(JsonObject volumeInfo) {
        List<String> genres = new ArrayList<>();
        if (!volumeInfo.has("categories")) {
            genres.add("No genre available");
            return genres;
        }
        JsonArray categories = volumeInfo.getAsJsonArray("categories");
        for (int i = 0; i < categories.size(); i++) {
            genres.add(categories.get(i).getAsString());
        }
        return genres;
    }

    /**
     * Retrieves the information link (URL) of the book.
     *
     * @param volumeInfo The {@link JsonObject} containing book information.
     * @return The URL to the book's information page, or "No URL available" if not present.
     */
    private static String getInfoLink(JsonObject volumeInfo) {
        return volumeInfo.has("infoLink") ? volumeInfo.get("infoLink").getAsString()
                : "No URL available";
    }

    /**
     * Retrieves the ISBN-13 identifier of the book.
     *
     * @param volumeInfo The {@link JsonObject} containing book information.
     * @return The ISBN-13 identifier, or "No ISBN available" if not present.
     */
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

    /**
     * Retrieves the publisher of the book.
     *
     * @param volumeInfo The {@link JsonObject} containing book information.
     * @return The publisher's name, or "Unknown publisher" if not present.
     */
    private static String getPublisher(JsonObject volumeInfo) {
        return volumeInfo.has("publisher") ? volumeInfo.get("publisher").getAsString()
                : "Unknown publisher";
    }


    /**
     * Tests and prints the thumbnails of a list of books.
     *
     * @param books A {@link JsonArray} containing book data.
     */
    // Hàm kiểm tra và in ra thumbnail của từng cuốn sách
    public static void testThumbnails(JsonArray books) {
        if (books == null || books.size() == 0) {
            System.out.println("Không có sách nào để kiểm tra.");
            return;
        }

        for (int i = 0; i < books.size(); i++) {
            JsonObject book = books.get(i).getAsJsonObject();
            JsonObject volumeInfo = book.getAsJsonObject("volumeInfo");

            // Lấy liên kết thumbnail
            String thumbnail = getImageLink(volumeInfo);
            System.out.println("Sách #" + (i + 1) + ":");
            System.out.println("  Tiêu đề: " + getTitle(volumeInfo));
            System.out.println("  Thumbnail: " + thumbnail);
            System.out.println("-------------------------------");
        }
    }

}
