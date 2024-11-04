package library.management.data.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class KetNoiCSDL {
    private KetNoiCSDL() {
        // Đảm bảo lớp không thể bị khởi tạo từ bên ngoài
    }

    public static Connection getConnection() {
        Connection con = null;
        try {
            String url = "jdbc:mysql://localhost:3306/libdemo?useSSL=false&autoReconnect=true";
            String user = "root";
            String password = "Pdthien432005~";
            con = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }

    public static void close(Connection con) {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
