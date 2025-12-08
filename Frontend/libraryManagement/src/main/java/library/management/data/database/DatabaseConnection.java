package library.management.data.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * This class is responsible for managing the connection to a MySQL database using HikariCP, a
 * high-performance JDBC connection pool library for Java.
 *
 * <p> The class follows the singleton pattern to maintain a single connection object for the
 * entire
 * application. It configures the connection parameters including URL, username, password, and pool
 * settings.
 * </p>
 */
public class DatabaseConnection {

  private static final HikariDataSource dataSource;

  static {
    HikariConfig config = new HikariConfig();
    config.setJdbcUrl("jdbc:mysql://localhost:3307/libdemo?useSSL=false&autoReconnect=true");
    config.setUsername("root");
    config.setPassword("@Tuananh1275");
    config.setMaximumPoolSize(10);
    config.setMinimumIdle(5);
    config.setIdleTimeout(300000);
    config.setMaxLifetime(1800000);
    config.setConnectionTimeout(30000);

    dataSource = new HikariDataSource(config);
  }

  /**
   * Retrieves a connection from the connection pool.
   *
   * @return a {@link Connection} object connected to the database.
   * @throws SQLException if a connection to the database cannot be established.
   */
  public static Connection getConnection() throws SQLException {
    return dataSource.getConnection();
  }

  /**
   * Closes the connection pool if it is not already closed. This method should be called when the
   * application no longer needs a connection to the database.
   */
  public static void close() {
    if (dataSource != null && !dataSource.isClosed()) {
      dataSource.close();
    }
  }
}
