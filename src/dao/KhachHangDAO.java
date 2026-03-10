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
                        rs.getString("MaHang"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lỗi truy vấn khách hàng theo SDT: " + e.getMessage());
        }
        return kh;
    }

    public String layMaKhachHangKhaDung() {
        String sql = "SELECT COUNT(MaKH) FROM KhachHang";

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

            kh.setMaKH(layMaKhachHangKhaDung());

            pst.setString(1, kh.getMaKH());
            pst.setString(2, kh.getTenKH());
            pst.setString(3, kh.getGioiTinh());
            pst.setString(4, kh.getSdt());
            pst.setDouble(5, kh.getTenDaMua());
            pst.setString(6, kh.getMaHang());
            pst.setInt(7, 1);
            return pst.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lỗi thêm KhachHang: " + e.getMessage());
            return false;
        }
    }

    public ArrayList<KhachHang> layDanhSachKhachHang() {
        ArrayList<KhachHang> ds = new ArrayList<>();
        String sql = "SELECT * FROM KhachHang WHERE TrangThai = 1";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pst = conn.prepareStatement(sql);
                ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                KhachHang kh = new KhachHang(
                        rs.getString("MaKH"),
                        rs.getNString("TenKH"),
                        rs.getNString("GioiTinh"),
                        rs.getString("SDT"),
                        rs.getDouble("TenDaMua"),
                        rs.getString("MaHang"));
                ds.add(kh);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lỗi truy vấn danh sách khách hàng: " + e.getMessage());
        }
        return ds;
    }

    public KhachHang layKhachHangTheoMa(String maKH) {
        KhachHang kh = null;
        String sql = "SELECT * FROM KhachHang WHERE MaKH = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, maKH);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                kh = new KhachHang(
                        rs.getString("MaKH"),
                        rs.getNString("TenKH"),
                        rs.getNString("GioiTinh"),
                        rs.getString("SDT"),
                        rs.getDouble("TenDaMua"),
                        rs.getString("MaHang"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lỗi truy vấn KhachHang theo mã: " + e.getMessage());
        }
        return kh;
    }

    public boolean capNhatKhachHang(KhachHang kh) {
        String sql = "UPDATE KhachHang SET TenKH = ?, GioiTinh = ?, SDT = ?, TenDaMua = ?, MaHang = ? WHERE MaKH = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, kh.getTenKH());
            pst.setString(2, kh.getGioiTinh());
            pst.setString(3, kh.getSdt());
            pst.setDouble(4, kh.getTenDaMua());
            pst.setString(5, kh.getMaHang());
            pst.setString(6, kh.getMaKH());
            return pst.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lỗi cập nhật KhachHang: " + e.getMessage());
            return false;
        }
    }

    public boolean xoaKhachHang(String maKH) {
        String sql = "UPDATE KhachHang SET TrangThai = 0 WHERE MaKH = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, maKH);
            return pst.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lỗi xóa KhachHang: " + e.getMessage());
            return false;
        }
    }

    public boolean capNhatTienDaMua(String maKH, double tienThem) {
        String sql = "UPDATE KhachHang SET TenDaMua = ISNULL(TenDaMua, 0) + ? WHERE MaKH = ?";
        try (java.sql.Connection conn = dao.conection.DBConnection.getConnection();
                java.sql.PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setDouble(1, tienThem);
            pst.setString(2, maKH);
            return pst.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
