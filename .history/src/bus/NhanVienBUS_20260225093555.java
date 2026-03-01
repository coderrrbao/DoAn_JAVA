package bus;

import java.util.*;

import dao.NhanVienDAO;

public class NhanVienBUS {
    private NhanVienDAO dao;
    
    public List<String> layDanhSachChucVu(){
        List<String> ds = dao.layDanhSachChucVu();
        return ds;
    }
}
