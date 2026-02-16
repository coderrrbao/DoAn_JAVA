package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import dao.conection.DBConnection;
import dto.NhanVien;
import dto.TaiKhoan;

public class TaiKhoanDao {

    // thêm tài khoản 
    public boolean themTaiKhoan_DAO(TaiKhoan tk){

        String sql = "INSERT INTO TaiKhoan (TenTaiKhoan, TenDangNhap, MatKhau, maNQ, TrangThai) VALUES(?,?,?,?,?)";
        try(Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, tk.getTenTaiKhoan());
            ps.setString(2, tk.getTenDangNhap());
            ps.setString(3, tk.getMatKhau());
            ps.setString(4, tk.getMaNQ());
            ps.setBoolean(5, tk.getTrangThai());
            
            return ps.executeUpdate() > 0;
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
    // xóa tài khoản 
    public boolean xoaTaiKhoan_DAO(String tenDangNhap) {
        String sql = "UPDATE TaiKhoan SET TrangThai = 0 WHERE TenDangNhap = ?";
        try (Connection conn = DBConnection.getConnection(); 
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, tenDangNhap);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    // thay đổi mật khẩu 
    public boolean suaMatKhau_DAO(String tenDangNhap, String matKhauMoi){
        String sql = "UPDATE TaiKhoan SET MatKhau = ? Where TenDangNhap = ?";
        try(Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, matKhauMoi);
            ps.setString(2, tenDangNhap);
            return ps.executeUpdate() > 0;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
    //lay list tai khoan 
    public ArrayList<TaiKhoan> layDanhSachTaiKhoan_DAO() {
        ArrayList<TaiKhoan> ds = new ArrayList<>();
        String sql = "SELECT * FROM TaiKhoan";

        try (
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
        ) {
            while (rs.next()) {
                TaiKhoan tk = new TaiKhoan();
                tk.setTenTaiKhoan(rs.getString("TenTaiKhoan"));
                tk.setTenDangNhap(rs.getString("TenDangNhap"));
                tk.setMatKhau(rs.getString("MatKhau"));
                tk.setMaNQ(rs.getString("MaNQ"));
                tk.setTrangThai(rs.getBoolean("TrangThai"));
                ds.add(tk);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ds;
    }
    //test lay nhan vien 
    public NhanVien layNhanVien_DAO(String user){
            NhanVien nv = null;
            String sql = """
                    SELECT nv.*
                    FROM NhanVien nv
                    JOIN TaiKhoan tk ON nv.TaiKhoan = tk.TenDangNhap
                    WHERE tk.TenDangNhap = ?
                    """;
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){
                ps.setString(1, user);
                ResultSet rs = ps.executeQuery();
                if(rs.next()){
                    nv = new NhanVien();

                    nv.setMaNV(rs.getString("MaNV"));
                    nv.setTenNV(rs.getString("TenNV"));
                    nv.setGioiTinh(rs.getString("GioiTinh"));
                    nv.setNgaySinh(rs.getDate("NgaySinh"));
                    nv.setSdt(rs.getString("SDT"));
                    nv.setDiaChi(rs.getString("DiaChi"));
                    nv.setChucVu(rs.getString("ChucVu"));
                    nv.setTaiKhoan(rs.getString("TaiKhoan"));
                    nv.setTrangThai(rs.getBoolean("TrangThai"));
                    // nv.setAnh(rs.getString("Anh"));
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        return nv;
    }

    //dang nhap tai khoan 
    public boolean dangNhap_DAO(String tenDangNhap, String matKhau){
        String sql = "SELECT * FROM TaiKhoan WHERE TenDangNhap = ? AND MatKhau = ?";
        try (Connection conn = DBConnection.getConnection(); 
            PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, tenDangNhap);
            ps.setString(2, matKhau);
            
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next(); 
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
