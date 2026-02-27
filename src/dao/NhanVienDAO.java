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
            pst.setString(5, nv.getNgayVaoLam());
            pst.setString(6, nv.getSdt());
            pst.setString(7, nv.getDiaChi());
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

    public NhanVien timNhanVienTheoMa(String maNV) {
        String sql = "SELECT * FROM NhanVien WHERE MaNV = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pst = conn.prepareStatement(sql);) {
            pst.setString(1, maNV);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return new NhanVien(
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
                            rs.getBoolean("TrangThai"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean capNhatNhanVien(NhanVien nv) {
        String sql = """
                UPDATE NhanVien
                SET TenNV = ?, GioiTinh = ?, NgaySinh = ?, SDT = ?, DiaChi = ?, ChucVu = ?, TrangThai = ?
                WHERE MaNV = ?
                """;
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pst = conn.prepareStatement(sql);) {
            pst.setNString(1, nv.getTenNV());
            pst.setString(2, nv.getGioiTinh());
            pst.setString(3, nv.getNgaySinh());
            pst.setString(4, nv.getSdt());
            pst.setString(5, nv.getDiaChi());
            pst.setString(6, nv.getChucVu());
            pst.setBoolean(7, nv.getTrangThai());
            pst.setString(8, nv.getMaNV());
            return pst.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean xoaNhanVien(String maNV) {
        String sql = "DELETE FROM NhanVien WHERE MaNV = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pst = conn.prepareStatement(sql);) {
            pst.setString(1, maNV);
            return pst.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
