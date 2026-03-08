package dao;

import dto.HangThanhVien;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dao.conection.DBConnection;

public class HangThanhVienDAO {
    public ArrayList<HangThanhVien> layListHangThanhVien() {
        ArrayList<HangThanhVien> listHTV = new ArrayList<>();
        String sql = "SELECT MaHang, TenHang, PhanTramGiam, DieuKien FROM HangThanhVien WHERE TrangThai = 1";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pst = conn.prepareStatement(sql);
                ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                HangThanhVien htv = new HangThanhVien();
                htv.setMaHang(rs.getString("MaHang"));
                htv.setTenHang(rs.getNString("TenHang"));
                htv.setPhanTramGiam(rs.getInt("PhanTramGiam"));
                htv.setDieuKien(rs.getDouble("DieuKien"));
                listHTV.add(htv);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listHTV;
    }

    public boolean themHangThanhVien(HangThanhVien htv, Connection conn) {
        String sql = "INSERT INTO HangThanhVien (MaHang, TenHang, PhanTramGiam, DieuKien, TrangThai) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            if (htv.getMaHang() == null || htv.getMaHang().trim().isEmpty()) {
                htv.setMaHang(layMaHangThanhVienKhaDung(conn));
            }
            pst.setString(1, htv.getMaHang());
            pst.setString(2, htv.getTenHang());
            pst.setInt(3, htv.getPhanTramGiam());
            pst.setDouble(4, htv.getDieuKien());
            pst.setInt(5, 1); // Set trạng thái = 1 khi thêm mới

            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean capNhatHangThanhVien(HangThanhVien htv, Connection conn) {
        String sql = "UPDATE HangThanhVien SET TenHang = ?, PhanTramGiam = ?, DieuKien = ? WHERE MaHang = ?";

        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, htv.getTenHang());
            pst.setInt(2, htv.getPhanTramGiam());
            pst.setDouble(3, htv.getDieuKien());
            pst.setString(4, htv.getMaHang());

            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean xoaHangThanhVien(String maHang, Connection conn) {
        String sql = "UPDATE HangThanhVien SET TrangThai = 0 WHERE MaHang = ?"; // Xóa mềm
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, maHang);
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String layMaHangThanhVienKhaDung(Connection conn) {
        String sql = "SELECT COUNT(MaHang) FROM HangThanhVien";
        try (PreparedStatement pst = conn.prepareStatement(sql);
                ResultSet rs = pst.executeQuery()) {
            if (rs.next()) {
                int so = rs.getInt(1) + 1;
                return "HTV" + String.format("%02d", so);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "HTV01";
    }

    public void insert(Connection conn, HangThanhVien h) throws Exception {

        String sql = """
                    INSERT INTO HangThanhVien
                    (MaHang, TenHang, PhanTramGiam, DieuKien, TrangThai)
                    VALUES (?, ?, ?, ?, ?)
                """;

        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setString(1, h.getMaHang());
        ps.setString(2, h.getTenHang());
        ps.setInt(3, h.getPhanTramGiam());
        ps.setDouble(4, h.getDieuKien());
        ps.setInt(5, 1);

        ps.executeUpdate();
    }

    public boolean exists(Connection conn, String maHang) throws Exception {

        String sql = "SELECT 1 FROM HangThanhVien WHERE MaHang = ?";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, maHang);

        ResultSet rs = ps.executeQuery();

        return rs.next();
    }
}