package dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    private static final String URL = "wfwf";
    private static String taiKhoan;
    private static String matKhau;

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, taiKhoan, matKhau);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}
