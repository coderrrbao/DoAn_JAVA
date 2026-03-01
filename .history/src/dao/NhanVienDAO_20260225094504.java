package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
// import java.awt.;
import java.util.*;

import dao.conection.DBConnection;

public class NhanVienDAO {
    public List<String> layDanhSachChucVu() {
        List<String> ds = new ArrayList<>();
        String sql = "SELECT ";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pst = conn.prepareStatement(sql);
                ResultSet rs = pst.executeQuery();) {
                    String cv = rs.getString("TenNhomQuyen");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ds;
    }
}
