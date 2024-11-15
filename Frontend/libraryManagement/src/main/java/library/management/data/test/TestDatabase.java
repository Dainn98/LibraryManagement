package library.management.data.test;

import library.management.data.database.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;

public class TestDatabase {
    public static void main(String[] args) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            if (conn != null) {
                System.out.println("Kết nối thành công: " + conn);
            } else {
                System.out.println("Kết nối thất bại.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
