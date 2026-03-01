package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
// import java.awt.;
import java.util.*;

import dao.conection.DBConnection;

public class NhanVienDAO {
    public List<String> layDanhSachChucVu() {
        List<String> ds = new ArrayList<>();
        String sql = "";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pst = conn.prepareStatement(sql)) {

        } catch (Exception e) {
            // TODO: handle exception
        }
        return ds;
    }
}
