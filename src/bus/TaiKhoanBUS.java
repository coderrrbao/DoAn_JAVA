package bus;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import dao.TaiKhoanDao;
import dao.conection.DBConnection;
import dto.NhanVien;
import dto.TaiKhoan;

//them tk
public class TaiKhoanBUS {
    private TaiKhoanDao dao = new TaiKhoanDao();

    public boolean themTaiKhoan_BUS(TaiKhoan tk){
        
        if(tk == null){
            return false;
        }

        Connection conn = DBConnection.getConnection();

        try {
            conn.setAutoCommit(false);
            if (!dao.themTaiKhoan_DAO(tk, conn)) {
                throw new SQLException();
            }
            conn.commit();
            return true;
        } catch (Exception e) {
            try {
                if (conn != null) conn.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;

        } finally {
            try {
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    //xoa tai khoan
    public boolean xoaTaiKhoan_BUS(String tenDangNhap){
        if(tenDangNhap == null || tenDangNhap.isEmpty()){
            return false;
        }
        Connection conn = DBConnection.getConnection();
        try {
            conn.setAutoCommit(false);
            if (!dao.xoaTaiKhoan_DAO(tenDangNhap, conn)) {
                throw new SQLException();
            }
            conn.commit();
            return true;
        } catch (Exception e) {
            try {
                if (conn != null) conn.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    //sua tai khoan
    public boolean suaMatKhau_BUS(String tenDangNhap, String matKhauMoi){
        if(tenDangNhap == null || tenDangNhap.isEmpty()){
            return false;
        }
        if(matKhauMoi == null || matKhauMoi.isEmpty()){
            return false;
        }
        Connection conn = DBConnection.getConnection();
        try {
            conn.setAutoCommit(false);

            if (!dao.suaMatKhau_DAO(tenDangNhap, matKhauMoi, conn)) {
                throw new SQLException();
            }
            conn.commit();
            return true;
        } catch (Exception e) {
            try {
                if (conn != null) conn.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    //lay danh sach tai khoan
    public ArrayList<TaiKhoan> layDanhSachTaiKhoan_BUS(){
        return dao.layDanhSachTaiKhoan_DAO();
    }
    //dang nhap 
    public boolean dangNhap_BUS(String tenDangNhap, String MatKhau){
        return dao.dangNhap_DAO(tenDangNhap, MatKhau);
    }

    //test nhap vao ten dang nhap lay nhan vien 
    public NhanVien layNhanVien_BUS(String user){
        return dao.layNhanVien_DAO(user);
    }

}

