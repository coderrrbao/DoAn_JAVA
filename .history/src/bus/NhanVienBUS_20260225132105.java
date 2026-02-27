package bus;

import java.util.*;

import dao.NhanVienDAO;
import dto.NhanVien;

public class NhanVienBUS {
    private NhanVienDAO dao = new NhanVienDAO();

    public List<String> layDanhSachChucVu() {
        return dao.layDanhSachChucVu();
    }

    public String themNhanVien(NhanVien nv) {
        if (nv.getTenNV().isEmpty() || nv.getDiaChi().isEmpty() || nv.getSdt().isEmpty() || nv.getSdt().isEmpty()) {
            return "Vui lòng điền đầy đủ thông tin";
        }
        Boolean result = dao.themNhanVien(nv);
        if(!result){
            return "Lỗi thêm nhân viên";
        }
        return null;
    }

    public List<NhanVien> layDanhSachNhanVien(){
        ArrayList<NhanVien> ds = new
    }
}
