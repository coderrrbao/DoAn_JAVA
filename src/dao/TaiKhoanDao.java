package dao;
import java.sql.Connection;
import java.sql.ConnectionBuilder;
import java.sql.PreparedStatement;

import dao.conection.DBConnection;
import dto.TaiKhoan;

public class TaiKhoanDao {

    // thêm tài khoản 
    public boolean themTaiKhoan_DAO(TaiKhoan tk){

        String sql = "INSERT INTO TaiKhoan (TenDangNhap, MatKhau, maNQ, TrangThai) VALUES(?,?,?,?)";
        try(Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, tk.getTenDangNhap());
            ps.setString(2, tk.getMatKhau());
            ps.setString(3, tk.getMaNQ());
            ps.setBoolean(4, tk.getTrangThai());
            
            return ps.executeUpdate() > 0;
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
    // xóa tài khoản 
    public boolean xoaTaiKhoan_DAO(String tenDangNhap){
        String sql = "DELETE FROM TaiKhoan WHERE TenDangNhap = ?";
        try(Connection conn = DBConnection.getConnection() ; PreparedStatement ps = conn.prepareStatement(sql) ){
            ps.setString(1, tenDangNhap);
            return ps.executeUpdate() > 0;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
    // thay đổi mật khẩu 
    public boolean suaMatKhau_DAO(String tenDangNhap, String matKhauMoi){
        String sql = "UPDATE FROM TaiKhoan SET MatKhau = ? Where TenDangNhap = ?";
        try(Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, matKhauMoi);
            ps.setString(2, tenDangNhap);
            return ps.executeUpdate() > 0;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
