package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import dao.conection.DBConnection;
import dto.KhachHang;

public class KhachHangDAO {
    public KhachHang layKhachHangTheoSDT(String sdt) {
        KhachHang kh = null;
        String sql = "SELECT * FROM KhachHang WHERE TrangThai = 1 AND SDT = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, sdt);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                kh = new KhachHang(
                        rs.getString("MaKH"),
                        rs.getNString("TenKH"),
                        rs.getNString("GioiTinh"),
                        rs.getString("SDT"),
                        rs.getDouble("TenDaMua"),
                        rs.getString("MaHang"),
                        rs.getBoolean("TrangThai")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lỗi truy vấn khách hàng theo SDT: " + e.getMessage());
        }
        return kh;
    }



}
