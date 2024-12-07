package library.management.ui.applications;

import java.io.InputStream;
import java.net.URL;
import javafx.scene.image.Image;

public class ImageDownloader {

  /**
   * Tải hình ảnh từ URL và trả về một đối tượng javafx.scene.image.Image.
   *
   * @param imageUrl URL của hình ảnh
   * @return Đối tượng Image hoặc null nếu không tải được
   */
  public static Image downloadImage(String imageUrl) {
    System.out.println(imageUrl);
    try {
      InputStream inputStream = new URL(imageUrl).openStream();
      return new Image(inputStream);
    } catch (Exception e) {
      System.err.println("Không thể tải hình ảnh từ URL: " + imageUrl);
      e.printStackTrace();
      return null;
    }
  }
}
