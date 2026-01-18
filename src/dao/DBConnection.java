package dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    private static final String URL = "jdbc:sqlserver://127.0.0.1:1433;"
                                    + "databaseName=QL_NUOC;" 
                                    + "encrypt=true;"
                                    + "trustServerCertificate=true;";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, "sa", "123456");
        } catch (Exception e) {
            System.err.println("Lỗi kết nối Java: " + e.getMessage());
            return null;
        }
    }
}