package util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import dao.conection.DBConnection;

public class NhapKhoUtil {
    public static String chuyenSangMaNCC(String tenncc) {
        String res = "";
        String sql = "SELECT MaNCC FROM NhaCungCap WHERE TenNCC = ?";

        try (Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, tenNCC);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                res = rs.getString("MaNCC");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return res;
    }
}
