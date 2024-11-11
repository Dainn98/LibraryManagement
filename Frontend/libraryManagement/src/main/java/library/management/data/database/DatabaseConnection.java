package library.management.data.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

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
        config.setPassword("20022005aB!");

        // Các cấu hình tối ưu cho HikariCP
        config.setMaximumPoolSize(10); // Số lượng kết nối tối đa trong bể
        config.setMinimumIdle(5); // Số lượng kết nối tối thiểu luôn tồn tại trong bể
        config.setIdleTimeout(300000); // Thời gian chờ tối đa để một kết nối không hoạt động trước khi bị đóng (300 giây)
        config.setMaxLifetime(1800000); // Thời gian sống tối đa của một kết nối (1800 giây)
        config.setConnectionTimeout(30000); // Thời gian chờ để lấy kết nối (30 giây)

        // Khởi tạo HikariDataSource với cấu hình trên
        dataSource = new HikariDataSource(config);
    }

    // Phương thức để lấy kết nối từ bể kết nối
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    // Phương thức để đóng HikariDataSource khi ứng dụng kết thúc
    public static void close() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
        }
    }

    // Phương thức để lưu thông tin sách vào cơ sở dữ liệu
    public static void saveBook(String title, String authors, String description, String genre) {
        String insertSQL = "INSERT INTO books (title, authors, description, genre) VALUES (?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(insertSQL)) {
            stmt.setString(1, title);
            stmt.setString(2, authors);
            stmt.setString(3, description);
            stmt.setString(4, genre);
            stmt.executeUpdate();
            System.out.println("Book saved to database: " + title);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
