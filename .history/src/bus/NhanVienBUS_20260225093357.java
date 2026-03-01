package bus;

import java.util.ArrayList;

import dao.NhanVienDAO;

public class NhanVienBUS {
    private NhanVienDAO dao;
    public List<String> layDanhSachChucVu(){
        ArrayList<NhanVien> ds = new ArrayList<>();
        return ds;
    }
}
