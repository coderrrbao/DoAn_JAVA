package bus;

import java.util.*;

import dao.NhanVienDAO;

public class NhanVienBUS {
    private NhanVienDAO dao;

    public List<String> layDanhSachChucVu(){
        return dao.layDanhSachChucVu();
    }
}
