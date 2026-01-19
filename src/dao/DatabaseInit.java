package dao;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.Statement;
import java.util.stream.Collectors;

public class DatabaseInit {
    public static void initDatabase() {
        try (InputStream is = DatabaseInit.class.getClassLoader().getResourceAsStream("dao/Database.sql");
             Connection conn = DBConnection.getConnection()) {
            
            if (is == null) {
                System.err.println("Không tìm thấy file Database.sql!");
                return;
            }

            if (conn != null) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                String sql = reader.lines().collect(Collectors.joining("\n"));
                String[] queries = sql.split(";"); 
                
                try (Statement stmt = conn.createStatement()) {
                    for (String query : queries) {
                        String trimmedQuery = query.trim();
                        if (!trimmedQuery.isEmpty()) {
                            stmt.execute(trimmedQuery);
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Lỗi khởi tạo Database: " + e.getMessage());
        }
    }

}