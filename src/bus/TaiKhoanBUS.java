package bus;
import dao.TaiKhoanDao;
import dto.TaiKhoan;

//them tk
public class TaiKhoanBUS {
    private TaiKhoanDao dao = new TaiKhoanDao();

    public boolean themTaiKhoan_BUS(TaiKhoan tk){
        
        if(tk == null){
            System.out.println("tai khoan khong hop le");
            return false;
        }
        if(tk.getTenDangNhap() == null || tk.getTenDangNhap().isEmpty()){
            System.out.println("ten dang nhap khong duoc rong");
            return false;
        }
        if(tk.getMatKhau() == null || tk.getMatKhau().isEmpty()){
            System.out.println("mat khau khong duoc rong");
            return false;
        }
        if(tk.getMaNQ() == null || tk.getMaNQ().isEmpty()){
            System.out.println("ma nhom quyen khong duoc rong");
            return false;
        }
        return dao.themTaiKhoan_DAO(tk);
    }
    //xoa tai khoan
    public boolean xoaTaiKhoan_BUS(String tenDangNhap){
        if(tenDangNhap == null || tenDangNhap.isEmpty()){
            System.out.println("ten dang nhap rong");
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
    //dang nhap 
    public boolean dangNhap_BUS(String tenDangNhap, String MatKhau){
        return dao.dangNhap_DAO(tenDangNhap, MatKhau);
    }
}

