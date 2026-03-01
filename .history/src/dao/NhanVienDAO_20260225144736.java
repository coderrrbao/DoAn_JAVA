package dao;

import java.sql.*;
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
                INSERT INTO NhanVien (MaNV, TenNV, GioiTinh, NgaySinh, NgayVaoLam, SDT, DiaChi, ChucVu, TaiKhoan, TrangThai)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pst = conn.prepareStatement(sql);) {
            nv.setMaNV(layMaNhanVien());
            pst.setString(1, nv.getMaNV());
            pst.setNString(2, nv.getTenNV());
            pst.setString(3, nv.getGioiTinh());
            pst.setString(4, nv.getNgaySinh());
            pst.setString(4, nv.getNgayVaoLam());
            pst.setString(5, nv.getSdt());
            pst.setString(6, nv.getDiaChi());
            pst.setString(8, nv.getChucVu());
            pst.setString(9, null);
            pst.setBoolean(10, nv.getTrangThai());

            return pst.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<NhanVien> layDanhSachNhanVien() {
        ArrayList<NhanVien> ds = new ArrayList<>();
        String sql = "SELECT * FROM NhanVien";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pst = conn.prepareStatement(sql);
                ResultSet rs = pst.executeQuery();) {
            while (rs.next()) {
                NhanVien nv = new NhanVien(
                        rs.getString("MaNV"),
                        rs.getString("TenNV"),
                        rs.getString("GioiTinh"),
                        rs.getString("NgaySinh"),
                        rs.getString("NgayVaoLam"),
                        rs.getString("SDT"),
                        rs.getString("DiaChi"),
                        rs.getString("ChucVu"),
                        rs.getString("TaiKhoan"),
                        null,
                        rs.getBoolean("TrangThai")
                    );

                ds.add(nv);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ds;
    }
}
