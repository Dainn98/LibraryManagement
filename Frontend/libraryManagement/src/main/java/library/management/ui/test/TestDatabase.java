package library.management.ui.test;

import library.management.ui.database.databaseConnection;

import java.sql.Connection;

public class TestDatabase {
    public static void main(String[] args) {
        Connection conn = databaseConnection.getConnection();
        System.out.println(conn);
        databaseConnection.close(conn);
        System.out.println(conn);
    }
}
