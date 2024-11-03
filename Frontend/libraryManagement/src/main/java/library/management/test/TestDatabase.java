package library.management.test;

import library.management.database.KetNoiCSDL;

import java.sql.Connection;

public class TestDatabase {
    public static void main(String[] args) {
        Connection conn = KetNoiCSDL.getConnection();
        System.out.println(conn);
        KetNoiCSDL.close(conn);
        System.out.println(conn);
    }
}
