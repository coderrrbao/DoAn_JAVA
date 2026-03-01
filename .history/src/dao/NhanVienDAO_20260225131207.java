package dao;

import java.sql.*;
// import java.awt.;
import java.util.*;

import dao.conection.DBConnection;
import dto.NhanVien;

public class NhanVienDAO {
    public List<String> layDanhSachChucVu() {
        List<String> ds = new ArrayList<>();
        String sql = "SELECT TenNhomQuyen FROM NhomQuyen";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pst = conn.prepareStatement(sql);
                ResultSet rs = pst.executeQuery();) {
            while (rs.next()) {
                ds.add(rs.getString("TenNhomQuyen"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ds;
    }

    public String layMaNhanVien() {
        String sql = "SELECT COUNT(MaNV) FROM NhanVien";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pst = conn.prepareStatement(sql);
                ResultSet rs = pst.executeQuery();) {
            if (rs.next()) {
                int num = rs.getInt(1) + 1;
                String ma = String.format("%02d", num);
                return "NV" + ma;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public Boolean themNhanVien(NhanVien nv) {
        String sql = """
                INSERT INTO NhanVien (MaNV, TenNV, GioiTinh, NgaySinh, SDT, DiaChi, ChucVu, TaiKhoan, TrangThai)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pst = conn.prepareStatement(sql);) {
                    nv.setMaNV(layMaNhanVien());
                    pst.setString(0, nv.getMaNV());
                    pst.setNString(1, nv.getTenNV());
                    pst.setString(2, nv.getMaNV());
                    pst.setString(3, nv.getMaNV());
                    pst.setString(0, nv.getMaNV());
                    pst.setString(0, nv.getMaNV());
                    pst.setString(0, nv.getMaNV());
                    pst.setString(0, nv.getMaNV());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}
