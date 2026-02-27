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
        if (nv.getTenNV().isEmpty()) {
            return "Vui lòng nhập tên nhân viên";
        }
        return null;
    }
}
