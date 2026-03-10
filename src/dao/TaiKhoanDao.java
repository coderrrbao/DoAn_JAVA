package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dao.conection.DBConnection;
import dto.NhomQuyen;
import dto.TaiKhoan;

public class TaiKhoanDao {

    public boolean themTaiKhoan(TaiKhoan tk, Connection conn) {
        String sql = "INSERT INTO TaiKhoan (MaTK, MaNV, TenDangNhap, MatKhau, maNQ, TrangThaiXuLy, TrangThai) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, layMaTaiKhoanKhaDung(conn));
            ps.setString(2, tk.getMaNV());
            ps.setString(3, tk.getTenDangNhap());
            ps.setString(4, tk.getMatKhau());
            ps.setString(5, tk.getNhomQuyen() != null ? tk.getNhomQuyen().getMaNQ() : null);
            ps.setString(6, "Đang hoạt động");
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

    public ArrayList<TaiKhoan> layDanhSachTaiKhoan() {
        ArrayList<TaiKhoan> ds = new ArrayList<>();
        String sql = "SELECT * FROM TaiKhoan Where TrangThai = 1";

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
                    tk.setMaTK(rs.getString("MaTK"));
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

    public boolean suaTaiKhoan(TaiKhoan tk, Connection conn) {
        String sql = "UPDATE TaiKhoan SET "
                + "MaNV = ?, "
                + "MatKhau = ?, "
                + "maNQ = ?, "
                + "TrangThaiXuLy = ?,"
                + "TenDangNhap= ?"
                + "WHERE MaTK = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, tk.getMaNV());
            ps.setString(2, tk.getMatKhau());
            ps.setString(3, tk.getNhomQuyen().getMaNQ());
            ps.setString(4, tk.getTrangThaiXuLy());
            ps.setString(5, tk.getTenDangNhap());
            ps.setString(6, tk.getMaTK());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public String layMaTaiKhoanKhaDung(Connection conn) {
        String sql = "SELECT MAX(MaTK) FROM TaiKhoan";
        String maMoi = "";
        try (
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                String maCuoi = rs.getString(1);
                if (maCuoi != null) {
                    int so = Integer.parseInt(maCuoi.substring(2));
                    so++;
                    maMoi = String.format("TK%02d", so);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return maMoi;
    }

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

    public boolean kiemTraTrungUsername(Connection conn, String username) throws SQLException {
        String sql = "SELECT COUNT(*) FROM taikhoan WHERE tenDangNhap = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }

        return false;
    }

    public boolean insertTaiKhoan(Connection conn, TaiKhoan tk) throws SQLException {
        String sql = "INSERT INTO TaiKhoan (MaTK, TenDangNhap, MatKhau, MaNQ, TrangThai) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, tk.getMaTK());
            pst.setString(2, tk.getTenDangNhap());
            pst.setString(3, tk.getMatKhau());
            pst.setString(4, tk.getNhomQuyen().getMaNQ());
            pst.setInt(5, 1);
            return pst.executeUpdate() > 0;
        }
    }

}