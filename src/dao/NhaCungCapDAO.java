package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import dto.NhaCungCap;

public class NhaCungCapDAO {
    public NhaCungCap timNhaCungCap(String maNCC) {
        NhaCungCap nhaCungCap = null;

        String sql = "SELECT * FROM NhaCungCap";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pst = conn.prepareStatement(sql)) {
            ResultSet rs = pst.executeQuery();
            nhaCungCap = new NhaCungCap();
            while (rs.next()) {
                if (rs.getString("MaNCC").equals(maNCC)) {
                    nhaCungCap.setMaNCC(rs.getString("MaNCC"));
                    nhaCungCap.setTenNCC(rs.getString("TenNCC"));
                    nhaCungCap.setSoDienThoai(rs.getString("SoDienThoai"));
                    nhaCungCap.setTrangThai(rs.getBoolean("TrangThai"));
                    nhaCungCap.setDiaChi(rs.getString("DiaChi"));
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lỗi truy vấn sản phẩm: " + e.getMessage());
        }
        return nhaCungCap;
    }
}
