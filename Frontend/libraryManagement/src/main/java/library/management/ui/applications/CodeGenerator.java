package library.management.ui.applications;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class CodeGenerator {

    /**
     * Tạo mã QR từ URL và trả về JavaFX Image.
     * @param url URL cần mã hóa thành QR code.
     * @param width Chiều rộng mã QR.
     * @param height Chiều cao mã QR.
     * @return Image của mã QR.
     * @throws Exception Nếu có lỗi xảy ra khi tạo mã QR.
     */
    public static Image generateQRCode(String url, int width, int height) throws Exception {
        // Thiết lập cấu hình cho QR code
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        hints.put(EncodeHintType.MARGIN, 1);

        // Tạo mã QR từ URL
        BitMatrix bitMatrix = new MultiFormatWriter().encode(
                url, BarcodeFormat.QR_CODE, width, height, hints);

        // Chuyển BitMatrix thành BufferedImage
        BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);

        // Chuyển BufferedImage thành JavaFX Image
        return SwingFXUtils.toFXImage(bufferedImage, null);
    }

    /**
     * Tạo mã vạch EAN-13 với số ISBN và văn bản hiển thị, trả về JavaFX Image.
     * @param isbn ISBN cần tạo mã vạch.
     * @param width Chiều rộng mã vạch.
     * @param height Chiều cao mã vạch (không bao gồm văn bản).
     * @return JavaFX Image chứa mã vạch với văn bản.
     * @throws Exception Nếu có lỗi xảy ra khi tạo mã vạch.
     */
    public static Image generateBarcodeWithText(String isbn, int width, int height) throws Exception {
        // Tạo mã vạch từ ZXing
        BitMatrix bitMatrix = new MultiFormatWriter().encode(
                isbn, BarcodeFormat.EAN_13, width, height);

        // Tạo mã vạch dưới dạng BufferedImage
        MatrixToImageConfig config = new MatrixToImageConfig(MatrixToImageConfig.BLACK, MatrixToImageConfig.WHITE);
        BufferedImage barcodeImage = MatrixToImageWriter.toBufferedImage(bitMatrix, config);

        // Tạo hình ảnh cuối cùng (bao gồm mã vạch và văn bản)
        int fullHeight = height + 60; // Thêm khoảng trống cho văn bản
        BufferedImage combinedImage = new BufferedImage(width, fullHeight, BufferedImage.TYPE_INT_RGB);

        // Vẽ mã vạch và văn bản lên BufferedImage
        Graphics2D g2d = combinedImage.createGraphics();
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, width, fullHeight); // Nền trắng
        g2d.drawImage(barcodeImage, 0, 20, null); // Vẽ mã vạch (offset 20px)

        // Thêm văn bản phía trên và dưới mã vạch
        g2d.setColor(Color.GRAY); // Văn bản màu xám
        g2d.setFont(new Font("Arial", Font.PLAIN, 14));

        // Văn bản ISBN (phía trên mã vạch)
        String isbnText = "ISBN " + isbn.substring(0, 3) + "-" + isbn.substring(3, 4) + "-" +
                isbn.substring(4, 9) + "-" + isbn.substring(9, 12) + "-" + isbn.substring(12);
        g2d.drawString(isbnText, (width - g2d.getFontMetrics().stringWidth(isbnText)) / 2, 15);

        // Văn bản mã số ISBN (phía dưới mã vạch)
        g2d.setFont(new Font("Arial", Font.PLAIN, 16));
        g2d.drawString(isbn, (width - g2d.getFontMetrics().stringWidth(isbn)) / 2, height + 45);

        g2d.dispose();

        // Chuyển đổi BufferedImage thành JavaFX Image và trả về
        return SwingFXUtils.toFXImage(combinedImage, null);
    }
}