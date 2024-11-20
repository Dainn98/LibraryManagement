package library.management.data.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseConnection {
    private static HikariDataSource dataSource;

    // Khối khởi tạo tĩnh để cấu hình HikariCP
    static {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://localhost:3306/libdemo?useSSL=false&autoReconnect=true");
        config.setUsername("root");
        config.setPassword("");

        config.setMaximumPoolSize(10);
        config.setMinimumIdle(5);
        config.setIdleTimeout(300000);
        config.setMaxLifetime(1800000);
        config.setConnectionTimeout(30000);

        dataSource = new HikariDataSource(config);
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public static void close() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
        }
    }

    // Phương thức để lưu thông tin sách vào cơ sở dữ liệu
    public static void saveBook(String title, String authors, String description, String genre, String thumbnail, String language) {
        String insertSQL = "INSERT INTO books (title, authors, description, genre, thumbnail, language) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(insertSQL)) {
            stmt.setString(1, title);
            stmt.setString(2, authors);


            stmt.setString(3, description);
            stmt.setString(4, genre);
            stmt.setString(5, thumbnail); // Lưu đường dẫn ảnh bìa
            stmt.setString(6, language); // Lưu ngôn ngữ của sách
            stmt.executeUpdate();
            System.out.println("Book saved to database: " + title);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
