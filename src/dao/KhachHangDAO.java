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
                        rs.getBoolean("TrangThai"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lỗi truy vấn khách hàng theo SDT: " + e.getMessage());
        }
        return kh;
    }

    public String layMaKhachHangKhaDung() {
        String sql = "SELECT COUNT(*) FROM KhachHang";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pst = conn.prepareStatement(sql)) {

            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                int so = rs.getInt(1) + 1;
                String ma = String.format("%03d", so);
                return "KH" + ma;
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lỗi truy vấn Khách hàng: " + e.getMessage());
        }
        return "";
    }

    public boolean themKhachHang(KhachHang kh) {
        String sql = "INSERT INTO KhachHang (MaKH, TenKH, GioiTinh, SDT, TenDaMua, MaHang, TrangThai) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement pst = conn.prepareStatement(sql)) {
            if (kh.getMaKH() == null || kh.getMaKH().trim().isEmpty()) {
                kh.setMaKH(layMaKhachHangKhaDung());
            }
            pst.setString(1, kh.getMaKH());
            pst.setString(2, kh.getTenKH());
            pst.setString(3, kh.getGioiTinh());
            pst.setString(4, kh.getSdt());
            pst.setDouble(5, kh.getTenDaMua());
            pst.setString(6, kh.getMaHang());
            pst.setInt(7, kh.getTrangThai() ? 1 : 0);
            return pst.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lỗi thêm KhachHang: " + e.getMessage());
            return false;
        }
    }

}
