package dao;

import java.sql.*;
import java.util.*;
import dao.conection.DBConnection;
import dto.NhanVien;

public class NhanVienDAO {

    // 1. Lấy danh sách: Chỉ lấy những người có TrangThai = 1
    public List<NhanVien> layDanhSachNhanVien() {
        List<NhanVien> ds = new ArrayList<>();
        String sql = "SELECT * FROM NhanVien WHERE TrangThai = 1"; // Lọc trạng thái
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                ds.add(new NhanVien(
                    rs.getString("MaNV"),
                    rs.getNString("TenNV"),
                    rs.getNString("GioiTinh"),
                    rs.getString("NgaySinh"),
                    rs.getString("SDT"),
                    rs.getNString("DiaChi"),
                    rs.getNString("ChucVu"),
                    rs.getString("TaiKhoan"),
                    rs.getNString("Anh"),
                    rs.getBoolean("TrangThai")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ds;
    }

    // 2. Thêm nhân viên: Luôn gán TrangThai = 1
    public Boolean themNhanVien(NhanVien nv) {
        String sql = """
                INSERT INTO NhanVien (MaNV, TenNV, GioiTinh, NgaySinh, SDT, DiaChi, ChucVu, TaiKhoan, Anh, TrangThai)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, 1) -- Ép cứng trạng thái = 1
                """;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            
            nv.setMaNV(layMaNhanVien());
            
            pst.setString(1, nv.getMaNV());
            pst.setNString(2, nv.getTenNV());
            pst.setNString(3, nv.getGioiTinh());
            pst.setString(4, nv.getNgaySinh());
            pst.setString(5, nv.getSdt());
            pst.setNString(6, nv.getDiaChi());
            pst.setNString(7, nv.getChucVu());
            pst.setString(8, nv.getTaiKhoan());
            pst.setNString(9, nv.getAnh());
            // Không cần set tham số thứ 10 vì SQL đã để mặc định là 1

            return pst.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 3. Tìm kiếm: Chỉ tìm người có TrangThai = 1
    public NhanVien timNhanVienTheoMa(String maNV) {
        String sql = "SELECT * FROM NhanVien WHERE MaNV = ? AND TrangThai = 1";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, maNV);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return new NhanVien(
                        rs.getString("MaNV"),
                        rs.getNString("TenNV"),
                        rs.getNString("GioiTinh"),
                        rs.getString("NgaySinh"),
                        rs.getString("SDT"),
                        rs.getNString("DiaChi"),
                        rs.getNString("ChucVu"),
                        rs.getString("TaiKhoan"),
                        rs.getNString("Anh"),
                        rs.getBoolean("TrangThai")
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 4. Xóa nhân viên: Cập nhật TrangThai thành 0 (Soft Delete)
    public boolean xoaNhanVien(String maNV) {
        String sql = "UPDATE NhanVien SET TrangThai = 0 WHERE MaNV = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, maNV);
            return pst.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Các hàm phụ trợ giữ nguyên
    public String layMaNhanVien() {
        String sql = "SELECT MAX(CAST(SUBSTRING(MaNV, 3, LEN(MaNV)) AS INT)) FROM NhanVien";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
            if (rs.next()) {
                return String.format("NV%02d", rs.getInt(1) + 1);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return "NV01";
    }

    public List<String> layDanhSachChucVu() {
        List<String> ds = new ArrayList<>();
        String sql = "SELECT TenNhomQuyen FROM NhomQuyen";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) ds.add(rs.getString("TenNhomQuyen"));
        } catch (Exception e) { e.printStackTrace(); }
        return ds;
    }

    public boolean capNhatNhanVien(NhanVien nv) {
        String sql = """
                UPDATE NhanVien
                SET TenNV = ?, GioiTinh = ?, NgaySinh = ?, SDT = ?, DiaChi = ?, ChucVu = ?, Anh = ?
                WHERE MaNV = ? AND TrangThai = 1
                """;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setNString(1, nv.getTenNV());
            pst.setNString(2, nv.getGioiTinh());
            pst.setString(3, nv.getNgaySinh());
            pst.setString(4, nv.getSdt());
            pst.setNString(5, nv.getDiaChi());
            pst.setNString(6, nv.getChucVu());
            pst.setNString(7, nv.getAnh());
            pst.setString(8, nv.getMaNV());
            return pst.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }
}