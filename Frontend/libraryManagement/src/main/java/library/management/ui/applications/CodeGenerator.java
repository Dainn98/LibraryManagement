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

    public static Image generateQRCode(String url, int width, int height) throws Exception {
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        hints.put(EncodeHintType.MARGIN, 1);
        BitMatrix bitMatrix = new MultiFormatWriter().encode(
                url, BarcodeFormat.QR_CODE, width, height, hints);
        BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
        return SwingFXUtils.toFXImage(bufferedImage, null);
    }

    public static Image generateBarcodeWithText(String isbn, int width, int height) throws Exception {
        BitMatrix bitMatrix = new MultiFormatWriter().encode(
                isbn, BarcodeFormat.EAN_13, width, height);
        MatrixToImageConfig config = new MatrixToImageConfig(MatrixToImageConfig.BLACK, MatrixToImageConfig.WHITE);
        BufferedImage barcodeImage = MatrixToImageWriter.toBufferedImage(bitMatrix, config);

        int fullHeight = height + 60;
        BufferedImage combinedImage = new BufferedImage(width, fullHeight, BufferedImage.TYPE_INT_RGB);

        Graphics2D g2d = combinedImage.createGraphics();
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, width, fullHeight);
        g2d.drawImage(barcodeImage, 0, 20, null);

        g2d.setColor(Color.GRAY);
        g2d.setFont(new Font("Arial", Font.PLAIN, 14));

        String isbnText = "ISBN " + isbn.substring(0, 3) + "-" + isbn.charAt(3) + "-" +
                isbn.substring(4, 9) + "-" + isbn.substring(9, 12) + "-" + isbn.substring(12);
        g2d.drawString(isbnText, (width - g2d.getFontMetrics().stringWidth(isbnText)) / 2, 15);

        g2d.setFont(new Font("Arial", Font.PLAIN, 16));
        g2d.drawString(isbn, (width - g2d.getFontMetrics().stringWidth(isbn)) / 2, height + 45);

        g2d.dispose();

        return SwingFXUtils.toFXImage(combinedImage, null);
    }
}