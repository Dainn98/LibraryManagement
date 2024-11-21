package library.management.data.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseConnection {
    private static HikariDataSource dataSource;

    // Khối khởi tạo tĩnh để cấu hình HikariCP
    static {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://localhost:3306/libdemo?useSSL=false&autoReconnect=true");
        config.setUsername("root");

        config.setPassword("Pdthien432005~");

//        config.setJdbcUrl("jdbc:mysql://bhdhbvbnzgx0wns50jsy-mysql.services.clever-cloud.com:3306/bhdhbvbnzgx0wns50jsy?useSSL=false&autoReconnect=true");
//        config.setUsername("ueqiv7zizvzrfyya");
//        config.setPassword("lhzioZP31QXOKAZNuVFM");

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
}
