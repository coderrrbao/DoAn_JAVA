package bus;
import java.util.ArrayList;

import dao.TaiKhoanDao;
import dto.NhomQuyen;
import dto.TaiKhoan;

//them tk
public class TaiKhoanBUS {
    private TaiKhoanDao dao = new TaiKhoanDao();

    public boolean themTaiKhoan_BUS(TaiKhoan tk){
        
        if(tk == null){
            System.out.println("tai khoan khong hop le");
            return false;
        }
        if(tk.getTenDangNhap() == null || tk.getTenDangNhap().isEmpty() ||
            tk.getMatKhau() == null || tk.getMatKhau().isEmpty() ||
            tk.getMaNQ() == null || tk.getMaNQ().isEmpty() ||
            tk.getTenTaiKhoan() == null || tk.getTenDangNhap().isEmpty()){
                
            return false;
        }
        return dao.themTaiKhoan_DAO(tk);
    }
    //xoa tai khoan
    public boolean xoaTaiKhoan_BUS(String tenDangNhap){
        if(tenDangNhap == null || tenDangNhap.isEmpty()){
            return false;
        }
        return dao.xoaTaiKhoan_DAO(tenDangNhap);
    }
    //sua tai khoan
    public boolean suaMatKhau_BUS(String tenDangNhap, String matKhauMoi){
        if(tenDangNhap == null || tenDangNhap.isEmpty()){
            System.out.println("ten dang nhap khong duoc rong");
            return false;
        }
        if(matKhauMoi == null || matKhauMoi.isEmpty()){
            System.out.println("mat khau moi khong duoc rong");
            return false;
        }
        return dao.suaMatKhau_DAO(tenDangNhap, matKhauMoi);
    }
    //lay danh sach tai khoan
    public ArrayList<TaiKhoan> layDanhSachTaiKhoan_BUS(){
        return dao.layDanhSachTaiKhoan_DAO();
    }
    //dang nhap 
    public boolean dangNhap_BUS(String tenDangNhap, String MatKhau){
        return dao.dangNhap_DAO(tenDangNhap, MatKhau);
    }
}

