package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import dao.conection.DBConnection;
import dto.NhomQuyen;
import dto.TaiKhoan;

public class TaiKhoanDao {


    public boolean themTaiKhoan(TaiKhoan tk, Connection conn) {
        String sql = "INSERT INTO TaiKhoan (MaTK, MaNV, TenDangNhap, MatKhau, maNQ, TrangThaiXuLy, TrangThai) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, tk.getMaTK()); 
            ps.setString(2, tk.getMaNV());
            ps.setString(3, tk.getTenDangNhap());
            ps.setString(4, tk.getMatKhau());
            ps.setString(5, tk.getNhomQuyen() != null ? tk.getNhomQuyen().getMaNQ() : null);
            ps.setString(6,  "Đã xác nhận");
            ps.setInt(7, 1); 

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean xoaTaiKhoan(String tenDangNhap, Connection conn) {
        String sql = "UPDATE TaiKhoan SET TrangThai = 0 WHERE TenDangNhap = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, tenDangNhap);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Thay đổi mật khẩu
    public boolean suaMatKhau(String tenDangNhap, String matKhauMoi, Connection conn) {
        String sql = "UPDATE TaiKhoan SET MatKhau = ? WHERE TenDangNhap = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, matKhauMoi);
            ps.setString(2, tenDangNhap);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Lấy danh sách tài khoản
    public ArrayList<TaiKhoan> layDanhSachTaiKhoan() {
        ArrayList<TaiKhoan> ds = new ArrayList<>();
        String sql = "SELECT * FROM TaiKhoan";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                TaiKhoan tk = new TaiKhoan();
                tk.setMaTK(rs.getString("MaTK")); 
                tk.setMaNV(rs.getString("MaNV"));
                tk.setTenDangNhap(rs.getString("TenDangNhap"));
                tk.setMatKhau(rs.getString("MatKhau"));

                NhomQuyen nhomQuyen = new NhomQuyen();
                nhomQuyen.setMaNQ(rs.getString("MaNQ"));
                tk.setNhomQuyen(nhomQuyen);

                tk.setTrangThaiXuLy(rs.getString("TrangThaiXuLy"));

                ds.add(tk);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ds;
    }

    public TaiKhoan dangNhap(String tenDangNhap, String matKhau) {
        String sql = "SELECT * FROM TaiKhoan WHERE TenDangNhap = ? AND MatKhau = ? AND TrangThai = 1";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, tenDangNhap);
            ps.setString(2, matKhau);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    TaiKhoan tk = new TaiKhoan();
                    tk.setMaTK(rs.getString("MaTK")); // Fix: Bổ sung MaTK
                    tk.setMaNV(rs.getString("MaNV"));
                    tk.setTenDangNhap(rs.getString("TenDangNhap"));
                    tk.setMatKhau(rs.getString("MatKhau"));

                    NhomQuyen nhomQuyen = new NhomQuyen();
                    nhomQuyen.setMaNQ(rs.getString("MaNQ"));
                    tk.setNhomQuyen(nhomQuyen);

                    tk.setTrangThaiXuLy(rs.getString("TrangThaiXuLy"));
                    return tk;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Kiểm tra username đã tồn tại chưa
    public boolean kiemTraUsernameTonTai(String username) {
        String sql = "SELECT 1 FROM TaiKhoan WHERE TenDangNhap = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}