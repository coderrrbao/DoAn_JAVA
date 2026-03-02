package dao;

import java.sql.*;
import java.util.*;
import dao.conection.DBConnection;
import dto.NhanVien;
import dto.TaiKhoan;

public class NhanVienDAO {

    // 1. Lấy danh sách: Chỉ lấy những người có TrangThai = 1
    public ArrayList<NhanVien> layDanhSachNhanVien() {
        ArrayList<NhanVien> ds = new ArrayList<>();
        String sql = "SELECT * FROM NhanVien WHERE TrangThai = 1"; 
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                TaiKhoan taiKhoan = new TaiKhoan();
                // Giả sử cột trong DB lưu mã tài khoản là "TaiKhoan"
                taiKhoan.setMaTK(rs.getString("TaiKhoan"));
                
                ds.add(new NhanVien(
                        rs.getString("MaNV"),
                        rs.getNString("TenNV"),
                        rs.getNString("GioiTinh"),
                        rs.getString("NgaySinh"),
                        rs.getString("SDT"),
                        rs.getNString("DiaChi"),
                        taiKhoan,
                        rs.getNString("Anh")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ds;
    }

    // 2. Thêm nhân viên: Luôn gán TrangThai = 1, bỏ ChucVu
    public Boolean themNhanVien(NhanVien nv) {
        String sql = """
                INSERT INTO NhanVien (MaNV, TenNV, GioiTinh, NgaySinh, SDT, DiaChi, TaiKhoan, Anh, TrangThai)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, 1)
                """;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            // Nếu bạn chưa gán mã trước khi gọi hàm này
            if (nv.getMaNV() == null || nv.getMaNV().isEmpty()) {
                nv.setMaNV(layMaNhanVien());
            }

            pst.setString(1, nv.getMaNV());
            pst.setNString(2, nv.getTenNV());
            pst.setNString(3, nv.getGioiTinh());
            pst.setString(4, nv.getNgaySinh());
            pst.setString(5, nv.getSdt());
            pst.setNString(6, nv.getDiaChi());
            // Lấy TenDangNhap hoặc MaTK tùy vào thiết kế cột TaiKhoan của bạn
            pst.setString(7, nv.getTaiKhoan() != null ? nv.getTaiKhoan().getMaTK() : null);
            pst.setNString(8, nv.getAnh());

            return pst.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 3. Tìm kiếm: Chỉ tìm người có TrangThai = 1
    public NhanVien timNhanVien(String maNV) {
        String sql = "SELECT * FROM NhanVien WHERE MaNV = ? AND TrangThai = 1";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, maNV);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    TaiKhoan taiKhoan = new TaiKhoan();
                    taiKhoan.setMaTK(rs.getString("TaiKhoan"));
                    
                    return new NhanVien(
                            rs.getString("MaNV"),
                            rs.getNString("TenNV"),
                            rs.getNString("GioiTinh"),
                            rs.getString("NgaySinh"),
                            rs.getString("SDT"),
                            rs.getNString("DiaChi"),
                            taiKhoan,
                            rs.getNString("Anh"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 4. Xóa nhân viên (Xóa mềm bằng cách update TrangThai)
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

    // 5. Tự động lấy mã nhân viên tiếp theo
    public String layMaNhanVien() {
        String sql = "SELECT MAX(CAST(SUBSTRING(MaNV, 3, LEN(MaNV)) AS INT)) FROM NhanVien";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
            if (rs.next()) {
                return String.format("NV%02d", rs.getInt(1) + 1);
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
        }
        return false;
    }
}