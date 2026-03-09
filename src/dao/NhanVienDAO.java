package dao;

import java.sql.*;
import java.util.*;
import dao.conection.DBConnection;
import dto.NhanVien;

public class NhanVienDAO {

    public ArrayList<NhanVien> layDanhSachNhanVien() {
        ArrayList<NhanVien> ds = new ArrayList<>();
        String sql = "SELECT * FROM NhanVien WHERE TrangThai = 1";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pst = conn.prepareStatement(sql);
                ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {

                NhanVien nv = new NhanVien(
                        rs.getString("MaNV"),
                        rs.getString("TenNV"),
                        rs.getString("GioiTinh"),
                        rs.getString("NgaySinh"),
                        rs.getString("SDT"),
                        rs.getString("DiaChi"),
                        rs.getString("Anh"));
                ds.add(nv);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ds;
    }

    public boolean insert(Connection conn, NhanVien nv) throws SQLException {
        String sql = """
                INSERT INTO NhanVien
                (MaNV, TenNV, GioiTinh, NgaySinh, SDT, DiaChi, Anh, TrangThai)
                VALUES (?, ?, ?, ?, ?, ?, ?, 1)
                """;

        if (nv.getMaNV() == null || nv.getMaNV().isEmpty()) {
            nv.setMaNV(layMaNhanVien());
        }

        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, nv.getMaNV());
            pst.setNString(2, nv.getTenNV());
            pst.setNString(3, nv.getGioiTinh());
            pst.setString(4, nv.getNgaySinh());
            pst.setString(5, nv.getSdt());
            pst.setNString(6, nv.getDiaChi());
            pst.setString(7, nv.getAnh());

            return pst.executeUpdate() > 0;
        }
    }

    public boolean exist(String maNV) {
        String sql = "SELECT 1 FROM NhanVien WHERE MaNV = ? AND TrangThai = 1";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maNV);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public Boolean themNhanVien(NhanVien nv) {
        String sql = """
                INSERT INTO NhanVien (MaNV, TenNV, GioiTinh, NgaySinh, SDT, DiaChi, Anh, TrangThai)
                VALUES (?, ?, ?, ?, ?, ?, ?, 1)
                """;
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pst = conn.prepareStatement(sql)) {

            if (nv.getMaNV() == null || nv.getMaNV().isEmpty()) {
                nv.setMaNV(layMaNhanVien());
            }

            pst.setString(1, nv.getMaNV());
            pst.setNString(2, nv.getTenNV());
            pst.setNString(3, nv.getGioiTinh());
            pst.setString(4, nv.getNgaySinh());
            pst.setString(5, nv.getSdt());
            pst.setNString(6, nv.getDiaChi());
            pst.setNString(7, nv.getAnh());

            return pst.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public NhanVien timNhanVien(String maNV) {
        String sql = "SELECT * FROM NhanVien WHERE MaNV = ? AND TrangThai = 1";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, maNV);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return new NhanVien(
                            rs.getString("MaNV"),
                            rs.getString("TenNV"),
                            rs.getString("GioiTinh"),
                            rs.getString("NgaySinh"),
                            rs.getString("SDT"),
                            rs.getString("DiaChi"),
                            rs.getString("Anh"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean xoaNhanVien(String maNV) {
        String sql = "UPDATE NhanVien SET TrangThai = 0 WHERE MaNV = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, maNV);
            return pst.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public String layMaNhanVien() {
        String sql = "SELECT MAX(CAST(SUBSTRING(MaNV, 3, LEN(MaNV)) AS INT)) FROM NhanVien";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pst = conn.prepareStatement(sql);
                ResultSet rs = pst.executeQuery()) {

            if (rs.next()) {
                int nextId = rs.getInt(1) + 1;
                return String.format("NV%02d", nextId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "NV01";
    }

    public boolean capNhatNhanVien(NhanVien nv) {
        String sql = """
                UPDATE NhanVien
                SET TenNV = ?, GioiTinh = ?, NgaySinh = ?, SDT = ?, DiaChi = ?, Anh = ?
                WHERE MaNV = ? AND TrangThai = 1
                """;
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setNString(1, nv.getTenNV());
            pst.setNString(2, nv.getGioiTinh());
            pst.setString(3, nv.getNgaySinh());
            pst.setString(4, nv.getSdt());
            pst.setNString(5, nv.getDiaChi());
            pst.setNString(6, nv.getAnh());
            pst.setString(7, nv.getMaNV());

            return pst.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}